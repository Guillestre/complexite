import java.util.Iterator;

public class main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		/*int[][] values = new int[4][4];
		int [] l0 = { 0, 0, 1, 1 };
		int [] l1 = { 0, 0, 0, 0 };
		int [] l2 = { 1, 0, 0, 0 };
		int [] l3 = { 1, 0, 0, 0 };
		
		values[0] = l0;
		values[1] = l1;
		values[2] = l2;
		values[3] = l3;
		*/
		
		System.out.println("---- PARCOURS -----");
		Graphe g1 = new Graphe("D:\\Graphe.csv");
		System.out.println(g1);
		
		g1.sequentielle(g1.indiceCroissant());

		
		/*System.out.println("---- GRAPHE ALEATOIRE -----");
		System.out.println("Graphe Erdos :");
		RandomGraphe g2 = new RandomGraphe(5, 5); 
		System.out.println(g2);
		System.out.println("Graphe Aléatoire :");
		RandomGraphe g3 = new RandomGraphe(5, 0.5); 
		System.out.println(g3);
		*/
		
	}

}
