/**
 * Class that solves the Asterisk Sudoku.
 * Prints the number of solutions of a Sudoku if there are multiple. If there is only a single solution, prints this solution instead.
 * Charles Mitchiner (ID 1574531)
 * and Samir Saidi (ID 1548735)
 * as group number 97
 * Date: 24th September 2020
 */
class SudokuSolver {
    static final int SUDOKU_SIZE = 9;          // Size of the grid.
    static final int SUDOKU_MIN_NUMBER = 1;    // Minimum digit to be filled in.
    static final int SUDOKU_MAX_NUMBER = 9;    // Maximum digit to be filled in.
    static final int SUDOKU_BOX_DIMENSION = 3; // Dimension of the boxes (sub-grids that should contain all digits).
    static final int  EMPTY = 0;                // If a cell is empty it equals 0
    
    int[][] grid = new int[][] {  // The puzzle grid; 0 represents empty.
        { 7, 0, 0,   4, 0, 0,    2, 0, 0 },    // One solution.
        { 9, 3, 0,   0, 0, 0,    0, 0, 0 },
        { 0, 0, 2,   0, 8, 0,    0, 0, 0 },

        { 4, 0, 0,   0, 0, 2,    0, 9, 0 },
        { 0, 0, 0,   0, 0, 0,    0, 0, 6 },
        { 0, 0, 1,   8, 0, 6,    0, 7, 0 },

        { 0, 0, 0,   0, 0, 3,    0, 0, 0 },
        { 0, 0, 0,   9, 0, 5,    0, 0, 0 },
        { 0, 0, 0,   0, 0, 0,    0, 4, 0 },
    };

    int solutionCounter = 0; // Solution counter

    // Is there a conflict when we fill in d at position (r, c)?
    boolean givesConflict(int r, int c, int d) {
        return !(!rowConflict(r, d) && !columnConflict(c, d) && !boxConflict(r, c, d) && !asteriskConflict(grid)); //If there isnt a conflict in any of the conditions, return false
        //False if no conflict, true if there is conflict
    }

    // Is there a conflict when we fill in d in row r?
    boolean rowConflict(int r, int d) {
        for (int i = SUDOKU_MIN_NUMBER - 1; i < SUDOKU_SIZE; i++) { //Check every column for the passed paramter index for row
            if (grid[r][i] == d) {     
                return true;
            }
        }
        return false;
    }

    // Is there a conflict in column c when we fill in d?
    boolean columnConflict(int c, int d) {
        for (int i = SUDOKU_MIN_NUMBER - 1; i < SUDOKU_SIZE; i++) {  //Check every row for the passed paramter index for col
            if (grid[i][c] == d){
                return true;
            }
        }
        return false;
    }

    // Is there a conflict in the box at (r, c) when we fill in d?
    boolean boxConflict(int r, int c, int d) {
        int row = r - (r % SUDOKU_BOX_DIMENSION);
        int col = c - (c % SUDOKU_BOX_DIMENSION);

        for (int i = row; i < row + SUDOKU_BOX_DIMENSION; i++) {
            for (int j = col; j < col + SUDOKU_BOX_DIMENSION; j++) {
                if (grid[i][j] == d){
                    return true;
                }
            }
        }
        return false;
    }
	
    // Is there a conflict in the asterisk when we fill in d?
    //Passes each asterix cell's indicies to the isAsteriskUnique function, with a boolean array to keep track
	boolean asteriskConflict(int[][]board)
    {
        int[] asteriskRowIndex = {2, 1, 2, 4, 4, 4, 6, 7, 6};
        int[] asteriskColIndex = {2, 4, 6, 1, 4, 7, 2, 4, 6};
        boolean[] constraint = new boolean[SUDOKU_SIZE]; //This is all falses at initilization
        for (int i = 0; i < SUDOKU_SIZE; i++) {
            if (!isAsteriskUnique(board, asteriskRowIndex[i], constraint, asteriskColIndex[i])) {
                return true;
            }
        }
        return false;
    }

    //Checks whether or not an integer has already appeared in any of the astrisk indicies, if it has set a boolean array index to false
    //otherwise if it is unique set that index to true
    //After all 9 indicies in the boolean array are true, every asterisk cell has a unique number 1-9
    boolean isAsteriskUnique(int[][] board, int row, boolean[] constraint, int column) {
        if (board[row][column] != EMPTY) {
            if (!constraint[board[row][column] - 1]) {
                constraint[board[row][column] - 1] = true;
            } else {
                return false;
            }
        }
        return true;
    }
    boolean isAnAsteriskIndex(int row, int col)
    {
        int[] asteriskRowIndex = {2, 1, 2, 4, 4, 4, 6, 7, 6};
        int[] asteriskColIndex = {2, 4, 6, 1, 4, 7, 2, 4, 6};
        for (int i = 0; i < 9; i++)
        {
            if (asteriskRowIndex[i] == row && asteriskColIndex[i] == col){
                return true;
            }
        }
        return false;
    }

    // Find all solutions for the grid, and stores the final solution.
    public int[][] board2 = new int[SUDOKU_SIZE][SUDOKU_SIZE];
    public int solve(int[][] board, int solutionCounter)
    {
        for (int row = 0; row < SUDOKU_SIZE; row++) { //Increment through rows

            for (int column = SUDOKU_MIN_NUMBER - 1; column < SUDOKU_SIZE; column++) { //Increment through columns

              if (board[row][column] == EMPTY) { 

                for (int num = SUDOKU_MIN_NUMBER; num <= SUDOKU_MAX_NUMBER; num++) { //Increment through possible number choices

                  if (!givesConflict(row, column, num)) { 

                    board[row][column] = num;                   //If no conflict, set that spot to num

                    int cache = solve(board, solutionCounter);  //Keep track of previous solutions

                    //Once we have found one solution, we must backtrack again but force one of our solved values to increment, then check for a solution
                    if (cache > solutionCounter) {
                        solutionCounter = cache;
                      for (int i = SUDOKU_MIN_NUMBER - 1; i < SUDOKU_SIZE; i++) {
                        for (int j = SUDOKU_MIN_NUMBER - 1; j < SUDOKU_SIZE; j++) {
                          if (board[i][j] != EMPTY) {
                            board2[i][j] = board[i][j];
                          }
                        }
                      }
                      board[row][column] = EMPTY;
                    } else {
                      board[row][column] = EMPTY;
                    }
                  }
                //   else if (!rowConflict(row, num) && !columnConflict(column, num) && !boxConflict(row, column, num) && !isAnAsteriskIndex(row, column)){
                //     board[row][column] = num;
                //     int cache = solve(board, solutionCounter);
                //     if (cache > solutionCounter) {
                //         //solutionCounter = cache;
                //       for (int i = SUDOKU_MIN_NUMBER - 1; i < SUDOKU_SIZE; i++) {
                //         for (int j = SUDOKU_MIN_NUMBER - 1; j < SUDOKU_SIZE; j++) {
                //           if (board[i][j] != EMPTY) {
                //             board2[i][j] = board[i][j];
                //           }
                //         }
                //       }
                //       board[row][column] = EMPTY;
                //     } else {
                //       board[row][column] = EMPTY;
                //     }
                //   }
                }
                return solutionCounter;
              }
            }
          }
          return solutionCounter + 1;
      }

    // Print the sudoku grid.
    void print(int[][] board) {
        System.out.println("+-----------------+"); //Top bar
        for (int i = SUDOKU_MIN_NUMBER - 1; i < SUDOKU_SIZE; i++) {
            
            if (i == 3 || i == 6){
                System.out.print("+-----------------+\n|"); //Two middle bars
            } else {
                System.out.print("|"); //Prionts first row of |
            }
            for (int j = SUDOKU_MIN_NUMBER - 1; j < SUDOKU_SIZE; j++) {
                if (j != 8) {
                    if ((i == 1 && j == 3) || (i == 4 && j == 3) || (i == 7 && j == 3) || (i == 4 && j == 6)){ //Hardcode asterisks locations
                        System.out.print("|" + board[i][j] + ">");
                    }
                    else if ((i == 1 && j == 4)|| (i == 4 && j == 4) || (i == 7 && j == 4) || (i == 4 && j == 1) || (i == 4 && j == 7)) { //Hardcode asterisks locations
                        System.out.print(board[i][j] + "<");
                    }
                    else if ((i == 2 && j == 6) || (i == 6 && j == 6)) { //Hardcode asterisks locations
                        System.out.print("|" + board[i][j] + "<");
                    }
                    else if ((i == 2 && j== 1) || (i == 6 && j == 1) || (i == 4 && j == 0)) { //Hardcode asterisks locations
                        System.out.print( board[i][j] + ">");
                    }
                    else if (j == 3 || j == 6) {                    //The rest handles printing for every cell that is not an asterisk
                        System.out.print("|" + board[i][j] + " ");
                    }
                    else if (j == 2 || j == 5) {
                        System.out.print(board[i][j]);
                    } else {
                        System.out.print(board[i][j] + " ");
                    }
                } else {
                    System.out.print(board[i][j] + "|"); //Prints last row of |
                }
            }
            System.out.println(); //After all columns for a row are printed go to the next line
        }
        System.out.println("+-----------------+"); //Bottom bar
    }

    // Run the actual solver.
    void solveIt() {
        solutionCounter = solve(grid, solutionCounter);
        if (solutionCounter == 1) { //If one solution, print it
            print(board2);
        } else if (solutionCounter > 1){
            System.out.println(solutionCounter); //Otherwise print how many solutions
        }
        else
        {
            print(board2);
        }
    }

    public static void main(String[] args) {
        (new SudokuSolver()).solveIt();         //Calls our solveIt function which solves the puzzle
    }
}
