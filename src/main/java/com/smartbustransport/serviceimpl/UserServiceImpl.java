package com.smartbustransport.serviceimpl;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.smartbustransport.dto.LoginRequestDetails;
import com.smartbustransport.dto.LoginResponseDetails;
import com.smartbustransport.dto.LogoutDTO;
import com.smartbustransport.dto.RoleUpdateDTO;
import com.smartbustransport.dto.UserDetailDTO;
import com.smartbustransport.entity.DeviceTokenEntity;
import com.smartbustransport.entity.UserDetailEntity;
import com.smartbustransport.exception.CohRuntimeException;
import com.smartbustransport.exception.UserRuntimeException;
import com.smartbustransport.repository.DeviceTokenRepo;
import com.smartbustransport.repository.UserDetailsRepository;
import com.smartbustransport.services.UserDetailService;
import com.smartbustransport.util.CommonUtils.Roles;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class UserServiceImpl implements UserDetailService {

	@Autowired
	private UserDetailsRepository userDetailsDao;

	@Autowired
	private DeviceTokenRepo deviceTokenDAO;

	@Override
	public UserDetailEntity registerUser(UserDetailDTO userDetailsdto, Roles role) {
		userDetailsValidation(userDetailsdto);
		UserDetailEntity userDetailsEntity = toEntity(userDetailsdto);

		try {
			byte[] salt = getSalt();
			String securePassword = getSecurePassword(userDetailsdto.getPassword(), salt);

			userDetailsEntity.setPassword(securePassword);
			userDetailsEntity.setSalt(salt);
			userDetailsEntity.setCreatedAt(new Date());
			List<String> roles = new ArrayList<>();

			if (role.toString().equals(Roles.ADMIN.toString())) {
				roles.add(role.toString());
				userDetailsEntity.setRoles(roles);
				userDetailsEntity.setCurrentRoleType(Roles.ADMIN.toString());
			} else {
				roles.add(role.toString());
				userDetailsEntity.setRoles(roles);
				userDetailsEntity.setCurrentRoleType(Roles.OWNER.toString());
			}
			return userDetailsDao.save(userDetailsEntity);
		} catch (Exception e) {
			e.printStackTrace();
			throw new UserRuntimeException(e.getMessage());
		}

	}

	@Override
	public LoginResponseDetails userLogin(LoginRequestDetails loginRequestDetails) {

		if (loginRequestDetails.getDeviceToken() != null && !loginRequestDetails.getDeviceToken().isEmpty()) {
			DeviceTokenEntity deviceTokenEntity = new DeviceTokenEntity();
			deviceTokenEntity.setEmailId(loginRequestDetails.getUserName().toLowerCase());
			deviceTokenEntity.setDeviceToken(loginRequestDetails.getDeviceToken());
			deviceTokenDAO.save(deviceTokenEntity);
		}

		UserDetailEntity findByEmail = userDetailsDao.findByUserName(loginRequestDetails.getUserName().toLowerCase());
		if (null != findByEmail) {
			String securePassword = getSecurePassword(loginRequestDetails.getPassWord(), findByEmail.getSalt());

			if (findByEmail.getPassword().equalsIgnoreCase(securePassword)) {
				LoginResponseDetails loginResponse = new LoginResponseDetails();
				loginResponse.setLoginTime(new Date());
				loginResponse.setUserId(findByEmail.getUserId());
				loginResponse.setUserName(findByEmail.getUserName());
				loginResponse.setRoles(findByEmail.getRoles());
				loginResponse.setPhoneNum(findByEmail.getPhoneNumber());
				loginResponse.setCurrentRoleType(findByEmail.getCurrentRoleType());
				loginResponse.setFirstName(findByEmail.getFirstName());
				loginResponse.setLastName(findByEmail.getLastName());
				return loginResponse;
			} else {
				throw new UserRuntimeException("Please enter correct password");

			}

		} else {
			throw new UserRuntimeException("no such record found");
		}

	}

	void userDetailsValidation(UserDetailDTO userDetails) {
		if (null == userDetails.getFirstName()) {
			throw new UserRuntimeException("please provid valid firstName");
		}
		if (null == userDetails.getEmail()) {
			throw new UserRuntimeException("please provid valid email");
		}

		if (null == userDetails.getPassword()) {
			throw new UserRuntimeException("please provid valid password");
		}

		UserDetailEntity findByEmail = userDetailsDao.findByUserName(userDetails.getEmail());
		if (null != findByEmail) {
			throw new UserRuntimeException("user already existed");
		}
	}

	//
	void existingUserValidation() {
	}

	// password encryption

	private static byte[] getSalt() throws NoSuchAlgorithmException, NoSuchProviderException {
		// Always use a SecureRandom generator
		SecureRandom sr = SecureRandom.getInstance("SHA1PRNG", "SUN");
		// Create array for salt
		byte[] salt = new byte[16];
		// Get a random salt
		sr.nextBytes(salt);
		// return salt
		return salt;
	}

	private static String getSecurePassword(String password, byte[] salt) {
		String generatedPassword = null;
		try {

			MessageDigest md = MessageDigest.getInstance("MD5");
			md.update(salt);
			byte[] bytes = md.digest(password.getBytes());
			// Convert it to decimal hexadecimal format
			StringBuilder sb = new StringBuilder();
			for (int i = 0; i < bytes.length; i++) {
				sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
			}
			// Get complete hashed password in hex format
			generatedPassword = sb.toString();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return generatedPassword;
	}

	private UserDetailEntity toEntity(UserDetailDTO dto) {
		UserDetailEntity userDetailsEntity = new UserDetailEntity();
		userDetailsEntity.setFirstName(dto.getFirstName());
		userDetailsEntity.setLastName(dto.getLastName());
		userDetailsEntity.setPhoneNumber(dto.getPhoneNumber());
		userDetailsEntity.setUserName(dto.getEmail().toLowerCase());
		return userDetailsEntity;

	}

	@Override
	public List<UserDetailEntity> findAll() {
		List<UserDetailEntity> findAll = userDetailsDao.findAll();
		return findAll;
	}

	@Override
	public UserDetailEntity upDateRole(RoleUpdateDTO dto) {
		Optional<UserDetailEntity> findById = userDetailsDao.findById(dto.getUserId());
		UserDetailEntity userDetailsEntity = findById.get();
		userDetailsEntity.setRoles(dto.getRole());
		UserDetailEntity save = userDetailsDao.save(userDetailsEntity);
		return save;
	}

	@Override
	public boolean updateCurrentRole(String userId, String currentRole) {
		Optional<UserDetailEntity> findById = userDetailsDao.findById(Long.valueOf(userId));
		if (null != findById.get()) {
			UserDetailEntity userDetailsEntity = findById.get();
			userDetailsEntity.setCurrentRoleType(currentRole);
			userDetailsDao.save(userDetailsEntity);
			return true;
		} else {
			throw new UserRuntimeException("not a valid user");
		}
		// TODO Auto-generated method stub
	}

	@Override
	public boolean userLogout(LogoutDTO dto) {

		try {
			if (null == dto.getToken()) {
				throw new CohRuntimeException("Please provide valid token");
			}
			deviceTokenDAO.deletetoken(dto.getToken());
			return true;
		} catch (CohRuntimeException e) {
			throw e;
		} catch (Exception e) {
			e.printStackTrace();
			throw new CohRuntimeException(e.getMessage());
		}

	}

}
