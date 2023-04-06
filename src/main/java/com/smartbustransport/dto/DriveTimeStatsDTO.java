package com.smartbustransport.dto;

import java.util.ArrayList;
import java.util.List;


import lombok.Data;

@Data
public class DriveTimeStatsDTO {
	
	
	private StatsDTO day=new StatsDTO();
	private StatsDTO weekly=new StatsDTO();
	private StatsDTO monthly=new StatsDTO();
	private StatsDTO yearly=new StatsDTO();

}
