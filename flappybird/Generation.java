package evolution.flappybird;

import javafx.scene.layout.Pane;
import java.util.ArrayList;

/**
 * This is the Generation class. It wraps an ArrayList of SmartFlappyBirds and contains methods that affects FlappyBirds
 * within a Generation.
 */
public class Generation {

    private Pane gamePane;
    private Pipe nearestPipe;
    private ArrayList<FlappyBird> smartFlappyBirds;
    private ArrayList<FlappyBird> deadFlappyBirds;
    private ArrayList<Integer> fitnessTracker;
    private ArrayList<double[][]> bestSyn0Weights;
    private ArrayList<double[][]> bestSyn1Weights;
    private int bestFitnessCurrentGen;
    private int numBirdsSelected;

    /**
     * This is the Generation default Constructor, it is associated with the gamePane and the nearest Pipe.
     * @param gamePane
     * @param nearestPipe
     */
    public Generation(Pane gamePane, Pipe nearestPipe){
        this.gamePane = gamePane;
        this.nearestPipe = nearestPipe;
        this.smartFlappyBirds = new ArrayList<FlappyBird>();
        this.deadFlappyBirds = new ArrayList<FlappyBird>();
        this.fitnessTracker = new ArrayList<Integer>();
        this.bestSyn0Weights = new ArrayList<double[][]>();
        this.bestSyn1Weights = new ArrayList<double[][]>();
        this.setBirdsNewGen();
    }

    /**
     * This is the Generation overload Constructor, it is associated with the gamePane and the nearest Pipe and
     * the best syn0 and syn1 weights from the birds in the previous generation.
     * @param gamePane
     * @param nearestPipe
     */
    public Generation(Pane gamePane, Pipe nearestPipe, ArrayList<double[][]> bestSyn0Weights, ArrayList<double[][]> bestSyn1Weights, int averageFitness){
        this.gamePane = gamePane;
        this.nearestPipe = nearestPipe;
        this.smartFlappyBirds = new ArrayList<FlappyBird>();
        this.deadFlappyBirds = new ArrayList<FlappyBird>();
        this.fitnessTracker = new ArrayList<Integer>();
        this.setBirdsSameGen(bestSyn0Weights, bestSyn1Weights, averageFitness);
        this.bestSyn0Weights = new ArrayList<double[][]>();
        this.bestSyn1Weights = new ArrayList<double[][]>();
    }

    /**
     * This method sets the 50 smart flappy birds in a completely new generation
     */
    public void setBirdsNewGen(){
        for (int i = 1; i <= 50; i++) {
            FlappyBird smartFlappyBird = new SmartFlappyBird(gamePane, i, this.nearestPipe);
            this.smartFlappyBirds.add(smartFlappyBird);
            this.fitnessTracker.add(0);
        }
    }

    /**
     * This method sets the 50 smart flappy birds in the same collection of generations, the best weights are passed
     * down to birds based on the number of birds selected
     */
    public void setBirdsSameGen(ArrayList<double[][]> bestSyn0Weights, ArrayList<double[][]> bestSyn1Weights, int averageFitness){
        for (int i = 1; i <= bestSyn0Weights.size(); i++) {
            int startIndex = ((50 / bestSyn0Weights.size()) * i) - ((50 / bestSyn0Weights.size()) - 1);
            int endIndex = (50 / bestSyn0Weights.size()) * i;
            for(int j = startIndex; j <= endIndex; j++){
                FlappyBird smartFlappyBird = new SmartFlappyBird(gamePane, j, this.nearestPipe, bestSyn0Weights.get(i - 1), bestSyn1Weights.get(i - 1), averageFitness);
                this.smartFlappyBirds.add(smartFlappyBird);
                this.fitnessTracker.add(0);
            }
        }
    }

    /**
     * This method updates all the birds in the ArrayList of Smart Flappy Birds. It updates the nearest pipe and the
     * input nodes. It also tells the bird to based on the input nodes. It also tracks the fitness of all the birds
     */
    public void updateBirds(Pipe pipe, int counter){
        for(int i = 0; i < this.smartFlappyBirds.size(); i++){
            this.smartFlappyBirds.get(i).updatePhysics();
            this.smartFlappyBirds.get(i).setY(this.smartFlappyBirds.get(i).getY());
            this.smartFlappyBirds.get(i).updateNearestPipe(pipe);
            this.smartFlappyBirds.get(i).updateInputNodes();
            this.smartFlappyBirds.get(i).jump();

            this.fitnessTracker.set(i, counter); //updates fitness of birds of corresponding index
        }
    }

    /**
     * This method updates the best fitness within all the fitnesses tracked asscoiated with the birds
     */
    public void updateBestFitness(){
        for(int i = 0; i < this.fitnessTracker.size(); i++) {
            if((int) this.fitnessTracker.get(i) > this.bestFitnessCurrentGen){
                this.bestFitnessCurrentGen = (int) this.fitnessTracker.get(i);
            }
        }
    }

    /**
     * This method returns whether all the birds passed the first pipe and updates the number of birds that should be
     * selected.
     */
    private boolean selectBestBirds(){
        this.numBirdsSelected = 0;
        int numFail = 0;
        for(int i = 0; i < this.deadFlappyBirds.size(); i++) {
            if(this.deadFlappyBirds.get(i).getFitness() < Constants.FITNESS_THRESHOLD){
                numFail++;
            }
        }
        if(numFail == 50){
            return false;
        }
        else{
            this.numBirdsSelected = 50 - numFail;
            return true;
        }
    }

    /**
     * This method naturally selects the birds to take the best weights from based on the birds' fitness levels and
     * their relationship to the best fitness in the population of birds within one population. If all the birds died
     * before the first pipe, all 50 birds will have randomized weights
     */
    public void naturalSelection() {
        if (this.selectBestBirds()) {
            for (int i = 50 - numBirdsSelected; i < this.deadFlappyBirds.size(); i++) {
                if (this.deadFlappyBirds.get(i).getFitness() > Constants.FITNESS_THRESHOLD - this.bestFitnessCurrentGen + Constants.SELECTION_RATE) {
                    this.addBestWeights(i);
                }
            }
        } else {
            this.addRandomWeights();
        }
    }

    /**
     * This method adds all the best weights to the array of bestSyn0Weights and bestSyn1Weights
     * @param i
     */
    private void addBestWeights(int i){
        this.bestSyn0Weights.add(this.deadFlappyBirds.get(i).getSyn0());
        this.bestSyn1Weights.add(this.deadFlappyBirds.get(i).getSyn1());
    }

    /**
     * This method adds random weights to all 50 new smart flappy birds in the generation
     */
    private void addRandomWeights(){
        for(int i = 0; i < 50; i++){
            double[][] syn0 = new double[3][2];
            double[][] syn1 = new double[1][3];
            for(int a = 0; a < syn0.length; a++){
                for(int b = 0; b < syn0[0].length; b++){
                    syn0[a][b] = -1 + (Math.random() * 2);

                }
            }
            for(int c = 0; c < syn1.length; c++){
                for(int d = 0; d < syn1[0].length; d++){
                    syn1[c][d] = -1 + (Math.random() * 2);

                }
            }
            this.bestSyn0Weights.add(syn0);
            this.bestSyn1Weights.add(syn1);
        }
    }

    /**
     * This method returns the array of best syn0 weights
     * @return
     */
    public ArrayList<double[][]> getBestSyn0Weights(){
        return this.bestSyn0Weights;
    }

    /**
     * This method returns the array of best syn1 weights
     * @return
     */
    public ArrayList<double[][]> getBestSyn1Weights(){
        return this.bestSyn1Weights;
    }

    /**
     * This method returns the best fitness in the current generation
     * @return
     */
    public int getBestFitness(){
        return this.bestFitnessCurrentGen;
    }

    /**
     * This method returns the average fitness in the current Generation
     * @return
     */
    public int getAverageFitness(){
        if(!this.deadFlappyBirds.isEmpty()){
            return (int) ((this.deadFlappyBirds.get(0).getFitness() + this.deadFlappyBirds.get(this.deadFlappyBirds.size() - 1).getFitness()) / 2);
        }
        else{
            return 0;
        }
    }

    /**
     * This method returns the Arraylist of Smart flappy birds
     * @return
     */
    public ArrayList<FlappyBird> getBirds(){
        return this.smartFlappyBirds;
    }

    /**
     * This method checks whether the birds in the ArrayLlist collde with the pipes or the ground
     */
    public void checkCollision(){
        for (int i = 0; i < this.smartFlappyBirds.size(); i++) {
            if (this.smartFlappyBirds.get(i).hitPipe() || this.smartFlappyBirds.get(i).hitGround()) {
                this.smartFlappyBirds.get(i).removeFromPane();
                this.smartFlappyBirds.get(i).setFitness(this.fitnessTracker.get(i));
                this.smartFlappyBirds.get(i).setDeathPosition(this.smartFlappyBirds.get(i).getY());
                this.deadFlappyBirds.add(this.smartFlappyBirds.get(i));
                this.smartFlappyBirds.remove(i);
                this.fitnessTracker.remove(i);
            }
        }
    }

    /**
     * This method removes all the birds in the ArrayList graphically and logically
     */
    public void removeAllBirds(){
        for (int i = 0; i < this.smartFlappyBirds.size(); i++) {
            this.smartFlappyBirds.get(i).removeFromPane();
            this.smartFlappyBirds.remove(i);
        }
        for (int j = 0; j < this.deadFlappyBirds.size(); j++) {
            this.deadFlappyBirds.remove(j);
        }
    }

    /**
     * This method returns whether the smart flappy birds arraylist is empty
     * @return
     */
    public boolean isEmpty(){
        return this.smartFlappyBirds.isEmpty();
    }
}
