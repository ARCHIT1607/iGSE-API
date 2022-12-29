package com.iGSE.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.iGSE.entity.Meter;
import com.iGSE.service.AdminService;

@RestController
public class AdminController {
	
	@Autowired
	private AdminService adminService;
	
	@PostMapping("admin/setMeterPrice")
	public ResponseEntity<Integer> setMeterPrice(@RequestBody Meter meter){
		try {
			if (meter == null) {
				throw new Exception("Meter price setting cannot be null");
			} else {
				return new ResponseEntity<Integer>(adminService.setMeterPrice(meter), HttpStatus.OK);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<Integer>(HttpStatus.BAD_REQUEST);
		}
	}
	
	@GetMapping("admin/getAllMeterReading")
	public ResponseEntity<Object> getAllMeterReading(){
		try {
				return new ResponseEntity<Object>(adminService.getAllMeterReading(), HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<Object>(e.getMessage(),HttpStatus.BAD_REQUEST);
		}
	}
	
	

}
