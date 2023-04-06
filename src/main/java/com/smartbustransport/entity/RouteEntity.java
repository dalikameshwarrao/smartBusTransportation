package com.smartbustransport.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name="Route_info")
@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class RouteEntity {
	
	
	
	
	@Id
	@GeneratedValue(generator = "uuid")
	@GenericGenerator(name = "uuid", strategy = "uuid2")
	@Column(name="Route_id")
	private String routeId;
	@Column(name="route_name")
	private String routeName;
	@Column(name="source")
	private String source;
	@Column(name="destination")
	private String destination;
	@Column(name="total_stop")
	private int  totalStop;
	@Column(name="total_miles")
	private double totalMiles;
	@Column(name="bus_fare")
	private double busFare;
	
	@Column(name="safety_score")
	private int safetyScore;
	
	@Column(name="region_depot")
	private String regionDepot; 
	 

}
