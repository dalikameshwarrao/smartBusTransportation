package com.smartbustransport.dto;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;

@Data
public class DriverAnalyticsDTO {
	
	private List<DriverAnalyticDTO> driverAnalytics=new ArrayList<>();
	private DriverOverallAnalyticDTO overAllAnalytics=new DriverOverallAnalyticDTO(); 
	
}
