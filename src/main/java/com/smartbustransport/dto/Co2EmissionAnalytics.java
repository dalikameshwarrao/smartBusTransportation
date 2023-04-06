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
public class Co2EmissionAnalytics {
	
	private Co2Analytics day;
    private Co2Analytics weekly;
    private Co2Analytics monthly;
    private Co2Analytics yearly;
	

}
