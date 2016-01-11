package figures;
import java.io.IOException;

import javafx.geometry.Point2D;


public class Rook extends Figure {
	
	private Boolean Rochade;
	
	/**
	 * Rochade ist am Anfang true. Dieser Wert wird dann in der 
	 * move-Methode geaendert.
	 */
	public Rook(Boolean iW, Point2D sP) throws IOException{
		super(sP, iW, "Figurenbilder/Rook_", "Rook");
		setRochade(true);
	}

	public Boolean getRochade() {
		return Rochade;
	}

	public void setRochade(Boolean r) {
		Rochade = r;
	}
}
