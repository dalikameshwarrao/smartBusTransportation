package com.smartbustransport.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@JsonInclude(Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class ActiveAndCompleteRouteDTO {
	
	private ActiveRouteDTO activeRouteDTO;
	private CompleteRouteDTO completeRouteDTO;


}
