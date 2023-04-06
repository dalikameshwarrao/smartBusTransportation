package com.smartbustransport.dto;

import java.util.List;

public class RoleUpdateDTO {
	
	private Long userId;

	private List<String> role;

	public List<String> getRole() {
		return role;
	}

	public void setRole(List<String> role) {
		this.role = role;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

}
