package com.smartbustransport.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.smartbustransport.entity.DriverEntity;

@Repository
public interface DriverDetailsRepository extends JpaRepository<DriverEntity, Long>{

	DriverEntity getByDriverId(Long driveId);

}
