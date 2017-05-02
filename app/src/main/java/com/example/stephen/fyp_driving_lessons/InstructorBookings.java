package com.example.stephen.fyp_driving_lessons;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.Serializable;
import java.util.List;

public class InstructorBookings extends Activity implements View.OnClickListener, Serializable {
    Button addBooking, showBookings;
    FirebaseAuth fAuth;
    DatabaseReference ref;
    EditText lrnrName,lrnrAddress1,lrnrAddress2,lrnrAddress3;
    DatePicker bookingDate;
    TimePicker bookingTime;
    TextView welcomeTv;
    MyDBHandler bdh;
    Bookings bk = new Bookings();
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
        showBookings = (Button) findViewById(R.id.showBookings);
        showBookings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(InstructorBookings.this, BookingsList.class));

            }
        });

        welcomeTv = (TextView)findViewById(R.id.welcomeMessage);
        welcomeTv.setText("Welcome " + fAuth.getCurrentUser().getEmail());
        bookingDate.setMinDate(System.currentTimeMillis()-1000);
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
        List<String> times = bdh.getTimes();
        List<String> dates = bdh.getDates();
        if(!times.contains(time) && !dates.contains(date)) {
            ref.child("Bookings").child(fAuth.getCurrentUser().getUid()).child(name).child("Date").setValue(date);
            ref.child("Bookings").child(fAuth.getCurrentUser().getUid()).child(name).child("Time").setValue(time);
            ref.child("Bookings").child(fAuth.getCurrentUser().getUid()).child(name).child("Address").setValue(address);

            bk.setLearnerName(name);
            bk.setAddress(address);
            bk.setDate(date);
            bk.setTime(time);
            bdh.addBooking(bk);
            bdh.getAllBookings();
        }else{
            Toast.makeText(InstructorBookings.this, "Date and time already booked please choose another", Toast.LENGTH_LONG).show();
        }


    }



}
