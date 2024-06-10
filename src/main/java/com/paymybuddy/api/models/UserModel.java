package com.paymybuddy.api.models;

import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.*;

@Entity
@Table(name = "user")
public class UserModel {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;
	@Column(nullable = false)
	private String username;
	@Column(nullable = false, unique = true)
	private String email;
	@Column(nullable = false)
	private String password;

	@ManyToMany
	@JoinTable(name = "Connection", 
	joinColumns = @JoinColumn(name = "user_id"), 
	inverseJoinColumns = @JoinColumn(name = "connection_id"))
	private Set<UserModel> connections = new HashSet<>();

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Set<UserModel> getConnections() {
		return connections;
	}

	public void setConnections(Set<UserModel> connections) {
		this.connections = connections;
	}
	
	public UserModel() {};

	public UserModel(String username, String email, String password, Set<UserModel> connections) {
		super();
		this.username = username;
		this.email = email;
		this.password = password;
		this.connections = connections;
	}
	
	
	
}