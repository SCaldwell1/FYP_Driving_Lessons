package com.example.stephen.fyp_driving_lessons;


import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Parcel;
import android.support.annotation.RequiresApi;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


public class DriverDetails extends Activity implements View.OnClickListener {
    FirebaseAuth firebaseAuth;
    Button logout, addBooking;
    TextView textView;
    DatabaseReference ref;
    ArrayAdapter adapter;
    ListView lv;
    ArrayList<String> eventList = new ArrayList<String>();
    DatabaseReference addedBooking;
    FirebaseAuth bAuth;
    static final int PICK_CONTACT_REQUEST = 0;
    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driver_details);
        bAuth = FirebaseAuth.getInstance();
        logout = (Button) findViewById(R.id.instructorLogOut);
        logout.setOnClickListener(this);
        textView = (TextView) findViewById(R.id.welcomeText);
        firebaseAuth = FirebaseAuth.getInstance();
        if (firebaseAuth.getCurrentUser() == null) {
            finish();
            startActivity(new Intent(this, DriverLogin.class));
        }

        FirebaseUser user = firebaseAuth.getCurrentUser();
        textView.setText("Welcome " + user.getEmail());
        addBooking = (Button) findViewById(R.id.addBookingButton);
        addBooking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DriverDetails.this, InstructorBookings.class);
                startActivity(intent);
            }
        });
        ref = FirebaseDatabase.getInstance().getReference();
        lv = (ListView) findViewById(R.id.eventsList);
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, eventList);
        addedBooking = ref.child(bAuth.getCurrentUser().getUid());

    }


    @Override
    public void onClick(View view) {
        if (view == logout) {
            firebaseAuth.signOut();
            finish();
            startActivity(new Intent(this, DriverLogin.class));
        }
    }



}

