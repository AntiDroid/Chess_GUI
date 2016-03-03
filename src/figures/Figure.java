package figures;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;

import utilities.Point;

public abstract class Figure {
	
	private Point startPosition;
	private JLabel image;
	private boolean isWhite;
	private String name;
	
	/**
	 * Die Figurenbilder werden hier unteranderem initialisiert.
	 * @param sP Startposition der Figur
	 * @param isW Teamfarbe
	 * @param fP filePath des Figurenbildes
	 * @param n Name der Figur
	 */
	public Figure(Point sP, boolean isW, String fP, String n){
		
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
	
	public void setSP(Point point){
		this.startPosition = point;
	}
	
	public Point getSP(){
		
		return startPosition;
	}
	
	public boolean getIW(){
		return isWhite;
	}
	
	public JLabel getImage(){
		return this.image;
	}
}
