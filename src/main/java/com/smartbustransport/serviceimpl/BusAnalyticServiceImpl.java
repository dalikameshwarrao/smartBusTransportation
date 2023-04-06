package com.smartbustransport.serviceimpl;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.geo.Metric;
import org.springframework.stereotype.Service;

import com.smartbustransport.dto.Analytics;
import com.smartbustransport.dto.AnalyticsDTO;
import com.smartbustransport.dto.AnalyticsTime;
import com.smartbustransport.dto.BusAnalytics;
import com.smartbustransport.dto.Co2Analytics;
import com.smartbustransport.dto.Co2EmissionAnalytics;
import com.smartbustransport.dto.DistanceTravelledStats;
import com.smartbustransport.dto.ExpenseAnalytics;
import com.smartbustransport.dto.ExpensesStats;
import com.smartbustransport.dto.FuelConsumedStats;
import com.smartbustransport.dto.FuelGenericMetrics;
import com.smartbustransport.dto.GeneralStats;
import com.smartbustransport.dto.GenericMetrics;
import com.smartbustransport.dto.IncomeMetricName;
import com.smartbustransport.dto.IncomeStats;
import com.smartbustransport.dto.Metrics;
import com.smartbustransport.dto.OverAllBusAnalytics;
import com.smartbustransport.dto.Stats;
import com.smartbustransport.dto.TravelMetrics;
import com.smartbustransport.dto.TravelingMetricName;
import com.smartbustransport.dto.TravellingHoursStats;
import com.smartbustransport.dto.TripAnalyticDTO;
import com.smartbustransport.entity.BusesEntity;
import com.smartbustransport.entity.NotificationEntity;
import com.smartbustransport.repository.BusDAO;
import com.smartbustransport.repository.NotificationEntityDAO;
import com.smartbustransport.services.BusAnalyticService;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class BusAnalyticServiceImpl implements BusAnalyticService {

	@Autowired
	NotificationEntityDAO notificationEntityDAO;

	@Autowired
	BusDAO busDAO;
	
	private static final String CURRENT_YEAR = "Current Year";
	private static final String LAST_YEAR = "Last Year";
	private static final String IDLE_HOURS ="Idle Hours";
	private static final String RUNNING_HOURS="Running Hours";
	
	// LocalDate date = LocalDate.now(); // Get the current date
			// LocalDate monthly = date.minusDays(30); // Subtract 30 days from the current
			// date
			LocalDateTime now = LocalDateTime.now(); // Get the current date and time
			LocalDateTime lastDay = now.minusHours(24); // Subtract 24 hours from the current time
			LocalDateTime LastmonthDate = now.minusMonths(1);
			LocalDateTime LastWeekDate = now.minusWeeks(1);
			LocalDateTime LastYrDate = now.minusYears(1);
			

	@Override
	public List<BusAnalytics> getbusAnalytics() {
		
		
		List<BusAnalytics> busAnalyticsList = new ArrayList<BusAnalytics>();

		List<BusesEntity> busesEntityList = busDAO.findAll();
		
		List<NotificationEntity> entityList = new ArrayList<NotificationEntity>();
		
		
		for (BusesEntity busesEntity : busesEntityList) {
			
			BusAnalytics busAnalytics = new BusAnalytics();
			busAnalytics.setBusNO(busesEntity.getBusNo());
			busAnalytics.setBusLicensePlateNo(busesEntity.getBusLicenseNumber());
			entityList = notificationEntityDAO.getNotificationDetailsByBusNo(busesEntity.getBusNo());
			
		
			
			// get Last day general stats
			List<NotificationEntity> recordsLessThanDay = entityList.stream()
					.filter(record -> LocalDateTime
							.ofInstant(record.getNotificationDate().toInstant(), ZoneId.systemDefault()).isAfter(lastDay))
					.collect(Collectors.toList());
			
			
			// one week data
					List<NotificationEntity> recordsLessThanWeek = entityList.stream()
							// .filter(record -> now.isBefore(LastWeekDate))
							.filter(record -> LocalDateTime
									.ofInstant(record.getNotificationDate().toInstant(), ZoneId.systemDefault())
									.isAfter(LastWeekDate))
							.collect(Collectors.toList());
					
					
					// one Month Data
					List<NotificationEntity> recordsLessThanMoth = entityList.stream()
							.filter(record -> LocalDateTime
									.ofInstant(record.getNotificationDate().toInstant(), ZoneId.systemDefault())
									.isAfter(LastmonthDate))
							.collect(Collectors.toList());
					
					
					// 365 days from the current date
					List<NotificationEntity> recordsLessThanYr = entityList.stream()
							.filter(record -> LocalDateTime
									.ofInstant(record.getNotificationDate().toInstant(), ZoneId.systemDefault())
									.isAfter(LastYrDate))
							.collect(Collectors.toList());
					
					HashMap<String, Double> avgCalculatedValuesForDay =  getAvgCalculatedValues(recordsLessThanDay);
					HashMap<String, Double> avgCalculatedValuesForWeek =  getAvgCalculatedValues(recordsLessThanWeek);
					HashMap<String, Double> avgCalculatedValuesForMonth =  getAvgCalculatedValues(recordsLessThanMoth);
					HashMap<String, Double> avgCalculatedValuesForYr =  getAvgCalculatedValues(recordsLessThanYr);
					List<HashMap<String, Double>> avgCalculatedValuesMap = new ArrayList<HashMap<String, Double>>();
					avgCalculatedValuesMap.add(avgCalculatedValuesForDay);
					avgCalculatedValuesMap.add(avgCalculatedValuesForWeek);
					avgCalculatedValuesMap.add(avgCalculatedValuesForMonth);
					avgCalculatedValuesMap.add(avgCalculatedValuesForYr);
								
				if(entityList!=null && entityList.size()>0) {
				GeneralStats generalStats  = getGeneralStats(recordsLessThanDay,recordsLessThanWeek,recordsLessThanMoth,recordsLessThanYr);
				IncomeStats IncomeStats  = getIncomeStats(busesEntity.getBusNo(),avgCalculatedValuesMap);
				TravellingHoursStats travellingHoursStats = getTravellingHoursStats(busesEntity.getBusNo(),avgCalculatedValuesMap);
				DistanceTravelledStats distanceTravelledStats = getDistanceTravelledStats(busesEntity.getBusNo(),avgCalculatedValuesMap);
				FuelConsumedStats fuelConsumedStats = getFuelConsumedStats(busesEntity.getBusNo(),avgCalculatedValuesMap);
				Co2EmissionAnalytics co2EmissionAnalytics = getCo2EmissionAnalyticsStats(busesEntity.getBusNo(),avgCalculatedValuesMap);
				ExpensesStats expensesStats = getExpensesStats(busesEntity.getBusNo(),avgCalculatedValuesMap);
				busAnalytics.setGeneralStats(generalStats);
				busAnalytics.setIncomeStats(IncomeStats);
				busAnalytics.setTravellingHoursStats(travellingHoursStats);
				busAnalytics.setDistanceTravelledStats(distanceTravelledStats);
				busAnalytics.setFuelConsumedStats(fuelConsumedStats);
				busAnalytics.setCo2EmissionAnalytics(co2EmissionAnalytics);
				busAnalytics.setExpensesStats(expensesStats);
				busAnalyticsList.add(busAnalytics);
			}
		}
		
		return busAnalyticsList;
	}

	private IncomeStats getIncomeStats(String busNo, List<HashMap<String, Double>> avgCalculatedValuesMap) {
		//System.out.println(now+"   "+ now.minusDays(1));
		List<Object[]> dayAnalystList = notificationEntityDAO.getDriverNotificationsByDay(now.minusDays(1), now, busNo);
		List<Object[]> dayAnalystListLastYr = notificationEntityDAO.getDriverNotificationsByDay(now.minusYears(1).minusDays(1),now.minusYears(1), busNo);
		List<Object[]> weekAnalystList = notificationEntityDAO.getDriverNotificationsByWeek(now.minusWeeks(1), now, busNo);
		List<Object[]> weekAnalystListLastYr = notificationEntityDAO.getDriverNotificationsByWeek(now.minusYears(1).minusWeeks(1), now.minusYears(1), busNo);
		List<Object[]> monthyAnalystList = notificationEntityDAO.getDriverNotificationsByMonth(now.minusMonths(1), now, busNo);
		List<Object[]> monthyAnalystListLastYr = notificationEntityDAO.getDriverNotificationsByMonth(now.minusYears(1).minusMonths(1), now.minusYears(1), busNo);
		List<Object[]> yearAnalystList = notificationEntityDAO.getDriverNotificationsByYear(now.minusYears(1), now , busNo);
		List<Object[]> yearAnalystListLastYr = notificationEntityDAO.getDriverNotificationsByYear(now.minusYears(2), now.minusYears(1), busNo);
		IncomeStats IncomeStats = new IncomeStats();
		IncomeStats.setDay(getMetrics(dayAnalystList,dayAnalystListLastYr,avgCalculatedValuesMap.get(0)));
		IncomeStats.setWeekly(getMetrics(weekAnalystList,weekAnalystListLastYr,avgCalculatedValuesMap.get(1)));
		IncomeStats.setMonthly(getMetrics(monthyAnalystList,monthyAnalystListLastYr,avgCalculatedValuesMap.get(2)));
		IncomeStats.setYearly(getMetrics(yearAnalystList,yearAnalystListLastYr,avgCalculatedValuesMap.get(3)));
		return IncomeStats;
	}
	
	
	private TravellingHoursStats getTravellingHoursStats(String busNo, List<HashMap<String, Double>> avgCalculatedValuesMap) {
		//System.out.println(now+"   "+ now.minusDays(1));
		List<Object[]> dayAnalystList = notificationEntityDAO.getBusNotificationsTravelByDay(now.minusDays(1), now, busNo);
		List<Object[]> weekAnalystList = notificationEntityDAO.getBusNotificationsTravelByWeek(now.minusWeeks(1), now, busNo);
		List<Object[]> monthyAnalystList = notificationEntityDAO.getBusNotificationsTravelByMonth(now.minusMonths(1), now, busNo);
		List<Object[]> yearAnalystList = notificationEntityDAO.getBusNotificationsByTravelYear(now.minusYears(1), now , busNo);
		TravellingHoursStats travellingHoursStats = new TravellingHoursStats();
		travellingHoursStats.setDay(getTravelMetrics(dayAnalystList,avgCalculatedValuesMap.get(0)));
		travellingHoursStats.setWeekly(getTravelMetrics(weekAnalystList,avgCalculatedValuesMap.get(1)));
		travellingHoursStats.setMonthly(getTravelMetrics(monthyAnalystList,avgCalculatedValuesMap.get(2)));
		travellingHoursStats.setYearly(getTravelMetrics(yearAnalystList,avgCalculatedValuesMap.get(3)));
		return travellingHoursStats;
	}
	
	
	
	private TravelMetrics getTravelMetrics(List<Object[]> analystList, HashMap<String, Double> avgCalculatedValuesMap) {
		TravelMetrics metrics = new TravelMetrics();
		metrics.setIdleHrsPercentage("1.5%");
		metrics.setRunningHrsPercentage("50 %");
		metrics.setRunningHrsObservation("up");
		metrics.setIdleHrsObservation("idle");
		List<TravelingMetricName> travelingMetricNameList = new ArrayList<TravelingMetricName>();
		
		Set<Map.Entry<String, Double>> entries = avgCalculatedValuesMap.entrySet();
		for (Map.Entry<String, Double> entry : entries) {
		    String key = entry.getKey();
		    Double value = entry.getValue();
		    if(key!=null && key.equalsIgnoreCase("avgRunningHours")) {
		    	metrics.setAvgRunningHours(value.toString());
		    }
		    if(key!=null && key.equalsIgnoreCase("avgIdleHours")) {
		    	metrics.setAvgIdleHours(value.toString());
		    }
		}
		
		TravelingMetricName travelingMetricNameRunning = new TravelingMetricName();
		List<AnalyticsTime> analyticsListYr = new ArrayList<AnalyticsTime>();
		if(analystList!=null && analystList.size()>0) {
			travelingMetricNameRunning.setMetricName(RUNNING_HOURS);
			for (Object[] numbers : analystList) {
				AnalyticsTime analytics = new AnalyticsTime();
				analytics.setNode(numbers[0].toString());
				analytics.setCount(numbers[2].toString());
				analyticsListYr.add(analytics);
			}
			travelingMetricNameRunning.setAnalytics(analyticsListYr);
			travelingMetricNameList.add(travelingMetricNameRunning);
		}
		
		
		List<AnalyticsTime> analyticsListIdle = new ArrayList<AnalyticsTime>();
		if(analystList!=null && analystList.size()>0) {
			TravelingMetricName travelingMetricNameIdle = new TravelingMetricName();
			travelingMetricNameIdle.setMetricName(IDLE_HOURS);
			for (Object[] numbers : analystList) {
				AnalyticsTime analytics = new AnalyticsTime();
				analytics.setNode(numbers[0].toString());
				analytics.setCount(numbers[2].toString());
				analyticsListIdle.add(analytics);
			}
			travelingMetricNameIdle.setAnalytics(analyticsListIdle);
			travelingMetricNameList.add(travelingMetricNameIdle);
		}
		
		metrics.setTravelingMetricNameList(travelingMetricNameList);
		return metrics;
	}
	
	
	
	
	
	
	
	
	

	
	private DistanceTravelledStats getDistanceTravelledStats(String busNo, List<HashMap<String, Double>> avgCalculatedValuesMap) {
		List<Analytics> analyticsList = new ArrayList<Analytics>();
		//System.out.println(now+"   "+ now.minusDays(1));
		List<Object[]> dayAnalystList = notificationEntityDAO.getDriverNotificationsByDistCovDay(now.minusDays(1), now, busNo);
		List<Object[]> weekAnalystList = notificationEntityDAO.getDriverNotificationsByDistCovWeek(now.minusWeeks(1), now, busNo);
		List<Object[]> monthyAnalystList = notificationEntityDAO.getDriverNotificationsByDistCovMonth(now.minusMonths(1), now, busNo);
		List<Object[]> yearAnalystList = notificationEntityDAO.getDriverNotificationsByDistCovYear(now.minusYears(1), now , busNo);
		DistanceTravelledStats distanceTravelledStats = new DistanceTravelledStats();
		distanceTravelledStats.setDay(getMetricsForDistanceCovered(dayAnalystList,avgCalculatedValuesMap.get(0)));
		distanceTravelledStats.setWeekly(getMetricsForDistanceCovered(weekAnalystList,avgCalculatedValuesMap.get(1)));
		distanceTravelledStats.setMonthly(getMetricsForDistanceCovered(monthyAnalystList,avgCalculatedValuesMap.get(2)));
		distanceTravelledStats.setYearly(getMetricsForDistanceCovered(yearAnalystList,avgCalculatedValuesMap.get(3)));
		return distanceTravelledStats;
	}
	
	
	private Co2EmissionAnalytics getCo2EmissionAnalyticsStats(String busNo, List<HashMap<String, Double>> avgCalculatedValuesMap) {
		List<Analytics> analyticsList = new ArrayList<Analytics>();
		//System.out.println(now+"   "+ now.minusDays(1));
		List<Object[]> dayAnalystList = notificationEntityDAO.getDriverNotificationsByCo2Day(now.minusDays(1), now, busNo);
		List<Object[]> weekAnalystList = notificationEntityDAO.getDriverNotificationsByCo2Week(now.minusWeeks(1), now, busNo);
		List<Object[]> monthyAnalystList = notificationEntityDAO.getDriverNotificationsByCo2Month(now.minusMonths(1), now, busNo);
		List<Object[]> yearAnalystList = notificationEntityDAO.getDriverNotificationsByCo2Year(now.minusYears(1), now , busNo);
		Co2EmissionAnalytics co2EmissionAnalytics = new Co2EmissionAnalytics();
		co2EmissionAnalytics.setDay(getMetricsCo2Emission(dayAnalystList,avgCalculatedValuesMap.get(0)));
		co2EmissionAnalytics.setWeekly(getMetricsCo2Emission(weekAnalystList,avgCalculatedValuesMap.get(1)));
		co2EmissionAnalytics.setMonthly(getMetricsCo2Emission(monthyAnalystList,avgCalculatedValuesMap.get(2)));
		co2EmissionAnalytics.setYearly(getMetricsCo2Emission(yearAnalystList,avgCalculatedValuesMap.get(3)));
		return co2EmissionAnalytics;
	}
	
	
	private ExpensesStats getExpensesStats(String busNo, List<HashMap<String, Double>> avgCalculatedValuesMap) {
		//System.out.println(now+"   "+ now.minusDays(1));
		List<Object[]> dayAnalystList = notificationEntityDAO.getDriverNotificationsByExpenseDay(now.minusDays(1), now, busNo);
		List<Object[]> weekAnalystList = notificationEntityDAO.getDriverNotificationsByExpenseWeek(now.minusWeeks(1), now, busNo);
		List<Object[]> monthyAnalystList = notificationEntityDAO.getDriverNotificationsByExpenseMonth(now.minusMonths(1), now, busNo);
		List<Object[]> yearAnalystList = notificationEntityDAO.getDriverNotificationsByExpenseYear(now.minusYears(1), now , busNo);
		ExpensesStats expensesStats = new ExpensesStats();
		expensesStats.setObservation("up");
		expensesStats.setPercentage("100 %");
		expensesStats.setDay(getExpenseAnalytics(dayAnalystList,avgCalculatedValuesMap.get(0)));
		expensesStats.setWeekly(getExpenseAnalytics(weekAnalystList,avgCalculatedValuesMap.get(1)));
		expensesStats.setMonthly(getExpenseAnalytics(monthyAnalystList,avgCalculatedValuesMap.get(2)));
		expensesStats.setYearly(getExpenseAnalytics(yearAnalystList,avgCalculatedValuesMap.get(3)));
		return expensesStats;
	}
	
	private ExpenseAnalytics getExpenseAnalytics(List<Object[]> analystList,HashMap<String, Double> avgCalculatedValuesMap) {
		ExpenseAnalytics metrics = new ExpenseAnalytics();
		if(analystList!=null && analystList.size()>0) {
			for (Object[] numbers : analystList) {
				metrics.setFuel(numbers[1].toString());
				metrics.setMaintenance(numbers[1].toString());
				metrics.setOthers(" ");
			}
		}
		return metrics;
	}

	
	
	
	private FuelConsumedStats getFuelConsumedStats(String busNo, List<HashMap<String, Double>> avgCalculatedValuesMap) {
		List<Analytics> analyticsList = new ArrayList<Analytics>();
		//System.out.println(now+"   "+ now.minusDays(1));
		List<Object[]> dayAnalystList = notificationEntityDAO.getDriverNotificationsByFuelDay(now.minusDays(1), now, busNo);
		List<Object[]> weekAnalystList = notificationEntityDAO.getDriverNotificationsByFuelWeek(now.minusWeeks(1), now, busNo);
		List<Object[]> monthyAnalystList = notificationEntityDAO.getDriverNotificationsByFuelMonth(now.minusMonths(1), now, busNo);
		List<Object[]> yearAnalystList = notificationEntityDAO.getDriverNotificationsByFuelYear(now.minusYears(1), now , busNo);
		FuelConsumedStats fuelConsumedStats = new FuelConsumedStats();
		fuelConsumedStats.setDay(getMetricsForFuelCovered(dayAnalystList,avgCalculatedValuesMap.get(0)));
		fuelConsumedStats.setWeekly(getMetricsForFuelCovered(weekAnalystList,avgCalculatedValuesMap.get(1)));
		fuelConsumedStats.setMonthly(getMetricsForFuelCovered(monthyAnalystList,avgCalculatedValuesMap.get(2)));
		fuelConsumedStats.setYearly(getMetricsForFuelCovered(yearAnalystList,avgCalculatedValuesMap.get(3)));
		return fuelConsumedStats;
	}
	
	
	
		
	
	private GenericMetrics getMetricsForDistanceCovered(List<Object[]> analystList,HashMap<String, Double> avgCalculatedValuesMap) {
		GenericMetrics metrics = new GenericMetrics();
		metrics.setPercentage("1.5%");
		metrics.setObservation("UP");
		Set<Map.Entry<String, Double>> entries = avgCalculatedValuesMap.entrySet();
		for (Map.Entry<String, Double> entry : entries) {
		    String key = entry.getKey();
		    Double value = entry.getValue();
		    if(key!=null && key.equalsIgnoreCase("avgDistanceTravelled")) {
		    	metrics.setAvgDistanceTravelled(value.toString());
		    }
		}
		
		List<Analytics> analyticsList = new ArrayList<Analytics>();
		if(analystList!=null && analystList.size()>0) {
			for (Object[] numbers : analystList) {
				Analytics analytics = new Analytics();
				analytics.setNode(numbers[0].toString());
				analytics.setCount(Double.parseDouble(numbers[1].toString()));
				analyticsList.add(analytics);
			}
			metrics.setAnalytics(analyticsList);
		}
				
		return metrics;
	}
	
	
	private Metrics getMetrics(List<Object[]> analystList, List<Object[]> analystListLastYr,HashMap<String, Double> avgCalculatedValuesMap) {
		Metrics metrics = new Metrics();
		metrics.setPercentage("1.5%");
		metrics.setObservation("UP");
		List<IncomeMetricName> IncomeMetricList = new ArrayList<IncomeMetricName>();
		
		Set<Map.Entry<String, Double>> entries = avgCalculatedValuesMap.entrySet();
		for (Map.Entry<String, Double> entry : entries) {
		    String key = entry.getKey();
		    Double value = entry.getValue();
		    if(key!=null && key.equalsIgnoreCase("avgDailyIncome")) {
		    	metrics.setAvgIncome(value.toString());
		    }
		}
		
		IncomeMetricName incomeMetricNameCurrentYear = new IncomeMetricName();
		List<Analytics> analyticsListYr = new ArrayList<Analytics>();
		if(analystList!=null && analystList.size()>0) {
			incomeMetricNameCurrentYear.setMetricName(CURRENT_YEAR);
			for (Object[] numbers : analystList) {
				Analytics analytics = new Analytics();
				analytics.setNode(numbers[0].toString());
				analytics.setCount(Double.parseDouble(numbers[1].toString()));
				analyticsListYr.add(analytics);
			}
			incomeMetricNameCurrentYear.setAnalytics(analyticsListYr);
			IncomeMetricList.add(incomeMetricNameCurrentYear);
		}
		
		
		List<Analytics> analyticsListPrevYr = new ArrayList<Analytics>();
		if(analystListLastYr!=null && analystListLastYr.size()>0) {
			IncomeMetricName incomeMetricNamePrevYr = new IncomeMetricName();
			incomeMetricNamePrevYr.setMetricName(LAST_YEAR);
			for (Object[] numbers : analystListLastYr) {
				Analytics analytics = new Analytics();
				analytics.setNode(numbers[0].toString());
				analytics.setCount(Double.parseDouble(numbers[1].toString()));
				analyticsListPrevYr.add(analytics);
			}
			incomeMetricNamePrevYr.setAnalytics(analyticsListPrevYr);
			IncomeMetricList.add(incomeMetricNamePrevYr);
		}
		
		metrics.setAnalyticsData(IncomeMetricList);
		return metrics;
	}
	
	
	


	
	private Co2Analytics getMetricsCo2Emission(List<Object[]> analystList,HashMap<String, Double> avgCalculatedValuesMap) {
		Co2Analytics metrics = new Co2Analytics();
		metrics.setPercentage("1.5%");
		metrics.setObservation("UP");
		Set<Map.Entry<String, Double>> entries = avgCalculatedValuesMap.entrySet();
		for (Map.Entry<String, Double> entry : entries) {
		    String key = entry.getKey();
		    Double value = entry.getValue();
		    if(key!=null && key.equalsIgnoreCase("avgCo2Emission")) {
		    	metrics.setEmission(value.toString());
		    }
		}
		
		List<Analytics> analyticsList = new ArrayList<Analytics>();
		if(analystList!=null && analystList.size()>0) {
			for (Object[] numbers : analystList) {
				Analytics analytics = new Analytics();
				analytics.setNode(numbers[0].toString());
				analytics.setCount(Double.parseDouble(numbers[1].toString()));
				analyticsList.add(analytics);
			}
			metrics.setAnalytics(analyticsList);
		}
				
		return metrics;
	}

	
	private FuelGenericMetrics getMetricsForFuelCovered(List<Object[]> analystList,HashMap<String, Double> avgCalculatedValuesMap) {
		FuelGenericMetrics metrics = new FuelGenericMetrics();
		metrics.setPercentage("1.5%");
		metrics.setObservation("UP");
		Set<Map.Entry<String, Double>> entries = avgCalculatedValuesMap.entrySet();
		for (Map.Entry<String, Double> entry : entries) {
		    String key = entry.getKey();
		    Double value = entry.getValue();
		    if(key!=null && key.equalsIgnoreCase("avgFuelConsumed")) {
		    	metrics.setAvgFuelConsumed(value.toString());
		    }
		}
		
		List<Analytics> analyticsList = new ArrayList<Analytics>();
		if(analystList!=null && analystList.size()>0) {
			for (Object[] numbers : analystList) {
				Analytics analytics = new Analytics();
				analytics.setNode(numbers[0].toString());
				analytics.setCount(Double.parseDouble(numbers[1].toString()));
				analyticsList.add(analytics);
			}
			metrics.setAnalytics(analyticsList);
		}
				
		return metrics;
	}



	private GeneralStats getGeneralStats(List<NotificationEntity> recordsLessThanDay,List<NotificationEntity> recordsLessThanWeek,List<NotificationEntity> recordsLessThan30DaysOld,List<NotificationEntity> recordsLessThanYr) {
		GeneralStats generalStats = new GeneralStats();
		if (recordsLessThanDay != null && recordsLessThanDay.size() > 0) {
			Optional<Double> sumFuelConsumed = recordsLessThanDay.stream().map(x -> x.getFuelConsumed())
					.reduce(Double::sum);
			Optional<Double> sumCo2Emission = recordsLessThanDay.stream().map(x -> x.getCo2Emission())
					.reduce(Double::sum);
			Optional<Integer> sumOveralOccupancy = recordsLessThanDay.stream().map(x -> x.getOveralOccupancy())
					.reduce(Integer::sum);
			Optional<Double> sumRevenue = recordsLessThanDay.stream().map(x -> x.getTicketSaleCost())
					.reduce(Double::sum);
			
			
			sumFuelConsumed= Optional.of(sumFuelConsumed.get()/recordsLessThanDay.size());
			sumCo2Emission= Optional.of(sumCo2Emission.get()/recordsLessThanDay.size());
			sumOveralOccupancy= Optional.of(sumOveralOccupancy.get()/recordsLessThanDay.size());
			
			Double hours = 0.0;
			Double Minutes = 0.0;
			Double seconds = 0.0;
			for (NotificationEntity notificationEntity : recordsLessThanDay) {
				hours = hours + notificationEntity.getDrivingHours().getHour();
				Minutes = Minutes + notificationEntity.getDrivingHours().getMinute();
				seconds = seconds + notificationEntity.getDrivingHours().getSecond();
			}
			hours = hours + (Minutes / 60) + (seconds / 3600);
			Double sumAvgRunningHoursDay = hours / recordsLessThanDay.size();
			Stats lastDayStats = new Stats();
			lastDayStats.setAvgFuelConsumed(sumFuelConsumed.get() != null ? sumFuelConsumed.get().toString() : null);
			lastDayStats.setRevenue(sumRevenue.get() != null ? sumRevenue.get().toString() : null);
			lastDayStats.setAvgCO2Emission(sumCo2Emission.get() != null ? sumCo2Emission.get().toString() : null);
			lastDayStats.setAvgPassengerCarried(
					sumOveralOccupancy.get() != null ? sumOveralOccupancy.get().toString() : null);
			lastDayStats.setAvgRunningHours(sumAvgRunningHoursDay != null ? sumAvgRunningHoursDay.toString() : null);
			System.out.println(sumFuelConsumed);
			generalStats.setDay(lastDayStats);
		}

		
		if (recordsLessThanWeek != null && recordsLessThanWeek.size() > 0) {
			Optional<Double> sumFuelConsumedWeek = recordsLessThanWeek.stream().map(x -> x.getFuelConsumed())
					.reduce(Double::sum);
			Optional<Double> sumCo2EmissionWeek = recordsLessThanWeek.stream().map(x -> x.getCo2Emission())
					.reduce(Double::sum);
			Optional<Integer> sumOveralOccupancyWeek = recordsLessThanWeek.stream().map(x -> x.getOveralOccupancy())
					.reduce(Integer::sum);
			
			Optional<Double> sumRevenue = recordsLessThanWeek.stream().map(x -> x.getTicketSaleCost())
					.reduce(Double::sum);
			
			
			sumFuelConsumedWeek= Optional.of(sumFuelConsumedWeek.get()/recordsLessThanWeek.size());
			sumCo2EmissionWeek= Optional.of(sumCo2EmissionWeek.get()/recordsLessThanWeek.size());
			sumOveralOccupancyWeek= Optional.of(sumOveralOccupancyWeek.get()/recordsLessThanWeek.size());
			
			
			Double hours = 0.0;
			Double Minutes = 0.0;
			Double seconds = 0.0;
			for (NotificationEntity notificationEntity : recordsLessThanWeek) {
				hours = hours + notificationEntity.getDrivingHours().getHour();
				Minutes = Minutes + notificationEntity.getDrivingHours().getMinute();
				seconds = seconds + notificationEntity.getDrivingHours().getSecond();
			}
			hours = hours + (Minutes / 60) + (seconds / 3600);
			Double sumAvgRunningHoursWeek = hours / recordsLessThanDay.size();
			Stats lastWeekStats = new Stats();
			lastWeekStats.setAvgFuelConsumed(
					sumFuelConsumedWeek.get() != null ? sumFuelConsumedWeek.get().toString() : null);
			lastWeekStats.setRevenue(sumRevenue.get() != null ? sumRevenue.get().toString() : null);
			lastWeekStats
					.setAvgCO2Emission(sumCo2EmissionWeek.get() != null ? sumCo2EmissionWeek.get().toString() : null);
			lastWeekStats.setAvgPassengerCarried(
					sumOveralOccupancyWeek.get() != null ? sumOveralOccupancyWeek.get().toString() : null);
			lastWeekStats.setAvgRunningHours(sumAvgRunningHoursWeek != null ? sumAvgRunningHoursWeek.toString() : null);
			lastWeekStats.setRevenue("100");
			System.out.println(sumAvgRunningHoursWeek);
			generalStats.setWeekly(lastWeekStats);
		}

		
		if (recordsLessThan30DaysOld != null && recordsLessThan30DaysOld.size() > 0) {
			Optional<Double> sumFuelConsumedMonthly = recordsLessThan30DaysOld.stream().map(x -> x.getFuelConsumed())
					.reduce(Double::sum);
			Optional<Double> sumCo2EmissionMonthly = recordsLessThan30DaysOld.stream().map(x -> x.getCo2Emission())
					.reduce(Double::sum);
			Optional<Integer> sumOveralOccupancyMonthly = recordsLessThan30DaysOld.stream()
					.map(x -> x.getOveralOccupancy()).reduce(Integer::sum);
			Optional<Double> sumRevenue = recordsLessThan30DaysOld.stream().map(x -> x.getTicketSaleCost())
					.reduce(Double::sum);

			sumFuelConsumedMonthly= Optional.of(sumFuelConsumedMonthly.get()/recordsLessThan30DaysOld.size());
			sumCo2EmissionMonthly= Optional.of(sumCo2EmissionMonthly.get()/recordsLessThan30DaysOld.size());
			sumOveralOccupancyMonthly= Optional.of(sumOveralOccupancyMonthly.get()/recordsLessThan30DaysOld.size());
			Double hours = 0.0;
			Double Minutes = 0.0;
			Double seconds = 0.0;
			for (NotificationEntity notificationEntity : recordsLessThan30DaysOld) {
				hours = hours + notificationEntity.getDrivingHours().getHour();
				Minutes = Minutes + notificationEntity.getDrivingHours().getMinute();
				seconds = seconds + notificationEntity.getDrivingHours().getSecond();
			}

			hours = hours + (Minutes / 60) + (seconds / 3600);

			Double sumAvgRunningHoursMonthly = hours / recordsLessThan30DaysOld.size();
			Stats lastMonthStats = new Stats();
			lastMonthStats.setAvgFuelConsumed(
					sumFuelConsumedMonthly.get() != null ? sumFuelConsumedMonthly.get().toString() : null);
			lastMonthStats.setAvgCO2Emission(
					sumCo2EmissionMonthly.get() != null ? sumCo2EmissionMonthly.get().toString() : null);
			lastMonthStats.setAvgPassengerCarried(
					sumOveralOccupancyMonthly.get() != null ? sumOveralOccupancyMonthly.get().toString() : null);
			lastMonthStats.setAvgRunningHours(
					sumAvgRunningHoursMonthly != null ? sumAvgRunningHoursMonthly.toString() : null);
			lastMonthStats.setRevenue(sumRevenue.get() != null ? sumRevenue.get().toString() : null);
			generalStats.setMonthly(lastMonthStats);
		}

		if (recordsLessThanYr != null && recordsLessThanYr.size() > 0) {
			Optional<Double> sumFuelConsumedYr = recordsLessThanYr.stream().map(x -> x.getFuelConsumed())
					.reduce(Double::sum);
			Optional<Double> sumCo2EmissionYr = recordsLessThanYr.stream().map(x -> x.getCo2Emission())
					.reduce(Double::sum);
			Optional<Integer> sumOveralOccupancyYr = recordsLessThanYr.stream().map(x -> x.getOveralOccupancy())
					.reduce(Integer::sum);
			
			Optional<Double> sumRevenue = recordsLessThanYr.stream().map(x -> x.getTicketSaleCost())
					.reduce(Double::sum);
			
			
			sumFuelConsumedYr= Optional.of(sumFuelConsumedYr.get()/recordsLessThanYr.size());
			sumCo2EmissionYr= Optional.of(sumCo2EmissionYr.get()/recordsLessThanYr.size());
			sumOveralOccupancyYr= Optional.of(sumOveralOccupancyYr.get()/recordsLessThanYr.size());
			
			Double hours = 0.0;
			Double Minutes = 0.0;
			Double seconds = 0.0;
			for (NotificationEntity notificationEntity : recordsLessThanYr) {
				hours = hours + notificationEntity.getDrivingHours().getHour();
				Minutes = Minutes + notificationEntity.getDrivingHours().getMinute();
				seconds = seconds + notificationEntity.getDrivingHours().getSecond();
			}
			hours = hours + (Minutes / 60) + (seconds / 3600);
			Double sumAvgRunningHoursYr = hours / recordsLessThan30DaysOld.size();
			Stats lastYearStats = new Stats();
			lastYearStats
					.setAvgFuelConsumed(sumFuelConsumedYr.get() != null ? sumFuelConsumedYr.get().toString() : null);
			lastYearStats.setAvgCO2Emission(sumCo2EmissionYr.get() != null ? sumCo2EmissionYr.get().toString() : null);
			lastYearStats.setAvgPassengerCarried(
					sumOveralOccupancyYr.get() != null ? sumOveralOccupancyYr.get().toString() : null);
			lastYearStats.setAvgRunningHours(sumAvgRunningHoursYr != null ? sumAvgRunningHoursYr.toString() : null);
			lastYearStats.setRevenue(sumRevenue.get() != null ? sumRevenue.get().toString() : null);
			generalStats.setYearly(lastYearStats);
		}
		// generalStats code
		return generalStats;
	}
	
	 
		private HashMap<String, Double> getAvgCalculatedValues(List<NotificationEntity> records) {

			HashMap<String, Double> avgCalculatedValues = new HashMap<String, Double>();
			avgCalculatedValues.put("observation", null);
			avgCalculatedValues.put("percentage", 100.00);

			if (records != null && records.size() > 0) {
				int avgcount = records.size();
				Optional<Double> dailyIncome = records.stream().map(x -> x.getTicketSaleCost()).reduce(Double::sum);
				Double avgDailyIncome = dailyIncome.get() / avgcount;
				avgCalculatedValues.put("avgDailyIncome", avgDailyIncome != null ? avgDailyIncome : null);

				Optional<Double> distanceTravelled = records.stream().map(x -> x.getDistanceCovered())
						.reduce(Double::sum);
				Double avgDistanceTravelled = distanceTravelled.get() / avgcount;
				avgCalculatedValues.put("avgDistanceTravelled",
						avgDistanceTravelled != null ? avgDistanceTravelled : null);

				Optional<Double> fuelConsumed = records.stream().map(x -> x.getFuelConsumed()).reduce(Double::sum);
				Double avgFuelConsumed = fuelConsumed.get() / avgcount;
				avgCalculatedValues.put("avgFuelConsumed", avgFuelConsumed != null ? avgFuelConsumed : null);

				Optional<Double> emission = records.stream().map(x -> x.getCo2Emission()).reduce(Double::sum);
				Double avgEmission = emission.get() / avgcount;
				avgCalculatedValues.put("avgCo2Emission", avgEmission != null ? avgEmission : null);
				
				Double hours=0.0 , hoursIdle  = 0.0;
				Double Minutes=0.0 ,minutesIdle = 0.0;
				Double seconds=0.0,secondsIdle = 0.0;
				for (NotificationEntity notificationEntity : records) {
					hours = hours + notificationEntity.getDrivingHours().getHour();
					Minutes = Minutes + notificationEntity.getDrivingHours().getMinute();
					seconds = seconds + notificationEntity.getDrivingHours().getSecond();
					hoursIdle= hoursIdle + notificationEntity.getIdleTime().getHour();
					minutesIdle= minutesIdle + notificationEntity.getIdleTime().getMinute();
					secondsIdle=secondsIdle + notificationEntity.getIdleTime().getSecond();
				}
				hours = hours + (Minutes / 60) + (seconds / 3600);
				Double avgRunningHours = hours / records.size();
				
				hours = hoursIdle + (minutesIdle / 60) + (secondsIdle / 3600);
				Double avgIdleHours = hours / records.size();
				avgCalculatedValues.put("avgRunningHours", avgRunningHours != null ? avgRunningHours : null);
				avgCalculatedValues.put("avgIdleHours", avgIdleHours != null ? avgIdleHours : null);


			}

			return avgCalculatedValues;

		}

		
		@Override
		public  OverAllBusAnalytics getBusOverAllAnalytics() {
			
			    OverAllBusAnalytics busAnalyticsOverAll = new OverAllBusAnalytics();
				List<Object[]> overAllAvgdayAnalystList = notificationEntityDAO.getOverAllNotificationsByYear(now.minusDays(1), now);
				List<Object[]> overAllAvgWeekAnalystList = notificationEntityDAO.getOverAllNotificationsByYear(now.minusWeeks(1), now);
				List<Object[]> overAllAvgMonthyAnalystList = notificationEntityDAO.getOverAllNotificationsByYear(now.minusMonths(1), now);
				List<Object[]> overAllAvgYearAnalystList = notificationEntityDAO.getOverAllNotificationsByYear(now.minusYears(1), now);
				
				List<Object[]> overAlldayAnalystList = notificationEntityDAO.getBusNotificationsOverallTravelByDay(now.minusDays(1), now);
				List<Object[]> overAllWeekAnalystList = notificationEntityDAO.getBusNotificationsOverallTravelByWeek(now.minusWeeks(1), now);
				List<Object[]> overAllMonthyAnalystList = notificationEntityDAO.getBusNotificationsOverallTravelByMonth(now.minusMonths(1), now);
				List<Object[]> overAllYearAnalystList = notificationEntityDAO.getBusNotificationsOverallByTravelYear(now.minusYears(1), now);

				List<Object[]> dayAnalystListLastYr = notificationEntityDAO.getBusNotificationsOverallTravelByDay(now.minusYears(1).minusDays(1),now.minusYears(1));
				List<Object[]> weekAnalystListLastYr = notificationEntityDAO.getBusNotificationsOverallTravelByDay(now.minusYears(1).minusWeeks(1), now.minusYears(1));
				List<Object[]> monthyAnalystListLastYr = notificationEntityDAO.getBusNotificationsOverallTravelByDay(now.minusYears(1).minusMonths(1), now.minusYears(1));
				List<Object[]> yearAnalystListLastYr = notificationEntityDAO.getBusNotificationsOverallTravelByDay(now.minusYears(2), now.minusYears(1));
				
				IncomeStats incomeStats = new IncomeStats();
				incomeStats.setDay(setOverAllIncomeStatsOvall(overAllAvgdayAnalystList,overAlldayAnalystList,dayAnalystListLastYr));
				incomeStats.setWeekly(setOverAllIncomeStatsOvall(overAllAvgWeekAnalystList,overAllWeekAnalystList,weekAnalystListLastYr));
				incomeStats.setMonthly(setOverAllIncomeStatsOvall(overAllAvgMonthyAnalystList,overAllMonthyAnalystList,monthyAnalystListLastYr));
				incomeStats.setYearly(setOverAllIncomeStatsOvall(overAllAvgYearAnalystList,overAllYearAnalystList,yearAnalystListLastYr));
				busAnalyticsOverAll.setIncomeStats(incomeStats);
				
				GeneralStats generalStats = new GeneralStats();
				generalStats.setDay(setGeneralStats(overAllAvgdayAnalystList));
				generalStats.setWeekly(setGeneralStats(overAllAvgWeekAnalystList));
				generalStats.setMonthly(setGeneralStats(overAllAvgMonthyAnalystList));
				generalStats.setYearly(setGeneralStats(overAllAvgYearAnalystList));
				busAnalyticsOverAll.setGeneralStats(generalStats);
				
				DistanceTravelledStats distanceTravelledStats = new DistanceTravelledStats();
				distanceTravelledStats.setDay(getDistOverAll(overAlldayAnalystList,overAllAvgdayAnalystList));
				distanceTravelledStats.setWeekly(getDistOverAll(overAllWeekAnalystList,overAllAvgWeekAnalystList));
				distanceTravelledStats.setMonthly(getDistOverAll(overAllMonthyAnalystList,overAllAvgMonthyAnalystList));
				distanceTravelledStats.setYearly(getDistOverAll(overAllYearAnalystList,overAllAvgYearAnalystList));
				busAnalyticsOverAll.setDistanceTravelledStats(distanceTravelledStats);
				
				FuelConsumedStats fuelConsumedStats = new FuelConsumedStats();
				fuelConsumedStats.setDay(getFuelOverAll(overAlldayAnalystList,overAllAvgdayAnalystList));
				fuelConsumedStats.setWeekly(getFuelOverAll(overAllWeekAnalystList,overAllAvgWeekAnalystList));
				fuelConsumedStats.setMonthly(getFuelOverAll(overAllMonthyAnalystList,overAllAvgMonthyAnalystList));
				fuelConsumedStats.setYearly(getFuelOverAll(overAllYearAnalystList,overAllAvgYearAnalystList));
				busAnalyticsOverAll.setFuelConsumedStats(fuelConsumedStats);
				
				
				TravellingHoursStats travellingHoursStats = new TravellingHoursStats();
				travellingHoursStats.setDay(getTravelMetricsOverAll(overAlldayAnalystList,overAllAvgdayAnalystList));
				travellingHoursStats.setWeekly(getTravelMetricsOverAll(overAllWeekAnalystList,overAllAvgWeekAnalystList));
				travellingHoursStats.setMonthly(getTravelMetricsOverAll(overAllMonthyAnalystList,overAllAvgMonthyAnalystList));
				travellingHoursStats.setYearly(getTravelMetricsOverAll(overAllYearAnalystList,overAllAvgYearAnalystList));
				busAnalyticsOverAll.setTravellingHoursStats(travellingHoursStats);
				
				Co2EmissionAnalytics co2EmissionAnalytics = new Co2EmissionAnalytics();
				co2EmissionAnalytics.setDay(getMetricsCo2OverAll(overAlldayAnalystList,overAllAvgdayAnalystList));
				co2EmissionAnalytics.setWeekly(getMetricsCo2OverAll(overAllWeekAnalystList,overAllAvgWeekAnalystList));
				co2EmissionAnalytics.setMonthly(getMetricsCo2OverAll(overAllMonthyAnalystList,overAllAvgMonthyAnalystList));
				co2EmissionAnalytics.setYearly(getMetricsCo2OverAll(overAllYearAnalystList,overAllAvgYearAnalystList));
				busAnalyticsOverAll.setCo2EmissionAnalytics(co2EmissionAnalytics);
				
				ExpensesStats expensesStats = new ExpensesStats();
				expensesStats.setObservation("up");
				expensesStats.setPercentage("100 %");
				expensesStats.setDay(getExpenseAnalyticsOverAll(overAllAvgdayAnalystList));
				expensesStats.setWeekly(getExpenseAnalyticsOverAll(overAllAvgWeekAnalystList));
				expensesStats.setMonthly(getExpenseAnalyticsOverAll(overAllAvgMonthyAnalystList));
				expensesStats.setYearly(getExpenseAnalyticsOverAll(overAllAvgYearAnalystList));
				busAnalyticsOverAll.setExpensesStats(expensesStats);
				
						
			return busAnalyticsOverAll;
		}
		
		
		
		
		private ExpenseAnalytics getExpenseAnalyticsOverAll(List<Object[]> analystList) {
			ExpenseAnalytics metrics = new ExpenseAnalytics();
			if(analystList!=null && analystList.size()>0) {
				for (Object[] numbers : analystList) {
					metrics.setFuel(numbers[10]!=null ? numbers[10].toString() :null);
					metrics.setMaintenance(numbers[10]!=null ? numbers[10].toString() :null);
					metrics.setOthers(" ");
				}
			}
			return metrics;
		}

		
		private Co2Analytics getMetricsCo2OverAll(List<Object[]> analystList,List<Object[]> avgCalculatedValues) {
			Co2Analytics metrics = new Co2Analytics();
			if(avgCalculatedValues!=null && avgCalculatedValues.size()>0) {
			metrics.setPercentage("1.5%");
			metrics.setObservation("UP");
			for (Object[] objects : avgCalculatedValues) {
				metrics.setEmission(objects[9]!=null ? objects[9].toString() :null);
			}
			
			List<Analytics> analyticsList = new ArrayList<Analytics>();
			if(analystList!=null && analystList.size()>0) {
				for (Object[] numbers : analystList) {
					Analytics analytics = new Analytics();
					analytics.setNode(numbers[0].toString());
					analytics.setCount(Double.parseDouble(numbers[4].toString()));
					analyticsList.add(analytics);
				}
				metrics.setAnalytics(analyticsList);
			}
			}	
			return metrics;
		}

		
		
		private TravelMetrics getTravelMetricsOverAll(List<Object[]> analystList, List<Object[]> avgCalculatedValuesMap) {
			TravelMetrics metrics = new TravelMetrics();
			if(avgCalculatedValuesMap!=null && avgCalculatedValuesMap.size()>0) {
			metrics.setIdleHrsPercentage("1.5%");
			metrics.setRunningHrsPercentage("50 %");
			metrics.setRunningHrsObservation("up");
			metrics.setIdleHrsObservation("idle");
			for (Object[] objects : avgCalculatedValuesMap) {
				metrics.setAvgIdleHours(objects[8]!=null ? objects[8].toString() :null);
				metrics.setAvgIdleHours(objects[2]!=null ? objects[2].toString() :null);
			}
			
			List<TravelingMetricName> travelingMetricNameList = new ArrayList<TravelingMetricName>();
	    
			
			TravelingMetricName travelingMetricNameRunning = new TravelingMetricName();
			List<AnalyticsTime> analyticsListYr = new ArrayList<AnalyticsTime>();
			if(analystList!=null && analystList.size()>0) {
				travelingMetricNameRunning.setMetricName(RUNNING_HOURS);
				for (Object[] numbers : analystList) {
					AnalyticsTime analytics = new AnalyticsTime();
					analytics.setNode(numbers[0].toString());
					analytics.setCount(numbers[2].toString());
					analyticsListYr.add(analytics);
				}
				travelingMetricNameRunning.setAnalytics(analyticsListYr);
				travelingMetricNameList.add(travelingMetricNameRunning);
			}
			
			
			List<AnalyticsTime> analyticsListIdle = new ArrayList<AnalyticsTime>();
			if(analystList!=null && analystList.size()>0) {
				TravelingMetricName travelingMetricNameIdle = new TravelingMetricName();
				travelingMetricNameIdle.setMetricName(IDLE_HOURS);
				for (Object[] numbers : analystList) {
					AnalyticsTime analytics = new AnalyticsTime();
					analytics.setNode(numbers[0].toString());
					analytics.setCount(numbers[3].toString());
					analyticsListIdle.add(analytics);
				}
				travelingMetricNameIdle.setAnalytics(analyticsListIdle);
				travelingMetricNameList.add(travelingMetricNameIdle);
			}
			
			metrics.setTravelingMetricNameList(travelingMetricNameList);
			}
			return metrics;
		}
		
		
		
		private FuelGenericMetrics getFuelOverAll(List<Object[]> analystList,List<Object[]> avgCalculatedValues) {
			FuelGenericMetrics metrics = new FuelGenericMetrics();
			if(avgCalculatedValues!=null && avgCalculatedValues.size()>0) {
			
			metrics.setPercentage("1.5%");
			metrics.setObservation("UP");
			for (Object[] objects : avgCalculatedValues) {
				metrics.setAvgFuelConsumed(objects[6]!=null ? objects[6].toString() :null);
			}
			
			List<Analytics> analyticsList = new ArrayList<Analytics>();
			if(analystList!=null && analystList.size()>0) {
				for (Object[] numbers : analystList) {
					Analytics analytics = new Analytics();
					analytics.setNode(numbers[0].toString());
					analytics.setCount(Double.parseDouble(numbers[6].toString()));
					analyticsList.add(analytics);
				}
				metrics.setAnalytics(analyticsList);
			}
			}		
			return metrics;
		}

		
		
		private GenericMetrics getDistOverAll(List<Object[]> analystList,List<Object[]> avgCalculatedValues) {
			GenericMetrics metrics = new GenericMetrics();
			if(avgCalculatedValues!=null && avgCalculatedValues.size()>0) {
			metrics.setPercentage("1.5%");
			metrics.setObservation("UP");
			for (Object[] objects : avgCalculatedValues) {
				metrics.setAvgDistanceTravelled(objects[6]!=null ? objects[6].toString() :null);
			}
			List<Analytics> analyticsList = new ArrayList<Analytics>();
			if(analystList!=null && analystList.size()>0) {
				for (Object[] numbers : analystList) {
					Analytics analytics = new Analytics();
					analytics.setNode(numbers[0].toString());
					analytics.setCount(Double.parseDouble(numbers[4].toString()));
					analyticsList.add(analytics);
				}
				metrics.setAnalytics(analyticsList);
			}
			}
			return metrics;
		}
		
		
		
		private Stats setGeneralStats(List<Object[]> analystList) {
			Stats lastDayStats = new Stats();
			
			if(analystList!=null && analystList.size()>0) {
			for (Object[] objects : analystList) {
				lastDayStats.setRevenue(objects[5]!=null ? objects[5].toString() :null);
				lastDayStats.setAvgFuelConsumed(objects[1]!=null ? objects[1].toString() :null);
				lastDayStats.setAvgRunningHours(objects[2]!=null ? objects[2].toString() :null);
				lastDayStats.setAvgCO2Emission(objects[3]!=null ? objects[3].toString() :null);
				lastDayStats.setAvgPassengerCarried(objects[4]!=null ? objects[4].toString() :null);
			}
			}
			return lastDayStats;
			
		}
		
		private Metrics setOverAllIncomeStatsOvall(List<Object[]> overAllStats,List<Object[]> analystList, List<Object[]> analystListLastYr) {
			Metrics metrics = new Metrics();
			if(analystList!=null&& !analystList.isEmpty() && analystList.size()>0 ) {
				
			// this is for Income
			metrics.setPercentage("1.5%");
			metrics.setObservation("UP");
			for (Object[] objects : overAllStats) {
				metrics.setAvgIncome(objects[0]!=null ? objects[0].toString() :null);
			}
			
			List<IncomeMetricName> IncomeMetricList = new ArrayList<IncomeMetricName>();
			IncomeMetricName incomeMetricNameCurrentYear = new IncomeMetricName();
			List<Analytics> analyticsListYr = new ArrayList<Analytics>();
			if(analystList!=null && analystList.size()>0) {
				incomeMetricNameCurrentYear.setMetricName(CURRENT_YEAR);
				for (Object[] numbers : analystList) {
					Analytics analytics = new Analytics();
					analytics.setNode(numbers[0].toString());
					analytics.setCount(Double.parseDouble(numbers[1].toString()));
					analyticsListYr.add(analytics);
				}
				incomeMetricNameCurrentYear.setAnalytics(analyticsListYr);
				IncomeMetricList.add(incomeMetricNameCurrentYear);
			}
			
			List<Analytics> analyticsListPrevYr = new ArrayList<Analytics>();
			
			  if(analystListLastYr!=null && analystListLastYr.size()>0) { IncomeMetricName
			  incomeMetricNamePrevYr = new IncomeMetricName();
			  incomeMetricNamePrevYr.setMetricName(LAST_YEAR); for (Object[] numbers :
			  analystListLastYr) { Analytics analytics = new Analytics();
			  analytics.setNode(numbers[0].toString());
			  analytics.setCount(Double.parseDouble(numbers[1].toString()));
			  analyticsListPrevYr.add(analytics); }
			  incomeMetricNamePrevYr.setAnalytics(analyticsListPrevYr);
			  IncomeMetricList.add(incomeMetricNamePrevYr); }
			 
			metrics.setAnalyticsData(IncomeMetricList);
			return metrics;
			}
			return metrics;
			
		}

}
