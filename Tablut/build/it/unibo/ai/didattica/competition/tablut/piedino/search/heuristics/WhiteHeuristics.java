package it.unibo.ai.didattica.competition.tablut.piedino.search.heuristics;

import java.util.Random;

import it.unibo.ai.didattica.competition.tablut.domain.State;

public class WhiteHeuristics extends Heuristics {
	
	/*
    private final String BEST_POSITIONS = "bestPositions";
    private final String BLACK_EATEN = "numberOfBlackEaten";
    private final String WHITE_ALIVE = "numberOfWhiteAlive";
    private final String NUM_ESCAPES_KING = "numberOfWinEscapesKing";
    private final String BLACK_SURROUND_KING = "blackSurroundKing";
    private final String PROTECTION_KING = "protectionKing";

	
    private final static int[][] bestWhitePositions = {
            {2,3},  {3,5},
            {5,3},  {6,5}
    };

    private final static int WHITE_POSITION = bestWhitePositions.length;
	*/
	public WhiteHeuristics() {
		super();
	}

	@Override
	public double evaluateState(State state) {
		return new Random().nextInt()*10;
	}
	

}
