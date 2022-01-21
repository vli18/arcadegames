package evolution.doodlejump;

import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Ellipse;
import javafx.scene.shape.Rectangle;
import javafx.scene.transform.Rotate;

/**
This is the Doodle class where the flower composite shape components of the doodle are declared and instantiated.

This class is also where the collisions between the doodle and the platforms are checked.

The gravity/physics of the program as well as doodle's movements are also calculated here.
 */
public class Doodle {

    private Circle flowerCenter;
    private Circle flowerPetal1;
    private Circle flowerPetal2;
    private Circle flowerPetal3;
    private Circle flowerPetal4;
    private Circle flowerPetal5;
    private Ellipse leaf1;
    private Ellipse leaf2;
    private Rectangle stem;
    private Rotate rotate1;
    private Rotate rotate2;
    private double currentVelocity;
    public double currentPosition;
    private double updatedVelocity;
    private double updatedPosition;
    private boolean isColliding;

    /**
    This is the constructor of the Doodle class that takes in an instance of pane through its parameters.
    This is also where the various shapes are instantiated in order to create the flower composite shape. The rotate
    components are also instantiated here.

    The X and Y positions of the doodle are also initialized here and all of the different components are later added
    to the pane.
     */
    public Doodle(Pane pane) {
        this.stem = new Rectangle(Constants.STEM_WIDTH, Constants.STEM_HEIGHT, Color.SEAGREEN);

        this.flowerCenter = new Circle(Constants.FLOWER_CENTER_RAD, Color.BLANCHEDALMOND);

        this.flowerPetal1 = new Circle(Constants.PETAL_RAD, Color.LIGHTCORAL);
        this.flowerPetal2 = new Circle(Constants.PETAL_RAD, Color.LIGHTCORAL);
        this.flowerPetal3 = new Circle(Constants.PETAL_RAD, Color.LIGHTCORAL);
        this.flowerPetal4 = new Circle(Constants.PETAL_RAD, Color.LIGHTCORAL);
        this.flowerPetal5 = new Circle(Constants.PETAL_RAD, Color.LIGHTCORAL);

        this.rotate1 = new Rotate(135);
        this.rotate2 = new Rotate(45);
        this.leaf1 = new Ellipse(Constants.LEAF_RAD_X, Constants.LEAF_RAD_Y);
        this.leaf2 = new Ellipse(Constants.LEAF_RAD_X, Constants.LEAF_RAD_Y);
        this.setLeaf();

        this.setXPos(Constants.DOODLE_START_X);
        this.currentPosition = Constants.DOODLE_START_Y;
        pane.getChildren().addAll(this.stem, this.leaf1, this.leaf2, this.flowerPetal1, this.flowerPetal2,
                this.flowerPetal3, this.flowerPetal4, this.flowerPetal5, this.flowerCenter);
    }

    /**
    This is the setLeaf() method which is responsible for the physical appearance of the leaves. It declares the colors
    of the leaves as well as the rotation of them.
     */
    private void setLeaf(){
        this.leaf1.setFill(Color.SEAGREEN);
        this.leaf2.setFill(Color.SEAGREEN);
        this.leaf1.getTransforms().add(rotate1);
        this.leaf2.getTransforms().add(rotate2);
    }

    /**
    This is the setXPos() method which passes in a double value through its parameters. This method sets the initial
    X location of the various components of the composite shape to create the image of a flower.
     */
    public void setXPos(double x) {
        this.stem.setX(x);
        this.flowerCenter.setCenterX(x + Constants.FLOWER_CENTER_X_OFFSET);
        this.flowerPetal1.setCenterX(x + Constants.PETAL1_X_OFFSET);
        this.flowerPetal2.setCenterX(x + Constants.PETAL2_X_OFFSET);
        this.flowerPetal3.setCenterX(x + Constants.PETAL3_X_OFFSET);
        this.flowerPetal4.setCenterX(x + Constants.PETAL4_X_OFFSET);
        this.flowerPetal5.setCenterX(x + Constants.PETAL5_X_OFFSET);
        this.rotate1.setPivotX(x + Constants.LEAF1_X_OFFSET);
        this.leaf1.setCenterX(x + Constants.LEAF1_X_OFFSET);
        this.rotate2.setPivotX(x + Constants.LEAF2_X_OFFSET);
        this.leaf2.setCenterX(x + Constants.LEAF2_X_OFFSET);
    }

    /**
    This is the setYPos() method which passes in a double value through its parameters. This method sets the initial
    Y location of the various components of the composite shape to create the image of a flower.
     */
    public void setYPos(double y) {
        this.stem.setY(y);
        this.flowerCenter.setCenterY(y);
        this.flowerPetal1.setCenterY(y + Constants.PETAL1_Y_OFFSET);
        this.flowerPetal2.setCenterY(y + Constants.PETAL2_Y_OFFSET);
        this.flowerPetal3.setCenterY(y + Constants.PETAL3_Y_OFFSET);
        this.flowerPetal4.setCenterY(y + Constants.PETAL4_Y_OFFSET);
        this.flowerPetal5.setCenterY(y + Constants.PETAL5_Y_OFFSET);
        this.rotate1.setPivotY(y + Constants.LEAF1_Y_OFFSET);
        this.leaf1.setCenterY(y + Constants.LEAF1_Y_OFFSET);
        this.rotate2.setPivotY(y + Constants.LEAF2_Y_OFFSET);
        this.leaf2.setCenterY(y + Constants.LEAF2_Y_OFFSET);
    }

    /**
    This getXPos() method which returns the X location of the stem
     */
    public double getXPos() {
        return this.stem.getX();
    }

    /**
    This is the getYPost() method which returns the current Y location of the doodle.
     */
    public double getYPos() {
        return this.currentPosition;
    }

     /**
     This is the updatePhysics() method which calculates the effects of gravity and how fast the doodle should fall back
     down.

      By calculating this, the current velocity and position of the doodle are also updated with the calculations made.
     */
    public void updatePhysics() {
        this.updatedVelocity = this.currentVelocity + Constants.GRAVITY * Constants.DURATION;
        this.updatedPosition = this.currentPosition + updatedVelocity * Constants.DURATION;
        this.currentVelocity = this.updatedVelocity;
        this.currentPosition = this.updatedPosition;
    }

    /**
    This is the checkCollision() method which passes an instance of the abstract Platform class through its parameters.
     This checks for any collisions occuring between the platforms and the doodle composite shape.
     If there is a collision, the boolean value of isColliding is changed to true.
     */
    public void checkCollision(Platform platform) {
        if (currentVelocity > 0) {
            if (this.stem.intersects(platform.wrapper.getPlatformX(), platform.wrapper.getPlatformY(), Constants.PLATFORM_WIDTH, Constants.PLATFORM_HEIGHT)) {
                this.currentVelocity = platform.getReboundVelocity();
                this.isColliding = true;
            }
            else{
                this.isColliding = false;
            }
        }
        else{
            this.isColliding = false;
        }
    }

    /**
     * This is the getIsColliding() method that returns the boolean value of isColliding.
     */
    public boolean getIsColliding() {
        return this.isColliding;
    }

    /**
     * This is the collidesWithEnemy() method that passes an instance of Enemy through its parameters and returns a
     * boolean value that determines whether the doodle intersects with the enemy or not.
     *
     * This method will return a boolean value of true if the doodle does collide with the enemy and will return
     * a boolean value of false if the doodle does not collide with the enemy.
     */
    public boolean collidesWithEnemy(Enemy enemy){
        if(this.stem.intersects(enemy.getEnemyX(), enemy.getEnemyY(), Constants.PLATFORM_WIDTH, Constants.PLATFORM_WIDTH)) {
            return true;
        }
        else{
            return false;
        }
    }
}