package figures;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;

import javafx.geometry.Point2D;


public abstract class Figure {
	
	private Point2D startPosition;
	protected JLabel image;
	private Boolean isWhite;
	private String name;
	
	/**
	 * Die Figurenbilder werden hier unteranderem initialisiert.
	 * @param sP Startposition der Figur
	 * @param isW Teamfarbe
	 * @param fP filePath des Figurenbildes
	 * @param n Name der Figur
	 */
	public Figure(Point2D sP, Boolean isW, String fP, String n){
		
		name = n;
		isWhite = isW;
		startPosition = sP;
		
		if(isW){
			name += "(WHITE)";
			fP += "w";
		}
		else{
			name += "(BLACK)";
			fP += "s";
		}
		fP += ".png";
		
		try{
			image = new JLabel(new ImageIcon(ImageIO.read(new File(fP))));
		}
		catch(IOException e){
			System.out.println(fP+" couldn't be loaded");
		}
	}
	
	public String getName(){
		return name;
	}
	
	public void setSP(Point2D point){
		this.startPosition = point;
	}
	
	public Point2D getSP(){
		
		return startPosition;
	}
	
	public Boolean getIW(){
		return isWhite;
	}
	
	public JLabel getImage(){
		return this.image;
	}
}
