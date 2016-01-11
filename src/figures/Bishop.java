package figures;
import java.io.IOException;

import javafx.geometry.Point2D;


public class Bishop extends Figure {
	
	public Bishop(Boolean iW, Point2D sP) throws IOException{
		super(sP, iW, "Figurenbilder/Bishop_", "Bishop");
	}
}
