package com.smartbustransport.dto;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class IncomeMetricName {
	private String metricName;
	private List<Analytics> analytics;
}
