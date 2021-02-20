package com.example.demo.repository;

import javax.validation.constraints.NotNull;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.model.User;

/**
 * @author ebasanez
 * @since 2021-02-04
 */
@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

	public User findByUsername(
			@NotNull String username);

}
