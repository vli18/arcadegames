package evolution.cartoon;

import javafx.scene.effect.DropShadow;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Ellipse;
import javafx.scene.shape.Line;
import javafx.scene.transform.Rotate;

/**
 * This class models the Turtle. It is a composite shape, containing
 * Ellipses, Circles, Lines, Rotates, and a DropShadow. It is also associated
 * to a Pane, so that it can be added to a Node.
 */
public class Turtle {

    private Ellipse head;
    private Ellipse arm1;
    private Rotate rotateArm1;
    private Ellipse arm2;
    private Rotate rotateArm2;
    private Ellipse arm3;
    private Rotate rotateArm3;
    private Ellipse arm4;
    private Rotate rotateArm4;
    private Circle shell;
    private Line shellHexagon1;
    private Line shellHexagon2;
    private Line shellHexagon3;
    private Line shellHexagon4;
    private Line shellHexagon5;
    private Line shellHexagon6;
    private Line shellLine1;
    private Line shellLine2;
    private Line shellLine3;
    private Line shellLine4;
    private Line shellLine5;
    private Line shellLine6;
    private Circle leftEye;
    private Circle rightEye;
    private DropShadow dropShadow;

    /**
     * This method starts the program. It instantiates the shapes,
     * rotates, and effect. It creates the DropShadow effect and adds
     * all the shapes to the pane.
     */
    public Turtle(Pane pane) {
        this.head = new Ellipse();
        this.arm1 = new Ellipse();
        this.rotateArm1 = new Rotate();
        this.arm2 = new Ellipse();
        this.rotateArm2 = new Rotate();
        this.arm3 = new Ellipse();
        this.rotateArm3 = new Rotate();
        this.arm4 = new Ellipse();
        this.rotateArm4 = new Rotate();
        this.shell = new Circle();
        this.shellHexagon1 = new Line();
        this.shellHexagon2 = new Line();
        this.shellHexagon3 = new Line();
        this.shellHexagon4 = new Line();
        this.shellHexagon5 = new Line();
        this.shellHexagon6 = new Line();
        this.shellLine1 = new Line();
        this.shellLine2 = new Line();
        this.shellLine3 = new Line();
        this.shellLine4 = new Line();
        this.shellLine5 = new Line();
        this.shellLine6 = new Line();
        this.leftEye = new Circle();
        this.rightEye = new Circle();

        this.dropShadow = new DropShadow();
        this.dropShadow.setRadius(10.0);
        dropShadow.setOffsetX(0.0);
        dropShadow.setOffsetY(2.0);
        dropShadow.setColor(Color.rgb(56, 47, 45));

        this.setTurtle();
        pane.getChildren().addAll
                (this.head, this.arm1, this.arm2, this.arm3, this.arm4, this.shell, this.shellHexagon1,
                        this.shellHexagon2, this.shellHexagon3, this.shellHexagon4, this.shellHexagon5,
                        this.shellHexagon6,this.shellLine1, this.shellLine2, this.shellLine3, this.shellLine4,
                        this.shellLine5, this.shellLine6, this.leftEye, this.rightEye);
    }

    /**
     * This method compiles all the other methods to set up the Turtle,
     * including setting all the shapes and the positions
     * of the shapes.
     */
    private void setTurtle() {
        this.setPosition(Constants.START_TURTLE_X_OFFSET, Constants.START_TURTLE_Y_OFFSET);
        this.setHead();
        this.setArms();
        this.setShell();
        this.setShellDetails();
        this.setEyes();
    }

    /**
     * This method is public, it sets the position of the shapes based on the
     * input values of x and y.
     */
    public void setPosition(double x, double y) {
        this.shell.setCenterX(x);
        this.shell.setCenterY(y);

        this.head.setCenterX(x);
        this.head.setCenterY(y - Constants.HEAD_Y_OFFSET);

        this.leftEye.setCenterX(x - Constants.EYE_X_OFFSET);
        this.leftEye.setCenterY(y - Constants.EYE_Y_OFFSET);
        this.rightEye.setCenterX(x + Constants.EYE_X_OFFSET);
        this.rightEye.setCenterY(y - Constants.EYE_Y_OFFSET);

        this.rotateArm1.setAngle(Constants.ARM_ANGLE1);
        this.rotateArm1.setPivotX(x - Constants.ARM_X1_OFFSET);
        this.rotateArm1.setPivotY(y - Constants.ARM_Y1_OFFSET);
        this.arm1.setCenterX(x - Constants.ARM_X1_OFFSET);
        this.arm1.setCenterY(y - Constants.ARM_Y1_OFFSET);
        this.arm1.getTransforms().add(rotateArm1);

        this.rotateArm2.setAngle(Constants.ARM_ANGLE2);
        this.rotateArm2.setPivotX(x + Constants.ARM_X1_OFFSET);
        this.rotateArm2.setPivotY(y - Constants.ARM_Y1_OFFSET);
        this.arm2.setCenterX(x + Constants.ARM_X1_OFFSET);
        this.arm2.setCenterY(y - Constants.ARM_Y1_OFFSET);
        this.arm2.getTransforms().add(rotateArm2);

        this.rotateArm3.setAngle(Constants.ARM_ANGLE2);
        this.rotateArm3.setPivotX(x - Constants.ARM_X1_OFFSET);
        this.rotateArm3.setPivotY(y + Constants.ARM_Y2_OFFSET);
        this.arm3.setCenterX(x - Constants.ARM_X1_OFFSET);
        this.arm3.setCenterY(y + Constants.ARM_Y2_OFFSET);
        this.arm3.getTransforms().add(rotateArm3);

        this.rotateArm4.setAngle(Constants.ARM_ANGLE1);
        this.rotateArm4.setPivotX(x + Constants.ARM_X1_OFFSET);
        this.rotateArm4.setPivotY(y + Constants.ARM_Y2_OFFSET);
        this.arm4.setCenterX(x + Constants.ARM_X1_OFFSET);
        this.arm4.setCenterY(y + Constants.ARM_Y2_OFFSET);
        this.arm4.getTransforms().add(rotateArm4);

        this.shellHexagon1.setStartX(x - Constants.SHELL_HEXAGON_X_OFFSET1);
        this.shellHexagon1.setStartY(y - Constants.SHELL_HEXAGON_Y_OFFSET);
        this.shellHexagon1.setEndX(x + Constants.SHELL_HEXAGON_X_OFFSET1);
        this.shellHexagon1.setEndY(y - Constants.SHELL_HEXAGON_Y_OFFSET);

        this.shellHexagon2.setStartX(x + Constants.SHELL_HEXAGON_X_OFFSET1);
        this.shellHexagon2.setStartY(y - Constants.SHELL_HEXAGON_Y_OFFSET);
        this.shellHexagon2.setEndX(x + Constants.SHELL_HEXAGON_X_OFFSET2);
        this.shellHexagon2.setEndY(y);

        this.shellHexagon3.setStartX(x + Constants.SHELL_HEXAGON_X_OFFSET2);
        this.shellHexagon3.setStartY(y);
        this.shellHexagon3.setEndX(x + Constants.SHELL_HEXAGON_X_OFFSET1);
        this.shellHexagon3.setEndY(y + Constants.SHELL_HEXAGON_Y_OFFSET);

        this.shellHexagon4.setStartX(x + Constants.SHELL_HEXAGON_X_OFFSET1);
        this.shellHexagon4.setStartY(y + Constants.SHELL_HEXAGON_Y_OFFSET);
        this.shellHexagon4.setEndX(x - Constants.SHELL_HEXAGON_X_OFFSET1);
        this.shellHexagon4.setEndY(y + Constants.SHELL_HEXAGON_Y_OFFSET);

        this.shellHexagon5.setStartX(x - Constants.SHELL_HEXAGON_X_OFFSET1);
        this.shellHexagon5.setStartY(y + Constants.SHELL_HEXAGON_Y_OFFSET);
        this.shellHexagon5.setEndX(x - Constants.SHELL_HEXAGON_X_OFFSET2);
        this.shellHexagon5.setEndY(y);

        this.shellHexagon6.setStartX(x - Constants.SHELL_HEXAGON_X_OFFSET2);
        this.shellHexagon6.setStartY(y);
        this.shellHexagon6.setEndX(x - Constants.SHELL_HEXAGON_X_OFFSET1);
        this.shellHexagon6.setEndY(y - Constants.SHELL_HEXAGON_Y_OFFSET);

        this.shellLine1.setStartX(x - Constants.SHELL_LINE_STARTX_OFFSET1);
        this.shellLine1.setStartY(y - Constants.SHELL_LINE_STARTY_OFFSET);
        this.shellLine1.setEndX(x - Constants.SHELL_LINE_ENDX_OFFSET1);
        this.shellLine1.setEndY(y - Constants.SHELL_LINE_ENDY_OFFSET);

        this.shellLine2.setStartX(x + Constants.SHELL_LINE_STARTX_OFFSET1);
        this.shellLine2.setStartY(y - Constants.SHELL_LINE_STARTY_OFFSET);
        this.shellLine2.setEndX(x + Constants.SHELL_LINE_ENDX_OFFSET1);
        this.shellLine2.setEndY(y - Constants.SHELL_LINE_ENDY_OFFSET);

        this.shellLine3.setStartX(x + Constants.SHELL_LINE_STARTX_OFFSET2);
        this.shellLine3.setStartY(y);
        this.shellLine3.setEndX(x + Constants.SHELL_LINE_ENDX_OFFSET2);
        this.shellLine3.setEndY(y);

        this.shellLine4.setStartX(x + Constants.SHELL_LINE_STARTX_OFFSET1);
        this.shellLine4.setStartY(y + Constants.SHELL_HEXAGON_Y_OFFSET);
        this.shellLine4.setEndX(x + Constants.SHELL_LINE_ENDX_OFFSET1);
        this.shellLine4.setEndY(y + Constants.SHELL_LINE_ENDY_OFFSET);

        this.shellLine5.setStartX(x - Constants.SHELL_LINE_STARTX_OFFSET1);
        this.shellLine5.setStartY(y + Constants.SHELL_HEXAGON_Y_OFFSET);
        this.shellLine5.setEndX(x - Constants.SHELL_LINE_ENDX_OFFSET1);
        this.shellLine5.setEndY(y + Constants.SHELL_LINE_ENDY_OFFSET);

        this.shellLine6.setStartX(x - Constants.SHELL_LINE_STARTX_OFFSET2);
        this.shellLine6.setStartY(y);
        this.shellLine6.setEndX(x - Constants.SHELL_LINE_ENDX_OFFSET2);
        this.shellLine6.setEndY(y);
    }

    /**
     * This method returns the center x position of the shell.
     */
    public double getXPos() {
        return this.shell.getCenterX();
    }

    /**
     * This method returns the center y position of the shell.
     */
    public double getYPos() {
        return this.shell.getCenterY();
    }

    /**
     * This method sets an Ellipse to create the turtle's head. It
     * also sets the drop shadow for the Ellipse.
     */
    private void setHead() {
        this.head.setRadiusX(Constants.HEAD_RAD_X);
        this.head.setRadiusY(Constants.HEAD_RAD_Y);
        this.head.setFill(Color.rgb(181, 207, 130));
        this.head.setEffect(this.dropShadow);
    }

    /**
     * This method sets several Ellipses to create the turtle's arms. It
     * also sets the drop shadow for the Ellipses.
     */
    private void setArms() {
        this.arm1.setRadiusX(Constants.ARM_RAD_X);
        this.arm1.setRadiusY(Constants.ARM_RAD_Y);
        this.arm1.setFill(Color.rgb(181, 207, 130));
        this.arm1.setEffect(this.dropShadow);

        this.arm2.setRadiusX(Constants.ARM_RAD_X);
        this.arm2.setRadiusY(Constants.ARM_RAD_Y);
        this.arm2.setFill(Color.rgb(181, 207, 130));
        this.arm2.setEffect(this.dropShadow);

        this.arm3.setRadiusX(Constants.ARM_RAD_X);
        this.arm3.setRadiusY(Constants.ARM_RAD_Y);
        this.arm3.setFill(Color.rgb(181, 207, 130));
        this.arm3.setEffect(this.dropShadow);

        this.arm4.setRadiusX(Constants.ARM_RAD_X);
        this.arm4.setRadiusY(Constants.ARM_RAD_Y);
        this.arm4.setFill(Color.rgb(181, 207, 130));
        this.arm4.setEffect(this.dropShadow);
    }

    /**
     * This method sets a Circle to create the turtle's shell. It
     * also sets the drop shadow for the Circle.
     */
    private void setShell() {
        this.shell.setRadius(Constants.SHELL_RAD);
        this.shell.setFill(Color.rgb(213, 127, 164));
        this.shell.setEffect(this.dropShadow);
    }

    /**
     * This method sets several Lines to create the details on the
     * turtle's shell.
     */
    private void setShellDetails() {
        this.shellHexagon1.setStroke(Color.WHITE);
        this.shellHexagon2.setStroke(Color.WHITE);
        this.shellHexagon3.setStroke(Color.WHITE);
        this.shellHexagon4.setStroke(Color.WHITE);
        this.shellHexagon5.setStroke(Color.WHITE);
        this.shellHexagon6.setStroke(Color.WHITE);

        this.shellLine1.setStroke(Color.WHITE);
        this.shellLine2.setStroke(Color.WHITE);
        this.shellLine3.setStroke(Color.WHITE);
        this.shellLine4.setStroke(Color.WHITE);
        this.shellLine5.setStroke(Color.WHITE);
        this.shellLine6.setStroke(Color.WHITE);
    }

    /**
     * This method sets two Circles to create the turtle's eyes.
     */
    private void setEyes() {
        this.leftEye.setRadius(Constants.EYE_RAD);
        this.leftEye.setFill(Color.rgb(56, 47, 45));

        this.rightEye.setRadius(Constants.EYE_RAD);
        this.rightEye.setFill(Color.rgb(56, 47, 45));
    }
}
