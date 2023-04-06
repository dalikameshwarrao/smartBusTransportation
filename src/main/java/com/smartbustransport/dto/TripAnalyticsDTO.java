package com.smartbustransport.dto;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;

@Data
public class TripAnalyticsDTO {
	
	
	private TripStatsDTO day;
	private TripStatsDTO weekly;
	private TripStatsDTO monthly;
	private TripStatsDTO yearly;


}
 