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
public class DistanceTravelledStats {
	
	
	private GenericMetrics day;
    private GenericMetrics weekly;
    private GenericMetrics monthly;
    private GenericMetrics yearly;

}
