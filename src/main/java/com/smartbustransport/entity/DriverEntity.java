package com.smartbustransport.entity;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import lombok.Data;


@Entity
@Table(name = "Drivers")
@Data
public class DriverEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "driver_id")
	private Long driverId;

	@Column(name = "driver_name")
	private String driverName;

	@Column(name = "driving_license")
	private String drivingLicense;

	@Column(name = "driving_score")
	private String drivingScore;

	@Column(name = "region_depot")
	private String regionDepot;

	@Column(name = "total_trips")
	private Long totalTrips;

	@Column(name = "passengers_carried")
	private Integer passengersCarried;

	@Column(name = "total_hours_driven")
	private float totalHoursDriven;

	@Column(name = "total_violations")
	private Integer totalViolations;
	

}