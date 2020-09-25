/**
 * Class that solves the Asterisk Sudoku.
 * Prints the number of solutions of a Sudoku if there are multiple. If there is only a single solution, prints this solution instead.
 * Charles Mitchiner (ID 1574531)
 * and Samir Saidi (ID 1548735)
 * as group number 97
 * Date: 24th September 2020
 */
class SudokuSolver {
    static final int SUDUKU_GRID_MIN_INDEX = 0; //Minimum value for a row's index or a col's index
    static final int SUDOKU_SIZE = 9;          // Size of the grid.
    static final int SUDOKU_MIN_NUMBER = 1;    // Minimum digit to be filled in.
    static final int SUDOKU_MAX_NUMBER = 9;    // Maximum digit to be filled in.
    static final int SUDOKU_BOX_DIMENSION = 3; // Dimension of the boxes (sub-grids that should contain all digits).
    static final int  EMPTY = 0;                // If a cell is empty it equals 0
    
    int[][] grid = new int[][] {  // The puzzle grid; 0 represents empty.
        { 0, 9, 0,   7, 3, 0,    4, 0, 0 },    // One solution.
        { 0, 0, 0,   0, 0, 0,    5, 0, 0 },
        { 3, 0, 0,   0, 0, 6,    0, 0, 0 },

        { 0, 0, 0,   0, 0, 2,    6, 4, 0 },
        { 0, 0, 0,   6, 5, 1,    0, 0, 0 },
        { 0, 0, 6,   9, 0, 7,    0, 0, 0 },

        { 5, 8, 0,   0, 0, 0,    0, 0, 0 },
        { 9, 0, 0,   0, 0, 3,    0, 2, 5 },
        { 6, 0, 3,   0, 0, 0,    8, 0, 0 },
    };

    int solutionCounter = 0; // Solution counter

    private int[][] board;

    //Fills our secondary array "board" with passed argument
    void fillSecondaryArray() {  
        this.board = new int[SUDOKU_SIZE][SUDOKU_SIZE];

        for (int i = SUDUKU_GRID_MIN_INDEX; i < SUDOKU_SIZE; i++) {
            for (int j = SUDUKU_GRID_MIN_INDEX; j < SUDOKU_SIZE; j++) {
                this.board[i][j] = grid[i][j];
            }
        }
    }

    // Is there a conflict when we fill in d at position (r, c)?
    boolean givesConflict(int r, int c, int d) {
        if (!rowConflict(r, d) && !columnConflict(c, d) && !boxConflict(r, c, d) && !asteriskConflict(board)) { //If there isnt a conflict in any of the conditions, return false
            return false;
        }
        return true; //if any of the 4 conditions have conflict, return true
    }

    // Is there a conflict when we fill in d in row r?
    boolean rowConflict(int r, int d) {
        for (int i = SUDUKU_GRID_MIN_INDEX; i < SUDOKU_SIZE; i++) { //Check every column for the passed paramter index for row
            if (board[r][i] == d) {     
                return true;
            }
        }
        return false;
    }

    // Is there a conflict in column c when we fill in d?
    boolean columnConflict(int c, int d) {
        for (int i = SUDUKU_GRID_MIN_INDEX; i < SUDOKU_SIZE; i++) {  //Check every row for the passed paramter index for col
            if (board[i][c] == d){
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
                if (board[i][j] == d){
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

    // Find all solutions for the grid, and stores the final solution.
    boolean solve() {
        for (int row = SUDUKU_GRID_MIN_INDEX; row < SUDOKU_SIZE; row++) {               //Loops through every row
            for (int column = SUDUKU_GRID_MIN_INDEX; column < SUDOKU_SIZE; column++) { //Loops through every col
                if (board[row][column] == EMPTY) {                                     //If a spot we can move something into it
                    for (int num = SUDOKU_MIN_NUMBER; num <= SUDOKU_MAX_NUMBER; num++) { //Loop through every possible option to put into the empty spot
                        if (!givesConflict(row, column, num)) {                          //If there is no conflict with adding a number "num" at a spot add it to that spot
                            board[row][column] = num;
                            if (solve()){                                                 //Now recursivly backtrack and repeat                                           
                                return true;
                            } else {
                                board[row][column] = EMPTY;                              // If we returned false i.e there was conflict on all numbers 1-9, reset that space to EMPTY                        
                            }
                        }
                    }
                    solutionCounter++;
                    return false;                                                         //If all numbers 1-9 give conflict recurisvly backtrack until they dont
                }
            }
        }
        return true; //Puzzle has been solved
    }

    // Print the sudoku grid.
    void print() {
        System.out.println("+-----------------+"); //Top bar
        for (int i = SUDUKU_GRID_MIN_INDEX; i < SUDOKU_SIZE; i++) {
            
            if (i == 3 || i == 6){
                System.out.print("+-----------------+\n|"); //Two middle bars
            } else {
                System.out.print("|"); //Prionts first row of |
            }
            for (int j = SUDUKU_GRID_MIN_INDEX; j < SUDOKU_SIZE; j++) {
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
        fillSecondaryArray();
        if (solve()){                         //If solve returned true, the puzzle is solved
            print();                          //Thus print out the grid

        } else {
            System.out.println("Unsolvable"); //If the last resursive call returns false, the puzzle was unsolvable
        }
        //System.out.println(solutionCounter); //Number of failed attempts to insert numbers
    }

    public static void main(String[] args) {
        (new SudokuSolver()).solveIt();         //Calls our solveIt function which solves the puzzle
    }
}
