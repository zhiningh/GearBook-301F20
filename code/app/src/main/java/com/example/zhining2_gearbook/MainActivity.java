package com.example.zhining2_gearbook;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;
import java.util.ArrayList;

/**
 * Functionality:
 * The mainActivity displays the main page of the app, and the basic functions of the app.
 * This app saves user input as the information of gear, and displays them in a list.
 * Each row contains the information of a gear.
 * Button:
 * Add - User can click ADD button to add a new gear.
 * View/Edit - User first need to click a gear from the list, then click VIEW/EDIT
 *      button to view and edit the details of specific gear.
 * Delete - User first need to click a gear from the list, then click DELETE to remove
 *      the gear from the list. An alert window will pop up to avoid unintentionally deletion.
 */
public class MainActivity extends AppCompatActivity {// implements UserInputPage.UserInputListener{
    ListView gearListView;
    ArrayAdapter<Gear> gearAdapter;
    ArrayList<Gear> gearList;
    static final int ADD_REQUEST = 0;
    static final int EDIT_REQUEST = 1;

    /**
     * Provide functionality of addition, viewing, edition, and deletion.
     * Switch to a new activity page when adding, viewing, and editing gear.
     * onClick: activate the function of each button
     * onItemClick: get the index of the selected item
     * @param savedInstanceState save the data and update display through adapter.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // create an empty list
        gearListView = findViewById(R.id.view_list);
        gearList = new ArrayList<>();
        gearAdapter = new Adapter(this, gearList);
        gearListView.setAdapter(gearAdapter);

        // click button ADD to add a new gear
        Button btnAdd = findViewById(R.id.button_add);
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, UserInputPage.class);
                startActivityForResult(intent, ADD_REQUEST);    // start a new activity
            }
        });

        // get the selected item
        gearListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {

                // first click item then click button EDIT to edit a gear
                Button btnEdit = findViewById(R.id.button_edit);
                btnEdit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Gear myGear;
                        try {
                            myGear = gearList.get(position);
                        } catch (Exception e) {
                            // ignore edition error with unselected item
                            return;
                        }
                        // pass the gear object, index, and whether it is on edit mode
                        Intent intent = new Intent(MainActivity.this, UserInputPage.class);
                        intent.putExtra("EDIT_GEAR", myGear);
                        intent.putExtra("EDIT_POS", position);
                        intent.putExtra("EDIT_MODE", true);
                        startActivityForResult(intent, EDIT_REQUEST);   // start a new activity
                    }
                });

                // first click item then click button DELETE to remove a gear
                Button btnDelete = findViewById(R.id.button_delete);
                btnDelete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Gear myGear;
                        try {
                            myGear = gearList.get(position);
                        } catch (Exception e) {
                            // ignore deletion error with unselected item
                            return;
                        }
                        // pass the index and the description of the gear to delete
                        onDeleteMode(position, myGear.getDes());
                    }
                });
            }
        });
    }

    /**
     * Received respond from corresponding activity, and invoked function of addGear or editGear.
     * @param requestCode activity that is done
     * @param resultCode result of the activity
     * @param data gear data that is passed to this function
     * code cited: https://developer.android.com/reference/android/app/Activity#Fragments
     * credit to: android studio documentation - Activity Fragments - Oct 4th 2020 - Apache 2.0
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == ADD_REQUEST) {
            Gear myGear = (Gear) data.getSerializableExtra("NEW_GEAR");
            if (resultCode == RESULT_OK) {  // addition activity is done successfully
                onAddMode(myGear);
            }
        } else if (requestCode == EDIT_REQUEST) {
            if (resultCode == RESULT_OK) {  // edition activity is done successfully
                int pos = data.getIntExtra("EDIT_POS",-1);
                Gear editGear= (Gear) data.getSerializableExtra("EDIT_GEAR");
                onEditMode(editGear, pos);
            }
        }
    }

    /**
     * Notified the adapter to add a new item
     * @param newGear gear that need to add to the list
     */
    public void onAddMode(Gear newGear) {
        gearAdapter.add(newGear);
    }

    /**
     * Notified the adapter to update the item information
     * @param editGear object that need to be updated
     * @param pos index of the object in the array list
     */
    public void onEditMode(Gear editGear, int pos) {
        gearList.set(pos, editGear);
        gearAdapter.notifyDataSetChanged();
    }

    /**
     * Removed item from the list. An alert window will pop up to avoid unintentionally deletion.
     * @param pos index of the object in the array list
     * @param gearDes description of the gear (for alert purpose)
     * code cited: https://www.tutorialspoint.com/android/android_alert_dialoges.htm
     * credit to: tutorialspoint.com - build an alert window
     */
    public void onDeleteMode(final int pos, String gearDes){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Are you sure to delete " + gearDes + "?");
        builder.setPositiveButton("yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface arg0, int arg1) {
                gearList.remove(pos);
                gearAdapter.notifyDataSetChanged();
                Toast.makeText(getApplicationContext(),"Item removed", Toast.LENGTH_LONG).show();
            }
        });
        builder.setNegativeButton("No",null);    // no nothing
        AlertDialog alert = builder.create();
        alert.show();
    }
}
