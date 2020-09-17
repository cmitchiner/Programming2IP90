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
  
  void run(){

    Character whichAuto = Character.toLowerCase(scanner.next().charAt(0)); //Letter A, B, or U: Specify which rules to follow
    int rowLength = scanner.nextInt(); //L : Length for boolean array without additional padding on front and end, i.e our full array length is rowlength + 2
    boolean[] currentGeneration = new boolean[rowLength + 2]; //Declare boolean array with two extra indicies
    currentGeneration[0] = false; //Set first index to false 
    currentGeneration[rowLength + 1] = false; //Set last index to false
    int numOfGens = scanner.nextInt(); //G: number of generations
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
    
    for (int i = 0; i < rowLength + 2; i++)
    {
      System.out.println(currentGeneration[i]);
    } 
    if (whichAuto == 'a')
    {
      currentGeneration = nextGenerationA(currentGeneration);
    }
    if (whichAuto == 'b')
    {
      currentGeneration = nextGenerationB(currentGeneration);
    }
    draw(currentGeneration);
    
	
  }
  void draw(boolean[] generation){ //Draws the state of provided generation

  }
  boolean[] nextGenerationA(boolean[] generation){
    //Calculates the next generation from the provided generation according to
    //Rules for automaton A
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
