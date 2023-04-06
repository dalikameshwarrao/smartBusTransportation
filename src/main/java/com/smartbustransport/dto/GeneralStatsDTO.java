package com.smartbustransport.dto;

import lombok.Data;



@Data
public class GeneralStatsDTO {
	
	private int totalTrips;
	private double avgDistanceDriven;
	private long avgDrivingHours;
	private double avgLabourCost;
	private double avgDrivingScore;

}
