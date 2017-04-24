package com.example.stephen.fyp_driving_lessons;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class InstructorsList extends AppCompatActivity {

    ArrayList<String> instructorsArray = new ArrayList<>();
    MyDBHandler mdbh;
    Instructors i = new Instructors();
    ListView lv;
    TextView tv, instructorsTv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_instructors_list);
       final ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, instructorsArray);
        mdbh = new MyDBHandler(this,null,null,1);
        mdbh.getInstructors();


            instructorsTv = (TextView) findViewById(R.id.insTextView);
            String dbString = mdbh.getInstructors();
            instructorsTv.setText(dbString);




    }
}
