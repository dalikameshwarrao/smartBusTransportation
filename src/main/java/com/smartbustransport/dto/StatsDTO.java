package com.smartbustransport.dto;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;

@Data
public class StatsDTO {
	private String  driveTime;
	private String driveObservation;
	private String drivePercentage;
	private String idleTime;
	private String idleObservation;
	private String idlePercentage;
	private List<DriveTimeStatsAnalyticsDataDTO> analyticsData=new ArrayList<>();
	

}
