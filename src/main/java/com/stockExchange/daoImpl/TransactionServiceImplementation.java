package com.stockExchange.daoImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.stockExchange.dao.TransactionService;
import com.stockExchange.data.dto.ResponseDTO;
import com.stockExchange.data.dto.TransactionDTO;
import com.stockExchange.data.entity.TransactionEntity;
import com.stockExchange.repository.TransactionRepo;

@Service
public class TransactionServiceImplementation implements TransactionService {

    @Autowired
    TransactionRepo transactionRepo;

    @Override
    public ResponseDTO findAllTransactions() {
        try {
            List<TransactionEntity> transactions = transactionRepo.findAll();
            return new ResponseDTO(HttpStatus.OK, "Transactions retrieved successfully", transactions);
        } catch (Exception e) {
            return new ResponseDTO(HttpStatus.INTERNAL_SERVER_ERROR, "Error retrieving transactions: " + e.getMessage());
        }
    }

    @Override
    public ResponseDTO addTransactions(TransactionDTO transactionModel) {
        try {
            TransactionEntity transactionEntity = new TransactionEntity(transactionModel);
            transactionRepo.save(transactionEntity);
            return new ResponseDTO(HttpStatus.CREATED, "Transaction added successfully", transactionEntity);
        } catch (Exception e) {
            return new ResponseDTO(HttpStatus.INTERNAL_SERVER_ERROR, "Error adding transaction: " + e.getMessage());
        }
    }

    @Override
    public ResponseDTO getTransactionsByUsername(String username) {
        try {
            List<TransactionEntity> transactions = transactionRepo.findByUsernameOrderByTimeStampDesc(username);
            return new ResponseDTO(HttpStatus.OK, "Transactions retrieved successfully", transactions);
        } catch (Exception e) {
            return new ResponseDTO(HttpStatus.INTERNAL_SERVER_ERROR, "Error retrieving transactions for user: " + e.getMessage());
        }
    }

    @Override
    public ResponseDTO getTodaysTransactions(String date) {
        try {
            List<TransactionEntity> transactions = transactionRepo.findByDateOrderByTimeStamp(date);
            
            if (transactions.isEmpty()) {
                return new ResponseDTO(HttpStatus.OK, "No transactions found for today", transactions);
            }
            
            return new ResponseDTO(HttpStatus.OK, "Today's transactions retrieved successfully", transactions);
        } catch (Exception e) {
            return new ResponseDTO(HttpStatus.INTERNAL_SERVER_ERROR, "Error retrieving today's transactions: " + e.getMessage());
        }
    }

    @Override
    public ResponseDTO getTransactionsByCompany(String company) {
        try {
            List<TransactionEntity> transactions = transactionRepo.findByCompanyName(company);
            return new ResponseDTO(HttpStatus.OK, "Transactions for the company retrieved successfully", transactions);
        } catch (Exception e) {
            return new ResponseDTO(HttpStatus.INTERNAL_SERVER_ERROR, "Error retrieving transactions for the company: " + e.getMessage());
        }
    }
}
