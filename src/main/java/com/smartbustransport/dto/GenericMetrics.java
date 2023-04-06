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
public class GenericMetrics {
	
	private String avgDistanceTravelled;
	private String observation;
	private String percentage;
	private List<Analytics> analytics;

}
