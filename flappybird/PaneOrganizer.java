package evolution.flappybird;

import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;

/**
 * This is the PaneOrganizer class in charge all graphical elements of the Flappy Bird games.
 */
public class PaneOrganizer {

    private Timeline timeline;
    private BorderPane root;
    private Button back;
    private Button restart;
    private Button quit;
    private Pane gamePane;
    private Pane bottomPane;
    private FlappyBirdGame flappyBirdGame;
    private Label gameStatus;

    /**
     * This is the constructor of the PaneOrganizer it is associated with the shared timeline, game status label, the buttons,
     * and the game mode
     * @param timeline
     * @param gameStatus
     * @param back
     * @param restart
     * @param quit
     * @param gameMode
     */
    public PaneOrganizer(Timeline timeline, Label gameStatus, Button back, Button restart, Button quit, GameMode gameMode) {
        this.timeline = timeline;
        this.gameStatus = gameStatus;
        this.back = back;
        this.restart = restart;
        this.quit = quit;
        this.root = new BorderPane();
        this.root.setPrefSize(Constants.APP_WIDTH, Constants.APP_HEIGHT);

        this.setGamePane();
        this.setBottomPane();

        this.flappyBirdGame = new FlappyBirdGame(this.gamePane, this.bottomPane, this.timeline, this.gameStatus, gameMode);
    }

    /**
     * This method sets the game pane
     */
    private void setGamePane() {
        this.gamePane = new Pane();
        this.gamePane.setPrefSize(Constants.GAME_PANE_WIDTH, Constants.GAME_PANE_HEIGHT);
        this.gamePane.setFocusTraversable(true);
        this.gamePane.setOnKeyPressed((KeyEvent e) -> this.flappyBirdGame.handleKeyPress(e));
        this.root.setTop(this.gamePane);

        Image background = new Image("/evolution/flappybird/background.png", Constants.GAME_PANE_WIDTH, Constants.GAME_PANE_HEIGHT, false, true);
        ImageView backgroundNode = new ImageView(background);

        this.gameStatus.setLayoutX(2 * Constants.GAME_PANE_WIDTH / 7);
        this.gameStatus.setLayoutY(Constants.GAME_PANE_HEIGHT / 2);

        this.gamePane.getChildren().addAll(backgroundNode, this.gameStatus);
    }

    /**
     * This method sets the bottom pane with the controls
     */
    private void setBottomPane(){
        this.bottomPane = new Pane();
        this.bottomPane.setPrefSize(Constants.GAME_PANE_WIDTH, Constants.LABEL_PANE_HEIGHT);
        this.bottomPane.setStyle("-fx-background-color: #DED79D");
        this.root.setBottom(this.bottomPane);

        Image ground = new Image("/evolution/flappybird/ground.png", Constants.GAME_PANE_WIDTH, Constants.LABEL_PANE_HEIGHT / 2, false, true);
        ImageView groundNode = new ImageView(ground);
        groundNode.setLayoutY(0);

        HBox gameControls = new HBox(7);
        gameControls.setLayoutX(Constants.GAME_CONTROLS_X);
        gameControls.setLayoutY(Constants.GAME_CONTROLS_Y);
        this.restart.setOnAction((ActionEvent e) -> this.flappyBirdGame.restart());
        gameControls.getChildren().addAll(this.back, this.restart, this.quit);

        this.bottomPane.getChildren().addAll(groundNode, gameControls);
    }

    /**
     * Thie method returns the Flappy Bird BorderPane
     * @return
     */
    public BorderPane getRoot() {
        return this.root;
    }
}
