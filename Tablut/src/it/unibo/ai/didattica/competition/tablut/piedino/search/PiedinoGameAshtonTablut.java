package it.unibo.ai.didattica.competition.tablut.piedino.search;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

import it.unibo.ai.didattica.competition.tablut.piedino.search.heuristics.BlackHeuristics;
import it.unibo.ai.didattica.competition.tablut.piedino.search.heuristics.Heuristics;
import it.unibo.ai.didattica.competition.tablut.piedino.search.heuristics.WhiteHeuristics;
import it.unibo.ai.didattica.competition.tablut.domain.Action;
import it.unibo.ai.didattica.competition.tablut.domain.GameAshtonTablut;
import it.unibo.ai.didattica.competition.tablut.domain.State;
import it.unibo.ai.didattica.competition.tablut.domain.State.Turn;

public class PiedinoGameAshtonTablut extends GameAshtonTablut implements Cloneable, aima.core.search.adversarial.Game<State,Action,State.Turn> {

	private List<String> citadels;

	public PiedinoGameAshtonTablut(int repeated_moves_allowed, int cache_size, String logs_folder, String whiteName,
			String blackName) {
		super(repeated_moves_allowed, cache_size, logs_folder, whiteName, blackName);

		this.citadels = new ArrayList<String>();

		this.citadels.add("a4");
		this.citadels.add("a5");
		this.citadels.add("a6");
		this.citadels.add("b5");
		this.citadels.add("d1");
		this.citadels.add("e1");
		this.citadels.add("f1");
		this.citadels.add("e2");
		this.citadels.add("i4");
		this.citadels.add("i5");
		this.citadels.add("i6");
		this.citadels.add("h5");
		this.citadels.add("d9");
		this.citadels.add("e9");
		this.citadels.add("f9");
		this.citadels.add("e8");

	}
	
	private List<Action> sortActionList (List<Action> actions) {
		return actions;
	}

	@Override
	public State getInitialState() {
		return null;
	}

	@Override
	public State.Turn getPlayer(State state) {
		return state.getTurn();
	}

	@Override
	public State.Turn[] getPlayers() {
		State.Turn []retval={State.Turn.BLACK,State.Turn.WHITE};
		return retval;
	}

	/*
	 * FINISH SORT LAMBDA
	 */
	@Override
	public List<Action> getActions(State state) {
		State.Turn turn = state.getTurn();

		int rowIncr[] = {1, 0, -1, 0}; //rowIncr from aima library 
		int colIncr[] = {0, 1, 0, -1};


		List<Action> possibleActions = new ArrayList<Action>();

		for (int i = 0; i < state.getBoard().length; i++) { //rows of the board
			for (int j = 0; j < state.getBoard().length; j++) { //col of the board

				// if pawn color  is equal of turn color
				if (state.getPawn(i, j).toString().equals(turn.toString()) ||
						(state.getPawn(i, j).equals(State.Pawn.KING) && turn.equals(State.Turn.WHITE)) ) {

					for(int z=0; z < 4 ;z++) { //4 length of rowIncr, colIncr
						int rIncr = rowIncr[z];
						int cIncr = colIncr[z];
						
						int rBound = rIncr > 0 ? state.getBoard().length : -1;
						int cBound = cIncr > 0 ? state.getBoard().length : -1;

						int row = i + rIncr;
						int col = j + cIncr;
						
						// search on top of pawn
						while(row != rBound && col != cBound) {
						
							// break if pawn is out of citadels and it is moving on a citadel
							if (!citadels.contains(state.getBox(i, j)) && citadels.contains(state.getBox(row, col))) {
								break;
							}

							// check if we are moving on a empty cell
							else if (state.getPawn(row, col).equalsPawn(State.Pawn.EMPTY.toString())) {

								String from = state.getBox(i, j);
								String to = state.getBox(row, col);

								Action action = null;
								try {
									action = new Action(from, to, turn);
								} catch (IOException e) {
									e.printStackTrace();
								}

								// check if action is admissible and if it is, add it to list possibleActions
								try {
									this.checkMove(state.clone(), action);
									possibleActions.add(action);
								} catch (Exception e) {
									e.printStackTrace();
								}
							} else {
								// there is a pawn in the same cell and it cannot be crossed
								break;
							}
							
							row += rIncr;
							col += cIncr;
						}
					}

				}
			}
		}
		return sortActionList(possibleActions);
	}


	@Override
	public boolean isTerminal(State state) {
		Turn turn=state.getTurn();
		return turn.equals(Turn.BLACKWIN)||turn.equals(Turn.WHITEWIN)||turn.equals(Turn.DRAW);
	}

	@Override
	public State getResult(State state, Action action) {
		
		try{
			return super.checkMove(state.clone(),action);
		}
		catch(Exception e) {
			e.printStackTrace();
			return null;
		}
	}


	@Override
	public double getUtility(State state, Turn turn) {
		// if it is a terminal state
		if ((turn.equals(State.Turn.BLACK) && state.getTurn().equals(State.Turn.BLACKWIN))
				|| (turn.equals(State.Turn.WHITE) && state.getTurn().equals(State.Turn.WHITEWIN)))
			return Double.POSITIVE_INFINITY;
		else if ((turn.equals(State.Turn.BLACK) && state.getTurn().equals(State.Turn.WHITEWIN))
				|| (turn.equals(State.Turn.WHITE) && state.getTurn().equals(State.Turn.BLACKWIN)))
			return Double.NEGATIVE_INFINITY;


		// if it isn't a terminal state
		Heuristics heuristics = null;
		if (turn.equals(State.Turn.WHITE)) {
			heuristics = new WhiteHeuristics();
		} else {
			heuristics = new BlackHeuristics();
		}
		return  heuristics.evaluateState(state);		
	}


}
