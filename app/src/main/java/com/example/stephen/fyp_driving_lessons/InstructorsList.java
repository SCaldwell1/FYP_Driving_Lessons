package com.example.stephen.fyp_driving_lessons;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class InstructorsList extends AppCompatActivity {
    MyDBHandler mdbh;
    Instructors i = new Instructors();
    TextView instructorsTv;
    Button bLesson;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_instructors_list);

        mdbh = new MyDBHandler(this,null,null,1);
        mdbh.getInstructors();
        instructorsTv = (TextView) findViewById(R.id.insTextView);
        String dbString = mdbh.getInstructors();
        instructorsTv.setText(dbString);
        bLesson = (Button) findViewById(R.id.bookLesson);
        bLesson.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(InstructorsList.this, BookingForm.class));
            }
        });
    }
}
