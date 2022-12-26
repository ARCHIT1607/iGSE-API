package com.iGSE.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;

@Entity
public class Customer {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "CUSTOMER_SEQ")
	@SequenceGenerator(initialValue = 1, name = "CUSTOMER_SEQ", sequenceName = "CUSTOMER_SEQ", allocationSize = 1)
	private long id;

	private String email;

	private String password;

	private String address;

	private String propertyType;

	private int bedrooms;

	private String role = "USER";

	private long EVC;
	
	private long balance;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public long getEVC() {
		return EVC;
	}

	public void setEVC(long eVC) {
		EVC = eVC;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getPropertyType() {
		return propertyType;
	}

	public void setPropertyType(String propertyType) {
		this.propertyType = propertyType;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public int getBedrooms() {
		return bedrooms;
	}

	public void setBedrooms(int bedrooms) {
		this.bedrooms = bedrooms;
	}

	public long getBalance() {
		return balance;
	}

	public void setBalance(long balance) {
		this.balance = balance;
	}

	public Customer(long id, String email, String address, String propertyType, int bedrooms, String password,
			String role, long eVC,long balance) {
		super();
		this.id = id;
		this.email = email;
		this.address = address;
		this.propertyType = propertyType;
		this.bedrooms = bedrooms;
		this.password = password;
		this.role = role;
		this.balance = balance;
		EVC = eVC;
	}

	public Customer() {
		super();
	}

	@Override
	public String toString() {
		return "Customer [id=" + id + ", email=" + email + ", password=" + password + ", address=" + address
				+ ", propertyType=" + propertyType + ", bedrooms=" + bedrooms + ", role=" + role + ", EVC=" + EVC + "]";
	}

}
