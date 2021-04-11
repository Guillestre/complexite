package classes;

public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		//Graphe g1 = new Graphe("Graphe.csv");
		//Graphe g2 = new RandomGraphe(100, 0.99);
	
		//SA parameters value
		double initTemp = 10000;
		double minLimitTemp = 0.3;
		double alpha = 0.9;
		double itermax = 1000; 
		double maxTconst = 50;
	
		/** TEST AREA ***/
	
		Sudoku sudoku = new Sudoku();
		//System.out.println(Color.backtracking(g1));
		//Color.simulatedAnnealing(g2, initTemp, minLimitTemp, alpha, itermax, maxTconst, TypePath.normal, TypeMode.normal);
		//sudoku.resolveSA(initTemp, minLimitTemp, alpha, itermax, maxTconst);

	}

}
