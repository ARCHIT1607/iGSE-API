package com.iGSE.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.iGSE.entity.Customer;
import com.iGSE.entity.MeterReading;
import com.iGSE.mapper.CustomerMapper;
import com.iGSE.repositry.ImageRepository;

@Service
public class CustomerService {

	@Autowired
	private CustomerMapper cusMapper;
	
	@Autowired
	private ImageRepository imageRepo;

	public Customer findByEmail(String email) {
		return cusMapper.findByEmail(email);
	}

	public Long register(Customer cus, String evc) {
		cus.setBalance(200);
		Long result = cusMapper.register(cus);
		System.out.println("result "+result + " "+result.getClass() + "evc "+evc);
		if(result.equals(1L)) {
			imageRepo.updateEvcExpiry(evc);
		}
		return result;
	}

	public String submitMeterReading(MeterReading mReading, String email) throws Exception {
		String response;
		mReading.setEmail(email);
		Long success = cusMapper.submitMeterReading(mReading);
		if(success==1){
			response = "sumitted successfuly";
		}else{
				throw new Exception("Error in submission");
			}
		return response;
	}

	public String getBalance(String email) {
		return cusMapper.getBalance(email);
	}

	public String topUp(String email) {
		Customer cus = cusMapper.findByEmail(email);
		cus.setBalance(cus.getBalance()+200);
		cusMapper.topUp(cus);
		return "Top up credited";
	}


}
