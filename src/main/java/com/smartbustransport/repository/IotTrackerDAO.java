package com.smartbustransport.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.smartbustransport.entity.BusesEntity;
import com.smartbustransport.entity.IotDetails;

public interface IotTrackerDAO extends JpaRepository<IotDetails, Long>{
	
}
