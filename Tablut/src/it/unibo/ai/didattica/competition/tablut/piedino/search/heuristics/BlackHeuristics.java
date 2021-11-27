package it.unibo.ai.didattica.competition.tablut.piedino.search.heuristics;

import it.unibo.ai.didattica.competition.tablut.domain.State;
import it.unibo.ai.didattica.competition.tablut.domain.State.Pawn;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BlackHeuristics extends Heuristics{
	
	private final String BEST_POSITIONS = "bestPositions";
	private final String EATEN_WHITE = "eatenWhite";
	private final String BLACK_LEFT = "numberOfBlack";
	private final String NUMBER_OF_ESCAPES="numberOfEscapes";
	private final String ENCIRCLEMENT="encirclement";
	
	
	private final List<String> wideRhombus = Arrays.asList(
		"b3",
		"c2",
		"g2",
		"h3",
		"b7",
		"c8",
		"g8",
		"h7"
		);


	private final List<String> narrowRhombus = Arrays.asList(
		"c4",
		"d3",
		"f3",
		"g4",
		"c6",
		"d7",
		"f7",
		"g6"
		);
	private final List<String>citadels = Arrays.asList(
		"a4",
		"a5",
		"a6",
		"b5",
		"d1",
		"e1",
		"f1",
		"e2",
		"i4",
		"i5",
		"i6",
		"h5",
		"d9",
		"e9",
		"f9",
		"e8"
	);
	
	private final Map<String,Double> weights;
	
	public BlackHeuristics() {
		super();
		weights=new HashMap<String,Double>();
		weights.put(EATEN_WHITE,40.4); 
		weights.put(BLACK_LEFT, 23.1); 
		weights.put(NUMBER_OF_ESCAPES, 28.8); 
		weights.put(BEST_POSITIONS, 6.7); 
		weights.put(ENCIRCLEMENT,1.0);
	}

	@Override
	public double evaluateState(State state) {
//		if(hasKingSafeEscape(state)) 
//			return state.getTurn().equals(State.Turn.WHITE)?Double.NEGATIVE_INFINITY:Double.POSITIVE_INFINITY;
		
		double numEscapesBlocked=(1.0-Math.pow(numberOfKingEscapes(state)/4.0,2))* weights.get(NUMBER_OF_ESCAPES);
		
		double eatenWhite= ((NUM_WHITE - state.getNumberOf(State.Pawn.WHITE))/NUM_WHITE) * weights.get(EATEN_WHITE);// FIXME metti il numero tot di white come variabile presa da boh
		
		double numBlack=(state.getNumberOf(State.Pawn.BLACK)/NUM_BLACK) * weights.get(BLACK_LEFT);

		//double encirclement= (countBlackNearKing(state)/4.0) * weights.get(ENCIRCLEMENT);
		double encirclement=calcDistanceSupport(state)*weights.get(ENCIRCLEMENT);
		double pawnsFormation=calcPawnFormationSupport(state)*weights.get(BEST_POSITIONS);
		//double pawnsFormation=(wideRhombus.stream().filter(box->getPawnAt(state, box).equals(State.Pawn.WHITE)).count()/(double)wideRhombus.size());
		
		return encirclement+numEscapesBlocked+eatenWhite+numBlack+pawnsFormation;
		
	}
	
	private double calcPawnFormationSupport(State state) {
		double wideRhombusSupport=(wideRhombus.stream().filter(box->getPawnAt(state, box).equals(State.Pawn.WHITE)).count()/(double)wideRhombus.size());
		double narrowRhombusSupport=(narrowRhombus.stream().filter(box->getPawnAt(state, box).equals(State.Pawn.WHITE)).count()/(double)narrowRhombus.size());
		return Math.max(wideRhombusSupport, narrowRhombusSupport);
	}
	
	private boolean hasKingSafeEscape(State state) {
		int[] king= findKing(state);
		State.Pawn[][] board=state.getBoard();
		if(numberOfKingEscapes(state)>1) {
			int[] rowIncr= {1,-1,0,0};
			int[] colIncr= {0,0,1,-1};
			for (int i=0;i<4;i++) {
				int r=rowIncr[i];
				int c=colIncr[i];
				if((board[king[0]+r][king[1]+c].equalsPawn("B")||citadels.contains(state.getBox(king[0]+r,king[1]+c)))
						&&canBlackAttackPos(state, king[0]-r, king[1]-c)) return false;
			}
			return true;
		}
		return false;
	}
	
	private boolean canBlackAttackPos(State state, int row,int col) {
		int rowIncr[] = {1, 0, -1, 0}; 
		int colIncr[] = {0, 1, 0, -1};
		State.Pawn[][] board=state.getBoard();
		for (int i=0;i<4;i++) {
			int rIncr=rowIncr[i];
			int cIncr=colIncr[i];
			int rBound = rIncr > 0 ? state.getBoard().length : -1;
			int cBound = cIncr > 0 ? state.getBoard().length : -1;
			int r = row;
			int c = col;
			
			// search on top of pawn
			while(r != rBound && c != cBound) {
				if(board[r][c].equalsPawn("B")) return true;
				else if(!board[r][c].equalsPawn("O")) break;
				r += rIncr;
				c += cIncr;
			}
		}	
		return false;
	}
	
	private double calcDistanceSupport(State state) {
		List<Integer[]> positions= getPositionsOf(state,Pawn.BLACK);
		int[] king=findKing(state);
		double result=0.0;
		for (Integer[] pos:positions) {
			result+=1.0/Math.max(Math.abs(king[0]-pos[0]), Math.abs(king[1]-pos[1]));
		}
		return result/12.0;
	}
	

}
