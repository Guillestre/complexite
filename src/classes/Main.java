package classes;

import types.TypeDisplay;
import types.TypePath;

public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		Graphe g1 = new Graphe("Graphe.csv");
		//Graphe g2 = new RandomGraphe(1000, 0.9);
		
		//SA parameters value
		double initTemp = 150;
		double minLimitTemp = 0.001;
		double alpha = 0.95;
		double itermax = 200; 
		double maxTconst = 1000;
	
		/** TEST AREA ***/
	
		int[][] m = {
				{0,0,0 ,0,0,0 ,0,2,3},
				{0,0,0 ,0,0,0 ,0,0,0},
				{0,0,0 ,0,2,3 ,0,0,0},
				
				{0,0,0 ,0,0,0 ,0,0,0},
				{0,0,0 ,0,0,0 ,0,0,0},
				{0,0,0 ,0,0,0 ,0,0,0},
				
				{0,0,0 ,0,0,0 ,0,0,0},
				{0,8,9 ,0,0,0 ,0,0,0},
				{7,0,0 ,0,0,0 ,0,0,4},
		};
		
		Sudoku sudoku = new Sudoku();
		sudoku.resolveSA(initTemp, minLimitTemp, alpha, itermax, maxTconst);
		
		//System.out.println(Color.backtracking(g1));
		//Color.simulatedAnnealing(g2, initTemp, minLimitTemp, alpha, itermax, maxTconst, TypePath.normal, TypeDisplay.normal);

	}

}
