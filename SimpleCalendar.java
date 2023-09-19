package calendar;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.FileNotFoundException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.GregorianCalendar;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

/**
* CalendarView
* @author Katherine Yee 
* @version 1.0 4/21/23 
*/

/**
* The view and controller components of calendar
* view - to display the calendar
* controller - updates model
*/

public class SimpleCalendar extends JFrame {
	
	private static LocalDate cal;
	private static CalendarModel model;
	private static JButton backButton, forwardButton, createButton, quitButton;
	private static JLabel monthLabel, dayLabel;
	private static JPanel days;
	private static JLabel[] dayLabelArr;
	private static JTextArea events;
	private static DateTimeFormatter formatter;
	private static ArrayList<Event> eventsList;
	
	/**
	 * Displays GUI of calendar
	 * @param m - model of calendar
	 */
	public SimpleCalendar(CalendarModel m) {
		cal = LocalDate.now();
		model = m;
		setLayout(null);
		formatter = DateTimeFormatter.ofPattern("M/dd/yy");
		
		//create back button
	    backButton = new JButton("<");
	    backButton.setBounds(200, 10, 40, 40);
        this.add(backButton);
        backButton.addActionListener( event -> {
			cal = cal.minusDays(1);
			displayMonth(cal);
			displayDay(cal);
			model.updateDate(formatter.format(cal));
		});
        
        //create forward button
        forwardButton = new JButton(">");
        forwardButton.setBounds(240, 10, 40, 40);
        this.add(forwardButton);
        forwardButton.addActionListener( event -> {
			cal = cal.plusDays(1);
			displayMonth(cal);
			displayDay(cal);
			model.updateDate(formatter.format(cal));
		});
        
        //create create button to add new event
        createButton = new JButton("CREATE");
	    createButton.setBounds(30, 50, 100, 40);
        this.add(createButton);
        createButton.addActionListener( event -> {
        	new CreateEvent(model);
        	displayMonth(cal);
        	displayDay(cal);
        });
        
        //create quit button to exit GUI
        quitButton = new JButton("QUIT");
        quitButton.setBounds(650, 10, 70, 40);
        this.add(quitButton);
        quitButton.addActionListener( event -> {
        	CalendarModel.quit();
			System.exit(0);
        });	
        
        //populates all events from txt file
        try {
			CalendarModel.popEvents();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
         
        //create month and day labels
        monthLabel = new JLabel("");
        monthLabel.setBounds(35, 120, 150, 15);
        dayLabel = new JLabel(cal.toString());
        dayLabel.setBounds(430, 60, 150, 15);
        
        //create panel to add all month days
        days = new JPanel();
        days.setLayout(new GridLayout(6, 7));
        days.setBounds(30, 140, 225, 225);
        dayLabelArr = new JLabel[49];
		
        //create text area to display all events in day
		events = new JTextArea();
        events.setEditable(false);
        events.setBounds(300, 80, 415, 385);
        events.setLayout(new BoxLayout(events, BoxLayout.Y_AXIS));
        events.setBorder(new EmptyBorder(10, 10, 10, 10));
        Color c1 = new Color(204, 204, 204);  
        events.setBackground(c1);
        
        //add labels and month and day view
        this.add(monthLabel);
        this.add(dayLabel);
        this.add(days);
        this.add(events);
        displayMonth(cal);
		displayDay(cal);
		
		setResizable(false);
		setSize(new Dimension(750, 520));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
	}
	
	/**
	 * Display the month view
	 * @param c - LocalDate of current date
	 */
	public void displayMonth(LocalDate c) {
		monthLabel.setText(c.getMonth() + " " + c.getYear());
		days.removeAll();
		
		GregorianCalendar calGreg = new GregorianCalendar(c.getYear(), c.getMonthValue() - 1, 1);
		int daysOfMonth = calGreg.getActualMaximum(GregorianCalendar.DAY_OF_MONTH);
		int dayofWeek = calGreg.get(GregorianCalendar.DAY_OF_WEEK);

		//create calendar month grid with day names and numbers
		String[] headers = { "S", "M", "T", "W", "T", "F", "S" };
		if(dayofWeek == 7) {
			days.setLayout(new GridLayout(7, 7));
		} else {
			days.setLayout(new GridLayout(6, 7));
		}
		for(int i = 0; i < 7; i++) {
			days.add(new JLabel(headers[i]));
		}
		for(int i = 1; i < dayofWeek; i++) {
			days.add(new JLabel(""));
		}
		for(int i = 1; i <= daysOfMonth; i++) {
			if(i == c.getDayOfMonth()) {
				dayLabelArr[i] = new JLabel("[" + Integer.toString(i) + "]");
			} else {
				dayLabelArr[i] = new JLabel(Integer.toString(i));
			}
			days.add(dayLabelArr[i]);
			
			//mouse listener if day label is clicked
			final int dayLabelIndex = i;
			dayLabelArr[i].addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    cal = LocalDate.of(c.getYear(), c.getMonthValue(), dayLabelIndex);
                    displayMonth(cal);
            		displayDay(cal);
        			model.updateDate(formatter.format(cal));
                }
            });
		}
		days.validate();
		days.repaint();
	}
	
	/**
	 * Display the day view with events
	 * @param c - LocalDate of current date
	 */
	public void displayDay(LocalDate cal) {
		String day = cal.getDayOfWeek() + " " + cal.getMonthValue() + "/" + cal.getDayOfMonth();     
        dayLabel.setText(day);
  
        //display the events in text field
        events.setText("");
        eventsList = model.getEvents();
		for(Event e : eventsList) {
			if(cal.equals(e.getDate())) {			
				String eventPrint = e.getTimeInterval().toString() + " " + e.getName();
				events.append(eventPrint + "\n");
			}
		} 
		events.validate();
        events.repaint();    
	}
}
