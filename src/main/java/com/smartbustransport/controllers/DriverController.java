package com.smartbustransport.controllers;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.support.ServletContextResource;

import com.google.common.io.Files;
import com.smartbustransport.dto.DriverAnalyticsDTO;
import com.smartbustransport.dto.DriverOverallAnalyticDTO;
import com.smartbustransport.dto.DriversDTO;
import com.smartbustransport.services.DriverService;

import lombok.var;
import lombok.extern.slf4j.Slf4j;
import springfox.documentation.spring.web.paths.Paths;

@Slf4j
@RestController
@RequestMapping(path = "smartBus")
public class DriverController {
	
	
	@Autowired
	DriverService driverService;
	
	  @GetMapping("/drivers")
	  public ResponseEntity <List<DriversDTO>> getAllTutorials(@RequestParam(required = false) String busNo) {
		  
	      List<DriversDTO> driverDetails = new ArrayList<DriversDTO>();
	      if (busNo == null) {
	    	 driverDetails= driverService.getAllDriversDetailsFromNotiFy(); 
	  } else {
		  return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	  }
	      return new ResponseEntity<>(driverDetails,HttpStatus.OK);  
	  }


	  @GetMapping("/get-image-dynamic-type")
	  public ResponseEntity<InputStreamResource> getImageDynamicType() throws IOException {
	      MediaType contentType = MediaType.IMAGE_PNG;
	      var imgFile = new ClassPathResource("images/2022.11.30.3113.png");
	      InputStream in =imgFile.getInputStream();
	      return ResponseEntity.ok()
	        .contentType(contentType)
	        .body(new InputStreamResource(in));
	  }
	  
	  @GetMapping("/driversanalytics")
	  public ResponseEntity<DriverAnalyticsDTO> getDriverAnalytics()
	  {
		  DriverAnalyticsDTO driverAnalyticsDTO=new DriverAnalyticsDTO();
		  driverAnalyticsDTO.setDriverAnalytics(driverService.getDriverAnalytics());
		  DriverOverallAnalyticDTO driverOverallAnalyticDTO=driverService.getDriverOverallAnalytics();
		  driverAnalyticsDTO.setOverAllAnalytics(driverOverallAnalyticDTO);
		  	
		  return new ResponseEntity<>(driverAnalyticsDTO,HttpStatus.OK);  
	  }


}
