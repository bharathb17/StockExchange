package com.stockExchange.data.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.time.LocalDate;

@Entity
@Table(name = "company_info")
public class StockEntity {

	@Id
	private String companyName;

	@Column
	private String symbol;

	@Column
	private Double sharePrice;

	@Column
	private Long numberOfShares;

	@Column
	private Double changePercentage;

	@Column
	private Long volumeOfTrade;

	@Column
	private String description;

	@Column
	private LocalDate date;

	public StockEntity(String companyName, String symbol, Double sharePrice, Long numberOfShares, String description) {
		this.companyName = companyName;
		this.symbol = symbol;
		this.sharePrice = sharePrice;
		this.numberOfShares = numberOfShares;
		this.description = description;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public String getSymbol() {
		return symbol;
	}

	public void setSymbol(String symbol) {
		this.symbol = symbol;
	}

	public Double getSharePrice() {
		return sharePrice;
	}

	public void setSharePrice(Double sharePrice) {
		this.sharePrice = sharePrice;
	}

	public Long getNumberOfShares() {
		return numberOfShares;
	}

	public void setNumberOfShares(Long numberOfShares) {
		this.numberOfShares = numberOfShares;
	}

	public Double getChangePercentage() {
		return changePercentage;
	}

	public void setChangePercentage(Double changePercentage) {
		this.changePercentage = changePercentage;
	}

	public Long getVolumeOfTrade() {
		return volumeOfTrade;
	}

	public void setVolumeOfTrade(Long volumeOfTrade) {
		this.volumeOfTrade = volumeOfTrade;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public LocalDate getDate() {
		return date;
	}

	public void setDate(LocalDate date) {
		this.date = date;
	}

	public StockEntity() {
	}
}
