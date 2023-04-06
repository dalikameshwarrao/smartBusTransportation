package com.smartbustransport.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Data;

@JsonInclude(Include.NON_EMPTY)
@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class DriverInfoDTO{
	private String drivingLicense;
	private String regionDepo;
	private double totalTrips;
	private String drivingScore;
	private int passengersCarried;
	private double totalHoursDriven;
	private double totalViolations;
	private double totalMiles;
	public List<TripDTO> trips;
    public List<PastViolationDTO> pastViolations;
}
