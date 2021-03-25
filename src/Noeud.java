import java.util.ArrayList;
import java.util.LinkedList;

public class Noeud {
	
	private int id;
	private LinkedList<Arc> succ;
	private boolean mark;
	private int couleur;
	
	public Noeud(int id)
	{
		this.id = id;
		succ = new LinkedList<Arc>();
	}
	
	public Noeud(int id, int couleur)
	{
		this.id = id;
		this.couleur = couleur;
		succ = new LinkedList<Arc>();
	}
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String toString()
	{
		String s = id + "";
				
		return s;
	}
	
	public LinkedList<Arc> getSucc()
	{
		return succ;
	}
	
	
	public boolean hasSuccesseur(int j)
	{
		for(Arc arc : succ)
		{
			if( arc.getCible().getId() == j )
				return true;
		}
		
		return false;
	}

	public boolean isMark() {
		return mark;
	}

	public void setMark(boolean mark) {
		this.mark = mark;
	}
	
	public void removeAllSucc()
	{
		succ = new LinkedList<Arc>();
	}
	
	public int getCouleur() {
		return couleur;
	}

	public void setCouleur(int couleur) {
		this.couleur = couleur;
	}

	public ArrayList<Noeud> getSuccTriee()
	{
		ArrayList<Noeud> noeudsMarque = new ArrayList<Noeud>();
		
		//On recupere le plus petit noeud
		int plusPetitIndice = (int) Double.POSITIVE_INFINITY;
		
		Noeud noeud = null;
		
		while(noeudsMarque.size() != succ.size()) {
		
			for(Arc arc : succ)
			{
				Noeud voisin = arc.getCible();
				if(voisin.getId() < plusPetitIndice && !noeudsMarque.contains(voisin)) {
					plusPetitIndice = voisin.getId();
					noeud = arc.getCible();
				}
			}
			
			if(plusPetitIndice != (int) Double.POSITIVE_INFINITY ) {
				noeudsMarque.add(noeud);
				plusPetitIndice = (int) Double.POSITIVE_INFINITY;
			}
			
		}
		
		return noeudsMarque;
	}

}
