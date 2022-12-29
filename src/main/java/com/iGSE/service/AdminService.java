package com.iGSE.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;

import com.iGSE.entity.Meter;
import com.iGSE.entity.MeterReading;
import com.iGSE.mapper.AdminMapper;

@Service
public class AdminService {

	@Autowired
	private AdminMapper adminMapper;

	public int setMeterPrice(Meter meter) {
		Meter lastActiveMeter = adminMapper.getLastActiveMeterPrice();
		adminMapper.updateMeterPrice(lastActiveMeter);
		meter.setSubmissionDate(LocalDate.now().toString());
		meter.setActive("Y");
		return adminMapper.addMeterPrice(meter);
	}

	public List<MeterReading> getAllMeterReading() {
		return adminMapper.getAllMeterReading();
	}

	public Meter getMeterReading() {
		return adminMapper.getLastActiveMeterPrice();
	}

}
