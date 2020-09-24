/**
 * Class that solves the Asterisk Sudoku.
 * Prints the number of solutions of a Sudoku if there are multiple. If there is only a single solution, prints this solution instead.
 *
 * by <<TODO YOUR NAME AND ID HERE>>
 * and <<TODO YOUR PARTNERS NAME AND ID HERE>>
 * as group <<TODO GROUP NUMBER HERE>>
 */
class SudokuSolver {

    static final int SUDOKU_SIZE = 9;          // Size of the grid.
    static final int SUDOKU_MIN_NUMBER = 1;    // Minimum digit to be filled in.
    static final int SUDOKU_MAX_NUMBER = 9;    // Maximum digit to be filled in.
    static final int SUDOKU_BOX_DIMENSION = 3; // Dimension of the boxes (sub-grids that should contain all digits).
    static final int  EMPTY = 0;                // If a cell is empty it equals 0
    static final String ASTERISK = "> <";
    static final String ASTERISK_LEFT_CORNER = "> ";
    static final String ASTERISK_RIGHT_CORNER = "< ";

    static int[][] grid = new int[][] {  // The puzzle grid; 0 represents empty.
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
    void fillSecondaryArray(int[][] board) {  
        this.board = new int[SUDOKU_SIZE][SUDOKU_SIZE];

        for (int i = 0; i < SUDOKU_SIZE; i++) {
            for (int j = 0; j < SUDOKU_SIZE; j++) {
                this.board[i][j] = board[i][j];
            }
        }
    }

    // Is there a conflict when we fill in d at position (r, c)?
    boolean givesNoConflict(int row, int col, int num) {

        return !rowConflict(row, num) && !columnConflict(col, num) && !boxConflict(row, col, num); // && !asteriskConflict(num);

    }

    // Is there a conflict when we fill in d in row r?
    boolean rowConflict(int row, int num) {
        for (int i = 0; i < SUDOKU_SIZE; i++)
            if (board[row][i] == num)
                return true;

        return false;
    }

    // Is there a conflict in column c when we fill in d?
    boolean columnConflict(int col, int num) {
        for (int i = 0; i < SUDOKU_SIZE; i++)
            if (board[i][col] == num)
                return true;

        return false;
    }

    // Is there a conflict in the box at (r, c) when we fill in d?
    boolean boxConflict(int row, int col, int num) {
        int rowCalc = row - (row % 3);
        int colCalc = col - (col % 3);

        for (int i = rowCalc; i < rowCalc + 3; i++)
            for (int j = colCalc; j < colCalc + 3; j++)
                if (board[i][j] == num)
                    return true;

        return false;
    }

    boolean asteriskConflict(int num){
        // 1: [3][3] /
        // 2: [2][5] //
        // 3: [3][7] ///
        // 4: [5][2] //
        // 5: [5][5] /
        // 6: [5][8] ////
        // 7: [7][3] ///
        // 8: [8][5] ////
        // 9: [7][7] /

        if(board[3][3] == num)
        return true;
        else if (board[2][5] == num)
        return true;
        else if (board[3][7] == num)
        return true;
        else if (board[5][2] == num)
        return true;
        else if (board[5][5] == num)
        return true;
        else if (board[5][8] == num)
        return true;
        else if (board[7][3] == num)
        return true;
        else if (board[8][5] == num)
        return true;
        else if (board[7][7] == num)
        return true;
        else return false;

    }
	
	// Finds the next empty square (in "reading order").
    int[] findEmptySquare() {
        // TODO
        return new int[]{-1, -1};
    }

    // Find all solutions for the grid, and stores the final solution.
    public boolean solve() {
        for (int row = 0; row < SUDOKU_SIZE; row++) {
            for (int column = 0; column < SUDOKU_SIZE; column++) {
                if (board[row][column] == EMPTY) {
                    for (int num = 1; num <= SUDOKU_SIZE; num++) {
                        if (givesNoConflict(row, column, num)) {
                            board[row][column] = num;
                            if (solve()){
                                return true;
                            } else {
                                board[row][column] = EMPTY;
                            }
                        }
                    }
                    return false;
                }
            }
        }
        return true;
    }


    // Print the sudoku grid.
    void print() {
        System.out.println("+-----------------+");
        for (int i = 0; i < SUDOKU_SIZE; i++) {
            
            if (i == 3 || i == 6){
                System.out.print("+-----------------+\n|");
            } else {
                System.out.print("|"); //Prionts first row of |
            }
            for (int j = 0; j < SUDOKU_SIZE; j++) {
                if (j != 8) {
                    if (j == 3 || j == 6) {
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
            System.out.println();
        }
        System.out.println("+-----------------+");
    }

    // Run the actual solver.
    void solveIt() {
        fillSecondaryArray(grid);
        if (solve()){
            print();

        } else {
            System.out.println("Unsolvable");
        }
        
    }

    public static void main(String[] args) {
        (new SudokuSolver()).solveIt();
    }
}
