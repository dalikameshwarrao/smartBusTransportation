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
public class Co2Analytics {
	
	private String emission;
	
	private String observation;
	
	private String percentage;
	
	private List<Analytics> analytics;

}
