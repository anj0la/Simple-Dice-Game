package com.example.dicegameapplication;

/**
 * Builds the player for the dice game.
 */
public class Player {

    int playerScore;
    TwoDice dice1;
    TwoDice dice2;

    public Player() {
        dice1 = new TwoDice();
        dice2 = new TwoDice();
        playerScore = 0;
    }

    public TwoDice getDice1() {return dice1; }

    public TwoDice getDice2() {return dice2; }

    public int getPlayerScore() {return playerScore; }
}
