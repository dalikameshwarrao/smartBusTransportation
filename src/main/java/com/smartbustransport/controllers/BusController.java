package com.smartbustransport.controllers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.smartbustransport.dto.BusesDTO;
import com.smartbustransport.dto.RouteDTO;
import com.smartbustransport.entity.IotDetails;
import com.smartbustransport.services.BusService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping(path = "smartBus")
public class BusController {
	
	
	@Autowired
	BusService busService;
	
	  @GetMapping("/buses")
	  public ResponseEntity <List<RouteDTO>> getAllTutorials(@RequestParam(required = false) String busNo) {
		  
	      List<RouteDTO> busDetails = new ArrayList<RouteDTO>();
	      if (busNo == null) {
	    	 busDetails= busService.getAllBusesDetailsFromNotification(); 
	  } else {
		  return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	  }
	      return new ResponseEntity<>(busDetails,HttpStatus.OK);  
	  }
	  
	  @GetMapping("/busObjectDetails")
	  public ResponseEntity <List<IotDetails>> getBusObjectDetails(@RequestParam(required = false) String busNo) {
		  
	      List<IotDetails> busDetails = new ArrayList<>();
	      if (busNo == null) {
	    	 busDetails= busService.getAllTrackerDetails(); 
	  } else {
		  return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	  }
	      return new ResponseEntity<>(busDetails,HttpStatus.OK);  
	  }
}
