package com.smartbustransport.controllers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.smartbustransport.dto.BusAnalytics;
import com.smartbustransport.dto.BusOverAllAnalytics;
import com.smartbustransport.dto.RouteDTO;
import com.smartbustransport.services.BusAnalyticService;

@RestController
@RequestMapping("/api/smartbustransport")
public class BusAnalyticsController {

	
	@Autowired
	private BusAnalyticService busAnalyticService;
	

	
	@GetMapping("/v1/getBusAnalytics")
	 public ResponseEntity <BusOverAllAnalytics> getBusAnalytics() {
		BusOverAllAnalytics busOverAllAnalytics = new BusOverAllAnalytics();
	       List<BusAnalytics> busAnalytics = busAnalyticService.getbusAnalytics(); 
	       busOverAllAnalytics.setBusAnalytics(busAnalytics);
	       busOverAllAnalytics.setOverAllBusAnalytics(busAnalyticService.getBusOverAllAnalytics());
	      return new ResponseEntity<>(busOverAllAnalytics,HttpStatus.OK);  
	  }
	
	
	
	@GetMapping("/getDirections")
	  public ResponseEntity<String>  getDirections2 (@RequestParam(required = false) String origin, @RequestParam(required = false) String destination) {
		RestTemplate restTemplate = new RestTemplate();
			String apiKey="AIzaSyCmwqbYb48dfmPqYiWWU0A2kRr54I2L3wE";
		  String URL="https://maps.googleapis.com/maps/api/directions/json?origin="+origin+"&destination="+destination+"&key="+apiKey;
		  String response = restTemplate.getForObject(URL,String.class);
		  return new ResponseEntity<>(response,HttpStatus.OK);  
	  }
}
