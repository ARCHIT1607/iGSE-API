package com.iGSE.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;

@Entity
public class Bill {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "BILL_SEQ")
	@SequenceGenerator(initialValue=1, name="BILL_SEQ", sequenceName="BILL_SEQ", allocationSize=1)
	private long id;
	
	private int eMeterReadingDay;

	private int eMeterReadingNight;

	private int gMeterReading;
	
	private String billDate;
	
	private String email;

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	private String status = "un-paid";

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public int geteMeterReadingDay() {
		return eMeterReadingDay;
	}

	public void seteMeterReadingDay(int eMeterReadingDay) {
		this.eMeterReadingDay = eMeterReadingDay;
	}

	public int geteMeterReadingNight() {
		return eMeterReadingNight;
	}

	public void seteMeterReadingNight(int eMeterReadingNight) {
		this.eMeterReadingNight = eMeterReadingNight;
	}

	public int getgMeterReading() {
		return gMeterReading;
	}

	public void setgMeterReading(int gMeterReading) {
		this.gMeterReading = gMeterReading;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getBillDate() {
		return billDate;
	}

	public void setBillDate(String billDate) {
		this.billDate = billDate;
	}

	@Override
	public String toString() {
		return "Bill [id=" + id + ", eMeterReadingDay=" + eMeterReadingDay + ", eMeterReadingNight="
				+ eMeterReadingNight + ", gMeterReading=" + gMeterReading + ", billDate=" + billDate + ", email="
				+ email + ", status=" + status + "]";
	}

	public Bill(long id, int eMeterReadingDay, int eMeterReadingNight, int gMeterReading, String billDate, String email,
			String status) {
		super();
		this.id = id;
		this.eMeterReadingDay = eMeterReadingDay;
		this.eMeterReadingNight = eMeterReadingNight;
		this.gMeterReading = gMeterReading;
		this.billDate = billDate;
		this.email = email;
		this.status = status;
	}

	public Bill() {
		super();
		// TODO Auto-generated constructor stub
	}
	
}
