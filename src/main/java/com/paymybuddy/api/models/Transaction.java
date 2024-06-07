package com.paymybuddy.api.models;

import jakarta.persistence.Entity;
import jakarta.persistence.*;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "transaction")
public class Transaction {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)

	private Integer id;

	@ManyToOne
	@JoinColumn(name = "sender_id", nullable = false)
	private UserModel sender;

	@ManyToOne
	@JoinColumn(name = "receiver_id", nullable = false)
	private UserModel receiver;

	@Column
	private String description;
	@Column(nullable = false)
	private double amount;
}
