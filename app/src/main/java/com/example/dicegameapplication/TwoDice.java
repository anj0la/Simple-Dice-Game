package com.example.dicegameapplication;

import android.content.res.Resources;
import android.view.View;
import android.widget.ImageView;

import com.example.dicegameapplication.R;

import java.util.Random;

/**
 * Creates a com.example.dicegameapplication.TwoDice, and contains methods that can be used on the com.example.dicegameapplication.TwoDice.
 */
public class TwoDice {

    // Data

    private int dice1Value;
    private int dice2Value;
    private int currentScore;
    private int score;
    private int totalScore;
    final private Random rand = new Random();

    /**
     * Assigns the values of the parameters into dice1Value and dice2Value, and
     * initializes the currentScore, score, totalScore, and rand.
     * @param value1 - value of dice 1
     * @param value2 - value of dice 2
     */
    public TwoDice(int value1, int value2) {
        dice1Value = value1;
        dice2Value = value2;
        currentScore = 0;
        score = 0;
        totalScore = 0;
    } // TwoDice Constructor

    /**
     * Default constructor.
     */
    public TwoDice() {
        this(1, 1);
    }

    // Methods

    /**
     * Simulates the roll of two dice.
     */
    public void rollDice() {
        dice1Value = rand.nextInt(1000000) % 6 + 1;
        System.out.println("Value of Dice 1: " + dice1Value);
        dice2Value = rand.nextInt(1000000) % 6 + 1;
        System.out.println("Value of Dice 2: " + dice2Value);
        currentScore = dice1Value + dice2Value;
        System.out.println("Current Score: " + currentScore);
        score = score + dice1Value + dice2Value;
        System.out.println("Score: " + score);
    }

    public int getDice1Value() {
        return dice1Value;
    }

    public int getDice2Value() {
        return dice2Value;
    }

    public int getCurrentScore() {
        return currentScore;
    }

    /**
     * Returns the total score.
     * @return the total score
     */
    public int getTotalScore() {
        return totalScore;
    } // getTotalScore

    /**
     * Returns the value of score.
     * @return value of score
     */
    public int getScore() {
        return score;
    } // getScore

    /**
     * Returns true if the value of dice 1 or dice 2 is equal to 1.
     * @return true if value of either dice is equal to 1, false otherwise
     */
    public boolean hasOne() {
        if (dice1Value == 1 && dice2Value != 1) {
            return true;
        }
        else if (dice1Value != 1 && dice2Value == 1) {
            return true;
        }
        else {
            return false;
        }
    } // ifHasOne

    /**
     * Doubles the score and returns true if the value of dice 1 and dice 2 are
     * equal to each other.
     * @return true if the value of dice 1 equals dice 2; false otherwise
     */
    public boolean isDouble() {
        if (dice1Value == dice2Value) {
            score = score - currentScore; // 37 - 12 = 25 24
            int doubleValue = currentScore * 2;
            score = score + doubleValue;
            return true;
        }
        else {
            return false;
        }
    } // isDouble

    /**
     * Adds the current score to the total score.
     * @param presentScore the current score
     */
    public void calculateScoreTotal(int presentScore) {
        totalScore = totalScore + presentScore;
    } // totalScore

    /**
     * Resets and returns the value of score.
     * @return score
     */
    public int resetScore() {
        score = 0;
        return score;
    } // resetScore

} // class
