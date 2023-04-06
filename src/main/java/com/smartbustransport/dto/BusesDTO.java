package com.smartbustransport.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class BusesDTO {
	
private Long busId;
private String tripId;

private String busNo;

private String route;

private String busIconType;

private String speed;

private String fuelConsumed;

private String distanceCovered;

private String stops;

private String driverName;

private String timeStamp;

private AreaAndCoordinateDTO source;

private AreaAndCoordinateDTO destination;

private AreaAndCoordinateDTO nextStop;

private AreaAndCoordinateDTO currentLocation;

}
