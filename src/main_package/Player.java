package main_package;


public class Player {

	private String name;
	private boolean isWhite;
	
	public Player(String name, boolean isW){
		this.name = name;
		this.isWhite = isW;
	}
	
	public String getName(){
		return name;
	}
	public boolean getIsWhite(){
		return isWhite;
	}
}
