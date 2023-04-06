package com.smartbustransport.dto;

import lombok.Data;

@Data
public class NotificationDTO {

	private EventsDTO events;
	
	private IncidentsDTO incidents;
	
	private AlertsDTO alerts;
	
}
