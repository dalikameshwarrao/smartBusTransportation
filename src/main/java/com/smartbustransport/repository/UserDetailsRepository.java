package com.smartbustransport.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.smartbustransport.entity.UserDetailEntity;

@Repository
public interface UserDetailsRepository extends JpaRepository<UserDetailEntity, Long> {

	@Query(value = " SELECT * FROM user_details ud WHERE ud.email = :email ", nativeQuery = true)
	UserDetailEntity findByEmailId(@Param(value = "email") String email);

	@Query(value = "SELECT *  FROM user_details WHERE LOWER(user_name)= LOWER(:email)", nativeQuery = true)
	UserDetailEntity findByUserName(@Param(value = "email") String email);

	@Query(value = "SELECT t.first_name FROM user_details t WHERE t.user_id = :userId", nativeQuery = true)
	String findNameById(@Param("userId") long userId);

	@Query(value = "SELECT * FROM user_details  WHERE user_id IN (:userId) ", nativeQuery = true)
	List<UserDetailEntity> findDifferentToken(@Param("userId") List<Long> userId);

	@Query(value = "select * from user_details ud where ud.user_name =:username limit 1", nativeQuery = true)
	UserDetailEntity findByUserId(@Param(value = "username") String username);

}
