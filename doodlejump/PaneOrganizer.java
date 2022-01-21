package evolution.doodlejump;

import javafx.animation.Timeline;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;

/**
 * This is the PaneOrganizer class where the top level graphic components of the doodlejump project are addressed.
 */
public class PaneOrganizer {

    private BorderPane root;
    private Pane gamePane;
    private HBox buttonPane;
    private Button back;
    private Button restart;
    private Button quit;

    /**
     * This is the constructor of the PaneOrganizer class where instances of the BorderPane, HBox, and Pane are created.
     * They are then added to the root.
     */
    public PaneOrganizer(Timeline timeline, Label gameStatus, Button back, Button restart, Button quit){
        this.root = new BorderPane();
        this.back = back;
        this.restart = restart;
        this.quit = quit;

        this.setGamePane();
        this.setButtonPane();

        new DoodleJumpGame(this.gamePane, timeline, gameStatus, restart);
    }

    private void setGamePane(){
        //Create the "Game over" Label
        this.gamePane = new Pane();
        this.gamePane.setPrefSize(300, 470);
        this.root.setCenter(this.gamePane);
    }

    private void setButtonPane() {
        this.buttonPane = new HBox();
        this.buttonPane.setAlignment(Pos.BOTTOM_CENTER);
        this.root.setBottom(this.buttonPane);

        this.buttonPane.getChildren().addAll(this.back, this.restart, this.quit);
    }

    /**
     * This is the getRoot() method that returns an instance of BorderPane. It will return the root pane.
     */
    public BorderPane getRoot() {
        return this.root;
    }
}
