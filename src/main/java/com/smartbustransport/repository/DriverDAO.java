package com.smartbustransport.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.smartbustransport.entity.BusesEntity;
import com.smartbustransport.entity.DriverEntity;

public interface DriverDAO extends JpaRepository<DriverEntity, Long>{
	
	DriverEntity findBydriverName(String driverName);
}
