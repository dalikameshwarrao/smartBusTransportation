package com.smartbustransport.dto;

import lombok.Data;



@Data
public class DriverAnalyticDTO {
	
	 private String name;
	 private String licenseNo;
	 private AnalyticsDTO generalStats;
//	 private TripStatsDTO tripStats;
//	 private AvgDistanceDrivenStatsDTO avgDistanceDrivenStats;
//	 private LabourCostStatsDTO labourCostStats;
//	 private DriveTimeStatsDTO driveTimeStats;
//	 private ViolationsStatsDTO violationsStats;
//	 
	 
	 private TripAnalyticsDTO tripStats;
	 private TripAnalyticsDTO avgDistanceDrivenStats;
	 private LabourTripAnalyticsDTO labourCostStats;
     private DriveTimeStatsDTO driveTimeStats;
     private ViolationsStatsDTO violationsStats;

}
