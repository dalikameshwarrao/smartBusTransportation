package com.smartbustransport.dto;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;

@Data
public class IncidentsDTO {
	
	private int totalCount;
	private List<IncidentDTO> incidentsList=new ArrayList<>();
	

}
