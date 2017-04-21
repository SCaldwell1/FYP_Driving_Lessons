package com.example.stephen.fyp_driving_lessons;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class instructorRegister extends Activity implements View.OnClickListener{
    Button regButton;
    EditText insName, insEmail, insPassword;
    ProgressDialog progressDialog;
    FirebaseAuth fAuth;
    DatabaseReference ref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_instructor_register);
        regButton = (Button) findViewById(R.id.insRegButton);
        insName = (EditText) findViewById(R.id.instructorName);
        insEmail = (EditText)findViewById(R.id.instructorEmail);
        insPassword = (EditText) findViewById(R.id.instructorPassword);
        regButton.setOnClickListener(this);
        progressDialog = new ProgressDialog(this);
        fAuth = FirebaseAuth.getInstance();

        if(fAuth.getCurrentUser() !=null){
            finish();
            startActivity(new Intent(getApplicationContext(), InstructorBookings.class));
        }


        ref = FirebaseDatabase.getInstance().getReference();
    }

    private void registerInstructor(){
        String name = insName.getText().toString();
        final String email = insEmail.getText().toString();
        String password = insPassword.getText().toString();
         if(TextUtils.isEmpty(email)){
             Toast.makeText(this,"Please enter an email address", Toast.LENGTH_LONG).show();
             return;
         }

        if(TextUtils.isEmpty(password)){
            Toast.makeText(this,"Please enter a password", Toast.LENGTH_LONG).show();
        }

        progressDialog.setMessage("Registering....");
        progressDialog.show();

        fAuth.createUserWithEmailAndPassword(email,password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        progressDialog.dismiss();
                        if(task.isSuccessful()){
                            finish();
                            startActivity(new Intent(getApplicationContext(), InstructorBookings.class));
                            ref.child("Instructors").child(fAuth.getCurrentUser().getUid()).child("Name").setValue(insName.getText().toString());
                            ref.child("Instructors").child(fAuth.getCurrentUser().getUid()).child("Email").setValue(email);
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
