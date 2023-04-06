package com.smartbustransport.dto;

import lombok.Data;

@Data
public class ViolationsStatsDTO {
	private ViolationsStatDTO day;
	private ViolationsStatDTO weekly;
	private ViolationsStatDTO monthly;
	private ViolationsStatDTO yearly;

}
