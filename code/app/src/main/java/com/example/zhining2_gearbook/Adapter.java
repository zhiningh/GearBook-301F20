package com.example.zhining2_gearbook;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import java.util.ArrayList;

/**
 * Functionality:
 * This class receives the gear object and is responsible for
 * updating the listView displayed on the home page.
 * code cited: CMPUT 301 fall 2020 lab 3
 */
public class Adapter extends ArrayAdapter<Gear> {
    private ArrayList<Gear> gears;
    private Context context;

    /**
     * Set up the attributes of the adapter
     * @param context string that needs to display
     * @param gears gear object in the list
     */
    public Adapter(Context context, ArrayList<Gear> gears) {
        super(context,0,gears);
        this.gears = gears;
        this.context = context;
    }

    /**
     * Update the information displayed in the listView
     */
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = convertView;
        TextView date, des, price;   // maker, comment has no space to display
        if (view == null){
            view = LayoutInflater.from(context).inflate(R.layout.content,parent,false);
        }

        Gear gear = gears.get(position);

        // reference to UI element
        date = view.findViewById(R.id.date_text);
        des = view.findViewById(R.id.des_text);
        price = view.findViewById(R.id.price_text);
        // maker = view.findViewById(R.id.maker_text);
        // comment = view.findViewById(R.id.comment_text);

        // set element to new data
        date.setText(gear.getDate());
        des.setText(gear.getDes());
        price.setText(gear.getPrice());
        // maker.setText(gear.getMaker());
        // comment.setText(gear.getComment());

        return view;    // updated the list view
    }
}
