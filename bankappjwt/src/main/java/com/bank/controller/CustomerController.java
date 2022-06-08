package com.bank.controller;

import com.bank.model.Customer;
import com.bank.repository.CustomerRepository;
import com.bank.service.CustomerService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

//@CrossOrigin(origins = "http://localhost:8081")
@RestController
public class CustomerController {

	@Autowired
	CustomerRepository customerRepository;
	
	@Autowired
	CustomerService customerService;

	/**
	 * Get all the customers
	 *
	 * @return ResponseEntity
	 */
	@GetMapping("/customers")
	public ResponseEntity<List<Customer>> getCustomers() {
		return customerService.getAllCustomers();
	}

	/**
	 * Get the customer by id
	 *
	 * @param id
	 * @return ResponseEntity
	 */
	@GetMapping("/customer/{id}")
	public ResponseEntity<Customer> getCustomerById(@PathVariable("id") long id) {
		return customerService.getCustomerById(id);

	}

	/**
	 * Create new customer
	 *
	 * @param customer
	 * @return ResponseEntity
	 */
	@PostMapping("/customer") 
	public ResponseEntity<Customer> newCustomer(@RequestBody Customer customer) {

		return customerService.addNewCustomer(customer);
		
	}


	/**
	 * Update customer record by using it's id
	 *
	 * @param id
	 * @param customer
	 * @return
	 */
	@PutMapping("/customer/{id}")
	public ResponseEntity<Customer> updateCustomer(@PathVariable("id") long id, @RequestBody Customer customer) {

	return customerService.updateCustomer(id, customer);
	}

	/**
	 * Delete Customer by Id
	 *
	 * @param id
	 * @return ResponseEntity
	 */
	@DeleteMapping("/customer/{id}")
	public ResponseEntity<HttpStatus> deleteCustomerById(@PathVariable("id") long id) {
		return customerService.deleteCustomerById(id);
	}

	/**
	 * Delete all customer
	 *
	 * @return ResponseEntity
	 */
	@DeleteMapping("/customers")
	public ResponseEntity<HttpStatus> deleteAllCustomers() {
	return customerService.deleteAllCustomers();
	}

	/**
	 * Method to get the customer record by id
	 *
	 * @param id
	 * @return customer
	 */
	private Customer getCustRec(long id) {
		Optional<Customer> custObj = customerRepository.findById(id);

		if (custObj.isPresent()) {
			return custObj.get();
		}
		return null;
	}

}
