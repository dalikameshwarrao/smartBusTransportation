package com.smartbustransport.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Stats {
	
	private String revenue;
    private String avgFuelConsumed;
    private String avgRunningHours;
    private String avgCO2Emission;
    private String avgPassengerCarried;
	

}
