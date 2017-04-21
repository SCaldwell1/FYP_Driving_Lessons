package com.example.stephen.fyp_driving_lessons;


import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.TimePicker;

import com.google.android.gms.vision.text.Text;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.Serializable;
import java.util.ArrayList;

public class InstructorBookings extends Activity implements View.OnClickListener, Serializable {
    Button addBooking, showForm;
    FirebaseAuth fAuth;
    DatabaseReference ref;
    EditText lrnrName,lrnrAddress1,lrnrAddress2,lrnrAddress3;
    DatePicker bookingDate;
    TimePicker bookingTime;
    ListView lv;
    TextView welcomeTv;
    MyDBHandler bdh;
    String booking;
    Bookings bk = new Bookings();
    ArrayList<String> bookings = new ArrayList<>();
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_instructor_bookings);

        lrnrName = (EditText) findViewById(R.id.bookingName);
        lrnrAddress1 = (EditText) findViewById(R.id.bookingAddress1);
        lrnrAddress2 = (EditText) findViewById(R.id.bookingAddress2);
        lrnrAddress3 = (EditText) findViewById(R.id.bookingAddress3);
        bookingDate = (DatePicker) findViewById(R.id.bookingDate);
        bookingTime = (TimePicker) findViewById(R.id.bookingTime);
        bookingTime.setIs24HourView(true);
        addBooking  = (Button) findViewById(R.id.createBooking);
        addBooking.setOnClickListener(this);
        fAuth = FirebaseAuth.getInstance();
        ref = FirebaseDatabase.getInstance().getReference();
        bdh = new MyDBHandler(this,null,null,1);
        showForm = (Button) findViewById(R.id.showForm);
        showForm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                lrnrName.setVisibility(View.VISIBLE);
                lrnrAddress1.setVisibility(View.VISIBLE);
                lrnrAddress2.setVisibility(View.VISIBLE);
                lrnrAddress3.setVisibility(View.VISIBLE);
                addBooking.setVisibility(View.VISIBLE);
                bookingDate.setVisibility(View.VISIBLE);
                bookingTime.setVisibility(View.VISIBLE);
                showForm.setVisibility(View.INVISIBLE);

            }
        });
        lv = (ListView) findViewById(R.id.bookingList);
        welcomeTv = (TextView)findViewById(R.id.welcomeMessage);
        welcomeTv.setText("Welcome " + fAuth.getCurrentUser().getEmail());
    }

    @Override
    public void onClick(View view){
        if(view == addBooking){
            addNewBooking();
        }
    }




    private void addNewBooking() {
        String day = Integer.toString(bookingDate.getDayOfMonth());
        String month = Integer.toString(bookingDate.getMonth() + 1);
        String year = Integer.toString(bookingDate.getYear());
        String date = day + "/" + month + "/" + year;
        String hour = Integer.toString(bookingTime.getHour());
        String minute = Integer.toString(bookingTime.getMinute());
        String time = hour +":"+ minute;
        String name = lrnrName.getText().toString();
        String address = lrnrAddress1.getText().toString()+ ", "
                + lrnrAddress2.getText().toString()+ ", "
                + lrnrAddress3.getText().toString();

        ref.child("Bookings").child(fAuth.getCurrentUser().getUid()).child(name).child("Date").setValue(date);
        ref.child("Bookings").child(fAuth.getCurrentUser().getUid()).child(name).child("Time").setValue(time);
        ref.child("Bookings").child(fAuth.getCurrentUser().getUid()).child(name).child("Address").setValue(address);

        bk.setLearnerName(name);
        bk.setAddress(address);
        bk.setDate(date);
        bk.setTime(time);
        bdh.addBooking(new Bookings(name, address, date, time));
        bdh.getAllBookings();
        bookings.add(name +"\n"+ address+"\n" + date+"\n" + time);
        final ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, bookings);
        lv.setAdapter(adapter);
        adapter.notifyDataSetChanged();

//        tv.setText("Name: " + bk.getLearnerName()
//                    +"\nAddress: " + bk.getAddress()
//                    +"\nDate: " + bk.getDate()
//                    +"\nTime: " + bk.getTime());


        lrnrName.setText("");
        lrnrAddress1.setText("");
        lrnrAddress2.setText("");
        lrnrAddress3.setText("");
        lrnrName.setVisibility(View.INVISIBLE);
        lrnrAddress1.setVisibility(View.INVISIBLE);
        lrnrAddress2.setVisibility(View.INVISIBLE);
        lrnrAddress3.setVisibility(View.INVISIBLE);
        addBooking.setVisibility(View.INVISIBLE);
        bookingDate.setVisibility(View.INVISIBLE);
        bookingTime.setVisibility(View.INVISIBLE);
        showForm.setVisibility(View.VISIBLE);

    }



}
