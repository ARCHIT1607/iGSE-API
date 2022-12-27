package com.iGSE.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.iGSE.entity.Customer;
import com.iGSE.entity.MeterReading;

@Mapper
public interface CustomerMapper {
	
	public Customer findByEmail(String email);

	public Long register(Customer cus);

	public Customer getUserDetails(String email);

	public Long submitMeterReading(MeterReading mReading);

	public String getBalance(String email);

	public void topUp(Customer cus);

}
