package com.example.stephen.fyp_driving_lessons;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Map;

public class Instructor_List extends AppCompatActivity{

    private static final String TAG = null;
    String name,description, email, phone, website;
    DatabaseReference dab;
    Instructors ins = new Instructors();
    private String[] instructors;
    FirebaseAuth auth;
    int count = 0;
    TextView text;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_learner_details);
        auth = FirebaseAuth.getInstance();
        dab = FirebaseDatabase.getInstance().getReference();
        text = (TextView) findViewById(R.id.text);
        for(int i = 0; i < 2; i++) {
            int list = count++;
            dab.child("Instructors").child("" + list).addValueEventListener(new ValueEventListener() {

                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    Log.d(TAG + "Added", dataSnapshot.getValue(Object.class).toString());


                    Map<String, String> map = (Map) dataSnapshot.getValue();
                    name = map.get("Name");
                    description = map.get("Description");
                    email = map.get("Email");
                    ins.setInstructorName(name);
                    ins.setInstructorEmail(email);
                    ins.setDescription(description);

                    text.setText(ins.toString());


                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    System.out.print(databaseError.toString());
                }


            });
        }


    }
}
