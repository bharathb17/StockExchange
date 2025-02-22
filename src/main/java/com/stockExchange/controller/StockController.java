	package com.stockExchange.controller;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.stockExchange.dao.StockService;
import com.stockExchange.data.dto.ResponseDTO;
import com.stockExchange.data.entity.StockEntity;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping("/stocks")
public class StockController {

	@Autowired
	StockService stockService;

    @PostMapping("/getCompany")
    public ResponseEntity<ResponseDTO> findByCompanyByDate(@RequestParam("companyName") String companyName) {
        
    	ResponseDTO response = stockService.findBycompanyNameAndDate(companyName, LocalDate.now());
        return new ResponseEntity<>(response, HttpStatus.valueOf(response.getStatusCode()));
    }
    
    @GetMapping("/getCompaniesByDate")
    public ResponseEntity<ResponseDTO> findAllCompanyByDate() {
        ResponseDTO response = stockService.findAllcompanyByDate(LocalDate.now());
        return new ResponseEntity<>(response, HttpStatus.valueOf(response.getStatusCode()));
    }
    
    @PostMapping("/add")
    public ResponseEntity<ResponseDTO> addStock(@RequestBody StockEntity stock) {
        ResponseDTO response = stockService.addStock(stock);
        return new ResponseEntity<>(response, HttpStatus.valueOf(response.getStatusCode()));
    }
}
