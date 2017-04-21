package com.example.stephen.fyp_driving_lessons;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class DriverLogin extends AppCompatActivity implements View.OnClickListener {
    Button registerButton, loginButton;
    EditText insEmail, insPassword;
    FirebaseAuth fireAuth;
    ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driver_login);

        registerButton = (Button) findViewById(R.id.instructorRegisterButton);
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DriverLogin.this, instructorRegister.class);
                startActivity(intent);
            }
        });

        loginButton = (Button) findViewById(R.id.instructorLoginButton);
        insEmail = (EditText) findViewById(R.id.insEmail);
        insPassword = (EditText) findViewById(R.id.insPassword);
        loginButton.setOnClickListener(this);
        fireAuth = FirebaseAuth.getInstance();
        progressDialog = new ProgressDialog(this);

        if(fireAuth.getCurrentUser() !=null){
            finish();
            startActivity(new Intent(getApplicationContext(), InstructorBookings.class));
        }
    }

    private void instructorLogin() {
        String email = insEmail.getText().toString();
        String password = insPassword.getText().toString();
        if(TextUtils.isEmpty(email)){
            Toast.makeText(this,"Please enter an email address", Toast.LENGTH_LONG).show();
            return;
        }

        if(TextUtils.isEmpty(password)){
            Toast.makeText(this,"Please enter a password", Toast.LENGTH_LONG).show();
        }
        progressDialog.setMessage("Login....");
        progressDialog.show();
        fireAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        progressDialog.dismiss();
                        if(task.isSuccessful()){
                            finish();
                            startActivity(new Intent(getApplicationContext(), InstructorBookings.class));
                        }
                    }
                });
    }
    @Override
    public void onClick(View view) {
        if(view == loginButton){
            instructorLogin();
        }
    }


}
