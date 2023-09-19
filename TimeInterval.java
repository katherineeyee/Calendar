package calendar;

import java.time.*;
import java.time.format.DateTimeFormatter;

/**
* TimeInterval
* @author Katherine Yee 
* * @version 1.0 2/15/23 
*/

/**
 * Represents time interval for event in calendar
 *
 */
public class TimeInterval {
	
	private LocalTime st;
	private LocalTime et;
	private DateTimeFormatter formatter;
	
	
	/**
	 * creates time interval using start and end time strings
	 * @param start - starting time of event
	 * @param end - ending time of event
	 */
	public TimeInterval(String start, String end) {
		formatter = DateTimeFormatter.ofPattern("H:mm");
		LocalTime t1 = LocalTime.parse(start, formatter);
        
        LocalTime t2 = LocalTime.parse(end, formatter);
		st = t1;
		et = t2;
	}

	//gets start time
	public LocalTime getSt() {
		return st;
	}

	//gets end time
	public LocalTime getEt() {
		return et;
	}
	
	/**
	 * puts the time interval into a readable string
	 */
	public String toString() {
		formatter = DateTimeFormatter.ofPattern("H:mm");
		String start = st.format(formatter);
		String end = et.format(formatter);
		return start + " - " + end;
	}
	
}
