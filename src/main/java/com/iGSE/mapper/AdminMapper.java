package com.iGSE.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.iGSE.entity.Meter;
import com.iGSE.entity.MeterReading;

@Mapper
public interface AdminMapper {

	Meter getLastActiveMeterPrice();

	int updateMeterPrice(Meter meter);

	int addMeterPrice(Meter meter);

	List<MeterReading> getAllMeterReading();

}
