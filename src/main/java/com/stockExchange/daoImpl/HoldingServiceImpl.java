package com.stockExchange.daoImpl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.stockExchange.dao.HoldingService;
import com.stockExchange.data.dto.ResponseDTO;
import com.stockExchange.data.entity.HoldingEntity;
import com.stockExchange.repository.HoldingRepo;

@Service
public class HoldingServiceImpl implements HoldingService {

	@Autowired
	HoldingRepo holdingRepo;

	@Override
	public ResponseDTO addNewHolding(HoldingEntity holding) {
		
		if (holding.getUsername().isBlank() || holding.getCompanyName().isBlank() || holding.getQuantity() <= 0
				|| holding.getCurrentValue() <= 0 || holding.getInvestedValue() <= 0) {
			return new ResponseDTO(HttpStatus.BAD_REQUEST, "should not be empty");
		}
		
		holdingRepo.save(holding);
		return new ResponseDTO(HttpStatus.OK, "Holding added successful", holding);

	}

	@Override
	public ResponseDTO getAllHoldingsByUsername(String username) {

		List<HoldingEntity> holdings = holdingRepo.findByUsername(username);

		if (holdings.isEmpty()) {
			return new ResponseDTO(HttpStatus.NOT_FOUND, "No holdings found for username: " + username);
		}

		return new ResponseDTO(HttpStatus.OK, "Holdings fetched successfully", holdings);
	}

	@Override
	public ResponseDTO deleteHolding(String username, String companyName) {

		Optional<HoldingEntity> stocksToDelete = holdingRepo.findByUsernameAndCompanyName(username, companyName);

		if (stocksToDelete.isEmpty()) {
			return new ResponseDTO(HttpStatus.NOT_FOUND,
					"Holding not found or does not belong to username: " + username);
		}

		holdingRepo.delete(stocksToDelete.get());
		return new ResponseDTO(HttpStatus.OK, "Holding deleted successfully");
	}

	@Override
	public ResponseDTO getHoldingByUserameAndCompanyName(String username, String companyName) {
		
		Optional<HoldingEntity> stock = holdingRepo.findByUsernameAndCompanyName(username, companyName);

		if (stock.isEmpty()) {
			return new ResponseDTO(HttpStatus.NOT_FOUND,
					"Holding not found or does not belong to username: " + username);
		}

		return new ResponseDTO(HttpStatus.OK, "Holding fetched successfully",stock.get());
	}

}
