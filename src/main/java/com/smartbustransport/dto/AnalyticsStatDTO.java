package com.smartbustransport.dto;

import lombok.Data;

@Data
public class AnalyticsStatDTO {
	private StatsDTO day;
	private StatsDTO weekly;
	private StatsDTO monthly;
	private StatsDTO yearly;
	

}
