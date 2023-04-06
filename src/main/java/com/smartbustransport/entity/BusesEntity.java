
package com.smartbustransport.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.Data;


@Entity
@Table(name="Buses")
@Data
public class BusesEntity {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	
	@Column(name="Bus_id")
	private Long busId;
	
	@Column(name="Bus_no")
	private String busNo;
	
	@Column(name="Route_name")
	private String routeName;
	
	@Column(name="bus_license_number")
	private String busLicenseNumber;
	
	@Column(name="region_depot")
	private String regionDepot;
	
	
	@Column(name="total_trips")
	private Long totalTrips;
	
	
	@Column(name="safety_score")
	private float safetyScore;
	
	
	@Column(name="total_passengers")
	private Integer totalPassengers;
	
	
	@Column(name="total_miles")
	private double totalMiles;
	
	
	@Column(name="avg_fuel_econmoy")
	private double avgFuelEconmoy;
	
	
	@Column(name="next_maintence")
	private Date nextmaintence;
	
	
	@Column(name="capcity_of_the_bus")
	private Integer capcityOfTheBus;
	
	
	@Column(name="bus_status")
	private String busStatus;
	
	@Column(name="bus_type")
	private String busType;
		
}
