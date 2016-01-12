package figures;
import javafx.geometry.Point2D;


public class King extends Figure {

	private Boolean Rochade;
	
	/**
	 * Rochade ist am Anfang true. Dieser Werte wird in der move-Methode veraendert.
	 */
	public King(Boolean iW, Point2D sP){
		super(sP, iW, "Figurenbilder/King_", "King");
		Rochade = true;
	}
	
	public Boolean getRochade() {
		return Rochade;
	}
	
	public void setRochade(Boolean r) {
		Rochade = r;
	}
}
