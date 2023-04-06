package com.smartbustransport.entity;

import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Table(name="trips")
@Data
public class TripEntity {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="trip_id")
	private int tripId;
	
	@Column(name="driver_id")
	private int driverId;
	
	@Column(name="bus_id")
	private int busId;
	
	@Column(name="bus_no")
	private String busNo;
	
	@Column(name="driver_name")
	private String driverName;
	
	@Column(name="route_id")
	private int  routeId;
	
	@Column(name="route_name")
	private String routeName;
	
	@Column(name="source")
	private String source;
	
	@Column(name="destination")
	private String destination;
	
	@Column(name="total_miles")
	private double totalMiles;
	
	@Column(name="date_And_Time")
	private Date   dateAndTime;
	
	@Column(name="status")
	private String status;
	
	@Column(name="overaloccapancy")
	private String overalOccapancy;
	
	@Column(name="spedd")
	private int speed;
	
	@Column(name="fuel_consumed")
	private float fuelConsumed;
	
	@Column(name="current_location")
	private String currentLocation;
	
	@Column(name="lattitude")
	private String lattitude;
	
	@Column(name="longtitude")
	private String longtitude;
	
	@Column(name="violation")
	private String violation;
	
	

}
