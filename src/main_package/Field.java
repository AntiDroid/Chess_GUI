package main_package;

import javax.swing.JPanel;

import javafx.geometry.Point2D;

class Field extends JPanel {

	private static final long serialVersionUID = 1L;
	
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
		this.belegung = index;
	}
	
	/**
	 * Methode, welche die Belegung eines Feldes auf "leer" setzt.
	 */
	public void clear(){
		removeAll();
		belegung = Field.emptyField;
	}
}
