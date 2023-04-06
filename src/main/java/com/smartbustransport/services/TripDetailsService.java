 	package com.smartbustransport.services;

import org.springframework.stereotype.Service;

import com.smartbustransport.dto.TripDetailsDTO;
import com.smartbustransport.dto.TripIDetailsInfo;

@Service
public interface TripDetailsService {
  TripDetailsDTO getTripDetails(TripIDetailsInfo tripIDetailsInfo);
}
