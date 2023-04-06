package com.smartbustransport.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.smartbustransport.dto.CoordinateDTO;
import com.smartbustransport.entity.RouteDetailEntity;
import com.smartbustransport.entity.RouteEntity;

public interface RouteDetailDAO extends JpaRepository<RouteDetailEntity, String>{
	
    @Query(value = "select latitude,longitude from route_Detail  where stop_name=:stopName and route_id=:routeId", nativeQuery = true)
	List<Number[]> findlatitudeandlongitude(String stopName,String routeId);

	@Query(value = "select latitude,longitude,stop_name from route_Detail  where route_id=:routeId and route_no=:routeNo", nativeQuery = true)
	List<Object[]> findlatitudeandlongitudeByRouteNo(String routeId,int routeNo);
	
	List<RouteDetailEntity> findByRouteId(String routeId);
	
	List<RouteDetailEntity> findByRouteIdOrderByRouteNoAsc(String routeId);

}
