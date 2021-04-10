package classes;

import java.util.Random;

import types.TypePath;

public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		Graphe g1 = new Graphe("Graphe.csv");
		Graphe g2 = new RandomGraphe(7, 0.8);
		Color c = new Color();
	
		double initTemp = 500;
		double minLimitTemp = 10;
		double alpha = 0.99;
		double itermax = 1000; 
		double maxTconst = 2;
	
		//c.simulatedAnnealing(g2.getNoeuds_hm(), initTemp, minLimitTemp, alpha, itermax, maxTconst, TypePath.increasingIndex);
	
		//c.backtracking(g1.getNoeuds_hm());
		
		int omega = c.taboo(c.increasingIndex(g1.getNoeuds_hm()), 3);
		System.out.println("omega : "+omega);
		
	}

}
