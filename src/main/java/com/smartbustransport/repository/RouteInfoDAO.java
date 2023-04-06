package com.smartbustransport.repository;


import org.springframework.data.jpa.repository.JpaRepository;

import com.smartbustransport.entity.RouteEntity;

public interface RouteInfoDAO extends JpaRepository<RouteEntity, String>{
	
	RouteEntity findByRouteId(String routeId);
	RouteEntity findByRouteName(String routeName);

}
