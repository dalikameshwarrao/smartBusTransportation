package com.smartbustransport.entity;

import java.io.Serializable;
import java.time.LocalTime;
import java.sql.Time;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;


@Entity
@Table(name = "NotificationEntity")
@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class NotificationEntity implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2316363918813058983L;

	@Id
	@GeneratedValue(generator = "uuid")
	@GenericGenerator(name = "uuid", strategy = "uuid2")
	@Column(name = "notification_id")
	private String notificationId;

	@Column(name = "notification_type")
	private String notificationType;// INCIDENT OR EVENT
	
	@Column(name = "trip_id")
	private String tripId;

	@Column(name = "bus_id")
	private String busId;

	@Column(name = "bus_no")
	private String busNo;
	
	@Column(name = "driver_id")
	private Long driveId;
	
	@Column(name = "driver_name")
	private String driveName;
	
	@Column(name = "route_id")
	private String routeId;

	@Column(name = "route_name")
	private String routeName;

	@Column(name = "notification_date")
	@Temporal(TemporalType.TIMESTAMP)
	private Date notificationDate;// current time stamp or client time stamp

	@Column(name = "source") // current time stamp or client time stamp
	private String source;

	@Column(name = "destination")
	private String destination;

	@Column(name = "stop_name")
	private String stopName;
	
	
	@JsonIgnore
	@Column(name = "is_Current_location" , columnDefinition = "varchar(1)")
	private String isCurrentLocation;
	
	@JsonIgnore
	@Column(name = "latitude")
	private Double latitude ;
	
	@JsonIgnore
	@Column(name = "longitude")
	private Double longitude;
	
	@JsonIgnore
	@Column(name = "speed")
	private Double speed;
	
	@JsonIgnore
	@Column(name = "fuel_consumed")
	private Double fuelConsumed;
	
	@JsonIgnore
	@Column(name = "distance_Covered")
	private Double distanceCovered;
	
	@JsonIgnore
	@Column(name = "overaloccapancy")
	private Integer overalOccupancy;
	
	@JsonIgnore
	@Column(name = "violation")
	private String violation;
	
	@JsonIgnore
	@Column(name = "trip_Start_Date")
	private Date tripStartDate;
	
	@JsonIgnore
	@Column(name = "driving_hours")
	private LocalTime drivingHours;
	
	@JsonIgnore
	@Column(name = "idle_time")
	private LocalTime idleTime;

	@JsonIgnore
	@Column(name = "driving_score")
	private Integer drivingScore;
	
	@JsonIgnore
	@Column(name = "fuel_tank_value")
	private Double fuelTankValue;
	
	@JsonIgnore
	@Column(name = "fuel_used")
	private Double fuelUsed;
	
	@JsonIgnore
	@Column(name = "fuel_cost")
	private Double fuelCost;
	
	@JsonIgnore
	@Column(name = "labour_cost")
	private Double labourCost;
	
	@JsonIgnore
	@Column(name = "step_out_Passenger")
	private Integer stepOutPassenger;
	
	@JsonIgnore
	@Column(name = "step_in_passenger")
	private Integer stepInPassenger;
	
	@JsonIgnore
	@Column(name = "maintenance_cost")
	private Double maintenanceCost;
	
	@JsonIgnore
	@Column(name = "co2_emission")
	private Double co2Emission;
	
	@JsonIgnore
	@Column(name = "ticket_sale_cost")
	private Double ticketSaleCost;


}
