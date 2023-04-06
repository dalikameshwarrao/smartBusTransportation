package com.smartbustransport.dto;

import java.util.ArrayList;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;

//@JsonInclude(Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class PastViolationDTO {
	    public String violationType;
	    public String busNo;
	    public String route;
	    public Date timeStamp;
}
