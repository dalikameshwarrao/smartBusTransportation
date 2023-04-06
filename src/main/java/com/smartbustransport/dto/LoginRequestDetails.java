package com.smartbustransport.dto;

import lombok.Data;

@Data
public class LoginRequestDetails {

	private String userName;

	private String passWord;
	
	private String deviceToken;
}
