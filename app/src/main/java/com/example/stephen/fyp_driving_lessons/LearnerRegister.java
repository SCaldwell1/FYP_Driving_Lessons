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
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class LearnerRegister extends Activity implements View.OnClickListener{
    Button lrnRegButton;
    EditText lrnName, lrnEmail, lrnPassword;
    ProgressDialog progressDialog;
    FirebaseAuth fAuth;
    DatabaseReference ref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_learner_register);
        lrnRegButton= (Button) findViewById(R.id.registerLearner);
        lrnName = (EditText) findViewById(R.id.registerName);
        lrnEmail = (EditText)findViewById(R.id.registerEmail);
        lrnPassword = (EditText) findViewById(R.id.registerPassword);
        lrnRegButton.setOnClickListener(this);
        progressDialog = new ProgressDialog(this);
        fAuth = FirebaseAuth.getInstance();

        if(fAuth.getCurrentUser() !=null){
            finish();
            startActivity(new Intent(getApplicationContext(), InstructorBookings.class));
        }


        ref = FirebaseDatabase.getInstance().getReference();
    }

    @Override
    public void onClick(View view) {
        if (view == lrnRegButton){
            regLearner();
        }
    }

    private void regLearner(){
        final String email = lrnEmail.getText().toString();
        String password = lrnPassword.getText().toString();
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
                            startActivity(new Intent(getApplicationContext(), LearnerDetails.class));
                            ref.child("Learners").child(fAuth.getCurrentUser().getUid()).child("Name").setValue(lrnName.getText().toString());
                            ref.child("Learners").child(fAuth.getCurrentUser().getUid()).child("Email").setValue(email);
                            lrnName.setText("");
                            lrnEmail.setText("");
                            lrnPassword.setText("");
                            Toast.makeText(LearnerRegister.this, "Register Successful", Toast.LENGTH_LONG).show();
                        }else {
                            Toast.makeText(LearnerRegister.this, "Register Failed", Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }



}
