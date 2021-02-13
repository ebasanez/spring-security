package com.example.demo.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.example.demo.service.IEntity;

import lombok.Getter;
import lombok.Setter;

/**
 * @author ebasanez
 * @since 2021-02-13
 */
@Getter
@Setter
@Entity
@Table(name = "possession")
public class Possession implements IEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	private String name;

	@ManyToOne
	@JoinColumn(name="owner_id", nullable = false)
	private User owner;

	@Override
	public String toString() {
		return "Possession{" +
				"id=" + id +
				", name='" + name + '\'' +
				'}';
	}
}
