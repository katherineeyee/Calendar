package calendar;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.TextStyle;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Locale;
import java.util.Scanner;

import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;


/**
* CalendarModel
* @author Katherine Yee 
* @version 1.0 4/21/23 
*/

/**
* The model component of calendar to hold the events and listeners of the calendar
*/
public class CalendarModel {

	private static ArrayList<Event> events = new ArrayList<Event>();
	private ArrayList<ChangeListener> listeners;
	private String currentDate;
	
	/**
	 * Represent model of calendar
	 */
	public CalendarModel() {
		this.listeners = new ArrayList<>();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("M/dd/yy");
		LocalDate cal = LocalDate.now();
		currentDate = formatter.format(cal);
	}
	
	/**
	 * set current date
	 * @param date - currentDate
	 */
	public void setTodayDate(String date) {
		currentDate = date;
	}
	
	/**
	 * get current date
	 * @return currentDate
	 */
	public String getTodayDate() {
		return currentDate;
	}
	
	/**
	 * get events list
	 * @return events
	 */
	public ArrayList<Event> getEvents() {
		return events;
	}
	
	/**
	 * update events and listeners list
	 * @param e - add to events list
	 */
	public void update(Event e) {
		events.add(e);
		for (ChangeListener l : listeners) {
			l.stateChanged(new ChangeEvent(this));
		}
	}
	
	/**
	 * update current date
	 * @param d - new date
	 */
	public void updateDate(String d){
		this.setTodayDate(d);
		for (ChangeListener l : listeners) {
			l.stateChanged(new ChangeEvent(this));
		}
	}
	
	/**
	 * populates events from events txt file
	 * @throws FileNotFoundException
	 */
	public static void popEvents() throws FileNotFoundException {
		File file = new File("events.txt");
		Scanner scan = new Scanner(file);
		String name = "";
		Event event;
		while(scan.hasNextLine()) {
			name = scan.nextLine();
			String data = scan.nextLine();
			String[] split = data.split(" ");			
			String t1 = split[1];
			String t2 = split[2];	
			TimeInterval ti = new TimeInterval(t1, t2);
			event = new Event(name, split[0], ti);
			events.add(event);
			
		}
		scan.close();
		System.out.println("Loading is done!");
		Collections.sort(events);
	}

	/**
	 * quits the GUI and writes new txt file of all events
	 */
	public static void quit() {
		File f = new File("/Users/katherineyee/Downloads/events.txt");
		try {
			f.createNewFile();
		} catch (IOException e) {
			e.printStackTrace();
		}
		try {
			FileWriter fWriter = new FileWriter(f.getAbsoluteFile());
			BufferedWriter bWriter = new BufferedWriter(fWriter);
			
			bWriter.write("ONE TIME EVENTS");
			bWriter.write("\n");
			String str = "";
			for(Event e :events) {
				LocalDate date = e.getDate();
				String day = date.getDayOfWeek().getDisplayName(TextStyle.FULL, Locale.US);
				String month = date.getMonth().getDisplayName(TextStyle.FULL, Locale.US);
				int dayOfMonth = date.getDayOfMonth();
				str = day + " " + month + " " + dayOfMonth + " " + e.getTimeInterval().toString()+ " "+ e.getName();
				bWriter.write(str);
				bWriter.write("\n");
			}
			bWriter.write("\n");
			bWriter.close();
			System.out.println("Successfully created events.txt");
		} catch (IOException e) {
			System.out.println("Error");
			e.printStackTrace();
		}
	}
}
