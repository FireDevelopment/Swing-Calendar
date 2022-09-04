//imports
import javax.imageio.ImageIO;

//main libraries
import java.awt.*;
import javax.swing.*;

import java.io.*;
import java.time.*;

//part of libraries
import java.awt.event.*;
import java.awt.image.*;

//utils
import utils.ScaleUtils;
import static utils.ScaleUtils.*;
import static utils.HolidayUtils.*;

//static imports
import static java.awt.Font.*;



public class Calendar extends Thread {
	
	//initialize the window
	public static JFrame root;
	
	//images
	private static BufferedImage calendarText = null; //they will be defined later
	private static BufferedImage devText = null;
	private static BufferedImage helpLogo = null;
	private static BufferedImage exitLogo = null;
	private static BufferedImage settingsLogo = null;
	private static BufferedImage dailyInfoLogo = null;
	private static BufferedImage newEventLogo = null;
	private static BufferedImage dateLogo = null;
	private static BufferedImage weeklyLogo = null;
	private static BufferedImage rightArrowLogo = null;
	private static BufferedImage leftArrowLogo = null;
	private static BufferedImage upArrowLogo = null;
	private static BufferedImage downArrowLogo = null;
	private static BufferedImage helpText = null;
	private static BufferedImage settingsText = null;
	private static BufferedImage ManageText = null;
	
	//keep track of screen
	public static byte screen = 1;
	
	//buttons
	private static JButton newEventButton;
	private static JButton dailyInfoButton;
	private static JButton manageButton;
	private static JButton helpButton;
	private static JButton exitButton;
	private static JButton settingsButton;
	private static JButton dailyInfoBackButton;
	private static JButton newEventDateEventButton;
	private static JButton newEventWeeklyEventButton;
	private static JButton newEventBackButton;
	private static JButton rightArrowButton;
	private static JButton leftArrowButton;
	private static JButton upArrowButton;
	private static JButton downArrowButton;
	private static JButton dateEventNextButton;
	private static JButton dateEventBackButton;
	private static JButton dateEventFinishButton;
	private static JButton dateEventTitleBackButton;
	private static JButton newEventFinishBackButton;
	private static JButton weeklyEventNextButton;
	private static JButton weeklyEventBackButton;
	private static JButton weeklyEventFinishButton;
	private static JButton weeklyEventTitleBackButton;
	private static JButton helpBackButton;
	private static JButton darkThemeButton;
	private static JButton lightThemeButton;
	private static JButton settingsBackButton;
	private static JButton manageEditButton;
	private static JButton manageDeleteButton;
	private static JButton manageBackButton;
	private static JButton manageSaveButton;
	private static JButton manageCancelButton;
	private static JButton manageDeleteAllButton;
	
	//label
	private static JLabel logoLabel;
	private static JLabel devLabel;
	private static JLabel dailyInfoLabel;
	private static JLabel greetingLabel;
	private static JLabel timeLabel;
	private static JLabel eventsHeaderLabel;
	private static JLabel holidayHeaderLabel;
	private static JLabel holidayLabel;
	private static JLabel nextholidayLabel;
	private static JLabel newEventLabel;
	private static JLabel newEventEventType;
	private static JLabel dateLabel;
	private static JLabel weeklyLabel;
	private static JLabel dateEventDatePrompt;
	private static JLabel weeklyEventDatePrompt;
	private static JLabel monthChooserLabel;
	private static JLabel dayChooserLabel;
	private static JLabel dateEventTitlePrompt;
	private static JLabel eventCreatedConfirmationLabel;
	private static JLabel weekdayChooserLabel;
	private static JLabel weeklyEventTitlePrompt;
	private static JLabel helpLabel;
	private static JLabel helpWelcomeLabel;
	private static JLabel settingsLabel;
	private static JLabel settingsPromptLabel;
	private static JLabel ManageLabel;
	private static JLabel daysNoticeLabel;
	private static JLabel manageEventNameLabel;
	private static JLabel settingsVersionLabel;
	private static JLabel newEventEventName;
	private static JLabel newEventDate;
	private static JLabel newEventCode;
	
	//pass on stuff to new event created screen
	private static String eventName;
	private static String eventDate;
	private static String eventCode;
	
	//text fields
	private static JTextField dateEventTextField;
	private static JTextField weeklyEventTextField;
	private static JTextField manageTextField;
	
	//text areas
	private static JTextArea helpHelpLabel;
	private static JTextArea eventsLabel;

	//JCheckBox
	private static JCheckBox repeatYearlyCheck;
	private static JCheckBox manageCheckBox;
	
	//JComboBox
	private static JComboBox<String> manageFilter;
	
	//JList
	private static JList<String> manageList;
	
	//jlist list model
	private static DefaultListModel<String> l1 = new DefaultListModel<>();  
	
	//manage list variables
	private static String manageSelectionKey;
	private static int manageLength;
	
	//event creating date
	private static int monthChooserMonth = 1;
	private static int dayChooserMonth = 1;
	private static final String[] months = {"January", "February", "March", "April", "May", "June", "July",
			"August", "September", "October", "November", "December"};
	private static int weekDayChooserDay = 1;
	private static final String[] weekdays = {"Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday"};
	
	
	//menubar
	private static boolean menuBarEnabled = true;
	private static JMenuBar menuBar = new JMenuBar();
	
	//time
	private static LocalDateTime date = LocalDateTime.now();
	private static int hour = date.getHour();
	
	private static int oldMinute = date.getMinute();
	private static int minute = date.getMinute();
	
	//color
	public static Color cBlue = new Color(33, 16, 140); //font color
	public static Color themeColor = Color.WHITE; //theme for calendar, can be changed to black
	public static boolean darkTheme = false;
	
	//other settings
	public static int daysNotice;
	
	//manage list arrows variable thing
	public static int manageListArrow = 0;
	
	//fonts
	public static Font buttonFont;
	public static Font timeFont;
	public static Font greetingFont;
	public static Font infoFont;
	
	//calendar version
	public static final String version = "v1.1.2";

	public static void main(String[] args) {
		//get theme
		//getColorTheme();
		
		//get settings
		getSettings();
		
		//create the window
		createWindow();
		
		//load images for the pages
		loadImages();
		
		//initialize components
		initializeComponents();
		
		//put components on scree
		renderScreen();
		
		//show the window
		showWindow();
		
		//set up all the holidays
		initHolidays();
		
		//load events
		readEvents();
		
		//delete old events
		deleteOldEvents();
		
		//check for window resize
		Calendar WindowResizeThread = new Calendar();
		WindowResizeThread.start();
	}
	
	private static void changeScreen(byte newScreen) {
		//remove old screen
		if (screen == 1) {
			unbindHome();
		}
		if (screen == 2) {
			unbindDailyInfo();
		}
		if (screen == 3) {
			unbindNewEventHome();
		}
		if (screen == 4) {
			unbindDateEventChooser();
		}
		if (screen == 5) {
			unbindDateEventTitle();
		}
		if (screen == 6) {
			unbindNewEventFinished();
		}
		if (screen == 7) {
			unbindWeeklyEventCreator();
		}
		if (screen == 8) {
			unbindWeeklyEventTitle();
		}
		if (screen == 9) {
			unbindHelpPage();
		}
		if (screen == 10) {
			unbindSettingsPage();
		}
		if (screen == 11) {
			unbindManagePage();
		}
		if (screen == 12) {
			unbindManageEditPage();
		}
		
		//tell the program the new screen
		screen = newScreen;
		
		//render the new screen
		renderScreen();
	}
	
	private static void unbindDateEventTitle() {	
		//remove buttons
		root.remove(dateEventFinishButton);
		root.remove(dateEventTitleBackButton);
		
		//remove text fields
		root.remove(dateEventTextField);
		
		//remove image labels
		root.remove(dateLabel);
		
		//remove labels
		root.remove(dateEventTitlePrompt);
		
		//remove checkbox
		root.remove(repeatYearlyCheck);
		
		
	}
	
	private static void unbindWeeklyEventTitle() {	
		//remove buttons
		root.remove(weeklyEventFinishButton);
		root.remove(weeklyEventTitleBackButton);
		
		//remove text fields
		root.remove(weeklyEventTextField);
		
		//remove image labels
		root.remove(weeklyLabel);
		
		//remove labels
		root.remove(weeklyEventTitlePrompt);
		
		
	}
	
	private static void unbindDateEventChooser() {	
		//remove buttons
		root.remove(upArrowButton);
		root.remove(downArrowButton);
		root.remove(leftArrowButton);
		root.remove(rightArrowButton);
		root.remove(dateEventBackButton);
		root.remove(dateEventNextButton);
		
		//remove image labels
		root.remove(monthChooserLabel);
		root.remove(dayChooserLabel);
		root.remove(dateEventDatePrompt);
		root.remove(dateLabel);
		
		
	}
	
	private static void unbindHome() {	
		//remove buttons
		root.remove(dailyInfoButton);
		root.remove(newEventButton);
		root.remove(manageButton);
		root.remove(helpButton);
		root.remove(exitButton);
		root.remove(settingsButton);
		
		//remove image labels
		root.remove(logoLabel);
		root.remove(devLabel);
		
	}
	
	private static void unbindDailyInfo() {	
		//remove buttons
		root.remove(dailyInfoBackButton);
		
		//remove image labels
		root.remove(dailyInfoLabel);
		
		//remove text labels
		root.remove(holidayHeaderLabel);
		root.remove(holidayLabel);
		root.remove(nextholidayLabel);
		root.remove(eventsHeaderLabel);
		root.remove(eventsLabel);
		root.remove(greetingLabel);
		root.remove(timeLabel);
		
	}
	
	public static void unbindNewEventHome() {
		//remove buttons
		root.remove(newEventBackButton);
		root.remove(newEventWeeklyEventButton);
		root.remove(newEventDateEventButton);
		
		//remove image labels
		root.remove(newEventLabel);
		
		//remove labels
		root.remove(newEventEventType);
	}
	
	public static void unbindNewEventFinished() {
		//remove buttons
		root.remove(newEventFinishBackButton);
		
		//remove labels
		root.remove(newEventLabel);
		root.remove(eventCreatedConfirmationLabel);
		root.remove(newEventEventName);
		root.remove(newEventDate);
		root.remove(newEventCode);
	}
	
	public static void unbindWeeklyEventCreator() {
		//remove buttons
		root.remove(leftArrowButton);
		root.remove(rightArrowButton);
		root.remove(weeklyEventBackButton);
		root.remove(weeklyEventNextButton);
				
		//remove image labels
		root.remove(weeklyLabel);
		
		//remove labels
		root.remove(weekdayChooserLabel);
		root.remove(weeklyEventDatePrompt);
	}
	
	public static void unbindHelpPage() {
		//remove buttons
		root.remove(helpBackButton);
				
		//remove image labels
		root.remove(helpLabel);
		
		//remove labels
		root.remove(helpWelcomeLabel);
		
		//remove text areas
		root.remove(helpHelpLabel); //ignore the fact it says label in the component name

	}
	
	public static void unbindSettingsPage() {
		//remove buttons
		root.remove(darkThemeButton);
		root.remove(lightThemeButton);
		root.remove(settingsBackButton);
		root.remove(rightArrowButton);
		root.remove(leftArrowButton);
				
		//remove image labels
		root.remove(settingsLabel);
		
		//remove labels
		root.remove(settingsPromptLabel);
		root.remove(daysNoticeLabel);
		root.remove(settingsVersionLabel);

	}
	     
	public static void unbindManagePage() {
		//remove list
		root.remove(manageList);
		
		//remove buttons
		root.remove(manageBackButton);
		root.remove(manageEditButton);
		root.remove(manageDeleteButton);
		root.remove(manageDeleteAllButton);
		root.remove(upArrowButton);
		root.remove(downArrowButton);
		
		//remove combobox
		root.remove(manageFilter);
		
		//remove image label
		root.remove(ManageLabel);
	}
	
	public static void unbindManageEditPage() {
		
		//remove buttons
		root.remove(manageSaveButton);
		root.remove(manageCancelButton);
		
		//remove image label
		root.remove(ManageLabel);
		
		//remove label
		root.remove(manageEventNameLabel);
		
		//remove text field
		root.remove(manageTextField);
		
		//remove checkbox
		root.remove(manageCheckBox);
		
	}
	
	private static void updateFonts() {
		//create fonts for different screens
		
		//setup the font
		int fontSize = Math.round(windowWidth/83);
		if(fontSize < 12) { fontSize = 12; } //the font can't be smaller than 12
										
		//create the font
		buttonFont = new Font("dialog", BOLD, fontSize);
		
		//time font
		int timefontSize = Math.round(windowWidth/30);
		if(timefontSize < 14) { timefontSize = 10; } //the font can't be smaller than 12
												
		//create the font
		timeFont = new Font("Segoe UI", PLAIN, timefontSize);
		
		//greeting font
		int greetingfontSize = Math.round(windowWidth/25);
		if(greetingfontSize < 16) { greetingfontSize = 12; } //the font can't be smaller than 12
												
		//create the font
		greetingFont = new Font("Segoe UI", PLAIN, greetingfontSize);
		
		//info font
		int infofontSize = Math.round(windowWidth/50);
		if(infofontSize < 9) { infofontSize = 9; } //the font can't be smaller than 12
								
		//create the font
		infoFont = new Font("Segoe UI", PLAIN, infofontSize);
		
	}
	
	private static void renderScreen() {
		//update the font
		updateFonts();
		
		//update background color
		root.getContentPane().setBackground(themeColor);
		
		//set up button positions and add them to the window
		if (screen == 1) {
			updateHome();
		}
		if (screen == 2) {
			updateDailyInfo();
		}
		
		if (screen == 3) {
			updateNewEventHome();
		}
		
		if (screen == 4) {
			updateDateEventCreator();
		}
		
		if (screen == 5) {
			updateDateEventTitlePage();
		}
		
		if (screen == 6) {
			updateNewEventFinished();
		}
		if (screen == 7) {
			updateWeeklyEventCreator();
		}
		if (screen == 8) {
			updateWeeklyEventTitlePage();
		}
		if (screen == 9) {
			updateHelpPage();
		}
		if (screen == 10) {
			updateSettingsPage();
		}
		if (screen == 11) {
			updateManageHomePage();
		}
		if (screen == 12) {
			updateManageEditScreen();
		}
		
		//redraw the entire window
		root.repaint();
	}
	
	private static void updateManageEditScreen() {
		
		//image label of page
		ManageLabel.setIcon(ScaleUtils.scaleImage(ManageText, windowWidth/25*13*1.1, windowHeight/110*19*1.5));
		ManageLabel.setBounds(placeX(0.5, windowWidth/10*6), placeY(0, 130), windowWidth/10*6, 130);
		
		//manage event name label
		manageEventNameLabel.setBounds(placeX(0.5, windowWidth/4), placeY(0.25, windowHeight/6), windowWidth/4, windowHeight/6);
		manageEventNameLabel.setForeground(cBlue);
		manageEventNameLabel.setFont(greetingFont);
		
		//text field
		manageTextField.setFont(timeFont);
		manageTextField.setBounds(placeX(0.5, windowWidth/3), placeY(0.4, windowHeight/10), windowWidth/3, windowHeight/10);
		
		manageTextField.setHorizontalAlignment(JTextField.CENTER);
		manageTextField.setText(events.get(manageSelectionKey));
		
		//check box
		manageCheckBox.setFont(infoFont);
		manageCheckBox.setBounds(placeX(0.5, windowWidth/6), placeY(0.55, windowHeight/10), windowWidth/6, windowHeight/10);
		
		if (manageSelectionKey.substring(0,2).equals("02")) {
			manageCheckBox.setSelected(true);
		}
		
		//cancel button
		manageCancelButton.setBounds(placeX(0.4, windowWidth/7), placeY(0.7, windowHeight/10), windowWidth/7, windowHeight/10);
				
		//save button
		manageSaveButton.setBounds(placeX(0.6, windowWidth/7), placeY(0.7, windowHeight/10), windowWidth/7, windowHeight/10);
		
		//set backgrounds
		manageCheckBox.setBackground(themeColor);
		manageSaveButton.setBackground(themeColor);
		manageCancelButton.setBackground(themeColor);
		manageTextField.setBackground(themeColor);
		
		//dark theme change button font color
		
		if (darkTheme) {
			manageSaveButton.setForeground(cBlue);
			manageCancelButton.setForeground(cBlue);
		} else {
			manageSaveButton.setForeground(null);
			manageCancelButton.setForeground(null);
		}
		
		
		//add labels
		root.add(ManageLabel);
		root.add(manageEventNameLabel);
		
		//add textfields
		root.add(manageTextField);
		
		//buttons
		root.add(manageCancelButton);
		root.add(manageSaveButton);
		
		//checkbox
		if (manageSelectionKey.substring(0,2).equals("01") || manageSelectionKey.substring(0,2).equals("02")) {
			root.add(manageCheckBox);
		}
	}
	
	private static void updateManageHomePage() {
		
		//image label of page
		ManageLabel.setIcon(ScaleUtils.scaleImage(ManageText, windowWidth/25*13*1.1, windowHeight/110*19*1.5));
		ManageLabel.setBounds(placeX(0.5, windowWidth/10*6), placeY(0, 130), windowWidth/10*6, 130);
		
		
		l1.clear(); //clear the list
		
		DefaultListCellRenderer listRenderer = new DefaultListCellRenderer(); //get the cell renderer
	    listRenderer.setHorizontalAlignment(DefaultListCellRenderer.CENTER); // center-aligned items
	    manageFilter.setRenderer(listRenderer); //make items center aligned
		manageFilter.setFont(infoFont);
		manageFilter.setForeground(cBlue);
		manageFilter.setBackground(themeColor);
		manageFilter.setFocusable(false);
		manageFilter.setBounds(placeX(0.9, windowWidth/6), placeY(0.05, windowHeight/6), windowWidth/6, windowHeight/6);
		
		manageLength = 0;
		
		for (int i = 0; i<events.size(); i++) { //add elements to the list with the correct scroll thing
			String eventType = events.keySet().toArray()[i].toString().substring(0,2); //and weekly/date event   			
			if (eventType.equals("03")) { //if weekly event
				if (manageFilter.getSelectedIndex() == 0 || manageFilter.getSelectedIndex() == 2) {
						manageLength++;
				}
			} else if(eventType.equals("02")) { //if yearly event
				if (manageFilter.getSelectedIndex() == 0 || manageFilter.getSelectedIndex() == 1 || manageFilter.getSelectedIndex() == 4) {
					manageLength++;
				}
			} else if(eventType.equals("01")) { //if single event
				if (manageFilter.getSelectedIndex() == 0 || manageFilter.getSelectedIndex() == 1 || manageFilter.getSelectedIndex() == 3) {
					manageLength++;
				}
			}
			
		}
		
		int eventPass = manageListArrow;
		
		
		for (int i = 0; i<events.size(); i++) { //add elements to the list with the correct scroll thing
			String eventName = events.values().toArray()[i].toString(); //get event name
			String eventType = events.keySet().toArray()[i].toString().substring(0,2); //and weekly/date event
			
			if (eventType.equals("03")) { //if weekly event
				if (manageFilter.getSelectedIndex() == 0 || manageFilter.getSelectedIndex() == 2) {
						int weekday = Integer.parseInt(events.keySet().toArray()[i].toString().substring(2,3)); //get the day of the week the event is on
						if (eventPass == 0) {
							l1.addElement(eventName + " - " + weekdays[weekday-1]); //put the event name with its date in the list
						} else {
							eventPass--;
						}
				}
			} else if(eventType.equals("02")) { //if yearly event
				if (manageFilter.getSelectedIndex() == 0 || manageFilter.getSelectedIndex() == 1 || manageFilter.getSelectedIndex() == 4) {
					String month = events.keySet().toArray()[i].toString().substring(2,4); //get the month of the event
					String day = events.keySet().toArray()[i].toString().substring(4,6); //get the day of the event
					if (eventPass == 0) {
						l1.addElement(eventName + " - " + month+"/"+day); //add the event with its date
					} else {
						eventPass--;
					}
				}
			} else if(eventType.equals("01")) { //if single event
				if (manageFilter.getSelectedIndex() == 0 || manageFilter.getSelectedIndex() == 1 || manageFilter.getSelectedIndex() == 3) {
					String month = events.keySet().toArray()[i].toString().substring(2,4); //get the month of the event
					String day = events.keySet().toArray()[i].toString().substring(4,6); //get the day of the event
					if (eventPass == 0) {
						l1.addElement(eventName + " - " + month+"/"+day); //add the event with its date
					} else {
						eventPass--;
					}
				}
			}
			
		}
		
		//list
		manageList.setBounds(placeX(0.5, windowWidth/10*6), placeY(0.4, windowHeight/3), windowWidth/10*6, windowHeight/3);
		manageList.setFont(timeFont);
		manageList.setForeground(cBlue);
		manageList.setBorder(BorderFactory.createLineBorder(cBlue));
		
		try { //arrow enabling and disabling
			if (manageListArrow == 0) { //if up arrow should be disabled
				if (!darkTheme) { //if in dark theme
					upArrowLogo = ImageIO.read(new File("./assets/UpArrowGray.gif"));
				} else { //if in light theme
					upArrowLogo = ImageIO.read(new File("./assets/UpArrowDarkGray.gif"));
				}
			} else { //if not supposed to be disabled
				if (!darkTheme) { //if in dark theme
					upArrowLogo = ImageIO.read(new File("./assets/UpArrow.gif"));
				} else { //if in light theme
					upArrowLogo = ImageIO.read(new File("./assets/UpArrowDark.gif"));
				}
			}
			if (manageListArrow == manageLength-1 || manageListArrow == manageLength) { //if down arrow should be disabled
				if (!darkTheme) { //if in dark theme
					downArrowLogo = ImageIO.read(new File("./assets/DownArrowGray.gif"));
				} else { //if in light theme
					downArrowLogo = ImageIO.read(new File("./assets/DownArrowDarkGray.gif"));
				}
			} else { //if not supposed to be disabled
				if (!darkTheme) { //if in dark theme
					downArrowLogo = ImageIO.read(new File("./assets/DownArrow.gif"));
				} else { //if in light theme
					downArrowLogo = ImageIO.read(new File("./assets/DownArrowDark.gif"));
				}
			}
		} catch (IOException e1) {
			System.err.println("Could not read file. Full error: "+e1);
		}
		
		//arrows
		
		upArrowButton.setIcon(ScaleUtils.scaleImage(upArrowLogo, windowWidth/10, windowHeight/6));
		upArrowButton.setBounds(placeX(0.9, windowWidth/10), placeY(0.27, 100), windowWidth/10, 100);
		
		downArrowButton.setIcon(ScaleUtils.scaleImage(downArrowLogo, windowWidth/10, windowHeight/6));
		downArrowButton.setBounds(placeX(0.9, windowWidth/10), placeY(0.57, 100), windowWidth/10, 100);
		
		
		//center text in list
		DefaultListCellRenderer renderer = (DefaultListCellRenderer) manageList.getCellRenderer(); //get the cell renderer for the list
		renderer.setHorizontalAlignment(SwingConstants.CENTER); //align the text it renders
		
		//edit button
		manageEditButton.setBounds(placeX(0.3, windowWidth/7), placeY(0.7, windowHeight/10), windowWidth/7, windowHeight/10);
		
		//delete button
		manageDeleteButton.setBounds(placeX(0.5, windowWidth/7), placeY(0.7, windowHeight/10), windowWidth/7, windowHeight/10);
		
		//delete all button
		manageDeleteAllButton.setBounds(placeX(0.7, windowWidth/7), placeY(0.7, windowHeight/10), windowWidth/7, windowHeight/10);
		
		//back button
		manageBackButton.setBounds(placeX(0.5, windowWidth/7), placeY(0.85, windowHeight/10), windowWidth/7, windowHeight/10);
		
		//change colors
		manageEditButton.setBackground(themeColor);
		manageDeleteButton.setBackground(themeColor);
		manageDeleteAllButton.setBackground(themeColor);
		manageBackButton.setBackground(themeColor);
		manageList.setBackground(themeColor);
		upArrowButton.setBackground(themeColor);
		downArrowButton.setBackground(themeColor);
		
		//change font color based off theme
		if (darkTheme) {
			manageEditButton.setForeground(cBlue);
			manageBackButton.setForeground(cBlue);
			manageDeleteButton.setForeground(cBlue);
			manageDeleteAllButton.setForeground(cBlue);
		} else { //if light theme
			manageEditButton.setForeground(null);
			manageBackButton.setForeground(null);
			manageDeleteButton.setForeground(null);
			manageDeleteAllButton.setForeground(null);
		}
		
		//add combo box
		root.add(manageFilter);
		manageFilter.revalidate();
		manageFilter.repaint(); //make the arrow show up
		
		//add labels
		root.add(ManageLabel);
		
		//add lists
		root.add(manageList);
		
		//add buttons
		root.add(upArrowButton);
		root.add(downArrowButton);
		root.add(manageEditButton);
		root.add(manageDeleteButton);
		root.add(manageDeleteAllButton);
		root.add(manageBackButton);
	}
	
	private static void updateSettingsPage() {
		
		//image label of page
		settingsLabel.setIcon(ScaleUtils.scaleImage(settingsText, windowWidth/25*13*1.1, windowHeight/110*19*1.5));
		settingsLabel.setBounds(placeX(0.5, windowWidth/10*6), placeY(0, 130), windowWidth/10*6, 130);
		
		//settings prompt label
		settingsPromptLabel.setBounds(placeX(0.5, windowWidth/4), placeY(0.2, windowHeight/6), windowWidth/4, windowHeight/6);
		settingsPromptLabel.setForeground(cBlue);
		settingsPromptLabel.setFont(infoFont);
		
		//theme setting buttons
		darkThemeButton.setBounds(placeX(0.5, windowWidth/7), placeY(0.55, windowHeight/10), windowWidth/7, windowHeight/10);
		lightThemeButton.setBounds(placeX(0.5, windowWidth/7), placeY(0.7, windowHeight/10), windowWidth/7, windowHeight/10);
		
		//back button
		settingsBackButton.setBounds(placeX(0.5, windowWidth/7), placeY(0.85, windowHeight/10), windowWidth/7, windowHeight/10);
		
		//set font of buttons
		darkThemeButton.setFont(buttonFont);
		lightThemeButton.setFont(buttonFont);
		settingsBackButton.setFont(buttonFont);
		
		//place all the buttons in their correct spot in their correct size
		//these buttons scale with the screen
		rightArrowButton.setIcon(ScaleUtils.scaleImage(rightArrowLogo, windowWidth/10, windowHeight/6));
		rightArrowButton.setBounds(placeX(0.65, windowWidth/10), placeY(0.35, 100), windowWidth/10, 100);
				
		leftArrowButton.setIcon(ScaleUtils.scaleImage(leftArrowLogo, windowWidth/10, windowHeight/6));
		leftArrowButton.setBounds(placeX(0.35, windowWidth/10), placeY(0.35, 100), windowWidth/10, 100);
		
		//adjust colors based on theme for arrow buttons
		rightArrowButton.setBackground(themeColor);
		leftArrowButton.setBackground(themeColor);
		
		daysNoticeLabel.setText(""+daysNotice);
		daysNoticeLabel.setForeground(cBlue);
		daysNoticeLabel.setFont(timeFont);
		daysNoticeLabel.setBounds(placeX(0.5, windowWidth/4), placeY(0.35, windowHeight/6), windowWidth/4, windowHeight/6);
		
		//set font color of buttons
		if (darkTheme) {
			darkThemeButton.setForeground(cBlue);
			lightThemeButton.setForeground(cBlue);
			settingsBackButton.setForeground(cBlue);
		} else {
			darkThemeButton.setForeground(null);
			lightThemeButton.setForeground(null);
			settingsBackButton.setForeground(null);
		}
		
		//version label
		settingsVersionLabel.setBounds(placeX(0.9, windowWidth/10), placeY(0.9, windowHeight/6), windowWidth/4, windowHeight/6);
		settingsVersionLabel.setFont(infoFont);
		settingsVersionLabel.setForeground(cBlue);
		
		//set background of buttons
		darkThemeButton.setBackground(themeColor);
		lightThemeButton.setBackground(themeColor);
		settingsBackButton.setBackground(themeColor);
		
		//add labels
		root.add(settingsLabel);
		root.add(settingsPromptLabel);
		root.add(daysNoticeLabel);
		root.add(settingsVersionLabel);
		
		//add buttons
		root.add(darkThemeButton);
		root.add(lightThemeButton);
		root.add(settingsBackButton);
		root.add(rightArrowButton);
		root.add(leftArrowButton);
		
	}
	
	private static void updateHelpPage() {
		
		//help part of help
		helpHelpLabel.setFont(infoFont); //set up font and colors
		helpHelpLabel.setForeground(cBlue);
		helpHelpLabel.setBackground(themeColor);
		helpHelpLabel.setBounds(placeX(0.5, windowWidth/3*2), placeY(0.7, windowHeight/2), windowWidth/3*2, windowHeight/2);
		
		//back button
		helpBackButton.setBounds(placeX(0.5, windowWidth/7), placeY(0.8, windowHeight/10), windowWidth/7, windowHeight/10);
				
		//change the font size of the buttons
		helpBackButton.setFont(buttonFont);
		
		//set font color of buttons
		if (darkTheme) {
			helpBackButton.setForeground(cBlue);
		} else {
			helpBackButton.setForeground(null);
		}
				
		//set background of buttons
		helpBackButton.setBackground(themeColor);
		
		//image label of page
		helpLabel.setIcon(ScaleUtils.scaleImage(helpText, windowWidth/25*13*1.4, windowHeight/110*19*1.5)); //1.5 is to make the image larger in general
		helpLabel.setBounds(placeX(0.5, windowWidth/10*6), placeY(0, 130), windowWidth/10*6, 130);
		
		//welcome label
		helpWelcomeLabel.setFont(greetingFont);
		helpWelcomeLabel.setForeground(cBlue);
		helpWelcomeLabel.setBounds(placeX(0.5, windowWidth/10*5), placeY(0.20, windowHeight/5), windowWidth/10*5, windowHeight/5);
		
		//add labels to screen
		root.add(helpLabel);
		root.add(helpWelcomeLabel);
		
		//add buttons to screen
		root.add(helpBackButton);
		
		//add text area to screen
		root.add(helpHelpLabel); //ignore the fact it says label its a text area
	}
	
	private static void updateWeeklyEventTitlePage() {
		
		//finish and back button
		weeklyEventTitleBackButton.setBounds(placeX(0.5, windowWidth/7), placeY(0.7, windowHeight/10), windowWidth/7, windowHeight/10);
		weeklyEventFinishButton.setBounds(placeX(0.5, windowWidth/7), placeY(0.85, windowHeight/10), windowWidth/7, windowHeight/10);
				
		//change the font size of the buttons
		weeklyEventTitleBackButton.setFont(buttonFont);
		weeklyEventFinishButton.setFont(buttonFont);
		
		//these image labels must have there image resized
		//label of page
		weeklyLabel.setIcon(ScaleUtils.scaleImage(weeklyLogo, windowWidth/25*13*1.1, windowHeight/110*19*1.5)); //1.5 is to make the image larger in general
		weeklyLabel.setBounds(placeX(0.5, windowWidth/10*6), placeY(0, 130), windowWidth/10*6, 130);
		
		//prompt label
		weeklyEventTitlePrompt.setFont(greetingFont);
		weeklyEventTitlePrompt.setForeground(cBlue);
		weeklyEventTitlePrompt.setBounds(placeX(0.5, windowWidth/10*5), placeY(0.20, windowHeight/5), windowWidth/10*5, windowHeight/5);
		
		//text field
		weeklyEventTextField.setFont(timeFont);
		weeklyEventTextField.setBounds(placeX(0.5, windowWidth/3), placeY(0.4, windowHeight/10), windowWidth/3, windowHeight/10);
		
		//change background color of things based off theme
		weeklyEventTextField.setBackground(themeColor);
		weeklyEventFinishButton.setBackground(themeColor);
		weeklyEventTitleBackButton.setBackground(themeColor);
		
		//change font color of buttons based off of theme
		if (darkTheme) {
			weeklyEventTitleBackButton.setForeground(cBlue);
			weeklyEventFinishButton.setForeground(cBlue);
		} else {
			weeklyEventTitleBackButton.setForeground(null);
			weeklyEventFinishButton.setForeground(null);
		}
		
		//add labels to screen
		root.add(weeklyLabel);
		root.add(weeklyEventTitlePrompt);
		
		//add text fields
		root.add(weeklyEventTextField);
		
		//add the buttons to the screen
		root.add(weeklyEventFinishButton);
		root.add(weeklyEventTitleBackButton);
	}
	
	private static void updateWeeklyEventCreator() {
		//next and back button
		weeklyEventBackButton.setBounds(placeX(0.5, windowWidth/7), placeY(0.7, windowHeight/10), windowWidth/7, windowHeight/10);
		weeklyEventNextButton.setBounds(placeX(0.5, windowWidth/7), placeY(0.85, windowHeight/10), windowWidth/7, windowHeight/10);
		
		//change the font size of the buttons
		weeklyEventBackButton.setFont(buttonFont);
		weeklyEventNextButton.setFont(buttonFont);
		
		//these image labels must have there image resized
		//label of page
		weeklyLabel.setIcon(ScaleUtils.scaleImage(weeklyLogo, windowWidth/25*13*1.1, windowHeight/110*19*1.5)); //1.5 is to make the image larger in general
		weeklyLabel.setBounds(placeX(0.5, windowWidth/10*6), placeY(0, 130), windowWidth/10*6, 130);
		
		//prompt label
		weeklyEventDatePrompt.setFont(greetingFont);
		weeklyEventDatePrompt.setForeground(cBlue);
		weeklyEventDatePrompt.setBounds(placeX(0.5, windowWidth/10*5), placeY(0.20, windowHeight/5), windowWidth/10*5, windowHeight/5);
		
		//month chooser label
		weekdayChooserLabel.setBounds(placeX(0.5, windowWidth/10*5), placeY(0.45, windowHeight/5), windowWidth/10*5, windowHeight/5);
		weekdayChooserLabel.setFont(timeFont);
		weekdayChooserLabel.setForeground(cBlue);
		
		//place all the buttons in their correct spot in their correct size
		//these buttons scale with the screen
		rightArrowButton.setIcon(ScaleUtils.scaleImage(rightArrowLogo, windowWidth/10, windowHeight/6));
		rightArrowButton.setBounds(placeX(0.65, windowWidth/10), placeY(0.46, 100), windowWidth/10, 100);
		
		leftArrowButton.setIcon(ScaleUtils.scaleImage(leftArrowLogo, windowWidth/10, windowHeight/6));
		leftArrowButton.setBounds(placeX(0.35, windowWidth/10), placeY(0.46, 100), windowWidth/10, 100);
		
		//weekday chooser text thingy
		weekdayChooserLabel.setText(weekdays[weekDayChooserDay-1]);
		
		//set buttons background color
		rightArrowButton.setBackground(themeColor);
		leftArrowButton.setBackground(themeColor);
		weeklyEventNextButton.setBackground(themeColor);
		weeklyEventBackButton.setBackground(themeColor);
		
		//change font color of buttons based off of theme
		if (darkTheme) {
			weeklyEventBackButton.setForeground(cBlue);
			weeklyEventNextButton.setForeground(cBlue);
		} else {
			weeklyEventBackButton.setForeground(null);
			weeklyEventNextButton.setForeground(null);
		}
		
		//add labels to the screen
		root.add(weeklyLabel);
		root.add(weeklyEventDatePrompt);
		root.add(weekdayChooserLabel);
		
		//add buttons to the screen
		root.add(weeklyEventBackButton);
		root.add(weeklyEventNextButton);
		root.add(rightArrowButton);
		root.add(leftArrowButton);
		
	}
	
	private static void updateNewEventFinished() {
		//new event page label
		newEventLabel.setIcon(ScaleUtils.scaleImage(newEventLogo, windowWidth/25*13*1.1, windowHeight/110*19*1.5)); //1.5 is to make the image larger in general
		newEventLabel.setBounds(placeX(0.5, windowWidth/10*6), placeY(0, 100), windowWidth/10*6, 100);
		
		//back button
		newEventFinishBackButton.setBounds(placeX(0.5, windowWidth/7), placeY(0.8, windowHeight/10), windowWidth/7, windowHeight/10);
		
		//set fonts of the buttons
		newEventFinishBackButton.setFont(buttonFont);
		
		//confirmation label
		eventCreatedConfirmationLabel.setFont(greetingFont);
		eventCreatedConfirmationLabel.setForeground(cBlue);
		eventCreatedConfirmationLabel.setBounds(placeX(0.5, windowWidth/1.5), placeY(0.2, windowHeight/5), (windowWidth/3)*2, windowHeight/5);
		
		//event name
		newEventEventName.setFont(timeFont);
		newEventEventName.setText("Event Name: "+eventName);
		newEventEventName.setForeground(cBlue);
		newEventEventName.setBounds(placeX(0.5, windowWidth/1.5), placeY(0.4, windowHeight/5), (windowWidth/3)*2, windowHeight/5);
		
		//event date label
		newEventDate.setFont(timeFont);
		newEventDate.setText("Event Date: "+eventDate);
		newEventDate.setForeground(cBlue);
		newEventDate.setBounds(placeX(0.5, windowWidth/1.5), placeY(0.5, windowHeight/5), (windowWidth/3)*2, windowHeight/5);
		
		//event code label
		newEventCode.setFont(infoFont);
		newEventCode.setText("Event Code: " + eventCode);
		newEventCode.setForeground(cBlue);
		newEventCode.setBounds(placeX(0.5, windowWidth/1.5), placeY(0.7, windowHeight/5), (windowWidth/3)*2, windowHeight/5);
		
		//color buttons
		newEventFinishBackButton.setBackground(themeColor);
		
		//color button font for dark/light theme
		if (darkTheme) {
			newEventFinishBackButton.setForeground(cBlue);
		} else {
			newEventFinishBackButton.setForeground(null);
		}
		
		//add labels to the screen
		root.add(newEventLabel);
		root.add(eventCreatedConfirmationLabel);
		root.add(newEventEventName);
		root.add(newEventDate);
		root.add(newEventCode);
		
		//add the buttons to the screen
		root.add(newEventFinishBackButton);
	}
	
	private static void updateDateEventTitlePage() {

		//finish and back button
		dateEventTitleBackButton.setBounds(placeX(0.5, windowWidth/7), placeY(0.7, windowHeight/10), windowWidth/7, windowHeight/10);
		dateEventFinishButton.setBounds(placeX(0.5, windowWidth/7), placeY(0.85, windowHeight/10), windowWidth/7, windowHeight/10);
		
		//change the font size of the buttons
		dateEventTitleBackButton.setFont(buttonFont);
		dateEventFinishButton.setFont(buttonFont);
		
		//check box
		repeatYearlyCheck.setFont(infoFont);
		repeatYearlyCheck.setBounds(placeX(0.5, windowWidth/6), placeY(0.55, windowHeight/10), windowWidth/6, windowHeight/10);
		
		//these image labels must have there image resized
		//label of page
		dateLabel.setIcon(ScaleUtils.scaleImage(dateLogo, windowWidth/25*13*1.1, windowHeight/110*19*1.5)); //1.5 is to make the image larger in general
		dateLabel.setBounds(placeX(0.5, windowWidth/10*6), placeY(0, 100), windowWidth/10*6, 100);
		
		//prompt label
		dateEventTitlePrompt.setFont(greetingFont);
		dateEventTitlePrompt.setForeground(cBlue);
		dateEventTitlePrompt.setBounds(placeX(0.5, windowWidth/10*5), placeY(0.20, windowHeight/5), windowWidth/10*5, windowHeight/5);
		
		//text field
		dateEventTextField.setFont(timeFont);
		dateEventTextField.setBounds(placeX(0.5, windowWidth/3), placeY(0.4, windowHeight/10), windowWidth/3, windowHeight/10);
		
		//change font color of buttons based off of theme
		if (darkTheme) {
			dateEventTitleBackButton.setForeground(cBlue);
			dateEventFinishButton.setForeground(cBlue);
		} else {
			dateEventTitleBackButton.setForeground(null);
			dateEventFinishButton.setForeground(null);
		}		
		
		//change background color of things based off theme
		dateEventTextField.setBackground(themeColor);
		repeatYearlyCheck.setBackground(themeColor);
		dateEventFinishButton.setBackground(themeColor);
		dateEventTitleBackButton.setBackground(themeColor);
		
		//add labels to the screen
		root.add(dateLabel);
		root.add(dateEventTitlePrompt);
		
		//add text fields
		root.add(dateEventTextField);
		
		//add the buttons to the screen
		root.add(dateEventFinishButton);
		root.add(dateEventTitleBackButton);
		
		//add check boxes
		root.add(repeatYearlyCheck);
		
		
	}
	
	private static void updateDateEventCreator() {
		
		//next and back button
		dateEventBackButton.setBounds(placeX(0.5, windowWidth/7), placeY(0.7, windowHeight/10), windowWidth/7, windowHeight/10);
		dateEventNextButton.setBounds(placeX(0.5, windowWidth/7), placeY(0.85, windowHeight/10), windowWidth/7, windowHeight/10);
		
		
		//place all the buttons in their correct spot in their correct size
		//these buttons scale with the screen
		rightArrowButton.setIcon(ScaleUtils.scaleImage(rightArrowLogo, windowWidth/10, windowHeight/6));
		rightArrowButton.setBounds(placeX(0.55, windowWidth/10), placeY(0.51, 100), windowWidth/10, 100);
		
		leftArrowButton.setIcon(ScaleUtils.scaleImage(leftArrowLogo, windowWidth/10, windowHeight/6));
		leftArrowButton.setBounds(placeX(0.25, windowWidth/10), placeY(0.51, 100), windowWidth/10, 100);
		
		upArrowButton.setIcon(ScaleUtils.scaleImage(upArrowLogo, windowWidth/10, windowHeight/6));
		upArrowButton.setBounds(placeX(0.7, windowWidth/10), placeY(0.35, 100), windowWidth/10, 100);
		
		downArrowButton.setIcon(ScaleUtils.scaleImage(downArrowLogo, windowWidth/10, windowHeight/6));
		downArrowButton.setBounds(placeX(0.7, windowWidth/10), placeY(0.7, 100), windowWidth/10, 100);
		
		//change the font size of the buttons
		dateEventBackButton.setFont(buttonFont);
		dateEventNextButton.setFont(buttonFont);
		
		//these image labels must have there image resized
		dateLabel.setIcon(ScaleUtils.scaleImage(dateLogo, windowWidth/25*13*1.1, windowHeight/110*19*1.5)); //1.5 is to make the image larger in general
		dateLabel.setBounds(placeX(0.5, windowWidth/10*6), placeY(0, 100), windowWidth/10*6, 100);
		
		//prompt label
		dateEventDatePrompt.setFont(greetingFont);
		dateEventDatePrompt.setForeground(cBlue);
		dateEventDatePrompt.setBounds(placeX(0.5, windowWidth/10*5), placeY(0.20, windowHeight/5), windowWidth/10*5, windowHeight/5);
		
		//month chooser label
		monthChooserLabel.setBounds(placeX(0.33, windowWidth/10*5), placeY(0.5, windowHeight/5), windowWidth/10*5, windowHeight/5);
		monthChooserLabel.setFont(timeFont);
		monthChooserLabel.setForeground(cBlue);
		
		//day chooser label
		dayChooserLabel.setBounds(placeX(0.855, windowWidth/10*5), placeY(0.5, windowHeight/5), windowWidth/10*5, windowHeight/5);
		dayChooserLabel.setFont(timeFont);
		dayChooserLabel.setForeground(cBlue);
		
		//month chooser text thingy
		monthChooserLabel.setText(months[monthChooserMonth-1]);
		dayChooserLabel.setText(Integer.toString(dayChooserMonth));
		
		//set buttons background color
		rightArrowButton.setBackground(themeColor);
		leftArrowButton.setBackground(themeColor);
		upArrowButton.setBackground(themeColor);
		downArrowButton.setBackground(themeColor);
		dateEventBackButton.setBackground(themeColor);
		dateEventNextButton.setBackground(themeColor);
		
		//change font color of buttons based off of theme
		if (darkTheme) {
			dateEventBackButton.setForeground(cBlue);
			dateEventNextButton.setForeground(cBlue);
		} else {
			dateEventBackButton.setForeground(null);
			dateEventNextButton.setForeground(null);
		}
		
		
		//add the buttons to the screen
		root.add(rightArrowButton);
		root.add(leftArrowButton);
		root.add(upArrowButton);
		root.add(downArrowButton);
		root.add(dateEventBackButton);
		root.add(dateEventNextButton);
		
		//add labels to the screen
		root.add(dateLabel);
		root.add(dateEventDatePrompt);
		root.add(monthChooserLabel);
		root.add(dayChooserLabel);
	}
	
	private static void updateNewEventHome() {
		
		//place all the buttons in their correct spot in their correct size
		//these buttons scale with the screen
		
		newEventDateEventButton.setBounds(placeX(0.5, windowWidth/7), placeY(0.4, windowHeight/10), windowWidth/7, windowHeight/10);
		newEventWeeklyEventButton.setBounds(placeX(0.5, windowWidth/7), placeY(0.55, windowHeight/10), windowWidth/7, windowHeight/10);
		newEventBackButton.setBounds(placeX(0.5, windowWidth/7), placeY(0.7, windowHeight/10), windowWidth/7, windowHeight/10);
		
		//change the font size of the buttons
		newEventDateEventButton.setFont(buttonFont);
		newEventWeeklyEventButton.setFont(buttonFont);
		newEventBackButton.setFont(buttonFont);

		
		//these image labels must have there image resized
		newEventLabel.setIcon(ScaleUtils.scaleImage(newEventLogo, windowWidth/25*13*1.1, windowHeight/110*19*1.5)); //1.5 is to make the image larger in general
		newEventLabel.setBounds(placeX(0.5, windowWidth/10*6), placeY(0, 100), windowWidth/10*6, 100);
		
		//prompt label
		newEventEventType.setFont(greetingFont);
		newEventEventType.setForeground(cBlue);
		newEventEventType.setBounds(placeX(0.5, windowWidth/10*5), placeY(0.20, windowHeight/5), windowWidth/10*5, windowHeight/5);
		
		//set buttons background color
		newEventDateEventButton.setBackground(themeColor);
		newEventWeeklyEventButton.setBackground(themeColor);
		newEventBackButton.setBackground(themeColor);
		
		//set buttons font color
		if (darkTheme) {
			newEventDateEventButton.setForeground(cBlue);
			newEventWeeklyEventButton.setForeground(cBlue);
			newEventBackButton.setForeground(cBlue);
		} else {
			newEventDateEventButton.setForeground(null);
			newEventWeeklyEventButton.setForeground(null);
			newEventBackButton.setForeground(null);
		}
		
		//add the buttons to the screen
		root.add(newEventDateEventButton);
		root.add(newEventWeeklyEventButton);
		root.add(newEventBackButton);
		
		//add labels to the screen
		root.add(newEventLabel);
		root.add(newEventEventType);
	
	}
	
	private static void updateDailyInfo() {
		
		//place all the buttons in their correct spot in their correct size
		dailyInfoBackButton.setBounds(placeX(0.5, windowWidth/7), placeY(0.8, windowHeight/10), windowWidth/7, windowHeight/10);
		
		//change the font size of the buttons
		dailyInfoBackButton.setFont(buttonFont);
		
		//change button font color based on theme
		if (darkTheme) {
			dailyInfoBackButton.setForeground(cBlue);
		} else { //if light theme
			dailyInfoBackButton.setForeground(null);
		}
		
		//update background color of button
		dailyInfoBackButton.setBackground(themeColor);
		
		//these image labels must have there image resized
		dailyInfoLabel.setIcon(ScaleUtils.scaleImage(dailyInfoLogo, windowWidth/25*13*1.1, windowHeight/110*19*1.5)); //1.5 is to make the image larger in general
		dailyInfoLabel.setBounds(placeX(0.5, windowWidth/10*6), placeY(0, windowHeight/5), windowWidth/10*6, windowHeight/5);
		
		//greeting label
		greetingLabel.setFont(greetingFont);
		greetingLabel.setForeground(cBlue);
		greetingLabel.setBounds(placeX(0.5, windowWidth/10*5), placeY(0.20, windowHeight/5), windowWidth/10*5, windowHeight/5);
		
		timeLabel.setFont(timeFont);
		timeLabel.setForeground(cBlue);
		timeLabel.setBounds(placeX(0.5, windowWidth/10*5), placeY(0.35, windowHeight/5), windowWidth/10*5, windowHeight/5);
		
		
		eventsHeaderLabel.setForeground(cBlue);
		eventsHeaderLabel.setBounds(placeX(-0.31, windowWidth/10*5), placeY(0.5, windowHeight/5), windowWidth/10*5, windowHeight/5);
		eventsHeaderLabel.setFont(greetingFont);
		
		holidayHeaderLabel.setForeground(cBlue);
		holidayHeaderLabel.setBounds(placeX(1.1, windowWidth/10*5), placeY(0.5, windowHeight/5), windowWidth/10*5, windowHeight/5);
		holidayHeaderLabel.setFont(greetingFont);
		
		holidayLabel.setForeground(cBlue);
		holidayLabel.setBounds(placeX(1.1, windowWidth/10*5), placeY(0.60, windowHeight/5), windowWidth/10*5, windowHeight/5);
		holidayLabel.setFont(infoFont);
		
		nextholidayLabel.setForeground(cBlue);
		nextholidayLabel.setBounds(placeX(1.1, windowWidth/10*5), placeY(0.7, windowHeight/5), windowWidth/10*5, windowHeight/5);
		nextholidayLabel.setFont(infoFont);
		
		holidayLabel.setText(getHoliday(0));
		nextholidayLabel.setText(getHoliday(1));
		
		
		boolean showTodayHolidayLabel = true;
		if (holidayLabel.getText() == "No Holidays Today" && nextholidayLabel.getText() != "") { //if there is an event in the future but not today
			nextholidayLabel.setBounds(placeX(1.1, windowWidth/10*5), placeY(0.60, windowHeight/5), windowWidth/10*5, windowHeight/5); //move this up
			showTodayHolidayLabel = false;
		}
		
		eventsLabel.setBackground(themeColor);
		eventsLabel.setForeground(cBlue);
		eventsLabel.setBounds(placeX(0.05, windowWidth/3), placeY(0.78, windowHeight/3), windowWidth/3, windowHeight/3);
		eventsLabel.setFont(infoFont);
		
		eventsLabel.setText("");
		
		deleteOldEvents(); //get rid of expired events
		
		for (int i = 0; i<events.size(); i++) { //get todays date events
			
			String eventData = (String)events.keySet().toArray()[i]; //get event date data
			LocalDate currentDate = LocalDate.now(); //get the current time
			
			if(!eventData.substring(0,2).equals("03")) {
				//if the current date is equal to the event date
				if (Integer.parseInt(eventData.substring(2,4)) == currentDate.getMonthValue() && Integer.parseInt(eventData.substring(4,6)) == currentDate.getDayOfMonth()) {
					eventsLabel.setText(eventsLabel.getText()+"\n"+events.values().toArray()[i]); //add event name to daily info
				}
				//else
				else {
					//check if it is equal in the next 7 days
					for(int j=1; j<daysNotice+1; j++) {
						int newEventDay = currentDate.getDayOfMonth()+j; //get the new day in the future
						int newEventMonth = currentDate.getMonthValue(); //new month incase the month changes in a few days
						
						if (newEventDay>daysInMonth[currentDate.getMonthValue()]) { //if the new day is past the end of the month
							newEventDay = newEventDay-daysInMonth[currentDate.getMonthValue()]; //change the day
							newEventMonth = currentDate.getMonthValue()+1; //change the month
						}
						
						//if the dates match up
						if (newEventMonth == Integer.parseInt(eventData.substring(2,4)) && newEventDay == Integer.parseInt(eventData.substring(4,6))) {
							if (j == 1) { //if the event is one day in the future
								eventsLabel.setText(eventsLabel.getText()+"\n"+events.values().toArray()[i]+" - 1 Day"); //day
							  } else {
								eventsLabel.setText(eventsLabel.getText()+"\n"+events.values().toArray()[i]+" - "+j+" Days"); //days
							  }
							break;
						}
						
					}
				}
			} else { //check for weekly events
				//check current date for matching weekly events
				if (Integer.parseInt(eventData.substring(2,3)) == currentDate.getDayOfWeek().getValue()) {
					eventsLabel.setText(eventsLabel.getText()+"\n"+events.values().toArray()[i]); //add event name to daily info
				} else {
					//check for events in the next 7 weekdays
					for(int j=1; j<daysNotice+1; j++) {
						int newEventWeekday = currentDate.getDayOfWeek().getValue()+j; //get the new day in the future
						
						if (newEventWeekday>7) { //if weekday is beyond sunday (which doesnt exist)
							newEventWeekday=newEventWeekday-7; //switch it to the next week starting from 1
						}
						
						//if the dates match up
						if (newEventWeekday == Integer.parseInt(eventData.substring(2,3))) {
							if (j == 1) { //if the event is one day in the future
								eventsLabel.setText(eventsLabel.getText()+"\n"+events.values().toArray()[i]+" - 1 Day"); //day
							  } else {
								eventsLabel.setText(eventsLabel.getText()+"\n"+events.values().toArray()[i]+" - "+j+" Days"); //days
							  }
							break;
						}
						
					}
				}
				
				
			}
		}
		
		if (eventsLabel.getText().equals("")) {
			eventsLabel.setText("\nNo Events Today");
		}
		
		date = LocalDateTime.now();
		hour = date.getHour();
		int minute = date.getMinute();
		//set the greeting text
		if (hour < 12) { greetingLabel.setText("Good Morning");} 
		else if (hour < 19) { greetingLabel.setText("Good Afternoon");}
		else if (hour < 25) {greetingLabel.setText("Good Evening");
	      }
		
		//set the hour to am/pm
		boolean pm = false;
		int pmHour = hour;
		
		if (hour > 11) {
			pm = true;
		}
		
		if (hour > 12) {
			pmHour = hour-12;
		}
		
		//set the time text
		if (pm == true) {
			if (minute < 10) {
				timeLabel.setText("It is "+pmHour + ":0" + minute + " pm");
			} else {
				timeLabel.setText("It is "+pmHour + ":" + minute + " pm");
			}
		} else {
			if (minute < 10) {
				timeLabel.setText("It is "+pmHour + ":0" + minute + " am");
			} else {
				timeLabel.setText("It is "+pmHour + ":" + minute + " am");
			}
		}
		
		//add labels to the screen
		root.add(dailyInfoLabel);
		root.add(greetingLabel);
		root.add(timeLabel);
		root.add(eventsHeaderLabel);
		root.add(eventsLabel);
		root.add(holidayHeaderLabel);
		if (showTodayHolidayLabel) {
			root.add(holidayLabel);
		}
		root.add(nextholidayLabel);
		
		//add buttons to the screen
		root.add(dailyInfoBackButton);
		
	}
	
	private static void updateHome() {
		
		//place all the buttons in their correct spot in their correct size
		
		 //these buttons scale with the screen
		newEventButton.setBounds(placeX(0.5, windowWidth/7), placeY(0.4, windowHeight/10), windowWidth/7, windowHeight/10);
		dailyInfoButton.setBounds(placeX(0.3, windowWidth/7), placeY(0.4, windowHeight/10), windowWidth/7, windowHeight/10);
		manageButton.setBounds(placeX(0.7, windowWidth/7), placeY(0.4, windowHeight/10), windowWidth/7, windowHeight/10);
		helpButton.setBounds(placeX(0.5, windowWidth/10), placeY(0.6, windowHeight/7), windowWidth/10, windowHeight/7);
		exitButton.setBounds(placeX(0.65, windowWidth/10), placeY(0.6, windowHeight/7), windowWidth/10, windowHeight/7);
		settingsButton.setBounds(placeX(0.35, windowWidth/10), placeY(0.6, windowHeight/7), windowWidth/10, windowHeight/7);
		
		//change the font size of the buttons
		newEventButton.setFont(buttonFont);
		dailyInfoButton.setFont(buttonFont);
		manageButton.setFont(buttonFont);
		
		//change font color in dark mode
		if (darkTheme) {
			newEventButton.setForeground(cBlue);
			dailyInfoButton.setForeground(cBlue);
			manageButton.setForeground(cBlue);
		} else {
			newEventButton.setForeground(null);
			dailyInfoButton.setForeground(null);
			manageButton.setForeground(null);
		}
		
		//set buttons background color
		newEventButton.setBackground(themeColor);
		dailyInfoButton.setBackground(themeColor);
		manageButton.setBackground(themeColor);
		helpButton.setBackground(themeColor);
		exitButton.setBackground(themeColor);
		settingsButton.setBackground(themeColor);
		
		
		//these image labels must have there image resized
		logoLabel.setIcon(ScaleUtils.scaleImage(calendarText, windowWidth/25*13*1.1, windowHeight/110*19*1.5)); //1.5 is to make the image larger in general
		logoLabel.setBounds(placeX(0.5, windowWidth/10*6), placeY(0.05, 100), windowWidth/10*6, 100);
		
		devLabel.setIcon(ScaleUtils.scaleImage(devText, ((float)windowWidth/(float)500)*297*1.1, windowHeight/110*19*1.2)); //1.2 is to make the image larger in general
		devLabel.setBounds(placeX(0.5, windowWidth/10*7), placeY(0.86, 100), windowWidth/10*7, 100);
		
		helpButton.setIcon(ScaleUtils.scaleImage(helpLogo, windowWidth/12.5, windowHeight/7.5));
		
		exitButton.setIcon(ScaleUtils.scaleImage(exitLogo, windowWidth/12.5, windowHeight/7.5));
		
		settingsButton.setIcon(ScaleUtils.scaleImage(settingsLogo, windowWidth/12.5, windowHeight/7.5));
		
		
		//add the buttons to the screen
		root.add(newEventButton);
		root.add(dailyInfoButton);
		root.add(manageButton);
		root.add(helpButton);
		root.add(exitButton);
		root.add(settingsButton);
		
		//add labels to the screen
		root.add(logoLabel);
		root.add(devLabel);
	}
	
	public void run() {
		
		//check for window resizing
		
		while (true) {
			
			//check for minute change
			date = LocalDateTime.now(); //get the correct time
			minute = date.getMinute(); //get the correct minute
			if (minute != oldMinute) {
				year = LocalDate.now().getYear(); //update year just in case someone is looking at this on new years eve
				renderScreen();
			}
			
			oldMinute = minute;
			
			//check if the window was scaled wider/thinner
			
			if (ScaleUtils.windowWidth != root.getWidth()) {
				
				//change the set window width
				ScaleUtils.windowWidth = root.getWidth();
				
				//redraw the screen with new sizes
				renderScreen();
				
			} //check if window was scaled taller/shorter
			else if (ScaleUtils.windowHeight != root.getHeight()) {
				
				//change the set window height
				ScaleUtils.windowHeight = root.getHeight();
				
				//redraw the screen with new sizes
				renderScreen();
			}
			
			//add delay to thread to let the loop run or something
			try {
				Thread.sleep(16); //1/60 of a second
			} catch (InterruptedException e) {
				System.err.println(e);
			}
		}
	}
	
	private static void loadImages() { //load all the images for use later
		try {
			if (!darkTheme) {
				calendarText = ImageIO.read(new File("./assets/logo.gif"));
				devText = ImageIO.read(new File("./assets/FIREDEV.gif"));
				helpLogo = ImageIO.read(new File("./assets/Help.gif"));
				exitLogo = ImageIO.read(new File("./assets/Exit.gif"));
				settingsLogo = ImageIO.read(new File("./assets/Settings.gif"));
				dailyInfoLogo = ImageIO.read(new File("./assets/dailyInfo.gif"));
				newEventLogo = ImageIO.read(new File("./assets/newevent.gif"));
				dateLogo = ImageIO.read(new File("./assets/datevent.gif"));
				weeklyLogo = ImageIO.read(new File("./assets/weeklyevent.gif"));
				rightArrowLogo = ImageIO.read(new File("./assets/RightArrow.gif"));
				leftArrowLogo = ImageIO.read(new File("./assets/LeftArrow.gif"));
				upArrowLogo = ImageIO.read(new File("./assets/UpArrow.gif"));
				downArrowLogo = ImageIO.read(new File("./assets/DownArrow.gif"));
				helpText = ImageIO.read(new File("./assets/helpLogo.gif"));
				settingsText = ImageIO.read(new File("./assets/settingsLogo.gif"));
				ManageText = ImageIO.read(new File("./assets/manage.gif"));
			} else {
				calendarText = ImageIO.read(new File("./assets/logoDark.gif"));
				devText = ImageIO.read(new File("./assets/FIREDEVdark.gif"));
				helpLogo = ImageIO.read(new File("./assets/HelpDark.gif"));
				exitLogo = ImageIO.read(new File("./assets/ExitDark.gif"));
				settingsLogo = ImageIO.read(new File("./assets/SettingsDark.gif"));
				dailyInfoLogo = ImageIO.read(new File("./assets/dailyInfoDark.gif"));
				newEventLogo = ImageIO.read(new File("./assets/neweventDark.gif"));
				dateLogo = ImageIO.read(new File("./assets/dateventDark.gif"));
				weeklyLogo = ImageIO.read(new File("./assets/weeklyeventDark.gif"));
				rightArrowLogo = ImageIO.read(new File("./assets/RightArrowDark.gif"));
				leftArrowLogo = ImageIO.read(new File("./assets/LeftArrowDark.gif"));
				upArrowLogo = ImageIO.read(new File("./assets/UpArrowDark.gif"));
				downArrowLogo = ImageIO.read(new File("./assets/DownArrowDark.gif"));
				helpText = ImageIO.read(new File("./assets/helpLogoDark.gif"));
				settingsText = ImageIO.read(new File("./assets/settingsLogoDark.gif"));
				ManageText = ImageIO.read(new File("./assets/manageDark.gif"));
			}
			
			
		} catch (IOException e) {
			System.err.println("ERROR: Image(s) unable to load. See below for full error.\n\n"+e); //error message
		}
	}
	
	private static void getSettings() {
		//default settings
		int theme = 1;
		daysNotice = 4;
		
		//read file to get last theme
		try {
			BufferedReader fileSetting = new BufferedReader(new FileReader("./settings/theme.calendar"));
			theme = Integer.parseInt(fileSetting.readLine());
			fileSetting.close();
			BufferedReader fileSetting2 = new BufferedReader(new FileReader("./settings/notice.calendar"));
			daysNotice = Integer.parseInt(fileSetting2.readLine());
			fileSetting2.close();
		}
		catch(Exception e) {
			System.err.println("Could not read settings files. Full Error: \n\n" + e);
		}
		
		//set the theme
		if (theme == 1) {
			themeColor = Color.WHITE; //theme for calendar, can be changed to black
			darkTheme = false;
		} else if (theme == 0) {
			themeColor = Color.BLACK;
			darkTheme = true;
		}
	}
	
	private static void saveEvents() {
		try {
			BufferedWriter eventFile = new BufferedWriter(new FileWriter("./events/events.calendar")); //open the events file
			
			for(int k = 0; k<events.size(); k++) { //for every event
				eventFile.write(events.keySet().toArray()[k].toString()); //write the event code in
				eventFile.newLine();
				eventFile.write(events.values().toArray()[k].toString()); //write the event name in
				
				if (k<events.size()-1) { //add new line if not at end of file
					eventFile.newLine();
				}
			}
			
			eventFile.close();
			
		} catch(Exception e) { //if events file cant be opened
			System.err.println("Events file could not be found. Full Error:\n\n"+e);
		}
		
	}
	
	private static void deleteOldEvents() {
		for (int i=0; i<events.size(); i++) {
			
			String eventData = events.keySet().toArray()[i].toString();
			
			if (eventData.substring(0,2).equals("01")) { //if single event
				//get info about the events date
				int eventYear = Integer.parseInt(eventData.substring(6,10));
				int eventMonth = Integer.parseInt(eventData.substring(2,4));
				int eventDay = Integer.parseInt(eventData.substring(4,6));
				
				//get info about the date
				int day = LocalDate.now().getDayOfMonth();
				int month = LocalDate.now().getMonthValue();
				int year = LocalDate.now().getYear();
				
				if (eventYear<year) { //if event was last year
					events.remove(eventData); //delete the event
					i--; //decrease index because hashmap is now shorter because an event is gone
				} else if (eventYear==year) { //if it was this year
					if (eventMonth<month) { //but was before this month
						i--; //decrease index because hashmap is now shorter because an event is gone
						events.remove(eventData); //delete the event
					} else if (eventMonth==month) { //if the event was this month
						if (eventDay<day) { //but was before this day
							i--; //decrease index because hashmap is now shorter because an event is gone
							events.remove(eventData); //delete the event
						}
					}
				}
				
				saveEvents(); //save new events
			}
		}
	}
	
	private static void readEvents() {
		try {
			BufferedReader eventFile = new BufferedReader(new FileReader("./events/events.calendar")); //to get events
			BufferedReader eventCounter = new BufferedReader(new FileReader("./events/events.calendar")); //to get number of lines
			
			int eventCount = 0; //count lines in file
			while((eventCounter.readLine()) != null) {eventCount++;} //read all lines to see how many there are
			eventCounter.close();
			
			eventCount = (eventCount/2); //events are two lines long
			
			for (int l=0; l<eventCount; l++) { //read through every event
				
				String eventID = eventFile.readLine(); //get the event id
				String eventName = eventFile.readLine(); //get the event name
				
				events.put(eventID, eventName); //put it in the event list thing
			}
			
			eventFile.close();
			
		} catch(Exception e) {
			System.err.println("Events file could not be found. Full Error:\n\n"+e);
		}
	}
	
	private static void initializeComponents() {
		//                ___
		// |__|  _  ____  |__
		// |  | |_| | | | |__
		
		//new event button
		newEventButton = new JButton("New Event");
		newEventButton.setBackground(themeColor);
		newEventButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				changeScreen((byte) 3);
			}
		});
		
		//daily info button
		dailyInfoButton = new JButton("Daily Info");
		dailyInfoButton.setBackground(themeColor);
		
		//set action
		dailyInfoButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				changeScreen((byte) 2);
			}
		});
		
		//manage button
		manageButton = new JButton("Manage");
		manageButton.setBackground(themeColor);
		
		//set action
		manageButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				
				manageFilter.setSelectedIndex(0);
				manageListArrow = 0;
				changeScreen((byte) 11);
			}
		});
		
		//help button
		helpButton = new JButton(ScaleUtils.scaleImage(helpLogo, 80, 80));
		helpButton.setBackground(themeColor);
		
		//set action
		helpButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				changeScreen((byte) 9);
			}
		});
		
		//exit button
		exitButton = new JButton(ScaleUtils.scaleImage(exitLogo, 80, 80));
		exitButton.setBackground(themeColor); //make the background white so it matches the image
		
		//add action
		exitButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		
		//settings button
		settingsButton = new JButton(ScaleUtils.scaleImage(settingsLogo, 80, 80));
		settingsButton.setBackground(themeColor);
		
		//set action
		settingsButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				changeScreen((byte) 10);
			}
		});
		
		//calendar text logo
		logoLabel = new JLabel(ScaleUtils.scaleImage(calendarText, windowWidth/25*13*1.1, windowHeight/110*19*1.5));
		
		//developer text logo
		devLabel = new JLabel(new ImageIcon(devText));
		
		//
		//daily info
		//
		
		//daily info logo
		dailyInfoLabel = new JLabel(ScaleUtils.scaleImage(dailyInfoLogo, windowWidth/25*13*1.1, windowHeight/110*19*1.5));
		
		//gretting label
		greetingLabel = new JLabel("", SwingConstants.CENTER); //text will be set later
		
		//time label
		timeLabel = new JLabel("", SwingConstants.CENTER); //text will also be set later
		
		//events header label
		eventsHeaderLabel = new JLabel("Events:", SwingConstants.CENTER); //text will not change
		
		//events label
		eventsLabel = new JTextArea(""); //will be recoded in far future
		eventsLabel.setEditable(false);
		
		//holidays header label
		holidayHeaderLabel = new JLabel("Holidays:", SwingConstants.CENTER); //text will not change
		
		//holidays header label
		holidayLabel = new JLabel("", SwingConstants.CENTER); //text will be set later
		
		//holidays header label
		nextholidayLabel = new JLabel("", SwingConstants.CENTER); //text will be set later or could stay empty
		
		//back button
		dailyInfoBackButton = new JButton("Back");
		dailyInfoBackButton.setBackground(themeColor);
		
		//give it its action
		dailyInfoBackButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				changeScreen((byte) 1);
			}
		});
		
		//
		//NEW EVENT HOME
		//
		
		//new event logo
		newEventLabel = new JLabel(ScaleUtils.scaleImage(newEventLogo, windowWidth/25*13*1.1, windowHeight/110*19*1.5));
		
		//single/yearly event button
		newEventDateEventButton = new JButton("Date Event");
		newEventDateEventButton.setBackground(themeColor);
		
		newEventDateEventButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				//set month and day to january first
				monthChooserMonth = 1;
				dayChooserMonth = 1;
				repeatYearlyCheck.setSelected(false);
				dateEventTextField.setText("");
				
				changeScreen((byte) 4); //go to the date event creator
			}
		});
		
		//weekly event button
		newEventWeeklyEventButton = new JButton("Weekly Event");
		newEventWeeklyEventButton.setBackground(themeColor);
		
		newEventWeeklyEventButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				
				//set weekday to default (monday)
				weekDayChooserDay = 1;
				weeklyEventTextField.setText("");
				
				changeScreen((byte) 7); //go to weekly event creator
			}
		});
		
		//back button
		newEventBackButton = new JButton("Back");
		newEventBackButton.setBackground(themeColor);
		
		//add event
		newEventBackButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				changeScreen((byte) 1);
			}
		});
		
		//label to choose event type
		newEventEventType = new JLabel("Choose Your Event Type:", SwingConstants.CENTER);
		
		//
		// Date Event Date
		// 
		
		dateEventBackButton = new JButton("Back");
		dateEventBackButton.setBackground(themeColor);
		
		//add event
		dateEventBackButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				changeScreen((byte) 3);
			}
		});
		
		dateEventNextButton = new JButton("Next");
		dateEventNextButton.setBackground(themeColor);
		
		//add event
		dateEventNextButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				changeScreen((byte) 5);
			}
		});
		
		//date date event prompt
		dateEventDatePrompt = new JLabel("Choose the date:", SwingConstants.CENTER);
		
		//month chooser label
		monthChooserLabel = new JLabel("", SwingConstants.CENTER); //text will be changed to a month later
		
		//day chooser label
		dayChooserLabel = new JLabel("", SwingConstants.CENTER); //text will be changed to a day later
		
		
		//
		// Date Event Title
		//
		
		//date event title prompt
		dateEventTitlePrompt = new JLabel("Name the Event:", SwingConstants.CENTER);
		
		dateEventTitleBackButton = new JButton("Back");
		dateEventTitleBackButton.setBackground(themeColor);
		
		//yearly check box
		repeatYearlyCheck = new JCheckBox("Repeat Yearly?");
		repeatYearlyCheck.setForeground(cBlue);
		repeatYearlyCheck.setHorizontalAlignment(SwingConstants.CENTER);
		

		//add event
		dateEventTitleBackButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				changeScreen((byte) 4);
			}
		});
		
		dateEventFinishButton = new JButton("Finish");
		dateEventFinishButton.setBackground(themeColor);
		
		//text field
		dateEventTextField = new JTextField("", SwingConstants.CENTER); //text will be inputed by user
		dateEventTextField.setBackground(themeColor);
		dateEventTextField.setForeground(cBlue);
		
		//add event
		dateEventFinishButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				
				if (dateEventTextField.getText().equals("") || dateEventTextField.getText().equals("Must Enter Name")) {
					JOptionPane.showMessageDialog(root, "You must name your event.");
				} else {
				
				//add event to event list
				
				String eventType = "01"; //get event type
				if (repeatYearlyCheck.isSelected()) {
				   eventType = "02";
				}
				
				String eventMonth = monthChooserMonth+""; //get the month in correct format
				if (monthChooserMonth < 10) {
					eventMonth = "0"+monthChooserMonth;
				}
				
				String eventDay = dayChooserLabel.getText()+""; //get the day in correct format
				if (Integer.parseInt(dayChooserLabel.getText()) < 10) {
					eventDay = "0"+dayChooserLabel.getText();
				}
				
				String eventYear = "";
				if (eventType == "01") {
					
					eventYear = year+"";
					
					int month = LocalDate.now().getMonthValue();
					int day = LocalDate.now().getDayOfMonth();
					
					if (Integer.parseInt(eventMonth)<month) {
						eventYear = (year+1)+"";
					} else if(Integer.parseInt(eventMonth)==month) {
						if (Integer.parseInt(eventDay)<day) {
							eventYear = (year+1)+"";
						}
					}
					
				}
				
				int nano = LocalDateTime.now().getNano(); //to make sure no keys are the same
				
				events.put(eventType+eventMonth+eventDay+eventYear+nano, dateEventTextField.getText());
				
				System.out.println(events);
				eventName = dateEventTextField.getText();
				eventCode = eventType+eventMonth+eventDay+eventYear+nano;
				eventDate = months[monthChooserMonth-1]+" "+dayChooserMonth;
				
				saveEvents(); //save the events
				
				changeScreen((byte) 6);
				}
			}
		});
		
		//
		// Weekly Event Date Screen
		//
		
		//weekly date event prompt
		weeklyEventDatePrompt = new JLabel("Choose the Weekday:", SwingConstants.CENTER);
		
		//weekday chooser label
		weekdayChooserLabel = new JLabel("", SwingConstants.CENTER); //text will be changed to a weekday later
		
		//back button
		weeklyEventBackButton = new JButton("Back");
		weeklyEventBackButton.setBackground(themeColor);
		
		//add event
		weeklyEventBackButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				changeScreen((byte) 3);
			}
		});
		
		//next button - goes to title creator
		weeklyEventNextButton = new JButton("Next");
		weeklyEventNextButton.setBackground(themeColor);
		
		//add event
		weeklyEventNextButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				changeScreen((byte) 8);
			}
		});
		
		//
		// Weekly Event Title Thing
		//
		
		//date event title prompt
		weeklyEventTitlePrompt = new JLabel("Name the Event:", SwingConstants.CENTER);
		
		//text field
		weeklyEventTextField = new JTextField("", SwingConstants.CENTER); //text will be inputed by user
		weeklyEventTextField.setBackground(themeColor);
		weeklyEventTextField.setForeground(cBlue);
		
		weeklyEventTitleBackButton = new JButton("Back");
		weeklyEventTitleBackButton.setBackground(themeColor);
		
		//add event
		weeklyEventTitleBackButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				changeScreen((byte) 7);
			}
		});
		
		weeklyEventFinishButton = new JButton("Finish");
		weeklyEventFinishButton.setBackground(themeColor);
		
		//add event
		weeklyEventFinishButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				
				if (weeklyEventTextField.getText().equals("") || weeklyEventTextField.getText().equals("Must Enter Name")) {
					JOptionPane.showMessageDialog(root, "You must name your event.");
				} else {
				
					int nano = LocalDateTime.now().getNano();
					
					eventName = weeklyEventTextField.getText();
					eventDate = weekdays[weekDayChooserDay-1];
					eventCode = "03"+weekDayChooserDay+nano;
					
					//add event to event list
					events.put("03"+weekDayChooserDay+nano, weeklyEventTextField.getText());
					
					saveEvents();
					
					changeScreen((byte) 6);
				}
			}
		});
		
		//
		// New Event Created
		//
		
		//back button
		newEventFinishBackButton = new JButton("Back");
		newEventFinishBackButton.setBackground(themeColor);
				
		//give it its action
		newEventFinishBackButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				changeScreen((byte) 1);
			}
		});
		
		eventCreatedConfirmationLabel = new JLabel("Your Event Has Been Created!", SwingConstants.CENTER);
		
		//event name
		newEventEventName = new JLabel("Event Name: ", SwingConstants.CENTER);
		
		//event date
		newEventDate = new JLabel("Date: ", SwingConstants.CENTER);
		
		//event date
		newEventCode = new JLabel("Code: ", SwingConstants.CENTER);
		
		//
		// Help Screen
		//
		
		//help part of the help page
		helpHelpLabel = new JTextArea("This calendar keeps track of two types of events, "
				+ "date events and weekly events. Date events are on a certain date, and can be repeated yearly, and weekly events is repeated weekly. "
				+ "To create events, go to New Events. To change or delete an event, go to Manage, and to see what events are coming up, go to daily info.");
		helpHelpLabel.setBorder(null); //change looks
		helpHelpLabel.setEditable(false); //text cant be changed by user
		helpHelpLabel.setWrapStyleWord(true); //auto line change thing
		helpHelpLabel.setLineWrap(true);
		
		
		//page title
		helpLabel = new JLabel(ScaleUtils.scaleImage(weeklyLogo, windowWidth/25*13*1.1, windowHeight/110*19*1.5));
		
		//welcome thing
		helpWelcomeLabel = new JLabel("Welcome to the Calendar!", SwingConstants.CENTER);
		
		//back button
		helpBackButton = new JButton("Back");
		helpBackButton.setBackground(themeColor);
				
		//add event
		helpBackButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				changeScreen((byte) 1);
			}
		});
		
		//
		//Settings
		//
		
		settingsLabel = new JLabel(ScaleUtils.scaleImage(settingsLogo, windowWidth/25*13*1.1, windowHeight/110*19*1.5));
		
		darkThemeButton = new JButton("Dark Theme");
		darkThemeButton.setBackground(themeColor);
		
		settingsPromptLabel = new JLabel("Days Notice Before Event:", SwingConstants.CENTER);
		
		daysNoticeLabel = new JLabel("", SwingConstants.CENTER);
		
		//add event
		darkThemeButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				themeColor = Color.BLACK; //turn on dark theme
				darkTheme = true; //^
				loadImages(); //get dark theme images
				renderScreen(); //redraw screen with new stuff
				
				//update file to start in the same theme next time calendar is opened
				try {
					BufferedWriter themeSaver = new BufferedWriter(new FileWriter("./settings/theme.calendar")); //open file
					themeSaver.write("0"); //put dark theme in the file
				    themeSaver.close();
				    
				} catch (IOException e1) {
					System.err.println("Could not write to file.\n\n"+e1);
				}
			}
		});
		
		lightThemeButton = new JButton("Light Theme");
		lightThemeButton.setBackground(themeColor);
		
		//add event
		lightThemeButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				themeColor = Color.WHITE; //turn on dark theme
				darkTheme = false; //^
				loadImages(); //get dark theme images
				renderScreen(); //redraw screen with new stuff
				
				//update file to start in the same theme next time calendar is opened
				try {
					FileWriter themeSaver = new FileWriter("./settings/theme.calendar"); //open file
					themeSaver.write("1"); //put dark theme in the file
				    themeSaver.close();
				    
				} catch (IOException e1) {
					System.err.println("Could not write to file.\n\n"+e1);
				}
			}
		});
		
		//back button
		settingsBackButton = new JButton("Back");
		settingsBackButton.setBackground(themeColor);
		
		//version label
		settingsVersionLabel = new JLabel("Version "+version);
		
		//add event
		settingsBackButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				changeScreen((byte) 1);
			}
		});
		
		//
		// MANAGE HOME PAGE
		//
		
		String eventTypes[]={"All Events","Date Events","Weekly Events", "Single Events", "Yearly Events"};
		manageFilter = new JComboBox<String>(eventTypes);
		
		manageFilter.addActionListener(new ActionListener() {  
	        public void actionPerformed(ActionEvent e) {
	        	manageListArrow = 0;
	        	
	        	renderScreen();
	        }
		});
		
		manageList = new JList<String>(l1);
		
		manageDeleteAllButton = new JButton("Delete All Events");
		manageDeleteAllButton.setBackground(themeColor);
		
		//set action
		manageDeleteAllButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {	
					int prompt = JOptionPane.showConfirmDialog(root, "Are you sure you want to delete all events? This cannot be undone.");
					
					if(prompt==JOptionPane.YES_OPTION){   					
						
						events.clear();
						
						saveEvents(); //write event to file
						
						renderScreen(); //update screen
					}
			}
		});
		
		//delete button
		manageDeleteButton = new JButton("Delete Event");
		manageDeleteButton.setBackground(themeColor);
		
		//set action
		manageDeleteButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (manageList.getSelectedValue() != null) { //if an event is selected
					int selection = manageList.getSelectedIndex()+manageListArrow;
					
					for (int p = 0; p<events.size(); p++) {
						String eventData = (String) events.keySet().toArray()[p];
						String eventType = eventData.substring(0, 2);
						
						if (eventType.equals("03")) { //if weekly event
							if (manageFilter.getSelectedIndex() == 0 || manageFilter.getSelectedIndex() == 2) {
								if (selection == 0) {
									manageSelectionKey = (String)events.keySet().toArray()[p];
									break;
								} else {
									selection--;
								}
							}
						} else if(eventType.equals("02")) { //if yearly event
							if (manageFilter.getSelectedIndex() == 0 || manageFilter.getSelectedIndex() == 1 || manageFilter.getSelectedIndex() == 4) {
								if (selection == 0) {
									manageSelectionKey = (String)events.keySet().toArray()[p];
									break;
								} else {
									selection--;
								}
							}
						} else if(eventType.equals("01")) { //if single event
							if (manageFilter.getSelectedIndex() == 0 || manageFilter.getSelectedIndex() == 1 || manageFilter.getSelectedIndex() == 3) {
								if (selection <= 0) {
									manageSelectionKey = (String)events.keySet().toArray()[p];
									break;
								} else {
									selection--;
								}
							}
						}
					}
					
					int prompt = JOptionPane.showConfirmDialog(root, "Are you sure you want to delete this event?");
					
					if(prompt==JOptionPane.YES_OPTION){   
						if (selection == 0 && manageListArrow == manageLength-1) { //if the last event is deleted
							manageListArrow--; //move the list up
						}
						
						events.remove(manageSelectionKey); //delete the event
						
						saveEvents(); //write event to file
						
						renderScreen(); //update screen
					}
					
				} else {
					JOptionPane.showMessageDialog(root,"You must select an event to delete.");  
				}
			}
		});
		
		//edit button
		manageEditButton = new JButton("Edit Event");
		manageEditButton.setBackground(themeColor);
		
		//set action
		manageEditButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (manageList.getSelectedValue() != null) {
					
					int selection = manageList.getSelectedIndex()+manageListArrow;
					
					for (int p = 0; p<events.size(); p++) {
						String eventData = (String) events.keySet().toArray()[p];
						String eventType = eventData.substring(0, 2);
						
						if (eventType.equals("03")) { //if weekly event
							if (manageFilter.getSelectedIndex() == 0 || manageFilter.getSelectedIndex() == 2) {
								if (selection == 0) {
									manageSelectionKey = (String)events.keySet().toArray()[p];
									break;
								} else {
									selection--;
								}
							}
						} else if(eventType.equals("02")) { //if yearly event
							if (manageFilter.getSelectedIndex() == 0 || manageFilter.getSelectedIndex() == 1 || manageFilter.getSelectedIndex() == 4) {
								if (selection == 0) {
									manageSelectionKey = (String)events.keySet().toArray()[p];
									break;
								} else {
									selection--;
								}
							}
						} else if(eventType.equals("01")) { //if single event
							if (manageFilter.getSelectedIndex() == 0 || manageFilter.getSelectedIndex() == 1 || manageFilter.getSelectedIndex() == 3) {
								if (selection <= 0) {
									manageSelectionKey = (String)events.keySet().toArray()[p];
									break;
								} else {
									selection--;
								}
							}
						}
					}
					
					changeScreen((byte)12);
					
				} else {
					JOptionPane.showMessageDialog(root,"You must select an event to edit.");  
				}
			}
		});
		
		//back button
		manageBackButton = new JButton("Back");
		manageBackButton.setBackground(themeColor);
				
		//add event
		manageBackButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				
				try { //undisable all the arrow buttons
					if (!darkTheme) { //if light theme
						downArrowLogo = ImageIO.read(new File("./assets/DownArrow.gif")); //set images
						upArrowLogo = ImageIO.read(new File("./assets/UpArrow.gif"));
					} else { //if dark theme
						downArrowLogo = ImageIO.read(new File("./assets/DownArrowDark.gif")); //set images
						upArrowLogo = ImageIO.read(new File("./assets/UpArrowDark.gif"));
					}
				} catch(Exception e2) {
					System.err.println("Could not read image files. Full Error: "+e2);
				}
				
				changeScreen((byte) 1);
			}
		});
		
		//Manage edit screen
		
		//event name label
		manageEventNameLabel = new JLabel("Event Name:", SwingConstants.CENTER);
				
		//text field
		manageTextField = new JTextField("", SwingConstants.CENTER); //text will be inputed by user
		manageTextField.setBackground(themeColor);
		manageTextField.setForeground(cBlue);
		
		//save button
		manageSaveButton = new JButton("Save");
		manageSaveButton.setBackground(themeColor);
		
		//add event
		manageSaveButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (manageSelectionKey.substring(0,2).equals("03")) {
					events.replace(manageSelectionKey, manageTextField.getText()); //change name of event
					
					saveEvents();
					
					changeScreen((byte) 11);
				}
				if (manageSelectionKey.substring(0,2).equals("01")) {
					if (manageCheckBox.isSelected()) {
						
						events.remove(manageSelectionKey); //delete old event
						
						String month = manageSelectionKey.substring(2,4);
						String day = manageSelectionKey.substring(4,6);
						
						String nano = ""+LocalDateTime.now().getNano();
						
						manageSelectionKey = "02"+month+day+nano;
						System.out.println(manageSelectionKey);
						
						events.put(manageSelectionKey, manageTextField.getText());
					} else {
						events.replace(manageSelectionKey, manageTextField.getText()); //change name of event
					}
					saveEvents();
					
					changeScreen((byte) 11);
				}
				if (manageSelectionKey.substring(0,2).equals("02")) {
					if (!manageCheckBox.isSelected()) {
						
						events.remove(manageSelectionKey); //delete old event
						
						String month = manageSelectionKey.substring(2,4);
						String day = manageSelectionKey.substring(4,6);
						
						String nano = ""+LocalDateTime.now().getNano();
						
						String eventYear = "";
							
						eventYear = year+"";
					
						int cmonth = LocalDate.now().getMonthValue();
						int cday = LocalDate.now().getDayOfMonth();
							
						if (Integer.parseInt(month)<cmonth) {
							eventYear = (year+1)+"";
						} else if(Integer.parseInt(month)==cmonth) {
							if (Integer.parseInt(day)<cday) {
								eventYear = (year+1)+"";
							}
						}
							
						
						
						manageSelectionKey = "01"+month+day+eventYear+nano;
						
						System.out.println(manageSelectionKey);
						
						events.put(manageSelectionKey, manageTextField.getText());
					} else {
						events.replace(manageSelectionKey, manageTextField.getText()); //change name of event
					}
					saveEvents();
					
					changeScreen((byte) 11);
				}
			}
		});
		
		//cancel button
		manageCancelButton = new JButton("Cancel");
		manageCancelButton.setBackground(themeColor);
		
		//add event
		manageCancelButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				changeScreen((byte) 11);
			}
		});
		
		//yearly check box
		manageCheckBox = new JCheckBox("Repeat Yearly?");
		manageCheckBox.setForeground(cBlue);
		manageCheckBox.setHorizontalAlignment(SwingConstants.CENTER);
		
		//
		//MIXED SCREEN COMPONENTS
		//
		
		//manage image label
		ManageLabel = new JLabel(ScaleUtils.scaleImage(ManageText, windowWidth/25*13*1.1, windowHeight/110*19*1.5));
		
		//right arrow button
		rightArrowButton = new JButton(ScaleUtils.scaleImage(rightArrowLogo, windowWidth/10, windowHeight/6));
		rightArrowButton.setBorder(null); //make button image only
		rightArrowButton.setBackground(themeColor);
		rightArrowButton.setFocusPainted(false);
		
		rightArrowButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (screen == (byte)4) { //if in date chooser screen
					monthChooserMonth++;
					if (monthChooserMonth>12) { //month cant be past december
						monthChooserMonth = 1;
					}
					if (dayChooserMonth>daysInMonth[monthChooserMonth]) {
						dayChooserMonth = daysInMonth[monthChooserMonth];
					}
				}
				if (screen == (byte)7) {//if in weekday chooser screen
					weekDayChooserDay++;
					if (weekDayChooserDay>7) {
						weekDayChooserDay = 1;
					}
				}
				if (screen == (byte)10) { //if in settings
					daysNotice++;
					if (daysNotice > 14) {
						daysNotice = 1;
					}
					
					//update file to save days notice setting
					try {
						BufferedWriter noticeSaver = new BufferedWriter(new FileWriter("./settings/notice.calendar")); //open file
						noticeSaver.write(""+daysNotice); //put dark theme in the file
					    noticeSaver.close();
					    
					} catch (IOException e1) {
						System.err.println("Could not write to file.\n\n"+e1);
					}
				}
				
				renderScreen();
			}
		});
		
		//up arrow button
		upArrowButton = new JButton(ScaleUtils.scaleImage(upArrowLogo, windowWidth/10, windowHeight/6));
		upArrowButton.setBorder(null); //make button image only
		upArrowButton.setBackground(themeColor);
		upArrowButton.setFocusPainted(false);
		upArrowButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if ((byte) screen == 4) {
					dayChooserMonth++;
					if (dayChooserMonth>daysInMonth[monthChooserMonth]) {
						dayChooserMonth = 1;
					}
					renderScreen();
				}
				if ((byte) screen == 11) {
					manageListArrow--;
					
					if (manageListArrow<0) {
						manageListArrow = 0;
					}
										
					renderScreen();
				}
			}
		});
		
		//down arrow button
		downArrowButton = new JButton(ScaleUtils.scaleImage(downArrowLogo, windowWidth/10, windowHeight/6));
		downArrowButton.setBorder(null); //make button image only
		downArrowButton.setBackground(themeColor);
		downArrowButton.setFocusPainted(false);
		downArrowButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				
				if ((byte) screen == 4) {
					dayChooserMonth--;
					if (dayChooserMonth<1) {
						dayChooserMonth = daysInMonth[monthChooserMonth];
					}
					renderScreen();
				}
				
				if ((byte) screen == 11) {
					manageListArrow++;
					
					if (manageListArrow>manageLength-1) {
						manageListArrow--;
					}
										
					renderScreen();
				}
			}
		});
		
		//left arrow button
		leftArrowButton = new JButton(ScaleUtils.scaleImage(leftArrowLogo, windowWidth/10, windowHeight/6));
		leftArrowButton.setBorder(null); //make button image only
		leftArrowButton.setBackground(themeColor);
		leftArrowButton.setFocusPainted(false);
		leftArrowButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (screen == (byte)4) { //if in date chooser screen
					monthChooserMonth--;
					if (monthChooserMonth<1) { //month cant be before january
						monthChooserMonth = 12;
					}
					if (dayChooserMonth>daysInMonth[monthChooserMonth]) {
						dayChooserMonth = daysInMonth[monthChooserMonth];
					}
					
				}
				if (screen == (byte)7) {//if in weekday chooser screen
					weekDayChooserDay--;
					if (weekDayChooserDay<1) {
						weekDayChooserDay = 7;
					}
				}
				if (screen == (byte)10) { //if in settings
					daysNotice--;
					if (daysNotice < 1) {
						daysNotice = 14;
					}
					
					//update file to save days notice setting
					try {
						BufferedWriter noticeSaver = new BufferedWriter(new FileWriter("./settings/notice.calendar")); //open file
						noticeSaver.write(""+daysNotice); //put dark theme in the file
					    noticeSaver.close();
					    
					} catch (IOException e1) {
						System.err.println("Could not write to file.\n\n"+e1);
					}
				}
				
				renderScreen();
			}
		});
		
		//date event pages label
		dateLabel = new JLabel(ScaleUtils.scaleImage(dateLogo, windowWidth/25*13*1.1, windowHeight/110*19*1.5));
		
		//weekly event pages label
		weeklyLabel = new JLabel(ScaleUtils.scaleImage(weeklyLogo, windowWidth/25*13*1.1, windowHeight/110*19*1.5));
		
		//
		// key listener
		//
		
		
		
		//  ___   _                _   
		// | | | |_| |\ | |  |    |_|   _    _
		// |   | |__ | \| |__|    |__| |_|\ |
		
		menuBar = new JMenuBar();
		
		//exit button on menu bar
		JMenuItem exitMenuBarButton = new JMenuItem("Exit");
		
		//give it its action
		exitMenuBarButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		
		//add it to the menu bar
		menuBar.add(exitMenuBarButton);
		
		//daily info button on menu bar
		JMenuItem dailyInfoMenuBarButton = new JMenuItem("Daily Info");
		
		//give it its action
		dailyInfoMenuBarButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				changeScreen((byte) 2);
			}
		});
		
		//add it to the menu bar
		menuBar.add(dailyInfoMenuBarButton);
		
		//manage button on menu bar
		JMenuItem eventMenuBarButton = new JMenuItem("New Event");
						
		//give it its action
		eventMenuBarButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				changeScreen((byte) 3);
			}
		});
						
		menuBar.add(eventMenuBarButton);
		
		//manage button on menu bar
		JMenuItem manageMenuBarButton = new JMenuItem("Manage");
				
		//give it its action
		manageMenuBarButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				
				manageFilter.setSelectedIndex(0);
				changeScreen((byte) 11);
			}
		});
				
		menuBar.add(manageMenuBarButton);
		
		//settings button on menu bar
		JMenuItem settingsMenuBarButton = new JMenuItem("Settings");
		
		//give it its action
		settingsMenuBarButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				changeScreen((byte) 10);
			}
		});
		
		menuBar.add(settingsMenuBarButton);
		
		//settings button on menu bar
		JMenuItem helpMenuBarButton = new JMenuItem("Help");
		
		helpMenuBarButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				changeScreen((byte) 9);
			}
		});
		
		menuBar.add(helpMenuBarButton);
		
		//add menubar
		if(menuBarEnabled) {
			root.setJMenuBar(menuBar);
		}
			
	}
	
	private static void showWindow() {
		//make the window visible
		root.setVisible(true);
	}
	
	private static void createWindow() {
		//create the window
		root = new JFrame();
		root.setLayout(null);
		
		//put the window in the correct position and size it
		root.setSize(windowWidth, windowHeight);
		root.setLocation((screenWidth-windowWidth)/2, (screenHeight-windowHeight)/2);
		
		//make the window white
		root.getContentPane().setBackground(themeColor);
		root.setTitle("Calendar");
		
		//exit java when the window closes
		root.addWindowListener(new WindowAdapter() {
			@Override	
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
	    });
	}

}
