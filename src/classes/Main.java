package classes;

import java.util.ArrayList;

import types.TypePath;

public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		Graphe g1 = new Graphe("D:\\Graphe.csv");
		Graphe g2 = new RandomGraphe(50, 0.5);
		Color c = new Color();
		System.out.println(g1);
	
		//c.sequential(c.increasingIndex(g1.getNoeuds_hm()));
		
		//Simulated annealing
		double initTemp = 9000;
		double minLimitTemp = 1000;
		double alpha = 0.0001;
		double itermax = 20; 
		double maxTconst = 10;
		//c.simulatedAnnealing(g2.getNoeuds_hm(), initTemp, minLimitTemp, alpha, itermax, maxTconst, TypePath.increasingIndex);
		//ArrayList<Node> result = c.backtracking(g1.getNoeuds_hm());
		//System.out.println(c.sequential(c.increasingIndex(g1.getNoeuds_hm())));
		//Backtracking
		//c.graphColour(g2.getNoeuds_hm(), 5, TypePath.increasingIndex);
		c.backtracking(g2.getNoeuds_hm());
	}

}
