package com.example.stephen.fyp_driving_lessons;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.webkit.WebView;

public class BookATest extends AppCompatActivity {
    private WebView bookTest;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_atest);
        bookTest = new WebView(this);
        bookTest.loadUrl("https://www.drivingtest.ie/ApplicantSelfScheduleTesting/Applicants/ApplicationSignIn.aspx");
        setContentView(bookTest);

        bookTest.setInitialScale(100);
    }

}
