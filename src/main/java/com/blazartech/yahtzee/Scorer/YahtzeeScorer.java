/** @author Scott Aaron
    @version $Header: YahtzeeScorer.java,v 1.1 2000/03/17 15:05:50 aar1069 Exp $ */

package com.blazartech.yahtzee.Scorer;

import com.blazartech.yahtzee.GameDice;

/** Determine Yahtzee.  Yahtzee is just 5 of a kind, so we derive this Scorer from
    that.  If the super.score() returns > 0, then it must be Yahtzee. */

public class YahtzeeScorer extends NofaKindScorer {
    public YahtzeeScorer() {
        super(5);
    }
    @Override
    public int score(GameDice dice) {
        return (super.score(dice) > 0) ? 50 : 0;
    }
};
