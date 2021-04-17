package classes;
import java.util.ArrayList;
import java.util.LinkedList;

public class Node {
	
	private int id;
	private LinkedList<Arc> succ;
	private boolean startColor;
	private boolean mark;
	private int couleur;
	
	public Node(int id)
	{
		this.id = id;
		succ = new LinkedList<Arc>();
	}
	
	public Node(int id, int couleur)
	{
		this.id = id;
		this.couleur = couleur;
		succ = new LinkedList<Arc>();
	}
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String toString()
	{
		String s = id + "";
				
		return s;
	}
	
	public LinkedList<Arc> getSucc()
	{
		return succ;
	}
	
	
	public boolean hasSuccesseur(int j)
	{
		for(Arc arc : succ)
		{
			if( arc.getTarget().getId() == j )
				return true;
		}
		
		return false;
	}

	public boolean isMark() {
		return mark;
	}

	public void setMark(boolean mark) {
		this.mark = mark;
	}
	
	public void removeAllSucc()
	{
		succ = new LinkedList<Arc>();
	}
	
	public int getColor() {
		return couleur;
	}

	public void setColor(int couleur) {
		this.couleur = couleur;
	}

	public boolean isStartColor() {
		return startColor;
	}

	public void setStartColor(boolean startColor) {
		this.startColor = startColor;
	}
	
	/**
	 * Called to get list of successors of the node ordered by their id in a increasing way
	 * @return list of successors
	 */
	
	public ArrayList<Node> getSuccSortedByIndex()
	{
		ArrayList<Node> markedNodes = new ArrayList<Node>();
		
		//Set lesser node
		int lesserID = (int) Double.POSITIVE_INFINITY;
		
		Node node = null;
	
		//While the list of result hasn't the size of successor list
		while(markedNodes.size() != succ.size()) {
		
			for(Arc arc : succ)
			{
				Node neighbour = arc.getTarget();
				
				//Select node with the lesser id which is no yet in the result
				if(neighbour.getId() < lesserID && !markedNodes.contains(neighbour)) {
					lesserID = neighbour.getId();
					node = arc.getTarget();
				}
			}
			
			//Add lesser id
			if(lesserID != (int) Double.POSITIVE_INFINITY ) {
				markedNodes.add(node);
				lesserID = (int) Double.POSITIVE_INFINITY;
			}
			
		}
		return markedNodes;
	}

	
	/**
	 * Called to get list of successors of the node ordered by their degree in a decreasing way
	 * @return list of successors
	 */
	
	public ArrayList<Node> getSuccSortedByDegree()
	{
		
		ArrayList<Node> sortedNodes = new ArrayList<Node>();
		int Best=-1;
		Node node = null;
	
		//While result list hasn't the size of successors list
		while(sortedNodes.size() != succ.size()) {
			
			for(Arc arc : succ)
			{
				Node neighbour = arc.getTarget();
				
				//Select node with the greater degree which is no yet in the result
				if(!sortedNodes.contains(neighbour) && neighbour.getSucc().size()>Best)
				{
					Best=neighbour.getSucc().size();
					node=neighbour;
				}	
			}
		
			//Add greater degree
			if(Best!=-1) {
				sortedNodes.add(node);
				Best=-1;
			}
		}

		return sortedNodes;
	}

}
