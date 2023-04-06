package com.smartbustransport.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class FuelGenericMetrics {
	private String avgFuelConsumed;
	private String observation;
	private String percentage;
	private List<Analytics> analytics;
}
