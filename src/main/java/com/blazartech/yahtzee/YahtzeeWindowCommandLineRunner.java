/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.blazartech.yahtzee;

import java.awt.BorderLayout;
import java.awt.Frame;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

/**
 *
 * @author scott
 */
@Component
public class YahtzeeWindowCommandLineRunner implements CommandLineRunner {

    private static final Logger logger = LoggerFactory.getLogger(YahtzeeWindowCommandLineRunner.class);
    @Override
    public void run(String... args) throws Exception {
        logger.info("opening window");
        
        Frame mainWindow = new Frame("Yahtzee");
        mainWindow.setLayout(new BorderLayout());
        GameDice dice = new GameDice();
        ScoreCard scoreCard = new ScoreCard(dice);
        mainWindow.add(dice, BorderLayout.NORTH);
        mainWindow.add(scoreCard, BorderLayout.CENTER);
        mainWindow.pack();
        mainWindow.setVisible(true);    }
    
}
