package com.smartbustransport.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import java.util.ArrayList;
import java.util.List;

@JsonInclude(Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class ActiveRouteDTO {
	
	private int totalCount;
	private List<RouteDTO> routeDTO = new ArrayList<RouteDTO>();
	public int getTotalCount() {
		return totalCount;
	}
	public void setTotalCount(int totalCount) {
		this.totalCount = totalCount;
	}
	public List<RouteDTO> getRouteDTO() {
		return routeDTO;
	}
	public void setRouteDTO(List<RouteDTO> routeDTO) {
		this.routeDTO = routeDTO;
	}


}
