package calendar;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;

/**
* CreateEvent
* @author Katherine Yee 
* @version 1.0 4/21/23 
*/

/**
* GUI to create a new event in calendar
*/

public class CreateEvent extends JFrame {
	private CalendarModel model;
	private JButton saveButton;
	private JTextField name, st, et;
	private JLabel date, to;
	private JPanel panel;
	private ArrayList<Event> events;

	/**
	 * Displays GUI to create new event
	 * @param model - model of calendar
	 */
	public CreateEvent(CalendarModel model) {
		this.model = model;
		events = model.getEvents();
		
		setLayout(new BorderLayout());
		setSize(350, 100);
		
		//create text fields for event name, start time, and end time
		//and label for the date
		name = new JTextField();
		name.setPreferredSize(new Dimension(100, 25));
		st = new JTextField();
		st.setPreferredSize(new Dimension(60, 25));
		et = new JTextField();
		et.setPreferredSize(new Dimension(60, 25));
		date = new JLabel(model.getTodayDate());
		date.setPreferredSize(new Dimension(100, 25));
		to = new JLabel("to");
		to.setPreferredSize(new Dimension(20, 25));
		
		//create panel and add text fields and labels
		panel = new JPanel();
		panel.setLayout(new FlowLayout());
		panel.add(date);
		panel.add(st);
		panel.add(to);
		panel.add(et);
		
		//create save button
		saveButton = new JButton("SAVE");
		saveButton.addActionListener(event -> {
			TimeInterval ti = new TimeInterval(st.getText(), et.getText());
			Event newEvent = new Event(name.getText(), date.getText(), ti);
			boolean over = false;
			//check if new event has time conflict
			for(Event ev : events) {
				if(ev.getDate().equals(newEvent.getDate())) {
					if(Event.timeOverlap(ev, newEvent)) {
						over = true;
						break;
					} else {
						over = false;
					}
				}
			}
			//add event to model if no time conflict
			if(!over) {
				model.update(newEvent);
				dispose();
			} else {
				//create new frame displaying an error
				JFrame errorFrame = new JFrame("Error");
				errorFrame.setSize(200, 100);
				JLabel timeLabel = new JLabel("Time Overlap");
				errorFrame.add(timeLabel);
				errorFrame.setResizable(false);
				errorFrame.setVisible(true);
			}
			Collections.sort(events);
		});
		panel.add(saveButton);
		
		//add panels to frame
		add(name, BorderLayout.NORTH);
		add(panel, BorderLayout.SOUTH);
		setResizable(false);
		setVisible(true);
	}
}
