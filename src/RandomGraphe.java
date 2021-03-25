
public class RandomGraphe extends Graphe {

	
	
	//Algorithm 1
	public RandomGraphe(int n, double p)
	{
		int v = 1;
		int w = -1;
		
		while( v < n )
		{
			double r = Math.random();
			
			if( p != 0 )
				w = w + 1 + (int)(Math.log(1-r)/Math.log(1-p));
				
			while( w >= v && v < n )
			{
				w = w - v;
				v = v + 1;
			}
			if( v < n )
				this.addArc(v, w);
		}
	}

	public RandomGraphe(int n, int m)
	{
		erdos_non_optimise(n, m);
		//erdos_optimise(n, m);
	}
	
	//Algorithm 2
	public void erdos_non_optimise(int n, int m)
	{
		int max = (int)( n*(n-1.0)/2.0 );
		for(int i = 0; i < m; i++)
		{
			int [] a;
			do {
				int r = (int)(Math.random() * max);
				a = bijection(r);
				
			}while(ArcExist(a[0], a[1]));
		
			this.addArc(a[0], a[1]);
		}
	}
	
	//Algorithm 3
	public void erdos_optimise(int n, int m)
	{
		int replaceSize = (int)(n*(n-2)/2);
		
		//Filling replace
		int[] replace = new int[replaceSize];
		
		for(int i = 0; i < replaceSize; i++)
		{
			replace[i] = i;
		}
		
		for(int i = 0; i < m ; i++)
		{
			int min = i;
			int max = (int)((n*(n-2.0)/2.0) - 1.0);
			int r = (int)(Math.random() * ((max - min) + 1));
			
			int[] a = bijection(r);
			
				if(!ArcExist(a[0], a[1]))
				{
					this.addArc(a[0], a[1]);
				}
				else
				{
					a = bijection(replace[r]);
					this.addArc(a[0], a[1]);	
				}
				
				a = bijection(i);
				if(!ArcExist(a[0], a[1]))
				{
					replace[r] = i;
				}
				else
					replace[r] = replace[i];
			}
		
	}
	
	public  int [] bijection(int i)
	{
		int v = 1 + (int)(-0.5 + Math.sqrt((1.0/4.0)+2.0*i));
		int w = i - (int)((v*(v-1.0))/2.0);
		int [] tab = {v, w};
		return tab;
	}
	
}
