package com.smartbustransport.controllers;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.smartbustransport.dto.ActiveAndCompleteRouteDTO;
import com.smartbustransport.services.NotificationEntityService;

@RestController
@RequestMapping("/api/smartbustransport")
public class RouteController {
	
	@Autowired
	private NotificationEntityService notificationEntityService;
	
	
	@GetMapping("/v1/getActiveAndCompleteRoutes")
	public ResponseEntity<?> getRoutes() {
		ActiveAndCompleteRouteDTO activeAndCompleteRouteDTO =notificationEntityService.getActiveAndCompleteRoutes();
	    return new ResponseEntity<>(activeAndCompleteRouteDTO,HttpStatus.OK);
	}
	
	

}
