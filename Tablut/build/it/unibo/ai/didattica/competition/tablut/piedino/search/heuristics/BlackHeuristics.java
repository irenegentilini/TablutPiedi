package it.unibo.ai.didattica.competition.tablut.piedino.search.heuristics;

import it.unibo.ai.didattica.competition.tablut.domain.State;
import java.util.HashMap;
import java.util.Map;

public class BlackHeuristics extends Heuristics{
	
	public final static double NUM_BLACK = 16.0;
	public final static double NUM_WHITE = 8.0;  //come le variabili di gameashton tablut
	
	private final String BEST_POSITIONS = "bestPositions";
	private final String EATEN_WHITE = "eatenWhite";
	private final String BLACK_LEFT = "numberOfBlack";
	private final String NUMBER_OF_ESCAPES="numberOfEscapes";
	private final String ENCIRCLEMENT="encirclement";
	
	private final Map<String,Double> weights;
	
	public BlackHeuristics() {
		super();
		weights=new HashMap<String,Double>();
		weights.put(BEST_POSITIONS, 10.0);
		weights.put(EATEN_WHITE,10.0);
		weights.put(BLACK_LEFT, 7.0);
		weights.put(NUMBER_OF_ESCAPES, 10.0);
		weights.put(ENCIRCLEMENT, 10.0);
	}
	
	/*private double normalize(double value, double min, double max) {
	    return 1 - ((value - min) / (max - min));
	}*/


	@Override
	public double evaluateState(State state) {
		double numEscapesBlocked= ((4 - numberOfKingEscapes(state))/4) * weights.get(NUMBER_OF_ESCAPES);
//		if (numEscapesBlocked<3) 
//			return Double.NEGATIVE_INFINITY;
//		
		double encirclement= (countBlackNearKing(state)/4) * weights.get(ENCIRCLEMENT);
		
		double eatenWhite= ((NUM_WHITE - state.getNumberOf(State.Pawn.WHITE))/NUM_WHITE) * weights.get(EATEN_WHITE);// FIXME metti il numero tot di white come variabile presa da boh
		
		double numBlack=(state.getNumberOf(State.Pawn.BLACK)/NUM_BLACK) * weights.get(BLACK_LEFT);
		
		return encirclement+numEscapesBlocked+eatenWhite+numBlack;
		
	}

}
