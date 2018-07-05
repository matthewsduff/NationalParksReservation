package com.techelevator;

import java.util.Date;
import java.util.List;
import org.apache.commons.dbcp2.BasicDataSource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;

public class JDBCCampsiteDAO implements CampsiteDAO {

	private JdbcTemplate jdbcCampsiteTemplate;

	public JDBCCampsiteDAO(BasicDataSource dataSource) {
		this.jdbcCampsiteTemplate = new JdbcTemplate(dataSource);
	}

	@Override
	public void addCampsite(Campsite newCampsite) {
		String sqlInsertNewCampground = "INSERT INTO site(site_id,campground_id,site_number,max_occupancy,"
				+ "accessible, max_rv_length,utilities) VALUES (?,?,?,?,?,?,?);";
		newCampsite.setSite_id(getNextCampsiteId());
		jdbcCampsiteTemplate.update(sqlInsertNewCampground, newCampsite.getSite_id(), newCampsite.getCampground_id(),
				newCampsite.getSite_number(), newCampsite.getMax_occupancy(), newCampsite.isAccessible(),
				newCampsite.getMax_rv_length(), newCampsite.isUtilities());

	}

	private long getNextCampsiteId() {
		SqlRowSet nextIdResult = jdbcCampsiteTemplate.queryForRowSet("SELECT nextval('seq_site_site_id')");
		if (nextIdResult.next()) {
			return nextIdResult.getLong(1);
		} else {
			throw new RuntimeException("Something went wrong while getting an id for the new campsite");
		}
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
		return theCampsite;
	}

	@Override
	public void updateCampsite(Campsite campsite) {
		int max_occupancy = campsite.getMax_occupancy();
		boolean accessible= campsite.isAccessible();
		int max_rv_length = campsite.getMax_rv_length();
		boolean utilities = campsite.isUtilities();

		String sqlUpdateCampground = "UPDATE site SET max_occupancy = ?, accessible= ?, max_rv_length = ?, utilities = ? WHERE site_id = ?;";

		jdbcCampsiteTemplate.update(sqlUpdateCampground, max_occupancy,accessible, max_rv_length, utilities);

	}

	@Override
	public void deleteCampsite(long site_id) {
		String sqlDeleteCampsite = "DELETE FROM site WHERE site_id = ?;";
		jdbcCampsiteTemplate.update(sqlDeleteCampsite,site_id);

	}

	@Override
	public Campsite findCampsiteById(long campsite_id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Campsite findCampsiteBySiteNumber(int site_number) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Campsite findCampsiteByReservationDate(Date from_date, Date to_date) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Campsite> displayCampsites(long campground_id) {
		// TODO Auto-generated method stub
		return null;
	}

}
