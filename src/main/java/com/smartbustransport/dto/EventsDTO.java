package com.smartbustransport.dto;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;

@Data
public class EventsDTO {
	
	private int totalCount;
	private List<EventDTO> eventsList=new ArrayList<>();
	

}
