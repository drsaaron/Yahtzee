/** @author Scott Aaron
    @version $Header: ScoreCard.java,v 1.1 2000/03/17 15:06:20 aar1069 Exp $ */

package com.blazartech.yahtzee;

import com.blazartech.yahtzee.Scorer.ChanceScorer;
import com.blazartech.yahtzee.Scorer.FindAllScorer;
import com.blazartech.yahtzee.Scorer.FullHouseScorer;
import com.blazartech.yahtzee.Scorer.NofaKindScorer;
import com.blazartech.yahtzee.Scorer.StraightScorer;
import com.blazartech.yahtzee.Scorer.YahtzeeScorer;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.beans.PropertyChangeEvent;
import javax.swing.JLabel;
import javax.swing.JPanel;

/** Implement the score card. */
public class ScoreCard extends JPanel {
    /** Represent a section of the score card. */
    abstract public class ScoreCardSection extends JPanel {
        /** the array of lines in the section. */
        private ScorePanel[] lines;

        /** The label in which to display the total score. */
        JLabel score_display;

        /** A reference to the containing ScoreCard. */
        ScoreCard card;

        /** Reset the section. */
        public void reset() {
            for (ScorePanel line : lines) {
                line.reset();
            }
            updateScore();
        }

        /** Set the lines array.
         * @param l */
        protected final void setLines(ScorePanel[] l) {
            lines = l;

            // set the layout to be l.length by 1.
            setLayout(new GridLayout(lines.length + 1, 1));

            // add each line.
            for (ScorePanel line : lines) {
                add(line);
            }

            // Add a score line.
            JPanel score_panel = new JPanel(); add(score_panel);
            score_panel.setLayout(new BorderLayout());
            JLabel text = new JLabel("Score:"); score_panel.add(text, BorderLayout.WEST);
            score_display = new JLabel("0"); score_panel.add(score_display,
                                                            BorderLayout.EAST);
        }

        // Constructor.
        public ScoreCardSection(ScoreCard c) {
            super();
            card = c;
            
            c.addPropertyChangeListener("gameDice", (PropertyChangeEvent arg0) -> {
                for (ScorePanel c1 : lines) {
                    c1.setGameDice((GameDice) arg0.getNewValue());
                }
            });
        }

        // calculate the score for the section.
        public int getScore() {
            int score = 0;
            for (ScorePanel line : lines) {
                score += line.getScore();
            }
            return score;
        }

        // Update the score display.
        public void updateScore() {
            score_display.setText(Integer.toString(getScore()));
            card.showScore();  // update the card score.
        }
    };

    /** The upper section, which overrides the getScore method. */
    public class UpperScoreCardSection extends ScoreCardSection {
        public UpperScoreCardSection(ScoreCard c) {
            super(c);
            ScorePanel[] l = {
                new ScorePanel("Aces",   new FindAllScorer(1), this),
                new ScorePanel("Twos",   new FindAllScorer(2), this),
                new ScorePanel("Threes", new FindAllScorer(3), this),
                new ScorePanel("Fours",  new FindAllScorer(4), this),
                new ScorePanel("Fives",  new FindAllScorer(5), this),
                new ScorePanel("Sixes",  new FindAllScorer(6), this)
            };
            setLines(l);
        }
        
        @Override
        public int getScore() {
            int score = super.getScore();
            if (score >= 63) score += 35;
            return score;
        }
    };

    /** The lower section, which just uses the default behavior. */
    public class LowerScoreCardSection extends ScoreCardSection {
        public LowerScoreCardSection(ScoreCard c) {
            super(c);
            ScorePanel[] l = {
                new ScorePanel("3 of a kind",     new NofaKindScorer(3), this),
                new ScorePanel("4 of a kind",     new NofaKindScorer(4), this),
                new ScorePanel("Full House",      new FullHouseScorer(), this),
                new ScorePanel("Sm. Straight",    new StraightScorer(4), this),
                new ScorePanel("Lg. Straight",    new StraightScorer(5), this),
                new ScorePanel("Yahtzee!",        new YahtzeeScorer(),   this),
                new ScorePanel("Chance",          new ChanceScorer(),    this)
            };
            setLines(l);
        }
    };


    /** The upper and lower sections of the score card. */
    private final ScoreCardSection upper, lower;

    /** Label in which to display the total score. */
    JLabel total_score_display;
    
    private GameDice gameDice;

    public void setGameDice(GameDice gameDice) {
        GameDice oldDice = this.gameDice;
        this.gameDice = gameDice;
        firePropertyChange("gameDice", oldDice, gameDice);
    }

    // Constructor.
    public ScoreCard() {
        super();

        /* The layout for the panel will be two mini score cards,
           one for the upper section and one for the lower, and a
           line to display the total score. */
        setLayout(new BorderLayout());

        // Add a panel for the mini-score cards.
        JPanel cards_panel = new JPanel(); add(cards_panel, BorderLayout.CENTER);
        cards_panel.setLayout(new BorderLayout());

        // build the two sections.
        upper = new UpperScoreCardSection(this); cards_panel.add(upper, BorderLayout.WEST);
        lower = new LowerScoreCardSection(this); cards_panel.add(lower, BorderLayout.EAST);

        // build the score display.
        JPanel score_panel = new JPanel(); add(score_panel, BorderLayout.SOUTH);
        JLabel score_label = new JLabel("Total score: ");
        score_panel.add(score_label);
        total_score_display = new JLabel("000");
        score_panel.add(total_score_display);
    }

    /** get the total score.
     * @return  */
    public int getScore() { return upper.getScore() + lower.getScore(); }

    /** display the score */
    private void showScore(int s) {
        total_score_display.setText(Integer.toString(s));
    }
    public void showScore() {
        showScore(getScore());
    }

    /** Reset. */
    public void reset() {
        upper.reset(); lower.reset();
    }
};
