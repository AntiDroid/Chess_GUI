package main_package;


public class Player {

	private String name;
	private Boolean isWhite;
	
	public Player(String name, Boolean isW){
		this.name = name;
		this.isWhite = isW;
	}
	
	public String getName(){
		return name;
	}
	public Boolean getIsWhite(){
		return isWhite;
	}
}
