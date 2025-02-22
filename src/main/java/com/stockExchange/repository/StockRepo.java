package com.stockExchange.repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.stockExchange.data.entity.StockEntity;

public interface StockRepo extends JpaRepository<StockEntity, String>{

	public Optional<StockEntity> findByCompanyNameAndDate(String companyName, LocalDate date);
	
	@Query("SELECT s.companyName FROM StockEntity s WHERE s.date = :date")
	public List<String> findCompanyNameByDate(@Param("date") LocalDate date);
	
	public List<StockEntity> findByDate(LocalDate date);
}
