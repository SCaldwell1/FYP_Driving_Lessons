package com.example.stephen.fyp_driving_lessons;

import android.nfc.Tag;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LearnerRegister extends AppCompatActivity {
    Button register;
    EditText learnerName, learnerEmail, learnerPassword, confirmPassword;
    FirebaseAuth mAuth;
    FirebaseAuth.AuthStateListener mAuthListener;
    private static final String TAG = "SignInActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_learner_register);
        learnerName = (EditText) findViewById(R.id.registerName);
        learnerEmail = (EditText) findViewById(R.id.registerEmail);
        learnerPassword = (EditText) findViewById(R.id.registerPassword);
        confirmPassword = (EditText) findViewById(R.id.confirmPassword);
        mAuth = FirebaseAuth.getInstance();

    }

   public void registerLearner(){
       String email = learnerEmail.getText().toString();
       String password = learnerPassword.getText().toString();
       if (TextUtils.isEmpty(email)) {
           Toast.makeText(getApplicationContext(), "Enter email address!", Toast.LENGTH_SHORT).show();
           return;
       }
       if (TextUtils.isEmpty(password)) {
           Toast.makeText(getApplicationContext(), "Enter password!", Toast.LENGTH_SHORT).show();
           return;
       }
       if (password.length() < 6) {
           Toast.makeText(getApplicationContext(), "Password too short, enter minimum 6 characters!", Toast.LENGTH_SHORT).show();
           return;
       }
       mAuth.createUserWithEmailAndPassword(email, password)
               .addOnCompleteListener(LearnerRegister.this, new OnCompleteListener<AuthResult>() {
                   @Override
                   public void onComplete(@NonNull Task<AuthResult> task) {
                       Toast.makeText(LearnerRegister.this, "createUserWithEmail:onComplete" + task.isSuccessful(), Toast.LENGTH_SHORT).show();
                       if(!task.isSuccessful()){
                           Toast.makeText(LearnerRegister.this, "Authentication failed." + task.getException(),
                                   Toast.LENGTH_SHORT).show();
                       }
                   }
               });

   }


}
