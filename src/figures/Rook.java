package figures;
import utilities.Point;
import javafx.geometry.Point2D;


public class Rook extends Figure {
	
	private Boolean Rochade;
	
	/**
	 * Rochade ist am Anfang true. Dieser Wert wird dann in der 
	 * move-Methode geaendert.
	 * @param point Startposition der Figur
	 * @param iW Teamfarbe
	 */
	public Rook(Boolean iW, Point point){
		super(point, iW, "Figurenbilder/Rook_", "Rook");
		setRochade(true);
	}

	public Boolean getRochade() {
		return Rochade;
	}

	public void setRochade(Boolean r) {
		Rochade = r;
	}
}
