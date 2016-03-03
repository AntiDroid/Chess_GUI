package figures;
import utilities.Point;


public class Rook extends Figure {
	
	private boolean Rochade;
	
	/**
	 * Rochade ist am Anfang true. Dieser Wert wird dann in der 
	 * move-Methode geaendert.
	 * @param point Startposition der Figur
	 * @param iW Teamfarbe
	 */
	public Rook(boolean iW, Point point){
		super(point, iW, "Figurenbilder/Rook_", "Rook");
		setRochade(true);
	}

	public boolean getRochade() {
		return Rochade;
	}

	public void setRochade(boolean r) {
		Rochade = r;
	}
}
