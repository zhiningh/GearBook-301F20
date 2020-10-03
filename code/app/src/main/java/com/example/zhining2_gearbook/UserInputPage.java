package com.example.zhining2_gearbook;

import androidx.appcompat.app.AppCompatActivity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import java.util.Calendar;

/**
 * Functionality:
 * The UserInputPage asks user to enter information of the gear, as well as
 * viewing and editing the details of any existing gear.
 * This class passes data back to the MainActivity to update gear data.
 * Entering Add or Edit mode will be determined by the boolean value of myEditMode.
 * The text box will assists the user in proper data entry. Input hint will display in the text-box.
 * Error message will display at the right side of the text-box if the input is invalid.
 * Button:
 * PICK DATE - User need to click PICK DATE to pick a date from calendar as the input.
 * OK - User need to click OK to save the edition and process the update of the information.
 * Cancel - User can click CANCEL to go back to the main page, no affection will be made to data.
 * EditText:
 * Date, Description, Price, Maker are required fields.
 * Comment field is optional.
 */
public class UserInputPage extends AppCompatActivity {
    private EditText desIpt, priceIpt, makerIpt, commentIpt;
    private TextView dateIpt;
    private Gear myGear;
    private int myPos;
    private boolean myEditMode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_input_page);

        // get the reference of UI element
        dateIpt = findViewById(R.id.edit_date);
        desIpt = findViewById(R.id.edit_des);
        priceIpt = findViewById(R.id.edit_price);
        makerIpt = findViewById(R.id.edit_maker);
        commentIpt = findViewById(R.id.edit_comment);

        // get the data that is sent from MainActivity class
        // The gear is already existed if bundle data is not empty
        Bundle bundle = getIntent().getExtras();
        if(bundle != null) {
            myPos = bundle.getInt("EDIT_POS");   // get the index of the item
            myGear= (Gear) bundle.getSerializable("EDIT_GEAR");   // get the item
            myEditMode = bundle.getBoolean("EDIT_MODE"); // get the function mode
            setTextBox(myGear);
        }

        // code cited: https://www.tutlane.com/tutorial/android/android-datepicker-with-examples
        // credit to: tutlane.com - date picker with calendar
        Button btnDatePicker = findViewById(R.id.button_pick_date);
        btnDatePicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar myCal = Calendar.getInstance();
                int year = myCal.get(Calendar.YEAR);
                int month = myCal.get(Calendar.MONTH);
                int day = myCal.get(Calendar.DAY_OF_MONTH);
                // date picker dialog
                DatePickerDialog datePicker = new DatePickerDialog(UserInputPage.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int month, int day) {
                                // set the format of the date as yyyy-mm-dd
                                String sYear = Integer.toString(year);
                                String sMonth = String.format("%2s", Integer.toString(month+1)).replace(' ', '0');
                                String sDay = String.format("%2s", Integer.toString(day)).replace(' ', '0');
                                String myDate = sYear + '-' + sMonth + '-' + sDay;
                                dateIpt.setText(myDate);
                            }
                        }, year, month, day);
                datePicker.show();
            }
        });

        // click button OK to update information
        Button btnOK = findViewById(R.id.button_ok);
        btnOK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // create a new gear with the inputs
                String date = dateIpt.getText().toString();
                String des = desIpt.getText().toString();
                String price = priceIpt.getText().toString();
                String maker = makerIpt.getText().toString();
                String comment = commentIpt.getText().toString();

                // check the validation of user input
                // only proper data can be passed to update
                Boolean areInputsValid = InputsValidation(date, des, price, maker, comment);
                if (areInputsValid) {
                    myGear = new Gear(date, des, price, maker, comment);
                    Intent intent = new Intent();
                    if (myEditMode) {
                        // User is on the Edit Gear Mode if myEditMode is true
                        // call the EditMode function to update data
                        intent.putExtra("EDIT_POS", myPos);
                        intent.putExtra("EDIT_GEAR", myGear);
                    } else {
                        // User is on the Add Gear Mode if myEditMode is false
                        // call the AddMode function to save a new gear
                        intent.putExtra("NEW_GEAR", myGear);
                    }
                    setResult(RESULT_OK, intent);   // update changes
                    finish();   // finish activity so go back to the main page
                }else {
                    Toast.makeText(getApplicationContext(), "Fail. Please check the input",
                            Toast.LENGTH_LONG).show();  // display failure message
                }
            }
        });

        // code cited: https://developer.android.com/training/basics/intents/result
        // credit to: android studio documentation - activity respond code - Jun 10th 2020 - Apache 2.0
        Button btnCancel = findViewById(R.id.button_cancel);
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                setResult(RESULT_CANCELED, intent); // no change is made
                finish();   // finish activity and go back to the previous page
            }
        });
    }

    /**
     * Displayed the original data in each field
     * @param gear gear information
     */
    public void setTextBox(Gear gear){
        dateIpt.setText(gear.getDate());
        desIpt.setText(gear.getDes());
        priceIpt.setText(gear.getPrice());
        makerIpt.setText(gear.getMaker());
        commentIpt.setText(gear.getComment());
    }

    /**
     * Update data if the user inputs are valid
     * Check all the inputs at the same time
     * Display error messages beside text-box if user inputs are invalid
     * @return a boolean that determines if user input is valid
     */
    public Boolean InputsValidation (String date, String des, String price, String maker, String comment){
        Boolean vDate = validateDate(date);
        Boolean vDes = validateDes(des);
        Boolean vPrice = validatePrice(price);
        Boolean vMaker = validateMaker(maker);
        Boolean vComment = validateComment(comment);
        return (vDate && vDes && vPrice && vMaker && vComment);
    }

    /**
     * Error message will display at the right side of the text-box if the input is invalid.
     * @param date string
     * @return a boolean that determines if user input is valid
     * code cited: https://howtodoinjava.com/java/regex/java-regex-date-format-validation/
     * credit to: Lokesh Gupta - date validation
     */
    public Boolean validateDate(String date){
        if (date.isEmpty()){
            dateIpt.setError("This field is required");
            return false;
        }else {
            return true;
        }
    }

    /**
     * Description is required and can not be empty. Maximum 40 characters for this field.
     * Error message will display at the right side of the text-box if the input is invalid.
     * @param des string
     * @return a boolean that determines if user input is valid
     */
    public Boolean validateDes(String des){
        if (des.isEmpty()){
            desIpt.setError("This field is required");
            return false;
        }else if (des.length() > 40){
            desIpt.setError("Maximum 40 characters");
            return false;
        }else {
            return true;
        }
    }

    /**
     * Price is required and can not be empty. Input entry must in the format of 0.00
     * Error message will display at the right side of the text-box if the input is invalid.
     * @param price string of whole number or decimal number with the precision of two decimal places
     * @return a boolean that determines if user input is valid
     * code cited: https://stackoverflow.com/questions/1547574/regex-for-prices
     * credit to: Andrea Ambu - currency validation - Oct 10th 2009
     */
    public Boolean validatePrice(String price){
        if (price.isEmpty()){
            priceIpt.setError("This field is required");
            return false;
        }else if (!price.matches("^\\d+(.\\d{1,2})?$")){
            priceIpt.setError("Please enter in format 0.00");
            return false;
        }else {
            return true;
        }
    }

    /**
     * Maker is required and can not be empty. Maximum 20 characters for this field.
     * Error message will display at the right side of the text-box if the input is invalid.
     * @param maker string
     * @return a boolean that determines if user input is valid
     */
    public Boolean validateMaker(String maker){
        if (maker.isEmpty()){
            makerIpt.setError("This field is required");
            return false;
        }else if (maker.length() > 20){
            makerIpt.setError("Maximum 20 characters");
            return false;
        }else {
            return true;
        }
    }

    /**
     * Comment is optional and can leave blank. Maximum 20 characters for this field.
     * Error message will display at the right side of the text-box if the input is invalid.
     * @param comment string
     * @return a boolean that determines if user input is valid
     */
    public Boolean validateComment(String comment){
        if (comment.length() > 20){
            commentIpt.setError("Maximum 20 characters");
            return false;
        }else {
            return true;
        }
    }
}
