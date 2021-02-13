package com.example.demo.service;

import java.util.List;

/**
 * @author ebasanez
 * @since 2021-02-09
 */
public interface ActiveUserService {

	List<String> getAllActiveUsers();

	int countAllActiveUsers();

}
