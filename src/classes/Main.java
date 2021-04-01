package classes;
public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		Graphe g1 = new Graphe("D:\\Graphe.csv");
		Colorier c = new Colorier();
		
		System.out.println("---- GRAPHE -----");
		System.out.println(g1);
		
		System.out.println("---- PATH -----");
		c.decreasingDegree(g1.getNoeuds_hm());
		
		System.out.println("---- METHOD -----");
	}

}
