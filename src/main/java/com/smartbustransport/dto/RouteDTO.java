

package com.smartbustransport.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@JsonInclude(Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class RouteDTO {

	
	public String getRouteName() {
		return routeName;
	}
	public void setRouteName(String routeName) {
		this.routeName = routeName;
	}
	public String getBusNo() {
		return busNo;
	}
	public void setBusNo(String busNo) {
		this.busNo = busNo;
	}
	public int getNoOfBuses() {
		return noOfBuses;
	}
	public void setNoOfBuses(int noOfBuses) {
		this.noOfBuses = noOfBuses;
	}
	public double getFuelConsumed() {
		return fuelConsumed;
	}
	public void setFuelConsumed(double fuelConsumed) {
		this.fuelConsumed = fuelConsumed;
	}
	public double getDistanceCovered() {
		return distanceCovered;
	}
	public void setDistanceCovered(double distanceCovered) {
		this.distanceCovered = distanceCovered;
	}
	public String getStops() {
		return stops;
	}
	public void setStops(String stops) {
		this.stops = stops;
	}
	public String getDriverName() {
		return driverName;
	}
	public void setDriverName(String driverName) {
		this.driverName = driverName;
	}
	public Date getTripTime() {
		return tripTime;
	}
	public void setTripTime(Date tripTime) {
		this.tripTime = tripTime;
	}
	public AreaAndCoordinateDTO getSource() {
		return source;
	}
	public void setSource(AreaAndCoordinateDTO source) {
		this.source = source;
	}
	public AreaAndCoordinateDTO getDestination() {
		return destination;
	}
	public void setDestination(AreaAndCoordinateDTO destination) {
		this.destination = destination;
	}
	public AreaAndCoordinateDTO getCurrentLocation() {
		return currentLocation;
	}
	public void setCurrentLocation(AreaAndCoordinateDTO currentLocation) {
		this.currentLocation = currentLocation;
	}
	public AreaAndCoordinateDTO getNextStop() {
		return nextStop;
	}
	public void setNextStop(AreaAndCoordinateDTO nextStop) {
		this.nextStop = nextStop;
	}
	
	public Double getSpeed() {
		return speed;
	}
	public void setSpeed(Double speed) {
		this.speed = speed;
	}
	
	public String getBusIconType() {
		return busIconType;
	}
	public void setBusIconType(String busIconType) {
		this.busIconType = busIconType;
	}

	
	private String routeName;
	private String tripId;
	public String getTripId() {
		return tripId;
	}
	public void setTripId(String tripId) {
		this.tripId = tripId;
	}


	private String busNo;
	private int noOfBuses;
	private double fuelConsumed;
	private double distanceCovered;
	private String stops;
	private String driverName;
	private Date tripTime;
	private Double speed;
	private String busIconType;
	private AreaAndCoordinateDTO source;
	private AreaAndCoordinateDTO destination;
	private AreaAndCoordinateDTO currentLocation;
	private AreaAndCoordinateDTO nextStop;
	
	
}
