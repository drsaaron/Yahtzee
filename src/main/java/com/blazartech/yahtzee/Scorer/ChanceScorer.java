/** @author Scott Aaron
 * @version $Header: ChanceScorer.java,v 1.1 2000/03/17 15:05:32 aar1069 Exp $
 */
package com.blazartech.yahtzee.Scorer;

import com.blazartech.yahtzee.GameDice;

/**
 * Chance scorer. This class will not use the keepers.
 */
public class ChanceScorer extends ScorerBase implements Scorer {

    public ChanceScorer() {
        super();
    }

    @Override
    public int score(GameDice dice) {
        return sumAll(dice);
    }
};
