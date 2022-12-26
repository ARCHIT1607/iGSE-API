package com.iGSE.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.iGSE.entity.Customer;
import com.iGSE.entity.MeterReading;
import com.iGSE.service.CustomerService;
import com.iGSE.service.IAuthenticationFacade;

@RestController
@Transactional
@CrossOrigin
public class CustomerController {
	
	@Autowired
	private CustomerService cusService;
	
	@Autowired
	BCryptPasswordEncoder passwordEncode;
	

	@PostMapping("/auth/register")
	public ResponseEntity<Object> register(@RequestBody Customer cus) {
		Customer user = cusService.findByEmail(cus.getEmail());
		System.out.println("user "+user);
		try {
			if (user!=null && user.getEmail().toUpperCase().equals(cus.getEmail().toUpperCase())) {
				throw new Exception("user already exists");
			}
			cus.setPassword(passwordEncode.encode(cus.getPassword()));
			return new ResponseEntity<Object>(cusService.register(cus), HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<Object>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@PostMapping("/auth/login")
	public ResponseEntity<String> getUserDetails(@RequestParam(name = "email") String email,
			@RequestParam(name = "password") String password) {
		Customer user = cusService.findByEmail(email);
		System.out.println("user "+user);
		try {
			if (user == null) {
				throw new Exception("User doesn't Exist");
			} else if (passwordEncode.matches(password, user.getPassword())) {
				System.out.println("pass is correct");
				return new ResponseEntity<String>("Success", HttpStatus.OK);
			} else {
				return new ResponseEntity<String>("Password is incorrect", HttpStatus.OK);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}
	
	@PostMapping("/customer/submitMeterReading")
	public ResponseEntity<String> submitMeterReading(@RequestBody MeterReading mReading) {
		try {
			if (mReading == null) {
				throw new Exception("Meter Reading cannot be empty");
			} else {
//				return new ResponseEntity<String>(cusService.submitMeterReading(mReading,authentication.getName()), HttpStatus.OK);
				return new ResponseEntity<String>("Success", HttpStatus.OK);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}
	
	@PostMapping("/customer/topUp")
	public ResponseEntity<String> topUp(@RequestParam(name = "EVC") String EVC) {
		try {
			if (EVC == null) {
				throw new Exception("EVC is null");
			} else {
				return new ResponseEntity<String>(cusService.topUp(EVC), HttpStatus.OK);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}
	
	@GetMapping("/customer/getBalance")
	public ResponseEntity<String> getBalance(@RequestParam(name = "email") String email) {
		try {
			if (email == null) {
				throw new Exception("Email is null");
			} else {
				return new ResponseEntity<String>(cusService.getBalance(email), HttpStatus.OK);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}

}
