// Hangman TEMPLATE
// Homework Assignment 2 2ip90 
/**
 * @name(s) //TODO
 * @id(s)   //TODO
 * @group   //TODO
 * @date    //TODO
 */

import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

class Hangman 
{
  
  void play()
  {
    //Intialize Local Variables
    Scanner scanner = new Scanner(System.in);
    boolean solved = false, rightOrWrong = false;
    Character guessChar;
    int tries = 0;
    ArrayList<Character> guessedLetters = new ArrayList<Character>();

    //Secret words
    String[] bagOfWords = new String[]{"the", "walrus", "and", "carpenter", "were", "walking", "close", "at", "hand"};
    
    //Initialize the random number generator
    System.out.println("Type an arbitrary number");
    long seed = scanner.nextInt();
    Random random = new Random(seed);
    
    //Select a secret word
    String secret = bagOfWords[random.nextInt(bagOfWords.length)];

    // TODO Insert your code here

    //Set an array of character guesses to compare against secret
    char[] guessString = new char[secret.length()];

    //Fill character array with underscores to represent unsolved characters
    for (int i = 0; i < guessString.length; i++)
    {
      guessString[i] = '_';
    }
    //Show user how long the secret word is
    for (int i = 0; i < secret.length(); i++)
    {
      System.out.print("_");
    }

    while (!solved)
    {
      System.out.print("\n");
      guessChar = scanner.next().charAt(0); 
      rightOrWrong = false;
      for (int i = 0; i < secret.length(); i++)
      {
        if (secret.charAt(i) == guessChar)
        {
          if (guessedLetters.get(guessedLetters.indexOf(guessChar)))
          {
            
          }
          //Store correct characters in a string
          guessString[i] = guessChar;
          System.out.print(guessString[i]);
          rightOrWrong = true;
          guessedLetters.add(guessChar);
        }
        else
        {
          System.out.print(guessString[i]); //This prints a _ in the index that doesnt match the guess
        }
      }
      if (!rightOrWrong)
      {
        tries++;
      }
      String complete = new String(guessString);
      if (complete.equalsIgnoreCase(secret))
      {
        solved = true;
        System.out.println("\nWell done, you won!");
      }
      if (tries >= 10)
      {
        solved = true;
        System.out.println("\nUnlucky, you lost!");
        System.out.print("The secret word was: " + secret);
      }
    }
    scanner.close();
  }
  
  public static void main(String[] args) { 
    new Hangman().play();
  }
}
