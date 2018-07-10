package com.techelevator;

public interface ReservationDAO {

	public Long createNewReservation(Reservation newReservation);

	String query = "insert into reservations () values () returning reservation_id";
}
