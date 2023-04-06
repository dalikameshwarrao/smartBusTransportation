package com.smartbustransport.dto;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;

@Data
public class AlertsDTO {
	
	private int totalCount;
	private List<AlertDTO> alertList=new ArrayList<>();
	

}
