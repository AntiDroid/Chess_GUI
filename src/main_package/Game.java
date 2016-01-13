package main_package;
import java.util.ArrayList;
import java.util.List;


public class Game {

	public Schachbrett brett;
	private int winner;
	private Player[] p;
	private int totalGameDur;
	
	private static List<Game> gameHistory = new ArrayList<Game>();
	
	public Game(Player p1, Player p2){
		
		p = new Player[2];
		p[0] = p1;
		p[1] = p2;
		winner = 10;
		totalGameDur = 0;
		this.brett = new Schachbrett();
	}
	
	public Player[] getPlayer(){
		return p;
	}
	
	/**
	 * Eine Ausgabe des Gewinners und das archivieren des Spiels wird durchgefuehrt
	 * @param isWhite welches Team gewonnen hat
	 */
	public void Win(Boolean isWhite){
		if(p[1].getIsWhite() == isWhite)
			winner = 0;
		else
			winner = 1;
		
		System.out.println("\n\nPlayer "+p[winner].getName()+" has won!");
		System.out.println("The enemy king can't move!");
		totalGameDur = p[1].getTimeSec()+p[2].getTimeSec();
		gameHistory.add(this);
	}
	
	/**
	 * Unentschieden durch Patt oder 1v1 King Situationen automatisch aufgerufen.
	 */
	public void Draw() {

		System.out.println("DRAW");
		totalGameDur = p[1].getTimeSec()+p[0].getTimeSec();
		gameHistory.add(this);
	}
	
	/**
	 * Ausgabemethode eines einzelnen Spieles
	 */
	public String toString(){
		String str = p[0].getName()+" vs " + p[1].getName()+"\n*************\n";
		if(winner != 10)
			str += p[winner].getName()+" won\n*************\n";
		str += "The game took"+Math.round((double)totalGameDur/60.0)+"seconds!\n\n";
		
		return str;
	}
}
