# GearBook-301

CMPUT 301 Fall 2020

ASSIGNMENT 1

Zhining He

ccid: zhining2


App name:

zhining2-GearBook


Functionality:

This app allows user to keep track of their own gears. It allows the user to add a gear, view and edit the information of a current gear. It saves user input as the information of gear, and displays them in a list. The text box will assists the user in proper data entry. Input hint will display in the text-box. Error message will display at the right side of the text-box if the input is invalid.


Code Folder: Java code of the app.
* 4 Java files: MainActivity.java, UserInputPage.java, Gear.java, Adapter.java
* 3 xml files: activity_main.xml, activity_user_input_page.xml, content.xml


Doc Folder: UML diagram of the app.
* zhining2-GearBook-uml.png

Video Folder: A video show the demo of the app.
* zhining2-GearBook-demo.mp4

-----

User Guide:

The app can save information of the date, the desctiption or the name of a gear, the price of purchased, the maker, and any additional note for a gear.

Home Page:
* ADD: click this button to add a new gear.
* VIEW/EDIT: first click an item from the list, then click this button to view or edit * the details of the gear.
* DELETE: first click a gear from the list, then click this button to remove the selected item from the list. An alert window will pop up to avoid unintentionally deletion.
* Underneath the 3 buttons is the list view of all items. Each row show the information of a gear in the order of the Date followed by the Desctiption followed by the Price in Canadian dollar.

Add/View/Edit Page:
* PICK DATE: click this button to quickly pick a date from the calendar
* OK: click this button to save the updated of the information.
* CANCEL: click button to go back to the main page, no affection will be made to data.
* User must enter all datas in proper format, otherwise an error message will display at the right side of the text-box and so updated can not be made.
* Format of input: 
	Date: yyyy-mm-dd (Pick a date from calendar. This field is required)
	Description: Any type of input with up to 40 characters (This field is required)
	Price: 000.00 (This field is required)
	Maker: Any type of input with up to 20 characters (This field is required)
	Comment: Any type of input with up to 20 characters (This field is required)


code cited: Details and license about the citation is in the code comment block

	CMPUT 301 fall 2020 lab 3
	https://www.tutlane.com/tutorial/android/android-datepicker-with-examples
	https://developer.android.com/training/basics/intents/result
	https://howtodoinjava.com/java/regex/java-regex-date-format-validation/
 	https://developer.android.com/reference/android/app/Activity#Fragments
	https://www.tutorialspoint.com/android/android_alert_dialoges.htm
