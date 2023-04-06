package com.smartbustransport.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class TravelMetrics {
	
    private String avgRunningHours;
	
	private String runningHrsObservation;
	
	private String runningHrsPercentage;
	
	private String avgIdleHours;
	
	private String idleHrsObservation;
	
	private String idleHrsPercentage;
	
	private List<TravelingMetricName> travelingMetricNameList;
	

}
