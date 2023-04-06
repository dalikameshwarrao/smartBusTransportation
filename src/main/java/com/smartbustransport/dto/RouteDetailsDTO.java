package com.smartbustransport.dto;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;

//@JsonInclude(Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class RouteDetailsDTO {
	public String route;
    public String occupancy;
    public String videoUrl;
    public RouteStopsDTO routeStops;
}
