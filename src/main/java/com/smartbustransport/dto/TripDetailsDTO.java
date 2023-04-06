package com.smartbustransport.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Data;

@JsonInclude(Include.NON_EMPTY)
@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class TripDetailsDTO {

	public BusInfoDTO busInfo;
	public DriverInfoDTO driverInfo;
	public RouteInfoDTO routeInfo;

}
