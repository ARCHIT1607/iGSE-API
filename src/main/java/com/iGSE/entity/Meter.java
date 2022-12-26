package com.iGSE.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;

@Entity
public class Meter {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "METER_SEQ")
	@SequenceGenerator(initialValue = 1, name = "METER_SEQ", sequenceName = "METER_SEQ", allocationSize = 1)
	private long id;

	private int eMeterReadingDay;

	private int eMeterReadingNight;

	private int gMeterReading;
	
	private String submissionDate;

	private String active;

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

	public String isActive() {
		return active;
	}

	public void setActive(String active) {
		this.active = active;
	}

	public String getSubmissionDate() {
		return submissionDate;
	}

	public void setSubmissionDate(String submissionDate) {
		this.submissionDate = submissionDate;
	}

	public Meter(long id, int eMeterReadingDay, int eMeterReadingNight, int gMeterReading, String submissionDate,
			String active) {
		super();
		this.id = id;
		this.eMeterReadingDay = eMeterReadingDay;
		this.eMeterReadingNight = eMeterReadingNight;
		this.gMeterReading = gMeterReading;
		this.submissionDate = submissionDate;
		this.active = active;
	}

	public Meter() {
		super();
		// TODO Auto-generated constructor stub
	}

	@Override
	public String toString() {
		return "Meter [id=" + id + ", eMeterReadingDay=" + eMeterReadingDay + ", eMeterReadingNight="
				+ eMeterReadingNight + ", gMeterReading=" + gMeterReading + ", submissionDate=" + submissionDate
				+ ", active=" + active + "]";
	}


}
