package com.smartbustransport.dto;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;

@Data
public class LabourTripAnalyticsDTO {
	
	
	private LabourTripStatsDTO day;
	private LabourTripStatsDTO weekly;
	private LabourTripStatsDTO monthly;
	private LabourTripStatsDTO yearly;


}
 