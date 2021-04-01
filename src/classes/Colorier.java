package classes;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Set;

public class Colorier {

	public ArrayList<Noeud> indiceCroissant(HashMap<Integer, Noeud> noeuds_hm)
	{
		ArrayList<Noeud> noeuds = new ArrayList<Noeud>();
		Set<Integer> indices = noeuds_hm.keySet();
		int plusPetitIndice = (int) Double.POSITIVE_INFINITY;
				
		for(int i : indices)
		{
			if( i < plusPetitIndice )
				plusPetitIndice = i;
		}
		
		noeuds = indiceCroissantRec(noeuds_hm.get(plusPetitIndice), noeuds);
		System.out.println(noeuds);
		return noeuds;
	}
	
	
	private ArrayList<Noeud> indiceCroissantRec(Noeud n, ArrayList<Noeud> noeudsTmp)
	{
		if(!n.getSucc().isEmpty() && !n.isMark())
		{
			n.setMark(true);
			noeudsTmp.add(n);
			ArrayList<Noeud> noeuds = n.getSuccSortedByIndex();
			for(Noeud noeud : noeuds)
			{
				if(!noeud.isMark()) {
					System.out.println("Voisins de " + n.getId() +" : " + noeud.getId());
					noeudsTmp = indiceCroissantRec(noeud, noeudsTmp);
				}
			}
		}
		return noeudsTmp;
	}
	
	public ArrayList<Noeud> decreasingDegree(HashMap<Integer, Noeud> noeuds_hm)
	{
		System.out.println("Decreasing degree : ");
		ArrayList<Noeud> nodes = new ArrayList<Noeud>();
		Noeud firstNode = null;
		Set<Integer> indexes = noeuds_hm.keySet();
		int greaterDegree = (int) Double.NEGATIVE_INFINITY;
		
		for(Integer index : indexes)
		{
			Noeud currentNode = noeuds_hm.get(index);
			
			//Getting successor
			LinkedList<Arc> succ =  currentNode.getSucc();
		
			//Getting greater node neighbour
			if(succ.size() > greaterDegree) {
				greaterDegree = succ.size();
				firstNode = currentNode;
			}
			
		}
		
		nodes = decreasingDegreeRec(noeuds_hm.get(greaterDegree), nodes);
		System.out.println("Path selected : " + nodes);
		return nodes;
	}
	
	private ArrayList<Noeud> decreasingDegreeRec(Noeud n, ArrayList<Noeud> noeudsTmp)
	{
		if(!n.getSucc().isEmpty() && !n.isMark())
		{
			n.setMark(true);
			noeudsTmp.add(n);
			ArrayList<Noeud> noeuds = n.getSuccSortedByDegree();
			for(Noeud noeud : noeuds)
			{
				if(!noeud.isMark()) {
					System.out.println("Neighbours of " + n.getId() +" : " + noeud.getId());
					noeudsTmp = decreasingDegreeRec(noeud, noeudsTmp);
				}
			}
		}
		return noeudsTmp;
	}
	
	public void sequentielle(ArrayList<Noeud> noeuds)
	{
		
		int omega = 0;
		
		for(Noeud noeud : noeuds)
		{
			int alpha = 1;
			for(Arc arc : noeud.getSucc())
			{
				Noeud cible = arc.getCible();
				
				if(cible.getCouleur() == alpha)
				{
					alpha++;
				}
				
			}
			
			if(alpha > omega)
				omega = alpha;
			
			noeud.setCouleur(alpha);
			
		}
		
		System.out.println(omega);
		
	}
	
}
