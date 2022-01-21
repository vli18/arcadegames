package evolution.doodlejump;

import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

/**
 This is the DisappearingPlatform class which extends the abstract class Platform.

 The class inherits methods components from the Platform class and manipulates the color, location, and pane to set it
 apart from the other types of platforms.

 The DisappearingPlatform class is also where the type of platform is checked in order for it to be removed and checked
 for collision in the DoodleJumpGame class.
 */
public class DisappearingPlatform extends Platform{

    /**
     This is the constructor of the DisappearingPlatform class which takes in a pane and two double values through its
     parameters.

     The specific location values, color, and a boolean value to determine the platform's direction are set here as well.
     */
    public DisappearingPlatform(Pane pane, double xLocation, double yLocation) {
        super(pane, xLocation, yLocation);
        super.setColor(Color.DODGERBLUE);
    }

    /**
     * This is the isDisappearingPlatform() method which returns a boolean which returns a value of true.
     */
    public boolean isDisappearingPlatform(){
        return true;
    }
}
