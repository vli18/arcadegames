package evolution.tetris;

import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

/**
 * This class models the Piece. It wraps a 1D Array of type Square and a boolean. Piece
 * is associated to Pane to help graphically add the Piece to the game. This class generates
 * and creates the pieces, it also has methods that help the piece move, fall down, and rotate
 */
public class Piece {

    private Pane root;
    private Square[] piece;
    private boolean isOPiece;

    /**
     * This is constructor which takes in a Pane. It initializes instance variables of
     * Pane, the 1D Array, and the boolean. It also calls the method to generate a random
     * piece.
     */
    public Piece(Pane root) {
        this.root = root;
        this.piece = new Square[4];
        this.isOPiece = false;

        this.generatePiece();
        this.root.setFocusTraversable(true);
    }

    /**
     * This method removes all the squares that make up the piece
     * 1D array graphically from the pane.
     */
    public void removeFromPane(){
        for (int i = 0; i < piece.length; i++) {
            this.piece[i].removeFromPane(this.root);
        }
    }

    /**
     * This method randomly generates one of 7 types of piece each time it
     * is called.
     */
    private void generatePiece() {
        int ranNum = (int) (Math.random() * 7);
        switch (ranNum) {
            case 0:
                createIPiece();
                break;
            case 1:
                createOPiece();
                break;
            case 2:
                createTPiece();
                break;
            case 3:
                createLPiece();
                break;
            case 4:
                createJPiece();
                break;
            case 5:
                createSPiece();
                break;
            case 6:
                createZPiece();
                break;
            default:
                break;
        }
    }

    /**
     * This method takes in a boolean that determines whether the piece move downwards
     * one square at a time or it should stop at its current position
     * @param noCollision
     */
    public void fallDown(boolean noCollision) {
        for (int i = 0; i < piece.length; i++) {
            if (noCollision) {
                this.piece[i].setY(this.piece[i].getY() + Constants.SQUARE_SIZE);
            } else {
                this.piece[i].setY(this.piece[i].getY());
            }
        }
    }

    /**
     * This method allows the piece to move left by moving all the squares in the
     * 1D Array to the left
     */
    public void moveLeft() {
        for (int i = 0; i < this.piece.length; i++) {
            this.piece[i].setX(this.piece[i].getX() - Constants.SQUARE_SIZE);
        }
    }

    /**
     * This method allows the piece to move right by moving all the squares in the
     * 1D Array to the right
     */
    public void moveRight() {
        for (int i = 0; i < this.piece.length; i++) {
            this.piece[i].setX(this.piece[i].getX() + Constants.SQUARE_SIZE);
        }
    }

    /**
     * This method allows the piece to move down one space by moving all the squares in the
     * 1D Array down
     */
    public void moveDown() {
        for (int i = 0; i < this.piece.length; i++) {
            this.piece[i].setY(this.piece[i].getY() + Constants.SQUARE_SIZE);
        }
    }

    /**
     * This method allows the piece to rotate counter-clockwise, it calculates the new
     * position of each of the squares in the 1D array based off x and y centers of rotation
     * and the squares' current x and y current location. It then sets the square locations
     * to the calculated new location.
     */
    public void rotatePiece() {
        for (int i = 0; i < this.piece.length; i++) {
            // Set to the value of x and y of the center point around which I am rotating
            int centerOfRotationX = (int) this.piece[0].getX();
            int centerOfRotationY = (int) this.piece[0].getY();

            // Set to the value of the point’s current x and y
            int oldXLoc = (int) this.piece[i].getX();
            int oldYLoc = (int) this.piece[i].getY();

            // Calculate coordinates of the rotated point
            int newXLoc = centerOfRotationX - centerOfRotationY + oldYLoc;
            int newYLoc = centerOfRotationY + centerOfRotationX - oldXLoc;

            // Sets the squares to their corresponding new coordinates
            this.piece[i].setX(newXLoc);
            this.piece[i].setY(newYLoc);
        }
    }

    /**
     * This method creates the I piece by setting the color and coordinates of the 4 squares
     * and adding them to the 1D piece array.
     */
    private void createIPiece() {
        Square[] piece = new Square[4];
        for (int i = 0; i < piece.length; i++) {
            Square square = new Square(this.root, Color.CYAN, Constants.I_PIECE_COORDS[i][0] + Constants.PIECE_START_X, Constants.I_PIECE_COORDS[i][1] + Constants.SQUARE_SIZE);
            piece[i] = square;
        }
        this.piece = piece;
    }

    /**
     * This method creates the T piece by setting the color and coordinates of the 4 squares
     * and adding them to the 1D piece array.
     */
    private void createTPiece() {
        Square[] piece = new Square[4];
        for (int i = 0; i < piece.length; i++) {
            Square square = new Square(this.root, Color.PURPLE, Constants.T_PIECE_COORDS[i][0] + Constants.PIECE_START_X, Constants.T_PIECE_COORDS[i][1] + Constants.SQUARE_SIZE);
            piece[i] = square;
        }
        this.piece = piece;
    }

    /**
     * This method creates the O piece by setting the color and coordinates of the 4 squares
     * and adding them to the 1D piece array. It also sets the isOPiece boolean to true.
     */
    private void createOPiece() {
        Square[] piece = new Square[4];
        for (int i = 0; i < piece.length; i++) {
            Square square = new Square(this.root, Color.YELLOW, Constants.O_PIECE_COORDS[i][0] + Constants.PIECE_START_X, Constants.O_PIECE_COORDS[i][1] + Constants.SQUARE_SIZE);
            piece[i] = square;
            this.isOPiece = true;
        }
        this.piece = piece;
    }

    /**
     * This method creates the L piece by setting the color and coordinates of the 4 squares
     * and adding them to the 1D piece array.
     */
    private void createLPiece() {
        Square[] piece = new Square[4];
        for (int i = 0; i < piece.length; i++) {
            Square square = new Square(this.root, Color.ORANGE, Constants.L_PIECE_COORDS[i][0] + Constants.PIECE_START_X, Constants.L_PIECE_COORDS[i][1] + Constants.SQUARE_SIZE);
            piece[i] = square;
        }
        this.piece = piece;
    }

    /**
     * This method creates the J piece by setting the color and coordinates of the 4 squares
     * and adding them to the 1D piece array.
     */
    private void createJPiece() {
        Square[] piece = new Square[4];
        for (int i = 0; i < piece.length; i++) {
            Square square = new Square(this.root, Color.PINK, Constants.J_PIECE_COORDS[i][0] + Constants.PIECE_START_X, Constants.J_PIECE_COORDS[i][1] + Constants.SQUARE_SIZE);
            piece[i] = square;
        }
        this.piece = piece;
    }

    /**
     * This method creates the S piece by setting the color and coordinates of the 4 squares
     * and adding them to the 1D piece array.
     */
    private void createSPiece() {
        Square[] piece = new Square[4];
        for (int i = 0; i < piece.length; i++) {
            Square square = new Square(this.root, Color.RED, Constants.S_PIECE_COORDS[i][0] + Constants.PIECE_START_X, Constants.S_PIECE_COORDS[i][1] + Constants.SQUARE_SIZE);
            piece[i] = square;
        }
        this.piece = piece;
    }

    /**
     * This method creates the Z piece by setting the color and coordinates of the 4 squares
     * and adding them to the 1D piece array.
     */
    private void createZPiece() {
        Square[] piece = new Square[4];
        for (int i = 0; i < piece.length; i++) {
            Square square = new Square(this.root, Color.GREEN, Constants.Z_PIECE_COORDS[i][0] + Constants.PIECE_START_X, Constants.Z_PIECE_COORDS[i][1] + Constants.SQUARE_SIZE);
            piece[i] = square;
        }
        this.piece = piece;
    }

    /**
     * This method returns the square of the 1D piece array
     * at index i
     */
    public Square getSquare(int i) {
        return this.piece[i];
    }

    /**
     * This method returns the row location of the square in the
     * 1D piece array at index i
     */
    public int getRow(int i) {
        return (int) (this.piece[i].getY() / Constants.SQUARE_SIZE);
    }

    /**
     * This method returns the column location of the square in the
     * 1D piece array at index i
     */
    public int getCol(int i) {
        return (int) (this.piece[i].getX() / Constants.SQUARE_SIZE);
    }

    /**
     * This method returns a boolean that indicates whether
     * the generated piece is an O piece
     */
    public boolean getIsOPiece() {
        return this.isOPiece;
    }
}


