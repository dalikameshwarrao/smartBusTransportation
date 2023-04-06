package com.smartbustransport.dto;

import java.util.Date;
import java.util.List;

import lombok.Data;

@Data
public class LoginResponseDetails {
	private String userName;

	private Long userId;

	private Date loginTime;

	private Date expiry;

	private List<String> roles;

	private String phoneNum;

	private String currentRoleType;

	private String firstName;

	private String lastName;
}
