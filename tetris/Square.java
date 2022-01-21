package evolution.tetris;

import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

/**
 * This is the class that details the creation of an individual square. It is a wrapper class for the JavaFX element,
 * rectangle. This class creates a square that is of set length/width, and x and y coordinates. Then, it sets the color
 * of the square and sets the outline to black. Finally, the square adds itself graphically to the pane that it is associated
 * with.
 */
public class Square {

    private Rectangle square;

    public Square(Pane root, Color color, double x, double y){
        this.square = new Rectangle(x, y, Constants.SQUARE_SIZE, Constants.SQUARE_SIZE);
        this.square.setFill(color);
        this.square.setStroke(Color.BLACK);
        root.getChildren().add(this.square);
    }
    /**
     * This is the setter for X location of the wrapper class.
     */
    public void setX(double x){
        this.square.setX(x);
    }
    /**
     * This is the setter for the Y location of the wrapper class.
     */
    public void setY(double y){
        this.square.setY(y);
    }
    /**
     * This is the getter for the X location of the wrapper class.
     */
    public double getX(){
        return this.square.getX();
    }
    /**
     * This is the getter for the Y location of the wrapper class.
     */
    public double getY() {
        return this.square.getY();
    }
    /**
     * This is the method that allows the square to remove itself from the pane.
     */
    public void removeFromPane(Pane pane) {
        pane.getChildren().remove(this.square);
    }
}
