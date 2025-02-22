package com.stockExchange.dao;

import com.stockExchange.data.dto.ResponseDTO;
import com.stockExchange.data.dto.TransactionDTO;

public interface TransactionService {
	
	public ResponseDTO findAllTransactions();
	public ResponseDTO addTransactions(TransactionDTO transaction);
	public ResponseDTO getTransactionsByUsername(String username);
	public ResponseDTO getTodaysTransactions(String date);
	public ResponseDTO getTransactionsByCompany(String company);
	
}
