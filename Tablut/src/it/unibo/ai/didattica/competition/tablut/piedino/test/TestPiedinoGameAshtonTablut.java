package it.unibo.ai.didattica.competition.tablut.piedino.test;

import it.unibo.ai.didattica.competition.tablut.domain.State;
import it.unibo.ai.didattica.competition.tablut.domain.StateTablut;
import it.unibo.ai.didattica.competition.tablut.piedino.search.PiedinoGameAshtonTablut;
import it.unibo.ai.didattica.competition.tablut.piedino.search.heuristics.BlackHeuristics;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

public class TestPiedinoGameAshtonTablut {
	
	State state1;
	@Before
	public void init() {
		state1=new StateTablut();
	}
	
	@Test
    public void test1() {
        
		PiedinoGameAshtonTablut game=new PiedinoGameAshtonTablut(0, -1, "logs", "white_ai", "black_ai");
		//Integer[][] testWhites={{2,4},{3,4},{5,4},{6,4},{4,2},{4,3},{4,5},{4,6}};
		Integer[][] testWhites={{3,4},{4,3},{4,5},{5,4},{2,4},{4,2},{4,6},{6,4}};

		Integer[][] testBlacks={{0,3},{0,4},{0,5},{1,4},{8,3},{8,4},{8,5},{7,4},
						{3,0},{4,0},{5,0},{4,1},{3,8},{4,8},{5,8},{4,7}};
		
		List<Integer[]> whites= game.getPositionsOf(state1, State.Pawn.WHITE);
		List<Integer[]> blacks= game.getPositionsOf(state1, State.Pawn.BLACK);
		List<Integer[]> king= game.getPositionsOf(state1, State.Pawn.KING);
	
		assertTrue(king.size()==1);
		assertEquals(king.get(0)[0].intValue(), 4);
		assertEquals(king.get(0)[1].intValue(), 4);
		//for (int i=0;i<whites.size();i++) System.out.println(whites.get(i)[0]+""+whites.get(i)[1]);
		assertTrue(whites.get(0)[0].equals(testWhites[0][0])&&whites.get(0)[1].equals(testWhites[0][1]));
		assertTrue(whites.get(1)[0].equals(testWhites[1][0])&&whites.get(1)[1].equals(testWhites[1][1]));
		assertTrue(whites.get(2)[0].equals(testWhites[2][0])&&whites.get(2)[1].equals(testWhites[2][1]));
		assertTrue(whites.get(3)[0].equals(testWhites[3][0])&&whites.get(3)[1].equals(testWhites[3][1]));
		assertTrue(whites.get(4)[0].equals(testWhites[4][0])&&whites.get(4)[1].equals(testWhites[4][1]));
		assertTrue(whites.get(5)[0].equals(testWhites[5][0])&&whites.get(5)[1].equals(testWhites[5][1]));
		assertTrue(whites.get(6)[0].equals(testWhites[6][0])&&whites.get(6)[1].equals(testWhites[6][1]));
		assertTrue(whites.get(7)[0].equals(testWhites[7][0])&&whites.get(7)[1].equals(testWhites[7][1]));
		
		
		for (int i=0;i<testBlacks.length;i++) {
			Integer[] w1=blacks.get(i);
			Integer[] w2=testBlacks[i];
			assertTrue(w1[0].equals(w2[0])&&w1[1].equals(w2[1]));
		}
		
	}

	@Test
    public void test2() {  	
		State state2=state1.clone();
		BlackHeuristics heu=new BlackHeuristics();
		assertFalse(heu.hasKingSafeEscape(state1));
		State.Pawn [][] board=state2.getBoard();
		board[4][4]=State.Pawn.THRONE;
		board[2][4]=State.Pawn.KING;
		System.out.println(state2.toString());
		assertFalse(heu.canBlackAttackPos(state2, 3,4));
		assertTrue(heu.canBlackAttackPos(state2, 2, 3));
		assertTrue(heu.hasKingSafeEscape(state2));	
		board[4][1]=State.Pawn.EMPTY;
		board[2][1]=State.Pawn.BLACK;
		assertFalse(heu.hasKingSafeEscape(state2));	
	}
	@Test
    public void test3() {  	
		State state3=state1.clone();
		BlackHeuristics heu=new BlackHeuristics();
		assertEquals(0.3611,heu.calcDistanceSupport(state3), 0.01);
	}
}
