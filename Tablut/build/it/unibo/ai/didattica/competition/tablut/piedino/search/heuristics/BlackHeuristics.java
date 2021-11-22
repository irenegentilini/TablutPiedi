package it.unibo.ai.didattica.competition.tablut.piedino.search.heuristics;

import it.unibo.ai.didattica.competition.tablut.domain.State;
import it.unibo.ai.didattica.competition.tablut.domain.State.Pawn;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;
public class BlackHeuristics extends Heuristics{
	
	private final String BEST_POSITIONS = "bestPositions";
	private final String EATEN_WHITE = "eatenWhite";
	private final String NUMBER_OF_BLACK = "numberOfBlack";
	private final String NUMBER_OF_ESCAPES="numberOfEscapes";
	private final String ENCIRCLEMENT="encirclement";
	
	private final Map<String,Double> weights;
	
	public BlackHeuristics() {
		super();
		weights=new HashMap<String,Double>();
		weights.put(BEST_POSITIONS, 10.0);
		weights.put(EATEN_WHITE,10.0);
		weights.put(NUMBER_OF_BLACK, 10.0);
		weights.put(NUMBER_OF_ESCAPES, 10.0);
		weights.put(ENCIRCLEMENT, 10.0);
	}

	@Override
	public double evaluateState(State state) {
		double totalWeight=weights.values().stream().mapToDouble(f->f).sum();
		double numEscapesBlocked= (4 - numberOfKingEscapes(state)) * weights.get(NUMBER_OF_ESCAPES);
		if (numEscapesBlocked<3) 
			return Double.NEGATIVE_INFINITY;
		
		double encirclement= countBlackNearKing(state) * weights.get(ENCIRCLEMENT);
		
		double eatenWhite= (8 - state.getNumberOf(State.Pawn.WHITE)) * weights.get(EATEN_WHITE);// FIXME metti il numero tot di white come variabile presa da boh
		
		double numBlack=state.getNumberOf(State.Pawn.BLACK) * weights.get(NUMBER_OF_BLACK);
		
		return (encirclement+numEscapesBlocked+eatenWhite+numBlack)/totalWeight;
		
	}

}
