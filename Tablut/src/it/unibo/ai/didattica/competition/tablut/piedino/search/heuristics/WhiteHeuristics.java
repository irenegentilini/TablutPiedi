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
	private final String KING_IN_CASTLE = "kingInCastle";
	
	private final Map<String,Double> weights;

	private List<String> nearCastle = Arrays.asList(
			"e4",
			"e6",
			"f5",
			"d5"			
			);
	

	private final List<String> bestEscapePositions = Arrays.asList(
			"c3",
			"c7",
			"g3",
			"g7"			
			);
	
	private final List<String> square = Arrays.asList(
			"d4",
			"f4",
			"d6",
			"f6"
			);
	
	private final List<String> rectangleH = Arrays.asList(
			"b4",
			"h4",
			"b6",
			"h6"
			);
	
	private final List<String> rectangleV = Arrays.asList(
			"d2",
			"f2",
			"d8",
			"f8"
			);



	public WhiteHeuristics() {
		super();
		weights=new HashMap<String,Double>();
		weights.put(EATEN_BLACK,34.0);
		weights.put(NUMBER_OF_ESCAPES, 34.0);
		weights.put(WHITE_LEFT, 23.5);
		weights.put(KING_IN_CASTLE, 5.0);
		weights.put(BEST_POSITIONS, 3.5);
	}

	@Override
	public double evaluateState(State state) {
		double numEscapes= Math.pow(numberOfKingEscapes(state)/4.0,2) * weights.get(NUMBER_OF_ESCAPES);
		
		double eatenBlack= ((NUM_BLACK - state.getNumberOf(State.Pawn.BLACK))/NUM_BLACK) * weights.get(EATEN_BLACK);// FIXME metti il numero tot di white come variabile presa da boh
		
		double numWhite=(state.getNumberOf(State.Pawn.WHITE)/NUM_WHITE) * weights.get(WHITE_LEFT);
		
		double kingInCastle = (isKingInCastle(state) ? 1.0 : 0.0) * weights.get(KING_IN_CASTLE);
	
		double pawnsFormation= calcPawnFormationSupport(state)*weights.get(BEST_POSITIONS);
		
		return numEscapes+eatenBlack+numWhite+kingInCastle+pawnsFormation;
	}
	
	private double calcPawnFormationSupport(State state) {
		double squareVal=(square.stream().filter(box->getPawnAt(state, box).equals(State.Pawn.WHITE)).count()/(double)square.size());
		double recHVal=(rectangleH.stream().filter(box->getPawnAt(state, box).equals(State.Pawn.WHITE)).count()/(double)rectangleH.size());
		double recVVal=(rectangleV.stream().filter(box->getPawnAt(state, box).equals(State.Pawn.WHITE)).count()/(double)rectangleV.size());
		return (0.5*(recHVal+recVVal)+squareVal)/2;	
	}

}
