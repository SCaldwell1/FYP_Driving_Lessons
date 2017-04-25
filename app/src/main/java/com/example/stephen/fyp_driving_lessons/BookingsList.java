package com.example.stephen.fyp_driving_lessons;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class BookingsList extends AppCompatActivity {
    MyDBHandler db ;
    TextView tv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bookings_list);
        db = new MyDBHandler(this,null,null,1);
        tv = (TextView) findViewById(R.id.bookingsTv);

        String bookingsDbString = db.getAllBookings();
        tv.setText(bookingsDbString);

    }
}
