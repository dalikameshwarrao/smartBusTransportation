package com.smartbustransport.dto;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;

@Data
public class BusOverAllAnalytics {
	
	private List<BusAnalytics> busAnalytics;
	private OverAllBusAnalytics overAllBusAnalytics; 

}
