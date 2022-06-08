package com.bank.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.bank.config.JwtTokenUtil;
import com.bank.model.Customer;
import com.bank.model.UserDto;
import com.bank.service.CustomerService;
import com.bank.model.ApiResponse;
import com.bank.model.AuthToken;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/token")
public class AuthenticationController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private CustomerService customerService;
    
    public UserDto userdto=new UserDto();
    
    public String insToken;
    
    

	@RequestMapping(value = "/generate-token", method = RequestMethod.POST)
    public String register(@RequestBody Customer customer) throws AuthenticationException {

    	System.out.println("==insice Authentiler controller 1 ="+customer.getUsername()+"  "+customer.getPassword());
         authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(customer.getUsername(), customer.getPassword()));
         System.out.println("==insice AuthenticationController controller 2=");
         final Customer cust = customerService.findOne(customer.getUsername());
        final String token = jwtTokenUtil.generateToken(cust);
        System.out.println("==token== "+token);
       fetchToken(token);
       
      // Context cts=ctx.setattribute
       return token;
    }

public String ftoken()
{
	System.out.println("  "+userdto.getToken()+"  instoken=" +insToken+ "  this.instan "+this.insToken);
	return userdto.getToken();	
}
	
public String fetchToken(String str)
{
	System.out.println("==fetchToken=="+str);
	userdto.setToken(str);
	this.insToken=str;
	return str;
}
	



}