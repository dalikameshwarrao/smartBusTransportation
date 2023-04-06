package com.smartbustransport.services;



import java.util.List;

import org.springframework.stereotype.Service;

import com.smartbustransport.dto.ActiveAndCompleteRouteDTO;
import com.smartbustransport.dto.DriversDTO;
/**
 * 
 * @author divyaj
 *
 */
import com.smartbustransport.dto.NotificationsDTO;
@Service
public interface NotificationEntityService {

	 ActiveAndCompleteRouteDTO getActiveAndCompleteRoutes();
	 public List<DriversDTO> getAllDriversDetailsFromNotiFy();
	 NotificationsDTO getNotifications();

}
