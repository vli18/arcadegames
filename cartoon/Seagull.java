package evolution.cartoon;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

/**
 * This class models the Seagull. It contains an Image and ImageView. It is also
 * associated to a Pane, so that it can be added to a Node.
 */
public class Seagull {

    private double startX;
    private double startY;
    private Image seagullPic;
    private ImageView seagullPicNode;

    /**
     * This method starts the program. It creates the Image of the
     * Seagull and adds it to the pane.
     */
    public Seagull(Pane pane, double startX, double startY) {
        this.startX = startX;
        this.startY = startY;
        this.seagullPic = new Image
                ("./cartoon/seagull.png", 70, 140, false, true);
        this.seagullPicNode = new ImageView(this.seagullPic);
        this.setSeagull();
        pane.getChildren().add(seagullPicNode);
    }

    /**
     * This method sets the position of the seagull.
     */
    private void setSeagull() {
        this.setPosition(this.startX);
    }

    /**
     * This public method sets the X and Y positions of the seagull.
     */
    public void setPosition(double x) {
        this.seagullPicNode.setX(x);
        this.seagullPicNode.setY(this.startY);
    }

    /**
     * This public method returns the X position of the ImageView of the seagull.
     */
    public double getXPos() {
        return this.seagullPicNode.getX();
    }

    /**
     * This public method returns the input Y position value of the seagull.
     */
    public double getYPos() {
        return this.startY;
    }

    /**
     * This public method sets the rotation of the seagull ImageView.
     */
    public void setRotation(double angle) {
       this.seagullPicNode.setRotate(angle);
    }

    /**
     * This public method gets the angle of the seagull ImageView.
     */
    public double getRotation() {
        return this.seagullPicNode.getRotate();
    }
}
