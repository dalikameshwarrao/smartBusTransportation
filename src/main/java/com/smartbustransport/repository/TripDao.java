package com.smartbustransport.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.smartbustransport.entity.TripEntity;

public interface TripDao extends JpaRepository<TripEntity, Integer>{
	
	List<TripEntity> findByBusNo(String busNo);
}
