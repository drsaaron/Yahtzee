/** @author Scott Aaron
    @version $Header: NofaKindScorer.java,v 1.1 2000/03/17 15:05:41 aar1069 Exp $ */

package com.blazartech.yahtzee.Scorer;

import com.blazartech.yahtzee.Die;
import com.blazartech.yahtzee.GameDice;

/** Determine 3, 4, and 5 of a kind.  The score will be the sum
    of all dice, if valid. */
public class NofaKindScorer extends ScorerBase implements Scorer {
    private int ndice;
    public NofaKindScorer(int n) {
        super();
        ndice = n;
    }
    public int score(GameDice dice) {
        Die[] keepers = dice.getKeepers();

        // We know that there must be at least ndice keepers to be valid.
        if (ndice > keepers.length) { return 0; }

        // Validate that we have ndice of a kind.
        int value = keepers[0].currValue();
        for (int i = 1; i < keepers.length; i++) {
            if (keepers[i].currValue() != value) { return 0; }
        }

        // We indeed have n of a kind, so return the sum of all dice.
        return sumAll(dice);
    }
};
