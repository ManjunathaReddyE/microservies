package com.bank.controller;

import com.bank.model.Account;
import com.bank.model.Baccount;
import com.bank.model.TxnDetail;
import com.bank.model.TxnHistory;
import com.bank.repository.AccountRepository;
import com.bank.repository.BAccountRepository;
import com.bank.repository.TransactionRepository;
import com.bank.repository.TxnHistoryRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

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
	
	/**
	 * Get all the accounts
	 *
	 * @return ResponseEntity
	 */
	@GetMapping("/txhistory")
	public ResponseEntity<List<TxnDetail>> getTransactionHistory() {
		try {
			return new ResponseEntity<>(transactionRepository.findAll(), HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	/**
	 * Get the account by id
	 *
	 * @param id
	 * @return ResponseEntity
	 */
	@GetMapping("/transaction/{txnId}")
	public ResponseEntity<TxnDetail> getTransactionDetailsBytxnId(@PathVariable("txnId") long txnId) {
		try {
			// check if account exist in database
			TxnDetail accObj = getAccountRec(txnId);

			if (accObj != null) {
				return new ResponseEntity<>(accObj, HttpStatus.OK);
			}

			return new ResponseEntity<>(HttpStatus.NOT_FOUND);

		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

	/**
	 * Create new account for registered customer
	 *
	 * @param Account
	 * @return ResponseEntity
	 */
	@PostMapping("/transaction/{accountId}") 
	public ResponseEntity<TxnDetail> saveTransaction(@RequestBody TxnDetail txnDetail) {

		
		
		TxnDetail newTxn =transactionRepository.save(txnDetail);
		return new ResponseEntity<>(newTxn, HttpStatus.OK); }


	/**
	 * Update Account record by using it's id
	 *
	 * @param id
	 * @param Account
	 * @return
	 */
	@PostMapping("/fundtransfer")
	public ResponseEntity<TxnDetail> updateAccount(@RequestBody TxnDetail txnDetail) {

		//check balance in the source account
		  RestTemplate restTemplate = new RestTemplate();
		String url="http://localhost:9101/bankapp1/account/"+txnDetail.getAccountId();
		Account sourcAccount=restTemplate.getForObject(url,Account.class);
		
		if(sourcAccount.getBalance()>0)
		{
			System.out.println("source bal:"+sourcAccount.getBalance()+" == "+txnDetail.getBaccount());
			Baccount baccount=new Baccount();
			baccount.setAccountNumber(txnDetail.getBaccount().getAccountNumber());
			baccount.setAccountType(txnDetail.getBaccount().getAccountType());
			baccount.setBalance(txnDetail.getBaccount().getBalance());

			baccrep.save(baccount);
			
			Account accObj=new Account();
			
			accObj.setAccountId(sourcAccount.getAccountId());
			accObj.setAccountType(sourcAccount.getAccountType());
			accObj.setBalance(sourcAccount.getBalance()-baccount.getBalance());
			accObj.setCustomer(sourcAccount.getCustomer());
			
			accRepository.save(accObj);
			
			System.out.println("===acc bal=="+accObj.getBalance());
			java.util.Date date = new java.util.Date();
		    System.out.println(date);
			
			TxnHistory txnhistory=new TxnHistory();
			txnhistory.setAccountId(txnDetail.getAccountId());
			txnhistory.setAccountNumber(txnDetail.getBaccount().getAccountNumber());
			txnhistory.setAmount(txnDetail.getAmount());
			txnhistory.setTxndate(date);
			
			txnHistoryRepository.save(txnhistory);
			

		}
				
		// check if Account exist in database
		//TxnDetail txnObj = getAccountRec(accountId);
		return new ResponseEntity<>(HttpStatus.OK);
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
		try {
			// check if Account exist in database
			TxnDetail txn = getAccountRec(txnId);

			if (txn != null) {
				transactionRepository.deleteById(txnId);
				return new ResponseEntity<>(HttpStatus.OK);
			}

			return new ResponseEntity<>(HttpStatus.NOT_FOUND);

		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	/**
	 * Delete all account
	 *
	 * @return ResponseEntity
	 */
	@DeleteMapping("/transactions")
	public ResponseEntity<HttpStatus> deleteAllAccounts() {
		try {
			transactionRepository.deleteAll();
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
