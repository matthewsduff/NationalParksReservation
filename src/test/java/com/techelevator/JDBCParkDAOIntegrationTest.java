package com.techelevator;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;

import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.dbcp2.BasicDataSource;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.SingleConnectionDataSource;

public class JDBCParkDAOIntegrationTest extends DAOIntegrationTest {

	private LocalDate testDate =  LocalDate.of(2000,01,01);
	private JDBCParkDAO parkDAO;
	

	@Before
	public void setup() {
		
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		parkDAO = new JDBCParkDAO(dataSource);
		
		String sqlInsertPark = "INSERT INTO park(name,location,establish_date,area,visitors,description) "
				+ "VALUES ('Penns Woods','Pennsylvania','2000-01-01'::date,50000,1000000, 'A beautiful fake park in PA');";
		jdbcTemplate.update(sqlInsertPark);
	}
	
	
	@After
	public void rollback() throws SQLException{
		dataSource.getConnection().rollback();
	}
	
	@Test
	public void save_new_park_and_read_it_back() throws SQLException{
		
		Park thePark = getPark("Penns Woods", "Pennsylvania",testDate, 50000, 1000000, "A beautiful fake park in PA");
		parkDAO.addPark(thePark);
		Park savedPark = parkDAO.findParkById(thePark.getPark_id());
		assertNotNull(savedPark);
		System.out.print(thePark.getPark_id() + "is thePark ID");
		System.out.print(savedPark.getPark_id()+ "is savedPark ID");
		assertParksAreEqual(thePark,savedPark);
		
	}
	
	@Test
	public void displayAllParkNames() {
		List<String>parksToDisplay = new ArrayList<String>();
		parksToDisplay = parkDAO.displayAllParkNames();
		Assert.assertEquals("Your method didn't display all the parks",4,parksToDisplay.size());
	
	}
	
	@Test
	public void updateParkTest() {
	
	}
	
	private Park getPark(String name, String location, LocalDate established_date, int area, int visitors, String description) {
		Park thePark = new Park();
		thePark.setPark_name(name);
		thePark.setLocation(location);
		thePark.setEstablished_date(established_date);
		thePark.setArea(area);
		thePark.setAnnual_visitors(visitors);
		thePark.setDescription(description);
		return thePark;
	}
	
	private void assertParksAreEqual(Park expected, Park actual) {
		assertEquals(expected.getPark_id(), actual.getPark_id());
		assertEquals(expected.getPark_name(), actual.getPark_name());
		assertEquals(expected.getLocation(), actual.getLocation());
		assertEquals(expected.getEstablished_date(),actual.getEstablished_date());
		assertEquals(expected.getArea(),actual.getArea());
		assertEquals(expected.getAnnual_visitors(),actual.getAnnual_visitors());
		assertEquals(expected.getDescription(),actual.getDescription());
	}
	
	
}
