package es.basa.security.server.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import es.basa.security.server.model.User;

/**
 * @author ebasanez
 * @since 2021-02-18
 */
public interface UserRepository extends JpaRepository<User, Integer> {

	User findByUsername(String username);

}
