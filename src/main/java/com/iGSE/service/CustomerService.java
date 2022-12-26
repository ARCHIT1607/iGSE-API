package com.iGSE.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.iGSE.entity.Customer;
import com.iGSE.entity.MeterReading;
import com.iGSE.mapper.CustomerMapper;

@Service
public class CustomerService {

	@Autowired
	private CustomerMapper cusMapper;

	public Customer findByEmail(String email) {
		return cusMapper.findByEmail(email);
	}

	public Long register(Customer cus) {
		cus.setBalance(200);
		return cusMapper.register(cus);
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

	public String topUp(String EVC) {
		return cusMapper.topUp(EVC);
	}


}
