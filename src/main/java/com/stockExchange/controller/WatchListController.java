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

import com.stockExchange.dao.WatchListService;
import com.stockExchange.data.dto.ResponseDTO;
import com.stockExchange.data.dto.WatchListDTO;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping("/watchList")
public class WatchListController {

	@Autowired
	WatchListService watchlistservice;

	@PostMapping("/getAllWatchList")
	private ResponseEntity<ResponseDTO> getAllUserWatchList(@RequestParam String username) {
		
		ResponseDTO response = watchlistservice.getAllUserWatchList(username);
		return new ResponseEntity<ResponseDTO>(response, HttpStatus.valueOf(response.getStatusCode()));
	}

	@PostMapping("/addCompany")
	private ResponseEntity<ResponseDTO> addCompany(@RequestBody WatchListDTO watchlist) {

		ResponseDTO response = watchlistservice.AddWatchList(watchlist);
		return new ResponseEntity<ResponseDTO>(response, HttpStatus.valueOf(response.getStatusCode()));
	}

	@PostMapping("/deleteCompany")
	private ResponseEntity<ResponseDTO> deleteCompanydeleteCompany(@RequestBody WatchListDTO watchlist) {

		ResponseDTO response = watchlistservice.DeleteWatchList(watchlist);
		return new ResponseEntity<ResponseDTO>(response, HttpStatus.valueOf(response.getStatusCode()));
	}
}
