package com.example.demo.repository;

import com.example.demo.model.User;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * @author ebasanez
 * @since 2021-02-04
 */
public interface UserRepository {

	Flux<User> findAll();

	Mono<User> save(User user);

	Mono<User> findUser(Integer id);

	Mono<Void> deleteUser(Integer id);

}