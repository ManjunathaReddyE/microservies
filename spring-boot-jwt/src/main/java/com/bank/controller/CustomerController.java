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
		try {
			return new ResponseEntity<>(customerService.getAllCustomers(), HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	/**
	 * Get the customer by id
	 *
	 * @param id
	 * @return ResponseEntity
	 */
	@GetMapping("/customer/{id}")
	public ResponseEntity<Customer> getCustomerById(@PathVariable("id") long id) {
		try {
			// check if customer exist in database
			Customer custObj = getCustRec(id);

			if (custObj != null) {
				return new ResponseEntity<>(custObj, HttpStatus.OK);
			}

			return new ResponseEntity<>(HttpStatus.NOT_FOUND);

		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

	/**
	 * Create new customer
	 *
	 * @param customer
	 * @return ResponseEntity
	 */
	@PostMapping("/customer") 
	public ResponseEntity<Customer> newCustomer(@RequestBody Customer customer) {

		Customer newCustomer =customerRepository.save(customer);

		return new ResponseEntity<>(newCustomer, HttpStatus.OK); }


	/**
	 * Update customer record by using it's id
	 *
	 * @param id
	 * @param customer
	 * @return
	 */
	@PutMapping("/customer/{id}")
	public ResponseEntity<Customer> updateCustomer(@PathVariable("id") long id, @RequestBody Customer customer) {

		// check if customer exist in database
		Customer custObj = getCustRec(id);

		if (custObj != null) {
			custObj.setCustomerId(customer.getCustomerId());
			custObj.setCustomerName(customer.getCustomerName());
			custObj.setPhone(customer.getPhone());
			custObj.setAddress(customer.getAddress());
			custObj.setEmailId(customer.getEmailId());
			return new ResponseEntity<>(customerRepository.save(custObj), HttpStatus.OK);
		}

		return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}

	/**
	 * Delete Customer by Id
	 *
	 * @param id
	 * @return ResponseEntity
	 */
	@DeleteMapping("/customer/{id}")
	public ResponseEntity<HttpStatus> deleteCustomerById(@PathVariable("id") long id) {
		try {
			// check if customer exist in database
			Customer cust = getCustRec(id);

			if (cust != null) {
				customerRepository.deleteById(id);
				return new ResponseEntity<>(HttpStatus.OK);
			}

			return new ResponseEntity<>(HttpStatus.NOT_FOUND);

		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	/**
	 * Delete all customer
	 *
	 * @return ResponseEntity
	 */
	@DeleteMapping("/customers")
	public ResponseEntity<HttpStatus> deleteAllCustomers() {
		try {
			customerRepository.deleteAll();
			return new ResponseEntity<>(HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}

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
