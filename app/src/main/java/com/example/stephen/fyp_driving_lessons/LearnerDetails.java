package com.example.stephen.fyp_driving_lessons;



import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;


import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;

public class LearnerDetails extends AppCompatActivity {
    Button addDetails, learnerSignOut;
    DatabaseReference databaseRef = FirebaseDatabase.getInstance().getReference();
    TextView learnerName, learnerEmail;
    private ArrayList<String> arrayList = new ArrayList<String>();
    EditText phoneNumber, address1,address2,address3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_learner_details);
        addDetails = (Button) findViewById(R.id.addDetailsButton);
        learnerSignOut = (Button) findViewById(R.id.learnerSignOutButton);
        databaseRef = FirebaseDatabase.getInstance().getReference();
        learnerName = (TextView) findViewById(R.id.learnerNameEdit);
        learnerEmail = (TextView) findViewById(R.id.learnerEmailEdit);
        phoneNumber = (EditText) findViewById(R.id.learnerPhoneEdit);
        address1 = (EditText) findViewById(R.id.learnerAddress1Edit);
        address2 = (EditText) findViewById(R.id.learnerAddress2Edit);
        address3 = (EditText) findViewById(R.id.learnerAddress3Edit);
        databaseRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.child("Learners") != null) {
                    String ldName = dataSnapshot.child("Learners").child("116451586855242733728").child("Name").getValue(String.class);
                    learnerName.setText(ldName);

                    String ldEmail = dataSnapshot.child("Learners").child("116451586855242733728").child("Email").getValue(String.class);
                    learnerEmail.setText(ldEmail);
                }
                else{
                    Toast.makeText(null, "Database Empty", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        learnerSignOut.setOnClickListener(new Button.OnClickListener(){

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LearnerDetails.this, MainActivity.class);
                startActivity(intent);
            }
        });



        populateListView();
    }

    public void populateListView(){
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String userName = user.getDisplayName();
        String userEmail = user.getEmail();
        String userId = user.getUid();

        arrayList.add(userName);
        arrayList.add(userEmail);
        arrayList.add(userId);

        final ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, arrayList);
        final ListView list = (ListView) findViewById(R.id.learnerList);
        list.setAdapter(adapter);
        addDetails.setOnClickListener(new Button.OnClickListener(){

            @Override
            public void onClick(View v){
                String phone = phoneNumber.getText().toString();
                String address = address1.getText().toString() + ", " + address2.getText().toString() + ", " + address3.getText().toString();
                databaseRef.child("Learners").child("116451586855242733728").child("Phone").setValue(phone);
                databaseRef.child("Learners").child("116451586855242733728").child("Address").setValue(address);
                arrayList.add(phone);
                arrayList.add(address);
                adapter.notifyDataSetChanged();

                phoneNumber.setText("");
                address1.setText("");
                address2.setText("");
                address3.setText("");
            }
        });

    }


}

