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
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.SignInButton;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class Learner_Login extends AppCompatActivity implements View.OnClickListener{
    Button loginButton;
    Button register;
    EditText learnerEmail, learnerPassword;
    private FirebaseAuth mAuth;
    private DatabaseReference dbRef;


    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_learner_login);

        mAuth = FirebaseAuth.getInstance();
        register = (Button) findViewById(R.id.registerButton);
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Learner_Login.this, LearnerRegister.class);
                startActivity(intent);
            }
        });

        learnerEmail = (EditText) findViewById(R.id.learnerEmailLogin);
        learnerPassword = (EditText) findViewById(R.id.learnerPasswordLogin);
        loginButton = (Button) findViewById(R.id.learnerLoginButton);
        loginButton.setOnClickListener(this);
        progressDialog = new ProgressDialog(this);


        dbRef = FirebaseDatabase.getInstance().getReference();

    }

    private void learnerLogin() {
        String email = learnerEmail.getText().toString();
        String password = learnerPassword.getText().toString();
        if(TextUtils.isEmpty(email)){
            Toast.makeText(this,"Please enter an email address", Toast.LENGTH_LONG).show();
            return;
        }

        if(TextUtils.isEmpty(password)){
            Toast.makeText(this,"Please enter a password", Toast.LENGTH_LONG).show();
        }

        progressDialog.setMessage("Login....");
        progressDialog.show();


        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        progressDialog.dismiss();
                        if(task.isSuccessful()){
                            finish();
                            startActivity(new Intent(getApplicationContext(), LearnerDetails.class));
                        }
                    }
                });
    }

    @Override
    public void onClick(View view) {
        if(view == loginButton){
            learnerLogin();
        }
    }


}
