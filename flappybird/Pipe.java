package evolution.flappybird;

import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

/**
 * This is the Pipe class that wraps and graphically creates the pipes.
 */
public class Pipe {

    private Pane gamePane;
    private ImageView topPipeNode;
    private ImageView topPipeRimNode;
    private ImageView bottomPipeNode;
    private ImageView bottomPipeRimNode;
    private int bottomPipeHeight;
    private int topPipeHeight;
    private int bottomPipeY;
    private double totalTopPipeHeight;
    private double totalBottomPipeHeight;

    /**
     * This is the constructor of the Pipe class. It is associated to the game Pane and takes in a starting x position value.
     * It calls the methods that randomize the position of the pipe gap, graphically sets the pipe, and sets the pipe
     * to its starting position
     * @param gamePane
     * @param startX
     */
    public Pipe(Pane gamePane, double startX){
        this.gamePane = gamePane;

        this.randomize();
        this.setPipe();
        this.setX(startX);
    }

    /**
     * This method randomizes the height of the top and bottom pipes to have a consistent gap height between the two
     * pipes.
     */
    private void randomize(){
        this.bottomPipeHeight = (int) (Constants.PIPE_MIN_HEIGHT + ((Constants.PIPE_MAX_HEIGHT - Constants.PIPE_MIN_HEIGHT + 1) * Math.random()));
        this.topPipeHeight = Constants.GAME_PANE_HEIGHT - Constants.PIPE_GAP_DISTANCE - this.bottomPipeHeight;

        this.bottomPipeY = Constants.GAME_PANE_HEIGHT - this.bottomPipeHeight;
    }

    /**
     * This method sets the x position for all the components that make up the whole pipe
     * @param x
     */
    public void setX(double x){
        this.topPipeNode.setX(x);
        this.bottomPipeNode.setX(x);
        this.topPipeRimNode.setX(x - 5);
        this.bottomPipeRimNode.setX(x - 5);
    }

    /**
     * this method returns the x position of the top pipe
     * @return
     */
    public double getX(){
        return this.topPipeNode.getX();
    }

    /**
     * This method returns the y position of the bottom of the top pipe
     * @return
     */
    public double getTopPipeBottomY(){
        return this.totalTopPipeHeight;
    }

    /**
     * This method graphically sets up the pipes by instantiating the images that create it and setting their position
     * and the randomized height of the pipes and adds them graphically to the game pane.
     */
    private void setPipe(){
        Image topPipe = new Image
                ("/evolution/flappybird/pipe tube.png", Constants.PIPE_WIDTH, this.topPipeHeight, false, true);
        this.topPipeNode = new ImageView(topPipe);
        this.topPipeNode.setY(0);

        Image topPipeRim = new Image
                ("/evolution/flappybird/pipe rim.png", Constants.PIPE_RIM_WIDTH, Constants.PIPE_RIM_HEIGHT, false, true);
        this.topPipeRimNode = new ImageView(topPipeRim);
        this.topPipeRimNode.setY(this.topPipeHeight);

        Image bottomPipe = new Image
                ("/evolution/flappybird/pipe tube.png", Constants.PIPE_WIDTH, this.bottomPipeHeight, false, true);
        this.bottomPipeNode = new ImageView(bottomPipe);
        this.bottomPipeNode.setY(this.bottomPipeY);

        Image bottomPipeRim = new Image
                ("/evolution/flappybird/pipe rim.png", Constants.PIPE_RIM_WIDTH, Constants.PIPE_RIM_HEIGHT, false, true);
        this.bottomPipeRimNode = new ImageView(bottomPipeRim);
        this.bottomPipeRimNode.setY(this.bottomPipeY - Constants.PIPE_RIM_HEIGHT);

        this.totalTopPipeHeight = this.topPipeHeight + Constants.PIPE_RIM_HEIGHT;
        this.totalBottomPipeHeight = this.bottomPipeHeight + Constants.PIPE_RIM_HEIGHT;

        this.gamePane.getChildren().addAll(topPipeNode, topPipeRimNode, bottomPipeNode, bottomPipeRimNode);
    }

    /**
     * This method returns the boolean of whether a top pipe or bottom pipe intersects with a bird
     * @param flappyBirdNode
     * @return
     */
    public boolean hitBird(Node flappyBirdNode){
        if(flappyBirdNode.intersects(this.topPipeRimNode.getX(), 0, Constants.PIPE_RIM_WIDTH, this.totalTopPipeHeight)){
            return true;
        }
        else if(flappyBirdNode.intersects(this.bottomPipeRimNode.getX(), this.bottomPipeY - Constants.BIRD_HEIGHT, Constants.PIPE_RIM_WIDTH, this.totalBottomPipeHeight)){
            return true;
        }
        else{
            return false;
        }
    }

    /**
     * This method returns a boolean of whether a pipe was passed by a bird.
     * @return
     */
    public boolean passed(){
        if(this.getX() <= Constants.BIRD_START_X - Constants.PIPE_RIM_WIDTH + 1 && this.getX() >= Constants.BIRD_START_X - Constants.PIPE_RIM_WIDTH - 3){
            return true;
        }
        else{
            return false;
        }
    }

    /**
     * This method removes all the components of the pipe graphically from the game pane
     */
    public void removeFromPane(){
        this.gamePane.getChildren().removeAll(this.topPipeNode, this.bottomPipeNode, this.topPipeRimNode, this.bottomPipeRimNode);
    }
}
