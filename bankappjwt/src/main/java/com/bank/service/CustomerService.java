package com.bank.service;

import com.bank.model.Customer;
import com.bank.repository.CustomerRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

//@CrossOrigin(origins = "http://localhost:8081")
@Service("userDetailsService")
public class CustomerService implements UserDetailsService {

	@Autowired
	CustomerRepository customerRepository;

	@Autowired
	private BCryptPasswordEncoder bcryptEncoder;
	/**
	 * Get all the customers
	 *
	 * @return ResponseEntity
	 */
	public ResponseEntity<List<Customer>> getAllCustomers() {
		try {
			System.out.println("service==="+customerRepository.findAll());
			return new ResponseEntity<>(customerRepository.findAll(), HttpStatus.OK);
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
	public ResponseEntity<Customer> getCustomerById(long id) {
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
	public ResponseEntity<Customer> addNewCustomer(Customer customer) {

		customer.setPassword(bcryptEncoder.encode(customer.getPassword()));
		Customer newCustomer =customerRepository.save(customer);

		return new ResponseEntity<>(newCustomer, HttpStatus.OK); }


	/**
	 * Update customer record by using it's id
	 *
	 * @param id
	 * @param customer
	 * @return
	 */
	public ResponseEntity<Customer> updateCustomer( long id, Customer customer) {

		
		Customer custObj = getCustRec(id);

		if (custObj != null) {
			custObj.setPassword(bcryptEncoder.encode(customer.getPassword()));
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
	public ResponseEntity<HttpStatus> deleteCustomerById(long id) {
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
	public ResponseEntity<HttpStatus> deleteAllCustomers() {
		try {
			customerRepository.deleteAll();
			return new ResponseEntity<>(HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}
	
	public Customer findOne(String username)
	{
		Optional<Customer> customer=  customerRepository.findByUsername(username);
		if (customer.isPresent()) {
			return customer.get();
		}
		return null;
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
	

	
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Optional<Customer> user = customerRepository.findByUsername(username);
		if(!user.isPresent()){
			throw new UsernameNotFoundException("Invalid username or password.");
		}
		Customer cust=user.get();
		System.out.println("---"+cust.getUsername());
		return new org.springframework.security.core.userdetails.User(cust.getUsername(), cust.getPassword(), getAuthority());
	}

	private List<SimpleGrantedAuthority> getAuthority() {
		return Arrays.asList(new SimpleGrantedAuthority("ROLE_ADMIN"));
	}

}
