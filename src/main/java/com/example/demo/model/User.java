package com.example.demo.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

/**
 * @author ebasanez
 * @since 2021-02-04
 */
@Data
@Entity
@Table(name = "user")
public class User {

	@Id
	@Column(updatable = false, name = "id", nullable = false)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@Column(name = "username")
	private String username;

	@Column(name = "password")
	private String password;

	@Column(name = "secret")
	private String secret;

	@Column(name = "phone")
	private String phone;

}
