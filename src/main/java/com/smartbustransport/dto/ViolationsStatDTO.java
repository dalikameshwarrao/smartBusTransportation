package com.smartbustransport.dto;

import lombok.Data;

@Data
public class ViolationsStatDTO {

	 private int overspeeding;
	 private int harshBraking;
	 private int harshAccelaration;
	 private int sharpCorner;
	 private int distractions;
	 private int otherViolations;
	 private int highFatiuge;
	 private int sharpTurns;
	
}
