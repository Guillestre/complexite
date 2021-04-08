package classes;

import java.util.ArrayList;

import types.TypePath;

public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		Graphe g1 = new Graphe("D:\\Graphe.csv");
		Graphe g2 = new RandomGraphe(1000, 0.4);
		Color c = new Color();
	
		double initTemp = 20;
		double minLimitTemp = 10;
		double alpha = 0.99;
		double itermax = 40; 
		double maxTconst = 60;
		
		c.simulatedAnnealing(g2.getNoeuds_hm(), initTemp, minLimitTemp, alpha, itermax, maxTconst, TypePath.increasingIndex);	
	}

}
