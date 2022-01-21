package evolution.doodlejump;
import doodlejump.Constants;
import doodlejump.RectangleWrapper;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

/**
 * This is the abstract class Platform that's the super class for the classes of the other platform types.
 */
public abstract class Platform {

    public RectangleWrapper wrapper;

    /**
     * This is the constructor of the Platform class that takes in an instance of a Pane and two doubles.
     * An instance of the RectangleWrapper class is also instantiated here and the same values of Pane and doubles
     * are passed through its parameters as well.
     */
    public Platform(Pane pane, double xLocation, double yLocation){
    this.wrapper = new RectangleWrapper(pane, xLocation, yLocation);

    }

    /**
     * This is the setColor() method that passes an instance of Color through its parameters.
     * It then sets the color of an instance of rectangle located within the RectangleWrapper class/
     */
    public void setColor(Color color){
        this.wrapper.rect.setFill(color);
    }

    /**
     * This is the updateDirection() method that returns a boolean value that's first initialized to true.
     */
    public boolean updateDirection(){return true;}

    /**
    This is the movePlatform() method that is responsible for moving the moving platforms in the MovingPlatform class.
     */
    public void movePlatform(){}

    /**
     * This is the isDisappearingPlatform() method that returns a boolean value that's first initialized to false.
     */
    public boolean isDisappearingPlatform(){return false;}

    /**
     * This is the isGameOverPlatformI() method that returns a boolean value that's first initialized to false.
     */
    public boolean isGameOverPlatform(){return false;}

    /**
     * This is the getReboundVelocity() method that acts as a getter for the reboundVelocity of the doodle.
     */
    public double getReboundVelocity() {
        return Constants.REBOUND_VELOCITY;
    }
}