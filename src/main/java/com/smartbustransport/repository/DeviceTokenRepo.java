package com.smartbustransport.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.smartbustransport.entity.DeviceTokenEntity;

@Repository
public interface DeviceTokenRepo extends JpaRepository<DeviceTokenEntity, String> {

	@Query(name = "findDeviceTokenByEmail", value = " select device_token from devicetoken where email_id IN( select user_email from user_info where user_id  = (select user_id from vehicle where vehicle_lpn = :lpn))", nativeQuery = true)
	List<String> findDeviceTokenByEmail(@Param("lpn") String lpn);

	// @Query(value = "SELECT * FROM devicetoken WHERE email_id =:email ",
	// nativeQuery = true)
	@Query(value = "SELECT *  FROM devicetoken WHERE LOWER(email_id)= LOWER(:email)", nativeQuery = true)
	List<DeviceTokenEntity> getDeviceTokens(@Param("email") String email);

	@Query(value = "SELECT * FROM devicetoken  WHERE email_id IN (:email) ", nativeQuery = true)
	List<DeviceTokenEntity> getDeviceTokensForMultipleUsers(@Param("email") List<String> email);

	@Modifying
	@Transactional
	@Query(value = "delete from devicetoken where device_token =:token", nativeQuery = true)
	void deletetoken(@Param("token") String token);

}
