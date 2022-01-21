package evolution.doodlejump;

import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

/**
This is the ExtraBouncyPlatform class which extends the abstract class Platform.

The class inherits methods components from the Platform class and manipulates the color, location, and pane to set it
apart from the other types of platforms.

The ExtraBouncyPlatform causes the doodle to jump higher when colliding in comparison to colliding with the other
type of platforms.
 */
public class ExtraBouncyPlatform extends Platform{

    /**
    This is the constructor of the ExtraBouncyPlatform class which takes in a pane and two double values through its
    parameters.

    The specific location values and color is set here as well.
     */
    public ExtraBouncyPlatform(Pane pane, double xLocation, double yLocation) {
        super(pane, xLocation, yLocation);
        super.setColor(Color.MEDIUMPURPLE);
    }

    /**
    This is getReboundVelocity() method which returns the bouncy version of rebound velocity which is later used
    in the Doodle class when checking for collisions.
     */
    public double getReboundVelocity() {
        return Constants.BOUNCY_REBOUND_VELOCITY;
    }
}
