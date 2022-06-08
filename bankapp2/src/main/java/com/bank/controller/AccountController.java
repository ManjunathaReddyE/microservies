package com.bank.controller;

import com.bank.model.Account;
import com.bank.repository.AccountRepository;
import com.bank.service.AccountService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

//@CrossOrigin(origins = "http://localhost:8081")
@RestController
public class AccountController {

	@Autowired
	AccountRepository accountRepository;
	
	@Autowired
	AccountService accountService;

	/**
	 * Get all the accounts
	 *
	 * @return ResponseEntity
	 */
	@GetMapping("/accounts")
	public ResponseEntity<List<Account>> getAccounts() {
		
		return accountService.getAllAccounts();
		
		}
	

	/**
	 * Get the account by id
	 *
	 * @param id
	 * @return ResponseEntity
	 */
	@GetMapping("/account/{id}")
	public ResponseEntity<Account> getAccountById(@PathVariable("id") long id) {
		try {
			// check if account exist in database
			Account accObj = getAccountRec(id);

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
	@PostMapping("/account") 
	public ResponseEntity<Account> generateAccountForCustomer(@RequestBody Account account) {

		return accountService.createAccountForCustomer(account);
		
	}


	/**
	 * Update Account record by using it's id
	 *
	 * @param id
	 * @param Account
	 * @return
	 */
	@PutMapping("/account/{id}")
	public ResponseEntity<Account> updateAccount(@PathVariable("id") long id, @RequestBody Account account) {

		return accountService.updateAccountDetails(id, account);
	}

	/**
	 * Delete Account by Id
	 *
	 * @param id
	 * @return ResponseEntity
	 */
	@DeleteMapping("/account/{id}")
	public ResponseEntity<HttpStatus> deleteAccountById(@PathVariable("id") long id) {
		return accountService.deleteAccountById(id);
	}

	/**
	 * Delete all account
	 *
	 * @return ResponseEntity
	 */
	@DeleteMapping("/accounts")
	public ResponseEntity<HttpStatus> deleteAllAccounts() {
		
		return accountService.deleteAllAccounts();

	}

	/**
	 * Method to get the account record by id
	 *
	 * @param id
	 * @return account
	 */
	public Account getAccountRec(long id) {
		
		return accountService.getAccountRecords(id);
	}

}
