package classes;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class Log {

	private static long startTime;
	private static long elapsedTime;
	

	
	public static void startClock()
	{
		startTime = System.nanoTime();
	}
	
	public static void endClock()
	{
		long endTime = System.nanoTime();
		elapsedTime = endTime - startTime;
	}
	
	
	public static void ErdosToCSV(String s, int one, int two) 
	{
		   FileWriter fileWriter = null;
		try {
			fileWriter = new FileWriter("LogErdos.csv", true);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		   BufferedWriter bw = new BufferedWriter(fileWriter);
		   PrintWriter printWriter = new PrintWriter(bw);
		   
		   //paramètre du graphe
		   switch (s)
		   {
			   case "taboo":
			   {
				   printWriter.println("Taboo,"+
						   one + ","  + two + "," + elapsedTime);
				   break;
			   }
			   case "backtracking":
			   {
				   printWriter.println("Backtracking,"+
						   one + ","  + two + "," +  elapsedTime);
				   break;
			   }
			   case "simulatedAnnealing":
			   {
				   printWriter.println("Simulated," +
						   one + ","  + two + "," +  elapsedTime);
				   break;
			   }
			   case "Dsatur":
			   {
				   printWriter.println("DSatur,"+
						   one + ","  + two + "," + elapsedTime);
				   break;
			   }
			   case "sequential":
			   {
				   printWriter.println("Sequential,"+
						   one + ","  + two + "," + elapsedTime);
				   break;
			   }
			   default:
			   {
				   
			   };
		   }
		   printWriter.close();
	}

	public static void GilbertToCSV(String s, int one, double two, int chroma, double s1, double s2, double s3, double s4) throws IOException 
	{
		
		   FileWriter fileWriter = new FileWriter("LogGilbert.csv", true);
		   BufferedWriter bw = new BufferedWriter(fileWriter);
		   PrintWriter printWriter = new PrintWriter(bw);
		   
		   //paramètre du graphe
		   switch (s)
		   {
			   case "taboo":
			   {
				   printWriter.println("Taboo," +
						   one + ","  + two + "," + chroma + "," + elapsedTime);
				   break;
			   }
			   case "backtracking":
			   {
				   printWriter.println("Backtracking"+ 
						   one + ","  + two + "," + chroma + "," + elapsedTime);
				   break;
			   }
			   case "simulatedAnnealing":
			   {
				   printWriter.println("Simulated"+ "," + 
						   one + ","  + two + "," + chroma + "," + elapsedTime / 1000000);
				   //printWriter.println("Simulated"+ "," + 
					//	   chroma + "," + elapsedTime / 1000000);
				   break;
			   }
			   case "Dsatur":
			   {
				   printWriter.println("DSatur,"+
						   one + ","  + two + "," + chroma + "," + elapsedTime);
				   break;
			   }
			   case "sequential":
			   {
				   printWriter.println("sequential,"+
						   one + ","  + two + "," +chroma + "," + elapsedTime);
				   break;
			   }
			   default:
			   {
				   
			   };
		   }
		   printWriter.close();
	}	
	
	public static void simulated(int nb_Node, double proba, int chroma, double s1, double s2, double s3, double s4) throws IOException
	{
		 FileWriter fileWriter = new FileWriter("simulatedAnealing.csv", true);
		  BufferedWriter bw = new BufferedWriter(fileWriter);
		  PrintWriter printWriter = new PrintWriter(bw);
		
		printWriter.println("Simulated"+ "," + 
				nb_Node + ","  + proba + "," + chroma + "," + s1 + "," + s2 + "," + s3 + "," + s4 + "," + elapsedTime / 1000000);
		printWriter.close();
	}
	
	public static void nodeArraytoCSV(String s, int nbNode,double two, int omeg, String func) throws IOException 
	{
		
		   FileWriter fileWriter = new FileWriter("LogGilbert.csv", true);
		   BufferedWriter bw = new BufferedWriter(fileWriter);
		   PrintWriter printWriter = new PrintWriter(bw);
		   
		   //paramètre du graphe
		   switch (s)
		   {
			   case "taboo":
			   {
				  printWriter.println("Taboo" + "," + func +
						   "," + nbNode +"," + two + "," + omeg +"," + elapsedTime/ 1000000); 
				   //printWriter.println("Taboo" + "," + func +
					//	  "," + omeg +"," + elapsedTime/ 1000000);
				   break;
			   }
			   
			   case "Dsatur":
			   {
				   printWriter.println("DSatur"+ "," + func +
						    "," + nbNode +"," + two + "," +  omeg +"," + elapsedTime/ 1000000); 
				  // printWriter.println("DSatur"+ "," + func +
					//	    "," +  omeg +"," + elapsedTime/ 1000000);
				   break;
			   }
			   case "sequential":
			   {
				   printWriter.println("sequential"+ "," + func + 
						   "," + nbNode +","+ two + "," +omeg +"," +elapsedTime / 1000000); 
				   //printWriter.println("sequential"+ "," + func + 
						 //  "," +omeg +"," +elapsedTime / 1000000);
				   break;
			   }
			   default:
			   {
				   
			   };
		   }
		   printWriter.close();
	}	
	
	
	
	
	
	
	
}
