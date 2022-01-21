package evolution.doodlejump;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.util.Duration;
import java.util.ArrayList;

/**
 * This is the DoodleJumpGame class where the logic of the game is established through the various methods called.
 * It creates an instance of doodle, checks for collisions between the platforms, updates the score, and randomly
 * displays the platforms on the screen.
 */
public class DoodleJumpGame implements evolution.Playable {

    private Pane gamePane;
    private Timeline timeline;
    private Label gameStatus;
    private Button restart;
    private Label scoreCountLabel;
    private Doodle doodle;
    private ArrayList<Platform> platforms;
    private Platform topPlatform;
    private Platform mostRecentPlatform;
    private ArrayList<Enemy> enemies;
    private Enemy mostRecentEnemy;
    private int scoreCountTotal;
    private boolean paused;

    /**
     * This method starts the program. It instantiates the Arraylists and sets up the game pane,
     * generates platforms(adds them to the Platform arraylist), and sets up the timeline
     */
    public DoodleJumpGame(Pane gamePane, Timeline timeline, Label gameStatus, Button restart){
        this.gamePane = gamePane;
        this.timeline = timeline;
        this.gameStatus = gameStatus;
        this.restart = restart;
        this.platforms = new ArrayList<Platform>();
        this.enemies = new ArrayList<Enemy>();
        this.doodle = new Doodle(this.gamePane);

        this.addToPanes();
        this.setUpGame();
        this.generatePlatforms();
        this.addToTimeline();
    }

    /**
     This is the setUpTimeline() method where the various keyframes used are added onto the timeline and updated
     every 30 milliseconds.
     This allows for the program to check use the updateGameMovement() method to update the locations of the doodle and
     platforms, check collisions, remove platforms, and move/scroll the platforms
     */
    public void addToTimeline() {
        KeyFrame kf = new KeyFrame(Duration.millis(30), (ActionEvent e) -> this.updateGame());

        this.timeline.getKeyFrames().addAll(kf);
        this.timeline.play();
    }

    private void addToPanes(){
        this.gameStatus.setLayoutX((Constants.APP_WIDTH / 2) - 100);
        this.gameStatus.setLayoutY((Constants.APP_HEIGHT / 2) - 30);

        this.scoreCountLabel = new Label();
        this.scoreCountLabel = new Label();
        this.scoreCountLabel.setLayoutX(0);
        this.scoreCountLabel.setLayoutY(0);

        this.restart.setOnAction((ActionEvent e) -> this.restart());

        this.gamePane.getChildren().addAll(this.gameStatus, this.scoreCountLabel);
    }
    /**
     This is the setGamePane() method where a new instance of the Pane is called and set as the gamePane for the
     doodlejump game.
     This method sets up the different components of the game so that the doodle, labels, and platforms are displayed
     when the program is run.
     */
    private void setUpGame() {
        //Moves doodle on key input
        this.scoreCountTotal = 0;
        this.topPlatform = new StandardPlatform(this.gamePane, Constants.DOODLE_START_X, Constants.DOODLE_START_Y);
        this.platforms.add(this.topPlatform);

        this.gamePane.setFocusTraversable(true);
        this.gamePane.setOnKeyPressed((KeyEvent e) -> this.handleKeyPress(e));
    }

    /**
     This updateGameMovement() method is the method that's called when the keyframe updates every 30 milliseconds on
     the timeline.
     It allows for doodle to wrap to the other side of the screen, move, and jump when colliding with a platform.
     It's also responsible for the scrolling component, removing platforms that have left the screen, and displaying
     the game over label.
     */
    public void updateGame() {
        this.checkDoodleWrap(); //makes doodle wrap around to the other side of the screen
        this.updateDoodle(); //updates the physics of doodle
        this.jumpOnCollision(); //makes doodle jump on collision
        this.checkEnemyCollision(); //checks if doodle collides with enemy
        this.gameOver(); //checks if game is over and stops game when doodle falls
        this.scrollPlatforms(); //scrolls the platforms
        this.removeOutsidePlatforms(); //removes the platforms that are offscreen
        this.removeOutsideEnemies(); //removes the enemies that are offscreen
        this.movePlatform(); //makes moving platforms move
    }

    /**
     This is the moveDoodle() method that is responsible for physically moving the doodle horizontally across the
     screen.
     Pressing the right arrow key will allow the doodle to move right and pressing the left direction will allow the
     doodle to move left.
     */
    public void handleKeyPress(KeyEvent e) {
        KeyCode keyPressed = e.getCode();
        switch (keyPressed) {
            case RIGHT:
                if(!paused){
                    this.doodle.setXPos(this.doodle.getXPos() + Constants.DOODLE_DISTANCE);
                }
                break;
            case LEFT:
                if(!paused){
                    this.doodle.setXPos(this.doodle.getXPos() - Constants.DOODLE_DISTANCE);
                }
                break;
            case P:
                this.pause();
                break;
            default:
                break;
        }
        e.consume();
    }

    /**
     The checkDoodleWrap() method will allow for the doodle to "wrap" around to the other side of the screen when it
     leaves the width of the screen on one side.
     */
    private void checkDoodleWrap() {
        if (this.doodle.getXPos() > Constants.APP_WIDTH) {
            this.doodle.setXPos(0);
        } else if (this.doodle.getXPos() < 0) {
            this.doodle.setXPos(280);
        }
    }

    /**
     The updateDoodle() method updates the position of the doodle everytime it is called through the
     updateGameMovement() method which is checked every 30 milliseconds through the timeline's keyframe.
     */
    private void updateDoodle() {
        this.doodle.updatePhysics();
        this.doodle.setYPos(this.doodle.getYPos());
    }

    /**
     * This method moves the moving platforms across the screen
     */
    private void movePlatform(){
        for(Platform platform : platforms){
            platform.updateDirection();
            platform.movePlatform();
        }
    }

    /**
     This is the jumpOnCollision() method that checks for the collision between the platforms and doodle.

     If there is a collision between the two, it will then check to see if the platform is a disappearing platform
     or a game over platform.
     - If it is a disappearing platform, the platform will be removed both logically and graphically after the collision.
     However, the doodle will still be able to jump off of it.
     - If it is a game over platform, the doodle will no longer be able to move, the timeline will be stopped, and the
     game over label will be displayed on the screen.
     */
    private void jumpOnCollision() {
        for(int i = 0; i < this.platforms.size(); i++){
            this.doodle.checkCollision(platforms.get(i));
            if(this.doodle.getIsColliding() && this.platforms.get(i).isDisappearingPlatform()){
                Platform disappearingPlatform = this.platforms.remove(i);
                disappearingPlatform.wrapper.removePlatform(this.gamePane);
            }
            else if(this.doodle.getIsColliding() && this.platforms.get(i).isGameOverPlatform()){
                this.gameStatus.setText("Game Over");
                this.gameStatus.toFront();
                this.gamePane.setOnKeyPressed(null);
                timeline.stop();
            }
        }
    }

    /**
     * This method checks if doodle is colliding with an Enemy and updates the game over label and ends the game if
     * doodle intersects with an enemy
     */
    private void checkEnemyCollision(){
        for(int j = 0; j < this.enemies.size(); j++){
            if(this.doodle.collidesWithEnemy(this.enemies.get(j))) {
                this.gameStatus.setText("Game Over");
                this.gameStatus.toFront();
                this.gamePane.setOnKeyPressed(null);
                timeline.stop();
            }
        }
    }

    /**
     This is the randomizePlatform() method which randomizes the different types of platform within the arraylist.

     It sets the randomly chosen platform to an instance of platform, mostRecentPlatform, which is then added to the
     arrayList to be displayed.

     In order to make standard platforms the most common kind of platform displayed, multiple cases were assigned to
     the standard platform.

     It also creates an enemy randomly on top of a standard platform, disappearing platform, or extra bouncy platform.
     */
    private void randomizePlatform(double x, double y) {
        int ranNum1 = (int) (Math.random() * 11);
        int ranNum2 = (int) (Math.random() * 10);
        switch (ranNum1) {
            case 0: case 1: case 2: case 3:
                this.mostRecentPlatform = new StandardPlatform(this.gamePane, x, y);
                this.createEnemy(ranNum2, x, y);
                break;
            case 4: case 5:
                this.mostRecentPlatform = new MovingPlatform(this.gamePane, x, y);
                break;
            case 6: case 7:
                this.mostRecentPlatform = new DisappearingPlatform(this.gamePane, x, y);
                this.createEnemy(ranNum2, x, y);
                break;
            case 8: case 9:
                this.mostRecentPlatform = new ExtraBouncyPlatform(this.gamePane, x, y);
                this.createEnemy(ranNum2, x, y);
                break;
            case 10:
                this.mostRecentPlatform = new GameOverPlatform(this.gamePane, x, y);
                break;
            default:
                break;
        }
        this.platforms.add(this.mostRecentPlatform);
    }

    /**
     * This method creates an enemy and adds it graphically and logically if the random value passed in is equal
     * to 0
     */
    private void createEnemy(double randomValue, double x, double y){
        if(randomValue == 0){
            this.mostRecentEnemy = new Enemy(this.gamePane, x, y - Constants.ENEMY_Y_OFFSET);
            this.enemies.add(this.mostRecentEnemy);
        }
    }

    /**
     This is the generatePlatforms() method which is responsible for displaying platforms onto the screen, it also
     helps update the Enemies ArrayList.
     */
    private void generatePlatforms() {
        while (this.topPlatform.wrapper.getPlatformY() >= 0) {
            double minX = Math.max(0, this.topPlatform.wrapper.getPlatformX() - Constants.DOODLE_X_OFFSET);
            double maxX = Math.min(Constants.APP_WIDTH - Constants.PLATFORM_WIDTH, this.topPlatform.wrapper.getPlatformX() + Constants.DOODLE_X_OFFSET);
            double ranX = minX + (int) (Math.random() * (maxX - minX + 1));

            double minY = this.topPlatform.wrapper.getPlatformY() - Constants.DOODLE_Y_OFFSET_MIN;
            double maxY = this.topPlatform.wrapper.getPlatformY() - Constants.DOODLE_Y_OFFSET_MAX;
            double ranY = minY + (int) (Math.random() * (maxY - minY + 1));

            this.randomizePlatform(ranX, ranY);
            this.topPlatform = this.mostRecentPlatform;
        }
    }

    /**
     * This method moves the platforms and enemies down everytime Doodle hits the midpoint of the screen to simulate
     * the screen "scrolling down." It also updates the total score count everytime the screen "scrolls" based on
     * how much doodle has moved up
     */
    private void scrollPlatforms() {
        if(this.doodle.getYPos() < Constants.APP_HEIGHT / 2) {
            double difference = (Constants.APP_HEIGHT / 2) - this.doodle.currentPosition;
            this.doodle.currentPosition = Constants.APP_HEIGHT / 2;
            this.setScoreCount(difference);
            this.generatePlatforms();
            this.scrollEnemies(difference);
            for (int i = 0; i < this.platforms.size(); i++) {
                this.platforms.get(i).wrapper.setPlatformX(this.platforms.get(i).wrapper.getPlatformX());
                this.platforms.get(i).wrapper.setPlatformY(this.platforms.get(i).wrapper.getPlatformY() + difference);
            }
        }
        else{
            this.setScoreCount(0);
        }
    }

    /**
     * This method helps moves the enemies down the screen to help simulate the scrolling
     */
    private void scrollEnemies(double value) {
        for (int i = 0; i < this.enemies.size(); i++) {
            this.enemies.get(i).setEnemyLocation(this.enemies.get(i).getEnemyX(), this.enemies.get(i).getEnemyY() + value);
        }
    }

    /**
     * This method removes the platforms that go offscreen graphically and logically (remove from pane and arraylist)
     */
    private void removeOutsidePlatforms(){
        for (int i = 0; i < this.platforms.size(); i++) {
            if(this.platforms.get(i).wrapper.getPlatformY() > Constants.APP_HEIGHT){
                Platform removedPlatform = this.platforms.remove(i);
                removedPlatform.wrapper.removePlatform(this.gamePane);
            }
        }
    }

    /**
     * This method removes the enemies that go offscreen graphically and logically (remove from pane and arraylist)
     */
    private void removeOutsideEnemies(){
        for (int i = 0; i < this.enemies.size(); i++) {
            if(this.enemies.get(i).getEnemyY() > Constants.APP_HEIGHT){
                Enemy removedEnemy = this.enemies.remove(i);
                removedEnemy.removeEnemy(this.gamePane);
            }
        }
    }

    /**
     * This method increments the total score count by the passed in value and updates the score count label with
     * the accurate score count
     */
    private void setScoreCount(double value) {
        this.scoreCountTotal += value;
        this.scoreCountLabel.setText("Score = " + this.scoreCountTotal);
    }

    /**
     * This method makes the game end when doodle falls out of screen, it sets the game over label, stops
     * key input movement, and stops the timeline
     */
    private void gameOver(){
        if(this.doodle.getYPos() > Constants.APP_HEIGHT){
            this.gameStatus.setText("Game Over");
            this.gameStatus.toFront();
            this.gamePane.setOnKeyPressed(null);
            timeline.stop();
        }
    }

    public void pause(){
        this.paused = !this.paused;
        if(this.paused){
            this.gameStatus.setText("Paused");
            this.gameStatus.setLayoutX((Constants.APP_WIDTH / 2) - 70);
            this.gameStatus.toFront();
            this.timeline.pause();
        }
        else{
            this.gameStatus.setText("");
            this.gameStatus.setLayoutX((Constants.APP_WIDTH / 2) - 100);
            this.gameStatus.toFront();
            this.timeline.play();
        }
    }

    public void restart(){
        this.doodle.setXPos(Constants.DOODLE_START_X);
        this.doodle.setYPos(Constants.DOODLE_START_Y);
        for(int i = 0; i < this.platforms.size(); i++){
            Platform removedPlatform = this.platforms.remove(i);
            removedPlatform.wrapper.removePlatform(this.gamePane);
        }
        for (int i = 0; i < this.enemies.size(); i++) {
            Enemy removedEnemy = this.enemies.remove(i);
            removedEnemy.removeEnemy(this.gamePane);
        }
        this.gameStatus.setText("");
        timeline.play();
        this.gamePane.requestFocus();
        this.setUpGame();
    }
}