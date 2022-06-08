package com.bank.controller;

import com.bank.model.Baccount;
import com.bank.repository.BAccountRepository;
import com.bank.service.BeneficearyService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

//@CrossOrigin(origins = "http://localhost:8081")
@RestController
public class BeneficearyController {

	@Autowired
	BAccountRepository baccountRepository;
	
	@Autowired
	BeneficearyService beneficearyService;

	/**
	 * Get all the accounts
	 *
	 * @return ResponseEntity
	 */
	@GetMapping("/baccounts")
	public ResponseEntity<List<Baccount>> getAllBeneficearyAccounts() {
		return beneficearyService.getAllBAccounts();
	}

	/**
	 * Get the account by id
	 *
	 * @param id
	 * @return ResponseEntity
	 */
	@GetMapping("/baccount/{accountNumber}")
	public ResponseEntity<Baccount> getBeneficearyAccountByAccNo(@PathVariable("accountNumber") long accountNumber) {
		return beneficearyService.getBAccountByAccNo(accountNumber);

	}

	/**
	 * Create new account for registered customer
	 *
	 * @param Account
	 * @return ResponseEntity
	 */
	@PostMapping("/baccount") 
	public ResponseEntity<Baccount> createBeneficearyAccount(@RequestBody Baccount baccount) {

		return beneficearyService.createBAccount(baccount);
	}

	/**
	 * Update Account record by using it's id
	 *
	 * @param id
	 * @param Account
	 * @return
	 */
	@PutMapping("/baccount/{accountNumber}")
	public ResponseEntity<Baccount> updateAccount(@PathVariable("accountNumber") long accountNumber, @RequestBody Baccount baccount) {

		return beneficearyService.updateBAccount(accountNumber, baccount);
	}

	/**
	 * Delete Account by Id
	 *
	 * @param id
	 * @return ResponseEntity
	 */
	@DeleteMapping("/baccount/{accountNumber}")
	public ResponseEntity<HttpStatus> deleteAccountById(@PathVariable("accountNumber") long accountNumber) {
		return beneficearyService.deleteAccountById(accountNumber);
	}

	/**
	 * Delete all account
	 *
	 * @return ResponseEntity
	 */
	@DeleteMapping("/baccounts")
	public ResponseEntity<HttpStatus> deleteAllAccounts() {
		return beneficearyService.deleteAllAccounts();

	}

	
}
