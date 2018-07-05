package com.techelevator;

import java.util.Date;

public interface ReservationDAO {

	public Long createNewReservation(String name, Long site_id, Date reservation_from_date, Date reservation_to_date);
	
	}
	
