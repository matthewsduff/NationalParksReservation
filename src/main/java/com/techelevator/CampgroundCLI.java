package com.techelevator;

import javax.sql.DataSource;

import org.apache.commons.dbcp2.BasicDataSource;
import com.techelevator.Park;
import com.techelevator.ParkDAO;
import com.techelevator.JDBCParkDAO;
import com.techelevator.Campground;
import com.techelevator.CampgroundDAO;
import com.techelevator.JDBCCampgroundDAO;
import com.techelevator.Campsite;
import com.techelevator.CampsiteDAO;
import com.techelevator.JDBCCampsiteDAO;
import com.techelevator.Reservation;
import com.techelevator.ReservationDAO;
import com.techelevator.JDBCReservationDAO;




public class CampgroundCLI {
	
	private ParkDAO parkDAO;
	private CampgroundDAO campgroundDAO;
	private CampsiteDAO campsiteDAO;
	private ReservationDAO reservationDAO;
	
	
	
	public static void main(String[] args) {
		BasicDataSource dataSource = new BasicDataSource();
		dataSource.setUrl("jdbc:postgresql://localhost:5432/capstone2");
		dataSource.setUsername("postgres");
		dataSource.setPassword("postgres1");
		
		CampgroundCLI application = new CampgroundCLI(dataSource);
		application.run();
	}

	public CampgroundCLI(DataSource datasource) {
		
		ParkDAO = new JDBCParkDAO(dataSource);
		JDBCCampgroundDAO CampgroundDAO;
	
	
	}
	
	public void run() {
		
	}
	
	

	
	
	
}
