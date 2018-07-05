package com.techelevator;

import java.util.Date;

import org.apache.commons.dbcp2.BasicDataSource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;

public class JDBCReservationDAO implements ReservationDAO {

	private JdbcTemplate jdbcReservationTemplate;

	public JDBCReservationDAO(BasicDataSource dataSource) {
		this.jdbcReservationTemplate = new JdbcTemplate(dataSource);
	}

	@Override
	public Long createNewReservation(Reservation newReservation) {
		String sqlCreateNewReservation = "INSERT INTO reservation(reservation_id,site_id,name,from_date,to_date,create_date) VALUES (?,?,?,?,?,?);";
		newReservation.setReservation_id(getNextReservationId());
		jdbcReservationTemplate.update(sqlCreateNewReservation, newReservation.getReservation_id(),
				newReservation.getSite_id(), newReservation.getName(), newReservation.getReservation_from_date(),
				newReservation.getReservation_to_date(), newReservation.getReservation_created_date());

		return newReservation.getReservation_id();
	}

	private long getNextReservationId() {
		SqlRowSet nextIdResult = jdbcReservationTemplate
				.queryForRowSet("SELECT nextval('seq_reservation_reservation_id')");
		if (nextIdResult.next()) {
			return nextIdResult.getLong(1);
		} else {
			throw new RuntimeException("Something went wrong while getting an id for the new reservation");
		}
	}

}
