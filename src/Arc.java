
public class Arc {

	private Noeud source;
	private Noeud cible;
	private double poids;
	
	public Arc(Noeud x, Noeud y)
	{
		this.source = x;
		this.cible = y;
	}
	
	public Arc(Noeud x, Noeud y, double poids)
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

	public void setSource(Noeud source) {
		this.source = source;
	}

	public void setCible(Noeud cible) {
		this.cible = cible;
	}


	public Noeud getSource() {
		return source;
	}

	public Noeud getCible() {
		return cible;
	}
	
}
