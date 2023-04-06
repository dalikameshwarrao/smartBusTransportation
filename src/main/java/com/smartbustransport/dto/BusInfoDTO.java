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
public class BusInfoDTO{
	private String busNo;
	private String licensePlateNo;
	private String regionDepo;
	private Long totalTrips;
	private float safetyScore;
	private int totalPassengers;
	private double totalMiles;
	private double avgFuelEconomy;
	private Date nextMaintanance;
	public List<TripDTO> trips;
    public List<PastViolationDTO> pastViolations;
}
