package com.techelevator;

import java.util.Date;

import javax.sql.DataSource;

import org.apache.commons.dbcp2.BasicDataSource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;

public class JDBCReservationDAO implements ReservationDAO {

	private JdbcTemplate jdbcReservationTemplate;

	public JDBCReservationDAO(DataSource dataSource) {
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
	
	public Reservation findReservationById(long reservation_id) {
		Reservation theReservation = null;
		String sqlDisplayParkInformation = "SELECT * FROM reservation WHERE reservation_id = ?;";
		SqlRowSet results = jdbcReservationTemplate.queryForRowSet(sqlDisplayParkInformation, reservation_id);
		if (results.next()) {
			theReservation = getReservation(results);

		}
		return theReservation;
	}

	private long getNextReservationId() {
		SqlRowSet nextIdResult = jdbcReservationTemplate
				.queryForRowSet("SELECT nextval('reservation_reservation_id_seq')");
		if (nextIdResult.next()) {
			return nextIdResult.getLong(1);
		} else {
			throw new RuntimeException("Something went wrong while getting an id for the new reservation");
		}
	}
	private Reservation getReservation(SqlRowSet results) {
		Reservation theReservation;
		theReservation = new Reservation();
		theReservation.setReservation_id(results.getLong("reservation_id"));
		theReservation.setSite_id(results.getLong("site_id"));
		theReservation.setName(results.getString("name"));
		theReservation.setReservation_from_date(results.getDate("from_date").toLocalDate());
		theReservation.setReservation_to_date(results.getDate("to_date").toLocalDate());
		theReservation.setReservation_created_date(results.getDate("create_date").toLocalDate());
		
		return theReservation;
	}

}
