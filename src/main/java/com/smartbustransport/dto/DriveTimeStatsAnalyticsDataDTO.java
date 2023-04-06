package com.smartbustransport.dto;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;

@Data
public class DriveTimeStatsAnalyticsDataDTO{
	
	
	private String metricName;
	private List<TripAnalyticDTO> analyticsData=new ArrayList<>();


}
 