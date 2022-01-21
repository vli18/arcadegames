package evolution.doodlejump;

import doodlejump.Constants;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Rectangle;

/**
This is the wrapper class, RectangleWrapper() that contains the basic elements of a platform including the instantiation of
 a rectangle, the rectangle's width and height,and the rectangle's X and Y locations.
 */
public class RectangleWrapper {
    public Rectangle rect;
    private double xLocation;
    private double yLocation;

    /**
     * This is the constructor of the RectangleWrapper class that passes an instance of Pane and two doubles through its
     * parameters.
     * A new instance of Rectangle is created here with the width and height components of the platform.
     * The XLocation and YLocations of the platforms are also instantiated here.
     *
     * The rectangle/platform is then added to the pane.
     */
    public RectangleWrapper(Pane pane, double xLocation, double yLocation){
    this.rect = new Rectangle(Constants.PLATFORM_WIDTH, Constants.PLATFORM_HEIGHT);
    this.xLocation = xLocation;
    this.yLocation = yLocation;

    this.setPlatformX(this.xLocation);
    this.setPlatformY(this.yLocation);
    pane.getChildren().add(this.rect);
}

    /**
     * This is the setPlatformX() method that takes in a double through its parameters.
     * It then sets the value passed through the parameters as the X location of the instance of the rectangle.
     */
    public void setPlatformX(double x){
        this.rect.setX(x);
    }

    /**
     * This is the setPlatformY() method that takes in a double through its parameters.
     * It then sets the value passed through the parameters as the Y location of the instance of the rectangle.
     */
    public void setPlatformY(double y){
        this.rect.setY(y);
    }

    /**
     * This is the getPlatformX() method that acts getter method to return a double value.
     * This method gets and returns the X location of the instance of the rectangle.
     */
    public double getPlatformX(){
        return this.rect.getX();
    }

    /**
     * This is the getPlatformY() method that acts getter method to return a double value.
     * This method gets and returns the Y location of the instance of the rectangle.
     */
    public double getPlatformY(){
        return this.rect.getY();
    }

    /**
     * This is the removePlatform() method that takes in an instance of Pane through its parameters.
     * This is where the platforms that have scrolled past the screen's height are removed graphically from the pane.
     */
    public void removePlatform(Pane pane) {
        pane.getChildren().remove(this.rect);
    }
}
