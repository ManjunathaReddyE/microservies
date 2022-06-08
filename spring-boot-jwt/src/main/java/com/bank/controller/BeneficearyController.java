package com.bank.controller;

import com.bank.model.Baccount;
import com.bank.repository.BAccountRepository;
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

	/**
	 * Get all the accounts
	 *
	 * @return ResponseEntity
	 */
	@GetMapping("/baccounts")
	public ResponseEntity<List<Baccount>> getAllBeneficearyAccounts() {
		try {
			return new ResponseEntity<>(baccountRepository.findAll(), HttpStatus.OK);
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
	@GetMapping("/baccount/{accountNumber}")
	public ResponseEntity<Baccount> getBeneficearyAccountByAccNo(@PathVariable("accountNumber") long accountNumber) {
		try {
			// check if account exist in database
			Baccount accObj = getAccountRec(accountNumber);

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
	@PostMapping("/baccount") 
	public ResponseEntity<Baccount> createBeneficearyAccount(@RequestBody Baccount baccount) {

		Baccount newAccount =baccountRepository.save(baccount);

		return new ResponseEntity<>(newAccount, HttpStatus.OK); }


	/**
	 * Update Account record by using it's id
	 *
	 * @param id
	 * @param Account
	 * @return
	 */
	@PutMapping("/baccount/{accountNumber}")
	public ResponseEntity<Baccount> updateAccount(@PathVariable("accountNumber") long accountNumber, @RequestBody Baccount baccount) {

		// check if Account exist in database
		Baccount accObj = getAccountRec(accountNumber);

		if (accObj != null) {
			accObj.setAccountNumber(baccount.getAccountNumber());
			accObj.setAccountType(baccount.getAccountType());
			accObj.setBalance(baccount.getBalance());
			accObj.setBank(baccount.getBank());
			//accObj.setCustomerId(account.getCustomerId());
			
			return new ResponseEntity<>(baccountRepository.save(accObj), HttpStatus.OK);
		}

		return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}

	/**
	 * Delete Account by Id
	 *
	 * @param id
	 * @return ResponseEntity
	 */
	@DeleteMapping("/baccount/{accountNumber}")
	public ResponseEntity<HttpStatus> deleteAccountById(@PathVariable("accountNumber") long accountNumber) {
		try {
			// check if Account exist in database
			Baccount acc = getAccountRec(accountNumber);

			if (acc != null) {
				baccountRepository.deleteById(accountNumber);
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
	@DeleteMapping("/baccounts")
	public ResponseEntity<HttpStatus> deleteAllAccounts() {
		try {
			baccountRepository.deleteAll();
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
	private Baccount getAccountRec(long accountNumber) {
		Optional<Baccount> acccObj = baccountRepository.findById(accountNumber);

		if (acccObj.isPresent()) {
			return acccObj.get();
		}
		return null;
	}

}
