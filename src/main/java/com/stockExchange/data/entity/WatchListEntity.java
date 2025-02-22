package com.stockExchange.data.entity;

import com.stockExchange.data.dto.WatchListDTO;
import jakarta.persistence.*;

@Entity
@Table(name = "watchlist_table")
public class WatchListEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column
	private String username;

	@Column
	private String companyName;

	public WatchListEntity() {
	}

	public WatchListEntity(WatchListDTO watch) {
		this.username = watch.getUsername();
		this.companyName = watch.getCompanyName();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
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
}
