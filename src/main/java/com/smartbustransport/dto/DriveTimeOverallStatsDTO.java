package com.smartbustransport.dto;

import java.util.ArrayList;
import java.util.List;


import lombok.Data;

@Data
public class DriveTimeOverallStatsDTO {
	
	
	private StatsOverallDTO day=new StatsOverallDTO();
	private StatsOverallDTO weekly=new StatsOverallDTO();
	private StatsOverallDTO monthly=new StatsOverallDTO();
	private StatsOverallDTO yearly=new StatsOverallDTO();

}
