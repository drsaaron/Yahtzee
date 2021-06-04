/** @author Scott Aaron
    @version $Header: ScorePanel.java,v 1.1 2000/03/17 15:06:22 aar1069 Exp $ */

package com.blazartech.yahtzee;

import java.awt.*;
import java.awt.event.*;
import com.blazartech.yahtzee.Scorer.Scorer;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

/** A class to represent a single score.  It will draw itself in a
    Panel. */
public class ScorePanel extends JPanel implements MouseListener {
    /** Has a score already been entered? */
    private boolean score_entered;

    /** What is the current score? */
    private int score;

    /** The Scorer object used to determine the score. */
    private final Scorer scorer;

    /** A Label widget to display the score. */
    private final JLabel score_display;

    /** An internal reference to the game dice. */
    private final GameDice dice;

    /** Each ScorePanel object is contained inside a ScoreCard.ScoreCardSection
        object.  Store a reference to this object so that when a score is taken,
        the section score can be updated. */
    ScoreCard.ScoreCardSection section;

    /** The font used to display the label text. */
    private static final Font LABEL_FONT = new Font("Courier", Font.PLAIN, 10);

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
        JButton b = new JButton(label);
        b.setFont(LABEL_FONT); 
        add(b, BorderLayout.WEST);
        b.addMouseListener(this);

        // Build the label.
        score_display = new JLabel();
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
    @Override
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
    @Override
    public void mousePressed(MouseEvent e) {}
    @Override
    public void mouseReleased(MouseEvent e) {}
    @Override
    public void mouseEntered(MouseEvent e) {
        scoreAndDisplay();
    }
    @Override
    public void mouseExited(MouseEvent e) {
        if (!score_entered) {
            score = 0;
            score_display.setText("");
        }
    }

    /** Get the current score.
     * @return  */
    public int getScore() { return score; }

    /** Get the current score entry state.
     * @return  */
    public boolean getState() { return score_entered; }

    /** Set the current score entry state.
     * @param s */
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
