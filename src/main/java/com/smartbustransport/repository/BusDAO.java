package com.smartbustransport.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.smartbustransport.entity.BusesEntity;

public interface BusDAO extends JpaRepository<BusesEntity, Long>{
	
	BusesEntity findByBusNo(String busNo);
}
