package com.example.stephen.fyp_driving_lessons;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class instructorRegister extends Activity implements View.OnClickListener{
    Button regButton;
    EditText insName, insEmail, insPassword, insDesc, insSite;
    ProgressDialog progressDialog;
    FirebaseAuth fAuth;
    DatabaseReference ref;
    Instructors i;
    MyDBHandler mdbh ;
    ListView lv;
    ArrayList<Instructors> instructorsArray = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_instructor_register);
        regButton = (Button) findViewById(R.id.insRegButton);
        insName = (EditText) findViewById(R.id.instructorName);
        insEmail = (EditText)findViewById(R.id.instructorEmail);
        insPassword = (EditText) findViewById(R.id.instructorPassword);
        insDesc = (EditText) findViewById(R.id.instructorDescription);
        insSite = (EditText) findViewById(R.id.instructorWebsite);
        regButton.setOnClickListener(this);
        progressDialog = new ProgressDialog(this);
        fAuth = FirebaseAuth.getInstance();
        i = new Instructors();
        InstructorsList il = new InstructorsList();
        ref = FirebaseDatabase.getInstance().getReference();
        mdbh=  new MyDBHandler(this,null,null,1);

    }

    private void registerInstructor(){

         if(TextUtils.isEmpty(insEmail.getText().toString())){
             Toast.makeText(this,"Please enter an email address", Toast.LENGTH_LONG).show();
             return;
         }

        if(TextUtils.isEmpty(insPassword.getText().toString())){
            Toast.makeText(this,"Please enter a password", Toast.LENGTH_LONG).show();
        }

        progressDialog.setMessage("Registering....");
        progressDialog.show();

        fAuth.createUserWithEmailAndPassword(insEmail.getText().toString(),insPassword.getText().toString())
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        progressDialog.dismiss();
                        if(task.isSuccessful()){
                            finish();
                            startActivity(new Intent(getApplicationContext(), InstructorBookings.class));

                            i.setInstructorName(insName.getText().toString());
                            i.setInstructorEmail(insEmail.getText().toString());
                            i.setDescription(insDesc.getText().toString());
                            i.setWebsite(insSite.getText().toString());
                            ref.child("Instructors").child(fAuth.getCurrentUser().getUid()).child("Name").setValue(insName.getText().toString());
                            ref.child("Instructors").child(fAuth.getCurrentUser().getUid()).child("Email").setValue(insEmail.getText().toString());
                            ref.child("Instructors").child(fAuth.getCurrentUser().getUid()).child("Description").setValue(insDesc.getText().toString());
                            ref.child("Instructors").child(fAuth.getCurrentUser().getUid()).child("Website").setValue(insSite.getText().toString());

                            mdbh.addInstructor(i);
                            mdbh.getInstructors();
                            insName.setText("");
                            insEmail.setText("");
                            insPassword.setText("");
                            Toast.makeText(instructorRegister.this, "Register Successful", Toast.LENGTH_LONG).show();
                        }else {
                            Toast.makeText(instructorRegister.this, "Register Failed", Toast.LENGTH_LONG).show();
                        }
                    }
                });

    }

    @Override
    public void onClick(View view) {
        if (view == regButton){
            registerInstructor();
        }
    }
}
