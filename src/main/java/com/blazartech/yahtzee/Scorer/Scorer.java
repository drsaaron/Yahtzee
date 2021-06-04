/** @author Scott Aaron
    @version $Header: Scorer.java,v 1.1 2000/03/17 15:05:43 aar1069 Exp $ */

/** This defines the interface for all scoring classes in the Yahtzee
    applet. */

package com.blazartech.yahtzee.Scorer;

import com.blazartech.yahtzee.GameDice;

public interface Scorer {
    public int score(GameDice dice);
};
