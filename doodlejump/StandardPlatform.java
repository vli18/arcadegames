package evolution.doodlejump;

import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

/**
This is the StandardPlatform class which extends the abstract class Platform.
The class inherits methods components from the Platform class and manipulates the color, location, and pane to set it
apart from the other types of platforms.
 */
public class StandardPlatform extends Platform{

    /**
    This is the constructor of the Standard class which takes in a pane and two double values through its
    parameters.

    The specific location values and color is set here as well.
     */
    public StandardPlatform(Pane pane, double xLocation, double yLocation) {
        super(pane, xLocation, yLocation);
        super.setColor(Color.MEDIUMAQUAMARINE);
    }

}
