// Cellulitis TEMPLATE
// Homework Assignment 3 2ip90 
/**
 * @name(s) //TODO
 * @id(s)   //TODO
 * @group   //TODO
 * @date    //TODO
 */

import java.util.Scanner;

class Cellulitis {
  Scanner scanner = new Scanner(System.in);
  int rowLength;
  int numOfGens;
  void run(){

    Character whichAuto = Character.toLowerCase(scanner.next().charAt(0)); //Letter A, B, or U: Specify which rules to follow
    rowLength = scanner.nextInt(); //L : Length for boolean array without additional padding on front and end, i.e our full array length is rowlength + 2
    boolean[] currentGeneration = new boolean[rowLength + 2]; //Declare boolean array with two extra indicies
    currentGeneration[0] = false; //Set first index to false 
    currentGeneration[rowLength + 1] = false; //Set last index to false
    numOfGens = scanner.nextInt(); //G: number of generations
    String initStart = scanner.next(); //I have no reason what to use this for because of the hasNextInt function in scanner works way better and way easier and this assignment sucks ass
    int initalOccupationIndex; //used for storing the initally occupied indicies

    while (scanner.hasNextInt())//Fills currentGeneration with inital occupied cells
    {
      initalOccupationIndex = scanner.nextInt();
      if ((initalOccupationIndex <= rowLength) && (initalOccupationIndex != 0))
      {
        currentGeneration[initalOccupationIndex] = true;
      }
    }
    String initEnd = scanner.next(); //I have no reason what to use this for because of the hasNextInt function in scanner works way better and way easier and this assignment sucks ass
    
    if (whichAuto == 'a')//If its auto A, follow rules for A
    {
      currentGeneration = nextGenerationA(currentGeneration);
    }
    if (whichAuto == 'b')//If its auto B, follow rules for B
    {
      currentGeneration = nextGenerationB(currentGeneration);
    }
    //draw(currentGeneration);//Print out the generations
    
	
  }
  void draw(boolean[] generation){ //Draws the state of provided generation
    for (int i = 1; i <= rowLength; i++)
    {
        if (generation[i] == false)
        {
            System.out.print(" ");
        }
        else
        {
            System.out.print("*");
        }
    }
  }
  boolean[] nextGenerationA(boolean[] generation){
    //Calculates the next generation from the provided generation according to
    //Rules for automaton A
    draw(generation); //First line
    //Change the generation array for the next row following rules for A
    
    return generation;
  }
  boolean[] nextGenerationB(boolean[] generation){
    //Calculates the next generation from the provided generation according to
    //Rules for automaton B
    return generation;
  }
  
  public static void main(String[] args) { 
    new Cellulitis().run();
  }
}
