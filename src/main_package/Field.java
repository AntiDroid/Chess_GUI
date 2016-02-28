package main_package;

import javax.swing.JPanel;

import utilities.Point;

class Field extends JPanel {

	private static final long serialVersionUID = 1L;
	
	private int belegung;
	private Point koordinate;
	private Boolean isWhite;
	
	static final int emptyField = 99;
	
	public Field(Point koord, Boolean iW){
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
	
	public Point getKoordinate(){
		
		return this.koordinate;
	}
	
	/**
	 * Methode, welche eine FigurenID an ein Feld bindet
	 * @param index FigurenID
	 */
	public void addFigur(int index){
		this.belegung = index;
	}
	
	/**
	 * Methode, welche die Belegung eines Feldes auf "leer" setzt.
	 */
	public void clear(){
		belegung = Field.emptyField;
	}
}
