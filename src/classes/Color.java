package classes;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.Set;
import types.TypePath;

public class Color {

	/**
	 * Called to found a path from a given hash map of nodes respecting increasing index rules
	 * @param nodes_hm
	 * @return the path represented by ArrayList
	 */
	
	public ArrayList<Node> increasingIndex(HashMap<Integer, Node> nodes_hm)
	{
		System.out.println("---- INCREASING INDEX ----");
		
		ArrayList<Node> nodes = new ArrayList<Node>();
		Set<Integer> indexes = nodes_hm.keySet();
		int lesserIndex = (int) Double.POSITIVE_INFINITY;
				
		for(int i : indexes)
		{
			if( i < lesserIndex )
				lesserIndex = i;
		}
		
		nodes = increasingIndexRec(nodes_hm.get(lesserIndex), nodes);
		System.out.println("Path selected : " + nodes);
		System.out.println("--------------------------");
		resetMarks(nodes);
		return nodes;
	}
	
	
	private ArrayList<Node> increasingIndexRec(Node n, ArrayList<Node> nodesTmp)
	{
		if(n != null && !n.getSucc().isEmpty() && !n.isMark())
		{
			n.setMark(true);
			nodesTmp.add(n);
			ArrayList<Node> noeuds = n.getSuccSortedByIndex();
			for(Node noeud : noeuds)
			{
				if(!noeud.isMark()) {
					//System.out.println("Voisins de " + n.getId() +" : " + noeud.getId());
					nodesTmp = increasingIndexRec(noeud, nodesTmp);
				}
			}
		}
		return nodesTmp;
	}
	
	/**
	 * Called to found a path from a given hash map of nodes respecting decreasing degree rules
	 * @param nodes_hm
	 * @return the path represented by ArrayList
	 */
	
	public ArrayList<Node> decreasingDegree(HashMap<Integer, Node> nodes_hm)
	{
		System.out.println("---- DECREASING DEGREE ----");
		ArrayList<Node> nodes = new ArrayList<Node>();
		Node firstNode = null;
		Set<Integer> indexes = nodes_hm.keySet();
		int greaterDegree = (int) Double.NEGATIVE_INFINITY;
		
		for(Integer index : indexes)
		{
			Node currentNode = nodes_hm.get(index);
			
			//Getting successor
			LinkedList<Arc> succ =  currentNode.getSucc();
		
			//Getting greater node neighbour
			if(succ.size() > greaterDegree) {
				greaterDegree = succ.size();
				firstNode = currentNode;
			}
			
		}
		nodes = decreasingDegreeRec(nodes_hm.get(firstNode.getId()), nodes);
		System.out.println("Path selected : " + nodes);
		System.out.println("--------------------------");
		resetMarks(nodes);
		return nodes;
	}
	
	private ArrayList<Node> decreasingDegreeRec(Node n, ArrayList<Node> nodesTmp)
	{
		if(!n.getSucc().isEmpty() && !n.isMark())
		{
			n.setMark(true);
			nodesTmp.add(n);
			ArrayList<Node> noeuds = n.getSuccSortedByDegree();
			for(Node noeud : noeuds)
			{
				if(!noeud.isMark()) {
					nodesTmp = decreasingDegreeRec(noeud, nodesTmp);
				}
			}
		}
		return nodesTmp;
	}
	
	/**
	 * Called to found a path from a given hash map of nodes respecting smallest last rules
	 * @param nodes_hm
	 * @return the path represented by ArrayList
	 */
	
	public ArrayList<Node> smallest_last(HashMap<Integer, Node> nodes_hm)
	{
		System.out.println("---- SMALLEST LAST ----");
		ArrayList<Node> init = transformToArray(nodes_hm);
		ArrayList<Node> result = new ArrayList<Node>();
			
		while(result.size() != init.size())
		{
			Node lesserNodeDegree = getLesserNodeDegree(init);
			result.add(lesserNodeDegree);
		}	
		
		System.out.println("Path selected : " + result);
		System.out.println("-----------------------");
		resetMarks(result);
		return inverse(result);
	}
	
	/**
	 * Transform a hash map of nodes into an ArrayList of nodes
	 * @param nodes_hm
	 * @return an ArrayList
	 */
	
	private ArrayList<Node> transformToArray(HashMap<Integer, Node> nodes_hm)
	{
		ArrayList<Node> result = new ArrayList<Node>();
		
		Set<Integer> indexes = nodes_hm.keySet();
		
		for(Integer index : indexes)
			result.add(nodes_hm.get(index));
		
		return result;
	}
	
	/**
	 * Called to found the node with lesser degree into nodes
	 * @param nodes
	 * @return node with the lesser degree
	 */
	
	private Node getLesserNodeDegree(ArrayList<Node> nodes)
	{
		Node lesserNodeDegree = null;
		int lesserDegree = Integer.MAX_VALUE;
		
		for(Node currentNode : nodes)
		{
			LinkedList<Arc> succ = currentNode.getSucc();
			if(succ.size() < lesserDegree && !currentNode.isMark())
			{
				lesserDegree = succ.size();
				lesserNodeDegree = currentNode;
			}
		}
		
		lesserNodeDegree.setMark(true);
		return lesserNodeDegree;
	}
	
	/**
	 * Inverse an array
	 * @param nodes
	 * @return the inverse array
	 */
	
	private ArrayList<Node> inverse(ArrayList<Node> nodes)
	{
		
		ArrayList<Node> result = new ArrayList<Node>();
		
		for(int i = nodes.size() - 1; i >= 0 ; i--)
			result.add(nodes.get(i));
		
		return result;
	}
	
	/**
	 * Color nodes in a sequential way
	 * @param nodes
	 * @return the minimum number of colors used
	 */
	public int sequential(ArrayList<Node> nodes) {
		int omega = 0;
		for (Node node : nodes) {
			ArrayList<Integer> colors = new ArrayList<>();
			// on récupère les couleurs des voisins
			for (Arc arc : node.getSucc()) {
				Node target = arc.getTarget();
				if (!colors.contains(target.getColor()) && target.getColor() != 0)
					colors.add(target.getColor());
			}
			int alpha = 1;
			while (colors.contains(alpha)) {
				alpha++;
			}
			if (alpha > omega)
				omega = alpha;
			node.setColor(alpha);
		}
		resetColors(nodes);
		return omega;
	}


	/*
	public int sequential(ArrayList<Node> nodes)
	{
		//System.out.println("---- SEQUENTIAL ----");
		resetColors(nodes);
		int omega = 0;
		
		for(int i = 0; i < nodes.size(); i++)
		{
			int alpha = 1;
			boolean isSafe = true;
			LinkedList<Arc> currentSucc = nodes.get(i).getSucc();
			
			do {
				isSafe = true;
				for(Arc arc : currentSucc)
				{
					Node cible = arc.getTarget();
					if(cible.getColor() == alpha) {
						isSafe = false;
						break;
					}
					
				}	
				
				if(!isSafe)
					alpha++;	
				
			}while(!isSafe);
			
			if(alpha > omega)
				omega = alpha;
		
			nodes.get(i).setColor(alpha);
		}
		//System.out.println("nb minimum colors : " + (int)omega);
		//System.out.println("----------------");
		resetColors(nodes);
		return omega;
	}
	 */
	
	private void resetColors(ArrayList<Node> nodes)
	{
		for(Node node : nodes)
			node.setColor(0);
	}
	
	private void resetMarks(ArrayList<Node> nodes)
	{
		for(Node node : nodes)
			node.setMark(false);
	}
	
	/**
	 * Return the best solution with a given initial solution
	 * @param initTemp
	 * @param alpha
	 * @param itermax
	 * @param maxTconst
	 * @return the best solution found
	 */
	
	public List<Node> simulatedAnnealing(HashMap<Integer, Node> nodes_hm, double initTemp, double minLimitTemp, double alpha, double itermax, double maxTconst, TypePath typePath)
	{
		System.out.println("---- SIMULATED ANNEALING STARTED ----");
		ArrayList<Node> currentSolution = getPath(nodes_hm, typePath);
		double currentTemp = initTemp;
		
		ArrayList<Node> initSolution = currentSolution;
		double initMinColors = sequential(initSolution);
		
		System.out.println("*** Init values ***");
		System.out.println("Init solution : " + initSolution);
		System.out.println("Init min colors : " + initMinColors);
		System.out.println("Init temperature : " + initTemp);
		System.out.println("*******************");
		
		int iter = 1;
		
		while( currentTemp > minLimitTemp && iter < itermax )
		{
			int counter = 1;
			while(counter < maxTconst)
			{
				ArrayList<Node> newSolution = randomSwap(currentSolution);
				if(sequential(newSolution) <= sequential(currentSolution))
				{
					currentSolution = newSolution;
					if(sequential(currentSolution) < initMinColors)
					{
						initSolution = currentSolution;
						initMinColors = sequential(currentSolution);
						System.out.println("\t-> Better solution found : " + initSolution + "\n\twith " + initMinColors + " min colors\n");
					}
				}
				else
				{
					double p = (Math.random());
					double n =Math.exp(-( sequential(newSolution) - sequential(currentSolution) )/currentTemp);
					if(p < n)
						currentSolution = newSolution;
				}
				
				counter++;
			}
			
			currentTemp = alpha * currentTemp;
			System.out.println("\tTemperature is now at " + currentTemp + " degrees\n");
			iter++;
		}
		
		
		System.out.println("\tBetter solution found during the program : " + initSolution + "\n\twith " + initMinColors + " min colors");
		
		System.out.println("\n---- SIMULATED ANNEALING FINISHED ----");
		return initSolution;
	}
	
	/**
	 * Called to transform a given solution by exchanging two node chosen randomly
	 * @return the new solution
	 */
	
	private ArrayList<Node> randomSwap(ArrayList<Node> solution)
	{
		//System.out.println("*** Transform solution ***");
		
		//System.out.println("old solution : " + solution);
		
		ArrayList<Node> newSolution = new ArrayList<Node>();
		
		for(Node node : solution)
			newSolution.add(node);
		
		//Getting two chosen id randomly
		int min = 0;
		int max = solution.size() - 1;
		
		int id_firstPosition;
		int id_secondPosition;
		
		do {
			id_firstPosition = min + (int)(Math.random() * ((max - min) + 1));
			id_secondPosition = min + (int)(Math.random() * ((max - min) + 1));
		}while(id_firstPosition == id_secondPosition);
		
		//Exchange positions
		Node tmpNode = newSolution.get(id_firstPosition);
		newSolution.set(id_firstPosition, newSolution.get(id_secondPosition));
		newSolution.set(id_secondPosition, tmpNode);
		
		//System.out.println("new solution : " + newSolution);
		
		return newSolution;
	}
	
	public ArrayList<ArrayList<Node>> backtracking(HashMap<Integer, Node> nodes_hm)
	{
		System.out.println("---- BACKTRACKING STARTED ----");
		
		ArrayList<Node> initPath = transformToArray(nodes_hm);
		ArrayList<ArrayList<Node>> allPaths = getPermutations(initPath);

		int nbAllPaths = allPaths.size();
		int nbColoriablePaths = 0;
		
		for(int m = 1; m <= initPath.size(); m++)
		{
			nbColoriablePaths = displayColoration(allPaths, nbAllPaths, nbColoriablePaths, m);
			
			//If paths are found with m colors, then we finish backtracking
			if(nbColoriablePaths > 0)
				break;
			nbColoriablePaths = 0;
		}
		
		System.out.println("---- BACKTRACKING FINISHED ----");
		return allPaths;
	}


	private int displayColoration(ArrayList<ArrayList<Node>> allPaths, int nbAllPaths, int nbColoriablePaths, int m) {
		System.out.println("\n******** " + m + " colors at most **********************************************************\n");
		for(ArrayList<Node> path : allPaths)
		{
			boolean isColoriable = backtrackColor(path, m);
			
			if(isColoriable) {
				System.out.println("Path: " + path + " -> " + sequential(path) + "\n");
				nbColoriablePaths++;
			}
		}
		
		System.out.println(nbAllPaths + " paths in total with " + nbColoriablePaths + " paths with " + m + " colors");
		return nbColoriablePaths;
	}
	
	private boolean backtrackColor(ArrayList<Node> nodes, int n)
	{
		boolean isColoriable = backtrackColorRec(nodes, 0, n, "");
		resetColors(nodes);
		return isColoriable;
	}
	
	private boolean backtrackColorRec(ArrayList<Node> nodes, int currentPosition, int n, String str)
	{
		Node currentNode = null;
		
		if(currentPosition == nodes.size()) {
			System.out.print("Coloration [" + str + "] \nwith the following ");
			return true;
		}
		else
			currentNode = nodes.get(currentPosition);
		
		for(int c = 1; c <= n; c++)
		{
			currentNode.setColor(c);
			LinkedList<Arc> succ = currentNode.getSucc();
			boolean isSafe = true;
			for(Arc arc : succ)
			{
				Node neighbour = arc.getTarget();
				if(neighbour.getColor() == c)
					isSafe = false;
			}
			
			if(isSafe)
				return backtrackColorRec(nodes, currentPosition + 1, n, str + " " + currentNode.getColor() + " ");
			
		}
		currentNode.setColor(0);
		return false;
	}
	
	private  ArrayList<ArrayList<Node>> getPermutations(ArrayList<Node> nodes){  
	 	ArrayList<ArrayList<Node>> result = new ArrayList<ArrayList<Node>>();
        getPermutationsRec(nodes, 0, result);  
        return result;    
	 }  
	  
	 private void getPermutationsRec(ArrayList<Node> nodes, int pos, ArrayList<ArrayList<Node>> result){  
		 
        if(pos >= nodes.size() - 1){   
        	ArrayList<Node> newSolution = new ArrayList<Node>();
          
            for(int i = 0; i < nodes.size() ; i++)
            	newSolution.add(nodes.get(i));
           
            if(!result.contains(newSolution))
            	result.add(newSolution);
  
            return;  
        }  
  
        for(int i = pos; i < nodes.size(); i++){   
          
            Node t = nodes.get(pos);  
            nodes.set(pos, nodes.get(i));
            nodes.set(i, t);
  
            getPermutationsRec(nodes, pos+1, result);  
  
            t = nodes.get(pos);  
            nodes.set(pos, nodes.get(i));
            nodes.set(i, t);
        }  
	 }  
	
	 /*
	 public void permutation(HashMap<Integer, Node> nodes_hm, ArrayList<Node> permutation,int color ) {
			ArrayList<Node> uniqueList=transformToArray(nodes_hm);
		    if (permutation == null) {
		        assert 0 < uniqueList.size() : "Liste en entrer vide";
		        permutation = new ArrayList<>(uniqueList.size());
		        System.out.println("---- BACKTRACKING STARTED ----");
		    }
		    for (Node i : uniqueList) {
		        if (permutation.contains(i)) {
		            continue;
		        }
		        permutation.add(i);
		        if (permutation.size() == uniqueList.size()) {
		        	if(backtrackColorRec(permutation, 0, color, "")) {
		        		System.out.println(Arrays.toString(permutation.toArray())+"->"+color+"\n\n");
		        	}
		        }
		        if (permutation.size() < uniqueList.size()) {
		        	 permutation(nodes_hm, permutation,color);
		        }
		        permutation.remove(permutation.size() - 1);
		    }
		    if(permutation.size()==0) {
		    	System.out.println(factorial(nodes_hm.size())+" path in total ");
		    	System.out.println("---- BACKTRACKING FINISH ----");
		    }
		}
		*/
	
	public int factorial(int n) {
	    if (n>1)
	        return n*factorial(n-1);
	    else
	        return 1;
	}
	
	
	/**
	 * Choose the right path to return according to typePath
	 * @param typePath
	 * @param nodes_hm
	 * @return
	 */
	
	private ArrayList<Node> getPath(HashMap<Integer, Node> nodes_hm, TypePath typePath)
	{
		switch (typePath) {
		
			case increasingIndex :
				return increasingIndex(nodes_hm);
			case decreasingDegree :
				return decreasingDegree(nodes_hm);
			default :
				return smallest_last(nodes_hm);
		
		}
		
	}
	
	
	/**
	* return a coloration with DSatur pocess
	* @param nodes:ArrayList<Node>
	* @return omega:int
	 */
	public int DSatur(ArrayList<Node> nodes) {
		int omega = 0;
		nodes.get(0).setColor(1);
		nodes.remove(0);
		while (!nodes.isEmpty()) {
			int i = DSat(nodes);
			Node node = nodes.get(i);
			int alpha = 1;
			for (Arc arc : node.getSucc()) {
				Node target = arc.getTarget();
				if (target.getColor() == alpha)
					alpha++;
			}
			node.setColor(alpha);
			nodes.remove(node);
			if (alpha > omega)
				omega = alpha;
		}
		return omega;
	}

	/**
	* DSat function
	* @param nodes:ArrayList<Node>
	* @return result:int
	 */
	private int DSat(ArrayList<Node> nodes) {
		Node result = null;
		int min = (int) Double.POSITIVE_INFINITY;
		for (Node node : nodes) {
			ArrayList<Integer> colors = new ArrayList<>();
			for (Arc arc : node.getSucc()) {
				Node target = arc.getTarget();
				if (!colors.contains(target.getColor()))
					colors.add(target.getColor());
			}
			if (colors.size() < min) {
				min = colors.size();
				result = node;
			}
		}
		return nodes.indexOf(result);
	}

	/**
	 * return a coloration with taboo process
	 * @param nodes:ArrayList
	 * @return omega:int
	 */
	public int taboo(ArrayList<Node> nodes, int iter) {
		int k = sequential(nodes);
		int omega = k;
		while (k > 0) {
			k -= 1;
			if (isKColoriable(nodes, k, iter))
				omega = k;
			else
				return omega;
		}
		return omega;
	}
	
	/**
	 * check if a graph is k-coloriable
	 * @param nodes:Arraylist
	 * @param k:int
	 * @param iter:int
	 * @return boolean
	 */
	private boolean isKColoriable(ArrayList<Node> nodes, int k, int iter) {
		// colors initialisation
		int[] c = new int[k];
		for (int i = 0; i < c.length; i++) {
			c[i] = i + 1;
		}
		// memory initialisation
		ArrayList<ArrayList<Integer>> M = new ArrayList<>();
		for (int i = 0; i < nodes.size(); i++) {
			ArrayList<Integer> m = new ArrayList<>();
			for (int j = 0; j < c.length + 1; j++) {
				m.add(j, 0);
				M.add(i, m);
			}
		}
		// --Initialisation--
		Collections.shuffle(nodes); // random route
		for (Node node : nodes) { // sequential coloration
			ArrayList<Integer> tmpColors = new ArrayList<>();
			for (Arc arc : node.getSucc()) {
				Node target = arc.getTarget();
				if (!tmpColors.contains(target.getColor()))
					tmpColors.add(target.getColor());
			}
			int alpha = 1;
			while (tmpColors.contains(alpha)) {
				alpha++;
			}
			if (alpha > k)
				alpha = c[randomGenerator(1, k) - 1];
			node.setColor(alpha);
		}
		// --Transformation locale--
		int index;
		int color;
		for (int cpt = 0; cpt < nodes.size(); cpt++) {
			int br = 0;
			do {
				index = randomGenerator(0, nodes.size() - 1);
				color = randomGenerator(1, k + 1);
				br++;
				if (br > M.size() * k)
					return false;
			} while (M.get(index).get(color) != 0);
			M.get(index).set(color, iter);
			nodes.get(index).setColor(color);
			if (f(nodes) ==0)
			return true;
			// update memory
			for (int i = 0; i < nodes.size(); i++) {
				for (int j = 0; j < c.length; j++) {
					int tmp = M.get(i).get(j);
					if (tmp != 0)
						M.get(i).set(j, tmp - 1);
				}
			}
		}
		return false;
	}

	/**
	 * objective function
	 * @param nodes:ArrayList
	 * @return result:int
	 */
	private int f(ArrayList < Node > nodes) {
		int result = 0;
		for (Node node : nodes) {
			for (Arc arc : node.getSucc()) {
				result += g(arc);
			}
		}
		return result;
	}

	/**
	 * g function
	 * @param arc:Arc
	 * @return int
	 */
	private int g (Arc arc){
		if (arc.getSource().getColor() == arc.getTarget().getColor())
			return 1;
		else
			return 0;
	}

	/**
	 * generate a random number between two bounds
	 * @param inf:int
	 * @param sup:int
	 * @return random:int
	 */
	private int randomGenerator ( int inf, int sup){
		if (inf == sup)
			return inf;
		Random r = new Random();
		int random = inf + r.nextInt(sup - inf);
		return random;
	}
	
	
}
