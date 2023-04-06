package com.smartbustransport.dto;

import java.util.ArrayList;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;

//@JsonInclude(Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class TripDTO {
	
	public String tripNo;
	public Double speed;
	public Double fuelConsumed;
	public Double distanceCovered;
	public String driver;
	public String status;
	public String distance;
	public RouteDetailsDTO routeDetails;
	
}
