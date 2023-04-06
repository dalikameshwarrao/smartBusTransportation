package com.smartbustransport.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Data;

@Data
@JsonInclude(Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class EventDTO {
	
	
	private String event;
	private String tripId;
	private String busNo;
	private String route;
	private AreaAndCoordinateDTO source;
	private AreaAndCoordinateDTO destination;
	private AreaAndCoordinateDTO currentLocation;
	private AreaAndCoordinateDTO nextStop;
	private String driver;
	private String licenceNo;
	private String timeStamp;
	

}
