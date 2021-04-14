package classes;
import java.util.ArrayList;
import java.util.LinkedList;

public class Node {
	
	private int id;
	private LinkedList<Arc> succ;
	private boolean startColor;
	private boolean mark;
	private int couleur;
	
	public Node(int id)
	{
		this.id = id;
		succ = new LinkedList<Arc>();
	}
	
	public Node(int id, int couleur)
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
			if( arc.getTarget().getId() == j )
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
	
	public int getColor() {
		return couleur;
	}

	public void setColor(int couleur) {
		this.couleur = couleur;
	}

	public boolean isStartColor() {
		return startColor;
	}

	public void setStartColor(boolean startColor) {
		this.startColor = startColor;
	}
	/**
	 * m�thode qui retourne les succ�sseur du noeud dans l'ordre des id croissant
	 * @return
	 */
	public ArrayList<Node> getSuccSortedByIndex()
	{
		ArrayList<Node> noeudsMarque = new ArrayList<Node>();
		
		//On recupere le plus petit noeud
		int plusPetitIndice = (int) Double.POSITIVE_INFINITY;
		
		Node noeud = null;
		//tant que la liste r�sultat n'a pas la taille de la liste des succ�sseurs
		while(noeudsMarque.size() != succ.size()) {
		
			for(Arc arc : succ)
			{
				Node voisin = arc.getTarget();
				//on prend le noeud avec le plus petit id qui n'est pas d�j� dans le r�sultat
				if(voisin.getId() < plusPetitIndice && !noeudsMarque.contains(voisin)) {
					plusPetitIndice = voisin.getId();
					noeud = arc.getTarget();
				}
			}
			//on ajoute le plus petit id
			if(plusPetitIndice != (int) Double.POSITIVE_INFINITY ) {
				noeudsMarque.add(noeud);
				plusPetitIndice = (int) Double.POSITIVE_INFINITY;
			}
			
		}
		return noeudsMarque;
	}
	
	//ancienne version
	/*public ArrayList<Node> getSuccSortedByDegree()
	{
		
		ArrayList<Node> sortedNodes = new ArrayList<Node>();
		
		while(sortedNodes.size() != succ.size()) {
			
			for(Arc arc : succ)
			{
				Node neighbour = arc.getTarget();
				boolean isGreater = true;
				
				if(!sortedNodes.contains(neighbour))
				{
					for(Arc tmpArc : succ)
					{
						Node tmpNeighbour = tmpArc.getTarget();
						if(!tmpNeighbour.equals(neighbour) && !sortedNodes.contains(tmpNeighbour))
							if(tmpNeighbour.getSucc().size() > neighbour.getSucc().size())
								isGreater = false;
	
					}
					
					if(isGreater) {
						sortedNodes.add(neighbour);
					}
				}	
			}
		}
		return sortedNodes;
	}*/
	
	/**
	 * m�thode qui retourne la liste des suc�sseurs dans l'ordre des degr�es d�croissant
	 * @return
	 */
	
	public ArrayList<Node> getSuccSortedByDegree2()
	{
		
		ArrayList<Node> sortedNodes = new ArrayList<Node>();
		int Best=-1;
		Node noeud=null;
		//tant que la liste r�sultat n'a pas la taille de la liste des succ�sseurs
		while(sortedNodes.size() != succ.size()) {
			
			for(Arc arc : succ)
			{
				Node neighbour = arc.getTarget();
				//on prend le noeud avec le plus grand degr� qui n'est pas d�j� dans le r�sultat
				if(!sortedNodes.contains(neighbour) && neighbour.getSucc().size()>Best)
				{
					Best=neighbour.getSucc().size();
					noeud=neighbour;
				}	
			}
			//on ajoute le plus grand degr�
			if(Best!=-1) {
				sortedNodes.add(noeud);
				Best=-1;
			}
		}

		return sortedNodes;
	}

}
