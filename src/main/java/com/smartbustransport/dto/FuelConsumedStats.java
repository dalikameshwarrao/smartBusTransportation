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
public class FuelConsumedStats {
	
	
	private FuelGenericMetrics day;
    private FuelGenericMetrics weekly;
    private FuelGenericMetrics monthly;
    private FuelGenericMetrics yearly;

}
