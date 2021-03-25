import java.awt.List;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;

public class GrapheTSP extends Graphe {

	private HashMap<Integer, Point> points = new HashMap<Integer, Point>();
	
	
	public GrapheTSP(int n)
	{
		creerPoints(n);
	}
	
	public GrapheTSP(int n, int k)
	{
		System.out.println();
		//Création des points
		creerPoints(n);
		
		//Création des arcs
		
			//Contient m arcs les plus proches
			LinkedList<Arc> arcsProche = new LinkedList<Arc>();
			
			//Distance qui démarre à l'infini
			double d;
			
			Point pointA = null, pointB = null, pointPlusProche = null;
			
			//Parcours chaque noeud
			for(int i = 0; i < n; i++)
			{
				
				//Point que l'on veut relier avec point le plus proche
				pointA = points.get(i);
				
				//On fait k fois
				for(int m = 0; m < k; m++)
				{
					pointPlusProche = null;
					d = Double.POSITIVE_INFINITY;
					//Parcours tous les noeuds non étudiés
					for(int j = i; j < n; j++)
					{
						pointB = points.get(j);
						double distanceAB;
					
						if( j != i )
						{
							//calcul distance
							double absicce = Math.pow( pointA.getX() - pointB.getX() , 2);
							double ordonnee = Math.pow( pointA.getY() - pointB.getY() , 2);
							distanceAB = Math.sqrt( absicce + ordonnee );
				
							
							if( distanceAB < d  && !ArcExist(pointA.getId(), pointB.getId())) {
								d = distanceAB;
								pointPlusProche = points.get(j);
							}
							
							
						}
						
					}
					if(pointPlusProche != null ) {
						System.out.println("d(" + pointA.getId() + ", " + pointPlusProche.getId() + ") = " + d);
						this.addArc(pointA.getId(), pointPlusProche.getId());
					}
					
				}
				
			}
			
		}
	
	
	public void creerPoints(int n)
	{
		double Min = 0;
		double Max = 1;
		
		for(int i = 0; i < n; i++)
		{
			double x = Min + (Math.random() * ((Max - Min) + 1));
			double y = Min + (Math.random() * ((Max - Min) + 1));
			
			points.put(i, new Point(x, y, i));
			System.out.println( i + " = (" + x + ", " + y +")" );
		}
		System.out.println();
	}

	
}
