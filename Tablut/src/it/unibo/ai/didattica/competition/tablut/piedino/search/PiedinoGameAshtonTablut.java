package it.unibo.ai.didattica.competition.tablut.piedino.search;

import java.util.List;

import it.unibo.ai.didattica.competition.tablut.piedino.search.heuristics.BlackHeuristics;
import it.unibo.ai.didattica.competition.tablut.piedino.search.heuristics.Heuristics;
import it.unibo.ai.didattica.competition.tablut.piedino.search.heuristics.WhiteHeuristics;
import it.unibo.ai.didattica.competition.tablut.domain.Action;
import it.unibo.ai.didattica.competition.tablut.domain.GameAshtonTablut;
import it.unibo.ai.didattica.competition.tablut.domain.State;
import it.unibo.ai.didattica.competition.tablut.domain.State.Turn;

public class PiedinoGameAshtonTablut extends GameAshtonTablut implements Cloneable, aima.core.search.adversarial.Game<State,Action,State.Turn> {

	public PiedinoGameAshtonTablut(int repeated_moves_allowed, int cache_size, String logs_folder, String whiteName,
			String blackName) {
		super(repeated_moves_allowed, cache_size, logs_folder, whiteName, blackName);
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

	
	@Override
	public List<Action> getActions(State state) {
		Turn turn =state.getTurn();
		
		return null;
	}
	
	@Override
	public boolean isTerminal(State state) {
		Turn turn=state.getTurn();
		return turn.equals(Turn.BLACKWIN)||turn.equals(Turn.WHITEWIN)||turn.equals(Turn.DRAW);
	}
	
	@Override
	public State getResult(State state, Action action) {
		try{
			return super.checkMove(state,action);
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
