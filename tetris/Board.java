package evolution.tetris;

import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

/**
 * This is the class that details the creation of the board, which is a 2D array that consists of squares. It includes the
 * instantiation of various score related variables and methods to manipulate board related actions like setting the board and
 * more complicated methods like clearing the lines.
 */
public class Board {

    private Pane root;
    private Square[][] board;
    private boolean[][] isFilled;
    private int numRowsCleared;
    private int specialScore;

    public Board(Pane root) {
        this.root = root;
        this.board = new Square[Constants.BORDER_NUM_ROWS][Constants.BORDER_NUM_COLS];
        this.isFilled = new boolean[Constants.BORDER_NUM_ROWS][Constants.BORDER_NUM_COLS];
        this.numRowsCleared = 0;
        this.specialScore = 0;

        this.setBoard();
        this.setIsFilled();
    }
    /**
     * This is the method that sets the board, it runs through each row and column of the array and sets the value in the array
     * to be null. Then, it creates the border squares, by creating new squares that are gray, rather than the internal black
     * squares and logically adds it to the array.
     */
    private void setBoard(){
        for (int row = 0; row < this.board.length; row++) {
            for (int col = 0; col < this.board[0].length; col++) {
                this.board[row][col] = null;//logically adding to array
                if (row == 0 || col == 0 || row == this.board.length - 1 || col == this.board[0].length - 1) {
                    Square square = new Square(this.root, Color.GRAY,col * Constants.SQUARE_SIZE, row * Constants.SQUARE_SIZE);
                    this.board[row][col] = square;//logically adding to array
                }
            }
        }
    }
    /**
     * This method sets up the isFilled 2D array of type boolean which corresponds to each individual square in the board.
     * It runs through each row, then each column, so index of the array, and sets it to false by default. However, if
     * the index is on the borders of the array, then the isFilled index will be set to true.
     */
    private void setIsFilled(){
        for (int row = 0; row < this.isFilled.length; row++) {
            for (int col = 0; col < this.isFilled[0].length; col++) {
                this.isFilled[row][col] = false;
                if (col == 0) {//left Border
                    this.isFilled[row][col] = true;
                }
                if(col == this.isFilled[0].length - 1){//right border
                    this.isFilled[row][col] = true;
                }
                if (row == 0 || row == this.isFilled.length -1) {// top and bottom borders
                    this.isFilled[row][col] = true;
                }
            }
        }
    }
    /**
     * this method sets the isFilled array to true for the individual squares of the pieces. Then, it adds the squares
     * of the piece to the squares of the board, in terms of both arrays' indexes. The if statement makes sure that the
     * board does not have a previous piece's square there before adding a new one.
     */
    public void updateBoard(Piece piece){
        for(int i = 0; i < 4; i++) {
            this.isFilled[piece.getRow(i)][piece.getCol(i)] = true;
            if(this.board[piece.getRow(i)][piece.getCol(i)] == null){//condition to make sure squares do not overlap
                Square square = piece.getSquare(i);
                this.board[piece.getRow(i)][piece.getCol(i)] = square;
            }
        }
    }
    /**
     * This is the method that resets the board. It starts indexing at the inner board, and states that if there is a
     * square currently in the board's array, it is graphically removed. Then, for all squares, they are logically removed,
     * by being set to null, and the isFilled values are reset.
     */
    public void resetBoard() {
        for (int row = 1; row < this.board.length - 1; row++) {
            for (int col = 1; col < this.board[0].length - 1; col++) {
                if (this.board[row][col] != null) {
                    this.board[row][col].removeFromPane(this.root);//graphically removing
                }
                this.board[row][col] = null;//logically resetting
                this.setIsFilled();
            }
        }
    }
    /**
     * This is the method that tests the validity of movement for each piece. It automatically sets a boolean moveIsValid
     * as true, but returns false if certain conditions are fulfilled. Those conditions vary based on what actions are prohibited
     * in which cases, but are typically regarding movement within a certain amount of rows or columns away. Then, it
     * returns the boolean value for movement purposes.
     */
    public boolean moveValidity(Piece piece, int rowOffset, int colOffset){
        boolean moveIsValid = true;
        for(int i = 0; i < Constants.PIECE_LENGTH; i++){
            if(this.board[piece.getRow(i) + rowOffset][piece.getCol(i) + colOffset] != null){//if the array is filled a certain offset away
                moveIsValid = false;
            }
        }
        return moveIsValid;
    }

    public boolean rotateValidity(int[] newXArray, int[] newYArray){
        boolean rotateIsValid = true;
        for(int i = 0; i < Constants.PIECE_LENGTH; i++) {
            if (newXArray[i] >= Constants.BORDER_LEFT_BOUND && newYArray[i] < Constants.BORDER_RIGHT_BOUND) {
                if (this.isFilled[newXArray[i] / Constants.SQUARE_SIZE][newYArray[i] / Constants.SQUARE_SIZE]) {//if the array is filled a certain offset away
                    rotateIsValid = false;
                }
            }
        }
        return rotateIsValid;
    }

    /**
     * This is the helper method to clear lines that involves a helper method, rowIsFull, and the logical and graphical
     * removal of lines that are full. It also counts how many rows are filled due to a certain piece for scoring purposes.
     * It first runs through all rows within the inner board and tests for if a row is filled. If a row is filled, it
     * increments scoring values, then graphically and logically remove the squares in that row. Then, it runs through
     * all the squares above the filled row. If there are squares that aren't null, then they are moved down logically
     * and graphically. The isFilled values are also updated accordingly.
     */
    public void clearLines() {
        int rowsFull = 0;
        for (int r = 1; r < this.board.length - 1; r++) {//run through all rows within the inner board
            if(this.rowIsFull(r)) {
                this.numRowsCleared++;//increment the total number of rows cleared by 1
                rowsFull++;// increment the rows filled with one piece by 1
                for(int col = 1; col < this.board[0].length - 1; col++) {//runs through all columns of the filled row
                    this.board[r][col].removeFromPane(this.root);//graphically removes
                    this.board[r][col] = null;
                    this.isFilled[r][col] = false;
                }
                for (int row = r; row > 1; row--) {
                    for (int col = 1; col < this.board[0].length - 1; col++) {
                        Square theSquareAbove = this.board[row - 1][col];
                        if(theSquareAbove != null) {
                            theSquareAbove.setY(theSquareAbove.getY() + Constants.SQUARE_SIZE);//graphically moving down
                            this.board[row][col]= theSquareAbove;//logically moving down
                            this.isFilled[row - 1][col] = false;
                            this.isFilled[row][col] = true;
                            this.board[row-1][col] = null;
                        }
                    }
                }
            }
        }
        if(rowsFull >=2 ){
            this.specialScore = rowsFull;//adapt scoring mechanism to give special points for clearing more than 2 rows at a time
        }
    }
    /**
     * This is the helper method that tests if a row is full, given a certain row value. It default sets a boolean rowIsFull
     * to true, then runs through all the squares of the row. If any of them are not filled, then the row is not full, and
     * rowIsFull is set to false. Finally, it returns the value to be used in the if statement in clearLines.
     */
    public boolean rowIsFull(int row) {
        boolean rowIsFull = true;
        for (int col = 1; col < this.isFilled[0].length; col++) {//run through all columns of the row
            if(this.isFilled[row][col] == false) {//if a row is not filled
                rowIsFull = false;
                break;
            }
        }
        return rowIsFull;
    }
    /**
     * This is the helper method to reset the total number of rows cleared used in the scoring system.
     */
    public void resetNumRowsCleared() {
        this.numRowsCleared = 0;
    }
    /**
     * This is the helper method to reset the total number of special score ie times in which multiple rows have
     * been cleared at once.
     */
    public void resetSpecialScore() {
        this.specialScore = 0;
    }
    /**
     * This is the getter for the special score to be used in TetrisGame for special score calculation.
     */
    public int getSpecialScore() {
        return this.specialScore;
    }
    /**
     * This is the getter for the total number of rows cleared to be used in TetrisGame for normal score calculation.
     */
    public int getNumRowsCleared() {
        return this.numRowsCleared;
    }
    /**
     * This is the getter for the isFilled array for movement related methods in TetrisGame.
     */
    public boolean getIsFilled(int row, int col){
        return this.isFilled[row][col];
    }
}