package classes;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Random;
import java.util.Set;
import types.TypeDisplay;
import types.TypePath;

public class Color {
	
	/******** ALGORITHMS ************************************************************************************/
	
	/**
	 * Called to found a path from a given graph of nodes respecting increasing index rules
	 * @param g
	 * @return the path represented by ArrayList
	 */
	
	public static ArrayList<Node> increasingIndex(Graphe g)
	{
		System.out.println("---- INCREASING INDEX ----");
		HashMap<Integer, Node> nodes_hm = g.getNoeuds_hm();
		ArrayList<Node> nodes = new ArrayList<Node>();
		Set<Integer> indexes = nodes_hm.keySet();
		int lesserIndex = (int) Double.POSITIVE_INFINITY;
		
		//Fetch the node which has the smaller id
		for(int i : indexes)
		{
			if( i < lesserIndex )
				lesserIndex = i;
		}
		
		//Getting list with recursive method
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
				//If the node isn't marked, then we take his increasing index list in recursive 
				if(!noeud.isMark()) {
					nodesTmp = increasingIndexRec(noeud, nodesTmp);
				}
			}
		}
		return nodesTmp;
	}
	
	/**
	 * Called to found a path from a given graph of nodes respecting decreasing degree rules
	 * @param g
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
			//Getting successorDecreasing by degree
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
	 * Called to found a path from a given graph of nodes respecting smallest last rules
	 * @param g
	 * @return the path represented by ArrayList
	 */
	
	public static ArrayList<Node> smallest_last(Graphe g)
	{
		System.out.println("---- SMALLEST LAST ----");
		HashMap<Integer, Node> nodes_hm = g.getNoeuds_hm();
		ArrayList<Node> init = transformToArrayList(nodes_hm);
		ArrayList<Node> result = new ArrayList<Node>();
		
		//Retrieve one by one the nodes that have the less successors towards those which have the most
		while(result.size() != init.size())
		{
			Node lesserNodeDegree = getLesserNodeDegree(init);
			result.add(lesserNodeDegree);
		}	
		
		result = inverse(result);
		System.out.println("Path selected : " + result);
		System.out.println("-----------------------");
		resetMarks(result);
		return result;
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
			
			//Select the node with the lesser number of succesors
			if(succ.size() < lesserDegree && !currentNode.isMark())
			{
				lesserDegree = succ.size();
				lesserNodeDegree = currentNode;
			}
		}
		
		//We mark it in order to avoid to go back to it when we will recall the method 
		lesserNodeDegree.setMark(true);
		
		return lesserNodeDegree;
	}
	
	/**
	 * Color nodes in a sequential way
	 * @param nodes
	 * @return the chromatic number
	 */
	public static int sequential(ArrayList<Node> nodes) {
	
		int chromaticNumber = 0;
		
		//Reset all colors
		resetColors(nodes);
		
		//Fetch each node
		for (Node node : nodes) {
			
			//If node isn't colored
			if(node.getColor() == 0) {
				ArrayList<Integer> colors = new ArrayList<>();
				
				//Fetch adjacent nodes
				for (Arc arc : node.getSucc()) {
					Node target = arc.getTarget();
					
					//Add colored adjacent nodes in colors
					if (!colors.contains(target.getColor()) && target.getColor() != 0)
						colors.add(target.getColor());
				}
				int alpha = 1; // current color
				
				//While we encounter a color already token by an adjacent node, 
				//then we increment alpha
				while (colors.contains(alpha)) 
					alpha++;
				
				if (alpha > chromaticNumber) // update of the chromatic number
					chromaticNumber = alpha;
				
				node.setColor(alpha); // node coloration
			}
			else
			{
				//If node is already colored
				int color = node.getColor();
				if(color > chromaticNumber) // update of the chromatic number
					chromaticNumber = color;
			}
		}
		return chromaticNumber;
	}

	/**
	 * Look for the best solution with a given initial solution by using SA algorithm
	 * @param g
	 * @param initTemp
	 * @param alpha
	 * @param itermax
	 * @param maxTconst
	 * @param typePath
	 * @param typeDisplay
	 * @return the chromatic number
	 */
	
	public static int simulatedAnnealing(Graphe g, double initTemp, double minLimitTemp, double alpha, double itermax, double maxTconst, TypePath typePath, TypeDisplay typeDisplay)
	{
		if(typeDisplay == TypeDisplay.normal)
			System.out.println("---- SIMULATED ANNEALING STARTED ----");
		
		//Initialization
		ArrayList<Node> currentSolution = getPath(g, typePath);
		double currentTemp = initTemp;
		
		ArrayList<Node> finalSolution = currentSolution;
		int finalChromaticNumber = sequential(finalSolution);
		
		int iter = 1;
		
		if(typeDisplay == TypeDisplay.normal) {
			System.out.println("*** Init values ***");
			System.out.println("Init solution : " + finalSolution);
			System.out.println("Init min colors : " + finalChromaticNumber);
			System.out.println("Init temperature : " + initTemp);
			System.out.println("*******************");
		}
		
		while( currentTemp > minLimitTemp && iter < itermax)
		{
			int counter = 1;
			while(counter < maxTconst)
			{
				//Fetch new random solution
				ArrayList<Node> newSolution = randomSwap(currentSolution);
				
				//If the new solution is better than currentSolution
				if(sequential(newSolution) <= sequential(currentSolution))
				{
					//We assign the new solution to currentSolution
					currentSolution = newSolution;
					
					//If chromatic number of currentSolution is better than the current one
					if(sequential(currentSolution) < finalChromaticNumber)
					{
						//Then we update finalSolution and finalChromaticNumber
						finalSolution = currentSolution;
						finalChromaticNumber = sequential(currentSolution);
						
						//We display the better solution found
						if(typeDisplay == TypeDisplay.normal) 
							System.out.println("\t-> Better solution found : " + finalSolution + "\n\twith " + finalChromaticNumber + " min colors\n");
						
					}
				}
				else
				{
					//Retrieve the probability to get the wrong solution  
					double p = (Math.random());
					double diff = (double)sequential(newSolution) - (double)sequential(currentSolution);
					double threshold = Math.exp(-( diff )/currentTemp);
					
					if(p < threshold)
						currentSolution = newSolution;
				}
				
				counter++;
			}
			
			//Shrink the temperature
			currentTemp = alpha * currentTemp;
			
			//Display current temperature
			if(typeDisplay == TypeDisplay.normal) 
				System.out.println("\tTemperature is now at " + currentTemp + " degrees\n");
			
			iter++;
		}
		
		if(typeDisplay == TypeDisplay.normal) 
		System.out.println("\tBetter solution found during the program : " + finalSolution + "\n\twith " + finalChromaticNumber + " min colors");
		
		
		if(typeDisplay == TypeDisplay.normal) 
			System.out.println("\n---- SIMULATED ANNEALING FINISHED ----");
		
		//Color the graph with the better path found
		sequential(finalSolution);
		
		return finalChromaticNumber;
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
		
		//Make sure that both numbers aren't equals
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
	 * @param g
	 * @return the chromatic number
	 */
	
	public static int backtracking(Graphe g)
	{
		System.out.println("---- BACKTRACKING STARTED ----");
		
		//Initialization
		HashMap<Integer, Node> nodes_hm = g.getNoeuds_hm();
		ArrayList<Node> initPath = transformToArrayList(nodes_hm);
		ArrayList<ArrayList<Node>> allPaths = getAllPermutations(initPath);
		int nbAllPaths = allPaths.size();
		int nbColoriablePaths = 0;
		int chromaticNumber = 1;
		
		//Display best colorations with the lower chromatic number at most
		for(int m = 1; m <= initPath.size(); m++)
		{
			nbColoriablePaths = displayColoration(allPaths, nbAllPaths, nbColoriablePaths, m);
			
			if(nbColoriablePaths > 0) {
				chromaticNumber = m;
				break;
			}
		}
		
		System.out.println("---- BACKTRACKING FINISHED ----");
		return chromaticNumber;
	}
	
	/**
	 * Show on the console the betters paths with their colorations
	 * @param allPaths
	 * @param nbAllPaths
	 * @param nbColoriablePaths
	 * @param m
	 * @return the number of coloriable path
	 */
	
	private static int displayColoration(ArrayList<ArrayList<Node>> allPaths, int nbAllPaths, int nbColoriablePaths, int m) {
		System.out.println("\n******** " + m + " colors at most **********************************************************\n");
		for(ArrayList<Node> path : allPaths)
		{
			boolean isColoriable = backtrackColor(path, m);
			
			if(isColoriable) {
				System.out.println("Path: " + path + " -> " + sequential(path) + "\n");
				nbColoriablePaths++;
			}
		}
		
		System.out.println(nbAllPaths + " paths in total with " + nbColoriablePaths + " paths with at most " + m + " colors");
		return nbColoriablePaths;
	}
	
	/**
	 * Check if the path nodes can be colored with at most m colors
	 * @param nodes
	 * @param m
	 * @return true if it's possible, false otherwise
	 */
	
	private static boolean backtrackColor(ArrayList<Node> nodes, int m)
	{
		resetColors(nodes);
		boolean isColoriable = backtrackColorRec(nodes, 0, m, "");
		return isColoriable;
	}
	
	private static boolean backtrackColorRec(ArrayList<Node> nodes, int currentPosition, int m, String str)
	{ 
		Node currentNode = null;
		
		//If we've gone through each node of the path, we display the coloration found.
		//Otherwise, we go to the next node
		if(currentPosition == nodes.size()) {
			System.out.print("Coloration [" + str + "] \nwith the following ");
			return true;
		}
		else
			currentNode = nodes.get(currentPosition);
		
		//We test each color until the color n
		for(int c = 1; c <= m; c++)
		{
			currentNode.setColor(c);
			LinkedList<Arc> succ = currentNode.getSucc();
			boolean isSafe = true;
			//Fetch adjacent nodes
			for(Arc arc : succ)
			{
				Node neighbour = arc.getTarget();
				if(neighbour.getColor() == c)
					isSafe = false;
			}
			//If c is coloriable, then we continue to go through the path
			if(isSafe)
				return backtrackColorRec(nodes, currentPosition + 1, m, str + " " + currentNode.getColor() + " ");	
		}
		//If currentNode has been processed, then we reset his color
		currentNode.setColor(0);
		
		return false;
	}
	
	/**
	 * Get all possibles permutations from an arrayList
	 * @param nodes
	 * @return all permutations
	 */
	
	private  static ArrayList<ArrayList<Node>> getAllPermutations(ArrayList<Node> nodes){  
	 	ArrayList<ArrayList<Node>> result = new ArrayList<ArrayList<Node>>();
        getPermutationsRec(nodes, 0, result);  
        return result;    
	 }  
	  
	 private static void getPermutationsRec(ArrayList<Node> nodes, int pos, ArrayList<ArrayList<Node>> result){  
		 
		//If pos has reached the size of nodes, then we can add the new path to the result
        if(pos >= nodes.size() - 1){   
        	ArrayList<Node> newSolution = new ArrayList<Node>();
          
            for(int i = 0; i < nodes.size() ; i++)
            	newSolution.add(nodes.get(i));
           
            if(!result.contains(newSolution))
            	result.add(newSolution);
  
            return;  
        }  
  
        //Go through each possible position from pos 
        for(int i = pos; i < nodes.size(); i++){   
          
        	//Swap two nodes
            Node t = nodes.get(pos);  
            nodes.set(pos, nodes.get(i));
            nodes.set(i, t);
  
            getPermutationsRec(nodes, pos+1, result);  
  
            //Swap two nodes
            t = nodes.get(pos);  
            nodes.set(pos, nodes.get(i));
            nodes.set(i, t);
        }  
	 }  
	
	
	/**
	* return a coloration with DSatur process
	* @param nodes:ArrayList<Node>
	* @return omega:int
	 */
	public static int DSatur(ArrayList<Node> nodes) {
		int omega = 0;
		nodes.get(0).setColor(1);
		nodes.remove(0);
		while (!nodes.isEmpty()) {
			int i = DSat(nodes);
			Node node = nodes.get(i);
			ArrayList<Integer> colors = new ArrayList<Integer>();
			for (Arc arc : node.getSucc()) {
				Node target = arc.getTarget();
				if (!colors.contains(target.getColor()) && target.getColor() != 0)
					colors.add(target.getColor());
			}
			int alpha = 1;
			while (colors.contains(alpha))
				alpha++;
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
	private static int DSat(ArrayList<Node> nodes) {
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
	 * return a colorating with taboo process
	 * @param ArrayList nodes
	 * @param int iter
	 * @return int omega
	 */
	public static int taboo(ArrayList<Node> nodes, int iter) {
		int k = sequential(nodes); // we calculate an upper bound of the chromatic number
		int omega = k; // we initialize the chromatic number
		while (k > 0) { // while we can find a smaller chromatic number
			k -= 1; // we decrease the chromatic number
			// we check that a k-coloring exists
			if (isKColoriable(nodes, k, iter))  // if it's true
				omega = k; // we update the chromatic number
			else // if not
				return omega; // we stop
		}
		return omega;
	}
	
	/**
	 * check if a graph is k-coloriable
	 * @param Arraylist nodes
	 * @param int k
	 * @param int iter
	 * @return boolean
	 */
	private static boolean isKColoriable(ArrayList<Node> nodes, int k, int iter) {
		// colors initialisation
		int[] c = new int[k]; 
		for (int i = 0; i < c.length; i++) {
			c[i] = i + 1;
		} // contains available colors
		// memory initialisation
		ArrayList<ArrayList<Integer>> M = new ArrayList<>();
		for (int i = 0; i < nodes.size(); i++) {
			ArrayList<Integer> m = new ArrayList<>();
			for (int j = 0; j < c.length + 1; j++) {
				m.add(j, 0);
				M.add(i, m);
			}
		} // contains the number of iterations during which a transformation is prohibited
		
				// ----Initialisation----
		Collections.shuffle(nodes); // random route
		// sequential coloring with conflicts
		for (Node node : nodes) { 
			ArrayList<Integer> tmpColors = new ArrayList<>(); // neighbor color list
			for (Arc arc : node.getSucc()) {
				Node target = arc.getTarget(); // neighbor node
				if (!tmpColors.contains(target.getColor()))
					tmpColors.add(target.getColor());
			}
			int alpha = 1; // current color
			while (tmpColors.contains(alpha)) { // current color selection
				alpha++;
			}
			// conflicting coloring
			if (alpha > k)
				alpha = c[randomGenerator(1, k) - 1];
			node.setColor(alpha);
		}
		
				// ----Local processing----
		int index; // index of the node to transform
		int color; // transformation color
		for (int cpt = 0; cpt < nodes.size()*iter; cpt++) {
			int br = 0;
			// transformation selection
			do {
				index = randomGenerator(0, nodes.size() - 1);
				color = randomGenerator(1, k + 1);
				br++;
				if (br > nodes.size() * k) // if the memory is satured
					return false;
			} while (M.get(index).get(color) != 0); // while the transformation is not available
			M.get(index).set(color, iter); // processing storage
			nodes.get(index).setColor(color); // node coloring
			if (f(nodes) ==0) // if the coloring is clean
				return true;
			// update of memory
				// we decrease the memory
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
	 * return a coloring with taboo process
	 * @param ArrayList nodes
	 * @param int iter
	 * @return int omega
	 */
	public static int taboo2(ArrayList<Node> nodes, int iter) {
		int k = sequential(nodes); // we calculate an upper bound of the chromatic number
		int omega = k; // initialize the chromatic number
		while (k > 0) { // while we can find a smaller chromatic number
			k -= 1; // we decrease the chromatic number
			// we check that a k-coloring exists
			if (isKColoriable2(nodes, k, iter)) // if it's true
				omega = k; // we update the chromatic number
			else // if not
				return omega; // we stop
		}
		return omega;
	}
	
	/**
	 * check if a graph is k-coloriable
	 * @param Arraylist nodes
	 * @param int k
	 * @param int iter
	 * @return boolean
	 */
	private static boolean isKColoriable2(ArrayList<Node> nodes, int k, int iter) {
		// colors initialisation
		int[] c = new int[k];
		for (int i = 0; i < c.length; i++) {
			c[i] = i + 1;
		} // contains available colors
		// memory initialisation
		ArrayList<ArrayList<Integer>> M = new ArrayList<>();
		for (int i = 0; i < nodes.size(); i++) {
			ArrayList<Integer> m = new ArrayList<>();
			for (int j = 0; j < c.length + 1; j++) {
				m.add(j, 0);
				M.add(i, m);
			}
		} // contains the number of iterations during which a transformation is prohibited
		
				// ----Initialization----
		Collections.shuffle(nodes); // random route
		ArrayList<Node> conflictingNodes = new ArrayList<Node>(); // list of conflicting nodes
		// sequential coloring whith conflicts
		for (Node node : nodes) { 
			ArrayList<Integer> tmpColors = new ArrayList<>(); // neighbor color list
			for (Arc arc : node.getSucc()) {
				Node target = arc.getTarget(); // neighbor node
				if (!tmpColors.contains(target.getColor()))
					tmpColors.add(target.getColor());
			}
			int alpha = 1; // current color
			while (tmpColors.contains(alpha)) { // current color selection
				alpha++;
			}
			// conflicting coloring
			if (alpha > k) { 
				alpha = c[randomGenerator(1, k) - 1];
				conflictingNodes.add(node);
			}
			node.setColor(alpha);
		}
		
				// ----Local processing----
		int index; // index of the node to transform
		int color; // transformation color
		for (int cpt = 0; cpt < conflictingNodes.size()*iter; cpt++) {
			int br = 0;
			// transformation selection
			do {
				index = randomGenerator(0, conflictingNodes.size() - 1);
				color = randomGenerator(1, k + 1);
				br++;
				if (br > conflictingNodes.size() * k) // if the memory is satured
					return false;
			} while (M.get(index).get(color) != 0); // while the transformation is not available
			M.get(index).set(color, iter); // processing sorage
			conflictingNodes.get(index).setColor(color); // node coloring
			if (f(nodes) ==0) // if the coloring is clean
			return true;
			// update memory
				// we decrease the memory
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
	 * check that a coloring is clean
	 * @param ArrayList nodes
	 * @return int result
	 */
	private static int f(ArrayList < Node > nodes) {
		// the coloring is clean if result = 0
		int result = 0;
		for (Node node : nodes) {
			for (Arc arc : node.getSucc()) {
				result += g(arc);
			}
		}
		return result;
	}

	/**
	 * tests if two neighboring nodes are in conflict
	 * @param Arc arc
	 * @return int
	 */
	private static int g (Arc arc){
		// return 1 if conflict
		if (arc.getSource().getColor() == arc.getTarget().getColor())
			return 1;
		else
			return 0;
	}

	/**
	 * generate a random number between two bounds
	 * @param int inf
	 * @param inf sup
	 * @return int random
	 */
	public static int randomGenerator ( int inf, int sup){
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
	 * @return a path
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
	 * @return a path
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
