package classes;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Set;
import java.util.Stack;

public class Graphe {
	
	private LinkedList<Noeud> noeuds = new LinkedList<Noeud>();
	private HashMap<Integer, Noeud> noeuds_hm = new HashMap<Integer, Noeud>();
	
	public Graphe() {}
	
	/**
	 * Cr�ation d'un graphe � partir d'une matrice
	 * @param mat
	 */
	
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
	
	/**
	 * Cr�ation d'un graphe avec des noeuds sans arcs
	 * @param k
	 */
	
	public Graphe(int k)
	{
		for(int i = 0; i < k; i++ )
		{
			this.noeuds.add(new Noeud(i));
			this.noeuds_hm.put(i, new Noeud(i));
		}
	}
	
	/**
	 * Cr�ation d'un graphe � partir d'un fichier CSV
	 * @param file
	 */
	
	public Graphe(String file)
	{
		String line = "";
	    String splitBy = ",";
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
	      br.close();
	    }
	    catch(IOException e) {
	      e.printStackTrace();
	    }
	}
	
	/**
	 * Ajout d'un noeud
	 * @param n
	 */
	
	public void addNoeud(int n) {
	
		if( noeuds_hm.get(n) == null )
			this.noeuds_hm.put(n, new Noeud(n));
		
	}
	
	/**
	 * Ajout d'un arc
	 * @param x
	 * @param y
	 */
	
	public void addArc(int x, int y)
	{
		//Si les deux id sont distincts, on cr�e l'arc
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
	
	/**
	 * V�rifie si l'arc (i, j) existe
	 * @param i
	 * @param j
	 * @return un bool�en
	 */
	
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
	
	public void parcours()
	{
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
	
	
	/**
	 * Export d�un graphe sous format CSV selon la liste de ses arcs
	 * Format Source : Target 
	 */
	
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
			e.printStackTrace();
		}
	}

	/**
	 * Affichage du graphe
	 */
	
	@Override
	public String toString()
	{
		String s = "";
		
		
		Set<Integer> keys = noeuds_hm.keySet();
		
		for(Integer key : keys)
		{
			s += "R(" + this.getNoeud(key) + ") = ";
			s += "{";
			for(Arc arc : this.getNoeud(key).getSucc())
			{
				s += " (" + arc.getSource().toString() + "," + arc.getCible().toString() + ") ";
			}
			s += "}\n";
		}
		
		return s;
	}
	

	/**
	 * R�cup�re un noeud du graphe identifi� par son id
 	 * @param n
	 * @return un noeud
	 */
	
	public Noeud getNoeud(int n)
	{		
		return noeuds_hm.get(n);
	}
	
	/**
	 * Retourne la liste de noeuds identifi�s par un id
	 * @return un hash map
	 */

	public HashMap<Integer, Noeud> getNoeuds_hm() {
		return noeuds_hm;
	}

	/**
	 * Met � jour le graphe
	 * @param noeuds_hm
	 */
	
	public void setNoeuds_hm(HashMap<Integer, Noeud> noeuds_hm) {
		this.noeuds_hm = noeuds_hm;
	}
	
	

}