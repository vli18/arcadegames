package evolution.doodlejump;

import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

/**
This is the MovingPlatform class which extends the abstract class Platform.

The class inherits methods components from the Platform class and manipulates the color, location, and pane to set it
apart from the other types of platforms.

The MovingPlatform class also moves back and forth across the screen width.
 */
public class MovingPlatform extends Platform{

    private boolean isMovingRight;

    /**
    This is the constructor of the MovingPlatform class which takes in a pane and two double values through its
    parameters.

    The specific location values, color, and a boolean value to determine the platform's direction are set here as well.
     */
    public MovingPlatform(Pane pane, double xLocation, double yLocation) {
        super(pane, xLocation, yLocation);
        super.setColor(Color.PLUM);
        this.isMovingRight = true;
    }

    /**
    This is the updateDirection() method that returns a boolean value.
    This method determines whether the platform has reached the end of the screen and if so, changes the direction of
    the platform's movement.
     */
    public boolean updateDirection(){
        if(super.wrapper.getPlatformX() >= Constants.APP_WIDTH - Constants.PLATFORM_WIDTH && this.isMovingRight) {
            this.isMovingRight = false;
        } else if (super.wrapper.getPlatformX() <= 0 && !this.isMovingRight) {
            this.isMovingRight = true;
        }
        return this.isMovingRight;
    }

    /**
    This is the movePlatform() method which allows the platform move across the screen.
     */
    public void movePlatform(){
        if(updateDirection()) {
            super.wrapper.setPlatformX(super.wrapper.getPlatformX() + Constants.PLATFORM_DISTANCE);
        }
        else{
            super.wrapper.setPlatformX(super.wrapper.getPlatformX() - Constants.PLATFORM_DISTANCE);
        }
    }
}