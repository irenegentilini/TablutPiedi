package it.unibo.ai.didattica.competition.tablut.piedino.search.heuristics;

import it.unibo.ai.didattica.competition.tablut.domain.State;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BlackHeuristics extends Heuristics{
	
	public final static double NUM_BLACK = 16.0;
	public final static double NUM_WHITE = 8.0;  //come le variabili di gameashton tablut
	
	private final String BEST_POSITIONS = "bestPositions";
	private final String EATEN_WHITE = "eatenWhite";
	private final String BLACK_LEFT = "numberOfBlack";
	private final String NUMBER_OF_ESCAPES="numberOfEscapes";
	private final String ENCIRCLEMENT="encirclement";
	
	/*
	//LARGO
	private final List<String> rhombus = Arrays.asList(
			"b3",
			"c2",
			"g2",
			"h3",
			"b7",
			"c8",
			"g8",
			"h7"
			);
	*/
	//STRETTO
	private final List<String> rhombus = Arrays.asList(
			"c4",
			"d3",
			"f3",
			"g4",
			"c6",
			"d7",
			"f7",
			"g6"
			);
	
	private final Map<String,Double> weights;
	
	/*
	 * 
	 */
	/*
	 * PROVE:
	 * SE ALZO TANTO IL NUMERO DI ESCAPES I NERI SI SUICIDANO E ABBASSO TROPPO I BLACK LEFT
	 * BEST_POSITIONS = 3.0
	 * EATEN_WHITE = 10.0
	 * BLACK_LEFT = 2.0
	 * NUMBER_OF_ESCAPES = 20.0
	 * ENCIRCLEMENT = 10.0
	 * 
	 * GOOD
	 * PERCENTUALE:
	 * BEST_POSITIONS = 5.0
	 * EATEN_WHITE = 32
	 * BLACK_LEFT = 24 + 5
	 * NUMBER_OF_ESCAPES = 14.0
	 * ENCIRCLEMENT = 20.0
	 * 
	 * 
	 * PERCENTUALE:
	 * BEST_POSITIONS = 5.0
	 * EATEN_WHITE = 27.0
	 * BLACK_LEFT = 29.0 
	 * NUMBER_OF_ESCAPES = 14.0
	 * ENCIRCLEMENT = 25.0
	 * 
	 */
	public BlackHeuristics() {
		super();
		weights=new HashMap<String,Double>();
		weights.put(BEST_POSITIONS, 5.0); //
		weights.put(EATEN_WHITE,32.0); //10.0 //16.00
		weights.put(BLACK_LEFT, 29.0); //5.0 //8.0 
		weights.put(NUMBER_OF_ESCAPES, 14.0); //20.0 		
		weights.put(ENCIRCLEMENT, 20.0);
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
		
		double pawnsFormation= (rhombus.stream().filter(box->getPawnAt(state, box).equals(State.Pawn.BLACK)).count()/(double)rhombus.size())*weights.get(BEST_POSITIONS);
		
		return encirclement+numEscapesBlocked+eatenWhite+numBlack+pawnsFormation;
		
	}

}
