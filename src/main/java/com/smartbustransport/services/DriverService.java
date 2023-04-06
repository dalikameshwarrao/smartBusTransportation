package com.smartbustransport.services;

import java.sql.Driver;
import java.util.List;

import com.smartbustransport.dto.DriverAnalyticDTO;
import com.smartbustransport.dto.DriverOverallAnalyticDTO;
import com.smartbustransport.dto.DriversDTO;


public interface DriverService {
	
	public Driver getAllDrivers();

	public List<DriversDTO> getAllDriversDetails();

	public void getDriversDetailsByBusNo(String busNo);
	
	public List<DriversDTO> getAllDriversDetailsFromNotiFy();
	
	public List<DriverAnalyticDTO> getDriverAnalytics();
	
	public DriverOverallAnalyticDTO getDriverOverallAnalytics();

}
