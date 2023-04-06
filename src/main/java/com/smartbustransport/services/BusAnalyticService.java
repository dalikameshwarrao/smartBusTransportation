 	package com.smartbustransport.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.smartbustransport.dto.BusAnalytics;
import com.smartbustransport.dto.OverAllBusAnalytics;

@Service
public interface BusAnalyticService {

	public OverAllBusAnalytics getBusOverAllAnalytics();

	public List<BusAnalytics> getbusAnalytics();
}
