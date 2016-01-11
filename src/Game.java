import java.io.IOException;
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
	private Player winner;
	private Player p1, p2;
	
	private static List<Game> gameHistory = new ArrayList<Game>();
	
	public Game(Player p1, Player p2) throws IOException{
		
		this.p1 = p1;
		this.p2 = p2;
		winner = null;
		this.brett = new Schachbrett();
	}
	
	/**
	 * Eine Ausgabe des Gewinners und das archivieren des Spiels wird durchgefuehrt
	 * @param isWhite welches Team gewonnen hat
	 */
	public void Win(Boolean isWhite){
		if(p1.getIsWhite() == isWhite)
			winner = p1;
		else
			winner = p2;
		
		consolePrint();
		
		System.out.println();
		System.out.println();
		System.out.println("Player "+winner.getName()+" has won!");
		System.out.println("The enemy king can't move!");
		
		gameHistory.add(this);
	}
	
	/**
	 * Unentschieden, welches (aktuell) nur in dem Fall eines Patts anerkannt wird.
	 * @param isWhite Welche Figur einen Draw ausloest
	 */
	public void Draw(Boolean isWhite) {
		
		Player draw;
		
		if(p1.getIsWhite() == isWhite)
			draw = p1;
		else
			draw = p2;
		
		consolePrint();
		
		System.out.println();
		System.out.println();
		System.out.println("Player "+draw.getName()+"'s king can't move!");
		System.out.println("DRAW");
		
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
		
		System.out.println("\n--black--  Name: "+p2.getName());
		if(brett.getBlackChecK())
			System.out.print("\n    ****CHECK****    \n");
		
		System.out.println("\n        A  B  C  D  E  F  G  H\n");
		for(int y = 0; y < 8; y++){
			System.out.print("   "+(8-y)+"   ");
			for(int x = 0; x < 8; x++){	
				System.out.print("[");
				int index = brett.getFelder()[x][y].getBelegung();
				
				if(index < 32){
					if(brett.Fig[index] instanceof Pawn)
						if(index < 16)
							System.out.print("P");
						else
							System.out.print("p");
					else if(brett.Fig[index] instanceof King)
						if(index < 16)
							System.out.print("K");
						else
							System.out.print("k");
					else if(brett.Fig[index] instanceof Queen)
						if(index < 16)
							System.out.print("Q");
						else
							System.out.print("q");
					else if(brett.Fig[index] instanceof Bishop)
						if(index < 16)
							System.out.print("B");
						else
							System.out.print("b");
					else if(brett.Fig[index] instanceof Rook)
						if(index < 16)
							System.out.print("R");
						else
							System.out.print("r");
					
					else if(brett.Fig[index] instanceof Knight)
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
		
		System.out.println("\n--WHITE--  Name: "+p1.getName());
		if(brett.getWhiteChecK())
			System.out.print("\n    ****CHECK****    \n");
		System.out.println();
	}

	
}
