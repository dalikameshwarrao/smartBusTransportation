package com.smartbustransport.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Table(name="Route_detail")
@Data
public class RouteDetailEntity {
	
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="route_details_Id")
	private String route_details_Id;
	@Column(name="route_id")
	private String routeId;
	@Column(name="stop_name")
	private String stopName;
	@Column(name="route_no")
	private int routeNo;
	@Column(name="total_miles")
	private double totalMiles;
	@Column(name="latitude" )
	private String latitude;
	@Column(name="longitude")
	private String longitude;

}
