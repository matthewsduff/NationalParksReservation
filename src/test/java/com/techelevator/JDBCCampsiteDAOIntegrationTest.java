package com.techelevator;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import javax.sql.DataSource;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;

public class JDBCCampsiteDAOIntegrationTest extends DAOIntegrationTest {

	

	private JDBCCampsiteDAO campsiteDAO;
	
	@Before
	public void setup() {
		
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		campsiteDAO = new JDBCCampsiteDAO(dataSource);

		
	}
@Test
public void findCampsitesByReservationDateTest() {
	List<Campsite> availableCampsites = new ArrayList<Campsite>();
	availableCampsites = campsiteDAO.findCampsitesByReservationDate(1, LocalDate.of(2020,01,01), LocalDate.of(2020,01,05));
	
	Assert.assertEquals("Your should display",5,availableCampsites.size());
}

@Test
public void findCampsiteBySite_id() {
	campsiteDAO.deleteCampsite(1);
	Long test = campsiteDAO.findCampsiteBySite_id(1);
	
	//Assert.assertEquals("Your should display",1,test);
	
}



private Campsite mapRowToCampsite(SqlRowSet results) {
	Campsite theCampsite;
	theCampsite = new Campsite();
	theCampsite.setSite_id(results.getLong("site_id"));
	theCampsite.setCampground_id(results.getLong("campground_id"));
	theCampsite.setSite_number(results.getInt("site_number"));
	theCampsite.setMax_occupancy(results.getInt("max_occupancy"));
	theCampsite.setAccessible(results.getBoolean("accessible"));
	theCampsite.setMax_rv_length(results.getInt("max_rv_length"));
	theCampsite.setUtilities(results.getBoolean("utilities"));
	theCampsite.setDailyFee(results.getDouble("daily_fee"));
	
	return theCampsite;
}
}
