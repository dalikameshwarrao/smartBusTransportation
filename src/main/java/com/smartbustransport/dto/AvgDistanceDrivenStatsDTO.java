package com.smartbustransport.dto;

import lombok.Data;

@Data
public class AvgDistanceDrivenStatsDTO {
	
	
	private double avgDistanceDriven;
	private String observation;
	private double percentage;
	private TripAnalyticsDTO analytics;

}
