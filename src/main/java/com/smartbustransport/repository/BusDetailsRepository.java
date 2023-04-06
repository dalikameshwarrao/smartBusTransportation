package com.smartbustransport.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.smartbustransport.entity.BusesEntity;

@Repository
public interface BusDetailsRepository extends JpaRepository <BusesEntity, Long>{

	BusesEntity findByBusId(Long busId);

}
