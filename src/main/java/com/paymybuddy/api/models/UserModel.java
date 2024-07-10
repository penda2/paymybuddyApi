package com.paymybuddy.api.models;

import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

// This model represents the entity of the user table in the database.

@Entity
@Table(name = "user")
public class UserModel {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@Column(nullable = false)
	@Size(min = 2, max = 31)
	private String username;

	@Column(nullable = false, unique = true)
	@Email(message = "Email should be valid")
	private String email;

	@Column(nullable = false)
	@Pattern(regexp = "^(?=.*[a-zA-Z])(?=.*\\d).{8,}$", message = "Le mot de passe doit contenir au moins une lettre, un chiffre et une longueur minimale de 8 caractères")
	private String password;

	@Column(nullable = false)
	private double balance = 300.0;

	@Column(name = "bank_account_number", nullable = true)
	private String bankAccountNumber;

	@Column(name = "bank_routing_number", nullable = true)
	private String bankRoutingNumber;

	@ManyToMany
	@JoinTable(name = "connection", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "connection_id"))
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

	public double getBalance() {
		return balance;
	}

	public void setBalance(double balance) {
		this.balance = balance;
	}

	public String getBankAccountNumber() {
		return bankAccountNumber;
	}

	public void setBankAccountNumber(String bankAccountNumber) {
		this.bankAccountNumber = bankAccountNumber;
	}

	public String getBankRoutingNumber() {
		return bankRoutingNumber;
	}

	public void setBankRoutingNumber(String bankRoutingNumber) {
		this.bankRoutingNumber = bankRoutingNumber;
	}

	public Set<UserModel> getConnections() {
		return connections;
	}

	public void setConnections(Set<UserModel> connections) {
		this.connections = connections;
	}

	public UserModel() {
	};

	public UserModel(String username, String email, String password, Set<UserModel> connections) {
		super();
		this.username = username;
		this.email = email;
		this.password = password;
		this.connections = connections;
	}
}