package com.techelevator;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

public interface CampsiteDAO {

	public void addCampsite(Campsite newCampsite);
	public void updateCampsite(Campsite campsite);
	public void deleteCampsite(long campsite_id);
//	public Campsite findCampsiteById(long campsite_id);
//	public Campsite findCampsiteBySiteNumber(int site_number);
	public List<Campsite> findCampsitesByReservationDate(long campground_id, LocalDate from_date,LocalDate to_date);
	
	
	
}
