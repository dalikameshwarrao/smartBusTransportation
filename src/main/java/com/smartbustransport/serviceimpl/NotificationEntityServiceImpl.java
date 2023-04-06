package com.smartbustransport.serviceimpl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.smartbustransport.dto.ActiveAndCompleteRouteDTO;
import com.smartbustransport.dto.ActiveRouteDTO;
import com.smartbustransport.dto.AlertDTO;
import com.smartbustransport.dto.AlertsDTO;
import com.smartbustransport.dto.AreaAndCoordinateDTO;
import com.smartbustransport.dto.CompleteRouteDTO;
import com.smartbustransport.dto.CoordinateDTO;
import com.smartbustransport.dto.DriversDTO;
import com.smartbustransport.dto.EventDTO;
import com.smartbustransport.dto.EventsDTO;
import com.smartbustransport.dto.IncidentDTO;
import com.smartbustransport.dto.IncidentsDTO;
import com.smartbustransport.dto.NotificationDTO;
import com.smartbustransport.dto.NotificationsDTO;
import com.smartbustransport.dto.RouteDTO;
import com.smartbustransport.entity.BusesEntity;
import com.smartbustransport.entity.DriverEntity;
import com.smartbustransport.entity.NotificationEntity;
import com.smartbustransport.entity.RouteEntity;
import com.smartbustransport.repository.BusDetailsRepository;
import com.smartbustransport.repository.DriverDetailsRepository;
import com.smartbustransport.repository.NotificationEntityDAO;
import com.smartbustransport.repository.RouteDetailDAO;
import com.smartbustransport.repository.RouteInfoDAO;
import com.smartbustransport.services.NotificationEntityService;

@Service
public class NotificationEntityServiceImpl  implements NotificationEntityService {
	
   @Autowired
	private NotificationEntityDAO notificationEntityDAO;
   
   @Autowired
  	private RouteDetailDAO routeDetailDAO;
   
   @Autowired
 	private RouteInfoDAO routeInfoDAO;
   
	@Autowired
	private BusDetailsRepository busDetailsRepository;
	
	@Autowired
	private DriverDetailsRepository driverDetailsRepository;
   
   public ActiveAndCompleteRouteDTO getActiveAndCompleteRoutes() {
	   ActiveAndCompleteRouteDTO activeAndCompleteRouteDTO= new ActiveAndCompleteRouteDTO();
	   activeAndCompleteRouteDTO.setActiveRouteDTO(getActiveRoutes("N"));
	   activeAndCompleteRouteDTO.setCompleteRouteDTO(getCompleteRoutes());
	return activeAndCompleteRouteDTO;
	   
   }

	public ActiveRouteDTO getActiveRoutes(String isCalledForBuses) {
		
		List<NotificationEntity> entityList = notificationEntityDAO.getActiveRoutes();
		ActiveRouteDTO actiRouteDTO= new ActiveRouteDTO();
		
		List<RouteDTO> routeDTOList= new ArrayList<>();
		actiRouteDTO.setTotalCount(entityList.size());
		CoordinateDTO coordinate;
		for(NotificationEntity entity : entityList) {
			RouteDTO routeDTO= new RouteDTO();
			routeDTO.setRouteName(entity.getRouteName());
			routeDTO.setTripId(entity.getTripId());
			  routeDTO.setBusNo(entity.getBusNo());
			  routeDTO.setFuelConsumed(entity.getFuelConsumed());
			  routeDTO.setDistanceCovered(entity.getDistanceCovered());
			  routeDTO.setDriverName(entity.getDriveName());
			  routeDTO.setDriverName(entity.getDriveName());
			  routeDTO.setTripTime(entity.getNotificationDate());
			  
			  //count of the bus
			 int countOfBus= notificationEntityDAO.getCountofBusesForRoute(entity.getBusId(),"Y");
			 routeDTO.setNoOfBuses(countOfBus);
			 // no of stop
			int comStopCount= notificationEntityDAO.getTripCompletedStopCount(entity.getTripId());
			RouteEntity routeEntity=routeInfoDAO.findByRouteId(entity.getRouteId());
			int totalStopCount=routeEntity.getTotalStop();
			routeDTO.setStops(+comStopCount+"/"+totalStopCount);
			  //source
			  AreaAndCoordinateDTO source= new  AreaAndCoordinateDTO();
			  coordinate=getLattitudeAndLongitude(entity.getSource(),entity.getRouteId());
			  source.setArea(entity.getSource());
			  source.setCoordinate(coordinate);
			  routeDTO.setSource(source);
			  
			  //destination
			  AreaAndCoordinateDTO destination= new  AreaAndCoordinateDTO();
			  coordinate=getLattitudeAndLongitude(entity.getDestination(),entity.getRouteId());
			  destination.setArea(entity.getDestination());
			  destination.setCoordinate(coordinate);
			  routeDTO.setDestination(destination); 
			  
			  //currentLocation
			  AreaAndCoordinateDTO current_location= new  AreaAndCoordinateDTO();
			  current_location.setArea(entity.getStopName());
			  coordinate= new CoordinateDTO();
			  coordinate.setLatitude(entity.getLatitude());
			  coordinate.setLongitude(entity.getLongitude());
			  current_location.setCoordinate(coordinate);
			  routeDTO.setCurrentLocation(current_location); 
			  
			  //nextStop
			  AreaAndCoordinateDTO nextStop=getNextStop(entity.getTripId(),entity.getRouteId());
			  if(isCalledForBuses.equalsIgnoreCase("Y")) {
				  routeDTO.setSpeed(entity.getSpeed());
				  BusesEntity busesEntity = busDetailsRepository.findByBusId(Long.valueOf(entity.getBusId()));
				  routeDTO.setBusIconType(isCalledForBuses);
				  if(busesEntity.getBusStatus()!=null && busesEntity.getBusStatus().equalsIgnoreCase("stopped") ) {
					  routeDTO.setBusIconType("Red");
					}else if(busesEntity.getBusStatus()!=null && busesEntity.getBusStatus().equalsIgnoreCase("idle")) {
						routeDTO.setBusIconType("Amber");
					}else {
						routeDTO.setBusIconType("green");
					}
			  }
			  routeDTO.setNextStop(nextStop); 
			  
			  routeDTOList.add(routeDTO);
			  
			 
		}
		actiRouteDTO.setRouteDTO(routeDTOList);
		return actiRouteDTO;
	}

	public CompleteRouteDTO getCompleteRoutes() {
		List<NotificationEntity> entityList = notificationEntityDAO.getCompletedRoutes();
		CompleteRouteDTO completeRouteDTO= new CompleteRouteDTO();
		List<RouteDTO> routeDTOList= new ArrayList<>();
		completeRouteDTO.setTotalCount(entityList.size());
		CoordinateDTO coordinate;
		for(NotificationEntity entity : entityList) {
			RouteDTO routeDTO= new RouteDTO();
			  routeDTO.setBusNo(entity.getBusNo());
			  routeDTO.setRouteName(entity.getRouteName());
			  routeDTO.setFuelConsumed(entity.getFuelConsumed());
			  routeDTO.setDistanceCovered(entity.getDistanceCovered());
			  routeDTO.setDriverName(entity.getDriveName());
			  routeDTO.setDriverName(entity.getDriveName());
			  routeDTO.setTripTime(entity.getNotificationDate());
			  
			  //count of the bus
			 int countOfBus= notificationEntityDAO.getCountofBusesForRoute(entity.getBusId().toString(),"N");
			 routeDTO.setNoOfBuses(countOfBus);
			 // no of stop
			int comStopCount= notificationEntityDAO.getTripCompletedStopCount(entity.getTripId());
			RouteEntity routeEntity=routeInfoDAO.findByRouteId(entity.getRouteId());
			int totalStopCount=routeEntity.getTotalStop();
			routeDTO.setStops(+comStopCount+"/"+totalStopCount);
			  //source
			  AreaAndCoordinateDTO source= new  AreaAndCoordinateDTO();
			  coordinate=getLattitudeAndLongitude(entity.getSource(),entity.getRouteId());
			  source.setArea(entity.getSource());
			  source.setCoordinate(coordinate);
			  routeDTO.setSource(source);
			  
			  //destination
			  AreaAndCoordinateDTO destination= new  AreaAndCoordinateDTO();
			  coordinate=getLattitudeAndLongitude(entity.getDestination(),entity.getRouteId());
			  destination.setArea(entity.getDestination());
			  destination.setCoordinate(coordinate);
			  routeDTO.setDestination(destination); 
			  
			  routeDTOList.add(routeDTO);
			  
			 
		}
		completeRouteDTO.setRouteDTO(routeDTOList);
		return completeRouteDTO;
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
	
	public AreaAndCoordinateDTO getNextStop(String tripId,String routeId) {
		AreaAndCoordinateDTO nextStop= new  AreaAndCoordinateDTO();
		CoordinateDTO coordinateDTO= new CoordinateDTO();
		int comStopCount = notificationEntityDAO.getTripCompletedStopCount(tripId);
		int nextStopCount=comStopCount+1;
		List<Object[]> coordinate= routeDetailDAO.findlatitudeandlongitudeByRouteNo(routeId,nextStopCount);
		if(coordinate!=null && coordinate.size()>0) {
			for(Object[] obj:coordinate) {
			coordinateDTO.setLatitude(Double.valueOf(obj[0].toString()));
			coordinateDTO.setLongitude(Double.valueOf(obj[1].toString()));
			nextStop.setArea(obj[2].toString());
			nextStop.setCoordinate(coordinateDTO);
			}
		}
		     
		return nextStop;
		
	}

	@Override
	public List<DriversDTO> getAllDriversDetailsFromNotiFy() {
		
		List<NotificationEntity> entityList = notificationEntityDAO.getActiveRoutes();
		
		List<DriversDTO> driverDTOList= new ArrayList<>();
		CoordinateDTO coordinate= new CoordinateDTO();
		for(NotificationEntity entity : entityList) {
			DriversDTO driversDTO= new DriversDTO();
			driversDTO.setTripId(entity.getTripId());
			driversDTO.setBusNo(entity.getBusNo());
			driversDTO.setRoute(entity.getRouteName());
			DriverEntity driverEntity = driverDetailsRepository.getByDriverId(entity.getDriveId());
			driversDTO.setDriverId(driverEntity.getDriverId());
			driversDTO.setLicenseNo(driverEntity.getDrivingLicense());
			BusesEntity busesEntity = busDetailsRepository.findByBusId(Long.valueOf(entity.getBusId()));
			driversDTO.setBusLicensePlateNo(busesEntity.getBusLicenseNumber());
			driversDTO.setDriver(entity.getDriveName());
			driversDTO.setTimeStamp(entity.getNotificationDate());
			  
			//source
			AreaAndCoordinateDTO source= new  AreaAndCoordinateDTO();
			 coordinate=getLattitudeAndLongitude(entity.getSource(),entity.getRouteId());
			  source.setArea(entity.getSource());
			  source.setCoordinate(coordinate);
			  driversDTO.setSource(source);
			  
			//currentLocation
			  AreaAndCoordinateDTO current_location= new  AreaAndCoordinateDTO();
			  current_location.setArea(entity.getStopName());
			  coordinate= new CoordinateDTO();
			  coordinate.setLatitude(entity.getLatitude());
			  coordinate.setLongitude(entity.getLongitude());
			  current_location.setCoordinate(coordinate);
			  driversDTO.setCurrentLocation(current_location); 
			  
			  //nextStop
			  AreaAndCoordinateDTO nextStop=getNextStop(entity.getTripId(),entity.getRouteId());
			  driversDTO.setNextStop(nextStop); 
			  
			  //destination
				AreaAndCoordinateDTO destination= new  AreaAndCoordinateDTO();
			  coordinate=getLattitudeAndLongitude(entity.getDestination(),entity.getRouteId());
			  destination.setArea(entity.getDestination());
			  destination.setCoordinate(coordinate);
			  driversDTO.setDestination(destination); 
			  driverDTOList.add(driversDTO);
			  
		}
		return driverDTOList;

	}

	@Override
	public NotificationsDTO getNotifications() {

		NotificationsDTO notifications = new NotificationsDTO();

		List<NotificationEntity> notificationsEventList = notificationEntityDAO.getNotifications("EVENTS");
		List<EventDTO> listEvent = new ArrayList<>();
		NotificationDTO notificationDTO = new NotificationDTO();
		EventsDTO eventsDTO = new EventsDTO();
		CoordinateDTO coordinateDTO = new CoordinateDTO();
		if (notificationsEventList != null && notificationsEventList.size() > 0) {
			eventsDTO.setTotalCount(notificationsEventList.size());
			for (NotificationEntity notificationEntity : notificationsEventList) {
				EventDTO eventDTO = new EventDTO();
				eventDTO.setTripId(notificationEntity.getTripId());
				eventDTO.setEvent(notificationEntity.getViolation());
				eventDTO.setBusNo(notificationEntity.getBusNo());
				eventDTO.setRoute(notificationEntity.getRouteName());
				AreaAndCoordinateDTO source = new AreaAndCoordinateDTO();
				coordinateDTO=getLattitudeAndLongitude(notificationEntity.getSource(),notificationEntity.getRouteId());
				source.setArea(notificationEntity.getSource());
				source.setCoordinate(coordinateDTO);
				eventDTO.setSource(source);

				AreaAndCoordinateDTO destination = new AreaAndCoordinateDTO();
				coordinateDTO=getLattitudeAndLongitude(notificationEntity.getDestination(),notificationEntity.getRouteId());
				destination.setArea(notificationEntity.getDestination());
				destination.setCoordinate(coordinateDTO);
				eventDTO.setDestination(destination);
				

				AreaAndCoordinateDTO currentLocation = new AreaAndCoordinateDTO();
				currentLocation.setArea(notificationEntity.getStopName());
				coordinateDTO.setLatitude(notificationEntity.getLatitude());
				coordinateDTO.setLongitude(notificationEntity.getLongitude());
				currentLocation.setCoordinate(coordinateDTO);

				eventDTO.setCurrentLocation(currentLocation);
				
				 AreaAndCoordinateDTO nextStop=getNextStop(notificationEntity.getTripId(),notificationEntity.getRouteId());
				 eventDTO.setNextStop(nextStop);

				eventDTO.setDriver(notificationEntity.getDriveName());
				eventDTO.setTimeStamp(notificationEntity.getNotificationDate() != null
						? notificationEntity.getNotificationDate().toString()
						: "");

				listEvent.add(eventDTO);

			}

		}
		eventsDTO.setEventsList(listEvent);
		notificationDTO.setEvents(eventsDTO);

		IncidentsDTO incidentsDTO = new IncidentsDTO();

		List<NotificationEntity> notificationsIncidentList = notificationEntityDAO.getNotifications("INCIDENT");
		List<IncidentDTO> listIncident = new ArrayList<>();
		if (notificationsIncidentList != null && notificationsIncidentList.size() > 0) {
			incidentsDTO.setTotalCount(notificationsIncidentList.size());

			for (NotificationEntity notificationEntity : notificationsIncidentList) {
				IncidentDTO incidentDTO = new IncidentDTO();
				incidentDTO.setTripId(notificationEntity.getTripId());
				incidentDTO.setIncident(notificationEntity.getViolation());
				incidentDTO.setBusNo(notificationEntity.getBusNo());
				incidentDTO.setRoute(notificationEntity.getRouteName());
				
				AreaAndCoordinateDTO source = new AreaAndCoordinateDTO();
				coordinateDTO=getLattitudeAndLongitude(notificationEntity.getSource(),notificationEntity.getRouteId());
				source.setArea(notificationEntity.getSource());
				source.setCoordinate(coordinateDTO);
				incidentDTO.setSource(source);

				AreaAndCoordinateDTO destination = new AreaAndCoordinateDTO();
				coordinateDTO=getLattitudeAndLongitude(notificationEntity.getDestination(),notificationEntity.getRouteId());
				destination.setArea(notificationEntity.getDestination());
				destination.setCoordinate(coordinateDTO);
				incidentDTO.setDestination(destination);
				

				AreaAndCoordinateDTO currentLocation = new AreaAndCoordinateDTO();
				currentLocation.setArea(notificationEntity.getStopName());
				coordinateDTO.setLatitude(notificationEntity.getLatitude());
				coordinateDTO.setLongitude(notificationEntity.getLongitude());
				currentLocation.setCoordinate(coordinateDTO);
				
				incidentDTO.setCurrentLocation(currentLocation);
				AreaAndCoordinateDTO nextStop=getNextStop(notificationEntity.getTripId(),notificationEntity.getRouteId());
				incidentDTO.setNextStop(nextStop);

				incidentDTO.setDriver(notificationEntity.getDriveName());
				incidentDTO.setLicenceNo("");
				incidentDTO.setTimeStamp(notificationEntity.getNotificationDate() != null
						? notificationEntity.getNotificationDate().toString()
						: "");
				listIncident.add(incidentDTO);

			}
		}
		incidentsDTO.setIncidentsList(listIncident);
		notificationDTO.setIncidents(incidentsDTO);
		
		
		AlertsDTO alertsDTO = new AlertsDTO();

		List<NotificationEntity> notificationsAlertList = notificationEntityDAO.getNotifications("ALERTS");
		List<AlertDTO> listOfAlert = new ArrayList<>();
		if (notificationsAlertList != null && notificationsAlertList.size() > 0) {
			incidentsDTO.setTotalCount(notificationsAlertList.size());

			for (NotificationEntity notificationEntity : notificationsAlertList) {
				AlertDTO alertDTO = new AlertDTO();
				alertDTO.setTripId(notificationEntity.getTripId());
				alertDTO.setAlert(notificationEntity.getViolation());
				alertDTO.setBusNo(notificationEntity.getBusNo());
				alertDTO.setRoute(notificationEntity.getRouteName());
				
				AreaAndCoordinateDTO source = new AreaAndCoordinateDTO();
				coordinateDTO=getLattitudeAndLongitude(notificationEntity.getSource(),notificationEntity.getRouteId());
				source.setArea(notificationEntity.getSource());
				source.setCoordinate(coordinateDTO);
				alertDTO.setSource(source);

				AreaAndCoordinateDTO destination = new AreaAndCoordinateDTO();
				coordinateDTO=getLattitudeAndLongitude(notificationEntity.getDestination(),notificationEntity.getRouteId());
				destination.setArea(notificationEntity.getDestination());
				destination.setCoordinate(coordinateDTO);
				alertDTO.setDestination(destination);
				

				AreaAndCoordinateDTO currentLocation = new AreaAndCoordinateDTO();
				currentLocation.setArea(notificationEntity.getStopName());
				coordinateDTO.setLatitude(notificationEntity.getLatitude());
				coordinateDTO.setLongitude(notificationEntity.getLongitude());
				currentLocation.setCoordinate(coordinateDTO);
				
				alertDTO.setCurrentLocation(currentLocation);
				AreaAndCoordinateDTO nextStop=getNextStop(notificationEntity.getTripId(),notificationEntity.getRouteId());
				alertDTO.setNextStop(nextStop);

				alertDTO.setDriver(notificationEntity.getDriveName());
				alertDTO.setLicenceNo("");
				alertDTO.setTimeStamp(notificationEntity.getNotificationDate() != null
						? notificationEntity.getNotificationDate().toString()
						: "");
				listOfAlert.add(alertDTO);

			}
		}
		alertsDTO.setAlertList(listOfAlert);
		notificationDTO.setAlerts(alertsDTO);
		
		notifications.setNotifications(notificationDTO);

		return notifications;
	}

}
