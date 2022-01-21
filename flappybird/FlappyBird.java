package evolution.flappybird;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

/**
 * This is the FlappyBird superclass that wraps the flappy bird images. It graphically sets up the bird and updates
 * the bird's velocity, position, and score
 */
public class FlappyBird {

    private Pane gamePane;
    private int playerNum;
    public Pipe pipe;
    public Image flappyBird;
    public ImageView flappyBirdNode;
    public double currentVelocity;
    private double updatedVelocity;
    private double currentPosition;
    private double updatedPosition;
    private int score;

    /**
     * This is the FlappyBird constructor that associates the class to the game pane and the nearest pipe. It instantiates
     * the instance variables and sets up the flappy bird.
     * @param gamePane
     * @param playerNum
     * @param pipe
     */
    public FlappyBird(Pane gamePane, int playerNum, Pipe pipe){
        this.gamePane = gamePane;
        this.playerNum = playerNum;
        this.pipe = pipe;

        this.setFlappyBird();
    }

    /**
     * This method sets up the flappy bird by instantiating the flappy bird Image and adds it to an ImageView node. It
     * sets the color of the bird. It sets the flappy bird to its starting position and adds the bird to the game pane.
     */
    private void setFlappyBird(){
        this.flappyBird = new Image
                ("/evolution/flappybird/flappy bird.png", Constants.BIRD_WIDTH, Constants.BIRD_HEIGHT, true, true);
        this.flappyBirdNode = new ImageView(flappyBird);
        this.setColor();
        this.flappyBirdNode.setX(Constants.BIRD_START_X);
        this.flappyBirdNode.setY(Constants.BIRD_START_Y);
        this.gamePane.getChildren().add(flappyBirdNode);
    }

    /**
     * This method graphically removes the flappy bird from the game pane
     */
    public void removeFromPane(){
        this.gamePane.getChildren().remove(this.flappyBirdNode);
    }

    /**
     * This method sets the color of the bird. If player number is 2, it creates a blue bird (for multiplayer mode)
     */
    public void setColor(){
        if(this.playerNum == 2){
            Image flappyBird2 = new Image
                    ("/evolution/flappybird/flappy bird 2.png", Constants.BIRD_WIDTH, Constants.BIRD_HEIGHT, true, true);
            this.flappyBirdNode = new ImageView(flappyBird2);
        }
    }

    /**
     * This method sets the y position of the bird.
     * @param y
     */
    public void setY(double y){
        this.flappyBirdNode.setY(y);
    }

    /**
     * This method returns the y position of the bird.
     * @return
     */
    public double getY() {
        return this.currentPosition;
    }

    /**
     * This method updates the physics of the bird to allow to fall with gravity. It updates the velocity and position
     * and sets the current velocity and position to the updated ones.
     */
    public void updatePhysics() {
        this.updatedVelocity = this.currentVelocity + Constants.ACCELERATION * Constants.DURATION;
        this.updatedPosition = this.currentPosition + this.updatedVelocity * Constants.DURATION;
        if(updatedPosition > 0){
            this.currentVelocity = this.updatedVelocity;
            this.currentPosition = this.updatedPosition;
        }
        else{
            this.currentVelocity = this.updatedVelocity;
            this.currentPosition = 0;
        }
    }

    /**
     * This method makes the bird jump with called by setting current velocity to rebound velocity
     */
    public void jump() {
        this.currentVelocity = Constants.REBOUND_VELOCITY;
    }

    /**
     * This method returns a boolean of whether the bird hits the ground (intersects with the bottom of the game pane)
     * @return
     */
    public boolean hitGround(){
        if(this.flappyBirdNode.getY() > Constants.GAME_PANE_HEIGHT && this.flappyBirdNode.getY() > Constants.GAME_PANE_HEIGHT - 10){
            return true;
        }
        else {
            return false;
        }
    }

    /**
     * This method updates the pipe associated to the bird to the nearest pipe.
     * @param pipe
     */
    public void updateNearestPipe(Pipe pipe){
        this.pipe = pipe;
    }

    /**
     * This method returns a boolean of whether bird collided with the nearest pipe
     * @return
     */
    public boolean hitPipe(){
        return this.pipe.hitBird(this.flappyBirdNode);
    }

    /**
     * This method returns the player number
     * @return
     */
    public int getPlayerNum(){
        return this.playerNum;
    }

    /**
     * This method sets the score of the bird
     * @param score
     */
    public void setScore(int score){
        this.score = score;
    }

    /**
     * This method returns the score of the bird
     * @return
     */
    public int getScore(){
        return this.score;
    }

    /**
     * This method sets the fitness of the bird
     * @param fitness
     */
    public void setFitness(int fitness) {}

    /**
     * This method returns the fitness of the bird
     */
    public int getFitness(){return 0;}

    /**
     * This method returns the syn0 weights of the bird
     * @return
     */
    public double[][] getSyn0(){return null;}

    /**
     * This method returns the syn1 weights of the bird
     * @return
     */
    public double[][] getSyn1() {
        return null;
    }

    /**
     * This method sets the position in which the bird died at
     * @param yPos
     */
    public void setDeathPosition(double yPos){}

    /**
     * This method returns the death position of the bird
     * @return
     */
    public double getDeathPosition(){return 0;}

    /**
     * This method updates the input nodes of the bird
     */
    public void updateInputNodes(){}
}
