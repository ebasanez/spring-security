package es.basa.security.server.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

/**
 * @author ebasanez
 * @since 2021-02-17
 */
@Entity
@Getter
@Setter
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

}
