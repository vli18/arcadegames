package evolution.flappybird;

/**
 * This is the NeuralNetwork class that wraps a neural network and includes the mathematical functions
 * to generate and activate the various layers within the neural network.
 */
public class NeuralNetwork {

    private double[][] syn0;
    private double[][] syn1;

    /**
     * This is the default constructor that instantiates the 2D arrays that contain the syn0 and syn1 weights and
     * randomizes them.
     */
    public NeuralNetwork(){
        this.syn0 = new double[5][2];
        this.randomizeWeights(this.syn0);
        this.syn1 = new double[1][5];
        this.randomizeWeights(this.syn1);
    }

    /**
     * This is overloaded constructor the has parameters that take in the best weights from the previous generation and
     * the average fitness. It sets the weights in the bird to the best weights and mutates a random percentage of them.
     * @param optimalSyn0
     * @param optimalSyn1
     * @param averageFitness
     */
    public NeuralNetwork(double[][] optimalSyn0, double[][] optimalSyn1, int averageFitness){
        this.syn0 = optimalSyn0;
        this.mutateWeights(this.syn0, averageFitness);
        this.syn1 = optimalSyn1;
        this.mutateWeights(this.syn1, averageFitness);
    }

    /**
     * This method iterates through the 2D arrays of the weights and randomizes a value between -1 and 1 as the weights
     * of the bird
     * @param syn
     */
    private void randomizeWeights(double[][] syn){
        for(int i = 0; i < syn.length; i++){
            for(int j = 0; j < syn[0].length; j++){
                syn[i][j] = -1 + (Math.random() * 2);
            }
        }
    }

    /**
     * This method mutates a random percentage of weights in the respective 2D Array of weights. It represents
     * dynamic learning where when the average fitness of the generation increases, it decreases the mutation rate
     * of the weights to preserve the weights that were successful the previous generation to optimize learning.
     * @param syn
     * @param averageFitness
     */
    private void mutateWeights(double[][] syn, int averageFitness){
        double ranNum = Math.random();
        for (int i = 0; i < syn.length; i++) {
            for (int j = 0; j < syn[0].length; j++) {
                if(averageFitness > 3000){
                    syn[i][j] = syn[i][j];
                }
                else if(averageFitness > 1000){
                    if (ranNum < Constants.LOWEST_MUTATION_PROBABILITY) {
                        syn[i][j] = (-1 + (Math.random() * 2));
                    }
                    else{
                        syn[i][j] = syn[i][j];
                    }
                }
                else if(averageFitness > 600){
                    if (ranNum  < Constants.EVEN_LOWER_MUTATION_PROBABILITY) {
                        syn[i][j] = (-1 + (Math.random() * 2));
                    }
                    else{
                        syn[i][j] = syn[i][j];
                    }
                }
                else if(averageFitness >= 260){
                    if (ranNum < Constants.LOWER_MUTATION_PROBABILITY) {
                        syn[i][j] = (-1 + (Math.random() * 2));
                    }
                    else{
                        syn[i][j] = syn[i][j];
                    }
                }
                else if(averageFitness > 150){
                    if (ranNum < Constants.LOW_MUTATION_PROBABILITY) {
                        syn[i][j] = (-1 + (Math.random() * 2));
                    }
                    else{
                        syn[i][j] = syn[i][j];
                    }
                }
                else{
                    if (Math.random() < Constants.MUTATION_PROBABILITY) {
                        syn[i][j] = (-1 + (Math.random() * 2));
                    }
                    else{
                        syn[i][j] = syn[i][j];
                    }
                }
            }
        }
    }

    /**
     * This method performs dot product between two 2D arrays and returns the result of the dot product
     * @param A
     * @param B
     * @return
     */
    private static double[][] dotProduct(double[][] A, double[][] B){
        int m = A.length;
        int n = A[0].length;
        int p = B.length;
        int q = B[0].length;
        double[][] dotProduct = null;
        if(n == p){
            double[][] C = new double[m][q];
            for(int row = 0; row < m; row++){
                for(int col = 0; col < q; col++){
                    for(int i = 0; i < p; i++){
                        C[row][col] += A[row][i] * B[i][col];
                    }
                }
            }
            dotProduct = C;
        }
        return dotProduct;
    }

    /**
     * This method forward propagates the layers within the neural network and returns a double as the forward propagated
     * output node of the neural network. It performs dot products between the weights and the inputnodes and the hidden
     * layer to reach the outcome of the neural network.
     * @param inputNodes
     * @return
     */
    public double forwardPropagation(double[][] inputNodes){
        double[][] hiddenLayer = dotProduct(this.syn0, inputNodes);
        activate(hiddenLayer);
        double[][] outputNode = dotProduct(this.syn1, hiddenLayer);
        activate(outputNode);
        return outputNode[0][0];
    }

    /**
     * This method performs the sigmoid function on an x value (squishes a value down to b a number between 0 and 1)
     * @param x
     * @return
     */
    private static double sigmoid(double x) {
        return (1 / (1 + Math.pow(Math.E, (-1 * x))));
    }

    /**
     * This method activates the values in a 2D array through performing sigmoid function on them
     * @param x
     */
    private static void activate(double[][] x) {
        for (int i = 0; i < x.length; i++) {
            for (int j = 0; j < x[0].length; j++) {
                x[i][j] = sigmoid(x[i][j]);
            }
        }
    }

    /**
     * This method returns the syn0 weights of the neural network
     * @return
     */
    public double[][] getSyn0(){
        return this.syn0;
    }

    /**
     * This method returns the syn1 weights of the neural network
     * @return
     */
    public double[][] getSyn1(){
        return this.syn1;
    }
}
