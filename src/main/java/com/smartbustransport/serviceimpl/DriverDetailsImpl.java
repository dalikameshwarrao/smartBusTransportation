package com.smartbustransport.serviceimpl;
import java.sql.Driver;
import java.sql.Time;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.smartbustransport.dto.AnalyticsDTO;
import com.smartbustransport.dto.AnalyticsStatDTO;
import com.smartbustransport.dto.CoordinateDTO;
import com.smartbustransport.dto.DistanceOverallDrivenDTO;
import com.smartbustransport.dto.DistancesOverallDrivenDTO;
import com.smartbustransport.dto.DriveTimeOverallStatsDTO;
import com.smartbustransport.dto.DriveTimeStatsAnalyticsDataDTO;
import com.smartbustransport.dto.DriveTimeStatsDTO;
import com.smartbustransport.dto.DriverAnalyticDTO;
import com.smartbustransport.dto.DriverOverallAnalyticDTO;
import com.smartbustransport.dto.DriversDTO;
import com.smartbustransport.dto.GeneralStatsDTO;
import com.smartbustransport.dto.LabourCostStatsDTO;
import com.smartbustransport.dto.LabourOverallTripAnalyticsDTO;
import com.smartbustransport.dto.LabourOverallTripStatsDTO;
import com.smartbustransport.dto.LabourTripAnalyticsDTO;
import com.smartbustransport.dto.LabourTripStatsDTO;
import com.smartbustransport.dto.MetricAnalyticDTO;
import com.smartbustransport.dto.StatsDTO;
import com.smartbustransport.dto.StatsOverallDTO;
import com.smartbustransport.dto.TripAnalyticDTO;
import com.smartbustransport.dto.TripAnalyticsDTO;
import com.smartbustransport.dto.TripOverallAnalyticsDTO;
import com.smartbustransport.dto.TripStatsDTO;
import com.smartbustransport.dto.ViolationsStatDTO;
import com.smartbustransport.dto.ViolationsStatsDTO;
import com.smartbustransport.entity.DriverEntity;
import com.smartbustransport.entity.NotificationEntity;
import com.smartbustransport.repository.BusDetailsRepository;
import com.smartbustransport.repository.DriverDetailsRepository;
import com.smartbustransport.repository.NotificationEntityDAO;
import com.smartbustransport.repository.RouteDetailDAO;
import com.smartbustransport.services.DriverService;
import com.smartbustransport.util.CommonUtils;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class DriverDetailsImpl implements DriverService {

	@Autowired
	private DriverDetailsRepository driverDetailsRepository;
	
	@Autowired
	private BusDetailsRepository busDetailsRepository;
	
	@Autowired
	private RouteDetailDAO routeDetailDAO;
	
	@Autowired
	NotificationEntityServiceImpl notificationEntityServiceImpl;
	
	@Autowired
	NotificationEntityDAO notificationEntityDAO;

	@Autowired
	TripDetalsServiceImpl tripDetalsServiceImpl;

	@Override
	public List<DriversDTO> getAllDriversDetails() {

		List<DriversDTO> driversModelList = new ArrayList<DriversDTO>();

		List<DriverEntity> drivers = driverDetailsRepository.findAll();

		for (DriverEntity driversfromDb : drivers) {}

		return driversModelList;
	}

	@Override
	public void getDriversDetailsByBusNo(String busNo) {
		// TODO Auto-generated method stub
	}

	@Override
	public Driver getAllDrivers() {
		// TODO Auto-generated method stub
		return null;
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
	public List<DriversDTO> getAllDriversDetailsFromNotiFy() {
		return notificationEntityServiceImpl.getAllDriversDetailsFromNotiFy();
	}
	@Override
	public List<DriverAnalyticDTO> getDriverAnalytics() {
		List<DriverAnalyticDTO> driverAnalytics = new ArrayList<>();

		List<DriverEntity> drivers = driverDetailsRepository.findAll();

		if (drivers != null && drivers.size() > 0) {
  
			AnalyticsDTO analyticsDTO = null;
				 List<Date> dayDates= getStartDateEndDate("DAY");
				 List<Date> weeklyDates= getStartDateEndDate("WEEKLY");
				 List<Date> monthlyDates= getStartDateEndDate("MONTHLY");
				 List<Date> yearlyDates= getStartDateEndDate("YEARLY");
				 

			for (DriverEntity de : drivers) {

				
				 List<NotificationEntity> notificationListforHours=notificationEntityDAO.getNotificationsByDate(dayDates.get(0), dayDates.get(1),de.getDriverId());
				 List<NotificationEntity> notificationListforDay=notificationEntityDAO.getNotificationsByDate(weeklyDates.get(0), weeklyDates.get(1),de.getDriverId());
				 List<NotificationEntity> notificationListforMonthly=notificationEntityDAO.getNotificationsByDate(monthlyDates.get(0), monthlyDates.get(1),de.getDriverId());
				 List<NotificationEntity> notificationListforYearly=notificationEntityDAO.getNotificationsByDate(yearlyDates.get(0), yearlyDates.get(2),de.getDriverId());

				List<Number[]> notificationsHours = notificationEntityDAO
						.getDriverNotificationsByDay(dayDates.get(0), dayDates.get(1),de.getDriverId());
				List<Number[]> notificationsDays = notificationEntityDAO
						.getDriverNotificationsByHours(weeklyDates.get(0), weeklyDates.get(1),de.getDriverId());
				List<Number[]> notificationsMonthly = notificationEntityDAO
						.getDriverNotificationsByDay(monthlyDates.get(0), monthlyDates.get(1),de.getDriverId());
				List<Object[]> notificationsYearly = notificationEntityDAO
						.getDriverNotificationsByMonthly(yearlyDates.get(0), yearlyDates.get(1),de.getDriverId());
				
				DriverAnalyticDTO driverAnalyticDTO = new DriverAnalyticDTO();
				driverAnalyticDTO.setName(de.getDriverName());
				driverAnalyticDTO.setLicenseNo(de.getDrivingLicense());
				analyticsDTO = new AnalyticsDTO();


				GeneralStatsDTO day = getGeneralStats(notificationListforHours,"DAY",de.getDriverName());
				GeneralStatsDTO weekly = getGeneralStats(notificationListforDay,"WEEKLY",de.getDriverName());
				GeneralStatsDTO monthly = getGeneralStats(notificationListforMonthly,"MONTHLY",de.getDriverName());
				GeneralStatsDTO yearly = getGeneralStats(notificationListforYearly,"YEARLY",de.getDriverName());

				analyticsDTO.setDay(day);
				analyticsDTO.setMonthly(monthly);
				analyticsDTO.setWeekly(weekly);
				analyticsDTO.setYearly(yearly);
				driverAnalyticDTO.setGeneralStats(analyticsDTO);

				// tripStats
				   List<TripAnalyticDTO> dayAnalytics=toHourlyMetricData(notificationsHours, dayDates.get(0));
				   
				   int startDayId = CommonUtils.dateToNumber(weeklyDates.get(0));
                   int endDayId = CommonUtils.dateToNumber(weeklyDates.get(1));
				   List<TripAnalyticDTO> weeklyAnalytics=toWeeklyMetricData(notificationsDays, startDayId,endDayId);
				   
				   startDayId = CommonUtils.dateToNumber(monthlyDates.get(0));
				    endDayId = CommonUtils.dateToNumber(monthlyDates.get(1));
				   List<TripAnalyticDTO> monthlyAnalytics=toMonthlyMetricData(notificationsMonthly,startDayId,endDayId);
				   
				   List<TripAnalyticDTO> yearlyAnalytics=toYearlyCount(notificationsYearly, yearlyDates.get(1));
				   
				   TripStatsDTO tripStatsDTO= null;
				   TripAnalyticsDTO tripAnalyticsDTO = new TripAnalyticsDTO();
				   //day
				   tripStatsDTO=new TripStatsDTO();
				  tripStatsDTO.setAnalyticsData(dayAnalytics);
				   tripStatsDTO.setTrips(0);
				   tripStatsDTO.setPercentage(100);
				   tripStatsDTO.setObservation("UP");
				   tripAnalyticsDTO.setDay(tripStatsDTO);
				   
				   //weekly
				   tripStatsDTO=new TripStatsDTO();
				  tripStatsDTO.setAnalyticsData(weeklyAnalytics);
				   tripStatsDTO.setTrips(0);
				   tripStatsDTO.setPercentage(100);
				   tripStatsDTO.setObservation("UP");
				   tripAnalyticsDTO.setWeekly(tripStatsDTO);
				   
				   //montly
				   tripStatsDTO=new TripStatsDTO();
				  tripStatsDTO.setAnalyticsData(monthlyAnalytics);
				   tripStatsDTO.setTrips(0);
				   tripStatsDTO.setPercentage(100);
				   tripStatsDTO.setObservation("UP");
				   tripAnalyticsDTO.setMonthly(tripStatsDTO);
				   
				   //yearly
				   tripStatsDTO=new TripStatsDTO();
				  tripStatsDTO.setAnalyticsData(yearlyAnalytics);
				   tripStatsDTO.setTrips(0);
				   tripStatsDTO.setPercentage(100);
				   tripStatsDTO.setObservation("UP");
				   tripAnalyticsDTO.setYearly(tripStatsDTO);
				   driverAnalyticDTO.setTripStats(tripAnalyticsDTO);
                 
//				tripStatsDTO.setTrips(driverNotificationsYearList.size());
//
//				TripAnalyticsDTO tripAnalyticsDTO = new TripAnalyticsDTO();
//
//				List<TripAnalyticDTO> tripDay = getTripAnalytic(driverNotificationsDayList);
//				if (tripDay == null || tripDay.size() < 1) {
//					tripDay = new ArrayList<>();
//					TripAnalyticDTO tripAnalyticDTO = new TripAnalyticDTO();
//					tripAnalyticDTO.setCount(0);
//					tripAnalyticDTO.setNode(dateToString(dayStartDate));
//					tripDay.add(tripAnalyticDTO);
//				}
//				tripAnalyticsDTO.setDay(tripDay);
//
//				List<TripAnalyticDTO> tripWeek = getTripAnalytic(driverNotificationsWeekList);
//				if (tripWeek == null || tripDay.size() < 1) {
//					tripWeek = new ArrayList<>();
//					TripAnalyticDTO tripAnalyticDTO = new TripAnalyticDTO();
//					tripAnalyticDTO.setCount(0);
//					tripAnalyticDTO.setNode(dayStartDate.toString());
//					tripWeek.add(tripAnalyticDTO);
//				}
//				tripAnalyticsDTO.setWeekly(tripWeek);
//
//				List<TripAnalyticDTO> tripMonth = getTripAnalytic(driverNotificationsMonthList);
//				if (tripMonth == null || tripDay.size() < 1) {
//					tripMonth = new ArrayList<>();
//					TripAnalyticDTO tripAnalyticDTO = new TripAnalyticDTO();
//					tripAnalyticDTO.setCount(0);
//					tripAnalyticDTO.setNode(dayStartDate.toString());
//					tripMonth.add(tripAnalyticDTO);
//				}
//				tripAnalyticsDTO.setMonthly(tripMonth);
//
//				List<TripAnalyticDTO> tripYear = getTripAnalytic(driverNotificationsYearList);
//				if (tripYear == null || tripYear.size() < 1) {
//					tripYear = new ArrayList<>();
//					TripAnalyticDTO tripAnalyticDTO = new TripAnalyticDTO();
//					tripAnalyticDTO.setCount(0);
//					tripAnalyticDTO.setNode(dayStartDate.toString());
//					tripYear.add(tripAnalyticDTO);
//				}
//				tripAnalyticsDTO.setYearly(tripYear);
//
//				tripStatsDTO.setAnalytics(tripAnalyticsDTO);
//
				///driverAnalyticDTO.setTripStats(tripStatsDTO);

				// avgDistanceDrivenStats
				List<Number[]> distanceHours = notificationEntityDAO
						.getDriverDisCoveredByHours(dayDates.get(0), dayDates.get(1),de.getDriverId());
				List<Number[]> distanceDays = notificationEntityDAO
						.getDriverDisCoveredByDay(weeklyDates.get(0), weeklyDates.get(1),de.getDriverId());
				List<Number[]> distanceMonthly = notificationEntityDAO
						.getDriverDisCoveredByDay(monthlyDates.get(0), monthlyDates.get(1),de.getDriverId());
				List<Object[]> distanceYearly = notificationEntityDAO
						.getDriverDisCoveredByMonthly(yearlyDates.get(0), yearlyDates.get(1),de.getDriverId());
                
				
				// diststat
				   List<TripAnalyticDTO> daydistAnalytics=toHourlyMetricData(distanceHours, dayDates.get(0));
				   
				    startDayId = CommonUtils.dateToNumber(weeklyDates.get(0));
                   endDayId = CommonUtils.dateToNumber(weeklyDates.get(1));
				   List<TripAnalyticDTO> weeklydistAnalytics=toWeeklyMetricData(distanceDays, startDayId,endDayId);
				   
				   startDayId = CommonUtils.dateToNumber(monthlyDates.get(0));
				    endDayId = CommonUtils.dateToNumber(monthlyDates.get(1));
				   List<TripAnalyticDTO> monthlydistAnalytics=toMonthlyMetricData(distanceMonthly,startDayId,endDayId);
				   
				   List<TripAnalyticDTO> yearlydistAnalytics=toYearlyCount(distanceYearly, yearlyDates.get(1));
				   
				    tripAnalyticsDTO = new TripAnalyticsDTO();
				   //day
				   tripStatsDTO=new TripStatsDTO();
				  tripStatsDTO.setAnalyticsData(daydistAnalytics);
				   tripStatsDTO.setTrips(0);
				   tripStatsDTO.setPercentage(100);
				   tripStatsDTO.setObservation("UP");
				   tripAnalyticsDTO.setDay(tripStatsDTO);
				   
				   //weekly
				   tripStatsDTO=new TripStatsDTO();
				  tripStatsDTO.setAnalyticsData(weeklydistAnalytics);
				   tripStatsDTO.setTrips(0);
				   tripStatsDTO.setPercentage(100);
				   tripStatsDTO.setObservation("UP");
				   tripAnalyticsDTO.setWeekly(tripStatsDTO);
				   
				   //montly
				   tripStatsDTO=new TripStatsDTO();
				  tripStatsDTO.setAnalyticsData(monthlydistAnalytics);
				   tripStatsDTO.setTrips(0);
				   tripStatsDTO.setPercentage(100);
				   tripStatsDTO.setObservation("UP");
				   tripAnalyticsDTO.setMonthly(tripStatsDTO);
				   
				   //yearly
				   tripStatsDTO=new TripStatsDTO();
				  tripStatsDTO.setAnalyticsData(yearlydistAnalytics);
				   tripStatsDTO.setTrips(0);
				   tripStatsDTO.setPercentage(100);
				   tripStatsDTO.setObservation("UP");
				   tripAnalyticsDTO.setYearly(tripStatsDTO);
				   driverAnalyticDTO.setAvgDistanceDrivenStats(tripAnalyticsDTO);
				   

				// labourCostStats

				LabourCostStatsDTO labourCostStats = new LabourCostStatsDTO();

				
				List<Number[]> labCostHours = notificationEntityDAO
						.getDriverLabCostByHours(dayDates.get(0), dayDates.get(1),de.getDriverId());
				List<Number[]> labCostDays = notificationEntityDAO
						.getDriverLabCostByDay(weeklyDates.get(0), weeklyDates.get(1),de.getDriverId());
				List<Number[]> labCostMonthly = notificationEntityDAO
						.getDriverLabCostByDay(monthlyDates.get(0), monthlyDates.get(1),de.getDriverId());
				List<Object[]> labCostYearly = notificationEntityDAO
						.getDriverLabCostByMonthly(yearlyDates.get(0), yearlyDates.get(1),de.getDriverId());
                
				
				// diststat
				   List<TripAnalyticDTO> dayLabCostAnalytics=toHourlyMetricData(labCostHours, dayDates.get(0));
				   
				    startDayId = CommonUtils.dateToNumber(weeklyDates.get(0));
                   endDayId = CommonUtils.dateToNumber(weeklyDates.get(1));
				   List<TripAnalyticDTO> weeklyLabCostAnalytics=toWeeklyMetricData(labCostDays, startDayId,endDayId);
				   
				   startDayId = CommonUtils.dateToNumber(monthlyDates.get(0));
				    endDayId = CommonUtils.dateToNumber(monthlyDates.get(1));
				   List<TripAnalyticDTO> monthlyLabCostAnalytics=toMonthlyMetricData(labCostMonthly,startDayId,endDayId);
				   
				   List<TripAnalyticDTO> yearlyLabCostAnalytics=toYearlyCount(labCostYearly, yearlyDates.get(1));
				   
				   LabourTripAnalyticsDTO labourTripAnalyticsDTO = new LabourTripAnalyticsDTO();
				   //day
				    LabourTripStatsDTO labourTripStatsDTO=new LabourTripStatsDTO();
				    
				    labourTripStatsDTO.setAnalyticsData(dayLabCostAnalytics);
				    labourTripStatsDTO.setLabourCost(0);
				    labourTripStatsDTO.setPercentage(100);
				    labourTripStatsDTO.setObservation("UP");
				    labourTripAnalyticsDTO.setDay(labourTripStatsDTO);
				   
				   //weekly
				    labourTripStatsDTO=new LabourTripStatsDTO();
				    labourTripStatsDTO.setAnalyticsData(weeklyLabCostAnalytics);
				    labourTripStatsDTO.setLabourCost(0);
				    labourTripStatsDTO.setPercentage(100);
				    labourTripStatsDTO.setObservation("UP");
				    labourTripAnalyticsDTO.setWeekly(labourTripStatsDTO);
				   
				   //montly
				    labourTripStatsDTO=new LabourTripStatsDTO();
				    labourTripStatsDTO.setAnalyticsData(monthlyLabCostAnalytics);
				    labourTripStatsDTO.setLabourCost(0);
				    labourTripStatsDTO.setPercentage(100);
				    labourTripStatsDTO.setObservation("UP");
				    labourTripAnalyticsDTO.setMonthly(labourTripStatsDTO);
				   
				   //yearly
				   labourTripStatsDTO=new LabourTripStatsDTO();
				   labourTripStatsDTO.setAnalyticsData(yearlyLabCostAnalytics);
				   labourTripStatsDTO.setLabourCost(0);
				   labourTripStatsDTO.setPercentage(100);
				   labourTripStatsDTO.setObservation("UP");
				   labourTripAnalyticsDTO.setYearly(labourTripStatsDTO);
				   driverAnalyticDTO.setLabourCostStats(labourTripAnalyticsDTO);
				   
				   
				   labourCostStats.setAnalytics(tripAnalyticsDTO);
				 
				   

				// driveTimeStats
				   DriveTimeStatsDTO driveTimeStats = new DriveTimeStatsDTO();
				   
				   StatsDTO statsDTODay=getStatsDTO(notificationListforHours,daydistAnalytics);
				   driveTimeStats.setDay(statsDTODay);
				   
				   StatsDTO statsDTOWeek=getStatsDTO(notificationListforDay,weeklydistAnalytics);
				   driveTimeStats.setWeekly(statsDTOWeek);
				   
				   
				   StatsDTO statsDTOMonth=getStatsDTO(notificationListforDay,monthlydistAnalytics);
				   driveTimeStats.setMonthly(statsDTOMonth);
				   
				   StatsDTO statsDTOYear=getStatsDTO(notificationListforDay,yearlydistAnalytics);
				   driveTimeStats.setYearly(statsDTOYear);
				   
				   
				   
				   
				   
				   
				   driverAnalyticDTO.setDriveTimeStats(driveTimeStats);
				   
				

				// violationsStats
				ViolationsStatsDTO violationsStats = new ViolationsStatsDTO();

				ViolationsStatDTO dayViolationsStatDTO = getViolationsStats(notificationListforHours);
				

				violationsStats.setDay(dayViolationsStatDTO);

				ViolationsStatDTO weekViolationsStatDTO = getViolationsStats(notificationListforDay);

				violationsStats.setWeekly(weekViolationsStatDTO);

				ViolationsStatDTO monthViolationsStatDTO = getViolationsStats(notificationListforMonthly);

				violationsStats.setMonthly(monthViolationsStatDTO);

				ViolationsStatDTO yearViolationsStatDTO = getViolationsStats(notificationListforYearly);
				violationsStats.setYearly(yearViolationsStatDTO);

				driverAnalyticDTO.setViolationsStats(violationsStats);

				driverAnalytics.add(driverAnalyticDTO);
			}
		}

		return driverAnalytics;
	}

	public GeneralStatsDTO getGeneralStats(List<NotificationEntity> driverNotifications,String metricKey,String driverName) {
		GeneralStatsDTO generalStatsDTO = new GeneralStatsDTO();
		if (driverNotifications != null && driverNotifications.size() > 0) {
			
			int totalTrips = driverNotifications.size();
//			if(metricKey.equals("DAY")) {
//				totalTrips=totalTrips*
//			}
			
			List<NotificationEntity> tripList= notificationEntityDAO.findByDriveName(driverName);
			double totalDistanceDriven = tripList.stream().map(notifi -> notifi.getDistanceCovered())
					.reduce(0d, (a, b) -> a + b);
			double avgDistanceDriven = totalDistanceDriven / totalTrips;
			long totalDrivingHours= tripDetalsServiceImpl.calculateTotalHours(driverNotifications,tripList);
			long avgDriviingHours = totalDrivingHours / totalTrips;
			double totalLabourCost = tripList.stream().map(notifi -> notifi.getLabourCost()).reduce(0d,
					(a, b) -> a + b);
			double avgLabourCost = totalLabourCost / totalTrips;
			double totalDrivingScore = tripList.stream().map(notifi -> notifi.getDrivingScore()).reduce(0,
					(a, b) -> a + b);
			double avgDrivingScore = totalDrivingScore / totalTrips;
			
			System.out.println(totalTrips + "totalTrips" + driverNotifications + "driverNotificationsDayList");
			System.out.println(totalDistanceDriven + "totalDistanceDriven");
			System.out.println(avgDistanceDriven + "avgDistanceDriven");
			System.out.println("totalDrivingScore" + totalDrivingScore);

			
			generalStatsDTO.setTotalTrips(totalTrips);
			generalStatsDTO.setAvgDistanceDriven(avgDistanceDriven);
			generalStatsDTO.setAvgDrivingHours(avgDriviingHours);
			generalStatsDTO.setAvgLabourCost(avgLabourCost);
			generalStatsDTO.setAvgDrivingScore(avgDrivingScore);
			
//			long totalSec = 0;
//			for (NotificationEntity notifi : driverNotifications) {
//				String drivingHours = notifi.getDrivingHours().toString();
//				String[] times = drivingHours.split(":");
//				int hourstoSec = Integer.parseInt(times[0]) * 3600;
//				int minsToSec = Integer.parseInt(times[1]) * 60;
//				int sec = Integer.parseInt(times[2]);
//				totalSec += hourstoSec + minsToSec + sec;
//				System.out.println(notifi + "notifi" + "hourstoSec" + Integer.parseInt(times[0]) + "minsToSec"
//						+ Integer.parseInt(times[1]) + "sec" + Integer.parseInt(times[2]));
//
//			}
//			long avgSec = totalSec / totalTrips;
//			long hrs = avgSec / 3600;
//			long rem = (avgSec - hrs * 3600);
//			long min = rem / 60;
//			rem = rem - min * 60;
//			long secs = rem;
//			generalStatsDTO.setAvgDrivingHours(hrs + ":" + min + ":" + secs);
//			System.out.println(hrs + ":" + min + ":" + secs);
//
//			double totalLabourCost = driverNotifications.stream().map(notifi -> notifi.getLabourCost()).reduce(0d,
//					(a, b) -> a + b);
//			double avgLabourCost = totalLabourCost / totalTrips;
//			generalStatsDTO.setAvgLabourCost(avgLabourCost);
//
//			double totalDrivingScore = driverNotifications.stream().map(notifi -> notifi.getDrivingScore()).reduce(0,
//					(a, b) -> a + b);
//			System.out.println("totalDrivingScore" + totalDrivingScore);
//			double avgDrivingScore = totalDrivingScore / totalTrips;
//			generalStatsDTO.setAvgDrivingScore(avgDrivingScore);
//
//		} else {
//			generalStatsDTO.setAvgDrivingHours("");
//		}
		}
		return generalStatsDTO;

	}

	public List<TripAnalyticDTO> getTripAnalytic(List<NotificationEntity> driverNotificationsDayList) {
		List<TripAnalyticDTO> day = new ArrayList<>();
		if (driverNotificationsDayList != null && driverNotificationsDayList.size() > 0) {
			Map<String, Integer> m = new LinkedHashMap<>();
			for (NotificationEntity notificationEntity : driverNotificationsDayList) {
				String date = dateToString(notificationEntity.getNotificationDate());
				int count = 0;
				if (m.containsKey(date)) {
					count = m.get(date);
				}
				count++;
				m.put(date, count);
				System.out.println(m);
				for (Map.Entry<String, Integer> me : m.entrySet()) {
					TripAnalyticDTO tripAnalyticDTO = new TripAnalyticDTO();
					tripAnalyticDTO.setCount(me.getValue());
					tripAnalyticDTO.setNode(me.getKey());
					day.add(tripAnalyticDTO);
				}

			}

		}
		System.out.println(day.size() + "size");
		return day;
	}

	private String dateToString(Date d) {
		String result = "";
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			result = sdf.format(d);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	private List<Date> getStartDateEndDate(String key) {
		List<Date> dates = new ArrayList<>();
		if (key.equals("DAY")) {
			Date endDate = new Date();
			Calendar cal = Calendar.getInstance();
			cal.add(Calendar.DAY_OF_MONTH, -1);
			cal.set(Calendar.HOUR_OF_DAY, 23);
			cal.set(Calendar.MINUTE, 59);
			cal.set(Calendar.SECOND, 59);
			cal.set(Calendar.MILLISECOND, 999);
			endDate = cal.getTime();

			cal.set(Calendar.HOUR_OF_DAY, 0);
			cal.set(Calendar.MINUTE, 0);
			cal.set(Calendar.SECOND, 0);
			cal.set(Calendar.MILLISECOND, 0);
			Date startDate = cal.getTime();
			dates.add(startDate);
			dates.add(endDate);
		} else if (key.equals("WEEKLY")) {
			Date endDate = new Date();
			Calendar cal = Calendar.getInstance();
			cal.add(Calendar.DAY_OF_MONTH, -1);
			cal.set(Calendar.HOUR_OF_DAY, 23);
			cal.set(Calendar.MINUTE, 59);
			cal.set(Calendar.SECOND, 59);
			cal.set(Calendar.MILLISECOND, 999);
			endDate = cal.getTime();

			// cal.setTime(endDate);
			cal.add(Calendar.DAY_OF_MONTH, -6);
			cal.set(Calendar.HOUR_OF_DAY, 0);
			cal.set(Calendar.MINUTE, 0);
			cal.set(Calendar.SECOND, 0);
			cal.set(Calendar.MILLISECOND, 0);
			Date startDate = cal.getTime();
			dates.add(startDate);
			dates.add(endDate);
		} else if (key.equals("MONTHLY")) {
			Date endDate = new Date();
			Calendar cal = Calendar.getInstance();
			// cal.setTime(endDate);
			int endMonthId = CommonUtils.dateToNumber(endDate);
			cal.add(Calendar.MONTH, -1);
			cal.add(Calendar.DAY_OF_MONTH, 1);
			cal.set(Calendar.HOUR_OF_DAY, 0);
			cal.set(Calendar.MINUTE, 0);
			cal.set(Calendar.SECOND, 0);
			cal.set(Calendar.MILLISECOND, 0);
			Date startDate = cal.getTime();
			dates.add(startDate);
			dates.add(endDate);
		}
		 else if (key.equals("YEARLY")) {
			 Date endDate = new Date();
		        Calendar cal = Calendar.getInstance();
		        cal.setTime(endDate);
		        cal.add(Calendar.MONTH, 0);
		        cal.add(Calendar.DAY_OF_MONTH, 1);
		        cal.add(Calendar.YEAR, -1);
		        cal.set(Calendar.DAY_OF_MONTH, cal.getActualMinimum(Calendar.DAY_OF_MONTH));
		        cal.set(Calendar.HOUR_OF_DAY, 0);
		        cal.set(Calendar.MINUTE, 0);
		        cal.set(Calendar.SECOND, 0);
		        cal.set(Calendar.MILLISECOND, 0);
		        Date startDate = cal.getTime();
		        Calendar pieChartEndDate = Calendar.getInstance();
		        pieChartEndDate.add(Calendar.MONTH, -1);
		        pieChartEndDate.set(Calendar.DAY_OF_MONTH, cal.getMaximum(Calendar.DAY_OF_MONTH));
		        pieChartEndDate.set(Calendar.HOUR_OF_DAY, 23);
		        pieChartEndDate.set(Calendar.MINUTE, 59);
		        pieChartEndDate.set(Calendar.SECOND, 59);
		        pieChartEndDate.set(Calendar.MILLISECOND, 999);
		        Date endDateForPieChartData = pieChartEndDate.getTime();
				dates.add(startDate);
				dates.add(endDateForPieChartData);
				dates.add(endDate);
			}
		return dates;
	}
	  private List<TripAnalyticDTO> toHourlyMetricData(List<Number[]> response, Date startDate) {
	        Map<Integer, Double> hashMap = new HashMap<Integer, Double>();
	        for (Number[] result : response) {
	            hashMap.put(result[0].intValue(), result[1].doubleValue());
	        }
	        List<Integer> hourList = response.stream().map(obj -> obj[0].intValue()).collect(Collectors.toList());

	        List<TripAnalyticDTO> metricDTOList = new ArrayList<>();
	        Calendar uiCalendar = Calendar.getInstance();
	        Calendar hapMapFormatCalendar = Calendar.getInstance();
	        for (int i = 1; i <= 24; i++) {
	        	TripAnalyticDTO metricDto = new TripAnalyticDTO();
	            uiCalendar.setTime(startDate);
	            hapMapFormatCalendar.setTime(startDate);
	            uiCalendar.add(Calendar.HOUR, i);
	            hapMapFormatCalendar.add(Calendar.HOUR, i - 1);
	            uiCalendar.set(Calendar.MINUTE, 0);
	            hapMapFormatCalendar.set(Calendar.MINUTE, 0);
	            uiCalendar.set(Calendar.SECOND, 0);
	            hapMapFormatCalendar.set(Calendar.SECOND, 0);
	            Date present = uiCalendar.getTime();
	            String uiFormat = CommonUtils.sdfT.format(present);
	            Date databaseFormatFromHashMap = hapMapFormatCalendar.getTime();
	            metricDto.setNode(uiFormat);
	            if (hashMap.containsKey(databaseFormatFromHashMap.getHours())) {
	                metricDto.setCount(hashMap.get(databaseFormatFromHashMap.getHours()));
	            }

	            metricDTOList.add(metricDto);
	        }
	        return metricDTOList;
	    }

	    private List<TripAnalyticDTO> toWeeklyMetricData(List<Number[]> response, int startDay, int endDay) {
	        Map<Integer, Double> hashMap = new HashMap<Integer, Double>();
	        for (Number[] result : response) {
	            hashMap.put(result[0].intValue(), result[1].doubleValue());
	        }
	        List<TripAnalyticDTO> metricDTOLiist = new ArrayList<>();
	        Date date = new Date();
	        List<TripAnalyticDTO> metricDTOList = new ArrayList<>();
	        int temp = startDay;
	        while (temp <= endDay) {
	        	TripAnalyticDTO metricDto = new TripAnalyticDTO();
	            try {
	                SimpleDateFormat sdfD = new SimpleDateFormat("yyyyMMdd", Locale.US);
	                date = sdfD.parse(String.valueOf(temp));
	                date.setHours(10);


	                metricDto.setNode(CommonUtils.sdfT.format(date));
	                if (hashMap.get(date.getDate()) != null) {
	                   // metricDto.setCount(Math.ceil(hashMap.get(date.getDate())));
	                	 metricDto.setCount(hashMap.get(date.getDate()));
	                }
	                Calendar cal = Calendar.getInstance();
	                cal.setTime(date);
	                cal.add(Calendar.DAY_OF_MONTH, 1);
	                Date nextDate = cal.getTime();
	                temp = CommonUtils.dateToNumber(nextDate);
	                metricDTOLiist.add(metricDto);
	            } catch (Exception e) {
	                e.printStackTrace();
	            }
	        }
	        return metricDTOLiist;
	    }

	    private List<TripAnalyticDTO> toMonthlyMetricData(List<Number[]> response, int startDay, int endDay) {
	        Map<Integer, Double> hashMap = new HashMap<Integer, Double>();
	        for (Number[] result : response) {
	            hashMap.put(result[0].intValue(), result[1].doubleValue());
	        }
	        List<TripAnalyticDTO> metricDTOLiist = new ArrayList<>();
	        Date date = new Date();
	        List<TripAnalyticDTO> metricDTOList = new ArrayList<>();
	        int temp = startDay;
	        while (temp <= endDay) {
	        	TripAnalyticDTO metricDto = new TripAnalyticDTO();
	            try {
	                SimpleDateFormat sdfD = new SimpleDateFormat("yyyyMMdd", Locale.US);
	                date = sdfD.parse(String.valueOf(temp));
	                int day = date.getDate();
	                metricDto.setNode(CommonUtils.sdfT.format(date));
	                if (hashMap.get(day) != null) {
	                    metricDto.setCount(hashMap.get(day));
	                }
	                Calendar cal = Calendar.getInstance();
	                cal.setTime(date);
	                cal.add(Calendar.DAY_OF_MONTH, 1);
	                Date nextDate = cal.getTime();
	                temp = CommonUtils.dateToNumber(nextDate);
	                metricDTOLiist.add(metricDto);

	            } catch (Exception e) {
	                e.printStackTrace();
	            }
	        }
	        return metricDTOLiist;
	    }
	    
	    private List<TripAnalyticDTO> toYearlyCount(List<Object[]> alert, Date startDate) {

	        List<TripAnalyticDTO> metricDTOList = new ArrayList<>();
	        int count = 12;
	        Map<Integer, Double> hashMap = new HashMap<Integer, Double>();

	        for (Object[] entity : alert) {
	            Integer d = CommonUtils.dateToNumber((Date) entity[0]);
	            hashMap.put(d,
	                    Double.valueOf(String.valueOf(((Object[]) entity)[1])));
	        }

	        Calendar cal = Calendar.getInstance();
	        for (Integer i = 0; i < count; i++) {
	        	TripAnalyticDTO metricDto = new TripAnalyticDTO();
	            Date date = new Date();
	            cal.setTime(startDate);
	            cal.add(Calendar.MONTH, i);
	            Date time = null;
	            cal.add(Calendar.YEAR, -1);
	            time = cal.getTime();
	            int dateToMonthStartIntVal = CommonUtils.dateToMonthStart(time);
	            try {
	                SimpleDateFormat sdfD = new SimpleDateFormat("yyyyMMdd", Locale.US);
	                date = sdfD.parse(String.valueOf(dateToMonthStartIntVal));
	                cal.setTime(date);
	                cal.add(Calendar.HOUR_OF_DAY, 10);
	                date = cal.getTime();
	            } catch (Exception e) {
	                e.printStackTrace();
	            }
	            metricDto.setNode(CommonUtils.sdfT.format(date));
	            if (hashMap.get(dateToMonthStartIntVal) != null) {
	                metricDto.setCount(hashMap.get(dateToMonthStartIntVal));
	            }
	            metricDTOList.add(metricDto);
	        }

	        return metricDTOList;
	    }
	    
	    public ViolationsStatDTO getViolationsStats(List<NotificationEntity> notifications)
	    {
	    	ViolationsStatDTO violationsStatDTO=new ViolationsStatDTO();
	    	if(notifications!=null && notifications.size()>0)
	    	{
	    	
	    	
	    		List<NotificationEntity> notifsOver=notifications.stream().filter(e->e.getViolation()!=null && e.getViolation().trim().equalsIgnoreCase("Over Speeding")).collect(Collectors.toList());
	    		
	    		violationsStatDTO.setOverspeeding(notifsOver!=null?notifsOver.size():0);
	    		
	    		List<NotificationEntity> notifsHarsh=notifications.stream().filter(e->e.getViolation()!=null && e.getViolation().equalsIgnoreCase("Harsh Breaking")).collect(Collectors.toList());
	    		violationsStatDTO.setOverspeeding(notifsHarsh!=null?notifsHarsh.size():0);
	    		
	    		List<NotificationEntity> notifsSharp=notifications.stream().filter(e->e.getViolation()!=null && e.getViolation().equalsIgnoreCase("Sharp Corners")).collect(Collectors.toList());
	    		violationsStatDTO.setSharpCorner(notifsSharp!=null?notifsSharp.size():0);
	    		
	    		List<NotificationEntity> notifsHarshAc=notifications.stream().filter(e->e.getViolation()!=null && e.getViolation().equalsIgnoreCase("Harsh Acceleration")).collect(Collectors.toList());
	    		violationsStatDTO.setOverspeeding(notifsHarshAc!=null?notifsHarshAc.size():0);
	    		
	    		List<NotificationEntity> notifSharpTurns=notifications.stream().filter(e->e.getViolation()!=null && e.getViolation().equalsIgnoreCase("Sharp Turns")).collect(Collectors.toList());
	    		violationsStatDTO.setSharpTurns(notifSharpTurns!=null?notifSharpTurns.size():0);
	    		
	    		List<NotificationEntity> notifsHighFatiuge=notifications.stream().filter(e->e.getViolation()!=null && e.getViolation().equalsIgnoreCase("High Fatiuge")).collect(Collectors.toList());
	    		violationsStatDTO.setHighFatiuge(notifsHighFatiuge!=null?notifsHighFatiuge.size():0);
	    		
	    		
	    		violationsStatDTO.setOtherViolations(notifications.size()-violationsStatDTO.getOverspeeding()-violationsStatDTO.getDistractions()-violationsStatDTO.getHarshAccelaration()-violationsStatDTO.getHarshBraking()-violationsStatDTO.getHighFatiuge()-violationsStatDTO.getSharpCorner()-violationsStatDTO.getSharpTurns());
	    		
	    	}
	    	
	    	return violationsStatDTO;
	    }
	    public AnalyticsStatDTO getAnalyticsStatDTO()
	    {
	    	AnalyticsStatDTO analyticsStatDTO=new AnalyticsStatDTO();
	    	
	    	
	    	return analyticsStatDTO;
	    }

		@Override
		public DriverOverallAnalyticDTO getDriverOverallAnalytics() {
			
				  
				AnalyticsDTO analyticsDTO = null;
					 List<Date> dayDates= getStartDateEndDate("DAY");
					 List<Date> weeklyDates= getStartDateEndDate("WEEKLY");
					 List<Date> monthlyDates= getStartDateEndDate("MONTHLY");
					 List<Date> yearlyDates= getStartDateEndDate("YEARLY");
					 

				

					
					 List<NotificationEntity> notificationListforHours=notificationEntityDAO.getNotificationsByDateAll(dayDates.get(0), dayDates.get(1));
					 List<NotificationEntity> notificationListforDay=notificationEntityDAO.getNotificationsByDateAll(weeklyDates.get(0), weeklyDates.get(1));
					 List<NotificationEntity> notificationListforMonthly=notificationEntityDAO.getNotificationsByDateAll(monthlyDates.get(0), monthlyDates.get(1));
					 List<NotificationEntity> notificationListforYearly=notificationEntityDAO.getNotificationsByDateAll(yearlyDates.get(0), yearlyDates.get(2));

					List<Number[]> notificationsHours = notificationEntityDAO
							.getDriverNotificationsByDayAll(dayDates.get(0), dayDates.get(1));
					List<Number[]> notificationsDays = notificationEntityDAO
							.getDriverNotificationsByHoursAll(weeklyDates.get(0), weeklyDates.get(1));
					List<Number[]> notificationsMonthly = notificationEntityDAO
							.getDriverNotificationsByDayAll(monthlyDates.get(0), monthlyDates.get(1));
					List<Object[]> notificationsYearly = notificationEntityDAO
							.getDriverNotificationsByMonthlyAll(yearlyDates.get(0), yearlyDates.get(1));
					
					DriverOverallAnalyticDTO driverOverallAnalyticDTO = new DriverOverallAnalyticDTO();
				
					analyticsDTO = new AnalyticsDTO();


					GeneralStatsDTO day = getGeneralStats(notificationListforHours);
					GeneralStatsDTO weekly = getGeneralStats(notificationListforDay);
					GeneralStatsDTO monthly = getGeneralStats(notificationListforMonthly);
					GeneralStatsDTO yearly = getGeneralStats(notificationListforYearly);

					analyticsDTO.setDay(day);
					analyticsDTO.setMonthly(monthly);
					analyticsDTO.setWeekly(weekly);
					analyticsDTO.setYearly(yearly);
					driverOverallAnalyticDTO.setGeneralStats(analyticsDTO);

					// tripStats
					   List<TripAnalyticDTO> dayAnalytics=toHourlyMetricData(notificationsHours, dayDates.get(0));
					   
					   int startDayId = CommonUtils.dateToNumber(weeklyDates.get(0));
	                   int endDayId = CommonUtils.dateToNumber(weeklyDates.get(1));
					   List<TripAnalyticDTO> weeklyAnalytics=toWeeklyMetricData(notificationsDays, startDayId,endDayId);
					   
					   startDayId = CommonUtils.dateToNumber(monthlyDates.get(0));
					    endDayId = CommonUtils.dateToNumber(monthlyDates.get(1));
					   List<TripAnalyticDTO> monthlyAnalytics=toMonthlyMetricData(notificationsMonthly,startDayId,endDayId);
					   
					   List<TripAnalyticDTO> yearlyAnalytics=toYearlyCount(notificationsYearly, yearlyDates.get(1));
					   
					   TripOverallAnalyticsDTO tripStatsDTO= null;
					   TripOverallAnalyticsDTO tripAnalyticsDTO = new TripOverallAnalyticsDTO();
					   //day
						/*
						 * tripStatsDTO=new TripOverallAnalyticsDTO();
						 * tripStatsDTO.setAnalyticsData(dayAnalytics); tripStatsDTO.setTotalTrips(0);
						 * tripStatsDTO.setPercentage(100); tripStatsDTO.setObservation("UP");
						 * tripAnalyticsDTO.setDay(tripStatsDTO);
						 * 
						 * //weekly tripStatsDTO=new TripOverallAnalyticsDTO();
						 * tripStatsDTO.setAnalyticsData(weeklyAnalytics);
						 * tripStatsDTO.setTotalTrips(0); tripStatsDTO.setPercentage(100);
						 * tripStatsDTO.setObservation("UP"); tripAnalyticsDTO.setWeekly(tripStatsDTO);
						 * 
						 * //montly tripStatsDTO=new TripOverallAnalyticsDTO();
						 * tripStatsDTO.setAnalyticsData(monthlyAnalytics);
						 * tripStatsDTO.setTotalTrips(0); tripStatsDTO.setPercentage(100);
						 * tripStatsDTO.setObservation("UP"); tripAnalyticsDTO.setMonthly(tripStatsDTO);
						 * 
						 * //yearly tripStatsDTO=new TripOverallAnalyticsDTO();
						 * tripStatsDTO.setAnalyticsData(yearlyAnalytics);
						 * tripStatsDTO.setTotalTrips(0); tripStatsDTO.setPercentage(100);
						 * tripStatsDTO.setObservation("UP"); tripAnalyticsDTO.setYearly(tripStatsDTO);
						 * driverOverallAnalyticDTO.setTripStats(tripAnalyticsDTO);
						 * 
						 * 
						 * // avgDistanceDrivenStats List<Number[]> distanceHours =
						 * notificationEntityDAO .getDriverDisCoveredByHoursAll(dayDates.get(0),
						 * dayDates.get(1)); List<Number[]> distanceDays = notificationEntityDAO
						 * .getDriverDisCoveredByDayAll(weeklyDates.get(0), weeklyDates.get(1));
						 * List<Number[]> distanceMonthly = notificationEntityDAO
						 * .getDriverDisCoveredByDayAll(monthlyDates.get(0), monthlyDates.get(1));
						 * List<Object[]> distanceYearly = notificationEntityDAO
						 * .getDriverDisCoveredByMonthlyAll(yearlyDates.get(0), yearlyDates.get(1));
						 * 
						 * 
						 * // diststat List<TripAnalyticDTO>
						 * daydistAnalytics=toHourlyMetricData(distanceHours, dayDates.get(0));
						 * 
						 * startDayId = CommonUtils.dateToNumber(weeklyDates.get(0)); endDayId =
						 * CommonUtils.dateToNumber(weeklyDates.get(1)); List<TripAnalyticDTO>
						 * weeklydistAnalytics=toWeeklyMetricData(distanceDays, startDayId,endDayId);
						 * 
						 * startDayId = CommonUtils.dateToNumber(monthlyDates.get(0)); endDayId =
						 * CommonUtils.dateToNumber(monthlyDates.get(1)); List<TripAnalyticDTO>
						 * monthlydistAnalytics=toMonthlyMetricData(distanceMonthly,startDayId,endDayId)
						 * ;
						 * 
						 * List<TripAnalyticDTO> yearlydistAnalytics=toYearlyCount(distanceYearly,
						 * yearlyDates.get(1));
						 * 
						 * DistancesOverallDrivenDTO distancesOverallDrivenDTO = new
						 * DistancesOverallDrivenDTO(); //day DistanceOverallDrivenDTO
						 * distanceOverallDrivenDTO=new DistanceOverallDrivenDTO();
						 * distanceOverallDrivenDTO.setAnalyticsData(daydistAnalytics);
						 * distanceOverallDrivenDTO.setAvgDistanceDriven("");
						 * distanceOverallDrivenDTO.setPercentage("100");
						 * distanceOverallDrivenDTO.setObservation("UP");
						 * distancesOverallDrivenDTO.setDay(distanceOverallDrivenDTO);
						 * 
						 * //weekly
						 * 
						 * distanceOverallDrivenDTO=new DistanceOverallDrivenDTO();
						 * distanceOverallDrivenDTO.setAnalyticsData(weeklydistAnalytics);
						 * distanceOverallDrivenDTO.setAvgDistanceDriven("");
						 * distanceOverallDrivenDTO.setPercentage("100");
						 * distanceOverallDrivenDTO.setObservation("UP");
						 * distancesOverallDrivenDTO.setWeekly(distanceOverallDrivenDTO); //montly
						 * 
						 * distanceOverallDrivenDTO=new DistanceOverallDrivenDTO();
						 * distanceOverallDrivenDTO.setAnalyticsData(monthlydistAnalytics);
						 * distanceOverallDrivenDTO.setAvgDistanceDriven("");
						 * distanceOverallDrivenDTO.setPercentage("100");
						 * distanceOverallDrivenDTO.setObservation("UP");
						 * distancesOverallDrivenDTO.setMonthly(distanceOverallDrivenDTO);
						 * 
						 * //yearly
						 * 
						 * distanceOverallDrivenDTO=new DistanceOverallDrivenDTO();
						 * distanceOverallDrivenDTO.setAnalyticsData(yearlydistAnalytics);
						 * distanceOverallDrivenDTO.setAvgDistanceDriven("");
						 * distanceOverallDrivenDTO.setPercentage("100");
						 * distanceOverallDrivenDTO.setObservation("UP");
						 * distancesOverallDrivenDTO.setYearly(distanceOverallDrivenDTO);
						 * 
						 * driverOverallAnalyticDTO.setAvgDistanceDrivenStats(distancesOverallDrivenDTO)
						 * ;
						 * 
						 * 
						 * // labourCostStats
						 * 
						 * 
						 * 
						 * 
						 * List<Number[]> labCostHours = notificationEntityDAO
						 * .getDriverLabCostByHoursAll(dayDates.get(0), dayDates.get(1)); List<Number[]>
						 * labCostDays = notificationEntityDAO
						 * .getDriverLabCostByDayAll(weeklyDates.get(0), weeklyDates.get(1));
						 * List<Number[]> labCostMonthly = notificationEntityDAO
						 * .getDriverLabCostByDayAll(monthlyDates.get(0), monthlyDates.get(1));
						 * List<Object[]> labCostYearly = notificationEntityDAO
						 * .getDriverLabCostByMonthlyAll(yearlyDates.get(0), yearlyDates.get(1));
						 * 
						 * 
						 * // diststat List<TripAnalyticDTO>
						 * dayLabCostAnalytics=toHourlyMetricData(labCostHours, dayDates.get(0));
						 * 
						 * startDayId = CommonUtils.dateToNumber(weeklyDates.get(0)); endDayId =
						 * CommonUtils.dateToNumber(weeklyDates.get(1)); List<TripAnalyticDTO>
						 * weeklyLabCostAnalytics=toWeeklyMetricData(labCostDays, startDayId,endDayId);
						 * 
						 * startDayId = CommonUtils.dateToNumber(monthlyDates.get(0)); endDayId =
						 * CommonUtils.dateToNumber(monthlyDates.get(1)); List<TripAnalyticDTO>
						 * monthlyLabCostAnalytics=toMonthlyMetricData(labCostMonthly,startDayId,
						 * endDayId);
						 * 
						 * List<TripAnalyticDTO> yearlyLabCostAnalytics=toYearlyCount(labCostYearly,
						 * yearlyDates.get(1));
						 * 
						 * LabourOverallTripAnalyticsDTO labourTripAnalyticsDTO = new
						 * LabourOverallTripAnalyticsDTO(); //day LabourOverallTripStatsDTO
						 * labourTripStatsDTO=new LabourOverallTripStatsDTO();
						 * 
						 * labourTripStatsDTO.setAnalyticsData(dayLabCostAnalytics);
						 * labourTripStatsDTO.setAvgLabourCost(0);
						 * labourTripStatsDTO.setPercentage(100);
						 * labourTripStatsDTO.setObservation("UP");
						 * labourTripAnalyticsDTO.setDay(labourTripStatsDTO);
						 * 
						 * //weekly labourTripStatsDTO=new LabourOverallTripStatsDTO();
						 * labourTripStatsDTO.setAnalyticsData(weeklyLabCostAnalytics);
						 * labourTripStatsDTO.setAvgLabourCost(0);
						 * labourTripStatsDTO.setPercentage(100);
						 * labourTripStatsDTO.setObservation("UP");
						 * labourTripAnalyticsDTO.setWeekly(labourTripStatsDTO);
						 * 
						 * //montly labourTripStatsDTO=new LabourOverallTripStatsDTO();
						 * labourTripStatsDTO.setAnalyticsData(monthlyLabCostAnalytics);
						 * labourTripStatsDTO.setAvgLabourCost(0);
						 * labourTripStatsDTO.setPercentage(100);
						 * labourTripStatsDTO.setObservation("UP");
						 * labourTripAnalyticsDTO.setMonthly(labourTripStatsDTO);
						 * 
						 * //yearly labourTripStatsDTO=new LabourOverallTripStatsDTO();
						 * labourTripStatsDTO.setAnalyticsData(yearlyLabCostAnalytics);
						 * labourTripStatsDTO.setAvgLabourCost(0);
						 * labourTripStatsDTO.setPercentage(100);
						 * labourTripStatsDTO.setObservation("UP");
						 * labourTripAnalyticsDTO.setYearly(labourTripStatsDTO);
						 * driverOverallAnalyticDTO.setLabourCostStats(labourTripAnalyticsDTO);
						 * 
						 * // driveTimeStats DriveTimeOverallStatsDTO driveTimeStats = new
						 * DriveTimeOverallStatsDTO(); StatsOverallDTO
						 * statsDTODay=getStatsDTOOverall(notificationListforHours,daydistAnalytics);
						 * driveTimeStats.setDay(statsDTODay);
						 * 
						 * StatsOverallDTO
						 * statsDTOWeek=getStatsDTOOverall(notificationListforDay,weeklydistAnalytics);
						 * driveTimeStats.setWeekly(statsDTOWeek);
						 * 
						 * 
						 * StatsOverallDTO
						 * statsDTOMonth=getStatsDTOOverall(notificationListforDay,monthlydistAnalytics)
						 * ; driveTimeStats.setMonthly(statsDTOMonth);
						 * 
						 * StatsOverallDTO
						 * statsDTOYear=getStatsDTOOverall(notificationListforDay,yearlydistAnalytics);
						 * driveTimeStats.setYearly(statsDTOYear);
						 * driverOverallAnalyticDTO.setDriveTimeStats(driveTimeStats);
						 * 
						 * 
						 * 
						 * 
						 * // violationsStats ViolationsStatsDTO violationsStats = new
						 * ViolationsStatsDTO();
						 * 
						 * ViolationsStatDTO dayViolationsStatDTO =
						 * getViolationsStats(notificationListforHours);
						 * 
						 * 
						 * violationsStats.setDay(dayViolationsStatDTO);
						 * 
						 * ViolationsStatDTO weekViolationsStatDTO =
						 * getViolationsStats(notificationListforDay);
						 * 
						 * violationsStats.setWeekly(weekViolationsStatDTO);
						 * 
						 * ViolationsStatDTO monthViolationsStatDTO =
						 * getViolationsStats(notificationListforMonthly);
						 * 
						 * violationsStats.setMonthly(monthViolationsStatDTO);
						 * 
						 * ViolationsStatDTO yearViolationsStatDTO =
						 * getViolationsStats(notificationListforYearly);
						 * violationsStats.setYearly(yearViolationsStatDTO);
						 * 
						 * driverOverallAnalyticDTO.setViolationsStats(violationsStats);
						 */					
					
					return driverOverallAnalyticDTO;

					
							

		}
		public GeneralStatsDTO getGeneralStats(List<NotificationEntity> driverNotifications) {
			GeneralStatsDTO generalStatsDTO = new GeneralStatsDTO();
			
			/*
			 * if
			 * (driverNotifications != null && driverNotifications.size() > 0) {
			 * 
			 * int totalTrips = driverNotifications.size(); List<NotificationEntity>
			 * tripList= driverNotifications; double totalDistanceDriven =
			 * tripList.stream().map(notifi -> notifi.getDistanceCovered()) .reduce(0d, (a,
			 * b) -> a + b); double avgDistanceDriven = totalDistanceDriven / totalTrips;
			 * long totalDrivingHours=
			 * tripDetalsServiceImpl.calculateTotalHours(driverNotifications,tripList); long
			 * avgDriviingHours = totalDrivingHours / totalTrips; double totalLabourCost =
			 * tripList.stream().map(notifi -> notifi.getLabourCost()).reduce(0d, (a, b) ->
			 * a + b); double avgLabourCost = totalLabourCost / totalTrips; double
			 * totalDrivingScore = tripList.stream().map(notifi ->
			 * notifi.getDrivingScore()).reduce(0, (a, b) -> a + b); double avgDrivingScore
			 * = totalDrivingScore / totalTrips;
			 * 
			 * System.out.println(totalTrips + "totalTrips" + driverNotifications +
			 * "driverNotificationsDayList"); System.out.println(totalDistanceDriven +
			 * "totalDistanceDriven"); System.out.println(avgDistanceDriven +
			 * "avgDistanceDriven"); System.out.println("totalDrivingScore" +
			 * totalDrivingScore);
			 * 
			 * 
			 * generalStatsDTO.setTotalTrips(totalTrips);
			 * generalStatsDTO.setAvgDistanceDriven(avgDistanceDriven);
			 * generalStatsDTO.setAvgDrivingHours(avgDriviingHours);
			 * generalStatsDTO.setAvgLabourCost(avgLabourCost);
			 * generalStatsDTO.setAvgDrivingScore(avgDrivingScore);
			 * 
			 */
			return generalStatsDTO;

		}
		
		public StatsDTO getStatsDTO(List<NotificationEntity> notifications,List<TripAnalyticDTO> analyticsData)
		{
			StatsDTO statsDTO=new StatsDTO();
			if(notifications!=null)
			{
				int total=notifications.size();
				if(total==0)
				{
					total=1;
				}
				statsDTO.setDriveObservation("Up");
				statsDTO.setDrivePercentage("100%");
				statsDTO.setIdleObservation("Down");
				statsDTO.setIdlePercentage("100%");
				
				List<DriveTimeStatsAnalyticsDataDTO> driveTimeStatsAnalyticsDataDTORunning=new ArrayList<>();
				
				DriveTimeStatsAnalyticsDataDTO running=new DriveTimeStatsAnalyticsDataDTO();
				running.setMetricName("Running Hours");
				running.setAnalyticsData(analyticsData);
				
				/*
				 * driveTimeStatsAnalyticsDataDTORunning.add(running); // long
				 * idle=notifications.stream().mapToLong(n->n.getIdleTime().getTime()).sum();
				 * long avgIdle=idle/total; Time idleTime=new Time(idle);
				 * statsDTO.setIdleTime(idleTime+"Hrs"); long drivingTime=0;
				 * for(NotificationEntity notificationEntity:notifications) { LocalTime
				 * localTime=notificationEntity.getDrivingHours(); if(localTime!=null) {
				 * drivingTime+=Time.valueOf(localTime).getTime(); } } long
				 * avgDriving=drivingTime/total;
				 * 
				 * Time drTime=new Time(drivingTime); statsDTO.setDriveTime(drTime+"Hrs");
				 * 
				 * 
				 */
				DriveTimeStatsAnalyticsDataDTO driveTimeStatsAnalyticsDataDTOIdle=new DriveTimeStatsAnalyticsDataDTO();
				driveTimeStatsAnalyticsDataDTOIdle.setMetricName("Idle Hours");
				running.setAnalyticsData(analyticsData);
				driveTimeStatsAnalyticsDataDTORunning.add(driveTimeStatsAnalyticsDataDTOIdle);
				
				statsDTO.setAnalyticsData(driveTimeStatsAnalyticsDataDTORunning);
				
				
				
				
			}
			
			return statsDTO;
		}
		public StatsOverallDTO getStatsDTOOverall(List<NotificationEntity> notifications,List<TripAnalyticDTO> analyticsData)
		{
			StatsOverallDTO statsDTO=new StatsOverallDTO();
			if(notifications!=null)
			{
				/*
				 * int total=notifications.size(); if(total==0) { total=1; }
				 * statsDTO.setAvgDriveTime("Up"); statsDTO.setAvgDriveObservation("100%");
				 * statsDTO.setAvgIdleObservation("Down");
				 * statsDTO.setAvgDrivePercentage("100%");
				 * 
				 * List<DriveTimeStatsAnalyticsDataDTO>
				 * driveTimeStatsAnalyticsDataDTORunning=new ArrayList<>();
				 * 
				 * DriveTimeStatsAnalyticsDataDTO running=new DriveTimeStatsAnalyticsDataDTO();
				 * running.setMetricName("Running Hours");
				 * running.setAnalyticsData(analyticsData);
				 * 
				 * driveTimeStatsAnalyticsDataDTORunning.add(running); long
				 * idle=notifications.stream().mapToLong(n->n.getIdleTime().getTime()).sum();
				 * long avgIdle=idle/total; Time idleTime=new Time(avgIdle);
				 * statsDTO.setAvgIdleTime(idleTime+"Hrs"); long drivingTime=0;
				 * for(NotificationEntity notificationEntity:notifications) { LocalTime
				 * localTime=notificationEntity.getDrivingHours(); if(localTime!=null) {
				 * drivingTime+=Time.valueOf(localTime).getTime(); } } long
				 * avgDriving=drivingTime/total;
				 * 
				 * Time drTime=new Time(avgDriving); statsDTO.setAvgDriveTime(drTime+"Hrs");
				 * 
				 * 
				 */
				/*
				 * DriveTimeStatsAnalyticsDataDTO driveTimeStatsAnalyticsDataDTOIdle=new
				 * DriveTimeStatsAnalyticsDataDTO();
				 * driveTimeStatsAnalyticsDataDTOIdle.setMetricName("Idle Hours");
				 * running.setAnalyticsData(analyticsData);
				 * driveTimeStatsAnalyticsDataDTORunning.add(driveTimeStatsAnalyticsDataDTOIdle)
				 * ;
				 * 
				 * statsDTO.setAnalyticsData(driveTimeStatsAnalyticsDataDTORunning);
				 * 
				 */
				
				
			}
			
			return statsDTO;
		}


	   
	    
	   
	
	
}
