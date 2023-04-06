package com.smartbustransport.dto;

import java.util.List;

import lombok.Data;

@Data
public class TripStatsDTO {
	
	private double trips;
	private String observation;
	private double percentage;
	private List<TripAnalyticDTO> analyticsData;
	

}
