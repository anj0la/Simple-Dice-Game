package com.example.dicegameapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class RulesActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rules);
    }

    /**
     * Takes the user to the home page.
     * @param aView - current View
     */
    public void toHomePage(View aView) {
        Button homeButton = (Button) aView;
        Intent toHomePage = new Intent(this, MainActivity.class);
        startActivity(toHomePage);
    }

}