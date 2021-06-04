/** @author Scott Aaron
    @version $Header: FindAllScorer.java,v 1.1 2000/03/17 15:05:36 aar1069 Exp $ */

package com.blazartech.yahtzee.Scorer;

import com.blazartech.yahtzee.Die;
import com.blazartech.yahtzee.GameDice;

/** A scorer for the upper section, i.e. one which finds all elements
    with the specified value and sums them. */
public class FindAllScorer extends ScorerBase implements Scorer {
    private int value;
    public FindAllScorer(int v) {
        super();
        value = v;
    }

    public int score(GameDice dice) {
        int sum = 0;
        Die[] keepers = dice.getKeepers();
        for (int i = 0; i < keepers.length; i++) {
            if (keepers[i].currValue() == value) {
                sum += value;
            }
        }
        return sum;
    }
};
