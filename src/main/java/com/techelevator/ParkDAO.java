package com.techelevator;

import java.util.List;

public interface ParkDAO {

	public void addPark(Park newPark);
	public void updatePark(Park park);
	public void deletePark(long park_id);
//	public Park findParkByID(long park_id);
//	public Park findParkByName(String park_name);
	public List<String> displayAllParkNames();
	public String displayParkInformation(long park_id);
	public String displayParkDescription(long park_id);
	
	
}
