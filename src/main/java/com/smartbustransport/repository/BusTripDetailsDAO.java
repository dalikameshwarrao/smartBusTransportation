package com.smartbustransport.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.smartbustransport.entity.NotificationEntity;

public interface BusTripDetailsDAO extends JpaRepository<NotificationEntity, String> {

	NotificationEntity findByBusNo(String busNo);
}
