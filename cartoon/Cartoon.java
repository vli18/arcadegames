package evolution.cartoon;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.util.Duration;

/**
 * This class is the Cartoon class, the top-level logic class. It is
 * responsible for controlling the composite shapes, animation, and other
 * things specific to the cartoon. It is associated to a BorderPane, so that
 * it recognizes the root Node. It also contains Turtle, multiple instances of
 * Seagull, multiple instances of Label, a Slider, and multiple Panes.
 */
public class Cartoon implements evolution.Playable {

    private Timeline timeline;
    private Turtle turtle;
    private Seagull seagull1;
    private Seagull seagull2;
    private Seagull seagull3;
    private Pane gamePane;
    private Label totalSeagullRoundsMade;
    private Label babyTurtlesSurvived;
    private Label totalPoints;
    private Label gameStatus;
    private Slider difficulty;
    private int seagullCounter;
    private int turtleCounter;
    private int points;
    private boolean paused;

    /**
     * This method starts the program. It creates panes and sets up
     * the timeline.
     */
    public Cartoon(Pane gamePane, Timeline timeline, Label gameStatus, Label totalSeagullRoundsMade, Label babyTurtlesSurvived, Label totalPoints, Slider difficulty) {
        this.gamePane = gamePane;
        this.timeline = timeline;
        this.gameStatus = gameStatus;
        this.totalSeagullRoundsMade = totalSeagullRoundsMade;
        this.babyTurtlesSurvived = babyTurtlesSurvived;
        this.totalPoints = totalPoints;
        this.difficulty = difficulty;

        this.turtle = new Turtle(this.gamePane);
        this.seagull1 = new Seagull(this.gamePane, Constants.START_SEAGULL_X_OFFSET1, Constants.START_SEAGULL_Y_OFFSET1);
        this.seagull2 = new Seagull(this.gamePane, Constants.START_SEAGULL_X_OFFSET2, Constants.START_SEAGULL_Y_OFFSET2);
        this.seagull2.setRotation(180);
        this.seagull3 = new Seagull(this.gamePane, Constants.START_SEAGULL_X_OFFSET3, Constants.START_SEAGULL_Y_OFFSET3);

        this.setUpCartoon();
        this.addToTimeline();
    }

    /**
     * This method sets up the timeline. It instantiates keyframes and adds them
     * to the timeline to move the seagulls, updates the seagull rounds counter,
     * updates the turtle counter, and checks for intersections.
     */
    public void addToTimeline() {
        KeyFrame kf = new KeyFrame(Duration.millis(30),  (ActionEvent e) -> this.updateGame());

        this.timeline.getKeyFrames().add(kf);
        this.timeline.play();
    }

    private void setUpCartoon(){
        this.gamePane.setFocusTraversable(true);
        this.gamePane.setOnKeyPressed((KeyEvent e) -> this.handleKeyPress(e));
    }

    public void updateGame(){
        this.updateSeagull(seagull1);
        this.updateSeagull(seagull2);
        this.updateSeagull(seagull3);
        this.checkTurtleWin();
        this.checkIntersection(seagull1);
        this.checkIntersection(seagull2);
        this.checkIntersection(seagull3);
        this.checkGameOver();
    }

    /**
     * This method moves the Turtle with keyInput. When player presses D the turtle moves right,
     * A moves the turtle to the left,  W moves the turtle up, S moves the turtle down.
     */
    public void handleKeyPress(KeyEvent e) {
        this.gamePane.setFocusTraversable(true);
        this.gamePane.requestFocus();
        this.difficulty.setFocusTraversable(false);
        KeyCode keyPressed = e.getCode();
        switch (keyPressed) {
            case RIGHT:
                if(!this.paused){
                    this.turtle.setPosition(this.turtle.getXPos() + Constants.TURTLE_DISTANCE, this.turtle.getYPos());
                }
                break;
            case LEFT:
                if(!paused){
                    this.turtle.setPosition(this.turtle.getXPos() - Constants.TURTLE_DISTANCE, this.turtle.getYPos());
                }
                break;
            case UP:
                if(!paused){
                    this.turtle.setPosition(this.turtle.getXPos(), this.turtle.getYPos() - Constants.TURTLE_DISTANCE);
                }
                break;
            case DOWN:
                if(!paused){
                    this.turtle.setPosition(this.turtle.getXPos(), this.turtle.getYPos() + Constants.TURTLE_DISTANCE);
                }
                break;
            case P:
                this.pause();
            default:
                break;
        }
        e.consume();
    }

    /**
     * This method checks if the Turtle has made it to the ocean. If the turtle made it the
     * Baby Turtles Survived label will update where the number increases by one. It also
     * updates the total points based on the level of difficulty set.
     */
    private void checkTurtleWin() {
        if(this.turtle.getYPos() == 38) {
            this.turtleCounter++;
            this.turtle.setPosition(Constants.START_TURTLE_X_OFFSET, Constants.START_TURTLE_Y_OFFSET);
            if (this.difficulty.getValue()/1 == 1) {
                this.points += 100;
            }
            else if(this.difficulty.getValue()/1 == 2) {
                this.points += 200;
            }
            else if(this.difficulty.getValue()/1 == 3) {
                this.points += 300;
            }
            else if(this.difficulty.getValue()/1 == 4) {
                this.points += 400;
            }
            else if(this.difficulty.getValue()/1 == 5) {
                this.points += 500;
            }
        }
        this.babyTurtlesSurvived.setText("Baby Turtles Survived: " + this.turtleCounter);
        this.totalPoints.setText("Total Points: " + this.points);
    }

    /**
     * This method checks if the Turtle intersects with a Seagull. If the two objects
     * intersect, the turtle will be moved back the start position and the
     * Total Points label will be updated where the score decreases.
     */
    private void checkIntersection(Seagull seagull) {
        double seagullUpperXBound = seagull.getXPos() + 70;
        double seagullLowerXBound = seagull.getXPos();
        double seagullUpperYBound = seagull.getYPos();
        double seagullLowerYBound = seagull.getYPos() + 140;
        double turtleUpperXBound = this.turtle.getXPos() + 50;
        double turtleLowerXBound = this.turtle.getXPos() - 50;
        double turtleUpperYBound = this.turtle.getYPos() - 50;
        double turtleLowerYBound = this.turtle.getYPos() + 50;
        if(((turtleUpperXBound <= seagullUpperXBound) && (turtleUpperXBound >= seagullLowerXBound)) &&
                ((turtleUpperYBound >= seagullUpperYBound) && (turtleUpperYBound <= seagullLowerYBound))) {
            this.turtle.setPosition(Constants.START_TURTLE_X_OFFSET, Constants.START_TURTLE_Y_OFFSET);
            this.points -= 50;
        }
        else if (((turtleLowerXBound <= seagullUpperXBound) && (turtleLowerXBound >= seagullLowerXBound)) &&
                ((turtleLowerYBound >= seagullUpperYBound) && (turtleLowerYBound <= seagullLowerYBound))){
            this.turtle.setPosition(Constants.START_TURTLE_X_OFFSET, Constants.START_TURTLE_Y_OFFSET);
            this.points -= 50;
        }
        this.totalPoints.setText("Total Points:  " + this.points);
    }

    /**
     * This method rotates the seagull to make it face right, it also adds one
     * to the seagull rounds counter.
     */
    private void seagullFaceRight(Seagull seagull) {
        seagull.setRotation(0);
        this.seagullCounter++;
    }

    /**
     * This method rotates the seagull to make it face left, it also adds one
     * to the seagull rounds counter.
     */
    private void seagullFaceLeft(Seagull seagull) {
        seagull.setRotation(180);
        this.seagullCounter++;
    }

    /**
     * This method updates the direction the seagull is facing if the seagull
     * hits either side of the scene. It also updates the Total Rounds Made By
     * Seagulls label with the seagull rounds counter.
     */
    private void updateSeagullRotation(Seagull seagull) {
        if((seagull.getXPos() >= 0) && (seagull.getXPos() <= 9)){
            this.seagullFaceRight(seagull);
        }
        else if((seagull.getXPos() >= 931) && (seagull.getXPos() <= 940)){
            this.seagullFaceLeft(seagull);
        }
        this.totalSeagullRoundsMade.setText("Total Rounds Made By Seagulls: " + this.seagullCounter);
    }

    /**
     * This method updates the movement of the seagull where if it is facing right
     * it will move to the right, if it is facing left it will move the left. The method
     * also changes the speed of the seagull depending on the value of the Difficulty
     * Slider.
     */
    private void updateSeagull(Seagull seagull) {
        this.gamePane.setFocusTraversable(true);
        this.updateSeagullRotation(seagull);
        if (seagull.getRotation() == 0){
            seagull.setPosition(seagull.getXPos() + this.difficulty.getValue());
        }
        else{
            seagull.setPosition(seagull.getXPos() - this.difficulty.getValue());
        }
        this.difficulty.setFocusTraversable(false);
        this.gamePane.setFocusTraversable(true);
        this.gamePane.requestFocus();
    }

    private void checkGameOver(){
        if(this.points <= -150){
            this.gameStatus.setText("Game Over");
            this.gameStatus.toFront();
            this.gamePane.setOnKeyPressed(null);
            this.timeline.stop();
        }
        else{
            this.gameStatus.toBack();
        }
    }

    public void pause(){
        this.paused = !this.paused;
        if(this.paused){
            this.gameStatus.setText("Paused");
            this.gameStatus.setLayoutX(Constants.APP_WIDTH / 2 - 70);
            this.gameStatus.toFront();
            this.timeline.pause();
        }
        else{
            this.gameStatus.setText("");
            this.gameStatus.setLayoutX(Constants.APP_WIDTH / 2 - 100);
            this.gameStatus.toBack();
            this.timeline.play();
        }
    }

    public void restart(){
        this.timeline.stop();
        this.gamePane.setOnKeyPressed((KeyEvent e) -> this.handleKeyPress(e));
        this.turtle.setPosition(Constants.START_TURTLE_X_OFFSET, Constants.START_TURTLE_Y_OFFSET);
        this.seagull1.setPosition(Constants.START_SEAGULL_X_OFFSET1);
        this.seagull2.setPosition(Constants.START_SEAGULL_X_OFFSET2);
        this.seagull2.setRotation(180);
        this.seagull3.setPosition(Constants.START_SEAGULL_X_OFFSET3);
        this.seagullCounter = 0;
        this.turtleCounter = 0;
        this.points = 0;
        this.gameStatus.setText("");
        this.timeline.play();
        this.paused = false;
    }
}
