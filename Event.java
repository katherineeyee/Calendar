package calendar;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.format.TextStyle;
import java.util.*;

/**
* Event
* @author Katherine Yee 
* * @version 1.0 2/15/23 
*/

/**
 * Represents event for calendar
 */
public class Event implements Comparable<Event> {

	private String name;
	private LocalDate date;
	private String days;
	private LocalDate sd;
	private LocalDate ed;
	private TimeInterval ti;
	private DateTimeFormatter formatter;
	
	
	/**
	 * creates one time event
	 * @param n - name of event
	 * @param d - date of event
	 * @param t - time interval of event
	 */
	public Event(String n, String d, TimeInterval t) {
		name = n;
		formatter = DateTimeFormatter.ofPattern("M/dd/yy");
		date = LocalDate.parse(d, formatter);
		ti = t;
	}
	
	/**
	 * checks if two events occur at same time
	 * @param e1 - event 1
	 * @param e2 - event 2
	 * @return true if overlap
	 * @return false if not overlap
	 */
	public static boolean timeOverlap(Event e1, Event e2) {
		boolean overlap = false;
		TimeInterval e1ti = e1.getTimeInterval();
		TimeInterval e2ti = e2.getTimeInterval();
		LocalTime e1St = e1ti.getSt();
		LocalTime e1Et = e1ti.getEt();
		LocalTime e2St = e2ti.getSt();
		LocalTime e2Et = e2ti.getEt();

		if((e1St.isBefore(e2Et)) && (e1St.isAfter(e2St))) {
			overlap = true;
		}		
		else if((e1Et.isBefore(e2Et) && e1Et.isAfter(e2St))) {
			overlap = true;
		}
		else if(e1St.isBefore(e2St) && e1Et.isAfter(e2Et)) {
			overlap = true;
		}
		else if(e1St.equals(e2St) && e1Et.isAfter(e2Et)) {
			overlap = true;
		}
		return overlap;
	}
	
	/**
	 * prints one time event in correct form
	 */
	public void printOneTimeEvent() {
		String day = date.getDayOfWeek().getDisplayName(TextStyle.FULL, Locale.US);
		String month = date.getMonth().getDisplayName(TextStyle.FULL, Locale.US);
		int dayOfMonth = date.getDayOfMonth();
		System.out.println(day + " " + month + " " + dayOfMonth + " " + ti.toString()+ " "+ name);
	}
	
	public TimeInterval getTimeInterval() {
		return ti;
	}
	
	public String getName() {
		return name;
	}

	public LocalDate getDate() {
		return date;
	}
	
	public String getDays() {
		return days;
	}
	
	public LocalDate getSd() {
		return sd;
	}

	public LocalDate getEd() {
		return ed;
	}
	
	/**
	 * allows for sorting the event arrays
	 */
	public int compareTo(Event e) {
		if(date.isAfter(e.getDate())) {
			return 1;
		}
		else if(date.isBefore(e.getDate())) {
			return -1;
		}
		else {
			if(ti.getSt().isAfter(e.getTimeInterval().getSt())) {
				return 1;
			}
			else if(ti.getSt().isBefore(e.getTimeInterval().getSt())) {
				return -1;
			}
		}
		return 0;
	}	

}
