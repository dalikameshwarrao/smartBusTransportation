package com.smartbustransport.dto;

import java.util.List;

import lombok.Data;

@Data
public class LabourTripStatsDTO {
	
	private double labourCost;
	private String observation;
	private double percentage;
	private List<TripAnalyticDTO> analyticsData;
	

}
