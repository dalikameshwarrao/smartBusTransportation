package com.smartbustransport.dto;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;

//@JsonInclude(Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class RouteStopsDTO {
	public String totalStops;
    public String stopsCovered;
    public List<StoppageDTO> stoppages;
}
