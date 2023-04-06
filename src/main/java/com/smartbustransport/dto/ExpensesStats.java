package com.smartbustransport.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ExpensesStats {
	private String observation;
	private String percentage;
	private ExpenseAnalytics day;
    private ExpenseAnalytics weekly;
    private ExpenseAnalytics monthly;
    private ExpenseAnalytics yearly;

}
