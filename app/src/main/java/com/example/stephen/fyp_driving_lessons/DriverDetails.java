package com.example.stephen.fyp_driving_lessons;

import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class DriverDetails extends Activity implements View.OnClickListener{
    FirebaseAuth firebaseAuth;
    Button logout;
    TextView textView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driver_details);

        logout = (Button)findViewById(R.id.instructorLogOut);
        logout.setOnClickListener(this);
        textView = (TextView)findViewById(R.id.welcomeText);
        firebaseAuth = FirebaseAuth.getInstance();
        if(firebaseAuth.getCurrentUser() == null) {
            finish();
            startActivity(new Intent(this, DriverLogin.class));
        }

        FirebaseUser user = firebaseAuth.getCurrentUser();

        textView.setText("Welcome " + user.getEmail());
    }

    @Override
    public void onClick(View view) {
        if (view == logout){
            firebaseAuth.signOut();
            finish();
            startActivity(new Intent(this, DriverLogin.class));
        }
    }
}
