package com.example.stephen.fyp_driving_lessons;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.IntegerRes;
import android.text.TextUtils;
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

public class InstructorBookings extends Activity implements View.OnClickListener{
    Button addBooking;
    EditText lrnrName,lrnrAddress1,lrnrAddress2,lrnrAddress3;
    DatabaseReference ref;
    TextView bookings, dateTxt;
    DatePicker bookingDate;
    TimePicker bookingTime;
    FirebaseAuth bAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_instructor_bookings);
        addBooking = (Button) findViewById(R.id.createBooking);
        lrnrName = (EditText) findViewById(R.id.bookingName);
        lrnrAddress1 = (EditText) findViewById(R.id.bookingAddress1);
        lrnrAddress2 = (EditText) findViewById(R.id.bookingAddress2);
        lrnrAddress3 = (EditText) findViewById(R.id.bookingAddress3);
        addBooking.setOnClickListener(this);
        bookings = (TextView) findViewById(R.id.bookingText);
        dateTxt = (TextView) findViewById(R.id.dateText);
        bookingDate = (DatePicker) findViewById(R.id.bookingDate);
        bookingTime = (TimePicker) findViewById(R.id.bookingTime);
        bookingTime.setIs24HourView(true);
        bAuth = FirebaseAuth.getInstance();
        ref = FirebaseDatabase.getInstance().getReference();
    }

    @Override
    public void onClick(View view) {
        if(view == addBooking){
            addNewBooking();
            
        }
    }

    private void addNewBooking() {
        int day = bookingDate.getDayOfMonth();
        int month = bookingDate.getMonth() + 1;
        int year  = bookingDate.getYear();

        int hour = bookingTime.getHour();
        int minute = bookingTime.getMinute();

        final String name = lrnrName.getText().toString();
        final String address = lrnrAddress1.getText().toString()
                + "," + lrnrAddress2.getText().toString()
                + "," + lrnrAddress3.getText().toString();

            if(name.equals("") || address.equals("")) {
            Toast.makeText(this, "Please fill out form", Toast.LENGTH_LONG).show();
            }
        else{

            ref.child("Bookings").child(bAuth.getCurrentUser().getUid()).child("Date").setValue(Integer.toString(day)
                    + "/" + Integer.toString(month)
                    + "/" + Integer.toString(year));
            ref.child("Bookings").child(bAuth.getCurrentUser().getUid()).child("Time").setValue(Integer.toString(hour) + ":" + Integer.toString(minute));
            ref.child("Bookings").child(bAuth.getCurrentUser().getUid()).child("Learner's Name").setValue(name);
            ref.child("Bookings").child(bAuth.getCurrentUser().getUid()).child("Learner's Address").setValue(address);
            }




        lrnrName.setText("");
        lrnrAddress1.setText("");
        lrnrAddress2.setText("");
        lrnrAddress3.setText("");



    }



}
