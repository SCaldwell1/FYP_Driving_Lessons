package com.example.stephen.fyp_driving_lessons;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void LearnerLogin(View view){
        Intent intent = new Intent(MainActivity.this, Learner_Login.class);
        startActivity(intent);
    }

    public void bookATest (View view){
        Intent intent = new Intent(MainActivity.this, BookATest.class);
        startActivity(intent);
    }

//    public void InstructorLogin (View view){
//        Intent intent = new Intent(MainActivity.this, Instructor_Login.class);
//        startActivity(intent);
 //   }
}
