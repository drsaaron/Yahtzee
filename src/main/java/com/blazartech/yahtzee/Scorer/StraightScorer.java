/** @author Scott Aaron
    @version $Header: StraightScorer.java,v 1.1 2000/03/17 15:05:48 aar1069 Exp $ */

package com.blazartech.yahtzee.Scorer;

import com.blazartech.yahtzee.Die;
import com.blazartech.yahtzee.GameDice;

/** Score straights. */
public class StraightScorer extends ScorerBase implements Scorer {
    /** The length of the straight. */
    private int length;
    public StraightScorer(int l) {
        super();
        length = l;
    }

    public int score(GameDice dice) {
        // get the keepers.
        Die[] keepers = dice.getKeepers();

        // Check the length we must have at least length dice.
        if (length > keepers.length) { return 0; }

        // Sort the keepers in ascending order.
        GameDice.sort(keepers);

        // Ensure that this is a straight of the appropriate size.
        int ival = keepers[0].currValue();
        for (int i = 1; i < length; i++) {
            if (keepers[i].currValue() != (ival + i)) { return 0; }
        }

        // Must be a straight, so return the correct score.
        return (length == 4) ? 30 : 40;
    }
};

