package com.iGSE.service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.iGSE.entity.Bill;
import com.iGSE.entity.Customer;
import com.iGSE.entity.Meter;
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
		System.out.println("result " + result + " " + result.getClass() + "evc " + evc);
		if (result != null) {
			imageRepo.updateEvcExpiry(evc);
		}
		return result;
	}

	@Transactional(rollbackOn = Exception.class)
	public String submitMeterReading(MeterReading mReading, String email) throws Exception {
		String response;
		Long success = 0L;
		List<Bill> bills = cusMapper.getBill(email);
		if(bills.size()==0) {
			mReading.setEmail(email);
			List<MeterReading> meterReadings = cusMapper.getMeterReadings(email);
			
			if(meterReadings.size()==0) {
				success = cusMapper.submitMeterReading(mReading);
			}else{
				if(meterReadings.get(0).geteMeterReadingDay()> mReading.geteMeterReadingDay() ||
						meterReadings.get(0).geteMeterReadingNight()> mReading.geteMeterReadingNight() ||
						meterReadings.get(0).getgMeterReading()> mReading.getgMeterReading())
				{
					throw new Exception("Incorrect reading entered");
				}
			}
			if(meterReadings.size()>0) {
				success = cusMapper.submitMeterReading(mReading);
				meterReadings = cusMapper.getMeterReadings(email);
				Meter meter = cusMapper.getLastActiveMeterPrice();
				Bill bill = new Bill();
				System.out.println("first merer "+meterReadings.get(0));
				System.out.println("second merer "+meterReadings.get(1));
				bill.setBillDate(meterReadings.get(1).getSubmissionDate());
				bill.setEmail(email);
				bill.seteMeterReadingDay(meterReadings.get(0).geteMeterReadingDay() - meterReadings.get(1).geteMeterReadingDay());
				bill.seteMeterReadingNight(meterReadings.get(0).geteMeterReadingNight() - meterReadings.get(1).geteMeterReadingNight());
				bill.setgMeterReading(meterReadings.get(0).getgMeterReading() - meterReadings.get(1).getgMeterReading());
				bill.setDays(findDifference(meterReadings.get(0).getSubmissionDate(), meterReadings.get(1).getSubmissionDate()));
				double amount = bill.geteMeterReadingDay()*meter.geteMeterPriceDay() + bill.geteMeterReadingNight()*meter.geteMeterPriceNight()+
						bill.getgMeterReading()*meter.getgMeterPrice() + bill.getDays()* meter.getStandingCharge();
				bill.setDue(amount);
				cusMapper.generateBill(bill);
			}
		}
		else {
			if(!bills.get(0).getStatus().equals("un-paid")) {
				List<MeterReading> meterReadings = cusMapper.getMeterReadings(email);
				if(meterReadings.get(0).geteMeterReadingDay()> mReading.geteMeterReadingDay() ||
						meterReadings.get(0).geteMeterReadingNight()> mReading.geteMeterReadingNight() ||
						meterReadings.get(0).getgMeterReading()> mReading.getgMeterReading())
				{
					throw new Exception("Incorrect reading entered");
				}
				mReading.setEmail(email);
				success = cusMapper.submitMeterReading(mReading);
				Map<String,String> param = new HashMap<>();
				System.out.println("bills.get(0).getBillDate() "+bills.get(0));
				param.put("billDate", bills.get(0).getBillDate());
				param.put("submissionDate", mReading.getSubmissionDate());
				MeterReading meter = cusMapper.checkSubmissionDate(param);
				if(meter!=null) {
					Bill bill = new Bill();
					System.out.println("first merer "+meter);
					System.out.println("second merer "+bills.get(0));
					bill.setBillDate(meter.getSubmissionDate());
					bill.setEmail(email);
					bill.seteMeterReadingDay(meter.geteMeterReadingDay() - bills.get(0).geteMeterReadingDay());
					bill.seteMeterReadingNight(meter.geteMeterReadingNight() - bills.get(0).geteMeterReadingNight());
					bill.setgMeterReading(meter.getgMeterReading() - bills.get(0).getgMeterReading());
					bill.setDays(findDifference(meter.getSubmissionDate(), bills.get(0).getBillDate()));
					cusMapper.generateBill(bill);
				}else {
					throw new Exception("Please choose correct bill date");
				}
			}else {
				throw new Exception("Please pay exisitng bill first");
			}
				
			}
//		}else if(bills.size()>0){
//			throw new Exception("Please fill exisitng bill first");
//		}
	if(success==1)

	{
		response = "sumitted successfuly";
	}else
	{
		throw new Exception("Error in submission");
	}return response;
	}

	public String getBalance(String email) {
		return cusMapper.getBalance(email);
	}

	public String topUp(String email, String EVC) {
		Customer cus = cusMapper.findByEmail(email);
		cus.setBalance(cus.getBalance() + 200);
		cusMapper.topUp(cus);
		imageRepo.updateEvcExpiry(EVC);
		return "Top up credited";
	}

	public List<Bill> getBill(String email) {
		return cusMapper.getBill(email);
	}

	public List<Bill> getUnPaidBill(String email) {
		return cusMapper.getUnPaidBill(email);
	}

	public long findDifference(String endDate, String startDate) {
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		LocalDate date1 = LocalDate.parse(startDate, dtf);
		LocalDate date2 = LocalDate.parse(endDate, dtf);
		System.out.println("Days: " + ChronoUnit.DAYS.between(date1, date2));
//		 long daysBetween = Duration.between(date1, date2).toDays();;
//		 System.out.println ("Days: " + daysBetween);
		return ChronoUnit.DAYS.between(date1, date2);
	}

	public String payBill(String email, String billId, String amt) throws Exception {
		List<Bill> bills = cusMapper.getUnPaidBill(email);
		Customer user = cusRepo.findByEmail(email);
		if (bills.size() == 1) {
			Bill bill = new Bill();
			bill = bills.get(0);
			cusMapper.payBill(bill);
			user.setBalance(user.getBalance() - Double.valueOf(amt));
			cusMapper.updateBalance(user);
		} else {
			throw new Exception("Please fill exisitng bill first");
		}
		return "Bill Paid";
	}

	public Meter getMeterPrice() {
		return cusMapper.getLastActiveMeterPrice();
	}

}
