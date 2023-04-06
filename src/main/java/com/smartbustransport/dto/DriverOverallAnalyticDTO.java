package com.smartbustransport.dto;

import lombok.Data;



@Data
public class DriverOverallAnalyticDTO {
	
	
	 private AnalyticsDTO generalStats;
	 private TripOverallAnalyticsDTO tripStats;
	 private DistancesOverallDrivenDTO avgDistanceDrivenStats;
	 private LabourOverallTripAnalyticsDTO labourCostStats;
     private DriveTimeOverallStatsDTO driveTimeStats;
     private ViolationsStatsDTO violationsStats;

}
