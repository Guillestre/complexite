package classes;

import types.TypePath;

public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		Graphe g1 = new Graphe("Graphe.csv");
		Graphe g2 = new RandomGraphe(500, 0.8);
		Color c = new Color();
	
		double initTemp = 500;
		double minLimitTemp = 10;
		double alpha = 0.99;
		double itermax = 1000; 
		double maxTconst = 2;
		
		//c.simulatedAnnealing(g2.getNoeuds_hm(), initTemp, minLimitTemp, alpha, itermax, maxTconst, TypePath.increasingIndex);	
		
		//90 solutions coloriables avec au plus trois couleurs et 120 solutions coloriables avec quatre couleurs au plus
		c.backtracking(g1.getNoeuds_hm(), 3);
		
	}

}
