package it.unibo.ai.didattica.competition.tablut.piedino.search.heuristics;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import it.unibo.ai.didattica.competition.tablut.domain.State;
import it.unibo.ai.didattica.competition.tablut.domain.State.Pawn;

public abstract class Heuristics {

	public final static double NUM_BLACK = 16.0;
	public final static double NUM_WHITE = 8.0;
	
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
	
	public State.Pawn getPawnAt(State state,String box) {
		int i=box.charAt(0)-'a';
		int j=Integer.parseInt(box.substring(1))-1;
		return state.getPawn(i, j);
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
	
	
//	private Pawn[] getRow(State state, int pos) {
//		State.Pawn[][] board=state.getBoard();
//		StringBuilder sb=new StringBuilder("");
//		for(int i=0;i<board.length;i++) {
//			sb.append(board[pos][i].toString());
//		}
//	}
//	
//	private Pawn[] getCol(State state, int pos) {
//		return "";
//	}
   
//	private boolean canBlackAttackPos(State state, int row,int col) {
//		int rowIncr[] = {1, 0, -1, 0}; 
//		int colIncr[] = {0, 1, 0, -1};
//		State.Pawn[][] board=state.getBoard();
//		for (int i=0;i<4;i++) {
//			int rIncr=rowIncr[i];
//			int cIncr=colIncr[i];
//			int rBound = rIncr > 0 ? state.getBoard().length : -1;
//			int cBound = cIncr > 0 ? state.getBoard().length : -1;
//			int r = row + rIncr;
//			int c = col + cIncr;
//			
//			// search on top of pawn
//			while(r != rBound && c != cBound) {
//				if(board[r][c].equalsPawn("B")) return true;
//				else if(!board[r][c].equalsPawn("O")) break;
//				r += rIncr;
//				c += cIncr;
//			}
//		}	
//		return false;
//	}
//	
//	public boolean hasWhiteSafeWin(State state) {
//		if(numberOfKingEscapes(state)>1) {
//			int[] king=findKing(state);
//			if (canBlackAttackPos(state,king[0]-1,king[1])) return false;
//			if (canBlackAttackPos(state,king[0]+1,king[1])) return false;
//			if (canBlackAttackPos(state,king[0],king[1]-1)) return false;
//			if (canBlackAttackPos(state,king[0],king[1]+1)) return false;
//			return true;
//		}
//		return false;
//	}
//	
	
	public List<Integer[]> getPositionsOf(State state, State.Pawn pawn){
		String strState=state.toLinearString().substring(0, 81);
		int pos=0;
		int boardsize=state.getBoard().length;
		List<Integer[]> result=new ArrayList<>();
		while((pos=strState.indexOf(pawn.toString(),pos))>0){
			result.add(new Integer[]{pos/boardsize,pos%boardsize});
			pos+=1;
		}
		Integer king[]= {strState.indexOf("K")/boardsize,strState.indexOf("K")%boardsize};
		if (pawn.equals(Pawn.WHITE)) result.add(king);
		Collections.sort(result,(p1,p2)->{
			int dist1=Math.max(Math.abs(p1[0]-king[0]),Math.abs(p1[1]-king[1]));
			int dist2=Math.max(Math.abs(p2[0]-king[0]),Math.abs(p2[1]-king[1]));
			return dist1-dist2;
		});
		
		return result;
	}
}

