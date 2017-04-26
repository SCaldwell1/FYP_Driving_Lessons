package com.example.stephen.fyp_driving_lessons;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;


public class BookingForm extends AppCompatActivity implements View.OnClickListener{
    EditText instructorName, learnerName, learnerAddress;
    Button submit;
    DatePicker date;
    TimePicker time;
    Bookings b = new Bookings();
    MyDBHandler db = new MyDBHandler(this,null,null,1);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking_form);
        instructorName = (EditText) findViewById(R.id.instructorName);
        learnerName = (EditText) findViewById(R.id.yourName);
        learnerAddress = (EditText) findViewById(R.id.yourAdress);
        submit = (Button) findViewById(R.id.submitButton);
        submit.setOnClickListener(this);
        date = (DatePicker)findViewById(R.id.lessonDate);
        time = (TimePicker)findViewById(R.id.lessonTime);
        time.setIs24HourView(true);
    }

    @Override
    public void onClick(View view) {
        if (view == submit) {
            bookLesson();
        }

    }

    private void bookLesson() {
        String lName = learnerName.getText().toString();
        String address = learnerAddress.getText().toString();
        String day = Integer.toString(date.getDayOfMonth());
        String month = Integer.toString(date.getMonth() + 1);
        String year = Integer.toString(date.getYear());
        String bDate = day + "/" + month + "/" + year;
        String hour = Integer.toString(time.getHour());
        String minute = Integer.toString(time.getMinute());
        String bTime = hour +":"+ minute;
        String iName = instructorName.getText().toString();

        if(iName.equals("Stephen Caldwell")){
            Intent intentJD  = new Intent(Intent.ACTION_SEND);
            intentJD.setType("message/rfc822");
            intentJD.putExtra(Intent.EXTRA_EMAIL, new String[]{"caldwell_s@live.com"});
            intentJD.putExtra(Intent.EXTRA_SUBJECT, lName + " booking a lesson");
            intentJD.putExtra(Intent.EXTRA_TEXT, "Name: " + lName + "\nAddress: " + address + "\nDate: " + bDate + "\nTime: " + bTime);
            try{
                startActivity(Intent.createChooser(intentJD, "Send mail..."));
            }catch (android.content.ActivityNotFoundException ex){
                Toast.makeText(BookingForm.this, "There are no email clients installed.", Toast.LENGTH_LONG).show();
            }
        }
        b.setLearnerName(lName);
        b.setAddress(address);
        b.setDate(bDate);
        b.setTime(bTime);
        db.addBooking(b);

        learnerName.setText("");
        learnerAddress.setText("");



    }
}
