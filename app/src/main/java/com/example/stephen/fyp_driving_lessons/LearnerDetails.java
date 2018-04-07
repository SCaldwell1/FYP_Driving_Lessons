package com.example.stephen.fyp_driving_lessons;

import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Map;


public class LearnerDetails extends AppCompatActivity {

    private static final String TAG = null;
    String name;
    TextView text;
    DatabaseReference dab;
    DatabaseReference actionItemRef;
    DatabaseReference actionItemUsers;
    FirebaseUser firebaseUser;
    FirebaseAuth.AuthStateListener listener;
    FirebaseAuth auth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_learner_details);

        text = (TextView) findViewById(R.id.welcomeText);
        auth = FirebaseAuth.getInstance();

        dab = FirebaseDatabase.getInstance().getReference();

                dab.child("Learners").child(auth.getCurrentUser().getUid()).addValueEventListener(new ValueEventListener() {

                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        Log.d(TAG+"Added", dataSnapshot.getValue(Object.class).toString());
//                        name = dataSnapshot.getValue(String.class);text.setText(name);
//                        text.setText(name);

                        Map<String, String> map = (Map)dataSnapshot.getValue();
                        name = map.get("Name");
                        text.setText(name);
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        text.setText(databaseError.toString());
                    }


                });
        text.setText(name);
    }
    private void fetchData(DataSnapshot snapshot) {
        LearnerDetails actionListItem = snapshot.getValue(LearnerDetails.class);
//        Learners.add(actionListItem);
    }
}
