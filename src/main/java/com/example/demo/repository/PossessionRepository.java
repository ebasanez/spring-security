package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.model.Possession;

/**
 * @author ebasanez
 * @since 2021-02-13
 */
@Repository
public interface PossessionRepository extends JpaRepository<Possession, Integer> {
}
