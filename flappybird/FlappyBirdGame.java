package evolution.flappybird;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.util.Duration;

import java.util.ArrayList;

/**
 * This is the top-level logical class for all the Flappy Bird games. It implements Interface Playable and includes
 * shared Arcade game methods. This class is responsible for delegating tasks to methods that are shared across all the
 * Flappy Bird modes, including setting up and updating the Pipes, restarting, pausing, checking game over and more.
 */
public class FlappyBirdGame implements evolution.Playable {

    public Pane gamePane;
    public Pane bottomPane;
    public Timeline timeline;
    public Label gameStatus;
    private GameMode gameMode;
    public ArrayList<Pipe> pipes;
    public Pipe nextPipe;
    public Pipe nearestPipe;
    private boolean paused;
    private int counter;

    /**
     * This is the constructor of the FlappyBirdGame class that is associated to the game pane, the bottom pane, the
     * timeline shared across Arcade games, the game status shared across Arcade games, and the GameMode enum class.
     * It instantiates the instance variables, sets up the pipes, creates birds, sets the bottom pane, and sets the game,
     * and sets up the timeline all according to the respective game mode.
     * @param gamePane
     * @param bottomPane
     * @param timeline
     * @param gameStatus
     * @param gameMode
     */
    public FlappyBirdGame(Pane gamePane, Pane bottomPane, Timeline timeline, Label gameStatus, GameMode gameMode){
        this.gamePane = gamePane;
        this.bottomPane = bottomPane;
        this.timeline = timeline;
        this.gameStatus = gameStatus;
        this.gameMode = gameMode;
        this.counter = 0;

        this.setPipes();
        this.gameMode.createBirds(this.gamePane, this.nearestPipe);
        this.gameMode.setBottomPane(this.bottomPane, this.timeline);
        this.gameMode.setGame();
        this.addToTimeline();
    }

    /**
     * This method sets up the ArrayList of Pipes and instantiates the first Pipe and sets it to the nearest Pipe and
     * adds the first pipe to the Pipes ArrayList.
     */
    private void setPipes(){
        this.pipes = new ArrayList<Pipe>();
        this.nextPipe = new Pipe(this.gamePane, Constants.GAME_PANE_WIDTH);
        this.nearestPipe = this.nextPipe;
        this.pipes.add(this.nextPipe);
    }

    /**
     * This method is a part of the ArcadeGame interface and adds the FlappyBirdGame keyframe to the shared timeline.
     */
    public void addToTimeline() {
        KeyFrame kf = new KeyFrame(Duration.millis(Constants.TIMELINE_DURATION), (ActionEvent e) -> this.updateGame());
        this.timeline.getKeyFrames().addAll(kf);
        this.timeline.play();
    }

    /**
     * This method updates the various game functions with the timeline:
     * -It generates the pipes and updates the horizontal movement of the pipes
     * -It removes pipes that go offscreen graphically and logically
     * -It checks whether Smart Flappy Bird needs to auto restart
     * -It counts the number of times the game is updated according to the timeline and updates the game and bird
     * -It updates the labels in the game
     * -It checks for game over
     */
    public void updateGame(){
        this.generatePipes();
        this.updatePipe();
        this.removeOutsidePipes();
        this.checkAutoRestart();
        this.counter++;
        this.gameMode.updateBird(this.nearestPipe, this.counter);
        this.updateLabels();
        this.checkGameOver();
    }

    /**
     * This method generates a new pipe consistently every 230 units, sets it as the nextPipe and adds it to the
     * ArrayList of pipes.
     */
    private void generatePipes() {
        while (this.nextPipe.getX() <= Constants.GAME_PANE_WIDTH) {
            Pipe pipe = new Pipe(this.gamePane, this.nextPipe.getX() + Constants.PIPE_HORIZONTAL_DISTANCE);
            this.nextPipe = pipe;
            this.pipes.add(this.nextPipe);
        }
    }

    /**
     * This method updates the Pipes to move at a constant rate to the right. It also calls the updates nearest pipe
     * method.
     */
    private void updatePipe() {
        for (int i = 0; i < this.pipes.size(); i++) {
            this.pipes.get(i).setX(this.pipes.get(i).getX() - Constants.DISTANCE);
            this.updateNearestPipe(i);
        }
    }

    /**
     * This method updates the nearest pipe as the pipe that is in the closest range of the bird.
     * @param i
     */
    private void updateNearestPipe(int i){
        if(this.pipes.get(i).getX() >= Constants.BIRD_START_X - Constants.PIPE_RIM_WIDTH && this.pipes.get(i).getX() <= Constants.BIRD_START_X + Constants.BIRD_WIDTH + Constants.PIPE_RIM_WIDTH){
            this.nearestPipe = this.pipes.get(i);
        }
    }

    /**
     * This method graphically and logically removes the pipes outside of the screen
     */
    private void removeOutsidePipes(){
        for (int i = 0; i < this.pipes.size(); i++) {
            if(this.pipes.get(i).getX() < -Constants.PIPE_RIM_WIDTH){
                Pipe removedPipe = this.pipes.remove(i);
                removedPipe.removeFromPane();
            }
        }
    }

    /**
     * This method updates the labels for the corresponding Flappy Bird game mode.
     */
    public void updateLabels(){
        this.gameMode.updateLabels();
    }

    /**
     * This method handles key press and pauses the game across all Flappy Bird modes and calls the handle
     * key press specific to the respective game mode.
     * @param e
     */
    public void handleKeyPress(KeyEvent e) {
        KeyCode keyPressed = e.getCode();
        switch (keyPressed) {
            case P:
                this.pause();
                break;
            default:
                break;
        }
        e.consume();
        this.gameMode.handleKeyPress(e);
    }

    /**
     * This method checks for game over for the respective game mode.
     */
    public void checkGameOver(){
        this.gameMode.checkCollision(this.timeline, this.gamePane, this.gameStatus, this.nearestPipe);
    }

    /**
     * This method pauses and unpauses the game and updates the game status label.
     */
    public void pause(){
        this.paused = !this.paused;
        if(this.paused){
            this.gameStatus.setText("Paused");
            this.gameStatus.setLayoutX(Constants.GAME_PANE_WIDTH / 3);
            this.gameStatus.toFront();
            this.timeline.pause();
        }
        else{
            this.gameStatus.setText("");
            this.gameStatus.setLayoutX(2 * Constants.GAME_PANE_WIDTH / 7);
            this.gameStatus.toFront();
            this.timeline.play();
        }
    }

    /**
     * This method checks for auto restart for Smart Flappy Bird and removes and creates new pipes, sets up the nearest
     * pipe, updates the game status label, resets the counter, plays the timeline, and calls the Smart Flappy Bird
     * auto restart method.
     */
    public void checkAutoRestart(){
        if(this.gameMode.getAutoRestart()){
            this.counter = 0;
            for (int i = 0; i < this.pipes.size(); i++) {
                this.pipes.get(i).removeFromPane();
            }
            this.pipes.clear();
            this.gameStatus.setText("");
            this.nextPipe = new Pipe(this.gamePane, Constants.GAME_PANE_WIDTH);
            this.nearestPipe = this.nextPipe;
            this.pipes.add(this.nextPipe);
            this.timeline.play();
            this.gameMode.autoRestart(this.gamePane, this.nearestPipe);
        }
    }

    /**
     * This method restarts the flappy bird game when pressing the Restart button by removing and creating new pipes,
     * setting up the nearest pipe, updating the game status label, resetting the counter, playing the timeline, setting
     * the timeline rate to 1x speed, calls the respective game mode restart method, and creates new birds
     */
    public void restart(){
        this.timeline.stop();
        this.gamePane.requestFocus();
        this.gamePane.setOnKeyPressed((KeyEvent e) -> this.handleKeyPress(e));
        for (int i = 0; i < this.pipes.size(); i++) {
            this.pipes.get(i).removeFromPane();
        }
        this.pipes.clear();
        this.gameStatus.setText("");
        this.nextPipe = new Pipe(this.gamePane, Constants.GAME_PANE_WIDTH);
        this.nearestPipe = this.nextPipe;
        this.pipes.add(this.nextPipe);
        this.timeline.setRate(Constants.SPEED_1X);
        this.timeline.play();
        this.gameMode.restart();
        this.gameMode.createBirds(this.gamePane, this.nearestPipe);
    }
}
