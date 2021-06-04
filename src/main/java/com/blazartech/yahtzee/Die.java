/** @author Scott Aaron
 * @version $Header: Die.java,v 1.2 2000/03/31 16:10:55 aar1069 Exp $
 */
package com.blazartech.yahtzee;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.util.Random;
import javax.swing.JCheckBox;
import javax.swing.JPanel;

/**
 * The Die class will represent a single die for the Yahtzee applet. The class
 * will draw itself on the screen and make the value and whether or not it is a
 * keeper available.
 */
public class Die extends JPanel implements Comparable<Die> {

    // data.
    private final JPanel upper, lower;
    private final JCheckBox cbox;
    private int value;
    private final Dot dots[][] = new Dot[3][3];
    private final Random rgen;
    public final Color BGCOLOR = Color.white;

    @Override
    public int compareTo(Die arg0) {
        return Integer.compare(value, arg0.value);
    }

    /**
     * Represent a dot on a die.
     */
    private class Dot extends JPanel {

        private boolean dstate;

        public Dot() {
            super();
            dstate = false;
        }

        public void draw(boolean state) {
            dstate = state;
            Color c = (state) ? Color.black : BGCOLOR;
            Graphics g = getGraphics();
            Dimension dim = getSize();
            int size = (int) (dim.width / 1.2);

            /* Erase the outline of any previous Dot by drawing an
               unfilled oval in the background color. */
            g.setColor(BGCOLOR);
            g.drawOval(0, 0, size, size);

            // Draw the dot in the desired color.
            g.setColor(c);
            g.fillOval(0, 0, size, size);
        }

        public void draw_on() {
            draw(true);
        }

        public void draw_off() {
            draw(false);
        }

        @Override
        public void paint(Graphics g) {
            draw(dstate);
        }
    };

    // constructor.
    public Die(Random rg) {
        super();

        // initialize the values.
        value = 1;
        rgen = rg;

        /* Set the layout to be two rows and one column.  The top row will
           be the display of the actual die, the lower will have a checkbox
           to allow the user to mark the die as a keeper. */
        setLayout(new GridLayout(2, 1));

        // Set each row to be a panel.
        upper = new JPanel();
        add(upper);
        upper.setBackground(BGCOLOR);
        lower = new JPanel();
        add(lower);

        // add the checkbox.
        cbox = new JCheckBox();
        lower.add(cbox);

        /* The upper panel will have a grid layout, with 3 rows and three
           columns. */
        upper.setLayout(new GridLayout(3, 3));
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                dots[i][j] = new Dot();
                upper.add(dots[i][j]);
            }
        }

    }

    /**
     * Is the die a keeper?
     *
     * @return
     */
    public boolean is_keeper() {
        return cbox.isSelected();
    }

    /**
     * Set the keeper status.
     *
     * @param s
     */
    public void setStatus(boolean s) {
        cbox.setSelected(s);
    }

    /**
     * What is the current value?
     *
     * @return
     */
    public int currValue() {
        return value;
    }

    public void setValue(int v) {
        value = v;
    }

    /**
     * Draw the die.
     *
     * @param g
     */
    @Override
    public void paint(Graphics g) {
        draw();
    }

    public void draw() {
        // Clear the die display in the upper panel and redraw it.
        Graphics gupper = upper.getGraphics();
        Dimension supper = upper.getSize();
        gupper.setColor(BGCOLOR);
        gupper.clearRect(0, 0, supper.width, supper.height);
        gupper.fillRect(0, 0, supper.width, supper.height);

        // redraw the dots.
        boolean flags[][] = new boolean[3][3];
        switch (value) {
            case 1:
                flags[1][1] = true;
                break;
            case 2:
            case 3:
                flags[0][2] = flags[2][0] = true;
                if (value == 3) {
                    flags[1][1] = true;
                }
                break;
            case 4:
            case 5:
                flags[0][0] = flags[2][2] = flags[0][2] = flags[2][0] = true;
                if (value == 5) {
                    flags[1][1] = true;
                }
                break;
            case 6:
                for (int i = 0; i < 3; i++) {
                    flags[0][i] = flags[2][i] = true;
                }
                break;
        }
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                dots[i][j].draw(flags[i][j]);
            }
        }

    }

    /**
     * Roll the die, obtaining a new, random number.
     */
    public void roll() {
        float r = rgen.nextFloat();
        value = ((int) (r * 6.)) + 1; // get it between 1 and 6.
        draw();
    }

    /**
     * Equals operators.
     *
     * @param d
     * @return
     */
    public boolean equals(Die d) {
        return currValue() == d.currValue();
    }

    public boolean equals(Integer i) {
        return currValue() == i;
    }
};
