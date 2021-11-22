package it.unibo.ai.didattica.competition.tablut.piedino.search.heuristics;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import it.unibo.ai.didattica.competition.tablut.domain.Action;
import it.unibo.ai.didattica.competition.tablut.domain.State;
import it.unibo.ai.didattica.competition.tablut.piedino.search.PiedinoGameAshtonTablut;

public abstract class Heuristics {

	private List<String> escapes = Arrays.asList(
			"b1",
			"c1",
			"g1",
			"h1",			
			"a2",
			"a3",
			"a7",
			"a8",		
			"i2",
			"i3",
			"i7",
			"i8",
			"b9",
			"c9",
			"g9",
			"h9"
			);

	
	public List<String> getEscapes() {
		return escapes;
	}

	public abstract double evaluateState(State state);
	
	
	
	/*
	 * For white player -> numero pedino
	 * Controllo se è il re è nel castello OK
	 * Check delle vie di uscite + controllo mosse con via di uscite profondità
	 * Numero delle pedine nere + Numero delle pedine nere vicino al re
	 * Numero delle pedine bianche (croce o altro)
	 * Posizione bianchi + posizione nere
	 */
	
	public boolean isKingInCastle(State state) {
		return state.getPawn(4,4).equalsPawn("K");
	}
	
	public int countBlackNearKing(State state) {
		int[] king = findKing(state);
		int rowIncr[] = {1, 0, -1, 0}; 
		int colIncr[] = {0, 1, 0, -1};
		int result = 0; 
		
		for(int i=0; i < 4; i++) {
			int row = king[0] + rowIncr[i];
			int col = king[1] + colIncr[i];

			if(state.getPawn(row,col).equalsPawn("B")) {
				result ++; 
			}
		}
		return result;
	}
	
	public int numberOfKingEscapes(State state) {
		int result = 0;
		int rowIncr[] = {1, 0, -1, 0}; 
		int colIncr[] = {0, 1, 0, -1};
		int[] king = findKing(state);
		int i = king[0];
		int j = king[1];

		for(int k=0; k < 4 ;k++) { //4 length of rowIncr, colIncr
			int rIncr = rowIncr[k];
			int cIncr = colIncr[k];
			
			int rBound = rIncr > 0 ? state.getBoard().length : -1;
			int cBound = cIncr > 0 ? state.getBoard().length : -1;

			int row = i + rIncr;
			int col = j + cIncr;
			
			// search on top of pawn
			while(row != rBound && col != cBound) {
		
				// check if the cell is an escape cell
				if (escapes.contains(state.getBox(row, col))) {

					result ++; 
					
				//} else if(!state.getPawn(row, col).equalsPawn(State.Pawn.EMPTY.toString()) && state.getPawn(row, col).equalsPawn(State.Pawn.THRONE.toString())){
				} else if(!state.getBoard()[row][col].equals(State.Pawn.EMPTY)) {
					// there is a pawn in the same cell and it cannot be crossed
					//System.out.println(state.getBoard()[row][col]);
					break;
				}
				
				row += rIncr;
				col += cIncr;
			}
		}
		
		return result;

	}
	
	public int[] findKing(State state) {
		int[] kingCoord = new int[2];
		State.Pawn[][] board = state.getBoard();
		for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board.length; j++) {
                if (state.getPawn(i, j).equalsPawn("K")) {
                	kingCoord[0] = i;
                	kingCoord[1] = j;
                }
            }
        }
        return kingCoord;
	}
   

}

