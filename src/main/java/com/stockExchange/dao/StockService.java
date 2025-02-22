package com.stockExchange.dao;

import java.time.LocalDate;

import com.stockExchange.data.dto.ResponseDTO;
import com.stockExchange.data.entity.StockEntity;

public interface StockService{

//	public ResponseDTO findBycompanyName(String companyName);
//	public ResponseDTO findAllStocks();
	public ResponseDTO addStock(StockEntity stock);
	public ResponseDTO findBycompanyNameAndDate(String companyName, LocalDate date);
	public ResponseDTO findAllcompanyByDate(LocalDate date);
}
