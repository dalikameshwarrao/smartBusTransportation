package com.smartbustransport.dto;

import lombok.Data;

@Data
public class LabourOverallTripAnalyticsDTO {
	
	private LabourOverallTripStatsDTO day;
	private LabourOverallTripStatsDTO weekly;
	private LabourOverallTripStatsDTO monthly;
	private LabourOverallTripStatsDTO yearly;

}
