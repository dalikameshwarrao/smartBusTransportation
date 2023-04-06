package com.smartbustransport.dto;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;

@Data
public class MetricAnalyticDTO {
	private String metricName;
	private List<TripAnalyticDTO> analytics=new ArrayList<>();

}
