package com.techelevator;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.springframework.jdbc.core.JdbcTemplate;

import org.junit.Assert;

public class JDBCCampgroundDAOIntegrationTest extends DAOIntegrationTest {

	private JDBCCampgroundDAO campgroundDAO = new JDBCCampgroundDAO(dataSource);

	@Before
	public void setup() {
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);

	}

	@Test
	public void displayCampgroundsTest() {
		List<Campground> campgroundsList = new ArrayList<Campground>();
		Assert.assertNotNull(campgroundsList);
		Assert.assertEquals(3, campgroundDAO.displayCampgrounds((long) 2).size());
		Assert.assertEquals("Method did not display all campgrounds", 3, campgroundsList.size());
		// Unsure why this test is causing a Null Pointer Exception
		// Unsure if this is being caused by incorrect setup of this integration test
		// class file

	}
}
