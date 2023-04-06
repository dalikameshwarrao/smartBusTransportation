package com.smartbustransport.entity;

import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;


@Data
@Entity
@Table(name="trip_detail")
public class TripDetailEntity {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="trip_detail_id")
	private int trip_details_Id;
	@Column(name="trip_Id")
	private int tripId;
	@Column(name="stop_name")
	private String stopName;
	@Column(name="miles")
	private double miles;
	@Column(name="date_and_time")
	private Date dateAndTime;
	@Column(name="violation")
	private String violation;
	@Column(name="next_stop")
	private String nextStop;
	@Column(name="isreached")
	private String isReached;
	@Column(name="source")
	private String source;
	@Column(name="destination")
	private String destination;
	
	

}
