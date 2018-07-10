package com.techelevator;

import java.util.List;

public interface CampgroundDAO {

	public void addCampground(Campground newCampground);

	public void updateCampground(Campground campground);

	public void deleteCampground(long campground_id);

	public List<Campground> displayCampgrounds(long park_id);

	public boolean campgroundOpen(Campground campground, long userInputMonth);

}
