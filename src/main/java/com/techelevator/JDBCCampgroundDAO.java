package com.techelevator;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.dbcp2.BasicDataSource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;

public class JDBCCampgroundDAO implements CampgroundDAO {

	private JdbcTemplate jdbcCampgroundTemplate;

	public JDBCCampgroundDAO(BasicDataSource dataSource) {
		this.jdbcCampgroundTemplate = new JdbcTemplate(dataSource);
	}

	@Override
	public void addCampground(Campground newCampground) {
		String sqlInsertNewCampground = "INSERT INTO campground(campground_id,park_id,name,open_from_mm, open_to_mm, daily_fee) "
				+ "VALUES (?,?,?,?,?,?);";
		newCampground.setCampground_id(getNextCampgroundId());
		jdbcCampgroundTemplate.update(sqlInsertNewCampground, newCampground.getCampground_id(),
				newCampground.getPark_id(), newCampground.getCampground_name(), newCampground.getOpen_from_month(),
				newCampground.getOpen_to_month(), newCampground.getDaily_fee());

	}

	@Override
	public void updateCampground(Campground campground) {
		String name = campground.getCampground_name();
		String open_from_mm = campground.getOpen_from_month();
		String open_to_mm = campground.getOpen_to_month();
		double daily_Fee = campground.getDaily_fee();

		String sqlUpdateCampground = "UPDATE campground SET name = ?, open_from_mm = ?, open_to_mm = ?, daily_fee = ? WHERE campground_id = ?;";

		jdbcCampgroundTemplate.update(sqlUpdateCampground, name, open_from_mm, open_to_mm, daily_Fee);

	}

	@Override
	public void deleteCampground(long campground_id) {
		String sqlDeleteCampground = "DELETE FROM campground WHERE campground_id = ?;";
		jdbcCampgroundTemplate.update(sqlDeleteCampground, campground_id);

	}

	@Override
	public List<Campground> displayCampgrounds(long park_id) {
		List<Campground> allCampgroundsInPark = new ArrayList<Campground>();
		String sqlDisplayCampgroundsFromParkId = "SELECT * FROM campground where park_id = ?;";
		SqlRowSet results = jdbcCampgroundTemplate.queryForRowSet(sqlDisplayCampgroundsFromParkId, park_id);
		while(results.next()) {
			Campground theCampground = mapRowToCampground(results);
			allCampgroundsInPark.add(theCampground);
		}
		
		return allCampgroundsInPark;
	}

	private long getNextCampgroundId() {
		SqlRowSet nextIdResult = jdbcCampgroundTemplate
				.queryForRowSet("SELECT nextval('seq_campground_campground_id')");
		if (nextIdResult.next()) {
			return nextIdResult.getLong(1);
		} else {
			throw new RuntimeException("Something went wrong while getting an id for the new campground");
		}
	}

	private Campground mapRowToCampground(SqlRowSet results) {
		Campground theCampground;
		theCampground = new Campground();
		theCampground.setCampground_id(results.getLong("Campground_id"));
		theCampground.setPark_id(results.getLong("park_id"));
		theCampground.setCampground_name(results.getString("name"));
		theCampground.setOpen_from_month(results.getString("open_from_mm"));
		theCampground.setOpen_to_month(results.getString("open_to_mm"));
		theCampground.setDaily_fee(results.getDouble("daily_fee"));
		return theCampground;
	}

	// @Override
	// public Campground findCampgroundById(long campground_id) {
	// // TODO Auto-generated method stub
	// return null;
	// }
	//
	// @Override
	// public Campground findCampgroundByName(String campground_name) {
	// // TODO Auto-generated method stub
	// return null;
	// }

}
