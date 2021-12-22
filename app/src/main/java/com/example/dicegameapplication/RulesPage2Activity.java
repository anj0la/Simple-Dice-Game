package com.example.dicegameapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

public class RulesPage2Activity extends AppCompatActivity {

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_rules_page2);
  } // onCreate

  /**
   * Takes the user to the previous page, displaying the previous set of rules,
   * @param aView current View of the application
   */
  public void toPreviousPage(View aView) {
    ImageButton backButtonRulesPage2 = (ImageButton) aView;
    Intent toRulesPage1Activity = new Intent(this, RulesPage1Activity.class);
    startActivity(toRulesPage1Activity);
  } // toPreviousPage

  /**
   * Takes the user to the main menu.
   * @param aView current View of the application
   */
  public void toMainMenu(View aView) {
    Button mainMenuButton = (Button) aView;
    Intent toMainActivity = new Intent(this, MainActivity.class);
    startActivity(toMainActivity);
  } // toMainMenu

} // class