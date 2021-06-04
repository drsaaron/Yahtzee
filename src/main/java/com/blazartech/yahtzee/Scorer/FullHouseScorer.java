/** @author Scott Aaron
    @version $Header: FullHouseScorer.java,v 1.1 2000/03/17 15:05:38 aar1069 Exp $ */

package com.blazartech.yahtzee.Scorer;

import com.blazartech.yahtzee.Die;
import com.blazartech.yahtzee.GameDice;

/** Score a full house. */
public class FullHouseScorer extends ScorerBase implements Scorer {
    public FullHouseScorer() { super(); }
    @Override
    public int score(GameDice dice) {
        // get the all the dice.
        Die[] keepers = dice.getDice();

        // Sort the dice.
        GameDice.sort(keepers);

        // There must be only 2 values present, low and high.
        int low  = keepers[0].currValue(),
            high = keepers[3].currValue(),
            mid  = keepers[2].currValue();

        /* There are only 2 cases:  the dice are
                low, low, high, high, high
           or
                low, low, low, high, high.
           Either way, keepers[0] == keepers[1] == low and
                       keepers[3] == keepers[4] == high.
           We have defined low to equal keepers[0] and high to
           equal keepers[3]. */
        if (low  != keepers[1].currValue() ||
            high != keepers[4].currValue() ||
            (mid != low && mid != high)) { // not a full house
                return 0;
             } else { // alles in Ordnung
                return 25;
             }
    }
};
