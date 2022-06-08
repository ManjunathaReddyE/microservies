package com.bank.controller.test;

import com.bank.model.Account;
import com.bank.model.Customer;
import com.bank.repository.AccountRepository;
import com.bank.service.AccountService;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.List;

//@CrossOrigin(origins = "http://localhost:8081")
@RestController
public class AccountControllerTest {

	@Mock
	AccountRepository accountRepository;
	
	@InjectMocks
	AccountService accountService;


	@Test
	public void getAllAccountsTestPositive() {
		
		List<Account> ListOfAccounts= new ResponseEntity<>(accountRepository.findAll(), HttpStatus.OK).getBody();
		
		System.out.println(" ListOfAccounts "+ListOfAccounts);
		Assert.notEmpty(ListOfAccounts);
		
	}	

	@Test
	public void getAccountByIdTestPositive() {
		Account acc=new Account();
		acc.setAccountId((long)1111);
		acc.setAccountType("saving");
		acc.setBalance(87.99);
		acc.setBank("icici");
		acc.setCustomer(new Customer());
		Account accObj = getAccountRec(acc.getAccountId());
		
	Assert.notNull(accObj);
	Assert.notNull(accObj.getAccountType());

	}
	
	@Test
	public void generateAccountForCustomerTestPositive() {

		 accountService.createAccountForCustomer(account);
		
	}


	@Test
	public void updateAccountTestPositive() {

		return accountService.updateAccountDetails(id, account);
	}

	@Test
	public void deleteAccountById(@PathVariable("id") long id) {
		accountService.deleteAccountById(id);
	}

	@Test
	public void deleteAllAccounts() {
		
		 accountService.deleteAllAccounts();

	}

	@Test
	public void getAccountRec(long id) {
		
		 accountService.getAccountRecords(id);
	}

}
