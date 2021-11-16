package com.example.dicegameapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    /**
     * Takes the user to the rules page.
     * @param aView - current View
     */
    public void toRulesPage(View aView) {
        Button rulesButton = (Button) aView;
        Intent toTheRulesPage = new Intent(this, RulesActivity.class);
        startActivity(toTheRulesPage);
    }

    public void toGamePage(View aView) {
        Button playGame = (Button) aView;
        Intent toGamePage = new Intent(this, DiceGameActivity.class);
        startActivity(toGamePage);
    }

}