 /**
 * CS 331
 * Professor: Tannaz Rezaei
 *
 * Project #2 - Task # 1
 * 
 * Description: program the greedy approach to the fractional knapsack problem.
 * 1. ask user for knapsack capacity
 * 2. ask user for n objects weights and profits or read an input.txt that contains n objects
 * 3. list objects that maximmize profit and show maximimum profit as output considering capacity.
 * 
 * @author - Eric Schenck
 * last modified: january 25, 2018
 *   
 */

import java.util.Scanner;
import java.io.*;

public class FractionalKnapsack {
	
	
	int capacity;											// used to store given capacity
	
	int userChoice = 0;											// used to store user choice for entry or file input
	
	int n;														// number of items to be looked at. user entered or at top of input.txt
	int weight;
	double value;												// used to temporarily store before creating Item
	double valueInSac;											// price of items in the knapsack
	boolean runAgain = true;											// boolean to store user choice to run program again
	String itemFile = "C://Users//Eric//Desktop//input.txt";
	String finalItemList = "";										// used to store and print out a formated list of items chosen and weights 
	String userInput;
	Item[] onTheTable;											// creating an array of items to store whats "on the table"
	
	private void greedyKnapsack(){
		
		calculateTableRatios();									// calculating all table ratios
		
		sortBasedOnRatio();										// sorting Table Items based on price/weight ratio
		
		/* //used for testing
		for(int j = 0; j< n ; ++j){
			System.out.println(onTheTable[j].toString() + "; Ratio = " + onTheTable[j].ratio);
		}
		*/
		int i = 0;
		
		double tempWeight = onTheTable[i].weight ;
		
		 while(capacity > 0){									// once the bag is full we stop looking at items on table
			
			if(capacity - tempWeight < 0){
				tempWeight--;									// take one less unit of item and try again
			}else if(capacity - tempWeight == 0){
				capacity -= tempWeight;							// taking up space in knapsack
				
				finalItemList += onTheTable[i].toString() + "; Units taken: " + tempWeight 	// the list of chosen items
						+ "; Item Value: " + (tempWeight * onTheTable[i].ratio) + "\n";
				
				valueInSac += tempWeight * onTheTable[i].ratio;		// adding value of item and weight to total in knapsack for later printout
			}else{ 												// capacity absorbs item weight without becoming zero or less than zero
				capacity -= tempWeight;							// adjusting size of knapsack
				
				
				finalItemList += onTheTable[i].toString() + "; Units taken: " + tempWeight 	// the list of chosen items
						+ "; Item Value: " + (tempWeight * onTheTable[i].ratio) + "\n"	;
				

				valueInSac += tempWeight * onTheTable[i].ratio;		// adding value of item and weight to total in knapsack for later printout
				
				
				
				tempWeight = onTheTable[++i].weight;			// getting next item weight
				
			}
		}
		 
		System.out.print(finalItemList);
		System.out.println("Total Value in KnapSack: " + valueInSac);					// printout to console 
		
		
	}
	
	private String[] handleInitialSpaces(String[] splitUserSel){

		try{
			splitUserSel[0].charAt(0);			// handling issue of initial entered space
		}catch(Exception e){
		
			for(int i = 0; i < splitUserSel.length - 1 ; ++i){
				splitUserSel[i] = splitUserSel[i+1];
				splitUserSel[i+1] = null;
			}
		}
		return splitUserSel;
	}
	
	private void sortBasedOnRatio(){												// sort in descending order
		
		for(int i = 0; i < n ; ++i){												
			for(int j = 0; j < n ; ++j){
				
				if(onTheTable[i].ratio > onTheTable[j].ratio){						// sort in descending order
					Item tempItem = onTheTable[i];
					onTheTable[i] = onTheTable[j];									// swapping items
					onTheTable[j] = tempItem;
					
					
				}
				
				
				
			}
		}
		
		
	}
	

	private void calculateTableRatios(){
		for(int i = 0; i< n ; ++i){
			onTheTable[i].ratio = onTheTable[i].value / onTheTable[i].weight ; // finding ratio of each item
		}
	}
	
	private FractionalKnapsack() throws FileNotFoundException{
		
		
		
		
		
				
		
		Scanner keyboard = new Scanner(System.in);
			
			
		System.out.println("----------------------------------");	// good portion is user input
														// didnt want to deal with errors so i just exit if wrong input
			
		System.out.println("Enter the Capacity of the Knapsack");	// user prompt
		try{
			capacity = keyboard.nextInt();
				
		}catch(Exception e){
				
			System.out.println("Only enter an integer value next time...");
			System.exit(1); 										// exit program if issue occurs
		}															
			
		System.out.println("Manual Entry of Items - Enter 1\n"
				+ "Load input.txt File - Enter 2");
		try{
			userChoice = keyboard.nextInt();
				
		}catch(Exception e){
				
			System.out.println("Only enter an integer value next time...");
			System.exit(1); 										// exit program if issue occurs
		}	
									
		if(userChoice == 1){// manual entry of items
				
			System.out.println("Enter n number of items");
				
			try{
				n = keyboard.nextInt();
					
			}catch(Exception e){
					
				System.out.println("Only enter an integer value next time...");
				System.exit(1); 										// exit program if issue occurs
			}
				
			onTheTable = new Item[n];									// array will be size n 
				
			System.out.println("Enter a weight and value separated by space. No Mistakes please");
				
				
			for(int i = 0; i < n ; ++i){
					
				System.out.println("-----------");
				System.out.println("Item no. " + (i+1));				// Item no. to go with user entry
					
				Scanner stringKey = new Scanner(System.in);				// used for user input
					
				String tempStr = stringKey.nextLine();					// getting user input as a string
					
				String[] tempStrArray = tempStr.split("\\s+");			// making sense of blank space between input
					
				tempStrArray = handleInitialSpaces(tempStrArray);		// handling initial space issue
					
				try{
					weight = Integer.parseInt(tempStrArray[0]);			// parsing input into weight and value
					value = Double.parseDouble(tempStrArray[1]);
				}catch(Exception e){
					System.out.println("Next time enter two numerical values");
					System.exit(1);											// exit program
				}
					
				onTheTable[i] = new Item((i+1) , weight, value);		// adding given item to the table
					
				stringKey.close();
				keyboard.close();
			}
				
		}else if(userChoice == 2){// input.txt 
				
			File file = new File(itemFile);									// Open file
			Scanner inputFile = new Scanner(file);
				
			String tempN = inputFile.nextLine();
			n = Integer.parseInt(tempN);
				
			onTheTable = new Item[n];									// initializing array
				
			int i = 0;													// used to keep track of current index
				
			while(inputFile.hasNextLine()){
																			
				String tempStr = inputFile.nextLine();					// getting user input as a string
				String[] tempStrArray = tempStr.split("\\s+");			// making sense of blank space between input
					
				weight = Integer.parseInt(tempStrArray[0]);			// getting weight
				value = Double.parseDouble(tempStrArray[1]);
					
				onTheTable[i] = new Item(++i, weight, value);			// storing item info and increase value of i by 1
			}
				
			inputFile.close();											// closing resource
				
				
				
		}else{
			System.out.println("Only enter 1 or 2 next time...");
			System.exit(1);
		}
			
		greedyKnapsack();
			
		
	}
	
	
		
	public static void main(String[] args) throws FileNotFoundException{

		new FractionalKnapsack();
	
	}
	
	private class Item{											// item constructor
		
		protected int itemNumber;							// too keep track of what item is what for later printout
		protected double ratio;									// value/weight = ratio
		protected double weight;								// values being saved
		protected double value;
		
		public Item(int itemNumber, double weight, double value){				// constructor
			this.weight = weight;
			this.value = value;
			this.itemNumber = itemNumber;
		}
		
		@Override
		public String toString(){
			
			return ("Item Number: " + itemNumber + "; Ratio = " + ratio + "; value = " + value);
		}
		
	}
	
	
}
