package com.smartbustransport.dto;

import java.util.ArrayList;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;
//@JsonInclude(Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class StoppageDTO {
	public String name;
    public String observation;
    public String area;
    public CoordinateDTO coordinateDTO;
    public Date timeStamp;
}
