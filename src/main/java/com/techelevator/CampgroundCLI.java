package com.techelevator;

import javax.sql.DataSource;

import org.apache.commons.dbcp2.BasicDataSource;

public class CampgroundCLI {
	public static void main(String[] args) {
		BasicDataSource dataSource = new BasicDataSource();
		dataSource.setUrl("jdbc:postgresql://localhost:5432/capstone2");
		dataSource.setUsername("postgres");
		dataSource.setPassword("postgres1");
		
		CampgroundCLI application = new CampgroundCLI(dataSource);
		application.run();
		
		JDBCParkDAO testDAO = new JDBCParkDAO(dataSource);
		System.out.println(testDAO.displayAllParkNames());
		System.out.println(testDAO.displayParkInformation((long) 1));
	}

	public CampgroundCLI(DataSource datasource) {
		// create your DAOs here
	}
	
	public void run() {
		
	}
	
	

	
	
	
}
