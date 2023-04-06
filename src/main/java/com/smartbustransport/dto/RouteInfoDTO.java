package com.smartbustransport.dto;

import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Data;

@JsonInclude(Include.NON_EMPTY)
@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class RouteInfoDTO{
	private String routeName;
	private int totalBuses;
	private double avgRevenue;
	private String regionDepot;
	private long totalTrips;
	private float safetyScore;
	private double fuelUsage;
	private double totalPassengers;
	private double totalHours;
	private double totalViolations;
	private double totalMiles;
	public List<TripDTO> trips;
    public List<PastViolationDTO> pastViolations;
}
