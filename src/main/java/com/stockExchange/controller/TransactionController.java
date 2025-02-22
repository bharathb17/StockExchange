package com.stockExchange.controller;

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

import com.stockExchange.dao.TransactionService;
import com.stockExchange.data.dto.ResponseDTO;
import com.stockExchange.data.dto.TransactionDTO;

@CrossOrigin("*")
@RestController
@RequestMapping("/transactions")
public class TransactionController {

    @Autowired
    TransactionService transactionService;

    @GetMapping("/getAllTranactions")
    public ResponseEntity<ResponseDTO> getAllTransactions() {
        ResponseDTO response = transactionService.findAllTransactions();
        return new ResponseEntity<>(response, HttpStatus.valueOf(response.getStatusCode()));
    }

    @PostMapping("/addTransactions")
    public ResponseEntity<ResponseDTO> addTransaction(@RequestBody TransactionDTO transactionModel) {
        ResponseDTO response = transactionService.addTransactions(transactionModel);
        return new ResponseEntity<>(response, HttpStatus.valueOf(response.getStatusCode()));
    }

    @PostMapping("/getTransactionsByUser")
    public ResponseEntity<ResponseDTO> getTransactionsByUser(@RequestParam("username") String username) {
        ResponseDTO response = transactionService.getTransactionsByUsername(username);
        return new ResponseEntity<>(response, HttpStatus.valueOf(response.getStatusCode()));
    }

    @PostMapping("/getTodaysTransactions")
    public ResponseEntity<ResponseDTO> getTodaysTransactions(@RequestParam("date") String date) {
    	
//    	SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SS");
//    	Date timestamp;
//    	
//		try {
//			timestamp = dateFormat.parse(timeStamp);
//		} catch (ParseException e) {
//			ResponseDTO error =  new ResponseDTO(HttpStatus.INTERNAL_SERVER_ERROR, "Exception while parsing date " + e.getLocalizedMessage());
//			return new ResponseEntity<>(error, HttpStatus.valueOf(error.getStatusCode()));
//		}
//    	
        ResponseDTO response = transactionService.getTodaysTransactions(date);
        return new ResponseEntity<>(response, HttpStatus.valueOf(response.getStatusCode()));
    }

    @PostMapping("/getTransactionsByCompany")
    public ResponseEntity<ResponseDTO> getTransactionsByCompany(@RequestParam("companyName") String companyName) {
        ResponseDTO response = transactionService.getTransactionsByCompany(companyName);
        return new ResponseEntity<>(response, HttpStatus.valueOf(response.getStatusCode()));
    }
}

