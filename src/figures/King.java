package figures;
import utilities.Point;
import javafx.geometry.Point2D;


public class King extends Figure {

	private Boolean Rochade;
	
	/**
	 * Rochade ist am Anfang true. Dieser Werte wird in der move-Methode veraendert.
	 * @param point Startposition der Figur
	 * @param iW Teamfarbe
	 */
	public King(Boolean iW, Point point){
		super(point, iW, "Figurenbilder/King_", "King");
		Rochade = true;
	}
	
	public Boolean getRochade() {
		return Rochade;
	}
	
	public void setRochade(Boolean r) {
		Rochade = r;
	}
}
