import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.Stack;

public class Graphe {
	
	private LinkedList<Noeud> noeuds = new LinkedList<Noeud>();
	private HashMap<Integer, Noeud> noeuds_hm = new HashMap<Integer, Noeud>();
	
	public Graphe() {}
	
	//Création d'un graphe à partir d'une matrice
	public Graphe( int [][] mat )
	{
		for(int i = 0; i < mat.length; i++)
		{
			for(int j = 0; j < mat[i].length; j++)
			{
				if( mat[i][j] == 1 )
					this.addArc(i, j);
			}
		}	
	}
	
	//Création d'un graphe avec ses noeuds sans arcs
	public Graphe(int k)
	{
		for(int i = 0; i < k; i++ )
		{
			this.noeuds.add(new Noeud(i));
			this.noeuds_hm.put(i, new Noeud(i));
		}
	}
	
	//Création d'un graphe à partir d'un fichier CSV
	public Graphe(String file)
	{
		String line = "";
	    String splitBy = ";";
	    try {
	      //parsing a CSV file into BufferedReader class constructor  
	      BufferedReader br = new BufferedReader(new FileReader(file));
	      while ((line = br.readLine()) != null)
	      //returns a Boolean value  
	      {
	        String[] ids = line.split(splitBy);
	        addNoeud(Integer.parseInt(ids[0]));
	        addNoeud(Integer.parseInt(ids[1]));
	        addArc(Integer.parseInt(ids[0]), Integer.parseInt(ids[1]));
	      }
	    }
	    catch(IOException e) {
	      e.printStackTrace();
	    }
	}
	
	//Ajout d'un noeud
	public void addNoeud(int n) {
		boolean appartient = false;
	
		if( noeuds_hm.get(n) == null )
			this.noeuds_hm.put(n, new Noeud(n));
		
	}
	
	//Ajout d'un  arc
	public void addArc(int x, int y)
	{
		//Si les deux id sont distincts, on crée l'arc
		if( x != y ) {
			
			addNoeud(x);
			addNoeud(y);
			Noeud source = getNoeud(x);
			Noeud cible = getNoeud(y);
			
			boolean exist = source != null && cible != null;
			
			if(exist)
			{
				if( !source.hasSuccesseur(y) )
				{
					LinkedList<Arc> succ = source.getSucc();
					succ.add(new Arc(source, cible));
				}
				if( !cible.hasSuccesseur(x) )
				{
					LinkedList<Arc> succ = cible.getSucc();
					succ.add(new Arc(cible, source));
				}
			}	
			
		}
	}
	
	//Vérifie si l'arc (i, j) existe
	public boolean ArcExist(int i, int j)
	{
		Set<Integer> keys = noeuds_hm.keySet();
		for(Integer key : keys)
		{
			Noeud n = noeuds_hm.get(key);
			LinkedList<Arc> arcs = n.getSucc();
			
			for(Arc arc : arcs)
			{
				if( arc.getSource().getId() == i && arc.getCible().getId() == j )
					return true;
				if( arc.getSource().getId() == j && arc.getCible().getId() == i )
					return true;
			}
		}
		
		return false;
	}

	
	public void RemoveAllArc()
	{
		Set<Integer> keys = noeuds_hm.keySet();
		for(Integer key : keys)
		{
			Noeud n = noeuds_hm.get(key);
			n.removeAllSucc();
		}
	
	}
	
	public void parcours()
	{
		int profondeur = 0;
		Set<Integer> keys = noeuds_hm.keySet();
		for(Integer key : keys)
		{
			noeuds_hm.get(key).setMark(false);
		
		}
		
		for(Integer key : keys)
		{
			
			Noeud n = noeuds_hm.get(key);
			if(!n.isMark())
			{
				//profR(n, profondeur);
				//profI(n, profondeur);
				//largeur(n, profondeur);
			}
			
		}
		
	}
	
	public ArrayList<Noeud> indiceCroissant()
	{
		ArrayList<Noeud> noeuds = new ArrayList<Noeud>();
		Set<Integer> indices = noeuds_hm.keySet();
		int plusPetitIndice = (int) Double.POSITIVE_INFINITY;
				
		for(int i : indices)
		{
			if( i < plusPetitIndice )
				plusPetitIndice = i;
		}
		
		noeuds = croissantRec(noeuds_hm.get(plusPetitIndice), noeuds);
		System.out.println(noeuds);
		return noeuds;
	}
	
	private ArrayList<Noeud> croissantRec(Noeud n, ArrayList<Noeud> noeudsTmp)
	{
		if(!n.getSucc().isEmpty() && !n.isMark())
		{
			n.setMark(true);
			noeudsTmp.add(n);
			ArrayList<Noeud> noeuds = n.getSuccTriee();
			for(Noeud noeud : noeuds)
			{
				if(!noeud.isMark()) {
					System.out.println("Voisins de " + n.getId() +" : " + noeud.getId());
					noeudsTmp = croissantRec(noeud, noeudsTmp);
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
	
	public void largeur(Noeud n, int profondeur)
	{
		
		boolean increaseProf = false;
		LinkedList<Noeud> F = new LinkedList<Noeud>();
		LinkedList<Arc> arcs = null;
		
		n.setMark(true);
		F.addFirst(n);
		System.out.println(n);
		
		while( !F.isEmpty() )
		{
			n = F.getLast();
			F.removeLast();
			arcs = n.getSucc();
			
			for(Arc arc : arcs)
			{
				Noeud x = arc.getCible();
				
				if(!x.isMark())
				{
					x.setMark(true);
					F.addFirst(x);
					if(!increaseProf && !x.getSucc().isEmpty())
						increaseProf = true;
					
					for(int i = 0; i < profondeur; i++ )
						System.out.print("---");
					
					System.out.println(n + "--" + x);
				}
				
				
	
			}
		
			
			if(increaseProf) {
				profondeur++;
				increaseProf = false;
			}
			
		}
		
	}
	
	public void profR(Noeud n, int profondeur)
	{
		LinkedList<Arc> arcs = n.getSucc();
		
		n.setMark(true);
		System.out.println(n);
		
		
		for(Arc arc : arcs)
		{
			
			Noeud succ = arc.getCible();
			
			if(!succ.isMark()) {	
				for(int i = -1; i < profondeur; i++)
					System.out.print("--");
				profR(succ, profondeur + 1);
				
			}
			
		}
		
		
	}
	
	public void profI(Noeud n, int profondeur)
	{
		Stack<Noeud> t = new Stack<Noeud>();
		n.setMark(true);
		t.push(n);
		System.out.println(n);
		
		while( !t.isEmpty() )
		{
			n = t.peek();
			LinkedList<Arc> arcs = n.getSucc();
			
			boolean allMarked = true;
			for(Arc arc : arcs)
			{
				allMarked &= arc.getCible().isMark();
			}
			
			if(allMarked) {
				t.pop();
				profondeur--;
			}
			else
			{
				
				for(Arc arc : arcs)
				{
					
					
					if(!arc.getCible().isMark())
					{
						profondeur++;
						for(int i = 0; i < profondeur; i++)
							System.out.print("--");
						arc.getCible().setMark(true);
						t.push(arc.getCible());
						System.out.println(arc.getCible());
						
						break;
					}
					
				}
			}
		}
	}	
	
	
	// Export d’un graphe sous format CSV selon la liste de ses arcs
	// Format Source : Target
	public void export() {
		Set<Integer> keys = noeuds_hm.keySet();
		String buff = "Source,Target\n";
		String sep = ",";
		for (Integer key : keys) {
			Noeud n = noeuds_hm.get(key);
			for (Arc a : n.getSucc()) {
				buff += a.getSource().getId() + sep +
				a.getCible().getId() + "\n";
				//System.out.println(a.getSource().getId() + sep +
				//a.getCible().getId() + "\n");
			}
		}
		File outputFile = new File(this.getClass() + ".csv");
		FileWriter out;
		try {
			out = new FileWriter(outputFile);
			out.write(buff);
			out.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	//Affichage du graphe
	public String toString()
	{
		String s = "";
		
		
		Set<Integer> keys = noeuds_hm.keySet();
		
		for(Integer key : keys)
		{
			s += "R(" + this.getNoeud(key) + ") = ";
			LinkedList<Arc> succ = this.getNoeud(key).getSucc();
			s += "{";
			for(Arc arc : this.getNoeud(key).getSucc())
			{
				s += " (" + arc.getSource().toString() + "," + arc.getCible().toString() + ") ";
			}
			s += "}\n";
		}
		
		return s;
	}
	
	//Récupère un noeud avec un id n
	public Noeud getNoeud(int n)
	{		
		return noeuds_hm.get(n);
	}

}
