package com.bank.controller;


import com.bank.model.Account;
import com.bank.model.TxnHistory;
import com.bank.repository.AccountRepository;
import com.bank.repository.BAccountRepository;
import com.bank.repository.TransactionRepository;
import com.bank.repository.TxnHistoryRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@RestController
public class HistoryController {

	@Autowired
	TxnHistoryRepository txnHistoryRepository;
	
	
	 @Autowired 
	 AccountRepository accRepository;
	 
	
	@Autowired
	BAccountRepository baccrep;
	
	/**
	 * Get all the txnhistory
	 *
	 * @return ResponseEntity
	 */
	@GetMapping("/txnhistory")
	public ResponseEntity<List<TxnHistory>> getTxnHistory() {
		try {
			return new ResponseEntity<>(txnHistoryRepository.findAll(), HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	
	@PostMapping("/history") 
	public ResponseEntity<TxnHistory> saveTxnHistory(@RequestBody TxnHistory txnHistory) {

		TxnHistory newAccount =txnHistoryRepository.save(txnHistory);

		return new ResponseEntity<>(newAccount, HttpStatus.OK); }

	/**
	 * Get the txnhistory by id
	 *
	 * @param id
	 * @return ResponseEntity
	 */
	@GetMapping("/txnhistory/{txnId}")
	public ResponseEntity<TxnHistory> getTransactionDetailsBytxnId(@PathVariable("txnId") long txnId) {
		try {
			// check if account exist in database
			TxnHistory accObj = getAccountRec(txnId);

			if (accObj != null) {
				return new ResponseEntity<>(accObj, HttpStatus.OK);
			}

			return new ResponseEntity<>(HttpStatus.NOT_FOUND);

		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

	/**
	 * Delete all account
	 *
	 * @return ResponseEntity
	 */
	@DeleteMapping("/txnhistory")
	public ResponseEntity<HttpStatus> deleteAllAccounts() {
		try {
			txnHistoryRepository.deleteAll();
			return new ResponseEntity<>(HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

	/**
	 * Method to get the account record by id
	 *
	 * @param id
	 * @return account
	 */
	private TxnHistory getAccountRec(long txnId) {
		Optional<TxnHistory> acccObj = txnHistoryRepository.findById(txnId);

		if (acccObj.isPresent()) {
			return acccObj.get();
		}
		return null;
	}

	

	


}
