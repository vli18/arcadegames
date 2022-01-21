package evolution.tetris;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.util.Duration;

/**
 * This class is the top-level logic class of Tetris, where the logic is established through its various methods.
 * It contains an instance of the Board and several instances of Piece. It also contains a timeline, booleans, Strings,
 * and integers that assist the logic of the game. The class is also associated to the graphical elements of the game
 * such as Panes, Labels, and Buttons.
 *
 * This class creates the pieces in the game, updates the movements in the game (the moving down of the
 * piece), updates the score based on the player's interactions with the pieces, updates the labels
 * and game difficulty and status through buttons, allows the player to make the pieces move left, right, down,
 * and rotate through key input, and allows the player to restart and pause the game.
 */
public class TetrisGame implements evolution.Playable {

    private Pane gamePane;
    private VBox buttonPane;
    private Timeline timeline;
    private Label gameStatus;
    private Label score;
    private Label highScore;
    private Button back;
    private Button restart;
    private Button quit;
    private Board board;
    private Piece piece;
    private boolean pieceLanded;
    private boolean paused;
    private int highScoreValue;
    private int scoreValue;
    private String difficultyLevel;

    /**
     * This is the constructor which takes in a Panes, Labels, and Buttons. It initializes these
     * variables along with setting the game's default difficulty level to Easy. It also
     * sets up the timeline and allows the handling of the buttons through setting them on action
     */
    public TetrisGame(Pane gamePane, VBox buttonPane, Timeline timeline, Label gameStatus, Button back, Button restart, Button quit){
        this.gamePane = gamePane;
        this.buttonPane = buttonPane;
        this.timeline = timeline;
        this.gameStatus = gameStatus;
        this.back = back;
        this.restart = restart;
        this.quit = quit;

        this.board = new Board(this.gamePane);
        this.piece = new Piece(this.gamePane);
        this.difficultyLevel = "Easy";

        this.addToTimeline();
        this.addToButtonPane();
    }

    /**
     * This is the setUpTimeline() method where the various keyframes used are added onto the timeline and updated
     * every 500 milliseconds.
     * This allows for the program to use the updateGame() method to update the piece movement, create the pieces,
     * updates the score, and sets the default rate of the timeline to easy
     */
    public void addToTimeline() {
        KeyFrame kf = new KeyFrame(Duration.millis(Constants.DURATION), (ActionEvent e) -> this.updateGame());

        this.timeline.getKeyFrames().addAll(kf);
        this.timeline.setRate(Difficulty.EASY.duration());
        this.timeline.play();
    }

    private void addToButtonPane() {
        this.highScore = new Label("High Score: " + 0);
        this.score = new Label("Score: " + 0);
        Button easy = new Button("Easy");
        easy.setOnAction((ActionEvent e) -> this.handleEasy());
        Button medium = new Button("Medium");
        medium.setOnAction((ActionEvent e) -> this.handleMedium());
        Button hard = new Button("Hard");
        hard.setOnAction((ActionEvent e) -> this.handleHard());
        this.restart = new Button("Restart");
        this.restart.setOnAction((ActionEvent e) -> this.restart());

        this.buttonPane.getChildren().addAll(this.highScore, this.score, easy, medium, hard, this.restart, this.back, this.quit);
    }
    /**
     * This is the setUpTimeline() method where the various keyframes used are added onto the timeline and updated
     * every 500 milliseconds.
     * This allows for the program to use the updateGame() method to update the piece movement, create the pieces,
     * updates the score, and sets the default rate of the timeline to easy
     */
    public void updateGame() {
        this.updatePieceMovement();
        this.createPiece();
        this.updateScore();
        this.gamePane.requestFocus();
    }

    /**
     * This method creates a new piece when a piece lands, updates the board to add
     * the landed pieces to the board, clears full rows, calculates the score, it also
     * updates the game and game status label if game is over
     */
    private void createPiece(){
        if(this.pieceLanded){
            this.board.updateBoard(this.piece); //updates the board with landed pieces
            this.board.clearLines(); //clears any filled lines
            this.calculateScore(); //calculates the score
            if(!this.gameOver()){ //if game is not over, create a new piece and set piece landed back to false
                Piece newPiece = new Piece(this.gamePane);
                this.piece = newPiece;
                this.pieceLanded = false;
            }
            else{ //if game is over stop the timeline and set the text of the game status to game over
                this.timeline.stop();
                this.gameStatus.setText("Game Over");
                this.gameStatus.toFront();
            }
        }
    }

    /**
     * This method updates the piece movement based on the piece's move validity and updates
     * the game status when the player pauses the game
     */
    private void updatePieceMovement(){
        this.gamePane.setFocusTraversable(true);
        this.gamePane.requestFocus();
        //if the piece lands it sets the piece landed to true, stops the piece from falling, and sets key pressed to null
        if(!this.board.moveValidity(this.piece,1, 0)) {
            this.gamePane.setOnKeyPressed(null);
            this.piece.fallDown(false);
            this.pieceLanded = true;
        }
        else if(!paused){
            this.gameStatus.setText("");
            this.gamePane.setOnKeyPressed((KeyEvent e) -> this.handleKeyPress(e));
            this.piece.fallDown(true);
            this.pieceLanded = false;
        }
    }

    /**
     * This method handles the piece's key input movement
     *
     * 1. If the player presses the right arrow and move is valid then the piece moves right
     * 2. If the player presses the left arrow and move is valid then the piece moves left
     * 3. If the player presses the up arrow and move is valid, the piece rotates counter-clockwise
     * 4. If the player presses the down arrow and move is valid then the piece moves down
     * 5. If the player presses P, the pValue counter increments by 1
     * 6. If the player presses space, the piece moves all the way down to the next empty spot
     */
    public void handleKeyPress(KeyEvent e){
        KeyCode keyPressed = e.getCode();
        switch (keyPressed) {
            case RIGHT:
                if(this.board.moveValidity(this.piece,0, 1) && !paused){
                    this.piece.moveRight();
                }
                break;
            case LEFT:
                if(this.board.moveValidity(this.piece, 0, -1) && !paused){
                    this.piece.moveLeft();
                }
                break;
            case UP:
                if(this.board.moveValidity(this.piece,1, 0 )) {
                    boolean rotatable = true;
                    int[] newXArray = new int[4];
                    int[] newYArray = new int[4];
                    for (int i = 0; i < 4; i++) {
                        int centerOfRotationX = (int) this.piece.getSquare(0).getX();
                        int centerOfRotationY = (int) this.piece.getSquare(0).getY();
                        int oldXLoc = (int) this.piece.getSquare(i).getX();
                        int oldYLoc = (int) this.piece.getSquare(i).getY();
                        int newXLoc = centerOfRotationX - centerOfRotationY + oldYLoc;
                        int newYLoc = centerOfRotationY + centerOfRotationX - oldXLoc;

                        newXArray[i] = newXLoc;
                        newYArray[i] = newYLoc;
                        if(newXLoc < Constants.BORDER_LEFT_BOUND || newXLoc >= Constants.BORDER_RIGHT_BOUND || newYLoc < Constants.BORDER_TOP_BOUND || newYLoc > Constants.BORDER_BOTTOM_BOUND){
                            rotatable = false;
                        }
                        if(!this.board.rotateValidity(newYArray, newXArray)) {
                            rotatable = false;
                        }
                    }
                    if (rotatable && !this.piece.getIsOPiece() && !paused) {
                        this.piece.rotatePiece();
                    }
                }
                break;
            case DOWN:
                if(this.board.moveValidity(this.piece, 1, 0) && !paused) {
                    this.piece.moveDown();
                }
                break;
            case P:
                this.pause();
                break;
            case SPACE:
                while (this.board.moveValidity(this.piece, 1, 0) && !paused) {
                    this.piece.fallDown(true);
                }
            default:
                break;
        }
        e.consume();
    }

    /**
     * This method calculates the score based on the difficulty level of the game where each piece landed
     * is worth 5 points + 50 * the number or rows cleared at once + the special score if the number of rows cleared
     * is greater than 2 and multiplied by the corresponding score multiplier for the current difficulty
     * level. It resets the number of rows cleared and special score
     */
    private void calculateScore() {
        if(this.difficultyLevel == "Easy"){
            this.scoreValue += (5 + 50 * (this.board.getNumRowsCleared() + this.board.getSpecialScore())) *  Difficulty.EASY.scoreMultiplier();
        }
        else if(this.difficultyLevel == "Medium"){
            this.scoreValue += (5 + 50 * (this.board.getNumRowsCleared() + this.board.getSpecialScore())) *  Difficulty.MEDIUM.scoreMultiplier();
        }
        else if(this.difficultyLevel == "Hard"){
            this.scoreValue += (5 + 50 * (this.board.getNumRowsCleared() + this.board.getSpecialScore())) *  Difficulty.HARD.scoreMultiplier();
        }
        this.board.resetNumRowsCleared();
        this.board.resetSpecialScore();
    }

    /**
     * This method updates the score label with the score value and sets the high score value to the
     * highest score the player achieves (if current score is greater than high score than high score is set
     * to current score). The high score is updated throughout the game
     */
    private void updateScore(){
        this.score.setText("Score: " + this.scoreValue);
        if (this.scoreValue>=this.highScoreValue) {
            this.highScoreValue = this.scoreValue;
            this.highScore.setText("High Score: " + this.highScoreValue);
        }
    }

    /**
     * This method sets the rate to the easy duration and sets the difficult level to easy
     */
    private void handleEasy() {
        this.timeline.setRate(Difficulty.EASY.duration());
        this.difficultyLevel = "Easy";
    }

    /**
     * This method sets the rate to the medium duration and sets the difficult level to medium
     */
    private void handleMedium(){
        this.timeline.setRate(Difficulty.MEDIUM.duration());
        this.difficultyLevel = "Medium";
    }

    /**
     * This method sets the rate to the hard duration and sets the difficult level to hard
     */
    private void handleHard() {
        this.timeline.setRate(Difficulty.HARD.duration());
        this.difficultyLevel = "Hard";
    }

    public void pause(){
        this.paused = !this.paused;
        if(this.paused){
            this.gameStatus.setText("Paused");
            this.gameStatus.setLayoutX(Constants.GAME_PANE_WIDTH / 3 - 7);
            this.gameStatus.toFront();
            this.timeline.pause();
        }
        else{
            this.gameStatus.setText("");
            this.gameStatus.setLayoutX(Constants.GAME_PANE_WIDTH / 4);
            this.gameStatus.toFront();
            this.timeline.play();
        }
    }

    /**
     * This method restarts the game where the current piece is removed graphically, the board is reset to being empty
     * graphically and logically, resets the number of rows cleared and special score. The game status is updated,
     * the difficulty level is set to easy, score value to 0, and creates the new piece and plays the timeline
     */
    public void restart() {
        this.piece.removeFromPane();
        this.board.resetBoard();
        this.board.resetNumRowsCleared();
        this.board.resetSpecialScore();

        this.gameStatus.setText("");
        this.difficultyLevel = "Easy";
        this.scoreValue = 0;
        this.paused = false;
        this.score.setText("Score: " + this.scoreValue);

        this.piece = new Piece(this.gamePane);
        this.timeline.setRate(Difficulty.EASY.duration());
        this.timeline.play();
    }

    /**
     * This method returns whether game is over based on whether any column of the top row is filled
     */
    private boolean gameOver() {
        boolean gameOver = false;
        for(int col = 1; col < Constants.BORDER_NUM_COLS - 1; col++) {
            if(this.board.getIsFilled(1, col) == true){
                gameOver = true;
            }
        }
        return gameOver;
    }
}

