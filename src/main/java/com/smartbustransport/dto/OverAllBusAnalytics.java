package com.smartbustransport.dto;

import lombok.Data;

@Data
public class OverAllBusAnalytics {

	private GeneralStats generalStats;
	
	private IncomeStats incomeStats;
	
	private DistanceTravelledStats distanceTravelledStats;
	
	private FuelConsumedStats fuelConsumedStats;
	
	private TravellingHoursStats travellingHoursStats;
	
	private Co2EmissionAnalytics co2EmissionAnalytics;
	
	private ExpensesStats expensesStats;
	
}
