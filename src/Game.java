import java.util.ArrayList;
import java.util.List;

import figures.Bishop;
import figures.King;
import figures.Knight;
import figures.Pawn;
import figures.Queen;
import figures.Rook;


class Game {

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
		
		consolePrint();
		
		System.out.println("\n\nPlayer "+p[winner].getName()+" has won!");
		System.out.println("The enemy king can't move!");
		totalGameDur = p[1].getTimeSec()+p[2].getTimeSec();
		gameHistory.add(this);
	}
	
	/**
	 * Unentschieden durch Patt oder 1v1 King Situationen automatisch aufgerufen.
	 */
	public void Draw() {
		
		consolePrint();

		System.out.println("DRAW");
		totalGameDur = p[1].getTimeSec()+p[2].getTimeSec();
		gameHistory.add(this);
	}
	
	/**
	 * Ausgabe des Feldes.
	 */
	public void consolePrint(){
		
		for(int i = 0; i < 10; i++){
			System.out.println();
		}
		
		System.out.print("\nQ - Queen(Dame)");
		System.out.print("\t\tK - King(Koenig)\n");
		System.out.print("P - Pawn(Bauer)");
		System.out.print("\t\tB - Bishop(Laeufer)\n");
		System.out.print("R - Rook(Turm)");
		System.out.print("\t\tJ - Knight(Springer)\n");
		
		System.out.println("\n--black--  Name: "+p[1].getName());
		if(brett.getBlackChecK())
			System.out.print("\n    ****CHECK****    \n");
		
		System.out.println("\n        A  B  C  D  E  F  G  H\n");
		for(int y = 0; y < 8; y++){
			System.out.print("   "+(8-y)+"   ");
			for(int x = 0; x < 8; x++){	
				System.out.print("[");
				int index = brett.getFelder()[x][y].getBelegung();
				
				if(index < 32){
					if(brett.getFigures()[index] instanceof Pawn)
						if(index < 16)
							System.out.print("P");
						else
							System.out.print("p");
					else if(brett.getFigures()[index] instanceof King)
						if(index < 16)
							System.out.print("K");
						else
							System.out.print("k");
					else if(brett.getFigures()[index] instanceof Queen)
						if(index < 16)
							System.out.print("Q");
						else
							System.out.print("q");
					else if(brett.getFigures()[index] instanceof Bishop)
						if(index < 16)
							System.out.print("B");
						else
							System.out.print("b");
					else if(brett.getFigures()[index] instanceof Rook)
						if(index < 16)
							System.out.print("R");
						else
							System.out.print("r");
					
					else if(brett.getFigures()[index] instanceof Knight)
						if(index < 16)
							System.out.print("J");
						else
							System.out.print("j");
				}
				else if(brett.getFelder()[x][y].getIsWhite())
					System.out.print(" ");
				else
					System.out.print("■");
				
				System.out.print("]");
			}
			System.out.println();
			
		}
		
		System.out.println("\n--WHITE--  Name: "+p[1].getName());
		if(brett.getWhiteChecK())
			System.out.print("\n    ****CHECK****    \n");
		System.out.println();
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
