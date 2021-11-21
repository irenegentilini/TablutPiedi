package it.unibo.ai.didattica.competition.tablut.piedino.search.heuristics;

import java.io.IOException;
import java.util.ArrayList;

import it.unibo.ai.didattica.competition.tablut.domain.Action;
import it.unibo.ai.didattica.competition.tablut.domain.State;
import it.unibo.ai.didattica.competition.tablut.piedino.search.PiedinoGameAshtonTablut;

public abstract class Heuristics {

	public abstract double evaluateState(State state);
	
	/*
	 * For white player -> numero pedino
	 * Controllo se è il re è nel castello OK
	 * Check delle vie di uscite + controllo mosse con via di uscite profondità
	 * Numero delle pedine nere + Numero delle pedine nere vicino al re
	 * Numero delle pedine bianche (croce o altro)
	 * Posizione bianchi + posizione nere
	 */
	
	public boolean checkKingInCastle(State state) {
		return state.getPawn(4,4).equalsPawn("K");
	}
	
	public int checkBlackNearKing(State state) {
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
	
	public int numberOfKingExit(State state) {
		int result = 0;
		int rowIncr[] = {1, 0, -1, 0}; 
		int colIncr[] = {0, 1, 0, -1};
		int[] king = findKing(state);
		int i = king[0];
		int j = king[1];
		
		ArrayList<String> escapes = new ArrayList<String>();

		escapes.add("b1");
		escapes.add("c1");
		escapes.add("g1");
		escapes.add("h1");
		escapes.add("a2");
		escapes.add("a3");
		escapes.add("a7");
		escapes.add("a8");
		
		escapes.add("i2");
		escapes.add("i3");
		escapes.add("i7");
		escapes.add("i8");
		
		escapes.add("b9");
		escapes.add("c9");
		escapes.add("g9");
		escapes.add("h9");

		for(int z=0; z < 4 ;z++) { //4 length of rowIncr, colIncr
			int rIncr = rowIncr[z];
			int cIncr = colIncr[z];
			
			int rBound = rIncr > 0 ? state.getBoard().length : -1;
			int cBound = cIncr > 0 ? state.getBoard().length : -1;

			int row = i + rIncr;
			int col = j + cIncr;
			
			// search on top of pawn
			while(row != rBound && col != cBound) {
			
			/*	// break if pawn is out of citadels and it is moving on a citadel
				if (!citadels.contains(state.getBox(i, j)) && citadels.contains(state.getBox(row, col))) {
					break;
				}*/

				// check if we are moving on a empty cell
				if (escapes.contains(state.getBox(row, col))) {

					result ++; //know how much escapes for the king are available
					
				} else if(!state.getPawn(row, col).equalsPawn(State.Pawn.EMPTY.toString()) && state.getPawn(row, col).equalsPawn(State.Pawn.THRONE.toString())){
					// there is a pawn in the same cell and it cannot be crossed
					System.out.println("Can't cross the throne!!!");
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

