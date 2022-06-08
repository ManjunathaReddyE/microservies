package com.bank.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bank.model.Customer;
import com.bank.repository.CustomerRepository;

@Service
public class CustomerService {	@Autowired
	CustomerRepository customerRepository;
	
	
	
	public List<Customer> getAllCustomers()
	{
		return customerRepository.findAll();
	}
	
	public Customer saveCustomer(Customer customer)
	{
		return customerRepository.save(customer);
	}
	
}