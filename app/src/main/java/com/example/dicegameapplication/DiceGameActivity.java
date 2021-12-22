package com.example.dicegameapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import java.util.Random;

public class DiceGameActivity extends AppCompatActivity {

    int playerScore;
    int computerScore;
    Button rollButton;
    TextView displayTurn;
    TextView displayCurrentScore;
    TextView changeCurrentScoreText;
    TextView displayUserTotalScore;
    TextView displayComputerTotalScore;
    public static final int SCORE_GOAL = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dice_game);

        // creates the player and the computer's dice
        TwoDice playerDice = new TwoDice();
        TwoDice computerDice = new TwoDice();

        // initializes the player and computer's total score by getting it (starts at 0)
        playerScore = playerDice.getTotalScore();
        computerScore = computerDice.getTotalScore();

        // initializes several TextViews
        displayTurn = (TextView) findViewById(R.id.displayTurn);
        displayCurrentScore = (TextView) findViewById(R.id.currentScore);
        changeCurrentScoreText = (TextView) findViewById(R.id.currentScoreText);
        displayUserTotalScore = (TextView) findViewById(R.id.userScore);
        displayComputerTotalScore = (TextView) findViewById(R.id.computerScore);

        // gets the roll button's ID along with displayTurn to display the player's turn
        rollButton = (Button) findViewById(R.id.rollButton);
        rollButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                playersTurn(playerDice, computerDice);
            }
        });
    } // onCreate

    /**
     * Displays the player's turn, where they roll the dice, and choose whether or not to roll again
     * or stop, adding their current score to their total score. Rolling a single 1 results in the
     * player losing their current score, nothing being added to their total score, and the
     * computer's turn.
     * @param player - the dice of the player
     * @param computer - the dice of the computer
     */
    public void playersTurn (TwoDice player, TwoDice computer) {
        // when the player clicks on the roll button, the dice is rolled
        Handler handler = new Handler();
        displayTurn.setText("Your Turn!");
        changeCurrentScoreText.setText("Current Score:   ");
        // after the player clicks on the roll dice, it greys out so they can't click it again
        // also displays the value of each dice
        player.rollDice();
        rollButton.setEnabled(false);
        displayDice1(player);
        displayDice2(player);
        // checks if the player has a single one
        // if so, it displays how much the user has lost and resets the player's score
        if (player.hasOne()) {
            displayRolledASingleOne(handler, player);
        }
        // checks if the player has a double
        // if so, tells the user to roll again and the roll button is enabled, running the
        // WHOLE code again
        else if (player.isDouble()) {
            displayRolledADouble(handler);
        }
        // if nothing like that happens, then the current score is displayed and a pop-up
        // comes up, asking the user if they'd like to roll again
        else {
            displayCurrentScore(handler, player);
                }
    } // playersTurn

    public void displayCurrentScore(Handler aHandler, TwoDice player) {
        displayCurrentScore.setAlpha(1);
        displayCurrentScore.setText(" " + player.getScore());
        aHandler.postDelayed(new Runnable() {
            public void run() {
                showRollAgainPopup(player);
            }
        }, 1000);
    }

    public void displayRolledASingleOne(Handler aHandler, TwoDice player) {
        displayCurrentScore.setAlpha(0);
        changeCurrentScoreText.setText("    Rolled A Single 1!");
        aHandler.postDelayed(new Runnable() {
            public void run() {
                changeCurrentScoreText.setText("You Lost: ");
                displayCurrentScore.setAlpha(1);
                displayCurrentScore.setText(player.getScore() + "        ");
                player.resetScore();
//                            computersTurn(player, computer);
            }
        }, 2000);
    }

    public void displayRolledADouble(Handler aHandler) {
        displayCurrentScore.setAlpha(0);
        changeCurrentScoreText.setText("  Rolled A Double!");
        aHandler.postDelayed(new Runnable() {
            public void run() {
                changeCurrentScoreText.setText("   Roll Again!");
                rollButton.setEnabled(true);
            }
        }, 2000);
    }

    public void displayScore(TwoDice player) {
        TextView userTotalScore = (TextView) findViewById(R.id.userScore);
        player.calculateScoreTotal(player.getScore());
        playerScore = player.getTotalScore();
        userTotalScore.setText("" + playerScore);
        TextView displayCurrentScore = (TextView) findViewById(R.id.currentScore);
        player.resetScore();
        displayCurrentScore.setText("0");
    }

    /**
     * Creates a popup window.
     * @param layoutId the XML layout ID of the last rules popup window
     * @return - a popup window
     */
    private PopupWindow createPopUp(int layoutId) {
        // inflates layout of the popup window
        LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
        View popupView = inflater.inflate(layoutId, null);
        int width = LinearLayout.LayoutParams.WRAP_CONTENT;
        int height = LinearLayout.LayoutParams.WRAP_CONTENT;
        final PopupWindow popupWindow = new PopupWindow(popupView, width, height);
        return popupWindow;
    } // createPopUp

    /**
     * Dims the background behind the popup view, making it more readable and visible to the user.
     * @param popupWindow the current popup window
     */
    public static void dimBehindPopUp(PopupWindow popupWindow) {
        View aView = popupWindow.getContentView().getRootView();
        // gets context of the current state of the object (popupWindow)
        Context context = popupWindow.getContentView().getContext();
        WindowManager aWindowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        WindowManager.LayoutParams layoutParams = (WindowManager.LayoutParams) aView.getLayoutParams();
        layoutParams.flags |= WindowManager.LayoutParams.FLAG_DIM_BEHIND;
        layoutParams.dimAmount = 0.5f;
        aWindowManager.updateViewLayout(aView, layoutParams);
    } // dimBehindPopUp

    /**
     * Creates and shows a popup window asking  the player wants to roll their dice again or not.
     * @param player the dice of the player
     */
    public void showRollAgainPopup(TwoDice player) {
        PopupWindow rollAgainPopup = createPopUp(R.layout.roll_again_popup_window);
        // Inflates the layout of the popup window
        rollAgainPopup.showAtLocation(rollAgainPopup.getContentView().getRootView(), Gravity.CENTER, 0, 0);
        dimBehindPopUp(rollAgainPopup);
        clickedOnYesButton(rollAgainPopup);
        clickedOnNoButton(rollAgainPopup, player);
    } // showRollAgainPopup

    /**
     * Allows the user to roll the dice again.
     * @param rollAgainPopup current roll again popup
     */
    private void clickedOnYesButton(PopupWindow rollAgainPopup) {
        Button yesButton = (Button) rollAgainPopup.getContentView().findViewById(R.id.yesButton);
        yesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                rollAgainPopup.dismiss();
                rollButton.setEnabled(true);
            }
        });
    } // clickedOnYesButton

    /**
     * Dismisses the popup and displays the score of the player.
     * @param rollAgainPopup current roll again popup
     */
    private void clickedOnNoButton(PopupWindow rollAgainPopup, TwoDice player) {
        Button noButton = (Button) rollAgainPopup.getContentView().findViewById(R.id.noButton);
        noButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                rollAgainPopup.dismiss();
                displayScore(player);
            }
        });
    } // clickedOnNoButton

    public int resetScore (TwoDice dice) {
        return dice.resetScore();
    }

    public int totalScore (TwoDice dice) {
        return dice.getTotalScore();
    }

    /**
     * Returns true if the total score is greater than or equal to the score goal, and false otherwise.
     * @param dice the player or computer's dice
     * @return - true if total score is greater than or equal to the score goal, false otherwise
     */
    public boolean isWinner (TwoDice dice) {
        if (dice.getTotalScore() >= SCORE_GOAL) {
            return true;
        }
        return false;
    } // isWinner

    /**
     * Returns true if the total score is less than the score goal, and false otherwise.
     * @param dice the player or computer's dice
     * @return - true if total score is less than the score goal, false otherwise
     */
    public boolean isGameOver (TwoDice dice) { // really redundant tbh
        if (dice.getTotalScore() >= SCORE_GOAL) {
            return true;
        }
        return false;
    } //isGameOver

    /**
     * Visually displays the value of the first dice.
     * @param dice1 the first dice
     */
    public void displayDice1(TwoDice dice1) {
        ImageView diceImage = (ImageView) findViewById(R.id.dice1Value1);
        if (dice1.getDice1Value() == 1) {
            diceImage.setImageDrawable(getDrawable(R.drawable.dice1));
        } else if (dice1.getDice1Value() == 2) {
            diceImage.setImageDrawable(getDrawable(R.drawable.dice2));
        } else if (dice1.getDice1Value() == 3) {
            diceImage.setImageDrawable(getDrawable(R.drawable.dice3));
        } else if (dice1.getDice1Value() == 4) {
            diceImage.setImageDrawable(getDrawable(R.drawable.dice4));
        } else if (dice1.getDice1Value() == 5) {
            diceImage.setImageDrawable(getDrawable(R.drawable.dice5));
        } else {
            diceImage.setImageDrawable(getDrawable(R.drawable.dice6));
        } // else
    } // displayDice1

    /**
     * Visually displays the value of the second dice.
     * @param dice2 the second dice
     */
    public void displayDice2(TwoDice dice2) {
        ImageView diceImage = (ImageView) findViewById(R.id.dice2Value1);
        if (dice2.getDice2Value() == 1) {
            diceImage.setImageDrawable(getDrawable(R.drawable.dice1));
        } else if (dice2.getDice2Value() == 2) {
            diceImage.setImageDrawable(getDrawable(R.drawable.dice2));
        } else if (dice2.getDice2Value() == 3) {
            diceImage.setImageDrawable(getDrawable(R.drawable.dice3));
        } else if (dice2.getDice2Value() == 4) {
            diceImage.setImageDrawable(getDrawable(R.drawable.dice4));
        } else if (dice2.getDice2Value() == 5) {
            diceImage.setImageDrawable(getDrawable(R.drawable.dice5));
        } else {
            diceImage.setImageDrawable(getDrawable(R.drawable.dice6));
        } // else

    } // displayDice2

    /**
     * Takes the player back to the main menu.
     */
    public void toMainActivity(View aView) {
        ImageButton backButton = (ImageButton) aView;
        Intent backToMainActivity = new Intent(this, MainActivity.class);
        startActivity(backToMainActivity);
    } // toMainActivity

} // class