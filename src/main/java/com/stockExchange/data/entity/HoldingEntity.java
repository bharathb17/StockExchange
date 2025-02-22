package com.stockExchange.data.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "holding_table")
public class HoldingEntity {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@Column(nullable = false)
	private String username;

	@Column(nullable = false)
	private String companyName;

	@Column(nullable = false)
	private int quantity;
	
	@Column(nullable = false)
	private double currentValue;

	@Column(nullable = false)
	private double investedValue;

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

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public double getCurrentValue() {
		return currentValue;
	}

	public void setCurrentValue(double currentValue) {
		this.currentValue = currentValue;
	}

	public double getInvestedValue() {
		return investedValue;
	}

	public void setInvestedValue(double investedValue) {
		this.investedValue = investedValue;
	}

	public HoldingEntity() {
	}

	public HoldingEntity(Integer id, String username, String companyName, int quantity, double currentValue, double investedValue) {
		this.id = id;
		this.username = username;
		this.companyName = companyName;
		this.quantity = quantity;
		this.currentValue = currentValue;
		this.investedValue = investedValue;
	}
}
