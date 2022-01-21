package evolution.flappybird;

import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import java.util.ArrayList;

/**
 * This is the enum GameMode class that contains the various Flappy Bird game modes and the methods for them.
 */
public enum GameMode {
    MANUAL, SMART, MULTIPLAYER; //three game modes

    private ArrayList<FlappyBird> flappyBirds;
    private Generation currentGeneration;

    private Label currentScoreLabel;
    private int currentScore;
    private Label highScoreLabel;
    private int highScore;
    private int pipesPassed;

    private Label numAliveLabel;
    private Label currentFitnessLabel;
    private Label generationNumLabel;
    private int generationNum;
    private Label avgFitnessPrevGenLabel;
    private Label bestFitnessPrevGenLabel;
    private Label bestFitnessAllTimeLabel;
    private int bestFitnessAllTime;

    /**
     * This method sets the bottom pane of the game with the corresponding labels
     * @param bottomPane
     * @param timeline
     */
    public void setBottomPane(Pane bottomPane, Timeline timeline) {
        switch (this) {
            case MANUAL: case MULTIPLAYER:
                this.highScoreLabel = new Label("High Score: ");
                this.highScoreLabel.setLayoutX(Constants.HIGH_SCORE_X);
                this.highScoreLabel.setLayoutY(Constants.HIGH_SCORE_Y);
                this.highScoreLabel.setTextFill(Color.rgb(83, 62, 71));
                this.highScoreLabel.setFont(Font.font("Verdana", FontWeight.MEDIUM, 15));

                this.currentScoreLabel = new Label("Current Score: ");
                this.currentScoreLabel.setLayoutX(Constants.CURRENT_SCORE_X);
                this.currentScoreLabel.setLayoutY(Constants.CURRENT_SCORE_Y);
                this.currentScoreLabel.setTextFill(Color.rgb(83, 62, 71));
                this.currentScoreLabel.setFont(Font.font("Verdana", FontWeight.MEDIUM, 15));

                bottomPane.getChildren().addAll(this.highScoreLabel, this.currentScoreLabel);
                break;
            case SMART:
                VBox stats = new VBox(2);
                stats.setLayoutX(Constants.STATS_X);
                stats.setLayoutY(Constants.STATS_Y);
                this.numAliveLabel = new Label("Alive: ");
                this.currentFitnessLabel = new Label("Current Fitness: ");
                this.generationNumLabel = new Label("Generation: ");
                this.avgFitnessPrevGenLabel = new Label("Average Fitness (Prev. Generation): ");
                this.bestFitnessPrevGenLabel = new Label("Best Fitness (Prev. Generation): ");
                this.bestFitnessAllTimeLabel = new Label("Best Fitness (All Time): ");
                stats.getChildren().addAll(this.numAliveLabel, this.currentFitnessLabel, this.generationNumLabel, this.avgFitnessPrevGenLabel, this.bestFitnessPrevGenLabel, this.bestFitnessAllTimeLabel);

                HBox speedControls = new HBox(7);
                speedControls.setLayoutX(Constants.SPEED_CONTROLS_X);
                speedControls.setLayoutY(Constants.SPEED_CONTROLS_Y);
                Button speed1xButton = new Button("1x");
                speed1xButton.setOnAction((ActionEvent e) -> timeline.setRate(Constants.SPEED_1X));
                Button speed2xButton = new Button("2x");
                speed2xButton.setOnAction((ActionEvent e) -> timeline.setRate(Constants.SPEED_2X));
                Button speed5xButton = new Button("5x");
                speed5xButton.setOnAction((ActionEvent e) -> timeline.setRate(Constants.SPEED_5X));
                Button speedMaxButton = new Button("Max");
                speedMaxButton.setOnAction((ActionEvent e) -> timeline.setRate(Constants.SPEED_MAX));
                speedControls.getChildren().addAll(speed1xButton, speed2xButton, speed5xButton, speedMaxButton);

                bottomPane.getChildren().addAll(stats, speedControls);
                break;
        }
    }

    /**
     * This method sets up the game for the corresponding game mode
     */
    public void setGame(){
        switch(this) {
            case MANUAL: case MULTIPLAYER:
                this.currentScore = 0;
                this.highScore = 0;
                break;
            case SMART:
                this.numAliveLabel.setText("Alive: " + 50);
                this.generationNum = 1;
                this.generationNumLabel.setText("Generation: " + this.generationNum);
                this.avgFitnessPrevGenLabel.setText("Average Fitness (Prev. Generation): " + 0);
                this.bestFitnessPrevGenLabel.setText("Best Fitness (Prev. Generation): " + 0);
                this.bestFitnessAllTimeLabel.setText("Best Fitness (All Time): " + 0);
                break;
        }
    }

    /**
     * This method creates the birds for the corresponding game mode (number of birds, types of birds, etc.). It also
     * adds the birds to the ArrayList of Flappy Birds. It instantiates the current Generation for Smart Flappy Bird.
     * @param gamePane
     * @param pipe
     */
    public void createBirds(Pane gamePane, Pipe pipe) {
        switch (this) {
            case MANUAL:
                this.flappyBirds = new ArrayList<FlappyBird>();
                this.flappyBirds.add(new FlappyBird(gamePane, 1, pipe));
                break;
            case SMART:
                Generation firstGeneration = new Generation(gamePane, pipe);
                this.currentGeneration = firstGeneration;
                break;
            case MULTIPLAYER:
                this.flappyBirds = new ArrayList<FlappyBird>();
                for (int i = 1; i <= 2; i++) {
                    this.flappyBirds.add(new FlappyBird(gamePane, i, pipe));
                }
                break;
            default:
                break;
        }
    }

    /**
     * This method updates the physics of the bird, its Y position, and updates the nearest pipe to be associated with
     * the bird.
     * @param pipe
     * @param counter
     */
    public void updateBird(Pipe pipe, int counter){
        switch(this){
            case MANUAL: case MULTIPLAYER:
                for(int i = 0; i < this.flappyBirds.size(); i++) {
                    this.flappyBirds.get(i).updatePhysics();
                    this.flappyBirds.get(i).setY(this.flappyBirds.get(i).getY());
                    this.flappyBirds.get(i).updateNearestPipe(pipe);
                }
                break;
            case SMART:
                this.currentGeneration.updateBirds(pipe, counter);
                break;
        }
    }

    /**
     * This method checks for collision for all the birds on whether they hit the pipe or hit the ground and removes them
     * graphically and logically if they do. It also updates the corresponding labels when the nearest pipe is succesfully
     * passed by the bird. It also sets game over for when there are no more birds left in the game (multiplayer) or when
     * the smart birds passed 500 pipes.
     * @param timeline
     * @param gamePane
     * @param gameStatus
     * @param nearestPipe
     */
    public void checkCollision(Timeline timeline, Pane gamePane, Label gameStatus, Pipe nearestPipe) {
        switch (this) {
            case MANUAL:
                for (int i = 0; i < this.flappyBirds.size(); i++) {
                    if (this.flappyBirds.get(i).hitPipe() || this.flappyBirds.get(i).hitGround()) {
                        this.flappyBirds.get(i).removeFromPane();
                        this.flappyBirds.remove(i);
                        gameStatus.setText("Game Over");
                        gamePane.setOnKeyPressed(null);
                        gameStatus.toFront();
                        timeline.stop();
                    } else if (nearestPipe.passed()) {
                        this.flappyBirds.get(i).setScore(this.flappyBirds.get(i).getScore() + 1);
                        this.currentScore = this.flappyBirds.get(i).getScore();
                    }
                }
                break;
            case SMART:
                this.currentGeneration.checkCollision();
                if (nearestPipe.passed()) {
                    this.pipesPassed++;
                    if (this.pipesPassed == 500) {
                        gameStatus.setText("Game Over");
                        gamePane.setOnKeyPressed(null);
                        gameStatus.toFront();
                        timeline.stop();
                    }
                }
                break;
            case MULTIPLAYER:
                for (int i = 0; i < this.flappyBirds.size(); i++) {
                    if (this.flappyBirds.get(i).hitPipe() || this.flappyBirds.get(i).hitGround()) {
                        this.flappyBirds.get(i).removeFromPane();
                        this.flappyBirds.remove(i);
                        if(nearestPipe.passed()){
                            this.flappyBirds.get(i).setScore(this.flappyBirds.get(i).getScore());
                            this.currentScore = this.flappyBirds.get(i).getScore();
                        }
                    } else if (nearestPipe.passed()) {
                        this.flappyBirds.get(i).setScore(this.flappyBirds.get(i).getScore() + 1);
                        this.currentScore = this.flappyBirds.get(i).getScore();
                    }
                }
                if (this.flappyBirds.isEmpty()) {
                    gameStatus.setText("Game Over");
                    gamePane.setOnKeyPressed(null);
                    gameStatus.toFront();
                    timeline.stop();
                }
                break;
        }
    }

    /**
     * This method updates the labels in the game. It updates the high score and current score for Manual and Multiplayer
     * and sets the stats of the Smart Flappy Bird game.
     */
    public void updateLabels() {
        switch (this) {
            case MANUAL:
                this.currentScoreLabel.setText("Score: " + this.currentScore / 3);
                if (this.currentScore >= this.highScore) {
                    this.highScore = this.currentScore;
                    this.highScoreLabel.setText("High Score: " + this.highScore / 3);
                }
                break;
            case MULTIPLAYER:
                this.currentScoreLabel.setText("Score: " + this.currentScore / 3);
                if(this.flappyBirds.size() == 2) {
                    if (this.flappyBirds.get(0).getScore() >= this.flappyBirds.get(1).getScore() && this.flappyBirds.get(0).getScore() >= this.highScore) {
                        this.currentScoreLabel.setText("Score: " + this.flappyBirds.get(0).getScore() / 3);
                        this.highScore = this.flappyBirds.get(0).getScore();
                        this.highScoreLabel.setText("High Score: " + this.highScore / 3);
                    } else if (this.flappyBirds.get(1).getScore() >= this.flappyBirds.get(0).getScore() && this.flappyBirds.get(1).getScore() >= this.highScore) {
                        this.currentScoreLabel.setText("Score: " + this.flappyBirds.get(1).getScore() / 3);
                        this.highScore = this.flappyBirds.get(1).getScore();
                        this.highScoreLabel.setText("High Score: " + this.highScore / 3);
                    }
                }
                else{
                    if (this.currentScore >= this.highScore) {
                        this.highScore = this.currentScore;
                        this.highScoreLabel.setText("High Score: " + this.highScore / 3);
                    }
                }
                break;
            case SMART:
                this.currentGeneration.updateBestFitness();
                this.currentFitnessLabel.setText("Current Fitness: " + this.currentGeneration.getBestFitness());
                this.numAliveLabel.setText("Alive: " + this.currentGeneration.getBirds().size());
                if(this.currentGeneration.getBestFitness() > this.bestFitnessAllTime){
                    this.bestFitnessAllTime = this.currentGeneration.getBestFitness();
                    this.bestFitnessAllTimeLabel.setText("Best Fitness (All Time): " + this.bestFitnessAllTime);
                }
                break;
        }
    }

    /**
     * This method returns a boolean of whether Smart Flappy Bird should auto restart (auto restart when current
     * generation is empty)
     * @return
     */
    public boolean getAutoRestart(){
        switch(this){
            case SMART:
                return this.currentGeneration.isEmpty();
            default:
                return false;
        }
    }

    /**
     * This method auto restarts smart flappy bird by resetting the pipes passed, selecting the best birds in the current
     * generation, updates the labels for the current generation, gets the best weights in the current generation,
     * then creates a new generation and passed in the best weights
     * @param gamePane
     * @param nearestPipe
     */
    public void autoRestart(Pane gamePane, Pipe nearestPipe){
        switch(this){
            case SMART:
                this.pipesPassed = 0;
                this.currentGeneration.naturalSelection();
                this.bestFitnessPrevGenLabel.setText("Best Fitness (Prev. Generation): " + this.currentGeneration.getBestFitness());
                this.avgFitnessPrevGenLabel.setText("Average Fitness (Prev. Generation): " + this.currentGeneration.getAverageFitness());
                ArrayList<double[][]> bestSyn0WeightsPrevGen = this.currentGeneration.getBestSyn0Weights();
                ArrayList<double[][]> bestSyn1WeightsPrevGen = this.currentGeneration.getBestSyn1Weights();

                Generation newGeneration = new Generation(gamePane, nearestPipe, bestSyn0WeightsPrevGen, bestSyn1WeightsPrevGen, this.currentGeneration.getAverageFitness());
                this.currentGeneration = newGeneration;
                this.generationNum++;
                this.generationNumLabel.setText("Generation: " + this.generationNum);
                break;
        }
    }

    /**
     * This method restarts the game for the corresponding game mode when the restart button is pressed.
     */
    public void restart() {
        switch (this) {
            case MANUAL: case MULTIPLAYER:
                this.currentScore = 0;
                this.currentScoreLabel.setText("Score: " + this.currentScore);
                for (int i = 0; i < this.flappyBirds.size(); i++) {
                    this.flappyBirds.get(i).removeFromPane();
                    this.flappyBirds.remove(i);
                }
                break;
            case SMART:
                this.pipesPassed = 0;
                this.setGame();
                this.currentGeneration.removeAllBirds();
                break;
        }
    }

    /**
     * This method handles key press for the various modes: SPACE to make bird jump in Manual, SPACE and UP to make
     * the two birds jump in Multiplayer
     * @param e
     */
    public void handleKeyPress(KeyEvent e) {
        KeyCode keyPressed = e.getCode();
        switch (this) {
            case MANUAL:
                switch (keyPressed) {
                    case SPACE:
                        this.flappyBirds.get(0).jump();
                        break;
                    default:
                        break;
                }
                e.consume();
                break;
            case MULTIPLAYER:
                switch (keyPressed) {
                    case SPACE:
                        if(this.flappyBirds.size() == 2) {
                            this.flappyBirds.get(0).jump();
                        }
                        else if (this.flappyBirds.get(0).getPlayerNum() == 1){
                            this.flappyBirds.get(0).jump();
                        }
                        break;
                    case UP:
                        if(this.flappyBirds.size() == 2) {
                            this.flappyBirds.get(1).jump();
                        }
                        else if (this.flappyBirds.get(0).getPlayerNum() == 2){
                            this.flappyBirds.get(0).jump();
                        }
                        break;
                    default:
                        break;
                }
                e.consume();
                break;
        }
    }
}
