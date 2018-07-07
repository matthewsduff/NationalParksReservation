package com.techelevator;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

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

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.Month;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;

public class CampgroundCLI {
	// main menu set up
	private Menu menu;
	private ParkDAO parkDAO;
	private CampgroundDAO campgroundDAO;
	private CampsiteDAO campsiteDAO;
	private ReservationDAO reservationDAO;
	SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd");

	private static final String ACADIA = "Acadia";
	private static final String ARCHES = "Arches";
	private static final String CUYAHOGA = "Cuyahoga National Valley Park";
	private static final String MAIN_MENU_OPTION_QUIT = "Quit";
	private static final String[] MAIN_MENU_OPTIONS = new String[] { ACADIA, ARCHES, CUYAHOGA, MAIN_MENU_OPTION_QUIT };

	private static final String MENU_OPTION_RETURN_TO_MAIN = "Return to main menu";
	// variables used throughout menu system

	public static void main(String[] args) {
		CampgroundCLI application = new CampgroundCLI();
		application.run();
	}

	public CampgroundCLI() {
		this.menu = new Menu(System.in, System.out);

		// set up datasource
		BasicDataSource dataSource = new BasicDataSource();
		dataSource.setUrl("jdbc:postgresql://localhost:5432/capstone2");
		dataSource.setUsername("postgres");
		dataSource.setPassword("postgres1");

		// instantiate DAOS
		parkDAO = new JDBCParkDAO(dataSource);
		campgroundDAO = new JDBCCampgroundDAO(dataSource);
		campsiteDAO = new JDBCCampsiteDAO(dataSource);
		reservationDAO = new JDBCReservationDAO(dataSource);

	}

	int userParkChoice = 0;

	public void run() {

		displayApplicationBanner(); // welcome banner
		printHeading("Welcome, please Select a National Park");

		// main menu
		while (true) {
			String choice = (String) menu.getChoiceFromOptions(MAIN_MENU_OPTIONS);
			if (choice.equals(ACADIA)) {
				System.out.println();
				System.out.println(parkDAO.displayParkInformation(1));
				userParkChoice = 1;
				handlePark();
			} else if (choice.equals(ARCHES)) {
				System.out.println();
				System.out.println(parkDAO.displayParkInformation(2));
				userParkChoice = 2;
				handlePark();
			} else if (choice.equals(CUYAHOGA)) {
				System.out.println();
				System.out.println(parkDAO.displayParkInformation(3));
				userParkChoice = 3;
				handlePark();
			} else if (choice.equals(MAIN_MENU_OPTION_QUIT)) {
				System.exit(0);
			}
		}
	}

	// campground menu
	private static final String VIEW_CAMPGROUNDS = "View Campgrounds";
	private static final String SEARCH_FOR_RESERVATION = "Search for Reservation";
	private static final String MENU_OPTION_RETURN_TO_PREVIOUS_SCREEN = "Return to Previous Screen";
	private static final String[] PARK_MENU_OPTIONS = new String[] { VIEW_CAMPGROUNDS, SEARCH_FOR_RESERVATION,
			MENU_OPTION_RETURN_TO_PREVIOUS_SCREEN };

	private void handlePark() {

		printHeading("Select A Command");
		String choice = (String) menu.getChoiceFromOptions(PARK_MENU_OPTIONS);

		if (choice.equals(VIEW_CAMPGROUNDS)) {
			printCampgroundsToScreen(userParkChoice);

			try {
				handleCampground();
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else if (choice.equals(SEARCH_FOR_RESERVATION)) {
			try {
				handleCampground();
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		else if (choice.equals(MENU_OPTION_RETURN_TO_PREVIOUS_SCREEN)) {

		}

	}

	// reservation menu
	private static final String SEARCH_FOR_AVAILABLE_RESERVATION = "Search for Available Reservation";

	private static final String[] SEARCH_FOR_AVAILABLE_OR_RETURN_TO_PREVIOUS = new String[] {
			SEARCH_FOR_AVAILABLE_RESERVATION, MENU_OPTION_RETURN_TO_PREVIOUS_SCREEN };

	private void handleCampground() throws ParseException {
		printHeading("Select A Command");
		String choice = (String) menu.getChoiceFromOptions(SEARCH_FOR_AVAILABLE_OR_RETURN_TO_PREVIOUS);

		if (choice.equals(SEARCH_FOR_AVAILABLE_RESERVATION)) {
			printCampgroundsToScreen(userParkChoice);
			Long userSelectedCampground = Long
					.parseLong(getUserInput("Which campground would you like? (enter 0 to cancel)"));
			if (userSelectedCampground == 0) {
				handleCampground();
			} else {
				LocalDate todaysDate = null;
				int dateComparer;

				Date userDesiredArrivalDate = dateFormatter
						.parse(getUserInput("What is your desired arrival date? (yyyy-mm-dd)"));
				
				//Checks that entered arrival date is not in the past
				LocalDate userDesiredArrivalDateLocal = convertDateToLocalDate(userDesiredArrivalDate);
				dateComparer = todaysDate.now().compareTo(userDesiredArrivalDateLocal);
				if (dateComparer > 0) {
					System.out.println("Your arrival date cannot be in the past");
					handleCampground();
				}
				//Checks that entered departure date is not before your arrival date
				Date userDesiredDepartureDate = dateFormatter
						.parse(getUserInput("What is your desired departure date? (yyyy-mm-dd)"));
				LocalDate userDesiredDepartureDateLocal = convertDateToLocalDate(userDesiredDepartureDate);
				dateComparer = userDesiredArrivalDateLocal.compareTo(userDesiredDepartureDateLocal);
				if (dateComparer > 0) {
					System.out.println("Your departure date cannot be before your arrival date");
					handleCampground();
				}

				Long lengthOfStay = calculateLengthOfUserReservation(userDesiredArrivalDateLocal,
						userDesiredDepartureDateLocal);

				List<Campsite> availableCampsites = new ArrayList<Campsite>();
				availableCampsites = campsiteDAO.findCampsitesByReservationDate(userSelectedCampground,
						userDesiredArrivalDate, userDesiredDepartureDate);
				if (availableCampsites.size() > 0) {
					System.out.println("Site No." + "Max Occup" + "Accessible?" + "Max RV Length" + "Utility" + "Cost");
					for (Campsite i : availableCampsites) {
						System.out.println(i.getSite_number() + "\t" + i.getMax_occupancy() + "\t" + i.isAccessible()
								+ "\t" + i.getMax_rv_length() + "\t" + i.isUtilities() + "\t"
								+ i.getCostForStay(lengthOfStay, i.getDailyFee()));
					}
					Long userReservedCampsite = Long
							.parseLong(getUserInput("Which site should be reserved (enter 0 to quit)?"));
					if (userReservedCampsite == 0) {
						run();
					}
					String userReservationName = getUserInput("What name should the reservation be made under?");

					Reservation newReservation = new Reservation();
					newReservation.setSite_id(userSelectedCampground);
					newReservation.setName(userReservationName);
					newReservation.setReservation_from_date(userDesiredArrivalDate);
					newReservation.setReservation_to_date(userDesiredDepartureDate);

					Date currentDate = new Date();
					dateFormatter.format(currentDate);
					newReservation.setReservation_created_date(currentDate);
					System.out.println("The reservation has been made and the confirmation id is "
							+ reservationDAO.createNewReservation(newReservation));

				} else {
					System.out.println("Sorry, there are no campsites that fit your search criteria");
					String userAnswer = getUserInput("Would you like to enter an alternate date range? (Y or N)");
					if (userAnswer.equalsIgnoreCase("Y")) {
						handleCampground();
					} else if (userAnswer.equalsIgnoreCase("N")) {
						run();
					}

				}

			}
		}
	}

	private void printHeading(String headingText) {
		System.out.println("\n" + headingText);
		for (int i = 0; i < headingText.length(); i++) {
			System.out.print("-");
		}
		System.out.println();
	}

	@SuppressWarnings("resource")
	private String getUserInput(String prompt) {
		System.out.print(prompt + " >>> ");
		return new Scanner(System.in).nextLine();
	}

	private void displayApplicationBanner() {
		System.out.println(" _   _       _______ _____ ____  _   _          _        _____        _____  _  __ _____ ");
		System.out.println(
				"| \\ | |   /\\|__   __|_   _/ __ \\| \\ | |   /\\   | |      |  __ \\ /\\   |  __ \\| |/ // ____| ");
		System.out.println(
				"|  \\| |  /  \\  | |    | || |  | |  \\| |  /  \\  | |      | |__) /  \\  | |__) | ' /| (___");
		System.out.println(
				"| . ` | / /\\ \\ | |    | || |  | | . ` | / /\\ \\ | |      |  ___/ /\\ \\ |  _  /|  <  \\___ \\ ");
		System.out.println(
				"| |\\  |/ ____ \\| |   _| || |__| | |\\  |/ ____ \\| |____  | |  / ____ \\| | \\ \\| . \\ ____) |");
		System.out.println(
				"|_| \\_/_/    \\_\\_|  |_____\\____/|_| \\_/_/    \\_\\______| |_| /_/    \\_\\_|  \\_\\_|\\_\\_____/");

		System.out.println();

	}

	public Long calculateLengthOfUserReservation(LocalDate fromDate, LocalDate toDate) {
		Long daysOfStay = ChronoUnit.DAYS.between(fromDate, toDate);
		return daysOfStay;
	}

	public LocalDate convertDateToLocalDate(Date dateToConvert) {

		ZoneId defaultZoneId = ZoneId.systemDefault();
		Instant instant = dateToConvert.toInstant();
		return instant.atZone(defaultZoneId).toLocalDate();

	}

	public String printCampgroundsToScreen(long userParkChoice) {

		List<Campground> selectedCampgrounds = new ArrayList<Campground>();
		selectedCampgrounds = campgroundDAO.displayCampgrounds((long) userParkChoice);
		System.out.println((String.format("%-31s", "Name")) + (String.format("%-12s", "Opening Month"))
				+ (String.format("%-12s", "Closing Month")) + (String.format("%-12s", "Daily Fee")));
		int counter = 1;

		for (Campground i : selectedCampgrounds) {
			System.out.println(counter + ") " + String.format("%-31s", i.getCampground_name()) + "\t\t"
					+ Month.of(Integer.parseInt(i.getOpen_from_month())) + "\t\t"
					+ Month.of(Integer.parseInt(i.getOpen_to_month())) + "\t" + i.getDaily_fee());
			counter++;
		}
		return null;
	}

}
