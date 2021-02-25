package com.example.demo.model;

import java.time.Instant;
import java.util.Date;

import lombok.Data;

/**
 * @author ebasanez
 * @since 2021-02-04
 */
@Data
public class User {

	private Integer id;

	private String username;

	private String password;

	private Instant created;

}
