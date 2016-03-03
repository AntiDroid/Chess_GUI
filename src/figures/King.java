package figures;
import utilities.Point;


public class King extends Figure {

	private boolean Rochade;
	
	/**
	 * Rochade ist am Anfang true. Dieser Werte wird in der move-Methode veraendert.
	 * @param point Startposition der Figur
	 * @param iW Teamfarbe
	 */
	public King(boolean iW, Point point){
		super(point, iW, "Figurenbilder/King_", "King");
		Rochade = true;
	}
	
	public boolean getRochade() {
		return Rochade;
	}
	
	public void setRochade(boolean r) {
		Rochade = r;
	}

	@Override
	public void move() {
		// TODO Auto-generated method stub
		
	}
}
