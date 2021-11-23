package it.unibo.ai.didattica.competition.tablut.piedino.search.heuristics;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import it.unibo.ai.didattica.competition.tablut.domain.State;

public class WhiteHeuristics extends Heuristics {
	
	public final static double NUM_BLACK = 16.0;
	public final static double NUM_WHITE = 8.0;  //come le variabili di gameashton tablut
	
	private final String BEST_POSITIONS = "bestPositions";
	private final String EATEN_BLACK = "eatenBlack";
	private final String WHITE_LEFT = "numberOfWhite";
	private final String NUMBER_OF_ESCAPES="numberOfEscapes";
	private final String ENCIRCLEMENT="encirclement";
	private final String KING_IN_CASTLE = "kingInCastle";
	private final String KING_NEAR_CASTLE = "kingNearCastle";  //da valutare se è utile
	private final String KING_ESCAPE_POSITION="kingEscapePosition";
	
	private List<String> nearCastle = Arrays.asList(
			"e4",
			"e6",
			"f5",
			"d5"			
			);
	
	private final Map<String,Double> weights;

	private final List<String> bestEscapePositions = Arrays.asList(
			"c3",
			"c7",
			"g3",
			"g7"			
			);
	
	private final List<String> rhombus = Arrays.asList(
			//"e3",
			"d4",
			"f4",
			//"c5",
			//"g5",
			"d6",
			"f6"
			//"e7"
			);


	public WhiteHeuristics() {
		super();
		weights=new HashMap<String,Double>();
		weights.put(BEST_POSITIONS, 1.0);
		weights.put(EATEN_BLACK,20.0);
		weights.put(WHITE_LEFT, 14.0);
		weights.put(NUMBER_OF_ESCAPES, 20.0);
		weights.put(ENCIRCLEMENT, 6.0);
		weights.put(KING_IN_CASTLE, 3.0);
		weights.put(KING_NEAR_CASTLE, 2.0);
		weights.put(KING_ESCAPE_POSITION,4.0);
	}

	@Override
	public double evaluateState(State state) {
		double numEscapes= ((numberOfKingEscapes(state))/4) * weights.get(NUMBER_OF_ESCAPES);
//		if (numEscapes>1) 
//			return Double.POSITIVE_INFINITY;
		
		double encirclement= ((4.0-countBlackNearKing(state))/4) * weights.get(ENCIRCLEMENT);
		
		double eatenBlack= ((NUM_BLACK - state.getNumberOf(State.Pawn.BLACK))/NUM_BLACK) * weights.get(EATEN_BLACK);// FIXME metti il numero tot di white come variabile presa da boh
		
		double numWhite=(state.getNumberOf(State.Pawn.WHITE)/NUM_WHITE) * weights.get(WHITE_LEFT);
		
		double kingInCastle = (isKingInCastle(state) ? 1.0 : 0.0) * weights.get(KING_IN_CASTLE);
		
		int[] kingPos = findKing(state);
		double kingNearCastle = (nearCastle.contains(state.getBox(kingPos[0], kingPos[1])) ? 1.0 : 0.0)*weights.get(KING_NEAR_CASTLE);
		
		double escapePosition = (bestEscapePositions.contains(state.getBox(kingPos[0], kingPos[1])) ? 1.0 : 0.0)*weights.get(KING_ESCAPE_POSITION);
		double pawnsFormation= (rhombus.stream().filter(box->getPawnAt(state, box).equals(State.Pawn.WHITE)).count()/(double)rhombus.size())*weights.get(BEST_POSITIONS);
		
		if(hasWhiteSafeWin(state)) {
			System.out.println("Safe win!!\n"+state.toString());
		}
		return encirclement+numEscapes+eatenBlack+numWhite+kingInCastle+kingNearCastle+escapePosition+pawnsFormation;
	}
	

}
