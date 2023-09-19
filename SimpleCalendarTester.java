package calendar;


/**
* SimpleCalendarTester
* @author Katherine Yee 
* @version 1.0 4/21/23 
*/

/**
* Tests the simple calendar GUI
*/

public class SimpleCalendarTester {
	
	public static void main(String[] args) {
		CalendarModel model = new CalendarModel();
		SimpleCalendar calendar = new SimpleCalendar(model);
	}
}
