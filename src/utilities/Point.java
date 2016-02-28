package utilities;

public class Point{
	
	private int x, y;
	
	public Point(int x, int y){
		this.x = x;
		this.y = y;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public void set(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	@Override
	public boolean equals(Object obj){
		
		Point p = (Point)obj;
		
		return (x == p.x) && (y == p.y);
	}
}