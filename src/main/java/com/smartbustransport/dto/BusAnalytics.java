package com.smartbustransport.dto;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Data;

@JsonInclude(Include.NON_EMPTY)
@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class BusAnalytics implements Serializable {
	
	private String busNO;
	
	private String busLicensePlateNo;
	
	private GeneralStats generalStats;
	
	private IncomeStats incomeStats;
	
	private DistanceTravelledStats distanceTravelledStats;
	
	private FuelConsumedStats fuelConsumedStats;
	
	private TravellingHoursStats travellingHoursStats;
	
	private Co2EmissionAnalytics co2EmissionAnalytics;
	
	private ExpensesStats expensesStats;
	
}
