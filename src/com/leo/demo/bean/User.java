package com.leo.demo.bean;

import java.util.Date;

public class User {
	private String username;
	private String password;
	private String pin;
	private String email;
	private String phone;
	private Date last_access_time;
	private String description;
	public String getName() {
		return username;
	}
	public void setName(String name) {
		this.username = name;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getPin() {
		return pin;
	}
	public void setPin(String pin) {
		this.pin = pin;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public Date getLast_access_time() {
		return last_access_time;
	}
	public void setLast_access_time(Date last_access_time) {
		this.last_access_time = last_access_time;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	
}
