package com.bank.controller;

import com.bank.model.Account;
import com.bank.model.Baccount;
import com.bank.model.TxnDetail;
import com.bank.model.TxnHistory;
import com.bank.repository.AccountRepository;
import com.bank.repository.BAccountRepository;
import com.bank.repository.TransactionRepository;
import com.bank.repository.TxnHistoryRepository;
import com.bank.service.TransactionService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.sql.Date;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

//@CrossOrigin(origins = "http://localhost:8081")
@RestController
public class TransactionController {

	@Autowired
	TransactionRepository transactionRepository;
	
	
	 @Autowired 
	 AccountRepository accRepository;
	 
	
	@Autowired
	BAccountRepository baccrep;
	
	@Autowired
	TxnHistoryRepository txnHistoryRepository;
	
	@Autowired
	TransactionService transactionService;
	
	/**
	 * Get all the accounts
	 *
	 * @return ResponseEntity
	 */
	@GetMapping("/txhistory")
	public ResponseEntity<List<TxnDetail>> getTransactionHistory() {
		return transactionService.getTxnHistory();
	}

	/**
	 * Get the account by id
	 *
	 * @param id
	 * @return ResponseEntity
	 */
	@GetMapping("/transaction/{txnId}")
	public ResponseEntity<TxnDetail> getTransactionDetailsBytxnId(@PathVariable("txnId") long txnId) {
		
		return transactionService.getTxnDetailsBytxnId(txnId);
	}

	/**
	 * Create new account for registered customer
	 *
	 * @param Account
	 * @return ResponseEntity
	 */
	@PostMapping("/transaction/{accountId}") 
	public ResponseEntity<TxnDetail> saveTransaction(@RequestBody TxnDetail txnDetail) {

		return transactionService.saveTxn(txnDetail);
		
	}


	/**
	 * Update Account record by using it's id
	 *
	 * @param id
	 * @param Account
	 * @return
	 */
	@PostMapping("/fundtransfer")
	public ResponseEntity<TxnDetail> updateAccount(@RequestBody TxnDetail txnDetail) {

	
		return transactionService.updateAccountDetails(txnDetail);
	}

	
	public Account getSourceAccountDetail(Long accountId)
	{
		AccountController ac=new AccountController();
		Account sAccount=ac.getAccountRec(accountId);
		return sAccount;
	}
	
	/**
	 * Delete Account by Id
	 *
	 * @param id
	 * @return ResponseEntity
	 */
	@DeleteMapping("/transaction/{txnId}")
	public ResponseEntity<HttpStatus> deleteAccountById(@PathVariable("txnId") long txnId) {
		return transactionService.deleteTxnById(txnId);
	}

	/**
	 * Delete all account
	 *
	 * @return ResponseEntity
	 */
	public ResponseEntity<HttpStatus> deleteAllAccounts() {
		return transactionService.deleteAllTxns();

	}

	/**
	 * Method to get the account record by id
	 *
	 * @param id
	 * @return account
	 */
	private TxnDetail getAccountRec(long txnId) {
		Optional<TxnDetail> acccObj = transactionRepository.findById(txnId);

		if (acccObj.isPresent()) {
			return acccObj.get();
		}
		return null;
	}

	public TransactionRepository getTransactionRepository() {
		return transactionRepository;
	}

	public void setTransactionRepository(TransactionRepository transactionRepository) {
		this.transactionRepository = transactionRepository;
	}

	


}
