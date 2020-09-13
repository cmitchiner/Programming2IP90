// Hangman
// Homework Assignment 2 2ip90 
/**
 * Charles Mitchiner (ID 1574531)
 * and Samir Saidi (ID 1548735)
 * as group number 97
 * Date: 10th September 2020
 */

import java.util.Random;
import java.util.Scanner;
import java.util.*;

class Hangman {
  
  void play() {
    // Intialize Local Variables
    Scanner scanner = new Scanner(System.in);
    boolean solved = false, correctAnswer = true; // Solved Determines whether secret has been found //correctAnswer to see if a guessed letter is correct
    Character guessChar; // Character varible to input each guessed character
    int failedTries = 0; // Counter for failed tries
    ArrayList<Character> repeatedCorrectTries = new ArrayList<Character>(); // EXTRA CREDIT: initializes ArrayList of correctly guessed letters

    // Secret words
    String[] bagOfWords = new String[]{"the", "walrus", "and", "carpenter", "were", "walking", "close", "at", "hand"};
    
    // Initialize the random number generator
    System.out.println("Type an arbitrary number");
    long seed = scanner.nextInt();
    Random random = new Random(seed);

    // Select a secret word
    String secret = bagOfWords[random.nextInt(bagOfWords.length)];

    // Set an array of character guesses to compare against secret
    char[] allCorrectlyGuessedLetters = new char[secret.length()];

    // Fill character array with underscores to represent unsolved characters
    for (int i = 0; i < allCorrectlyGuessedLetters.length; i++) {
      allCorrectlyGuessedLetters[i] = '_';
    }

    // Show user how long the secret word is
    for (int i = 0; i < secret.length(); i++) {
      System.out.print("_");
    }

    // Loop Runs until you run out of guesses or solve the puzzle
    while (!solved) {
      System.out.print("\n");

      
      guessChar = Character.toLowerCase(scanner.next().charAt(0));  // Input user's guessed character as a lowercase character
      
      if (repeatedCorrectTries.contains(guessChar)) { // EXTRA CREDIT: if this letter *has* already been guessed...
        failedTries++; // EXTRA CREDIT: ...increments number of failed tries
      }
      correctAnswer = false; // Reset boolean for knowing if a character is in secret or not

      if (failedTries == 9 && (!secret.contains(guessChar.toString()))){ //Prevents forloop from printing an additional line, if game ends
      } else {                                                           //For mometer purposes
        for (int i = 0; i < secret.length(); i++) {
          if (secret.charAt(i) == guessChar) { // If the guessed character equals one of the characters in secret
            allCorrectlyGuessedLetters[i] = guessChar; // Add the correct guessed character to our correct guessed string
            System.out.print(allCorrectlyGuessedLetters[i]); // Print out the correct letter inbetween underscores
            correctAnswer = true; // Indicate it was a correct guess

            if (!repeatedCorrectTries.contains(guessChar)) { // EXTRA CREDIT: if this letter hasn't already been guessed...
              repeatedCorrectTries.add(guessChar); // EXTRA CREDIT: ...adds it to the list of guessed characters
            }
          } else {
            System.out.print(allCorrectlyGuessedLetters[i]); // This prints a _ in any index that doesnt match the guessed Character and that isnt already guessed
          }
        }
    }

      if (!correctAnswer) { // If it was an incorrect guess increment failedTries, remeber 10 failed tries means you loose
        failedTries++;
      }

      String convertCharArrayToString = new String(allCorrectlyGuessedLetters); // Change our array of characters into a string to compare against secret

      if (convertCharArrayToString.equalsIgnoreCase(secret)) { // If our guessedString matches secret, aka you won
        solved = true;
        System.out.println("\nWell done, you won!");
      }
      if (failedTries >= 10) { // If you have 10 failed tries exit the game
        solved = true;
        System.out.println("Unlucky, you lost!");
        System.out.print("The secret word was: " + secret + "\n");
        return;
      }
    }
  }
  
  public static void main(String[] args) { 
    new Hangman().play();
  }
}