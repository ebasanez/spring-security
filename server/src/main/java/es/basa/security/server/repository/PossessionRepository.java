package es.basa.security.server.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import es.basa.security.server.model.Possession;

/**
 * @author ebasanez
 * @since 2021-02-17
 */
public interface PossessionRepository extends JpaRepository<Possession, Integer> {
}
