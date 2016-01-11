

class Player {

	private int timeSec;
	private String name;
	private Boolean isWhite;
	
	public Player(String name, Boolean isW){
		this.name = name;
		this.isWhite = isW;
		timeSec = 0;
	}
	
	public String getName(){
		return name;
	}
	public Boolean getIsWhite(){
		return isWhite;
	}
	
	public void increaseTimeSec() throws InterruptedException{
		//nicht in Verwendung
		timeSec++;
		Thread.sleep(timeSec);
	}
}
