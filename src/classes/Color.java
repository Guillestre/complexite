package classes;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
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
		return nodes;
	}
	
	
	private ArrayList<Node> increasingIndexRec(Node n, ArrayList<Node> nodesTmp)
	{
		if(!n.getSucc().isEmpty() && !n.isMark())
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
		
		System.out.println("old path : " + init);
		
		while(result.size() != init.size())
		{
			Node lesserNodeDegree = getLesserNodeDegree(init);
			result.add(lesserNodeDegree);
		}
		
		System.out.println("new path : " + result);
		
		
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
	
	public double sequential(ArrayList<Node> nodes)
	{
		//System.out.println("---- SEQUENTIAL ----");
	
		double omega = 0;
		int[] colors = new int[nodes.size()];
		
		for(int i = 0; i < nodes.size(); i++)
		{
			int alpha = 1;
			boolean isSafe = true;
			
			do {
				isSafe = true;
				for(Arc arc : nodes.get(i).getSucc())
				{
					Node cible = arc.getCible();
					int j = 0;
					for(Node node : nodes)
					{
						if(cible.equals(node))
							if(colors[j] == alpha)
								isSafe = false;
						j++;
					}	
				}	
				if(!isSafe)
					alpha++;	
			}while(!isSafe);
			
			if(alpha > omega)
				omega = alpha;
		
			colors[i] = alpha;
		}
		//System.out.println("nb minimum colors : " + (int)omega);
		return omega;
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
		System.out.println("---- SIMULATED ANNEALING ----");
		ArrayList<Node> currentSolution = getPath(nodes_hm, typePath);
		double currentTemp = initTemp;
		
		ArrayList<Node> initSolution = currentSolution;
		double initMinColors = sequential(initSolution);
		
		int iter = 1;
		
		while( currentTemp > minLimitTemp && iter < itermax )
		{
			int counter = 1;
			while(counter < maxTconst)
			{
				ArrayList<Node> newSolution = transformSolution(currentSolution);
				if(sequential(newSolution) <= sequential(currentSolution))
				{
					currentSolution = newSolution;
					if(sequential(currentSolution) < initMinColors)
					{
						initSolution = currentSolution;
						initMinColors = sequential(currentSolution);
					}
				}
				else
				{
					double p = (Math.random() * ((1.0) + 1.0));
					double n =Math.exp(-( sequential(newSolution) - sequential(currentSolution) )/currentTemp);
					if(p < n)
						currentSolution = newSolution;
				}
				
				counter++;
			}
			
			currentTemp = alpha * currentTemp;
			System.out.println("current temp : " + currentTemp);
			iter++;
		}
		
		System.out.println("\nbest solution found : " + initSolution);
		sequential(initSolution);
		return initSolution;
	}
	
	/**
	 * Called to transform a given solution by exchanging two node chosen randomly
	 * @return the new solution
	 */
	
	private ArrayList<Node> transformSolution(ArrayList<Node> solution)
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
		System.out.println("---- BACKTRACKING ----");
		
		ArrayList<Node> initPath = transformToArray(nodes_hm);
		ArrayList<ArrayList<Node>> allPath = new ArrayList<ArrayList<Node>>();
		allPath.add(initPath);
		
		System.out.println("\t-- All paths");
		while(allPath.size() != factorial(nodes_hm.size()))
		{
			initPath = transformSolution(initPath);
			if(!allPath.contains(initPath)) { 
				allPath.add(initPath);
				System.out.println("\t" + initPath + " : " + sequential(initPath));
			}
		}
		System.out.println("");
		
		ArrayList<Node> betterPath = allPath.get(0);
		for(ArrayList<Node> path : allPath)
		{
			if(!path.equals(betterPath))
				if(sequential(path) < sequential(betterPath))
					betterPath = path;
		}
		
		System.out.println("better path found : " + betterPath);
		
		return allPath;
	}
	
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
	
}
