package com.techelevator;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.sql.SQLException;
import java.time.LocalDate;

import javax.sql.DataSource;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;

public class JDBCReservationIntegrationTest extends DAOIntegrationTest {
	private JDBCReservationDAO reservationDAO;
	
	
	@Before
	public void setup() {
		
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		reservationDAO = new JDBCReservationDAO(dataSource);
		String sqlInsertReservation = "INSERT INTO park(name,location,establish_date,area,visitors,description) "
				+ "VALUES ('Penns Woods','Pennsylvania','2000-01-01'::date,50000,1000000, 'A beautiful fake park in PA');";
		jdbcTemplate.update(sqlInsertReservation);
		
	}
	
	
	@Test
	public void CreateReservation_id_test() throws SQLException {
			
			LocalDate test_from_date = LocalDate.of(2020,01,01);
			LocalDate test_to_date = LocalDate.of(2021,01,01);
			LocalDate test_current_date = LocalDate.of(2018,01,01);
		    Long RID = (long) 999;
		    Long SID = (long) 1;
			
			Reservation theReservation = getReservation(RID,SID, "Addams Family Values",test_from_date,test_to_date,test_current_date );
			reservationDAO.createNewReservation(theReservation);
			Reservation savedReservation = reservationDAO.findReservationById(theReservation.getReservation_id());
			assertNotNull(savedReservation);
			
			assertReservationsAreEqual(theReservation,savedReservation);
			
	}
	@Test
	public void FindReservation_id_test() throws SQLException {
			
			Reservation testReservation = reservationDAO.findReservationById((long)1);
			Long resultTest = testReservation.getReservation_id();
			Long one =(long) 1;

			Assert.assertEquals(one, resultTest);
		
			assertNotNull(testReservation);
			
			
			
	}
	
	private Reservation getReservation(Long reservation_id, Long site_id, String name, LocalDate from_date, LocalDate to_date,LocalDate create_date) {
		Reservation theReservation;
		theReservation = new Reservation();
		theReservation.setReservation_id(reservation_id);
		theReservation.setSite_id(site_id);
		theReservation.setName(name);
		theReservation.setReservation_from_date(from_date);
		theReservation.setReservation_to_date(to_date);
		theReservation.setReservation_created_date(create_date);
		
		return theReservation;
	}
	private void assertReservationsAreEqual(Reservation expected, Reservation actual) {
		assertEquals(expected.getReservation_id(), actual.getReservation_id());
		assertEquals(expected.getSite_id(), actual.getSite_id());
		assertEquals(expected.getName(), actual.getName());
		assertEquals(expected.getReservation_from_date(),actual.getReservation_from_date());
		assertEquals(expected.getReservation_to_date(),actual.getReservation_to_date());
		assertEquals(expected.getReservation_created_date(),actual.getReservation_created_date());
		
	}
	
}
