package com.example.stephen.fyp_driving_lessons;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ThrowOnExtraProperties;
import com.google.firebase.database.ValueEventListener;

import java.util.Map;


public class LearnerDetails extends AppCompatActivity {

    private static final String TAG = null;
    String name,type;
    TextView text;
    DatabaseReference dab;
    FirebaseAuth auth;
    Button ins_list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_learner_details);

        text = (TextView) findViewById(R.id.welcomeText);
        auth = FirebaseAuth.getInstance();
        ins_list = (Button) findViewById(R.id.viewInstructors);
        dab = FirebaseDatabase.getInstance().getReference();

                dab.child("Learners").child(auth.getCurrentUser().getUid()).addValueEventListener(new ValueEventListener() {

                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        Log.d(TAG+"Added", dataSnapshot.getValue(Object.class).toString());


                        Map<String, String> map = (Map)dataSnapshot.getValue();
                        type = map.get("User Type");
                        name = map.get("Name");
                        if(type.equals("Learner")) {
                            text.setText(name);
                        }

                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        text.setText(databaseError.toString());
                    }


                });

        text.setText(name);

        ins_list.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LearnerDetails.this, Instructor_List.class);
                startActivity(intent);
            }
        });

    }
    private void fetchData(DataSnapshot snapshot) {
        LearnerDetails actionListItem = snapshot.getValue(LearnerDetails.class);
//        Learners.add(actionListItem);
    }
}
