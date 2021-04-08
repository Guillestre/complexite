package classes;

public class Arc {

	private Node source;
	private Node cible;
	private double poids;
	
	public Arc(Node x, Node y)
	{
		this.source = x;
		this.cible = y;
	}
	
	public Arc(Node x, Node y, double poids)
	{
		this.source = x;
		this.cible = y;
		this.poids = poids;
	}
	
	public String toString() {
		
		String s = "";
		s += " x : " + this.source.getId() + "\n";
		s += " y : " + this.cible.getId() + "\n";
		return s;
		
	}
	
	public double getPoids() {
		return poids;
	}

	public void setPoids(double poids) {
		this.poids = poids;
	}

	public void setSource(Node source) {
		this.source = source;
	}

	public void setCible(Node cible) {
		this.cible = cible;
	}


	public Node getSource() {
		return source;
	}

	public Node getCible() {
		return cible;
	}
	
}
