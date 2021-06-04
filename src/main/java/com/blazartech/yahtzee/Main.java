/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.blazartech.yahtzee;

import java.awt.BorderLayout;
import java.awt.Frame;

/**
 *
 * @author aar1069
 */
public class Main {
    
    public static void main(String... args) {
        Frame mainWindow = new Frame("Yahtzee");
        mainWindow.setLayout(new BorderLayout());
        GameDice dice = new GameDice();
        ScoreCard scoreCard = new ScoreCard(dice);
        mainWindow.add(dice, BorderLayout.NORTH);
        mainWindow.add(scoreCard, BorderLayout.CENTER);
        mainWindow.pack();
        mainWindow.setVisible(true);
    }
}
