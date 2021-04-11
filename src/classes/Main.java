package classes;

import java.util.ArrayList;
import java.util.Random;

import types.TypeMode;
import types.TypePath;

public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		Graphe g1 = new Graphe("Graphe.csv");
		Graphe g2 = new RandomGraphe(100, 0.9);
	
		//SA parameters value
		double initTemp = 10000;
		double minLimitTemp = 0.3;
		double alpha = 0.999;
		double itermax = 1000; 
		double maxTconst = 10;
	
		/** TEST AREA ***/
		Sudoku sudoku = new Sudoku();
		
		
		//System.out.println("Chromatic number is : " + Color.sequential(path, TypeMode.sudoku));
		//Color.backtracking(g1);
		//Color.simulatedAnnealing(g2, initTemp, minLimitTemp, alpha, itermax, maxTconst, TypePath.normal, TypeMode.normal);
		//Color.backtracking(g1);
		sudoku.resolveSA(initTemp, minLimitTemp, alpha, itermax, maxTconst);
	}

}
