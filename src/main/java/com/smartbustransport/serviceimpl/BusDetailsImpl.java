package com.smartbustransport.serviceimpl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.smartbustransport.dto.ActiveRouteDTO;
import com.smartbustransport.dto.AreaAndCoordinateDTO;
import com.smartbustransport.dto.BusesDTO;
import com.smartbustransport.dto.CoordinateDTO;
import com.smartbustransport.dto.RouteDTO;
import com.smartbustransport.entity.BusesEntity;
import com.smartbustransport.entity.IotDetails;
import com.smartbustransport.repository.BusDetailsRepository;
import com.smartbustransport.repository.DriverDetailsRepository;
import com.smartbustransport.repository.RouteDetailDAO;
import com.smartbustransport.services.BusService;

import lombok.extern.slf4j.Slf4j;


@Service
@Slf4j
public class BusDetailsImpl implements BusService{

	@Autowired
	private BusDetailsRepository busDetailsRepository;
	
	@Autowired
	private RouteDetailDAO routeDetailDAO;
	
	@Autowired
	NotificationEntityServiceImpl notificationEntityServiceImpl;

	@Override
	public List<BusesDTO> getAllBusesDetails() {
		
		List<BusesDTO> busesModelList = new ArrayList<BusesDTO>();
		List<BusesEntity>  buses  = busDetailsRepository.findAll();
		CoordinateDTO coordinateDTO=new CoordinateDTO();
		for (BusesEntity busesfromDb : buses) {
			BusesDTO busesModel = new BusesDTO();
			busesModel.setBusNo(busesfromDb.getBusNo());
			busesModel.setRoute(busesfromDb.getRouteName());
			if(busesfromDb.getBusStatus()!=null && busesfromDb.getBusStatus().equalsIgnoreCase("stopped") ) {
				busesModel.setBusIconType("Red");
			}else if(busesfromDb.getBusStatus()!=null && busesfromDb.getBusStatus().equalsIgnoreCase("idle")) {
				
				busesModel.setBusIconType("Amber");
			}else {
				
				busesModel.setBusIconType("green");
			}
			busesModel.setSpeed("");
			busesModel.setFuelConsumed("100 galons");
			busesModel.setDistanceCovered("130 Km");
			busesModel.setStops("10");
			busesModel.setDriverName("John Doe");
			busesModel.setTimeStamp("27-12-2022");
			
			//setting source Model 
			AreaAndCoordinateDTO source = new AreaAndCoordinateDTO();
			source.setArea("Florrencia, Costa Rica");
			coordinateDTO = getLattitudeAndLongitude(null, null);
			source.setCoordinate(coordinateDTO);
			busesModel.setSource(source);
			
			//setting DestinationModel Model 
			AreaAndCoordinateDTO destination = new AreaAndCoordinateDTO();
			destination.setArea("Jicarito, Costa Rica");
			coordinateDTO  = getLattitudeAndLongitude(null, null);
			destination.setCoordinate(coordinateDTO);
			busesModel.setDestination(destination);
			
			//setting CurrentLocationModel 
			AreaAndCoordinateDTO currentLocation = new AreaAndCoordinateDTO();
			currentLocation.setArea("Tabacon, Costa Rica");
			coordinateDTO = getLattitudeAndLongitude(null, null);
			currentLocation.setCoordinate(coordinateDTO);
			busesModel.setCurrentLocation(currentLocation);
			
			//setting NextStopModel 
			AreaAndCoordinateDTO nextStop = new AreaAndCoordinateDTO();
			nextStop.setArea("San Jose, Costa Rica");
			coordinateDTO =getLattitudeAndLongitude(null, null);
			nextStop.setCoordinate(coordinateDTO);
			busesModel.setNextStop(nextStop);
			
			//adding up to the List
			busesModelList.add(busesModel);
		}
		return busesModelList;
	}
	
	
	public CoordinateDTO getLattitudeAndLongitude(String stopName,String routeId) {
		CoordinateDTO coordinateDTO= new CoordinateDTO();
		List<Number[]> coordinate= routeDetailDAO.findlatitudeandlongitude(stopName,routeId);
		if(coordinate!=null && coordinate.size()>0) {
			for(Number[] number:coordinate) {
				coordinateDTO.setLatitude(Double.valueOf(number[0].toString()));
				coordinateDTO.setLongitude(Double.valueOf(number[1].toString()));
			}
		}
		return coordinateDTO;
		
	}
	
	@Override
	public List<RouteDTO> getAllBusesDetailsFromNotification() {
		ActiveRouteDTO activeRouteDTO = notificationEntityServiceImpl.getActiveRoutes("Y");
		List<RouteDTO> routeDTOList = activeRouteDTO.getRouteDTO();
		return routeDTOList;
	}


	@Override
	public List<IotDetails> getAllTrackerDetails() {
		// TODO Auto-generated method stub
		return null;
	}
	
	

}
