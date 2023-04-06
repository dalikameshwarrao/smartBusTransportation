package com.smartbustransport.dto;

import java.util.List;

import lombok.Data;

@Data
public class LabourOverallTripStatsDTO {

	private double avgLabourCost;
	private String observation;
	private double percentage;
	private List<TripAnalyticDTO> analyticsData;
}
