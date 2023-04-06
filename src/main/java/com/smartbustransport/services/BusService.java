
package com.smartbustransport.services;
import java.util.List;
import com.smartbustransport.dto.BusesDTO;
import com.smartbustransport.dto.RouteDTO;
import com.smartbustransport.entity.IotDetails;

public interface BusService {
	
	public List<BusesDTO> getAllBusesDetails();
	public List<RouteDTO> getAllBusesDetailsFromNotification();
	public List<IotDetails> getAllTrackerDetails();

}
