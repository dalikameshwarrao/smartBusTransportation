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
@Table(name = "Violation")
@Data
public class ViolationEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "driver_id")
	private int driver_id;
	
	@Column(name = "past_violation_id")
	private int past_violation_id;
	
	@Column(name = "driver_name")
	private String driver_name;
	
	@Column(name = "violation_type")
	private String violation_type;
	
	@Column(name ="bus_no" )
	private int bus_no;
	
	@Column(name = "route_name")
	private String route_name;
	
	@Column(name = "dates")
	private Date dates;

}
