package com.iGSE.controller;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.iGSE.entity.Customer;
import com.iGSE.entity.EVC;
import com.iGSE.entity.MeterReading;
import com.iGSE.service.CustomerService;
import com.iGSE.service.QRService;

@RestController
@Transactional
@CrossOrigin
public class CustomerController {
	
	@Autowired
	private CustomerService cusService;
	
	@Autowired
	private QRService qrService;
	
	@Autowired
	BCryptPasswordEncoder passwordEncode;

	@PostMapping("/auth/register/{evc}")
	public ResponseEntity<Object> register(@PathVariable("evc") String evc, @RequestBody Customer cus) {
		try {
			Customer user = cusService.findByEmail(cus.getEmail());
			EVC qr = qrService.getQrDetails(evc);
			System.out.println("user "+user);
			if(qr.isExpired()==true) {
				throw new Exception("EVC has expired, Try with another one");
			}
			if (user!=null && user.getEmail().toUpperCase().equals(cus.getEmail().toUpperCase())) {
				throw new Exception("user already exists");
			}
			cus.setPassword(passwordEncode.encode(cus.getPassword()));
			return new ResponseEntity<Object>(cusService.register(cus,evc), HttpStatus.OK);
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
	public ResponseEntity<String> submitMeterReading(@RequestBody MeterReading mReading,Principal authenicatedUser) {
		try {
			if (mReading == null) {
				throw new Exception("Meter Reading cannot be empty");
			} else {
				return new ResponseEntity<String>(cusService.submitMeterReading(mReading,authenicatedUser.getName()), HttpStatus.OK);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}
	
	@PostMapping("/customer/topUp")
	public ResponseEntity<String> topUp(@RequestParam(name = "EVC") String EVC,Principal authenicatedUser) {
		try {
			EVC qr = qrService.getQrDetails(EVC);
			if(qr.isExpired()==true) {
				throw new Exception("EVC has expired, Try with another one");
			}
			if (EVC == null) {
				throw new Exception("EVC is null");
			} else {
				return new ResponseEntity<String>(cusService.topUp(authenicatedUser.getName(),EVC), HttpStatus.OK);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}
	
	@GetMapping("/customer/getBalance")
	public ResponseEntity<String> getBalance(Principal authenicatedUser) {
		try {
			if (authenicatedUser.getName() == null) {
				throw new Exception("Email is null");
			} else {
				return new ResponseEntity<String>(cusService.getBalance(authenicatedUser.getName()), HttpStatus.OK);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}
	
	@GetMapping("/customer/getBill")
	public ResponseEntity<Object> getBill(Principal authenicatedUser) {
		try {
			if (authenicatedUser.getName() == null) {
				throw new Exception("Email is null");
			} else {
				return new ResponseEntity<Object>(cusService.getBill(authenicatedUser.getName()), HttpStatus.OK);
//				return new ResponseEntity<Object>("success", HttpStatus.OK);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<Object>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}
	

	@PostMapping("/customer/payBill")
	public ResponseEntity<String> payBill(@RequestParam(name = "billId") String billId,Principal authenicatedUser) {
		try {
			if (billId == null) {
				throw new Exception("EVC is null");
			} else {
				return new ResponseEntity<String>(cusService.payBill(authenicatedUser.getName(),billId), HttpStatus.OK);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}

	
	@GetMapping("/customer/findDifference")
	public ResponseEntity<Object> findDifference(Principal authenicatedUser) {
		try {
			if (authenicatedUser.getName() == null) {
				throw new Exception("Email is null");
			} else {
				return new ResponseEntity<Object>(cusService.findDifference(authenicatedUser.getName()), HttpStatus.OK);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<Object>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}

}
