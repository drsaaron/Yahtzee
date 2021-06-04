/** @author Scott Aaron
    @version $Header: GameDice.java,v 1.3 2000/03/31 16:19:24 aar1069 Exp $ */

/* $Log:	GameDice.java,v $
Revision 1.3  2000/03/31  16:19:24  16:19:24  aar1069 (Aaron  Scott)
Removed the refresh button as it is no longer needed.

Revision 1.2  2000/03/31  16:14:05  16:14:05  aar1069 (Aaron  Scott)
Added the paint method.

***************************************************************************/

package com.blazartech.yahtzee;

import java.awt.BorderLayout;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import javax.swing.JButton;
import javax.swing.JPanel;

/** This class will store an array of Die object, representing the game
    dice for Yahtzee.  The object is derived from Panel, so the dice are
    automatically drawn.  A roll button is provided which will roll the
    dice, ensuring that only 3 rolls are made before being reset. */

public class GameDice extends JPanel {
    /** A random number generator. */
    private static final Random rgen = new Random();

    /** The dice. */
    private final Die[] dice;

    /** A constant to indicate how many dice there are. */
    private static final int NDICE = 5;

    /** A thread that will roll the requested die a few times, giving the
        impression of a die actually rolling. */
    private class Roller extends Thread {
        private final Die die;
        private final int NROLLS = 5;
        public Roller(Die d) {
            super();
            die = d;
        }
        
        @Override
        public void run() {
            for (int i = 0; i < NROLLS; i++) {
                die.roll();
                try {
                    Thread.sleep(250);
                } catch (InterruptedException e) {}
            }
        }
    };

    /** An array of Roller objects, to roll each die. */
    private final Roller rollers[];

    /** Current roll number. */
    private int roll_number;

    /** Paint method.
     * @param g */
    @Override
    public void paint(Graphics g) {
	for (int i = 0; i < NDICE; i++) { dice[i].paint(g); }
    }

    // Constructor.
    public GameDice() {
        super();

        // initialize data.
        dice = new Die[NDICE];
        rollers = new Roller[NDICE];
        roll_number = 0;

        /* We want a panel to store the actual dice, and one for control
           buttons. */
        setLayout(new BorderLayout());
        JPanel dice_panel = new JPanel(); add(dice_panel, BorderLayout.NORTH);
        JPanel control_panel = new JPanel(); add(control_panel, BorderLayout.SOUTH);

        // Build the dice.
        int i;
        for (i = 0; i < NDICE; i++) {
            dice[i] = new Die(rgen);
            dice_panel.add(dice[i]);
        }

        // Build the buttons.
        JButton roll_button = new JButton("roll");
        control_panel.add(roll_button);
        roll_button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                roll();
            }
        });

        JButton keep_button = new JButton("keep all");
        control_panel.add(keep_button);
        keep_button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                keepAll();
            }
        });

        JButton clear_button = new JButton("clear");
        control_panel.add(clear_button);
        clear_button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                clearAll();
            }
        });
    }

    /** Roll the non-keeper dice. */
    public void roll() {
        // Make sure this is a valid roll.
        if (roll_number == 3) {  // too many rolls.
            ErrorWindow ewin =
                new ErrorWindow("You only get 3 rolls!  Take a score!");
            return;
        }
        roll_number++;

        // do the rolls.
        for (int i = 0; i < NDICE; i++) {
            if (!dice[i].is_keeper()) {
                rollers[i] = new Roller(dice[i]);
                rollers[i].start();
            }
        }

        // Wait for the roller threads to finish.
        for (int i = 0; i < NDICE; i++) {
            try {
                rollers[i].join();
            } catch (InterruptedException e) {}
        }
    }

    /** Clear the status on all dice. */
    public void clearAll() {
        for (int i = 0; i < NDICE; i++) {
            dice[i].setStatus(false);
        }
    }

    /** Keep all the dice. */
    public void keepAll() {
        for (int i = 0; i < NDICE; i++) {
            dice[i].setStatus(true);
        }
    }

    /** Reset the game dice, clearing the status on all dice and rolling. */
    public void reset() {
        roll_number = 0;
        clearAll();
        roll();
    }

    /** Get the raw array of dice.
     * @return  */
    public Die[] getDice() { return dice; }

    /** Get the keepers.
     * @return  */
    public Die[] getKeepers() {
        List<Die> v = new ArrayList<>();
        for (Die dice1 : dice) {
            if (dice1.is_keeper()) {
                v.add(dice1);
            }
        }
        Die[] cpv = new Die[v.size()];
        v.toArray(cpv);
        return cpv;
    }

    /** Sort the dice in ascending order. */
    public void sort() { sort(dice); }
    public static void sort(Die[] d) {
        List<Die> dieList = Arrays.asList(d);
        Collections.sort(dieList);
        dieList.toArray(d); 
    }
};


