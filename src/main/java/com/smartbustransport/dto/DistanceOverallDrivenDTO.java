package com.smartbustransport.dto;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;

@Data
public class DistanceOverallDrivenDTO {
	 private String avgDistanceDriven;
	 private String observation;
	 private String percentage;
	 public List<TripAnalyticDTO>  analyticsData=new ArrayList<>();

}
