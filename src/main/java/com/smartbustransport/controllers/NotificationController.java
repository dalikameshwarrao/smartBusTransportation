package com.smartbustransport.controllers;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.smartbustransport.dto.NotificationsDTO;
import com.smartbustransport.services.NotificationEntityService;

@RestController
@RequestMapping("/api/smartbustransport")
public class NotificationController {
	
	@Autowired
	private NotificationEntityService notificationEntityService;
	
	@GetMapping("/v1/allnofifications")
	public ResponseEntity<?> getNofifications() {
		NotificationsDTO notifications =notificationEntityService.getNotifications();
	    return new ResponseEntity<>(notifications,HttpStatus.OK);
	}


}
