package com.smartbustransport.services;

import java.util.List;


import com.smartbustransport.dto.LoginRequestDetails;
import com.smartbustransport.dto.LoginResponseDetails;
import com.smartbustransport.dto.LogoutDTO;
import com.smartbustransport.dto.RoleUpdateDTO;
import com.smartbustransport.dto.UserDetailDTO;
import com.smartbustransport.entity.UserDetailEntity;
import com.smartbustransport.util.CommonUtils.Roles;

public interface UserDetailService {
	
	UserDetailEntity registerUser(UserDetailDTO userDetails, Roles role);

	LoginResponseDetails userLogin(LoginRequestDetails loginRequestDetails);

	List<UserDetailEntity> findAll();

	UserDetailEntity upDateRole(RoleUpdateDTO dto);

	boolean updateCurrentRole(String userId, String currentRole);

	boolean userLogout(LogoutDTO dto);

}
