package com.example.stephen.fyp_driving_lessons;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;


public class BookingForm extends AppCompatActivity implements View.OnClickListener{
    EditText learnerName, learnerAddress;
    Button submit;
    DatePicker date;
    TimePicker time;
    Bookings b = new Bookings();
    MyDBHandler db = new MyDBHandler(this, null, null, 1);
    Spinner spinner, lessons;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking_form);
        learnerName = (EditText) findViewById(R.id.yourName);
        learnerAddress = (EditText) findViewById(R.id.yourAdress);
        submit = (Button) findViewById(R.id.submitButton);
        submit.setOnClickListener(this);
        date = (DatePicker)findViewById(R.id.lessonDate);
        time = (TimePicker)findViewById(R.id.lessonTime);
        time.setIs24HourView(true);
        date.setMinDate(System.currentTimeMillis()-1000);
        spinner = (Spinner) findViewById(R.id.instructorName);
        List<String> list = db.getInstructorName();
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, list);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        lessons=(Spinner) findViewById(R.id.numOfLesson);
        List<String> numbers = new ArrayList<>();
        numbers.add("1");
        numbers.add("2");
        numbers.add("3");
        numbers.add("4");
        numbers.add("5");
        numbers.add("6");
        numbers.add("7");
        numbers.add("8");
        numbers.add("9");
        numbers.add("10");
        numbers.add("11");
        numbers.add("12");
        ArrayAdapter<String>adapter1 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item,numbers);
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        lessons.setAdapter(adapter1);

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
        String iName = spinner.getSelectedItem().toString();
        String numLessons = lessons.getSelectedItem().toString();
        List <String> times = db.getTimes();
        List<String> dates = db.getDates();
        if(!times.contains(bTime) && !dates.contains(bDate)){
        if(iName.equals("Stephen Caldwell")){
            Intent intentJD  = new Intent(Intent.ACTION_SEND);
            intentJD.setType("message/rfc822");
            intentJD.putExtra(Intent.EXTRA_EMAIL, new String[]{"caldwell_s@live.com"});
            intentJD.putExtra(Intent.EXTRA_SUBJECT, lName + " booking a lesson");
            intentJD.putExtra(Intent.EXTRA_TEXT, "Name: " + lName + "\nAddress: " + address + "\nDate: " + bDate + "\nTime: " + bTime + "\nNumber of Lessons: " + numLessons);
            try{
                startActivity(Intent.createChooser(intentJD, "Send mail..."));
            }catch (android.content.ActivityNotFoundException ex){
                Toast.makeText(BookingForm.this, "There are no email clients installed.", Toast.LENGTH_LONG).show();
            }
        }
        if(iName.equals("John Doe")){
            Intent intentJD  = new Intent(Intent.ACTION_SEND);
            intentJD.setType("message/rfc822");
            intentJD.putExtra(Intent.EXTRA_EMAIL, new String[]{"jd@gmail.com"});
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
        }else{
            Toast.makeText(BookingForm.this, "Date and time already booked please choose another", Toast.LENGTH_LONG).show();
        }

        learnerName.setText("");
        learnerAddress.setText("");



    }
}
