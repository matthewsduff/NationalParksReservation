package com.techelevator;

import java.util.List;

public interface CampgroundDAO {

	public void addCampground(Campground newCampground);
	public void updateCampground(Campground campground);
	public void deleteCampground(long campground_id);
//	public Campground findCampgroundById(long campground_id);
//	public Campground findCampgroundByName(String campground_name);
	public List<Campground> displayCampgrounds(long park_id);
	
}
