package com.techelevator;

import java.util.Date;

public class Park {
	private Long park_id;
	private String park_name;
	private String location;
	private Date established_date;
	private int area;
	private int annual_visitors;
	private String description;
	
	public Long getPark_id() {
		return park_id;
	}
	public void setPark_id(Long park_id) {
		this.park_id = park_id;
	}
	public String getPark_name() {
		return park_name;
	}
	public void setPark_name(String park_name) {
		this.park_name = park_name;
	}
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	public Date getEstablished_date() {
		return established_date;
	}
	public void setEstablished_date(Date established_date) {
		this.established_date = established_date;
	}
	public int getArea() {
		return area;
	}
	public void setArea(int area) {
		this.area = area;
	}
	public int getAnnual_visitors() {
		return annual_visitors;
	}
	public void setAnnual_visitors(int annual_visitors) {
		this.annual_visitors = annual_visitors;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
}

	