package com.techelevator;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.dbcp2.BasicDataSource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;

public class JDBCParkDAO implements ParkDAO {

	private JdbcTemplate jdbcParkTemplate;

	public JDBCParkDAO(BasicDataSource dataSource) {
		this.jdbcParkTemplate = new JdbcTemplate(dataSource);
	}

	@Override
	public void addPark(Park newPark) {
		String sqlInsertNewPark = "INSERT INTO park(park_id,name,location,establish_date,area,visitors,description) "
				+ "VALUES (?,?,?,?,?,?,?);";
		newPark.setPark_id(getNextParkId());
		jdbcParkTemplate.update(sqlInsertNewPark, newPark.getPark_id(), newPark.getPark_name(), newPark.getLocation(),
				newPark.getEstablished_date(), newPark.getArea(), newPark.getAnnual_visitors(),
				newPark.getDescription());

	}

	@Override
	public void updatePark(Park park) {
		String name = park.getPark_name();
		String location = park.getLocation();
		Date establish_date = park.getEstablished_date();
		int area = park.getArea();
		int visitors = park.getAnnual_visitors();
		String description = park.getDescription();

		String sqlUpdatePark = "UPDATE park SET name = ?, location = ?, establish_date = ?, area = ?, visitors = ?, description = ?)"
				+ "WHERE park_id = ?;";

		jdbcParkTemplate.update(sqlUpdatePark, name, location, establish_date, area, visitors, description);
	}

	@Override
	public void deletePark(long park_id) {
		String sqlDeletePark = "DELETE FROM park WHERE park_id = ?;";
		jdbcParkTemplate.update(sqlDeletePark, park_id);

	}

	@Override
	public List<String> displayAllParkNames() {
		List<String> allParksList = new ArrayList<String>();
		String sqlDisplayAllParks = "SELECT * FROM park;";
		SqlRowSet results = jdbcParkTemplate.queryForRowSet(sqlDisplayAllParks);
		while (results.next()) {
			Park thePark = mapRowToPark(results);
			allParksList.add(thePark.getPark_name());
		}
		return allParksList;

	}

	@Override
	public String displayParkInformation(long park_id) {
		Park parkToDisplay = null;
		String sqlDisplayParkInformation = "SELECT * FROM park WHERE park_id = ?;";
		SqlRowSet results = jdbcParkTemplate.queryForRowSet(sqlDisplayParkInformation, park_id);
		while (results.next()) {
			Park thePark = mapRowToPark(results);
			parkToDisplay = thePark;
		}
		String printString = parkToDisplay.getPark_name() + "\n" + parkToDisplay.getLocation() + "\n"
				+ parkToDisplay.getEstablished_date() + "\n" + parkToDisplay.getArea() + "\n"
				+ parkToDisplay.getAnnual_visitors() + "\n" + parkToDisplay.getDescription();
		return printString;
		// prints appropriate information, want to have formatting and printing done in
		// the CLI
	}

	private long getNextParkId() {
		SqlRowSet nextIdResult = jdbcParkTemplate.queryForRowSet("SELECT nextval('seq_park_park_id')");
		if (nextIdResult.next()) {
			return nextIdResult.getLong(1);
		} else {
			throw new RuntimeException("Something went wrong while getting an id for the new park");
		}
	}

	private Park mapRowToPark(SqlRowSet results) {
		Park thePark;
		thePark = new Park();
		thePark.setPark_id(results.getLong("park_id"));
		thePark.setPark_name(results.getString("name"));
		thePark.setLocation(results.getString("location"));
		thePark.setEstablished_date(results.getDate("establish_date"));
		thePark.setArea(results.getInt("area"));
		thePark.setAnnual_visitors(results.getInt("visitors"));
		thePark.setDescription(results.getString("description"));
		return thePark;
	}

}

// @Override
// public Park findParkByID(long park_id) {
// // TODO Auto-generated method stub
// return null;
// }

// @Override
// public Park findParkByName(String park_name) {
// // TODO Auto-generated method stub
// return null;
// }
