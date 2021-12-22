package com.example.dicegameapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

public class RulesPage1Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rules_page1);
    } // onCreate

    /**
     * Takes the user to the next page, displaying the next set of rules,
     * @param aView current View of the application
     */
    public void toNextPage(View aView) {
        Button nextButtonPage1 = (Button) aView;
        Intent toRulesPage2Activity = new Intent(this, RulesPage2Activity.class);
        startActivity(toRulesPage2Activity);
    } // toPreviousPage

    /**
     * Takes the user back to the previous page, the main menu.
     * @param aView current View of the application
     */
    public void toMainMenu(View aView) {
        ImageButton backButtonRulesPage1 = (ImageButton) aView;
        Intent toMainActivity = new Intent(this, MainActivity.class);
        startActivity(toMainActivity);
    } // toMainMenu

} // class