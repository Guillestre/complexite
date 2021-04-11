package classes;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Random;
import java.util.Set;

import com.sun.jdi.Type;

import types.TypeMode;
import types.TypePath;

public class Color {

	/******** ALGORITHMS ************************************************************************************/
	
	/**
	 * Called to found a path from a given hash map of nodes respecting increasing index rules
	 * @param nodes_hm
	 * @return the path represented by ArrayList
	 */
	
	public static ArrayList<Node> increasingIndex(Graphe g)
	{
		System.out.println("---- INCREASING INDEX ----");
		HashMap<Integer, Node> nodes_hm = g.getNoeuds_hm();
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
	
	
	private static ArrayList<Node> increasingIndexRec(Node n, ArrayList<Node> nodesTmp)
	{
		if(n != null && !n.getSucc().isEmpty() && !n.isMark())
		{
			n.setMark(true);
			nodesTmp.add(n);
			ArrayList<Node> noeuds = n.getSuccSortedByIndex();
			for(Node noeud : noeuds)
			{
				if(!noeud.isMark()) {
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
	
	public static ArrayList<Node> decreasingDegree(Graphe g)
	{
		System.out.println("---- DECREASING DEGREE ----");
		HashMap<Integer, Node> nodes_hm = g.getNoeuds_hm();
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
	
	private static ArrayList<Node> decreasingDegreeRec(Node n, ArrayList<Node> nodesTmp)
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
	
	public static ArrayList<Node> smallest_last(Graphe g)
	{
		System.out.println("---- SMALLEST LAST ----");
		HashMap<Integer, Node> nodes_hm = g.getNoeuds_hm();
		ArrayList<Node> init = transformToArrayList(nodes_hm);
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
	 * Inverse an array
	 * @param nodes
	 * @return the inverse array
	 */
	
	private static ArrayList<Node> inverse(ArrayList<Node> nodes)
	{
		
		ArrayList<Node> result = new ArrayList<Node>();
		
		for(int i = nodes.size() - 1; i >= 0 ; i--)
			result.add(nodes.get(i));
		
		return result;
	}
	
	/**
	 * Called to found the node with lesser degree into nodes
	 * @param nodes
	 * @return node with the lesser degree
	 */
	
	private static Node getLesserNodeDegree(ArrayList<Node> nodes)
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
	 * Color nodes in a sequential way
	 * @param nodes
	 * @return the minimum number of colors used
	 */
	public static int sequential(ArrayList<Node> nodes, TypeMode type) {
	
		switch (type) {
		
		case sudoku :
			return sudokuSequential(nodes);
		default :
			return normalSequential(nodes);
		}
		
	}
	
	private static int normalSequential(ArrayList<Node> nodes)
	{
		int omega = 0;
		resetColors(nodes);
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
	
	
	private static int sudokuSequential(ArrayList<Node> nodes)
	{
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
		
			if(!nodes.get(i).isStartColor()) {
				nodes.get(i).setColor(alpha);
			}
		}
		
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
	
	/**
	 * Return the best solution with a given initial solution
	 * @param initTemp
	 * @param alpha
	 * @param itermax
	 * @param maxTconst
	 * @return the best solution found
	 */
	
	public static ArrayList<Node> simulatedAnnealing(Graphe g, double initTemp, double minLimitTemp, double alpha, double itermax, double maxTconst, TypePath typePath, TypeMode typeMode)
	{
		switch(typeMode)
		{
		case sudoku :
			return sudokuSA(g, initTemp, minLimitTemp, alpha, itermax, maxTconst, typePath, typeMode);
		default :
			return normalSA(g, initTemp, minLimitTemp, alpha, itermax, maxTconst, typePath, typeMode);
		}
	}
	
	private static ArrayList<Node> normalSA(Graphe g, double initTemp, double minLimitTemp, double alpha, double itermax, double maxTconst, TypePath typePath, TypeMode typeMode)
	{
		System.out.println("---- SIMULATED ANNEALING STARTED ----");
		ArrayList<Node> currentSolution = getPath(g, typePath);
		double currentTemp = initTemp;
		
		ArrayList<Node> initSolution = currentSolution;
		double initMinColors = sequential(initSolution, typeMode);
		
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
				if(sequential(newSolution, typeMode) <= sequential(currentSolution, typeMode))
				{
					currentSolution = newSolution;
					if(sequential(currentSolution, typeMode) < initMinColors)
					{
						initSolution = currentSolution;
						initMinColors = sequential(currentSolution, typeMode);
						System.out.println("\t-> Better solution found : " + initSolution + "\n\twith " + initMinColors + " min colors\n");
					}
				}
				else
				{
					double p = (Math.random());
					double n =Math.exp(-( (double)sequential(newSolution, typeMode) - (double)sequential(currentSolution, typeMode) )/currentTemp);
					System.out.println(n);
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
	
	private static ArrayList<Node> sudokuSA(Graphe g, double initTemp, double minLimitTemp, double alpha, double itermax, double maxTconst, TypePath typePath, TypeMode typeMode)
	{
		ArrayList<Node> currentSolution = getPath(g, typePath);
		double currentTemp = initTemp;
		
		ArrayList<Node> initSolution = currentSolution;
		double initMinColors = sequential(initSolution, typeMode);
		
		int iter = 1;
		while( currentTemp > minLimitTemp && iter < itermax )
		{
			int counter = 1;
			while(counter < maxTconst)
			{
				ArrayList<Node> newSolution = randomSwap(currentSolution);
				if(sequential(newSolution, typeMode) <= sequential(currentSolution, typeMode))
				{
					currentSolution = newSolution;
					if(sequential(currentSolution, typeMode) < initMinColors)
					{
						initSolution = currentSolution;
						initMinColors = sequential(currentSolution, typeMode);
					}
				}
				else
				{
					double p = (Math.random());
					double n =Math.exp(-( (double)sequential(newSolution, typeMode) - (double)sequential(currentSolution, typeMode) )/currentTemp);
					//System.out.println("new sequential : " + sequential(newSolution, typeMode));
					//System.out.println("current sequential : " + sequential(currentSolution, typeMode));
					//System.out.println(n);
					if(p < n)
						currentSolution = newSolution;
				}
				
				counter++;
			}
			
			currentTemp = alpha * currentTemp;
		}
		
		return initSolution;
	}
	
	
	
	/**
	 * Called to transform a given solution by exchanging two node chosen randomly
	 * @return the new solution
	 */
	
	private static ArrayList<Node> randomSwap(ArrayList<Node> solution)
	{
		
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
		
		return newSolution;
	}
	
	/**
	 * Return best paths with their coloration with the smaller number of colors 
	 * @param nodes_hm
	 * @return
	 */
	
	public static ArrayList<ArrayList<Node>> backtracking(Graphe g)
	{
		System.out.println("---- BACKTRACKING STARTED ----");
		HashMap<Integer, Node> nodes_hm = g.getNoeuds_hm();
		ArrayList<Node> initPath = transformToArrayList(nodes_hm);
		ArrayList<ArrayList<Node>> allPaths = getPermutations(initPath);

		int nbAllPaths = allPaths.size();
		int nbColoriablePaths = 0;
		
		for(int m = 1; m <= initPath.size(); m++)
		{
			nbColoriablePaths = displayColoration(allPaths, nbAllPaths, nbColoriablePaths, m);
			
			if(nbColoriablePaths > 0)
				break;
			nbColoriablePaths = 0;
		}
		
		System.out.println("---- BACKTRACKING FINISHED ----");
		return allPaths;
	}
	
	/**
	 * Show on the console the betters paths with their colorations
	 * @param allPaths
	 * @param nbAllPaths
	 * @param nbColoriablePaths
	 * @param m
	 * @return the number of path coloriable
	 */
	
	private static int displayColoration(ArrayList<ArrayList<Node>> allPaths, int nbAllPaths, int nbColoriablePaths, int m) {
		System.out.println("\n******** " + m + " colors at most **********************************************************\n");
		for(ArrayList<Node> path : allPaths)
		{
			boolean isColoriable = backtrackColor(path, m);
			
			if(isColoriable) {
				System.out.println("Path: " + path + " -> " + sequential(path, TypeMode.normal) + "\n");
				nbColoriablePaths++;
			}
		}
		
		System.out.println(nbAllPaths + " paths in total with " + nbColoriablePaths + " paths with " + m + " colors");
		return nbColoriablePaths;
	}
	
	/**
	 * Check if the path nodes can be colored with at most n colors
	 * @param nodes
	 * @param n
	 * @return true if it's possible, false otherwise
	 */
	
	private static boolean backtrackColor(ArrayList<Node> nodes, int n)
	{
		resetColors(nodes);
		boolean isColoriable = backtrackColorRec(nodes, 0, n, "");
		return isColoriable;
	}
	
	private static boolean backtrackColorRec(ArrayList<Node> nodes, int currentPosition, int n, String str)
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
	
	/**
	 * Get all possibles permutations from an arrayList
	 * @param nodes
	 * @return all permutations
	 */
	
	private  static ArrayList<ArrayList<Node>> getPermutations(ArrayList<Node> nodes){  
	 	ArrayList<ArrayList<Node>> result = new ArrayList<ArrayList<Node>>();
        getPermutationsRec(nodes, 0, result);  
        return result;    
	 }  
	  
	 private static void getPermutationsRec(ArrayList<Node> nodes, int pos, ArrayList<ArrayList<Node>> result){  
		 
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
		int k = sequential(nodes, TypeMode.normal);
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
	
	/******** GENERAL FUNCTIONS ************************************************************************************/
	
	/**
	 * Reset all colors of the graph
	 * @param nodes
	 */
	
	public static void resetColors(ArrayList<Node> nodes)
	{
		for(Node node : nodes)
			if(!node.isStartColor())
				node.setColor(0);
	}
	
	/**
	 * Reset all marks of the graph
	 * @param nodes
	 */
	
	private static void resetMarks(ArrayList<Node> nodes)
	{
		for(Node node : nodes)
			node.setMark(false);
	}
	
	
	
	/**
	 * Transform a hash map of nodes into an ArrayList of nodes
	 * @param nodes_hm
	 * @return an ArrayList
	 */
	
	public static ArrayList<Node> transformToArrayList(HashMap<Integer, Node> nodes_hm)
	{
		ArrayList<Node> result = new ArrayList<Node>();
		
		Set<Integer> indexes = nodes_hm.keySet();
		
		for(Integer index : indexes)
			result.add(nodes_hm.get(index));
		
		return result;
	}
	
	/**
	 * Choose the right path to return according to typePath
	 * @param typePath
	 * @param nodes_hm
	 * @return
	 */
	
	private static ArrayList<Node> getPath(Graphe g, TypePath typePath)
	{
		switch (typePath) {
		
			case increasingIndex :
				return increasingIndex(g);
			case decreasingDegree :
				return decreasingDegree(g);
			case normal : 
				return transformToArrayList(g.getNoeuds_hm());
			default :
				return smallest_last(g);
		
		}
	}
	
}
