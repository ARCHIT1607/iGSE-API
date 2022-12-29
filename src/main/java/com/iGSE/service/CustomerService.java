package com.iGSE.service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.iGSE.entity.Bill;
import com.iGSE.entity.Customer;
import com.iGSE.entity.MeterReading;
import com.iGSE.mapper.CustomerMapper;
import com.iGSE.repositry.CustomerRepository;
import com.iGSE.repositry.ImageRepository;

@Service
public class CustomerService {

	@Autowired
	private CustomerMapper cusMapper;
	
	@Autowired
	private CustomerRepository cusRepo;
	@Autowired
	private ImageRepository imageRepo;

	public Customer findByEmail(String email) {
		return cusMapper.findByEmail(email);
	}

	public Customer register(Customer cus, String evc) {
		cus.setBalance(200);
		Customer result = cusRepo.save(cus);
		System.out.println("result "+result + " "+result.getClass() + "evc "+evc);
		if(result!=null) {
			imageRepo.updateEvcExpiry(evc);
		}
		return result;
	}

	public String submitMeterReading(MeterReading mReading, String email) throws Exception {
		String response;
		Long success = 0L;
		List<Bill> bills = cusMapper.getBill(email);
		if(bills.size()==0) {
			mReading.setEmail(email);
			success = cusMapper.submitMeterReading(mReading);
			List<MeterReading> meterReadings = cusMapper.getMeterReadings(email);
			if(meterReadings.size()==2) {
				Bill bill = new Bill();
				System.out.println("first merer "+meterReadings.get(0));
				System.out.println("second merer "+meterReadings.get(1));
				bill.setBillDate(meterReadings.get(1).getSubmissionDate());
				bill.setEmail(email);
				bill.seteMeterReadingDay(meterReadings.get(1).geteMeterReadingDay() - meterReadings.get(0).geteMeterReadingDay());
				bill.seteMeterReadingNight(meterReadings.get(1).geteMeterReadingNight() - meterReadings.get(0).geteMeterReadingNight());
				bill.setgMeterReading(meterReadings.get(1).getgMeterReading() - meterReadings.get(0).getgMeterReading());
				cusMapper.generateBill(bill);
			}
		}else if(bills.size()>0){
			throw new Exception("Please fill exisitng bill first");
		}
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

	public String topUp(String email, String EVC) {
		Customer cus = cusMapper.findByEmail(email);
		cus.setBalance(cus.getBalance()+200);
		cusMapper.topUp(cus);
			imageRepo.updateEvcExpiry(EVC);
		return "Top up credited";
	}

	public List<Bill> getBill(String email){
		return cusMapper.getBill(email);
	}

	public String findDifference(String name) {
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		String inputString1 = "2022-12-27";
		String inputString2 = "2022-12-27";
		LocalDate date1 = LocalDate.parse(inputString1, dtf);
		 LocalDate date2 = LocalDate.parse(inputString2, dtf);
		 System.out.println ("Days: " + ChronoUnit.DAYS.between(date1, date2));
//		 long daysBetween = Duration.between(date1, date2).toDays();;
//		 System.out.println ("Days: " + daysBetween);
		return "success";
	}

	public String payBill(String email, String billId) throws Exception {
		List<Bill> bills = cusMapper.getBill(email);
		if(bills.size()==1) {
			Bill bill = new Bill();
			bill = bills.get(0);
			cusMapper.payBill(bill);
		}else {
			throw new Exception("Please fill exisitng bill first");
		}
		return "Bill Paid";
	}


}
