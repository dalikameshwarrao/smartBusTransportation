package com.smartbustransport.repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Date;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.smartbustransport.entity.NotificationEntity;
import com.smartbustransport.entity.TripDetailEntity;

public interface NotificationEntityDAO extends JpaRepository<NotificationEntity, String>{
	

    static final String QUERY_FOR_GET_ACTIVE_ROUTES = "SELECT * from notification_entity "
    		+ " where is_Current_location='Y' ";
    
    static final String QUERY_FOR_GET_COMPLETED_ROUTES = "SELECT distinct *  from notification_entity where " + 
    		"trip_id not in (select trip_id from notification_entity " + 
    		"where is_Current_location='Y') and destination=stop_name  ";

    static final String QUERY_FOR_GET_BUSCOUNT = "SELECT count(distinct trip_id) from notification_entity "
    		+ " where bus_id=:busId and (case when :activeRoutes='Y' then is_Current_location=:activeRoutes"
    		+ " else trip_id not in (select trip_id from notification_entity " + 
    		"where is_Current_location='Y') and  is_Current_location is null end ) ";
    
    static final String QUERY_FOR_GET_TRIPSCOMPCOUNT = "SELECT count(trip_id)-1 from notification_entity "
    		+ " where trip_id=:tripId and  is_Current_location is null ";
    

    static final String QUERY_FOR_GET_UNIQUE_TRIP_BUSES_DETAILS = "SELECT * from notification_entity "
    		+ " where bus_no=:value and (is_Current_location ='Y' or (destination=stop_name)) ";
    

    static final String QUERY_FOR_GET_UNIQUE_DRIVER_TRIP_DETAILS = "SELECT * from notification_entity "
    		+ " where driver_name=:value and (is_Current_location ='Y' or (destination=stop_name)) ";
    

    static final String QUERY_FOR_GET_UNIQUE_TRIP_ROUTE_DETAILS = "SELECT * from notification_entity "
    		+ " where route_name=:value and (is_Current_location ='Y' or (destination=stop_name)) ";

    static final String QUERY_FOR_GET_NOTIFICATIONS_BY_TYPE = "SELECT * from notification_entity "
    		+ " where notification_type=:notificationType";
    

    static final String QUERY_FOR_GET_NOTIFICATIONS_BY_BUS_NO = "SELECT * from notification_entity "
    		+ " where bus_no=:busNo and (is_current_location='Y' or destination=stop_name) ";
    
    static final String QUERY_FOR_GET_NOTIFICATIONS_BY_DATE  ="select * from notification_entity"
    		+ " where notification_date>=:startDate and notification_date<=:endDate"
    		+ "  and (is_current_location='Y' or destination=stop_name) and driver_id=:driverId  ";
    
    static final String QUERY_FOR_GET_DRIVERNOTIFICATIONS_HOURS  =" SELECT  DATE_PART('hour',notification_date) as hour,  COUNT(*)"
    		+ " from notification_entity "
    		+ " where notification_date>=:startDate and notification_date<=:endDate"
    		+ "  and (is_current_location='Y' or destination=stop_name) and driver_id=:driverId  GROUP BY DATE_PART('hour',notification_date) ";

    static final String QUERY_FOR_GET_DRIVERNOTIFICATIONS_DAY  =" SELECT  DATE_PART('day',notification_date) as day,  COUNT(*)"
    		+ " from notification_entity "
    		+ " where notification_date>=:startDate and notification_date<=:endDate"
    		+ "  and (is_current_location='Y' or destination=stop_name) and driver_id=:driverId  GROUP BY DATE_PART('day',notification_date)";

    static final String QUERY_FOR_GET_DRIVERNOTIFICATIONS_MONTHLY  =" SELECT  date_trunc('month',notification_date) as month,  COUNT(*)"
    		+ " from notification_entity "
    		+ " where notification_date>=:startDate and notification_date<=:endDate "
    		+ "  and (is_current_location='Y' or destination=stop_name) and driver_id=:driverId  GROUP BY date_trunc('month',notification_date)";


    static final String QUERY_FOR_GET_DRIVERDISTCOV_HOURS  =" SELECT  DATE_PART('hour',notification_date) as hour,    sum(distance_covered) "
    		+ " from notification_entity "
    		+ " where notification_date>=:startDate and notification_date<=:endDate"
    		+ "  and (is_current_location='Y' or destination=stop_name) and driver_id=:driverId  GROUP BY DATE_PART('hour',notification_date) ";

    static final String QUERY_FOR_GET_DRIVERDISTCOV_DAY  =" SELECT  DATE_PART('day',notification_date) as day,    sum(distance_covered) "
    		+ " from notification_entity "
    		+ " where notification_date>=:startDate and notification_date<=:endDate"
    		+ "  and (is_current_location='Y' or destination=stop_name) and driver_id=:driverId  GROUP BY DATE_PART('day',notification_date)";

    static final String QUERY_FOR_GET_DRIVERDISTCOV_MONTHLY  =" SELECT  date_trunc('month',notification_date) as month,    sum(distance_covered) "
    		+ " from notification_entity "
    		+ " where notification_date>=:startDate and notification_date<=:endDate"
    		+ "  and (is_current_location='Y' or destination=stop_name) and driver_id=:driverId "
    		+ " GROUP BY date_trunc('month',notification_date)";
    
    static final String QUERY_FOR_GET_DRIVERLBCOST_HOURS  =" SELECT  DATE_PART('hour',notification_date) as hour,    sum(labour_cost) "
    		+ " from notification_entity "
    		+ " where notification_date>=:startDate and notification_date<=:endDate"
    		+ "  and (is_current_location='Y' or destination=stop_name) and driver_id=:driverId  GROUP BY DATE_PART('hour',notification_date) ";

    static final String QUERY_FOR_GET_DRIVERLBCOST_DAY  =" SELECT  DATE_PART('day',notification_date) as day,    sum(labour_cost) "
    		+ " from notification_entity "
    		+ " where notification_date>=:startDate and notification_date<=:endDate"
    		+ "  and (is_current_location='Y' or destination=stop_name) and driver_id=:driverId  GROUP BY DATE_PART('day',notification_date)";

    static final String QUERY_FOR_GET_DRIVERLBCOST_MONTHLY  =" SELECT  date_trunc('month',notification_date) as month,    sum(labour_cost) "
    		+ " from notification_entity "
    		+ " where notification_date>=:startDate and notification_date<=:endDate"
    		+ "  and (is_current_location='Y' or destination=stop_name) and driver_id=:driverId "
    		+ " GROUP BY date_trunc('month',notification_date)";
    
    static final String QUERY_FOR_GET_NOTIFICATIONS_BY_DATE_ALL  ="select * from notification_entity"
    		+ " where notification_date>=:startDate and notification_date<=:endDate"
    		+ "  and (is_current_location='Y' or destination=stop_name)   ";
    
    
    static final String QUERY_FOR_GET_DRIVERNOTIFICATIONS_HOURS_ALL  =" SELECT  DATE_PART('hour',notification_date) as hour,  COUNT(*)"
    		+ " from notification_entity "
    		+ " where notification_date>=:startDate and notification_date<=:endDate"
    		+ "  and (is_current_location='Y' or destination=stop_name)  GROUP BY DATE_PART('hour',notification_date) ";
    
    static final String QUERY_FOR_GET_DRIVERNOTIFICATIONS_MONTHLY_ALL  =" SELECT  date_trunc('month',notification_date) as month,  COUNT(*)"
    		+ " from notification_entity "
    		+ " where notification_date>=:startDate and notification_date<=:endDate "
    		+ "  and (is_current_location='Y' or destination=stop_name)  GROUP BY date_trunc('month',notification_date)";
    
    static final String QUERY_FOR_GET_DRIVERLBCOST_HOURS_ALL  =" SELECT  DATE_PART('hour',notification_date) as hour,    sum(labour_cost) "
    		+ " from notification_entity "
    		+ " where notification_date>=:startDate and notification_date<=:endDate"
    		+ "  and (is_current_location='Y' or destination=stop_name)   GROUP BY DATE_PART('hour',notification_date) ";

    static final String QUERY_FOR_GET_DRIVERLBCOST_DAY_ALL  =" SELECT  DATE_PART('day',notification_date) as day,    sum(labour_cost) "
    		+ " from notification_entity "
    		+ " where notification_date>=:startDate and notification_date<=:endDate"
    		+ "  and (is_current_location='Y' or destination=stop_name)   GROUP BY DATE_PART('day',notification_date)";

    static final String QUERY_FOR_GET_DRIVERLBCOST_MONTHLY_ALL  =" SELECT  date_trunc('month',notification_date) as month,    sum(labour_cost) "
    		+ " from notification_entity "
    		+ " where notification_date>=:startDate and notification_date<=:endDate"
    		+ "  and (is_current_location='Y' or destination=stop_name) "
    		+ " GROUP BY date_trunc('month',notification_date)";



    
    @Query(value =QUERY_FOR_GET_ACTIVE_ROUTES,nativeQuery = true)
    public List<NotificationEntity> getActiveRoutes();
    
    @Query(value =QUERY_FOR_GET_COMPLETED_ROUTES,nativeQuery = true)
    public List<NotificationEntity> getCompletedRoutes();
    
    @Query(value =QUERY_FOR_GET_BUSCOUNT,nativeQuery = true)
    public Integer getCountofBusesForRoute(String busId,String activeRoutes);
    
    @Query(value =QUERY_FOR_GET_TRIPSCOMPCOUNT, nativeQuery = true)
    public Integer getTripCompletedStopCount(String tripId); 
    

    @Query(value =QUERY_FOR_GET_UNIQUE_TRIP_BUSES_DETAILS, nativeQuery = true)
    List<NotificationEntity> getTripDetailsBuses(String value);
    
    @Query(value =QUERY_FOR_GET_UNIQUE_DRIVER_TRIP_DETAILS, nativeQuery = true)
    List<NotificationEntity> getTripDetailsDrivers(String value);
    
    @Query(value =QUERY_FOR_GET_UNIQUE_TRIP_ROUTE_DETAILS, nativeQuery = true)
    List<NotificationEntity> getTripDetailsRoutes(String value);
    

    @Query(value =QUERY_FOR_GET_NOTIFICATIONS_BY_TYPE,nativeQuery = true)
    public List<NotificationEntity> getNotifications(String notificationType);
    
    @Query(value =QUERY_FOR_GET_NOTIFICATIONS_BY_BUS_NO,nativeQuery = true)
	public List<NotificationEntity> getNotificationDetailsByBusNo(String busNo);
    
    

    static final String QUERY_FOR_GET_BUSNOTIFICATIONS_DAY  =" SELECT  date_trunc('hour',notification_date) as day,  sum(ticket_sale_cost) "
    		+ " from notification_entity "
    		+ " where notification_date>=:startDate and notification_date<=:endDate"
    		+ "  and (is_current_location='Y' or destination=stop_name) and bus_no=:busNo  GROUP BY date_trunc('hour',notification_date)";
    

    static final String QUERY_FOR_GET_BUSNOTIFICATIONS_WEEK  =" SELECT  date_trunc('day',notification_date) as week,  sum(ticket_sale_cost) "
    		+ " from notification_entity "
    		+ " where notification_date>=:startDate and notification_date<=:endDate"
    		+ "  and (is_current_location='Y' or destination=stop_name) and bus_no=:busNo  GROUP BY date_trunc('day',notification_date) ";

    static final String QUERY_FOR_GET_BUSNOTIFICATIONS_MONTHLY  =" SELECT  date_trunc('week',notification_date) as month,  sum(ticket_sale_cost) "
    		+ " from notification_entity "
    		+ " where notification_date>=:startDate and notification_date<=:endDate "
    		+ "  and (is_current_location='Y' or destination=stop_name) and bus_no=:busNo  GROUP BY date_trunc('week',notification_date) ";
    
    static final String QUERY_FOR_GET_BUSNOTIFICATIONS_YEARLY  =" SELECT  date_trunc('month',notification_date) as month,  sum(ticket_sale_cost) "
    		+ " from notification_entity "
    		+ " where notification_date>=:startDate and notification_date<=:endDate "
    		+ "  and (is_current_location='Y' or destination=stop_name) and bus_no=:busNo  GROUP BY date_trunc('month',notification_date)";
    
    // distance covered by Bus Queries
    
    static final String QUERY_FOR_GET_BUS_DISTANCE_NOTIFICATIONS_DAY  =" SELECT  date_trunc('hour',notification_date) as day,  sum(distance_covered) "
    		+ " from notification_entity "
    		+ " where notification_date>=:startDate and notification_date<=:endDate"
    		+ "  and (is_current_location='Y' or destination=stop_name) and bus_no=:busNo  GROUP BY date_trunc('hour',notification_date)";
    

    static final String QUERY_FOR_GET_BUS_DISTANCE_NOTIFICATIONS_WEEK  =" SELECT  date_trunc('day',notification_date) as week,  sum(distance_covered) "
    		+ " from notification_entity "
    		+ " where notification_date>=:startDate and notification_date<=:endDate"
    		+ "  and (is_current_location='Y' or destination=stop_name) and bus_no=:busNo  GROUP BY date_trunc('day',notification_date) ";

    static final String QUERY_FOR_GET_BUS_DISTANCE_NOTIFICATIONS_MONTHLY  =" SELECT  date_trunc('week',notification_date) as month,  sum(distance_covered) "
    		+ " from notification_entity "
    		+ " where notification_date>=:startDate and notification_date<=:endDate "
    		+ "  and (is_current_location='Y' or destination=stop_name) and bus_no=:busNo  GROUP BY date_trunc('week',notification_date) ";
    
    static final String QUERY_FOR_GET_BUS_DISTANCE_NOTIFICATIONS_YEARLY  =" SELECT  date_trunc('month',notification_date) as month,  sum(distance_covered) "
    		+ " from notification_entity "
    		+ " where notification_date>=:startDate and notification_date<=:endDate "
    		+ "  and (is_current_location='Y' or destination=stop_name) and bus_no=:busNo  GROUP BY date_trunc('month',notification_date)";
    
    //distance covered by Bus Queries Ended
    
    //fuel consumed by Bus Queries Started
    
    static final String QUERY_FOR_GET_BUS_FUEL_NOTIFICATIONS_DAY  =" SELECT  date_trunc('hour',notification_date) as day,  sum(fuel_consumed) "
    		+ " from notification_entity "
    		+ " where notification_date>=:startDate and notification_date<=:endDate"
    		+ "  and (is_current_location='Y' or destination=stop_name) and bus_no=:busNo  GROUP BY date_trunc('hour',notification_date)";
    

    static final String QUERY_FOR_GET_BUS_FUEL_NOTIFICATIONS_WEEK  =" SELECT  date_trunc('day',notification_date) as week,  sum(fuel_consumed) "
    		+ " from notification_entity "
    		+ " where notification_date>=:startDate and notification_date<=:endDate"
    		+ "  and (is_current_location='Y' or destination=stop_name) and bus_no=:busNo  GROUP BY date_trunc('day',notification_date) ";

    static final String QUERY_FOR_GET_BUS_FUEL_NOTIFICATIONS_MONTHLY  =" SELECT  date_trunc('week',notification_date) as month,  sum(fuel_consumed) "
    		+ " from notification_entity "
    		+ " where notification_date>=:startDate and notification_date<=:endDate "
    		+ "  and (is_current_location='Y' or destination=stop_name) and bus_no=:busNo  GROUP BY date_trunc('week',notification_date) ";
    
    static final String QUERY_FOR_GET_BUS_FUEL_NOTIFICATIONS_YEARLY  =" SELECT  date_trunc('month',notification_date) as month,  sum(fuel_consumed) "
    		+ " from notification_entity "
    		+ " where notification_date>=:startDate and notification_date<=:endDate "
    		+ "  and (is_current_location='Y' or destination=stop_name) and bus_no=:busNo  GROUP BY date_trunc('month',notification_date)";
    
    // fuel consumed by Bus Queries Ended
    
    
    //Co2 Started
    
    static final String QUERY_FOR_GET_BUS_CO2_NOTIFICATIONS_DAY  =" SELECT  date_trunc('hour',notification_date) as day,  sum(co2_emission) "
    		+ " from notification_entity "
    		+ " where notification_date>=:startDate and notification_date<=:endDate"
    		+ "  and (is_current_location='Y' or destination=stop_name) and bus_no=:busNo  GROUP BY date_trunc('hour',notification_date)";
    

    static final String QUERY_FOR_GET_BUS_CO2_NOTIFICATIONS_WEEKLY  =" SELECT  date_trunc('day',notification_date) as week,  sum(co2_emission) "
    		+ " from notification_entity "
    		+ " where notification_date>=:startDate and notification_date<=:endDate"
    		+ "  and (is_current_location='Y' or destination=stop_name) and bus_no=:busNo  GROUP BY date_trunc('day',notification_date) ";

    static final String QUERY_FOR_GET_BUS_CO2_NOTIFICATIONS_MONTHLY  =" SELECT  date_trunc('week',notification_date) as month,  sum(co2_emission) "
    		+ " from notification_entity "
    		+ " where notification_date>=:startDate and notification_date<=:endDate "
    		+ "  and (is_current_location='Y' or destination=stop_name) and bus_no=:busNo  GROUP BY date_trunc('week',notification_date) ";
    
    static final String QUERY_FOR_GET_BUS_CO2_NOTIFICATIONS_YEARLY  =" SELECT  date_trunc('month',notification_date) as year,  sum(co2_emission) "
    		+ " from notification_entity "
    		+ " where notification_date>=:startDate and notification_date<=:endDate "
    		+ "  and (is_current_location='Y' or destination=stop_name) and bus_no=:busNo  GROUP BY date_trunc('month',notification_date)";
    
    //Co2 Ended
    
    //Expense Started
    
    static final String QUERY_FOR_GET_BUS_TRAVEL_NOTIFICATIONS_DAY  =" SELECT  date_trunc('hour',notification_date) as day, CAST(sum(driving_hours) as varchar) driver , CAST(sum(idle_time) as varchar) idle_time  "
    		+ " from notification_entity "
    		+ " where notification_date>=:startDate and notification_date<=:endDate"
    		+ "  and (is_current_location='Y' or destination=stop_name) and bus_no=:busNo  GROUP BY date_trunc('hour',notification_date)";
    

    static final String QUERY_FOR_GET_BUS_TRAVEL_NOTIFICATIONS_WEEKLY  =" SELECT  date_trunc('day',notification_date) as week,  CAST(sum(driving_hours) as varchar) driver , CAST(sum(idle_time) as varchar) idle_time "
    		+ " from notification_entity "
    		+ " where notification_date>=:startDate and notification_date<=:endDate"
    		+ "  and (is_current_location='Y' or destination=stop_name) and bus_no=:busNo  GROUP BY date_trunc('day',notification_date) ";

    static final String QUERY_FOR_GET_BUS_TRAVEL_NOTIFICATIONS_MONTHLY  =" SELECT  date_trunc('week',notification_date) as month, CAST(sum(driving_hours) as varchar) driver , CAST(sum(idle_time) as varchar) idle_time "
    		+ " from notification_entity "
    		+ " where notification_date>=:startDate and notification_date<=:endDate "
    		+ "  and (is_current_location='Y' or destination=stop_name) and bus_no=:busNo  GROUP BY date_trunc('week',notification_date) ";
    
    static final String QUERY_FOR_GET_BUS_TRAVEL_NOTIFICATIONS_YEARLY  =" SELECT  date_trunc('month',notification_date) as year,  CAST(sum(driving_hours) as varchar) driver , CAST(sum(idle_time) as varchar) idle_time "
    		+ " from notification_entity "
    		+ " where notification_date>=:startDate and notification_date<=:endDate "
    		+ "  and (is_current_location='Y' or destination=stop_name) and bus_no=:busNo  GROUP BY date_trunc('month',notification_date)";
    
    //Expense Ended
    
    //Travel Started
    
    static final String QUERY_FOR_GET_BUS_EXPENSE_NOTIFICATIONS_DAY  =" SELECT  date_trunc('hour',notification_date) as day, sum(fuel_cost) as avgFuelCost , sum(maintenance_cost) "
    		+ " from notification_entity "
    		+ " where notification_date>=:startDate and notification_date<=:endDate"
    		+ "  and (is_current_location='Y' or destination=stop_name) and bus_no=:busNo  GROUP BY date_trunc('hour',notification_date)";
    

    static final String QUERY_FOR_GET_BUS_EXPENSE_NOTIFICATIONS_WEEKLY  =" SELECT  date_trunc('day',notification_date) as week,  sum(fuel_cost) as avgFuelCost , sum(maintenance_cost) "
    		+ " from notification_entity "
    		+ " where notification_date>=:startDate and notification_date<=:endDate"
    		+ "  and (is_current_location='Y' or destination=stop_name) and bus_no=:busNo  GROUP BY date_trunc('day',notification_date) ";

    static final String QUERY_FOR_GET_BUS_EXPENSE_NOTIFICATIONS_MONTHLY  =" SELECT  date_trunc('week',notification_date) as month, sum(fuel_cost) as avgFuelCost , sum(maintenance_cost) "
    		+ " from notification_entity "
    		+ " where notification_date>=:startDate and notification_date<=:endDate "
    		+ "  and (is_current_location='Y' or destination=stop_name) and bus_no=:busNo  GROUP BY date_trunc('week',notification_date) ";
    
    static final String QUERY_FOR_GET_BUS_EXPENSE_NOTIFICATIONS_YEARLY  =" SELECT  date_trunc('month',notification_date) as year,  sum(fuel_cost) as avgFuelCost, sum(maintenance_cost) "
    		+ " from notification_entity "
    		+ " where notification_date>=:startDate and notification_date<=:endDate "
    		+ "  and (is_current_location='Y' or destination=stop_name) and bus_no=:busNo  GROUP BY date_trunc('month',notification_date)";
    
    //Travel Ended

    static final String QUERY_FOR_GET_DRIVERNOTIFICATIONS_DAY_ALL  =" SELECT  DATE_PART('day',notification_date) as day,  COUNT(*)"
    		+ " from notification_entity "
    		+ " where notification_date>=:startDate and notification_date<=:endDate"
    		+ "  and (is_current_location='Y' or destination=stop_name)  GROUP BY DATE_PART('day',notification_date)";
    
    static final String QUERY_FOR_GET_DRIVERDISTCOV_HOURS_ALL  =" SELECT  DATE_PART('hour',notification_date) as hour,    sum(distance_covered) "
    		+ " from notification_entity "
    		+ " where notification_date>=:startDate and notification_date<=:endDate"
    		+ "  and (is_current_location='Y' or destination=stop_name)  GROUP BY DATE_PART('hour',notification_date) ";
    static final String QUERY_FOR_GET_DRIVERDISTCOV_DAY_ALL  =" SELECT  DATE_PART('day',notification_date) as day,    sum(distance_covered) "
    		+ " from notification_entity "
    		+ " where notification_date>=:startDate and notification_date<=:endDate"
    		+ "  and (is_current_location='Y' or destination=stop_name)   GROUP BY DATE_PART('day',notification_date)";

    static final String QUERY_FOR_GET_DRIVERDISTCOV_MONTHLY_ALL  =" SELECT  date_trunc('month',notification_date) as month,    sum(distance_covered) "
    		+ " from notification_entity "
    		+ " where notification_date>=:startDate and notification_date<=:endDate"
    		+ "  and (is_current_location='Y' or destination=stop_name) "
    		+ " GROUP BY date_trunc('month',notification_date)";

    
    @Query(value =QUERY_FOR_GET_BUSNOTIFICATIONS_DAY,nativeQuery = true )
    public List<Object[]> getDriverNotificationsByDay(@Param("startDate") LocalDateTime startDate,@Param("endDate") LocalDateTime endDate, String busNo);
    

    @Query(value =QUERY_FOR_GET_BUSNOTIFICATIONS_WEEK,nativeQuery = true )
    public List<Object[]> getDriverNotificationsByWeek(@Param("startDate") LocalDateTime startDate,@Param("endDate") LocalDateTime endDate, String busNo);
    

    @Query(value =QUERY_FOR_GET_BUSNOTIFICATIONS_MONTHLY,nativeQuery = true )
    public List<Object[]> getDriverNotificationsByMonth(@Param("startDate") LocalDateTime startDate,@Param("endDate") LocalDateTime endDate, String busNo);
    

    @Query(value =QUERY_FOR_GET_BUSNOTIFICATIONS_YEARLY,nativeQuery = true )
    public List<Object[]> getDriverNotificationsByYear(@Param("startDate") LocalDateTime startDate,@Param("endDate") LocalDateTime endDate, String busNo);
    
    
    public List<NotificationEntity> findByBusNo(String busNo);
    
    public List<NotificationEntity> findByDriveName(String driverName);
    
    public List<NotificationEntity> findByRouteName(String routeName);
    
    @Query(value =QUERY_FOR_GET_NOTIFICATIONS_BY_DATE,nativeQuery = true )
    public List<NotificationEntity> getNotificationsByDate(@Param("startDate") Date startDate,@Param("endDate") Date endDate,Long driverId);

    @Query(value =QUERY_FOR_GET_DRIVERNOTIFICATIONS_HOURS,nativeQuery = true )
    public List<Number[]> getDriverNotificationsByHours(@Param("startDate") Date startDate,@Param("endDate") Date endDate,Long driverId);

    @Query(value =QUERY_FOR_GET_DRIVERNOTIFICATIONS_DAY,nativeQuery = true )
    public List<Number[]> getDriverNotificationsByDay(@Param("startDate") Date startDate,@Param("endDate") Date endDate,Long driverId);


    @Query(value =QUERY_FOR_GET_DRIVERNOTIFICATIONS_MONTHLY,nativeQuery = true )
    public List<Object[]> getDriverNotificationsByMonthly(@Param("startDate") Date startDate,@Param("endDate") Date endDate,Long driverId);

    
    @Query(value =QUERY_FOR_GET_DRIVERDISTCOV_HOURS,nativeQuery = true )
    public List<Number[]> getDriverDisCoveredByHours(@Param("startDate") Date startDate,@Param("endDate") Date endDate,Long driverId);

    @Query(value =QUERY_FOR_GET_DRIVERDISTCOV_DAY,nativeQuery = true )
    public List<Number[]> getDriverDisCoveredByDay(@Param("startDate") Date startDate,@Param("endDate") Date endDate,Long driverId);


    @Query(value =QUERY_FOR_GET_DRIVERDISTCOV_MONTHLY,nativeQuery = true )
    public List<Object[]> getDriverDisCoveredByMonthly(@Param("startDate") Date startDate,@Param("endDate") Date endDate,Long driverId);


    
    @Query(value =QUERY_FOR_GET_DRIVERLBCOST_HOURS,nativeQuery = true )
    public List<Number[]> getDriverLabCostByHours(@Param("startDate") Date startDate,@Param("endDate") Date endDate,Long driverId);

    @Query(value =QUERY_FOR_GET_DRIVERLBCOST_DAY,nativeQuery = true )
    public List<Number[]> getDriverLabCostByDay(@Param("startDate") Date startDate,@Param("endDate") Date endDate,Long driverId);


    @Query(value =QUERY_FOR_GET_DRIVERLBCOST_MONTHLY,nativeQuery = true )
    public List<Object[]> getDriverLabCostByMonthly(@Param("startDate") Date startDate,@Param("endDate") Date endDate,Long driverId);
    
    //ALL DRIVERS
    
    
    @Query(value =QUERY_FOR_GET_NOTIFICATIONS_BY_DATE_ALL,nativeQuery = true )
    public List<NotificationEntity> getNotificationsByDateAll(@Param("startDate") Date startDate,@Param("endDate") Date endDate);

    @Query(value =QUERY_FOR_GET_DRIVERNOTIFICATIONS_HOURS_ALL,nativeQuery = true )
    public List<Number[]> getDriverNotificationsByHoursAll(@Param("startDate") Date startDate,@Param("endDate") Date endDate);

    @Query(value =QUERY_FOR_GET_DRIVERNOTIFICATIONS_DAY_ALL,nativeQuery = true )
    public List<Number[]> getDriverNotificationsByDayAll(@Param("startDate") Date startDate,@Param("endDate") Date endDate);


    @Query(value =QUERY_FOR_GET_DRIVERNOTIFICATIONS_MONTHLY_ALL,nativeQuery = true )
    public List<Object[]> getDriverNotificationsByMonthlyAll(@Param("startDate") Date startDate,@Param("endDate") Date endDate);

    
    @Query(value =QUERY_FOR_GET_DRIVERDISTCOV_HOURS_ALL,nativeQuery = true )
    public List<Number[]> getDriverDisCoveredByHoursAll(@Param("startDate") Date startDate,@Param("endDate") Date endDate);

    @Query(value =QUERY_FOR_GET_DRIVERDISTCOV_DAY_ALL,nativeQuery = true )
    public List<Number[]> getDriverDisCoveredByDayAll(@Param("startDate") Date startDate,@Param("endDate") Date endDate);


    @Query(value =QUERY_FOR_GET_DRIVERDISTCOV_MONTHLY_ALL,nativeQuery = true )
    public List<Object[]> getDriverDisCoveredByMonthlyAll(@Param("startDate") Date startDate,@Param("endDate") Date endDate);


    
    @Query(value =QUERY_FOR_GET_DRIVERLBCOST_HOURS_ALL,nativeQuery = true )
    public List<Number[]> getDriverLabCostByHoursAll(@Param("startDate") Date startDate,@Param("endDate") Date endDate);

    @Query(value =QUERY_FOR_GET_DRIVERLBCOST_DAY_ALL,nativeQuery = true )
    public List<Number[]> getDriverLabCostByDayAll(@Param("startDate") Date startDate,@Param("endDate") Date endDate);


    @Query(value =QUERY_FOR_GET_DRIVERLBCOST_MONTHLY_ALL,nativeQuery = true )
    public List<Object[]> getDriverLabCostByMonthlyAll(@Param("startDate") Date startDate,@Param("endDate") Date endDate);
    
    

    @Query(value =QUERY_FOR_GET_BUS_DISTANCE_NOTIFICATIONS_DAY,nativeQuery = true )
    public List<Object[]> getDriverNotificationsByDistCovDay(@Param("startDate") LocalDateTime startDate,@Param("endDate") LocalDateTime endDate, String busNo);
    
    @Query(value =QUERY_FOR_GET_BUS_DISTANCE_NOTIFICATIONS_WEEK,nativeQuery = true )
    public List<Object[]> getDriverNotificationsByDistCovWeek(@Param("startDate") LocalDateTime startDate,@Param("endDate") LocalDateTime endDate, String busNo);
    
    
    @Query(value =QUERY_FOR_GET_BUS_DISTANCE_NOTIFICATIONS_MONTHLY,nativeQuery = true )
    public List<Object[]> getDriverNotificationsByDistCovMonth(@Param("startDate") LocalDateTime startDate,@Param("endDate") LocalDateTime endDate, String busNo);
    
    
    @Query(value =QUERY_FOR_GET_BUS_DISTANCE_NOTIFICATIONS_YEARLY,nativeQuery = true )
    public List<Object[]> getDriverNotificationsByDistCovYear(@Param("startDate") LocalDateTime startDate,@Param("endDate") LocalDateTime endDate, String busNo);

    /// fuel Consumed
    
    @Query(value =QUERY_FOR_GET_BUS_FUEL_NOTIFICATIONS_DAY,nativeQuery = true )
    public List<Object[]> getDriverNotificationsByFuelDay(@Param("startDate") LocalDateTime startDate,@Param("endDate") LocalDateTime endDate, String busNo);
    
    @Query(value =QUERY_FOR_GET_BUS_FUEL_NOTIFICATIONS_WEEK,nativeQuery = true )
    public List<Object[]> getDriverNotificationsByFuelWeek(@Param("startDate") LocalDateTime startDate,@Param("endDate") LocalDateTime endDate, String busNo);
    
    
    @Query(value =QUERY_FOR_GET_BUS_FUEL_NOTIFICATIONS_MONTHLY,nativeQuery = true )
    public List<Object[]> getDriverNotificationsByFuelMonth(@Param("startDate") LocalDateTime startDate,@Param("endDate") LocalDateTime endDate, String busNo);
    
    
    @Query(value =QUERY_FOR_GET_BUS_FUEL_NOTIFICATIONS_YEARLY,nativeQuery = true )
    public List<Object[]> getDriverNotificationsByFuelYear(@Param("startDate") LocalDateTime startDate,@Param("endDate") LocalDateTime endDate, String busNo);

//fuel consumed Ended
    
 /// co2 emission
    
    @Query(value =QUERY_FOR_GET_BUS_CO2_NOTIFICATIONS_DAY,nativeQuery = true )
    public List<Object[]> getDriverNotificationsByCo2Day(@Param("startDate") LocalDateTime startDate,@Param("endDate") LocalDateTime endDate, String busNo);
    
    @Query(value =QUERY_FOR_GET_BUS_CO2_NOTIFICATIONS_WEEKLY,nativeQuery = true )
    public List<Object[]> getDriverNotificationsByCo2Week(@Param("startDate") LocalDateTime startDate,@Param("endDate") LocalDateTime endDate, String busNo);
    
    
    @Query(value =QUERY_FOR_GET_BUS_CO2_NOTIFICATIONS_MONTHLY,nativeQuery = true )
    public List<Object[]> getDriverNotificationsByCo2Month(@Param("startDate") LocalDateTime startDate,@Param("endDate") LocalDateTime endDate, String busNo);
    
    
    @Query(value =QUERY_FOR_GET_BUS_CO2_NOTIFICATIONS_YEARLY,nativeQuery = true )
    public List<Object[]> getDriverNotificationsByCo2Year(@Param("startDate") LocalDateTime startDate,@Param("endDate") LocalDateTime endDate, String busNo);

//co2 emission Ended
    
 /// co2 emission
    
    @Query(value =QUERY_FOR_GET_BUS_EXPENSE_NOTIFICATIONS_DAY,nativeQuery = true )
    public List<Object[]> getDriverNotificationsByExpenseDay(@Param("startDate") LocalDateTime startDate,@Param("endDate") LocalDateTime endDate, String busNo);
    
    @Query(value =QUERY_FOR_GET_BUS_EXPENSE_NOTIFICATIONS_WEEKLY,nativeQuery = true )
    public List<Object[]> getDriverNotificationsByExpenseWeek(@Param("startDate") LocalDateTime startDate,@Param("endDate") LocalDateTime endDate, String busNo);
    
    
    @Query(value =QUERY_FOR_GET_BUS_EXPENSE_NOTIFICATIONS_MONTHLY,nativeQuery = true )
    public List<Object[]> getDriverNotificationsByExpenseMonth(@Param("startDate") LocalDateTime startDate,@Param("endDate") LocalDateTime endDate, String busNo);
    
    
    @Query(value =QUERY_FOR_GET_BUS_EXPENSE_NOTIFICATIONS_YEARLY,nativeQuery = true )
    public List<Object[]> getDriverNotificationsByExpenseYear(@Param("startDate") LocalDateTime startDate,@Param("endDate") LocalDateTime endDate, String busNo);

	

//co2 emission Ended
    
    // Travelling hours start
    @Query(value =QUERY_FOR_GET_BUS_TRAVEL_NOTIFICATIONS_DAY,nativeQuery = true )
    public List<Object[]> getBusNotificationsTravelByDay(@Param("startDate") LocalDateTime startDate,@Param("endDate") LocalDateTime endDate, String busNo);
    
    @Query(value =QUERY_FOR_GET_BUS_TRAVEL_NOTIFICATIONS_WEEKLY,nativeQuery = true )
    public List<Object[]> getBusNotificationsTravelByWeek(@Param("startDate") LocalDateTime startDate,@Param("endDate") LocalDateTime endDate, String busNo);
    
    
    @Query(value =QUERY_FOR_GET_BUS_TRAVEL_NOTIFICATIONS_MONTHLY,nativeQuery = true )
    public List<Object[]> getBusNotificationsTravelByMonth(@Param("startDate") LocalDateTime startDate,@Param("endDate") LocalDateTime endDate, String busNo);
    
    
    @Query(value =QUERY_FOR_GET_BUS_TRAVEL_NOTIFICATIONS_YEARLY,nativeQuery = true )
    public List<Object[]> getBusNotificationsByTravelYear(@Param("startDate") LocalDateTime startDate,@Param("endDate") LocalDateTime endDate, String busNo);
    
 // Travelling hours End
    
    //OverAll hours  avg STATS Start
    
    
    static final String QUERY_FOR_GET_BUS_NOTIFICATIONS_OVERALL  = " SELECT  Avg(ticket_sale_cost) as revenue,  Avg(fuel_consumed) as fuelCost, CAST(Avg(driving_hours) as varchar) as drivingHours , Avg(co2_emission) as emission, Avg(overaloccapancy) as overaloccapancy , "
    		+ " sum(ticket_sale_cost) as Income, sum(distance_Covered) as travelleddistance, sum(fuel_consumed) as fuelConsumed, CAST(sum(idle_time) as varchar) idle_time, sum(co2_emission) as co2Emission, sum(fuel_cost) as Cost, sum(maintenance_cost) as maintenanceCost "
    		+ " from notification_entity "
    		+ " where notification_date>=:startDate and notification_date<=:endDate "
    		+ "  and (is_current_location='Y' or destination=stop_name) ";
    
    
    @Query(value =QUERY_FOR_GET_BUS_NOTIFICATIONS_OVERALL,nativeQuery = true )
    public List<Object[]> getOverAllNotificationsByYear(@Param("startDate") LocalDateTime startDate,@Param("endDate") LocalDateTime endDate);
    
    

    static final String QUERY_FOR_GET_BUS_OVERALL_NOTIFICATIONS_DAY  =" SELECT  date_trunc('hour',notification_date) as week,  sum(ticket_sale_cost) as income, CAST(sum(driving_hours) as varchar) driver , "
    		+ "    		 CAST(sum(idle_time) as varchar) idle_time , "
    		+ "    		 sum(distance_covered) as distanceCovered, sum(co2_emission) as co2Emission, sum(fuel_cost) as FuelCost , sum(maintenance_cost) as maintananceCost "
    		+ "    		 from notification_entity "
    		+ "   where notification_date>=:startDate and notification_date<=:endDate"
    		+ "  and (is_current_location='Y' or destination=stop_name)  GROUP BY date_trunc('hour',notification_date) ";
    
    static final String QUERY_FOR_GET_BUS_OVERALL_NOTIFICATIONS_WEEKLY  =" SELECT  date_trunc('day',notification_date) as week,  sum(ticket_sale_cost) as income, CAST(sum(driving_hours) as varchar) driver , "
    		+ "    		 CAST(sum(idle_time) as varchar) idle_time , "
    		+ "    		 sum(distance_covered) as distanceCovered, sum(co2_emission) as co2Emission, sum(fuel_cost) as FuelCost , sum(maintenance_cost) as maintananceCost "
    		+ "    		 from notification_entity "
    		+ "   where notification_date>=:startDate and notification_date<=:endDate"
    		+ "  and (is_current_location='Y' or destination=stop_name)  GROUP BY date_trunc('day',notification_date) ";
    
    static final String QUERY_FOR_GET_BUS_OVERALL_NOTIFICATIONS_MONTHLY  =" SELECT  date_trunc('week',notification_date) as week,  sum(ticket_sale_cost) as income, CAST(sum(driving_hours) as varchar) driver , "
    		+ "    		 CAST(sum(idle_time) as varchar) idle_time , "
    		+ "    		 sum(distance_covered) as distanceCovered, sum(co2_emission) as co2Emission, sum(fuel_cost) as FuelCost , sum(maintenance_cost) as maintananceCost "
    		+ "    		 from notification_entity "
    		+ "   where notification_date>=:startDate and notification_date<=:endDate"
    		+ "  and (is_current_location='Y' or destination=stop_name)  GROUP BY date_trunc('week',notification_date) ";
    
    static final String QUERY_FOR_GET_BUS_OVERALL_NOTIFICATIONS_YEARLY  = " SELECT  date_trunc('month',notification_date) as week,  sum(ticket_sale_cost) as income, CAST(sum(driving_hours) as varchar) driver , "
    		+ "    		 CAST(sum(idle_time) as varchar) idle_time , "
    		+ "    		 sum(distance_covered) as distanceCovered, sum(co2_emission) as co2Emission, sum(fuel_cost) as FuelCost , sum(maintenance_cost) as maintananceCost "
    		+ "    		 from notification_entity "
    		+ "   where notification_date>=:startDate and notification_date<=:endDate"
    		+ "  and (is_current_location='Y' or destination=stop_name)  GROUP BY date_trunc('month',notification_date) ";
    
    @Query(value =QUERY_FOR_GET_BUS_OVERALL_NOTIFICATIONS_DAY,nativeQuery = true )
    public List<Object[]> getBusNotificationsOverallTravelByDay(@Param("startDate") LocalDateTime startDate,@Param("endDate") LocalDateTime endDate);
    
    @Query(value =QUERY_FOR_GET_BUS_OVERALL_NOTIFICATIONS_WEEKLY,nativeQuery = true )
    public List<Object[]> getBusNotificationsOverallTravelByWeek(@Param("startDate") LocalDateTime startDate,@Param("endDate") LocalDateTime endDate);
    
    
    @Query(value =QUERY_FOR_GET_BUS_OVERALL_NOTIFICATIONS_MONTHLY,nativeQuery = true )
    public List<Object[]> getBusNotificationsOverallTravelByMonth(@Param("startDate") LocalDateTime startDate,@Param("endDate") LocalDateTime endDate);
    
    
    @Query(value =QUERY_FOR_GET_BUS_OVERALL_NOTIFICATIONS_YEARLY,nativeQuery = true )
    public List<Object[]> getBusNotificationsOverallByTravelYear(@Param("startDate") LocalDateTime startDate,@Param("endDate") LocalDateTime endDate);
    
}
