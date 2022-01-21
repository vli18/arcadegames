package evolution.flappybird;

import javafx.scene.effect.ColorAdjust;
import javafx.scene.layout.Pane;

/**
 * This SmartFlappyBird class is a subclass of FlappyBird, it wraps the graphical elements and functionalities of the
 * Smart Flappy Bird.
 */
public class SmartFlappyBird extends FlappyBird{

    private NeuralNetwork neuralNetwork;
    private double[][] inputNodes;
    private int fitness;
    private double deathPosition;

    /**
     * This constructor creates a brand new SmartFlappyBird with a new neural network and instantiates the input nodes,
     * it shares the same parameters as the superclass FlappyBird constructor
     * @param gamePane
     * @param playerNum
     * @param pipe
     */
    public SmartFlappyBird(Pane gamePane, int playerNum, Pipe pipe){
        super(gamePane, playerNum, pipe);
        this.neuralNetwork = new NeuralNetwork();
        this.inputNodes = new double[2][1];
    }

    /**
     * This constructor overloads the previous Smart FlappyBird constructor and is used to create a FlappyBird
     * with the same neural network except with the passed in best weights and the average fitness of the previous
     * generation
     * @param gamePane
     * @param playerNum
     * @param pipe
     * @param optimalSyn0
     * @param optimalSyn1
     * @param averageFitness
     */
    public SmartFlappyBird(Pane gamePane, int playerNum, Pipe pipe, double[][] optimalSyn0, double[][] optimalSyn1, int averageFitness){
        super(gamePane, playerNum, pipe);
        this.inputNodes = new double[2][1];
        this.neuralNetwork = new NeuralNetwork(optimalSyn0, optimalSyn1, averageFitness);
    }

    /**
     * This method overrides the set color method of the superclass by assigning a random color to the generated flappy
     * bird
     */
    @Override
    public void setColor(){
        ColorAdjust color = new ColorAdjust();
        color.setHue(-1 + (Math.random() * 2));
        super.flappyBirdNode.setEffect(color);
        super.flappyBirdNode.setOpacity(0.7);
    }

    /**
     * This method consistently updates the input nodes: the height of the flappy bird and the height of the top pipe for the nearest
     * pipe
     */
    public void updateInputNodes(){
        this.inputNodes[0][0] = super.flappyBirdNode.getY() / Constants.GAME_PANE_HEIGHT;
        this.inputNodes[1][0] = super.pipe.getTopPipeBottomY() / Constants.GAME_PANE_HEIGHT;
    }

    /**
     * This method activates the neural network and forward propagates the output node. It tells the bird to jump on its own
     * if the output node is above the jump threshold.
     */
    @Override
    public void jump(){
        if(this.neuralNetwork.forwardPropagation(this.inputNodes) > Constants.JUMP_THRESHOLD){
            super.currentVelocity = Constants.REBOUND_VELOCITY;
        }
    }

    /**
     * This method sets the fitness of the bird.
     * @param fitness
     */
    public void setFitness(int fitness){
        this.fitness = fitness;
    }

    /**
     * This method returns the fitness of the bird
     * @return
     */
    public int getFitness(){
        return this.fitness;
    }

    /**
     * This method sets the death position of the bird
     * @param yPos
     */
    public void setDeathPosition(double yPos){ this.deathPosition = yPos;}

    /**
     * This method returns the death position of the bird
     * @return
     */
    public double getDeathPosition(){ return this.deathPosition;}

    /**
     * The method returns the syn0 weights of the bird's neural network
     * @return
     */
    public double[][] getSyn0(){
        return this.neuralNetwork.getSyn0();
    }

    /**
     * This method returns the syn1 weights of the bird's neural network
     * @return
     */
    public double[][] getSyn1(){
        return this.neuralNetwork.getSyn1();
    }
}
