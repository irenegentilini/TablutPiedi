package it.unibo.ai.didattica.competition.tablut.piedino.client;

import java.io.IOException;
import java.net.UnknownHostException;

import it.unibo.ai.didattica.competition.tablut.client.TablutClient;
import it.unibo.ai.didattica.competition.tablut.domain.State;
import it.unibo.ai.didattica.competition.tablut.domain.StateTablut;
import it.unibo.ai.didattica.competition.tablut.piedino.player.Player;
import it.unibo.ai.didattica.competition.tablut.domain.Action;
import it.unibo.ai.didattica.competition.tablut.domain.Game;
import it.unibo.ai.didattica.competition.tablut.domain.GameAshtonTablut;

public class PiedinoTablutClient extends TablutClient {

	private int game;
	private boolean debug;
	
	public PiedinoTablutClient(String player, String name, int timeout, String ipAddress,int game, boolean debug) throws UnknownHostException, IOException {
		super(player,name,timeout,ipAddress);
		this.game=game;
		this.debug=debug;
	}

	public PiedinoTablutClient(String player, String name, int timeout, String ipAddress) throws UnknownHostException, IOException {
		this(player,name,timeout,ipAddress,0,false);
	}
	
	public PiedinoTablutClient(String player, String name, String ipAddress) throws UnknownHostException, IOException {
		this(player,name,60,ipAddress,0,false);
	}
	
	public PiedinoTablutClient(String player, String name) throws UnknownHostException, IOException {
		this(player,name,60,"localhost",0,false);
	}
	
	
	
	@Override
	public void run() {
		try {
			declareName();	
		}catch(Exception e) {
			e.printStackTrace();
		}
		
		State state=new StateTablut(); //FIXME c'è un modo per generalizzare? 
		Game rules = new GameAshtonTablut(0, -1, "logs", "white_ai", "black_ai"); //FIXME non usiamo la classe AshtonTablutGame ma usiamo un adapter o altro
		
		Player player= new Player(state,rules); // QUALCOSA DEL GENERE POTREBBE ANDARE? \
		
		state.setTurn(State.Turn.WHITE);
		
		 // set type of game
        	while(true) {
	        try {
	            this.read();
	        } catch (ClassNotFoundException | IOException e1) {
	            e1.printStackTrace();
	            System.exit(1);
	        }
	
	        // print current state
	        state = this.getCurrentState();
	        System.out.println("Current state:");
	        System.out.println(state.toString());
	
	        // if WHITE
	        if (this.getCurrentState().getTurn().equals(StateTablut.Turn.WHITE)) {
		        if (this.getPlayer().equals(State.Turn.WHITE)) {
		            // if is my turn (WHITE)
	                // search the best move in search tree
	                Action a = player.makeAction(state);
	                try {
	                    this.write(a);
	                } catch (ClassNotFoundException | IOException e) {
	                    e.printStackTrace();
	                }
	            }
	
	            // if is turn of oppenent (BLACK)
	            else {
	                System.out.println("Waiting for your opponent move...\n");
	            }
	        }
	        // if BLACK
	        else if (this.getCurrentState().getTurn().equals(StateTablut.Turn.BLACK)) {
	        	// if is my turn (BLACK)
	        	if (state.getTurn().equals(State.Turn.BLACK)) {
	        		// search the best move in search tree
	                Action a = player.makeAction(state);
	                try {
	                    this.write(a);
	                } catch (ClassNotFoundException | IOException e) {
	                    e.printStackTrace();
	                }
	            }
	            else {
	                System.out.println("Waiting for your opponent move...\n");
	            }
	        } 
	
	        // if WHITEWIN 
	        else if (state.getTurn().equals(StateTablut.Turn.WHITEWIN)) {
	            System.out.println(this.getPlayer().equals(State.Turn.WHITE)?"YOU WON!":"YOU LOSE!");
	            System.exit(0);
	        }
	
	        // if BLACKWIN
	        else if (state.getTurn().equals(StateTablut.Turn.BLACKWIN)) {
	            System.out.println(this.getPlayer().equals(State.Turn.BLACK)?"YOU WON!":"YOU LOSE!");
	            System.exit(0);
	        }
	
	        // if DRAW
	        else if (state.getTurn().equals(StateTablut.Turn.DRAW)) {
	            System.out.println("DRAW!");
	            System.exit(0);
	        }
	     
		}
	}


	/**
	 * Method that find a suitable moves searching in game tree
	 * @param tablutGame Current game
	 * @param state Current state
	 * @return Action that is been evaluated as best
	 */
	private Action findBestMove(GameAshtonTablut tablutGame, State state) {
	
	    //MyIterativeDeepeningAlphaBetaSearch search = new MyIterativeDeepeningAlphaBetaSearch(tablutGame, Double.MIN_VALUE, Double.MAX_VALUE, this.timeout - 2 );
	    //search.setLogEnabled(debug);
	    //return search.makeDecision(state);
		return null;
}
}