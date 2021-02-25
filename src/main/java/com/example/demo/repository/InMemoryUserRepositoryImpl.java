package com.example.demo.repository;

import java.time.Instant;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.atomic.AtomicInteger;

import org.springframework.stereotype.Repository;

import com.example.demo.model.User;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * @author ebasanez
 * @since 2021-02-24
 */
@Repository
public class InMemoryUserRepositoryImpl implements UserRepository {


	private static AtomicInteger counter = new AtomicInteger();

	private final ConcurrentMap<Integer, User> users = new ConcurrentHashMap<>();

	@Override
	public Flux<User> findAll() {
		return Flux.fromIterable(this.users.values());
	}

	@Override
	public Mono<User> save(User user) {
		Integer id = user.getId();
		if (id == null) {
			id = counter.incrementAndGet();
			user.setId(id);
		}
		user.setCreated(Instant.now());
		this.users.put(id, user);
		return Mono.just(user);
	}

	@Override
	public Mono<User> findUser(Integer id) {
		return Mono.just(this.users.get(id));
	}

	@Override
	public Mono<Void> deleteUser(Integer id) {
		this.users.remove(id);
		return Mono.empty();
	}
}
