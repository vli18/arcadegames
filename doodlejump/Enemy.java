package evolution.doodlejump;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

/**This class models the Enemy. It contains an Image and ImageView. It is also
 * associated to a Pane, so that it can be added to a Node.
 */
public class Enemy {
private double xLocation;
private double yLocation;
private Image blackHole;
private ImageView blackHoleNode;

/**
 * This is the constructor where the Image of the enemy (a black hole) is created. It the image's location
 * and adds the ImageView Node graphically to the pane
 */
public Enemy(Pane pane, double xLocation, double yLocation){
    this.blackHole = new Image
            ("./doodlejump/black hole.png", Constants.PLATFORM_WIDTH, Constants.PLATFORM_WIDTH, true, true);
    this.blackHoleNode = new ImageView(this.blackHole);

    this.xLocation = xLocation;
    this.yLocation = yLocation;
    this.setEnemyLocation(this.xLocation, this.yLocation);
    pane.getChildren().add(this.blackHoleNode);
}

/**
 * This method sets the x and y position of the black hole node
 */
public void setEnemyLocation(double x, double y){
    this.blackHoleNode.setX(x);
    this.blackHoleNode.setY(y);
}

/**
 * This method gets the x position of the black hole node
 */
public double getEnemyX(){
    return this.blackHoleNode.getX();
}

/**
 * This method gets the y position of the black hole node
 */
public double getEnemyY(){
    return this.blackHoleNode.getY();
}

/**
 * This method removes the black hole node graphically
 */
public void removeEnemy(Pane pane) {
    pane.getChildren().remove(this.blackHoleNode);
}
}