package classes;

import java.util.ArrayList;
import types.TypeDisplay;
import types.TypePath;

public class Sudoku extends Graphe {

	private int[][] m_ids = new int[9][9];
	private int[][] m_colors = new int[9][9];
	private ArrayList<ArrayList<Node>> id_rows = new ArrayList<ArrayList<Node>>();
	private ArrayList<ArrayList<Node>> id_cols = new ArrayList<ArrayList<Node>>();
	
	public Sudoku()
	{
		for(int i = 1; i < 82; i++)
			this.addNoeud(i);
		
		setFullMatrix();
		generateRandomColors();
	}
	
	public Sudoku(int m[][])
	{
		
		for(int i = 1; i < 82; i++)
			this.addNoeud(i);
		
		setFullMatrix();
		setSudoku(m);
	}
	
	private void setFullMatrix()
	{
		int k = 1;
		for(int i = 0; i < 9; i++)
		{
			for(int j = 0; j < 9; j++)
			{
				m_ids[i][j] =  this.getNoeud(k).getId();
				m_colors[i][j] = this.getNoeud(k).getColor();
				k++;
			}
		}
		setConnectionRows();
		setConnectionCols();
		setConnectionBlocks();
	}
	
	
	private void setConnectionRows()
	{
		for(int i = 0; i < 9; i++)
		{
			ArrayList<Node> row = new ArrayList<Node>();
			for(int j = 0; j < 9; j++)
			{
				if( j + 1 < 9)
				{
					Node firstNode = this.getNoeud(m_ids[i][j]); 
					Node secondNode = this.getNoeud(m_ids[i][j + 1]);
					
					this.addArc( firstNode.getId(), secondNode.getId());
					
					if(!row.contains(firstNode))
						row.add(firstNode);
					
					if(!row.contains(secondNode))
						row.add(secondNode);
				
				}
			}
			this.id_rows.add(row);
		}
		
		for(ArrayList<Node> row : id_rows)
		{
			for(Node node : row)
			{
				for(Node tmpNode : row)
				{
					this.addArc(node.getId(), tmpNode.getId());
				}
			}
		}
	}
	
	private void setConnectionCols()
	{
		for(int j = 0; j < 9; j++)
		{
			ArrayList<Node> col = new ArrayList<Node>();
			for(int i = 0; i < 9; i++)
			{
				if( i + 1 < 9)
				{
					Node firstNode = this.getNoeud(m_ids[i][j]); 
					Node secondNode = this.getNoeud(m_ids[i + 1][j]);
				
					this.addArc( firstNode.getId(), secondNode.getId());
					
					if(!col.contains(firstNode))
						col.add(firstNode);
					
					if(!col.contains(secondNode))
						col.add(secondNode);
				
				}
			}
			this.id_cols.add(col);
		}
		
		for(ArrayList<Node> col : id_cols)
		{
			for(Node node : col)
			{
				for(Node tmpNode : col)
					this.addArc(node.getId(), tmpNode.getId());
			}
		}
		
	}
	
	private void setConnectionBlocks()
	{
		for(int i = 0; i < 9; i++)
		{
			for(int j = 0; j < 9; j++)
				addBlockConnection(i, j);
		}
	}
	
	private void updateSudoku()
	{

		for(int i = 0; i < 9; i++)
		{
			for(int j = 0; j < 9; j++)
				m_colors[i][j] = this.getNoeud(m_ids[i][j]).getColor();
		}
		
	}
	
	private void addBlockConnection(int i, int j)
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
	
	public void generateRandomColors()
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
	
	private void addRandomColor(int min_row, int max_row, int min_col, int max_col)
	{
		int Min = 1;
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
							Node node = this.getNoeud(m_ids[i][j]);
							
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
							
							
							int color = alpha + (int)(Math.random() * ((9 - alpha) + 1));
							
							while(colors.contains(color))
								color = alpha + (int)(Math.random() * ((9 - alpha) + 1));
							
							node.setStartColor(true);
							node.setColor(color);
							
							nbColorAdded++;
							m_colors[i][j] = color;
						}
							
					}
					
				}
			}
		}
		
	}
	
	public void resolveSA(double initTemp, double minLimitTemp, double alpha, double itermax, double maxTconst)
	{	
		
		System.out.println("\n--- BEFORE ---");
		displaySudoku();
		
		simulatedAnnealingResolution(initTemp, minLimitTemp, alpha, itermax, maxTconst);
		
	}
	
	private void simulatedAnnealingResolution(double initTemp, double minLimitTemp, double alpha, double itermax, double maxTconst)
	{
		System.out.println("\nSolving....");
		int m = 0;
		double startTime = System.currentTimeMillis();
		boolean noFound = false;
		while(m != 9) {
			
			if(System.currentTimeMillis() - startTime > 60000) {
				noFound = true;
				break;
			}
			
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
		
		if(!noFound) {
			updateSudoku();
			System.out.println("--- AFTER ---");
			displaySudoku();
			System.out.println("Solving with simulated annealing finished");
		}
		else 
			System.out.println("No solutions found, resolution took too much time");
	}
	
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
	
	public void setSudoku(int[][] m)
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
