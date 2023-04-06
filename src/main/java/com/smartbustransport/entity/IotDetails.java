
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
@Table(name="trackingDetails")
@Data
public class IotDetails {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	
	@Column(name="tracker_Id")
	private Long tracketId;
	
	@Column(name="tracker_name")
	private String trackerName;
	
	@Column(name="tracker_type")
	private String trackerType;
	
	@Column(name="current_address")
	private String currentAddress;
	
	@Column(name="battery")
	private String battery;
			
}
