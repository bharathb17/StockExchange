package com.stockExchange.daoImpl;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.stockExchange.dao.StockService;
import com.stockExchange.data.dto.ResponseDTO;
import com.stockExchange.data.entity.StockEntity;
import com.stockExchange.repository.StockRepo;

@Service
public class StockServiceImplementation implements StockService {

	@Autowired
	StockRepo stockRepository;

	@Override
	public ResponseDTO addStock(StockEntity stockEntity) {
		if (stockEntity.getCompanyName().isBlank() || stockEntity.getSymbol().isBlank()
				|| stockEntity.getSharePrice() <= 0 || stockEntity.getNumberOfShares() <= 0) {
			return new ResponseDTO(HttpStatus.BAD_REQUEST, "Some stock data is missing or invalid");
		}

		try {
			stockRepository.save(stockEntity);
		} catch (Exception e) {
			return new ResponseDTO(HttpStatus.INTERNAL_SERVER_ERROR, "Error adding stock", null);
		}

		return new ResponseDTO(HttpStatus.CREATED, "Stock added successfully", stockEntity);
	}

	@Override
	public ResponseDTO findBycompanyNameAndDate(String companyName, LocalDate date) {

		if (companyName.isBlank()) {
			return new ResponseDTO(HttpStatus.BAD_REQUEST, "Company Name should not be null");
		}

		if (date == null) {
			return new ResponseDTO(HttpStatus.BAD_REQUEST, "Date should not be null");
		}

		Optional<StockEntity> stockData = stockRepository.findByCompanyNameAndDate(companyName, date);
		if (!stockData.isPresent()) {
			return new ResponseDTO(HttpStatus.NOT_FOUND, "Company not found");
		}

		return new ResponseDTO(HttpStatus.OK, "Company found", stockData.get());
	}

	@Override
	public ResponseDTO findAllcompanyByDate(LocalDate date) {
		
		if (date == null) {
			return new ResponseDTO(HttpStatus.BAD_REQUEST, "Date should not be null");
		}

		List<StockEntity> stockData = stockRepository.findByDate(date);
		if (stockData.isEmpty()) {
			return new ResponseDTO(HttpStatus.NOT_FOUND, "Company not found");
		}

		return new ResponseDTO(HttpStatus.OK, "Company found", stockData);
	}

}
