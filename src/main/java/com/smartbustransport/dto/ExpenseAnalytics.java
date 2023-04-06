package com.smartbustransport.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ExpenseAnalytics {

	private String fuel;
	private String maintenance;
	private String others;
}
