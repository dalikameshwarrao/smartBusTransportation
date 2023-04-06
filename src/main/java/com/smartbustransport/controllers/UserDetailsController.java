package com.smartbustransport.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.smartbustransport.dto.LoginRequestDetails;
import com.smartbustransport.dto.LoginResponseDetails;
import com.smartbustransport.dto.LogoutDTO;
import com.smartbustransport.dto.RoleUpdateDTO;
import com.smartbustransport.dto.UserDetailDTO;
import com.smartbustransport.entity.UserDetailEntity;
import com.smartbustransport.services.UserDetailService;
import com.smartbustransport.util.CommonUtils.Roles;
import com.smartbustransport.util.StringResponse;

@RestController
@RequestMapping(path ="/smartbus")
public class UserDetailsController {
	
	@Autowired
	UserDetailService userService;

	@PostMapping("/v1/registration")
	public ResponseEntity<?> registerUser(@RequestBody UserDetailDTO userDetailsdto) {
		UserDetailEntity registerUser = userService.registerUser(userDetailsdto, Roles.OWNER);
		return new ResponseEntity<>(registerUser, HttpStatus.OK);
	}

	@PostMapping("/v1/registration/admin")
	public ResponseEntity<?> registerUserAsAdmin(@RequestBody UserDetailDTO userDetailsdto) {
		UserDetailEntity registerUser = userService.registerUser(userDetailsdto, Roles.ADMIN);
		return new ResponseEntity<>(registerUser, HttpStatus.OK);
	}

	@PostMapping("/v1/login")
	public ResponseEntity<?> loginUser(@RequestBody LoginRequestDetails loginDetails) {
		LoginResponseDetails userLogin = userService.userLogin(loginDetails);

		return new ResponseEntity<>(userLogin, HttpStatus.OK);
	}

	@GetMapping("/v1/getAllUsers")
	public ResponseEntity<?> getAllusers() {
		List<UserDetailEntity> findAll = userService.findAll();
		// List<UserDetailsEntity> findAll = dao.findAll();
		return new ResponseEntity<>(findAll, HttpStatus.OK);
	}

	@PostMapping("/v1/upDateRole")
	public ResponseEntity<?> upDateUserRole(@RequestBody RoleUpdateDTO dto) {
		UserDetailEntity userDetails = userService.upDateRole(dto);
		return new ResponseEntity<>(userDetails, HttpStatus.OK);

	}
	
	@PostMapping("/v1/currentRoleUpdate/{userId}/{currnetRole}")
	public ResponseEntity<?> updateCurrentRole(@PathVariable String userId,@PathVariable ("currnetRole") String currentRole){
		boolean status = userService.updateCurrentRole(userId, currentRole);	
		return new ResponseEntity<>(status,HttpStatus.OK);
		
	}
	@PostMapping("/v1/userLogout")
	public ResponseEntity<StringResponse> userLogout(@RequestBody LogoutDTO dto){
		boolean status = userService.userLogout(dto);
		if(status) {
			StringResponse sr = new StringResponse();
			sr.setMessage("Success");
			return new ResponseEntity<>(sr, HttpStatus.OK);
		}else {
			StringResponse sr = new StringResponse();
			sr.setMessage("Failed");
			return new ResponseEntity<>(sr, HttpStatus.UNPROCESSABLE_ENTITY);
		}
		
	}
	

}
