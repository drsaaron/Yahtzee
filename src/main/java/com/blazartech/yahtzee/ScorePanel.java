/** @author Scott Aaron
    @version $Header: ScorePanel.java,v 1.1 2000/03/17 15:06:22 aar1069 Exp $ */

package com.blazartech.yahtzee;

import java.awt.*;
import java.awt.event.*;
import com.blazartech.yahtzee.Scorer.Scorer;

/** A class to represent a single score.  It will draw itself in a
    Panel. */
public class ScorePanel extends Panel implements MouseListener {
    /** Has a score already been entered? */
    private boolean score_entered;

    /** What is the current score? */
    private int score;

    /** The Scorer object used to determine the score. */
    private Scorer scorer;

    /** A Label widget to display the score. */
    private Label score_display;

    /** An internal reference to the game dice. */
    private GameDice dice;

    /** Each ScorePanel object is contained inside a ScoreCard.ScoreCardSection
        object.  Store a reference to this object so that when a score is taken,
        the section score can be updated. */
    ScoreCard.ScoreCardSection section;

    /** The font used to display the label text. */
    private static final Font label_font = new Font("Courier", Font.PLAIN, 10);

    // constructor.
    public ScorePanel(String label, Scorer s, GameDice d, ScoreCard.ScoreCardSection sec) {
        super();

        // initialize.
        score_entered = false;
        score = 0;
        scorer = s;
        dice = d;
        section = sec;

        // Use a border layout.
        setLayout(new BorderLayout());

        // Build the pushbutton.
        Button b = new Button();
        b.setFont(label_font); b.setLabel(label);
        add(b, BorderLayout.WEST);
        b.addMouseListener(this);

        // Build the label.
        score_display = new Label();
        add(score_display, BorderLayout.EAST);
        score_display.setBackground(Color.white);
    }

    /** Determine the score and display it in the display label. */
    private void scoreAndDisplay() {
        if (!score_entered) {
            score = scorer.score(dice);
            score_display.setText(Integer.toString(score));
        }
    }

    // Handle mouse clicks.
    public void mouseClicked(MouseEvent e) {
          if (score_entered) {  // Uh oh, somebody is cheating...
                ErrorWindow ewin =
                    new ErrorWindow("Don't cheat!  You've already taken that score!");
                    return;
          }

          /* The mouseEntered handler will calculate the score and display it.  All
             we need to do is mark the score as entered and update the card section. */
          score_entered = true;

          // Update the score for the score card section.
          section.updateScore();

          // Reset the dice for the next score.
          dice.reset();
    }

    // handle the other mouse events.
    public void mousePressed(MouseEvent e) {}
    public void mouseReleased(MouseEvent e) {}
    public void mouseEntered(MouseEvent e) {
        scoreAndDisplay();
    }
    public void mouseExited(MouseEvent e) {
        if (!score_entered) {
            score = 0;
            score_display.setText("");
        }
    }

    /** Get the current score. */
    public int getScore() { return score; }

    /** Get the current score entry state. */
    public boolean getState() { return score_entered; }

    /** Set the current score entry state. */
    public void setState(boolean s) {
        score_entered = s;
        if (s == false) { score = 0; }
    }

    /** Reset the panel. */
    public void reset() {
        setState(false);
        score_display.setText(null);
    }
};
