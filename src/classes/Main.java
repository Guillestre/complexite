package classes;

import types.TypeDisplay;
import types.TypePath;

public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		//Graphe g1 = new Graphe("Graphe.csv");
		Graphe g2 = new RandomGraphe(100, 0.5);
		System.out.println(g2);
		System.out.println("taboo : "+Color.taboo(Color.increasingIndex(g2), 10));
		System.out.println("taboo2 : "+Color.taboo2(Color.increasingIndex(g2), 10));
		
		//SA parameters value
		double initTemp = 150;
		double minLimitTemp = 0.001;
		double alpha = 0.9;
		double itermax = 200; 
		double maxTconst = 10;
	
		/** TEST AREA ***/
	
		int[][] s1 = {
				{0,0,1 ,0,0,9 ,0,0,0},
				{0,6,0 ,1,0,0 ,0,0,4},
				{0,0,0 ,2,0,0 ,0,3,0},
				
				{1,0,0 ,0,7,0 ,0,0,5},
				{0,0,0 ,0,0,0 ,0,4,0},
				{0,2,0 ,0,1,0 ,0,0,0},
				
				{8,0,0 ,0,0,0 ,0,0,0},
				{0,5,0 ,0,6,0 ,0,2,0},
				{0,0,0 ,0,5,0 ,0,0,6},
		};
		
		int[][] s2 = {
				{0,0,0 ,0,9,0 ,0,7,0},
				{0,0,0 ,0,0,0 ,0,0,0},
				{4,5,0 ,0,1,0 ,0,0,0},
				
				{9,0,2 ,1,0,0 ,0,5,0},
				{0,0,0 ,0,0,8 ,0,4,0},
				{1,0,0 ,0,0,3 ,0,0,0},
				
				{0,0,0 ,4,0,0 ,0,0,0},
				{0,0,0 ,0,2,0 ,0,0,0},
				{3,0,0 ,0,0,0 ,0,2,1},
		};
		
		int[][] s3 = {
				{0,0,0 ,0,0,0 ,0,1,0},
				{0,5,0 ,0,9,0 ,2,0,0},
				{0,0,0 ,0,0,0 ,0,0,0},
				
				{0,0,0 ,2,0,0 ,0,0,0},
				{0,0,8 ,0,0,7 ,3,0,0},
				{3,0,0 ,0,0,0 ,0,0,0},
				
				{0,0,0 ,0,0,4 ,0,3,0},
				{7,0,0 ,1,0,0 ,5,0,0},
				{0,0,3 ,0,0,2 ,0,0,0},
		};
		
		//Sudoku sudoku = new Sudoku(s2);
		//sudoku.resolveSA(initTemp, minLimitTemp, alpha, itermax, maxTconst);
		
		//System.out.println(Color.backtracking(g1));
		/*System.out.println(g2.toString());
		long startTime =  System.nanoTime();
		System.out.println(Color.sequential(Color.decreasingDegree(g2)));
		long endTime = System.nanoTime();
		long elapsedTime = endTime - startTime;
		System.out.println("Elapsed time: " + elapsedTime / 1000000 + " milliseconds");
		
		startTime =  System.nanoTime();
		System.out.println(Color.sequential(Color.increasingIndex(g2)));
		endTime = System.nanoTime();
		elapsedTime = endTime - startTime;
		System.out.println("Elapsed time: " + elapsedTime / 1000000 + " milliseconds");
		*/
		/*startTime =  System.nanoTime();
		System.out.println(Color.sequential(Color.smallest_last(g2)));
		endTime = System.nanoTime();
		elapsedTime = endTime - startTime;
		System.out.println("Elapsed time: " + elapsedTime / 1000000 + " milliseconds");*/
		
		
		//Color.simulatedAnnealing(g2, initTemp, minLimitTemp, alpha, itermax, maxTconst, TypePath.normal, TypeDisplay.normal);
		
		//System.out.println(Color.taboo( Color.increasingIndex(g1), 5));
		//System.out.println(Color.DSatur(Color.decreasingDegree(g1)));
	}

}
