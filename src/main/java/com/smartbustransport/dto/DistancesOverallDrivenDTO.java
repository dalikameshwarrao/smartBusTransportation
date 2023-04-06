package com.smartbustransport.dto;

import lombok.Data;

@Data
public class DistancesOverallDrivenDTO {
	
	
	private DistanceOverallDrivenDTO day;
	private DistanceOverallDrivenDTO weekly;
	private DistanceOverallDrivenDTO monthly;
	private DistanceOverallDrivenDTO yearly;

}
