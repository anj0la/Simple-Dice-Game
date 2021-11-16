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
        TextView yourTurn = (TextView) findViewById(R.id.displayTurn);
        yourTurn.setText("Your Turn!");

        // runs the player's Turn method
        playersTurn(playerDice, computerDice);

    }

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
        rollButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View aView) {
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
                    displayCurrentScore.setAlpha(0);
                    changeCurrentScoreText.setText("    Rolled A Single 1!");
                    handler.postDelayed(new Runnable() {
                        public void run() {
                            changeCurrentScoreText.setText("You Lost: ");
                            displayCurrentScore.setAlpha(1);
                            displayCurrentScore.setText(player.getScore() + "        ");
                            player.resetScore();
//                            computersTurn(player, computer);
                        }
                    }, 2000);
                }

                // checks if the player has a double
                // if so, tells the user to roll again and the roll button is enabled, running the
                // WHOLE code again
                else if (player.isDouble()) {
                    displayCurrentScore.setAlpha(0);
                    changeCurrentScoreText.setText("  Rolled A Double!");
                    handler.postDelayed(new Runnable() {
                        public void run() {
                            changeCurrentScoreText.setText("   Roll Again!");
                            rollButton.setEnabled(true);
                        }
                    }, 2000);

                }

                // if nothing like that happens, then the current score is displayed and a pop-up
                // comes up, asking the user if they'd like to roll again
                else {
                    displayCurrentScore.setAlpha(1);
                    displayCurrentScore.setText(" " + player.getScore());
                    handler.postDelayed(new Runnable() {
                        public void run() {
                            onButtonShowPopupWindowClick(aView, player, computer);
                        }
                    }, 1000);
                }
            } // onClick
        });
    } // playersTurn

    /* public void computersTurn (TwoDice player, TwoDice computer) {

        Handler handler = new Handler();
        displayTurn.setText("Comp Turn!");
        changeCurrentScoreText.setText("Current Score:   ");
        System.out.println("It's the computer's turn");

        handler.postDelayed(new Runnable() {
            public void run() {
                computer.rollDice();
                rollButton.setEnabled(false);
                displayDice1(computer);
                displayDice2(computer);
            }}, 2000);


        if (computer.hasOne()) {
            displayCurrentScore.setAlpha(0);
            changeCurrentScoreText.setText("    Rolled A Single 1!");
            handler.postDelayed(new Runnable() {
                public void run() {
                    changeCurrentScoreText.setText("Comp Lost: ");
                    displayCurrentScore.setAlpha(1);
                    displayCurrentScore.setText(computer.getScore() + "        ");
                    computer.resetScore();
                    rollButton.setEnabled(true);
                    displayTurn.setText("Your Turn!");
                    changeCurrentScoreText.setText("Current Score:   ");
                    playersTurn(player, computer);
                    }}, 2000);
        }
        else if (computer.isDouble()) {
            displayCurrentScore.setAlpha(0);
            changeCurrentScoreText.setText("  Rolled A Double!");
            handler.postDelayed(new Runnable() {
                public void run() {
                    changeCurrentScoreText.setText("   Roll Again!");
                    computersTurn(player, computer);
                        }}, 2000);
                }
        else {
            displayCurrentScore.setAlpha(1);
            computer.calculateScoreTotal(computer.getScore());
            computerScore = computer.getTotalScore();
            displayComputerTotalScore.setText("" + computerScore);
            computer.resetScore();
            displayCurrentScore.setText("0");
            rollButton.setEnabled(true);
            displayTurn.setText("Your Turn!");
            changeCurrentScoreText.setText("Current Score:   ");
            playersTurn(player, computer);
        }
    } // computersTurn */

    /**
     * Creates a popup window that asks if the player wants to roll their dice again  or not.
     * @param view - the current View, which is the activity_dice_game
     * @param player - the dice of the player
     * @param computer - the dice of the computer
     */
    public void onButtonShowPopupWindowClick(View view, TwoDice player, TwoDice computer) {

        // Inflates the layout of the popup window
        LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
        View popupView = inflater.inflate(R.layout.roll_again_popup_window, null);

        // Creates the popup window
        int width = LinearLayout.LayoutParams.WRAP_CONTENT;
        int height = LinearLayout.LayoutParams.WRAP_CONTENT;
        final PopupWindow popupWindow = new PopupWindow(popupView, width, height);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            popupWindow.setElevation(50);
        }
        // Shows the the popup window and dims everything behind the popup window
        popupWindow.showAtLocation(view, Gravity.CENTER, 0, 0);
        dimBehindOfPopUp(popupWindow);

        Button yesButton = (Button) popupView.findViewById(R.id.yesButton);
        Button noButton = (Button) popupView.findViewById(R.id.noButton);

        yesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                popupWindow.dismiss();
                rollButton.setEnabled(true);
            }
        });
        noButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TextView userTotalScore = (TextView) findViewById(R.id.userScore);
                popupWindow.dismiss();
                player.calculateScoreTotal(player.getScore());
                playerScore = player.getTotalScore();
                userTotalScore.setText("" + playerScore);
                TextView displayCurrentScore = (TextView) findViewById(R.id.currentScore);
                player.resetScore();
                displayCurrentScore.setText("0");
//                computersTurn(player, computer);
            }
        });
    }

    /**
     * Dims the background behind the popup view, making it more readable and visible to the user.
     * @param popupWindow - the current popup window in question
     */
    public static void dimBehindOfPopUp(PopupWindow popupWindow) {
        View view = popupWindow.getContentView().getRootView();
        // Gets the context of the current state of the object, popupWindow
        Context context = popupWindow.getContentView().getContext();
        WindowManager aWindowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        WindowManager.LayoutParams layoutParams = (WindowManager.LayoutParams) view.getLayoutParams();
        // enables blurring behind for the popup Window
        layoutParams.flags |= WindowManager.LayoutParams.FLAG_DIM_BEHIND;
        layoutParams.dimAmount = 0.7f;
        aWindowManager.updateViewLayout(view, layoutParams);
    } // dimBehindOfPopUp



    /* public void computersTurn(TwoDice player, TwoDice computer) {
        Random rand = new Random();
        int computerRoll = rand.nextInt(3) + 1;
        Handler handler = new Handler();
        TextView computersTurn = (TextView) findViewById(R.id.displayTurn);
        TextView displayCurrentScore = (TextView) findViewById(R.id.currentScore);
        TextView changeCurrentScoreText = (TextView) findViewById(R.id.currentScoreText);
        computersTurn.setText("Comp's Turn!");
        changeCurrentScoreText.setText("Current Score:   ");

        handler.postDelayed(new Runnable() {
            public void run() {
                displayCurrentScore.setAlpha(1);
                computer.rollDice();
                displayDice1(computer);
                displayDice2(computer);
            }
        }, 2000);

        if (computer.hasOne()) {
            displayCurrentScore.setAlpha(0);
            changeCurrentScoreText.setText("    Rolled A Single 1!");
            handler.postDelayed(new Runnable() {
                public void run() {
                    changeCurrentScoreText.setText("You Lost: ");
                    displayCurrentScore.setAlpha(1);
                    displayCurrentScore.setText(computer.getScore() + "        ");
                    computer.resetScore();
                    rollButton.setEnabled(true);
                    playersTurn(player, computer);
                }
            }, 2000);
        }
        else if (computer.isDouble()) {
            displayCurrentScore.setAlpha(0);
            changeCurrentScoreText.setText("  Rolled A Double!");
            handler.postDelayed(new Runnable() {
                public void run() {
                    changeCurrentScoreText.setText("   Roll Again!");
                    computersTurn(player, computer);
                }
            }, 2000);
        }
        else if (computerRoll == 1 || computerRoll == 2) {
            computersTurn(player, computer);
        }
        else {
            displayCurrentScore.setAlpha(1);
            TextView computerTotalScore = (TextView) findViewById(R.id.computerScore);
            computer.calculateScoreTotal(computer.getScore());
            computerScore = computer.getTotalScore();
            computerTotalScore.setText("" + computerScore);
            TextView displayTheCurrentScore = (TextView) findViewById(R.id.currentScore);
            computer.resetScore();
            displayTheCurrentScore.setText("0");
            rollButton.setEnabled(true);
            playersTurn(player, computer);
        }

    } // computersTurn */

    public int resetScore (TwoDice dice) {
        return dice.resetScore();
    }

    public int totalScore (TwoDice dice) {
        return dice.getTotalScore();
    }

    /**
     * Returns true if the total score is greater than or equal to the score goal, and false otherwise.
     * @param dice - the player or computer's dice
     * @return true if total score is greater than or equal to the score goal, false otherwise
     */
    public boolean isWinner (TwoDice dice) {
        if (dice.getTotalScore() >= SCORE_GOAL) {
            return true;
        }
        return false;
    } // isWinner

    /**
     * Returns true if the total score is less than the score goal, and false otherwise.
     * @param dice - the player or computer's dice
     * @return true if total score is less than the score goal, false otherwise
     */
    public boolean isGameOver (TwoDice dice) { // really redundant tbh
        if (dice.getTotalScore() >= SCORE_GOAL) {
            return true;
        }
        return false;
    } //isGameOver

    /**
     * Displays the value of the first dice.
     * @param dice1 - the first dice
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
     * Displays the value of the second dice.
     * @param dice2 - the second dice
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