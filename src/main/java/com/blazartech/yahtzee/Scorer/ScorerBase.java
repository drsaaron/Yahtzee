/** @author Scott Aaron
    @version $Header: ScorerBase.java,v 1.1 2000/03/17 15:05:45 aar1069 Exp $ */

package com.blazartech.yahtzee.Scorer;

import com.blazartech.yahtzee.Die;
import com.blazartech.yahtzee.GameDice;

/** An base for all the scoring classes, providing some useful
    utilities. */
public class ScorerBase {
    public ScorerBase() {}

    /** A simple method to sum all the dice.
     * @param d
     * @return  */
    public int sumAll(GameDice d) {
        int sum = 0;
        Die[] dice = d.getDice();
        for (Die dice1 : dice) {
            sum += dice1.currValue();
        }
        return sum;
    }
};

