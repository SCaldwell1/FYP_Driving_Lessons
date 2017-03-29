package com.example.stephen.fyp_driving_lessons;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class InstructorBookings extends Activity implements View.OnClickListener {
    Button addBooking;
    FirebaseAuth fAuth;
    DatabaseReference ref;

    static String booking;
    EditText lrnrName,lrnrAddress1,lrnrAddress2,lrnrAddress3;
    DatePicker bookingDate;
    TimePicker bookingTime;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_instructor_bookings);

        lrnrName = (EditText) findViewById(R.id.bookingName);
        lrnrAddress1 = (EditText) findViewById(R.id.bookingAddress1);
        lrnrAddress2 = (EditText) findViewById(R.id.bookingAddress2);
        lrnrAddress3 = (EditText) findViewById(R.id.bookingAddress3);
        bookingDate = (DatePicker) findViewById(R.id.bookingDate);
        bookingTime = (TimePicker) findViewById(R.id.bookingTime);
        addBooking  = (Button) findViewById(R.id.createBooking);
        addBooking.setOnClickListener(this);
        fAuth = FirebaseAuth.getInstance();
        ref = FirebaseDatabase.getInstance().getReference();
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
        String address = lrnrAddress1.getText().toString()
                + lrnrAddress2.getText().toString()
                + lrnrAddress3.getText().toString();
        ref.child("Bookings").child(fAuth.getCurrentUser().getUid()).child(name).child("Date").setValue(date);
        ref.child("Bookings").child(fAuth.getCurrentUser().getUid()).child(name).child("Time").setValue(time);
        ref.child("Bookings").child(fAuth.getCurrentUser().getUid()).child(name).child("Address").setValue(address);

        lrnrName.setText("");
        lrnrAddress1.setText("");
        lrnrAddress2.setText("");
        lrnrAddress3.setText("");

        Intent intent = new Intent(InstructorBookings.this, DriverDetails.class);
        startActivity(intent);
    }



}
