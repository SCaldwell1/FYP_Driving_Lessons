package com.example.stephen.fyp_driving_lessons;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;

public class LearnerDetails extends Activity implements View.OnClickListener{
    FirebaseAuth firebaseAuth;
    Button logout, instructorList;
    TextView textView, detailView;
    MyDBHandler mdbh;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_learner_details);

        logout = (Button)findViewById(R.id.instructorLogOut);
        logout.setOnClickListener(this);
        textView = (TextView)findViewById(R.id.welcomeText);
        detailView = (TextView) findViewById(R.id.fullDetail);
        firebaseAuth = FirebaseAuth.getInstance();
        if(firebaseAuth.getCurrentUser() == null) {
            finish();
            startActivity(new Intent(this, Learner_Login.class));
        }

        instructorList = (Button) findViewById(R.id.viewInstructors);
        instructorList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(LearnerDetails.this, InstructorsList.class);
                startActivity(i);
            }
        });
        textView.setText("Welcome " + firebaseAuth.getCurrentUser().getEmail());
    }

    @Override
    public void onClick(View view) {
        if (view == logout){
            firebaseAuth.signOut();
            finish();
            startActivity(new Intent(this, Learner_Login.class));
        }
    }
}
