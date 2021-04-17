package classes;

import java.util.ArrayList;
import types.TypeDisplay;
import types.TypePath;

/**
 * Class used to simulate Sudoku using graph
 * @author guill
 *
 */

public class Sudoku extends Graphe {

	//Matrix of ids of the nodes  
	private int[][] m_ids = new int[9][9];
	
	//Matrix representing the sudoku by colors from 1 to 9
	private int[][] m_colors = new int[9][9];
	
	//ids of nodes from each row
	private ArrayList<ArrayList<Node>> id_rows = new ArrayList<ArrayList<Node>>();
	
	//ids of nodes from each column
	private ArrayList<ArrayList<Node>> id_cols = new ArrayList<ArrayList<Node>>();
	
	/**
	 * Initialize sudoku with random colors
	 */
	
	public Sudoku()
	{
		for(int i = 1; i < 82; i++)
			this.addNoeud(i);
		
		setIdMatrix();
		
		//Set edges 
		setEdgesRows();
		setEdgesCols();
		setEdgesBlocks();
		
		setRandomSudoku();
	}
	
	/**
	 * Initialize sudoku a matrix m of colors given 
	 * @param m
	 */
	
	public Sudoku(int m[][])
	{
		
		for(int i = 1; i < 82; i++)
			this.addNoeud(i);
		
		setIdMatrix();
		
		//Set edges
		setEdgesRows();
		setEdgesCols();
		setEdgesBlocks();
		
		setSudoku(m);
	}
	
	/**
	 * Set matrix of id
	 */
	
	private void setIdMatrix()
	{
		int k = 1;
		for(int i = 0; i < 9; i++)
		{
			for(int j = 0; j < 9; j++)
			{
				m_ids[i][j] =  this.getNoeud(k).getId();
				k++;
			}
		}
	}
	
	/**
	 * Set edges for each row
	 */
	
	private void setEdgesRows()
	{
		
		//Add each row into into id_rows
		for(int i = 0; i < 9; i++)
		{
			ArrayList<Node> row = new ArrayList<Node>();
			for(int j = 0; j < 9; j++)
			{
				if( j + 1 < 9)
				{
					Node firstNode = this.getNoeud(m_ids[i][j]); 
					Node secondNode = this.getNoeud(m_ids[i][j + 1]);
					
					if(!row.contains(firstNode))
						row.add(firstNode);
					
					if(!row.contains(secondNode))
						row.add(secondNode);
				
				}
			}
			this.id_rows.add(row);
		}
		
		//For each row
		for(ArrayList<Node> row : id_rows)
		{
			//For a given node, create edges between this node and all others nodes from the same row 
			for(Node node : row)
			{
				for(Node tmpNode : row)
				{
					this.addArc(node.getId(), tmpNode.getId());
				}
			}
		}
	}
	
	/**
	 * Set edges for each column
	 */
	
	private void setEdgesCols()
	{
		//Add each column into into id_cols
		for(int j = 0; j < 9; j++)
		{
			ArrayList<Node> col = new ArrayList<Node>();
			for(int i = 0; i < 9; i++)
			{
				if( i + 1 < 9)
				{
					Node firstNode = this.getNoeud(m_ids[i][j]); 
					Node secondNode = this.getNoeud(m_ids[i + 1][j]);
					
					if(!col.contains(firstNode))
						col.add(firstNode);
					
					if(!col.contains(secondNode))
						col.add(secondNode);
				
				}
			}
			this.id_cols.add(col);
		}
		
		//For each column
		for(ArrayList<Node> col : id_cols)
		{
			//For a given node, create edges between this node and all others nodes from the same column
			for(Node node : col)
			{
				for(Node tmpNode : col)
					this.addArc(node.getId(), tmpNode.getId());
			}
		}
		
	}
	
	/**
	 * Set edges into each block
	 */
	
	private void setEdgesBlocks()
	{
		for(int i = 0; i < 9; i++)
		{
			for(int j = 0; j < 9; j++)
				addBlockEdges(i, j);
		}
	}
	
	/**
	 * Called to update the matrix of colors
	 */
	
	private void updateSudoku()
	{

		for(int i = 0; i < 9; i++)
		{
			for(int j = 0; j < 9; j++)
				m_colors[i][j] = this.getNoeud(m_ids[i][j]).getColor();
		}
		
	}
	
	/**
	 * For a given node from a specific block, create edges with others nodes in the same block not yet connected with
	 * @param i
	 * @param j
	 */
	
	private void addBlockEdges(int i, int j)
	{
		Node currentNode = this.getNoeud(m_ids[i][j]);
		
		if(i % 3 == 0)
		{
			if(j % 3 == 0)
			{
				this.addArc(currentNode.getId(), this.getNoeud(m_ids[i + 1][j + 1]).getId());
				this.addArc(currentNode.getId(), this.getNoeud(m_ids[i + 1][j + 2]).getId());
				this.addArc(currentNode.getId(), this.getNoeud(m_ids[i + 2][j + 1]).getId());
				this.addArc(currentNode.getId(), this.getNoeud(m_ids[i + 2][j + 2]).getId());
			} else if(j % 3 == 1) {
				this.addArc(currentNode.getId(), this.getNoeud(m_ids[i + 1][j - 1]).getId());
				this.addArc(currentNode.getId(), this.getNoeud(m_ids[i + 1][j + 1]).getId());
				this.addArc(currentNode.getId(), this.getNoeud(m_ids[i + 2][j - 1]).getId());
				this.addArc(currentNode.getId(), this.getNoeud(m_ids[i + 2][j + 1]).getId());
			} else if(j % 3 == 2) {
				this.addArc(currentNode.getId(), this.getNoeud(m_ids[i + 1][j - 2]).getId());
				this.addArc(currentNode.getId(), this.getNoeud(m_ids[i + 1][j - 1]).getId());
				this.addArc(currentNode.getId(), this.getNoeud(m_ids[i + 2][j - 2]).getId());
				this.addArc(currentNode.getId(), this.getNoeud(m_ids[i + 2][j - 1]).getId());
			}
		} else if(i % 3 == 1) {
			if(j % 3 == 0)
			{
				this.addArc(currentNode.getId(), this.getNoeud(m_ids[i - 1][j + 1]).getId());
				this.addArc(currentNode.getId(), this.getNoeud(m_ids[i - 1][j + 2]).getId());
				this.addArc(currentNode.getId(), this.getNoeud(m_ids[i + 1][j + 1]).getId());
				this.addArc(currentNode.getId(), this.getNoeud(m_ids[i + 1][j + 2]).getId());
			} else if(j % 3 == 1) {
				this.addArc(currentNode.getId(), this.getNoeud(m_ids[i - 1][j - 1]).getId());
				this.addArc(currentNode.getId(), this.getNoeud(m_ids[i - 1][j + 1]).getId());
				this.addArc(currentNode.getId(), this.getNoeud(m_ids[i + 1][j - 1]).getId());
				this.addArc(currentNode.getId(), this.getNoeud(m_ids[i + 1][j + 1]).getId());
			} else if(j % 3 == 2) {
				this.addArc(currentNode.getId(), this.getNoeud(m_ids[i - 1][j - 2]).getId());
				this.addArc(currentNode.getId(), this.getNoeud(m_ids[i - 1][j - 1]).getId());
				this.addArc(currentNode.getId(), this.getNoeud(m_ids[i + 1][j - 2]).getId());
				this.addArc(currentNode.getId(), this.getNoeud(m_ids[i + 1][j - 1]).getId());
			}
		} else if(i % 3 == 2) {
			if(j % 3 == 0)
			{
				this.addArc(currentNode.getId(), this.getNoeud(m_ids[i - 2][j + 1]).getId());
				this.addArc(currentNode.getId(), this.getNoeud(m_ids[i - 2][j + 2]).getId());
				this.addArc(currentNode.getId(), this.getNoeud(m_ids[i - 1][j + 1]).getId());
				this.addArc(currentNode.getId(), this.getNoeud(m_ids[i - 1][j + 2]).getId());
			} else if(j % 3 == 1) {
				this.addArc(currentNode.getId(), this.getNoeud(m_ids[i - 2][j - 1]).getId());
				this.addArc(currentNode.getId(), this.getNoeud(m_ids[i - 2][j + 1]).getId());
				this.addArc(currentNode.getId(), this.getNoeud(m_ids[i - 1][j - 1]).getId());
				this.addArc(currentNode.getId(), this.getNoeud(m_ids[i - 1][j + 1]).getId());
			} else if(j % 3 == 2) {
				this.addArc(currentNode.getId(), this.getNoeud(m_ids[i - 2][j - 2]).getId());
				this.addArc(currentNode.getId(), this.getNoeud(m_ids[i - 2][j - 1]).getId());
				this.addArc(currentNode.getId(), this.getNoeud(m_ids[i - 1][j - 2]).getId());
				this.addArc(currentNode.getId(), this.getNoeud(m_ids[i - 1][j - 1]).getId());
			}
		}
	}
	
	/**
	 * Generate a random sudoku with random colors in each block
	 */
	
	public void setRandomSudoku()
	{
		ArrayList<Node> nodes = Color.transformToArrayList(getNoeuds_hm());
		Color.resetColors(nodes);
		
		addRandomColor(0, 2, 0, 2);
		addRandomColor(0, 2, 3, 5);
		addRandomColor(0, 2, 6, 8);
		
		addRandomColor(3, 5, 0, 2);
		addRandomColor(3, 5, 3, 5);
		addRandomColor(3, 5, 6, 8);
		
		addRandomColor(6, 8, 0, 2);
		addRandomColor(6, 8, 3, 5);
		addRandomColor(6, 8, 6, 8);
		
	}
	
	/**
	 * Add a random color in the matrix id_colors between min_row and max_row and between min_col and max_col
	 * @param min_row
	 * @param max_row
	 * @param min_col
	 * @param max_col
	 */
	
	private void addRandomColor(int min_row, int max_row, int min_col, int max_col)
	{
		int Min = 2;
		int Max = 4;
		int nbColorToAdd = Min + (int)(Math.random() * ((Max - Min) + 1));
		int nbColorAdded = 0;
		while(nbColorAdded != nbColorToAdd) {
			
			for(int i = min_row; i <= max_row; i++)
			{
				for(int j = min_col; j <= max_col; j++)
				{
					Min = 0;
					Max = 1;
					int addColor = Min + (int)(Math.random() * ((Max - Min) + 1));
					
					if(addColor != 0)
					{
						if(m_colors[i][j] == 0 && nbColorAdded != nbColorToAdd)
						{
							//Getting node to color
							Node node = this.getNoeud(m_ids[i][j]);
							
							//Getting colors of each adjacent node
							ArrayList<Integer> colors = new ArrayList<>();
							for (Arc arc : node.getSucc()) {
								Node target = arc.getTarget();
								if (!colors.contains(target.getColor()) && target.getColor() != 0)
									colors.add(target.getColor());
							}
							int alpha = 1;
							while (colors.contains(alpha)) {
								alpha++;
							}
							
							//We generate a random color
							int color = alpha + (int)(Math.random() * ((9 - alpha) + 1));
							
							//We verify if this color can colored for this node. Otherwise, we generate a new color
							while(colors.contains(color))
								color = alpha + (int)(Math.random() * ((9 - alpha) + 1));
							
							//Set this node as start color
							node.setStartColor(true);
							
							//Color the node
							node.setColor(color);
							
							nbColorAdded++;
							
							//Add the new color to the sudoku matrix
							m_colors[i][j] = color;
						}
							
					}
					
				}
			}
		}
		
	}
	
	/**
	 * Used to resolve sudoku with simulated annealing algorithm
	 * @param initTemp
	 * @param minLimitTemp
	 * @param alpha
	 * @param itermax
	 * @param maxTconst
	 */
	
	public void resolveSA(double initTemp, double minLimitTemp, double alpha, double itermax, double maxTconst)
	{	
		
		System.out.println("\n--- BEFORE ---");
		displaySudoku();
		
		simulatedAnnealingResolution(initTemp, minLimitTemp, alpha, itermax, maxTconst);
		
	}
	
	/**
	 * Apply SA resolution
	 * @param initTemp
	 * @param minLimitTemp
	 * @param alpha
	 * @param itermax
	 * @param maxTconst
	 */
	
	private void simulatedAnnealingResolution(double initTemp, double minLimitTemp, double alpha, double itermax, double maxTconst)
	{
		System.out.println("\nSolving....");
		int m = 0;
		double startTime = System.currentTimeMillis();
		boolean noFound = false;
		
		//While SA hasn't found a chromatic number equal to 9
		while(m != 9) {
			
			//If resolving last more than 1 minute, the program stop
			if(System.currentTimeMillis() - startTime > 60000) {
				noFound = true;
				break;
			}
			
			//Call SA 
			m = Color.simulatedAnnealing(
					this, 
					initTemp, 
					minLimitTemp, 
					alpha, 
					itermax, 
					maxTconst, 
					TypePath.normal, 
					TypeDisplay.hidden);	
			
			
			
		}
		
		//If SA found a solution
		if(!noFound) {
			
			//Then we update m_colors
			updateSudoku();
			
			//Display solution found
			System.out.println("--- AFTER ---");
			displaySudoku();
			System.out.println("Solving with simulated annealing finished");
		}
		else 
			System.out.println("No solutions found, resolution took too much time");
	}
	
	/**
	 * Display id matrix
	 */
	
	public void displayIdTable()
	{
		System.out.println("\n--------------- ID TABLE -----------------\n");
		String str = "";
		int row = 0;
		int col = 0;
		for(int i = 0; i < m_ids.length; i++)
		{
			row++;
			for(int j = 0; j < m_ids[0].length; j++)
			{
				col++;
				
				if(m_ids[i][j] < 10)
					str += " ";
				
				str += " " + m_ids[i][j] + " ";
				if(col == 3) {
					str += "|";
					col = 0;
				}
				
			}
			str += "\n";
			
			if(row == 3) {
				str += "_______________________________________\n\n";
				row = 0;
			}
			
		}
		
		System.out.println(str);
	}
	
	/**
	 * Display the sudoku
	 */
	
	public void displaySudoku()
	{
		System.out.println("\n--------------- SUDOKU -----------------\n");
		
		String str = "";
		int row = 0;
		int col = 0;
		for(int i = 0; i < m_colors.length; i++)
		{
			row++;
			for(int j = 0; j < m_colors[0].length; j++)
			{
				col++;
				
				str += " " + m_colors[i][j] + " ";
				if(col == 3) {
					str += "|";
					col = 0;
				}
				
			}
			str += "\n";
			
			if(row == 3) {
				str += "_______________________________________\n\n";
				row = 0;
			}
			
		}
		System.out.println(str);
	}
	
	/**
	 * Set the sudoku with colors from the matrix m 
	 * @param m
	 */
	
	private void setSudoku(int[][] m)
	{
		for(int i = 0; i < 9; i++)
		{
			for(int j = 0; j < 9; j++)
			{
				Node node = this.getNoeud(m_ids[i][j]);

				m_colors[i][j] = m[i][j];
				node.setColor(m[i][j]);
				
				if(m[i][j] != 0)
					node.setStartColor(true);
			}
		}
	}
	
}
