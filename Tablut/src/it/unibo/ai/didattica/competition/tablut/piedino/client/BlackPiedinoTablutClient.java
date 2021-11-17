package it.unibo.ai.didattica.competition.tablut.piedino.client;

import java.io.IOException;
import java.net.UnknownHostException;

public class BlackPiedinoTablutClient {

	public static void main(String[] ignore) throws UnknownHostException, IOException {
		PiedinoTablutClient client=new PiedinoTablutClient("black","piedino",5,"localhost",0,true);
		Thread t=new Thread(client);
		t.start();
	}
}

