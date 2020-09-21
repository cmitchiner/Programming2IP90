// Cellulitis
// Homework Assignment 3 2ip90
/**
 * Charles Mitchiner (ID 1574531)
 * and Samir Saidi (ID 1548735)
 * as group number 97
 * Date: 17th September 2020
 */

import java.util.Scanner;

//JUST A HEADS UP, we adjusted A & B to be called through our Universal Method
//As instructed we left our code for when A & B were called indiviudally in our code, look for **OLD CODE** or NEVER CALLED
//Those two key words indicate that we no longer use that code.

class Cellulitis {
  //GLOBAL VARIABLES
  Scanner scanner = new Scanner(System.in);
  int rowLength; //Global variable for length of each row (remeber this is 2 less than the true length)
  int numOfGens; //Global variable for the amount of generations we need to compute

  boolean[] rulesetA = {false, true, false, true, true, true, true, false}; //Rulset for A so it can be used in the Universal Method
  boolean[] rulesetB = {false, true, true, false, true, false, true, false}; //Rulset for B so it can be used in the Universal Method

  int[] ruleSetUInt = new int[8]; //To input custom rule sets as 1's and 0's
  boolean[] ruleSetU = new boolean[8]; //A boolean array to put the converted 1's and 0's to T/F

  void run() { //Main method, handles importing and method calls
    //LOCAL VARIABLES to method *RUN*
    Character whichAuto = Character.toLowerCase(scanner.next().charAt(0)); //Letter A, B, or U: Specify which rules to follow
    rowLength = scanner.nextInt(); //L : Length for boolean array without additional padding on front and end, i.e our full array length is rowlength + 2
    boolean[] currentGeneration = new boolean[rowLength + 2]; //Declare boolean array with two extra indicies
    currentGeneration[0] = false; //Set first index to false 
    currentGeneration[rowLength + 1] = false; //Set last index to false
    numOfGens = scanner.nextInt(); //G: number of generations
    String initStart = scanner.next(); //Why is this here, when we can use .hasNextInt()
    int initalOccupationIndex; //used for storing the initally occupied indicies

    while (scanner.hasNextInt()) { //Fills currentGeneration with inital occupied cells
      initalOccupationIndex = scanner.nextInt();
      if ((initalOccupationIndex <= rowLength) && (initalOccupationIndex != 0)) {
        currentGeneration[initalOccupationIndex] = true;
      }
    }

    String initEnd = scanner.next(); //Why is this here, when we can use .hasNextInt()

    if (whichAuto == 'a') {
      ruleSetU = rulesetA; //Set the Universal rule set to follow the rules for Ruleset A
      //nextGenerationA(currentGeneration); **OLD CODE**
      nextGenerationU(currentGeneration); //Call method for auto U with rules for A
    }
    else if (whichAuto == 'b') {
      ruleSetU = rulesetB; //Set the Universal rule set to follow the rules for Ruleset B
      //nextGenerationB(currentGeneration); **OLD CODE**
      nextGenerationU(currentGeneration); //Call method for auto U with rules for B
    }
    else if (whichAuto == 'u') { //If its auto U, follow custom imported ruleset
      for(int count = 0; count < 8; count++) { //Inputs 1's and 0's from custom ruleset into int array
        ruleSetUInt[count] = scanner.nextInt();
      }
      for(int count = 0; count < 8; count++) { //Converts 1's and 0's to True and False
      if (ruleSetUInt[count] == 1) {          //and stores in boolean array
        ruleSetU[count] = true;
      } else {
        ruleSetU[count] = false;
      }
    }
      nextGenerationU(currentGeneration); //Call method for auto U
    }
    System.out.println();
  }

  void draw(boolean[] generation) { //Draws the state of provided generation
    for (int i = 1; i <= rowLength; i++) {
        if (generation[i] == false) {
            System.out.print(" "); //Space if cell is empty
        } else {
            System.out.print("*"); //Asterisk if cell is occupied
        }
    }
  }
  //**OLD CODE** BEFORE ADJUSTING EVERYTHING TO USE THE UNIVERSAL RULE METHOD
  //NEVER CALLED
  boolean rulesAutomataA(boolean a, boolean b, boolean c) { //Define ruleset for Auto A
    if (a == true && b == true && c == true) { // 1 1 1
      return false;
    }
    else if (a == true && b == true && c == false) { // 1 1 0
      return true;
    }
    else if (a == false && b == true && c == true) { // 0 1 1
      return true;
    }
    else if (a == false && b == true && c == false) { // 0 1 0
      return false;
    }
    else if (a == true && b == false && c == true) { // 1 0 1
      return true;
    }
    else if (a == true && b == false && c == false) { // 1 0 0 
      return true;
    }
    else if (a == false && b == false && c == true) { // 0 0 1
      return true;
    }
    else if (a == false && b == false && c == false) { // 0 0 0
      return false;
    }
    return false;
  }
  //**OLD CODE** BEFORE ADJUSTING EVERYTHING TO USE THE UNIVERSAL RULE METHOD
  //NEVER CALLED
  boolean rulesAutomataB(boolean a, boolean b, boolean c) { //Define ruleset for Auto B
    if (a == true && b == true && c == true) { // 1 1 1
      return false;
    }
    else if (a == true && b == true && c == false) { // 1 1 0
      return true;
    }
    else if (a == false && b == true && c == true) { // 0 1 1
      return false;
    }
    else if (a == false && b == true && c == false) { // 0 1 0
      return true;
    }
    else if (a == true && b == false && c == true) { // 1 0 1
      return false;
    }
    else if (a == true && b == false && c == false) { // 1 0 0
      return true;
    }
    else if (a == false && b == false && c == true) { // 0 0 1
      return true;
    }
    else if (a == false && b == false && c == false) { // 0 0 0
      return false;
    }
    return false;
  }

  boolean rulesAutomataU(boolean a, boolean b, boolean c) { //Define ruleset from inputed boolean array
    //ruleSetU is the boolean array we filled from the inputed rules in our run method
    if (a == false && b == false && c == false) { // 0 0 0
      return ruleSetU[0];
    }
    else if (a == false && b == false && c == true) { // 0 0 1
      return ruleSetU[1];
    }
    else if (a == false && b == true && c == false) { // 0 1 0
      return ruleSetU[2];
    }
    else if (a == false && b == true && c == true) { // 0 1 1
      return ruleSetU[3];
    }
    else if (a == true && b == false && c == false) { // 1 0 0
      return ruleSetU[4];
    }
    else if (a == true && b == false && c == true) { // 1 0 1
      return ruleSetU[5];
    }
    else if (a == true && b == true && c == false) { // 1 1 0
      return ruleSetU[6];
    }
    else if (a == true && b == true && c == true) { // 1 1 1
      return ruleSetU[7];
    }
    return false;
  }

  //Method that handles checking a cells neighbors and moves them according to the rules for A, B, or U
  boolean[] generate(boolean[] generation) {
    boolean[] newArray = new boolean[rowLength + 2]; //Create a second array so we can reference prev generation

    //if (autoLetter == 'u'){ **OLD CODE** PREVIOUSLY WE HAD A 2nd PARAMTER - autoLetter - IN THE GENERATE FUNCTION FOR A B & U, NOW WE DONT NEED THAT

      for (int j = 1; j <= rowLength; j++) {
          if (generation[j] == true) {
            try { //Prevent our program from trying to accsess an index out of bounds
              newArray[j-1] = rulesAutomataU(generation[j-2], generation[j-1], generation[j]); //Check & Adjust the left cells neighbors
            } catch (ArrayIndexOutOfBoundsException e) {
              j++;
            }  
            try {
              newArray[j] =  rulesAutomataU(generation[j-1], generation[j], generation[j+1]); //Check & Adjust  the middle cells neighbors
            } catch (ArrayIndexOutOfBoundsException e) {
              j++;
            }
            try {
              newArray[j+1] = rulesAutomataU(generation[j], generation[j+1], generation[j+2]); //Check & Adjust the right cells neighbors
            } catch (ArrayIndexOutOfBoundsException e) {
              j++;
            }
          }
      }
      return newArray; //Return array so our nextGenerationU function has access to our newly modified array
      
     //} **OLD CODE** was previously the closing bracket for the if statement
         

      //**OLD CODE** BEFORE ADJUSTING EVERYTHING TO USE THE UNIVERSAL RULE METHOD
    /*else if (autoLetter == 'b') { //PREVIOUSLY WE HAD A 2nd PARAMTER - autoLetter - IN THE GENERATE FUNCTION FOR A B & U, NOW WE DONT NEED THAT
      for (int j = 1; j <= rowLength; j++) {
          if (generation[j] == true) {
            try { //Prevent our program from trying to accsess an index out of bounds
              newArray[j-1] = rulesAutomataU(generation[j-2], generation[j-1], generation[j]); //Check & Adjust the left cells neighbors
            } catch (ArrayIndexOutOfBoundsException e) {
              j++;
            }  
            try {
              newArray[j] =  rulesAutomataU(generation[j-1], generation[j], generation[j+1]); //Check & Adjust  the middle cells neighbors
            } catch (ArrayIndexOutOfBoundsException e) {
              j++;
            }
            try {
              newArray[j+1] = rulesAutomataU(generation[j], generation[j+1], generation[j+2]); //Check & Adjust the right cells neighbors
            } catch (ArrayIndexOutOfBoundsException e) {
              j++;
            }
          }
      }
      return newArray; //Return array so our nextGenerationB function has access to our newly modified array
    }
    else if (autoLetter == 'a') { /PREVIOUSLY WE HAD A 2nd PARAMTER - autoLetter - IN THE GENERATE FUNCTION FOR A B & U, NOW WE DONT NEED THAT
      for (int j = 1; j <= rowLength; j++) {
          if (generation[j] == true) {
            try { //Prevent our program from trying to accsess an index out of bounds
              newArray[j-1] = rulesAutomataU(generation[j-2], generation[j-1], generation[j]); //Check & Adjust the left cells neighbors
            } catch (ArrayIndexOutOfBoundsException e) {
              j++;
            }  
            try {
              newArray[j] =  rulesAutomataU(generation[j-1], generation[j], generation[j+1]); //Check & Adjust  the middle cells neighbors
            } catch (ArrayIndexOutOfBoundsException e) {
              j++;
            }
            try {
              newArray[j+1] = rulesAutomataU(generation[j], generation[j+1], generation[j+2]); //Check & Adjust the right cells neighbors
            } catch (ArrayIndexOutOfBoundsException e) {
              j++;
            }
          }
      }
      return newArray; //Return array so our nextGenerationA function has access to our newly modified array
    }*/
    } //END OF GENERATE FUNCTION

    void nextGenerationU(boolean[] generation) {
      //Calculates the next generation from the provided generation according to
      //Imported rules for automaton U
      draw(generation); //Print First generation, as this is a given
      for (int i = 1; i < numOfGens; i++) { //Loop runs for as many generations as specified by user
          generation = generate(generation); //Call generate function to adjust each generation according to rules
          System.out.println(); //Make sure the next generation is on a new line
          draw(generation); //Finnaly print our newly adjusted generation
      }
    }

    //**OLD CODE** BEFORE ADJUSTING EVERYTHING TO USE THE UNIVERSAL RULE METHOD
    //NEVER CALLED
  void nextGenerationA(boolean[] generation) {
    //Calculates the next generation from the provided generation according to
    //Rules for automaton A
    draw(generation); //Print First line
    for (int i = 1; i < numOfGens; i++){
        generation = generate(generation/*, 'a'*/); //Call generate function to adjust each generation according to rules
        System.out.println(); //Make sure the next generation is on a new line
        draw(generation); //Finnaly print our newly adjusted generation
    }
  }
  
  //**OLD CODE** BEFORE ADJUSTING EVERYTHING TO USE THE UNIVERSAL RULE METHOD
  //NEVER CALLED
  void nextGenerationB(boolean[] generation) {
    //Calculates the next generation from the provided generation according to
    //Rules for automaton B
    draw(generation); //Print First line
    for (int i = 1; i < numOfGens; i++) {
        generation = generate(generation/*, 'b'*/); //Call generate function to adjust each generation according to rules
        System.out.println(); //Make sure the next generation is on a new line
        draw(generation); //Finnaly print our newly adjusted generation
    }
  }
  public static void main(String[] args) { 
    new Cellulitis().run();
  }
}
