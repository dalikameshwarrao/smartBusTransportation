package com.smartbustransport.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import com.fasterxml.jackson.annotation.JsonIgnore;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import lombok.Data;

@Entity
@Table(name = "user_details")
@Data
public class UserDetailEntity implements Serializable {

	

	/**
	 * 
	 */
	private static final long serialVersionUID = 7510725959319299387L;

	@Id
	@SequenceGenerator(name = "User_id_seq", sequenceName = "User_id_seq", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "User_id_seq")
	private Long userId;

	private String userName;
	@JsonIgnore
	private String password;
	@JsonIgnore
	private byte[] salt;

	private String firstName;

	private String lastName;

	private String phoneNumber;

	private String currentRoleType;

	@ElementCollection
	private List<String> roles = new ArrayList<>();

	@Temporal(TemporalType.TIMESTAMP)
	private Date createdAt;

}
