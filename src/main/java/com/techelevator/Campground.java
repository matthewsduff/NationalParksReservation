package com.techelevator;

import java.util.Date;

public class Campground {
	private Long campground_id;
	private Long park_id;
	private String campground_name;
	private String open_from_month;
	private String open_to_month;
	private double daily_fee;

	public Long getCampground_id() {
		return campground_id;
	}

	public void setCampground_id(Long campground_id) {
		this.campground_id = campground_id;
	}

	public Long getPark_id() {
		return park_id;
	}

	public void setPark_id(Long park_id) {
		this.park_id = park_id;
	}

	public String getCampground_name() {
		return campground_name;
	}

	public void setCampground_name(String campground_name) {
		this.campground_name = campground_name;
	}

	public String getOpen_from_month() {
		return open_from_month;
	}

	public void setOpen_from_month(String open_from_month) {
		this.open_from_month = open_from_month;
	}

	public String getOpen_to_month() {
		return open_to_month;
	}

	public void setOpen_to_month(String open_to_month) {
		this.open_to_month = open_to_month;
	}

	public double getDaily_fee() {
		return daily_fee;
	}

	public void setDaily_fee(double daily_fee) {
		this.daily_fee = daily_fee;
	}

}
