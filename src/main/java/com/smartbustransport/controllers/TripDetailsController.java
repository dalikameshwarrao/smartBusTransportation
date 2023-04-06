package com.smartbustransport.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.smartbustransport.dto.TripDetailsDTO;
import com.smartbustransport.dto.TripIDetailsInfo;
import com.smartbustransport.services.TripDetailsService;


@RestController
@RequestMapping("/api/smartbustransport")
public class TripDetailsController {

	@Autowired
	private TripDetailsService tripDetailsService;

	@PostMapping("/v1/getTripDetails")
	public ResponseEntity<?> getTripDetailsByUsingBusNoOrDriverOrRoutes( @RequestBody TripIDetailsInfo tripIDetailsInfo) {
		TripDetailsDTO tripDetailsDTO  = new TripDetailsDTO();
		if(tripIDetailsInfo!=null && tripIDetailsInfo.getValue()!=null) {
			tripDetailsDTO = tripDetailsService.getTripDetails(tripIDetailsInfo);
		}
		
		return new ResponseEntity<>(tripDetailsDTO, HttpStatus.OK);
	}
}
