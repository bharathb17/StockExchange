package com.stockExchange.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.stockExchange.data.entity.WatchListEntity;

public interface WatchListRepo extends JpaRepository<WatchListEntity, Long>{
	public List<Optional<WatchListEntity>> findByUsername(String username);
	public Optional<WatchListEntity> findByUsernameAndCompanyName(String username, String companyName);
}
