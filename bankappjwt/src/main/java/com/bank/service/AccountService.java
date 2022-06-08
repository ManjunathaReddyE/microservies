package com.bank.service;

import com.bank.model.Account;
import com.bank.repository.AccountRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

//@CrossOrigin(origins = "http://localhost:8081")
@Service
public class AccountService {

	@Autowired
	AccountRepository accountRepository;

	/**
	 * Get all the accounts
	 *
	 * @return ResponseEntity
	 */
	public ResponseEntity<List<Account>> getAllAccounts() {
		try {
			System.out.println("==service== "+accountRepository.findAll());
			return new ResponseEntity<>(accountRepository.findAll(), HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	
	
	public Account getAccountDetailsById(long id)
	{
			return getAccountRecords(id);
		
	}
	
	/**
	 * Create new account for registered customer
	 *
	 * @param Account
	 * @return ResponseEntity
	 */
	public ResponseEntity<Account> createAccountForCustomer(Account account) {

		Account newAccount =accountRepository.save(account);

		return new ResponseEntity<>(newAccount, HttpStatus.OK); }


	/**
	 * Update Account record by using it's id
	 *
	 * @param id
	 * @param Account
	 * @return
	 */
	public ResponseEntity<Account> updateAccountDetails(long id, Account account) {

		// check if Account exist in database
		Account accObj = getAccountRecords(id);

		if (accObj != null) {
			accObj.setAccountId(account.getAccountId());
			accObj.setAccountType(account.getAccountType());
			accObj.setBalance(account.getBalance());
			accObj.setBank(account.getBank());
			//accObj.setCustomerId(account.getCustomerId());
			
			return new ResponseEntity<>(accountRepository.save(accObj), HttpStatus.OK);
		}

		return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}

	/**
	 * Delete Account by Id
	 *
	 * @param id
	 * @return ResponseEntity
	 */
	public ResponseEntity<HttpStatus> deleteAccountById(@PathVariable("id") long id) {
		try {
			// check if Account exist in database
			Account acc = getAccountRecords(id);

			if (acc != null) {
				accountRepository.deleteById(id);
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
	public ResponseEntity<HttpStatus> deleteAllAccounts() {
		try {
			accountRepository.deleteAll();
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
	public Account getAccountRecords(long id) {
		Optional<Account> acccObj = accountRepository.findById(id);

		if (acccObj.isPresent()) {
			return acccObj.get();
		}
		return null;
	}

}
