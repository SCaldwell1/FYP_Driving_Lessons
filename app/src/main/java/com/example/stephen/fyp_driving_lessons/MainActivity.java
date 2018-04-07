package com.example.stephen.fyp_driving_lessons;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;

public class MainActivity extends AppCompatActivity {

    String userId;
    DatabaseReference dab;
    DatabaseReference actionItemRef;
    DatabaseReference actionItemUsers;
    FirebaseUser firebaseUser;
    FirebaseAuth.AuthStateListener listener;
    FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                firebaseUser = firebaseAuth.getCurrentUser();
                if(firebaseUser != null) {
                    userId = firebaseUser.getUid();
                }
                else {
                    Intent intent = new Intent(MainActivity.this, Learner_Login.class);
                    startActivity(intent);
                }
            }
        };

    }

    public void LearnerLogin(View view){
        Intent intent = new Intent(MainActivity.this, Learner_Login.class);
        startActivity(intent);
    }

    public void bookATest (View view){
        Intent intent = new Intent(MainActivity.this, BookATest.class);
        startActivity(intent);
    }

    public void InstructorLogin (View view){
        Intent intent = new Intent(MainActivity.this, DriverLogin.class);
        startActivity(intent);
    }
}
