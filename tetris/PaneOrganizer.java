package evolution.tetris;

import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;

/**
 * This is the top level graphical class the details all the graphical elements and adds them to the borderpane.
 * It is also where the TetrisGame class is initialized.
 */
public class PaneOrganizer {

    private BorderPane root;
    private Pane gamePane;
    private VBox buttonPane;
    private Label gameStatus;

    public PaneOrganizer(Timeline timeline, Label gameStatus, Button back, Button restart, Button quit) {
        this.root = new BorderPane();
        this.root.setPrefSize(Constants.APP_WIDTH, Constants.APP_HEIGHT);

        this.gameStatus = gameStatus;
        this.setGamePane();
        this.setButtonPane();
        new TetrisGame(this.gamePane, this.buttonPane, timeline, gameStatus, back, restart, quit);
    }
    /**
     * this is the method that sets up the primary game pane of a set size and sets the color/style to black. Then,
     * it uses the root pane to set the gamePane on the left side of the screen.
     */
    private void setGamePane(){
        this.gamePane = new Pane();
        this.gamePane.setPrefSize(Constants.GAME_PANE_WIDTH, Constants.APP_HEIGHT);
        this.gamePane.setStyle("-fx-background-color: black");

        this.gameStatus.setLayoutX(Constants.GAME_PANE_WIDTH / 4);
        this.gameStatus.setLayoutY(3 * Constants.APP_HEIGHT / 7);

        this.root.setLeft(this.gamePane);
        this.gamePane.getChildren().add(this.gameStatus);
    }
    /**
     * this is the method that sets up the button pane on the right side of the screen. It creates labels for the status of the
     * game, high score, score, difficulties, restart, and quit. It then adds all of these graphical elements to the pane.
     * The method also sets the text to be empty so that the tetris game can logically change the text.
     */
    private void setButtonPane(){
        this.buttonPane = new VBox(Constants.BUTTON_SPACING);
        this.buttonPane.setStyle("-fx-background-color: white");
        this.buttonPane.setPrefSize(Constants.BUTTON_PANE_WIDTH, Constants.APP_HEIGHT);
        this.buttonPane.setAlignment(Pos.CENTER);
        this.root.setRight(this.buttonPane);
    }

    /**
     * this is the getter for the borderpane to be used in the app class that concerns the dimensions of the scene.
     */
    public BorderPane getRoot() {
        return this.root;
    }
}
