package it.unibo.ai.didattica.competition.tablut.piedino.search.heuristics;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import it.unibo.ai.didattica.competition.tablut.domain.State;

public class WhiteHeuristics extends Heuristics {
	
	public final static int NUM_BLACK = 16;
	public final static int NUM_WHITE = 8;  //come le variabili di gameashton tablut
	
	private final String BEST_POSITIONS = "bestPositions";
	private final String EATEN_BLACK = "eatenBlack";
	private final String WHITE_LEFT = "numberOfWhite";
	private final String NUMBER_OF_ESCAPES="numberOfEscapes";
	private final String ENCIRCLEMENT="encirclement";
	private final String KING_IN_CASTLE = "kingInCastle";
	private final String KING_NEAR_CASTLE = "kingNearCastle";  //da valutare se è utile
	
	private List<String> nearCastle = Arrays.asList(
			"e4",
			"e6",
			"f5",
			"d5"			
			);
	
	private final Map<String,Double> weights;
	
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
		weights=new HashMap<String,Double>();
		weights.put(BEST_POSITIONS, 10.0);
		weights.put(EATEN_BLACK,50.0);
		weights.put(WHITE_LEFT, 7.0);
		weights.put(NUMBER_OF_ESCAPES, 10.0);
		weights.put(ENCIRCLEMENT, 10.0);
		weights.put(KING_IN_CASTLE, 15.0);
		weights.put(KING_NEAR_CASTLE, 2.0);
	}

	@Override
	public double evaluateState(State state) {
		double totalWeight=weights.values().stream().mapToDouble(f->f).sum();
		double numEscapes= ((numberOfKingEscapes(state))/4) * weights.get(NUMBER_OF_ESCAPES);
		if (numEscapes>1) 
			return Double.POSITIVE_INFINITY;
		
		double encirclement= (countBlackNearKing(state)/4) * weights.get(ENCIRCLEMENT);
		
		double eatenBlack= ((NUM_BLACK - state.getNumberOf(State.Pawn.BLACK))/NUM_BLACK) * weights.get(EATEN_BLACK);// FIXME metti il numero tot di white come variabile presa da boh
		
		double numWhite=(state.getNumberOf(State.Pawn.WHITE)/NUM_WHITE) * weights.get(WHITE_LEFT);
		
		double kingInCastle = isKingInCastle(state) ? 1.0 : 0.0;
		
		int[] kingPos = findKing(state);
		double kingNearCastle = nearCastle.contains(state.getBox(kingPos[0], kingPos[1])) ? 1.0 : 0.0;
		
		return (encirclement+numEscapes+eatenBlack+numWhite+kingInCastle+kingNearCastle)/totalWeight;
	}
	

}
