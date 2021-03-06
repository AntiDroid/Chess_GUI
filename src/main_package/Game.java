package main_package;
import javax.swing.JOptionPane;


public class Game {

	public Schachbrett brett;
	private int winner;
	private Player[] p;
	private boolean whiteTurn;
	
	public Game(Player p1, Player p2){
		
		p = new Player[2];
		p[0] = p1;
		p[1] = p2;
		winner = 10;
		this.brett = new Schachbrett();
		whiteTurn = true;
	}
	
	public Player[] getPlayer(){
		return p;
	}
	
	/**
	 * Eine Ausgabe des Gewinners und das archivieren des Spiels wird durchgefuehrt
	 * @param isWhite welches Team gewonnen hat
	 */
	public void win(boolean isWhite){
		if(p[0].getIsWhite() == isWhite)
			winner = 0;
		else
			winner = 1;
	
		JOptionPane.showMessageDialog(null, p[winner].getName()+" has won!");
	}
	
	/**
	 * Unentschieden durch Patt oder 1v1 King Situationen automatisch aufgerufen.
	 */
	public void remis() {
		JOptionPane.showMessageDialog(null, "Remis!");
	}
	
	public boolean getWhiteTurn() {
		return whiteTurn;
	}

	public void setWhiteTurn(boolean whiteTurn) {
		this.whiteTurn = whiteTurn;
	}
}
