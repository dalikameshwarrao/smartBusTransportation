package com.smartbustransport.dto;

import java.util.Date;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class DriversDTO {
	
	
	private Long driverId;
	private String tripId;

	private String driver;

	private String route;

	private String licenseNo;

	private String busNo;

	private String busLicensePlateNo;

	private Date timeStamp;

	private AreaAndCoordinateDTO source;
	
	private AreaAndCoordinateDTO destination;

	private AreaAndCoordinateDTO nextStop;

	private AreaAndCoordinateDTO currentLocation;
}

