package figures;
import utilities.Point;


public class Pawn extends Figure {

	private boolean doublemove;
	private boolean enPassantAble;
	
	/**
	 * enPassant ist am Anfang false und der Doppelzug ist true. Diese Werte werden dann in der 
	 * move-Methode geaendert.
	 * @param point Startposition der Figur
	 * @param iW Teamfarbe
	 */
	public Pawn(boolean iW, Point point){
		super(point, iW, "Figurenbilder/Pawn_", "Pawn");
		enPassantAble = false;
		doublemove = true;
	}
	
	public void setEnPassant(boolean b){
		enPassantAble = b;
	}
	
	public boolean getEnPassant(){
		return enPassantAble;
	}

	public boolean getDoublemove() {
		return doublemove;
	}

	public void setDoublemove(boolean doublemove) {
		this.doublemove = doublemove;
	}
}
