package com.paymybuddy.api.models;

import jakarta.persistence.*;

//This model represents the entity of the transaction table in the database.

@Entity
@Table(name = "transaction")
public class Transaction {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@Column(nullable = false)
	private String description;
	
	@Column(nullable = false)
	private double amount;
	
	@ManyToOne
	@JoinColumn(name = "sender_id", nullable = false)
	private UserModel sender;

	@ManyToOne
	@JoinColumn(name = "receiver_id", nullable = false)
	private UserModel receiver;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public UserModel getSender() {
		return sender;
	}
	public void setSender(UserModel sender) {
		this.sender = sender;
	}
	public UserModel getReceiver() {
		return receiver;
	}
	public void setReceiver(UserModel receiver) {
		this.receiver = receiver;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public double getAmount() {
		return amount;
	}
	public void setAmount(double amount) {
		this.amount = amount;
	}	
}
