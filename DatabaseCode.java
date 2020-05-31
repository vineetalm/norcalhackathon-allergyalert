package databaseCode;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

public class DatabaseCode {
	
	static String[][] products = new String[30958][2]; //From the database, there are around 40,000 entries which is why this is the length 
	static int productcount;
	
	public static boolean checkItem(String category, String item) {
		
		String wordSplit = " "; 
		
		String[] itemWords = item.split(wordSplit);
		
		for (int i = 0; i < itemWords.length; i++) {
			itemWords[i]=itemWords[i].toLowerCase();
		}
		
		String productLower = category.toLowerCase();
		
		for (int i = 0; i < itemWords.length; i++) {
			if(!productLower.contains(itemWords[i])) return false;	
		}
		
		return true;
	}
	
	public static boolean checkAllergen(String productIngredients, String allergen) {
		String wordSplit = " "; 
		
		String[] allergenWords = allergen.split(wordSplit);
		
		for (int i = 0; i < allergenWords.length; i++) {
			allergenWords[i] = allergenWords[i].toLowerCase();
		}
		
		productIngredients = productIngredients.toLowerCase();
		
		for (int i = 0; i < allergenWords.length; i++) {
			if(productIngredients.contains(allergenWords[i])) return false;	
		}
		
		return true;
		
	}
	
	public static void readItemsFile() throws IOException {
		String csvfile = "src/databaseCode/Food-Allergy-Database.txt";
		String line = " ";
		String csvSplit = "\t";
		
		BufferedReader objreader = null;
		
		objreader = new BufferedReader(new FileReader(csvfile));
		
		int i = 0;
		
		while ((line = objreader.readLine()) != null) {
			
			String[] productItem = line.split(csvSplit);
			
			String productCategory = productItem[0].replaceAll("\"", "");
			String productIngredients = productItem[1].replaceAll("\"", "");
			
			products[i][0] = productCategory;
			products[i][1] = productIngredients;
			
			i++;
		}
		
		productcount = i;
		
		System.out.println("Number of products : " + productcount);
	}

	public static void main(String[] args) throws IOException {	
		
		readItemsFile();
		
		Scanner scan = new Scanner(System.in);
		
		boolean Again = true;
		
		do {
		
		System.out.println("What is the item");
		String item = scan.nextLine();
		
		System.out.println("What is your allergen");
		String allergen = scan.nextLine();
		
		int matchedProducts = 0;
		
		for (int i = 0; i < productcount ; i++) {
			String productCategory = products[i][0];
			String productIngredients = products[i][1];
			
			if (checkItem(productCategory, item) && checkAllergen(productIngredients, allergen)) {
				System.out.println("product: " + productCategory + " ingredients: " + productIngredients);;
				matchedProducts++;
			}
		}		
		
		System.out.println("This is the number of options you have: " + matchedProducts);
		
		System.out.println("Do you want to check another product?");
		String askAgain = scan.nextLine(); 
		
		if(askAgain.equals("No")) {
			Again = false;
		}
		
		} while (Again!=false);
		
		
	}
}
