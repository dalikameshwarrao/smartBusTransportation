package com.smartbustransport.serviceimpl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.TreeSet;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.core.Predicate;
import org.springframework.stereotype.Service;

import com.smartbustransport.dto.BusInfoDTO;
import com.smartbustransport.dto.CoordinateDTO;
import com.smartbustransport.dto.DriverInfoDTO;
import com.smartbustransport.dto.PastViolationDTO;
import com.smartbustransport.dto.RouteDetailsDTO;
import com.smartbustransport.dto.RouteInfoDTO;
import com.smartbustransport.dto.RouteStopsDTO;
import com.smartbustransport.dto.StoppageDTO;
import com.smartbustransport.dto.TripDTO;
import com.smartbustransport.dto.TripDetailsDTO;
import com.smartbustransport.dto.TripIDetailsInfo;
import com.smartbustransport.entity.BusesEntity;
import com.smartbustransport.entity.DriverEntity;
import com.smartbustransport.entity.NotificationEntity;
import com.smartbustransport.entity.RouteDetailEntity;
import com.smartbustransport.entity.RouteEntity;
import com.smartbustransport.repository.BusDAO;
import com.smartbustransport.repository.DriverDAO;
import com.smartbustransport.repository.NotificationEntityDAO;
import com.smartbustransport.repository.RouteDetailDAO;
import com.smartbustransport.repository.RouteInfoDAO;
import com.smartbustransport.services.TripDetailsService;

@Service
public class TripDetalsServiceImpl implements TripDetailsService {

	Logger logger = LoggerFactory.getLogger(TripDetalsServiceImpl.class);
	@Autowired
	private NotificationEntityDAO notificationEntityDAO;

	@Autowired
	private BusDAO busDAO;

	@Autowired
	RouteInfoDAO routeInfoDAO;

	@Autowired
	RouteDetailDAO routeDetailDAO;

	@Autowired
	DriverDAO driverDAO;

	@Override
	public TripDetailsDTO getTripDetails(TripIDetailsInfo tripIDetailsInfo) {

		List<NotificationEntity> notificationEntity = new ArrayList<NotificationEntity>();
		BusInfoDTO busInfoDTO = new BusInfoDTO();
		DriverInfoDTO driverInfoDTO = new DriverInfoDTO();
		RouteInfoDTO routeInfoDTO = new RouteInfoDTO();
		TripDetailsDTO tripDetailsDTO = new TripDetailsDTO();
		List<TripDTO> listTrips = new ArrayList<TripDTO>();
		List<StoppageDTO> stoppageDTOList = new ArrayList<StoppageDTO>();
		List<NotificationEntity> tripList = new ArrayList<>();

		if (tripIDetailsInfo.getType().equalsIgnoreCase("buses")) {
			tripList = notificationEntityDAO.findByBusNo(tripIDetailsInfo.getValue());
		} else if (tripIDetailsInfo.getType().equalsIgnoreCase("drivers")) {
			tripList = notificationEntityDAO.findByDriveName(tripIDetailsInfo.getValue());
		} else if (tripIDetailsInfo.getType().equalsIgnoreCase("routes")) {
			tripList = notificationEntityDAO.findByRouteName(tripIDetailsInfo.getValue());
		}
		if (tripIDetailsInfo != null && tripIDetailsInfo.getValue() != null && tripIDetailsInfo.getType() != null) {
			notificationEntity = getNotificationEntityDetails(tripIDetailsInfo.getType(), tripIDetailsInfo.getValue());
			if (tripIDetailsInfo.getType().equalsIgnoreCase("buses")) {
				BusesEntity busDetails = busDAO.findByBusNo(tripIDetailsInfo.getValue());
				if (busDetails != null) {
					busInfoDTO.setBusNo(tripIDetailsInfo.getValue());
					busInfoDTO.setLicensePlateNo(busDetails.getBusLicenseNumber());
					busInfoDTO.setRegionDepo(busDetails.getRegionDepot());
					busInfoDTO.setTotalTrips(busDetails.getTotalTrips());
					busInfoDTO.setSafetyScore(busDetails.getSafetyScore());
					busInfoDTO.setTotalPassengers(busDetails.getTotalPassengers());
					busInfoDTO.setTotalMiles(busDetails.getTotalMiles());
					busInfoDTO.setAvgFuelEconomy(busDetails.getAvgFuelEconmoy());
					busInfoDTO.setNextMaintanance(busDetails.getNextmaintence());
				}
			}

			if (tripIDetailsInfo.getType().equalsIgnoreCase("drivers")) {
				DriverEntity driverDetails = driverDAO.findBydriverName(tripIDetailsInfo.getValue());
				if (driverDetails != null) {
					driverInfoDTO.setDrivingLicense(driverDetails.getDrivingLicense());
					driverInfoDTO.setRegionDepo(driverDetails.getRegionDepot());
					driverInfoDTO.setTotalTrips(driverDetails.getTotalTrips());
					driverInfoDTO.setDrivingScore(driverDetails.getDrivingScore());
					driverInfoDTO.setPassengersCarried(driverDetails.getPassengersCarried());
					driverInfoDTO.setTotalHoursDriven(driverDetails.getTotalHoursDriven());
					driverInfoDTO.setTotalViolations(driverDetails.getTotalViolations());
					Double totalmiles = tripList.stream().mapToDouble(f -> f.getDistanceCovered()).sum();
					driverInfoDTO.setTotalMiles(totalmiles);
				}
			}

			if (tripIDetailsInfo.getType().equalsIgnoreCase("routes")) {
				RouteEntity routeEntity = routeInfoDAO.findByRouteName(tripIDetailsInfo.getValue());
				
				Collection<NotificationEntity> tripUniqueList = tripList.stream()
				        .collect(Collectors.toMap(NotificationEntity::getTripId, Function.identity(),
				                (a1, a2) -> a1))
				        .values();
				
				long totalTrips= tripUniqueList.size();
				Double fuelUsage = tripList.stream().map(f -> f.getFuelConsumed()).collect(Collectors.summingDouble(Double::doubleValue));
				Double totalmiles = tripList.stream().mapToDouble(f -> f.getDistanceCovered()).sum();
				List<NotificationEntity> totalViolation = tripList.stream().filter(a -> a.getViolation() != null && !a.getViolation().contains("Delay") )
						.collect(Collectors.toList());
				int overAllOccupancy=  tripList.stream().filter(a -> a.getStopName().equals(a.getSource()))
					.mapToInt(a -> a.getOveralOccupancy()).sum();
				int stepInPassenger=  tripList.stream()
						.mapToInt(a -> a.getStepInPassenger()).sum();
				int passengerCarried = overAllOccupancy+stepInPassenger;
				List<String> totalBuses = notificationEntity.stream().distinct().map(s -> s.getBusNo())
						.collect(Collectors.toList());
				Double totalrevenue= tripList.stream().mapToDouble(a->a.getTicketSaleCost()).sum();
				if (routeEntity != null) {
					routeInfoDTO.setAvgRevenue(totalrevenue);
					 routeInfoDTO.setRegionDepot(routeEntity.getRegionDepot());
					routeInfoDTO.setTotalTrips(totalTrips);
					routeInfoDTO.setSafetyScore(routeEntity.getSafetyScore());
					routeInfoDTO.setFuelUsage(fuelUsage);
					routeInfoDTO.setTotalBuses(totalBuses.size());
					routeInfoDTO.setTotalPassengers(passengerCarried);
					routeInfoDTO.setTotalHours(calculateTotalHours( notificationEntity, tripList) );
					routeInfoDTO.setTotalViolations(totalViolation.size());
					routeInfoDTO.setTotalMiles(totalmiles);
				}
			}

			if (!notificationEntity.isEmpty()) {
				for (NotificationEntity entity : notificationEntity) {
					// pastViolationDTO = getPastViolation(entity);
					TripDTO tripDTO = new TripDTO();
					tripDTO.setTripNo(String.valueOf(entity.getTripId()));
					tripDTO.setSpeed(Double.valueOf(entity.getSpeed()));
					tripDTO.setFuelConsumed(Double.valueOf(entity.getFuelConsumed()));
					Double distanceCovered = tripList.stream().filter(a -> a.getTripId().equals(entity.getTripId()))
							.mapToDouble(f -> f.getDistanceCovered()).sum();
					if (entity.getIsCurrentLocation() != null && entity.getIsCurrentLocation().equalsIgnoreCase("Y")) {
						tripDTO.setStatus("Inprogress");
						tripDTO.setDistanceCovered(distanceCovered);
					} else {
						tripDTO.setStatus("Completed");
						tripDTO.setDistanceCovered(distanceCovered);
					}

					tripDTO.setDriver(entity.getDriveName());

					RouteEntity routeEntity = routeInfoDAO.findByRouteId(entity.getRouteId());
					List<RouteDetailEntity> routeDetailEntity = routeDetailDAO
							.findByRouteIdOrderByRouteNoAsc(entity.getRouteId());

					tripDTO.setDistance(String.valueOf(routeEntity.getTotalMiles()));
					// RouteStopsDTO code
					RouteStopsDTO routeStopsDTO = new RouteStopsDTO();
					routeStopsDTO.setTotalStops(String.valueOf(routeEntity.getTotalStop()));
					routeStopsDTO.setStopsCovered(String.valueOf(routeEntity.getTotalStop()));

					// StoppageDTO code
					if (routeDetailEntity.size() > 0) {
						StoppageDTO StoppageDTO = null;
						stoppageDTOList = new ArrayList<StoppageDTO>();
						CoordinateDTO coordinateDTO = new CoordinateDTO();
						for (RouteDetailEntity routeDet : routeDetailEntity) {
							NotificationEntity observation = tripList.stream()
									.filter(a -> a.getStopName().equals(routeDet.getStopName())
											&& a.getTripId().equals(entity.getTripId()))
									.findAny().orElse(null);

							if (routeDet.getRouteNo() == 1) {
								if (entity.getIsCurrentLocation() != null
										&& entity.getIsCurrentLocation().equals("Y")) {
									StoppageDTO = new StoppageDTO();
									StoppageDTO.setName("Current Location");
									StoppageDTO.setArea(entity.getStopName());
									coordinateDTO.setLatitude(Double.valueOf(entity.getLatitude()));
									coordinateDTO.setLongitude(Double.valueOf(entity.getLongitude()));
									StoppageDTO.setCoordinateDTO(coordinateDTO);
									StoppageDTO.setObservation(entity.getViolation());
									StoppageDTO.setTimeStamp(entity.getNotificationDate());
									stoppageDTOList.add(StoppageDTO);
									routeStopsDTO.setStoppages(stoppageDTOList);
								}
							}

							StoppageDTO = new StoppageDTO();
							if (entity.getSource().equals(routeDet.getStopName())) {
								StoppageDTO.setName("Source");
							} else if (entity.getDestination().equals(routeDet.getStopName())) {
								StoppageDTO.setName("Destination");
							} else {
								StoppageDTO.setName("Stop" + routeDet.getRouteNo());
							}

							StoppageDTO.setArea(routeDet.getStopName());
							coordinateDTO.setLatitude(Double.valueOf(routeDet.getLatitude()));
							coordinateDTO.setLongitude(Double.valueOf(routeDet.getLongitude()));
							StoppageDTO.setCoordinateDTO(coordinateDTO);
							StoppageDTO.setObservation(observation == null ? "" : observation.getViolation());
							StoppageDTO.setTimeStamp(observation == null ? null : observation.getNotificationDate());
							stoppageDTOList.add(StoppageDTO);
						}
						routeStopsDTO.setStoppages(stoppageDTOList);

					}

					// RouteDetailsDTO code
					RouteDetailsDTO routeDetailsDTO = new RouteDetailsDTO();
					routeDetailsDTO.setRoute(entity.getRouteName());
					routeDetailsDTO.setOccupancy(String.valueOf(entity.getOveralOccupancy()));
					routeDetailsDTO.setVideoUrl("http://busVideo");
					routeDetailsDTO.setRouteStops(routeStopsDTO);
					tripDTO.setRouteDetails(routeDetailsDTO);
					listTrips.add(tripDTO);
					if (tripIDetailsInfo.getType().equalsIgnoreCase("buses")) {
						busInfoDTO.setTrips(listTrips);
					} else if (tripIDetailsInfo.getType().equalsIgnoreCase("drivers")) {
						driverInfoDTO.setTrips(listTrips);
					} else if (tripIDetailsInfo.getType().equalsIgnoreCase("routes")) {
						routeInfoDTO.setTrips(listTrips);
					}

//					if (pastViolationDTO.getViolationType() != null) {
//						pastViolationDTOList.add(pastViolationDTO);
//					}
				}

			}

			List<PastViolationDTO> pastViolationList = getPastViolation(tripList);

			if (tripIDetailsInfo.getType().equalsIgnoreCase("buses")) {
				busInfoDTO.setPastViolations(pastViolationList);
				tripDetailsDTO.setBusInfo(busInfoDTO);
			} else if (tripIDetailsInfo.getType().equalsIgnoreCase("drivers")) {
				driverInfoDTO.setPastViolations(pastViolationList);
				tripDetailsDTO.setDriverInfo(driverInfoDTO);
			} else if (tripIDetailsInfo.getType().equalsIgnoreCase("routes")) {
				routeInfoDTO.setPastViolations(pastViolationList);
				tripDetailsDTO.setRouteInfo(routeInfoDTO);
			}

		}

		return tripDetailsDTO;
	}

	public List<PastViolationDTO> getPastViolation(List<NotificationEntity> tripList) {

		List<PastViolationDTO> pastViolationList = new ArrayList<>();
		List<NotificationEntity> violationList = tripList.stream().filter(a -> a.getViolation() != null)
				.collect(Collectors.toList());
		for (NotificationEntity violationObj : violationList) {
			if (!violationObj.getViolation().contains("Delay")) {
				PastViolationDTO pastViolationDTO = new PastViolationDTO();
				pastViolationDTO.setViolationType(violationObj.getViolation());
				pastViolationDTO.setBusNo(violationObj.getBusNo());
				pastViolationDTO.setRoute(violationObj.getRouteName());
				pastViolationDTO.setTimeStamp(violationObj.getNotificationDate());
				pastViolationList.add(pastViolationDTO);
			}

		}
		return pastViolationList;
	}

	public List<NotificationEntity> getNotificationEntityDetails(String tripIDetailsInfoType, String value) {
		List<NotificationEntity> notificationEntity = new ArrayList<NotificationEntity>();
		if (tripIDetailsInfoType.equalsIgnoreCase("buses")) {
			notificationEntity = notificationEntityDAO.getTripDetailsBuses(value);
		} else if (tripIDetailsInfoType.equalsIgnoreCase("drivers")) {
			notificationEntity = notificationEntityDAO.getTripDetailsDrivers(value);
		} else if (tripIDetailsInfoType.equalsIgnoreCase("routes")) {
			notificationEntity = notificationEntityDAO.getTripDetailsRoutes(value);
		}
		return notificationEntity;
	}

	public long calculateTotalHours(List<NotificationEntity> entities, List<NotificationEntity> tripList) {
		Long totalHours=(long) 0;
		for (NotificationEntity entity : entities) {
			Date StartDate = tripList.stream().filter(a -> a.getStopName().equals(entity.getSource()) && a.getTripId().equals(entity.getTripId()))
					.map(a -> a.getNotificationDate()).findAny().orElse(null);
			Date endDate = entity.getNotificationDate();
			if(StartDate!=null)
			{
				long difference_In_Time = endDate.getTime() - StartDate.getTime();
				long difference_In_Hours = (difference_In_Time / (1000 * 60 * 60)) % 24;
				totalHours+=difference_In_Hours;
			}
		}
		return totalHours;

	}
	
	
}