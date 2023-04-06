package com.smartbustransport.dto;

import lombok.Data;

@Data
public class AnalyticsDTO {

	private GeneralStatsDTO day;
	private GeneralStatsDTO weekly;
	private GeneralStatsDTO monthly;
	private GeneralStatsDTO yearly;

}
