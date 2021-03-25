
public class Point extends Noeud{

	private double x;
	private double y;
	
	public Point(int id) {
		super(id);
		// TODO Auto-generated constructor stub
	}
	
	public Point(double x, double y, int id)
	{
		super(id);
		this.x = x;
		this.y = y;
	}

	public double getX() {
		return x;
	}

	public void setX(double x) {
		this.x = x;
	}

	public double getY() {
		return y;
	}

	public void setY(double y) {
		this.y = y;
	}
	
	
	
	
}
