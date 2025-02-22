package com.stockExchange.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.stockExchange.dao.HoldingService;
import com.stockExchange.data.dto.ResponseDTO;
import com.stockExchange.data.entity.HoldingEntity;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping("/holding")
public class HoldingController {

	@Autowired
	HoldingService holdingService;

	@PostMapping("/getAllHoldingsByUsername")
	public ResponseEntity<ResponseDTO> getAllHoldings(@RequestParam("username") String username) {
		ResponseDTO response = holdingService.getAllHoldingsByUsername(username);
		return new ResponseEntity<>(response, HttpStatus.valueOf(response.getStatusCode()));
	}
	
	@PostMapping("/getHolding")
	public ResponseEntity<ResponseDTO> getHoldings(@RequestParam("username") String username, @RequestParam("companyName") String companyName) {
		ResponseDTO response = holdingService.getHoldingByUserameAndCompanyName(username, companyName);
		return new ResponseEntity<>(response, HttpStatus.valueOf(response.getStatusCode()));
	}

	@PostMapping("/deleteStocks")
	public ResponseEntity<ResponseDTO> deleteHolding(@RequestParam("username") String username, @RequestParam("companyName") String companyName) {
		ResponseDTO response = holdingService.deleteHolding(username, companyName);
		return new ResponseEntity<>(response, HttpStatus.valueOf(response.getStatusCode()));
	}

	@PostMapping("/addNewStocks")
	public ResponseEntity<ResponseDTO> addHolding(@RequestBody HoldingEntity holding) {
		ResponseDTO response = holdingService.addNewHolding(holding);
		return new ResponseEntity<>(response, HttpStatus.valueOf(response.getStatusCode()));
	}
}
