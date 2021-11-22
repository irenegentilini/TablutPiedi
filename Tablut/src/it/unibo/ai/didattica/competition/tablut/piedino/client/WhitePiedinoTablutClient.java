package it.unibo.ai.didattica.competition.tablut.piedino.client;

import java.io.IOException;
import java.net.UnknownHostException;

public class WhitePiedinoTablutClient {

	public static void main(String[] ignore) throws UnknownHostException, IOException {
		PiedinoTablutClient client=new PiedinoTablutClient("white","piedino",20,"localhost",0,true);
		Thread t=new Thread(client);
		t.start();
	}
}