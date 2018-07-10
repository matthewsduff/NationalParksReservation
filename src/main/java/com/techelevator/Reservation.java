package com.techelevator;

import java.time.LocalDate;
import java.util.Date;

public class Reservation {

	private Long reservation_id;
	private Long site_id;
	private String name;
	private LocalDate reservation_from_date;
	private LocalDate reservation_to_date;
	private LocalDate reservation_created_date;

	public Long getReservation_id() {
		return reservation_id;
	}

	public void setReservation_id(Long reservation_id) {
		this.reservation_id = reservation_id;
	}

	public Long getSite_id() {
		return site_id;
	}

	public void setSite_id(Long site_id) {
		this.site_id = site_id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public LocalDate getReservation_from_date() {
		return reservation_from_date;
	}

	public void setReservation_from_date(LocalDate reservation_from_date) {
		this.reservation_from_date = reservation_from_date;
	}

	public LocalDate getReservation_to_date() {
		return reservation_to_date;
	}

	public void setReservation_to_date(LocalDate reservation_to_date) {
		this.reservation_to_date = reservation_to_date;
	}

	public LocalDate getReservation_created_date() {
		return reservation_created_date;
	}

	public void setReservation_created_date(LocalDate reservation_created_date) {
		this.reservation_created_date = reservation_created_date;
	}

}
