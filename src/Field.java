
import javafx.geometry.Point2D;

class Field {
	
	private int belegung;
	private Point2D koordinate;
	private Boolean isWhite;
	
	static final int emptyField = 99;
	
	public Field(Point2D koord, Boolean iW){
		belegung = emptyField;
		this.koordinate = koord;
		this.isWhite = iW;
	}
	
	public int getBelegung(){
		
		return this.belegung;
	}
	
	public Boolean getIsWhite(){
		
		return this.isWhite;
	}
	
	public Point2D getKoordinate(){
		
		return this.koordinate;
	}
	
	/**
	 * Methode, welche eine FigurenID an ein Feld bindet
	 * @param index FigurenID
	 */
	public void addFigur(int index){
		if(belegung != emptyField)
			System.out.println("Fehler("+index+"): Feld ist bereits belegt von "+belegung+"! Position wird dennoch gesetzt!");
			
		this.belegung = index;
	}
	
	/**
	 * Methode, welche die Belegung eines Feldes auf "leer" setzt.
	 */
	public void clear(){
		this.belegung = Field.emptyField;
	}
}
