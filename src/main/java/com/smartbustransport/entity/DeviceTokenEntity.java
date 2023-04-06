package com.smartbustransport.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Entity
@Table(name = "devicetoken", indexes = { @Index(name = "devicetoken_index", columnList = "device_token") })
@Data
public class DeviceTokenEntity implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8608537408117405537L;
	
	@Id
	@JsonProperty("deviceToken")
	@Column(name = "device_token", unique = true)
	private String deviceToken;

	@Column(name = "email_id",nullable = false)
	private String emailId;

}
