package com.stockExchange.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.stockExchange.data.entity.HoldingEntity;


public interface HoldingRepo extends JpaRepository <HoldingEntity, Integer> {
	public List<HoldingEntity> findByUsername(String userName);
	public Optional<HoldingEntity> findByUsernameAndCompanyName(String userName, String companyName);
}