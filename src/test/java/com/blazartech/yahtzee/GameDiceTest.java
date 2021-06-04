/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.blazartech.yahtzee;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

/**
 *
 * @author scott
 */
@ExtendWith(SpringExtension.class)
public class GameDiceTest {
    
    private static final Logger logger = LoggerFactory.getLogger(GameDiceTest.class);
    
    @TestConfiguration
    static class GameDiceTestConfiguration {
        
        @Bean
        public GameDice instance() {
            return new GameDice();
        }
    }
    
    @Autowired
    private GameDice instance;
    
    public GameDiceTest() {
    }

    @BeforeAll
    public static void setUpClass() throws Exception {
    }

    @AfterAll
    public static void tearDownClass() throws Exception {
    }

    @BeforeEach
    public void setUp() throws Exception {
    }

    @AfterEach
    public void tearDown() throws Exception {
    }
    
    /**
     * Test of getKeepers method, of class GameDice.
     */
    @Test
    public void testGetKeepers() {
        logger.info("getKeepers");

        instance.getDice()[0].setStatus(true);
        Die[] result = instance.getKeepers();
        
        assertEquals(1, result.length);
    }

    /**
     * Test of sort method, of class GameDice.
     */
    @Test
    public void testSort_0args() {
        logger.info("sort");

        int[] values = { 3, 2, 5, 1, 4 };
        Die[] dice = instance.getDice();
        for (int i = 0; i < values.length; i++) {
            dice[i].setValue(values[i]);
        }
        instance.sort();
        
        dice = instance.getDice();
        assertEquals(1, dice[0].currValue());
        assertEquals(3, dice[2].currValue());
        assertEquals(5, dice[4].currValue());
    }

    
}
