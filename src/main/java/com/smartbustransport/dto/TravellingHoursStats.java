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
public class TravellingHoursStats {
	
	private TravelMetrics day;
    private TravelMetrics weekly;
    private TravelMetrics monthly;
    private TravelMetrics yearly;

}
