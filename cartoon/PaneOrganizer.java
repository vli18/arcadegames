package evolution.cartoon;

import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;

/**
 * This class is the PaneOrganizer class, the top-level graphic class. It is
 * responsible for graphical elements that affect the entire program. It contains
 * a BorderPane, the root Node of the Scene, and Cartoon, the top-level
 * logic class.
 */
public class PaneOrganizer {

    private BorderPane root;
    private Label gameStatus;
    private Button back;
    private Button restart;
    private Button quit;
    private Pane gamePane;
    private Label totalSeagullRoundsMade;
    private Label babyTurtlesSurvived;
    private Label totalPoints;
    private Slider difficulty;
    private Cartoon cartoon;

    /**
     * This method starts the program. It instantiates and sets
     * the appearance of the BorderPane, adds texture to the background,
     * creates the quit button, and adds the BorderPane to the Cartoon.
     */
    public PaneOrganizer(Timeline timeline, Label gameStatus, Button back, Button restart, Button quit){
        this.root = new BorderPane();
        this.root.setStyle("-fx-background-color: #f5e7c0");
        this.gameStatus = gameStatus;
        this.back = back;
        this.restart = restart;
        this.quit = quit;

        this.setGamePane();
        this.setTopPane();
        this.setBottomPane();
        this.cartoon = new Cartoon(this.gamePane, timeline, this.gameStatus, this.totalSeagullRoundsMade, this.babyTurtlesSurvived, this.totalPoints, this.difficulty);
    }

    /**
     * This method creates the game Pane where the Turtle and Seagulls are
     * instantiated and added to the Pane. It also adds an Image to the top of the
     * pane to represent the ocean and creates a Label on the bottom left of the
     * Pane to represent the user's total points.
     */
    private void setGamePane() {
        this.gamePane = new Pane();
        this.gamePane.setPrefSize(1000, 710);
        this.root.setCenter(this.gamePane);

        this.gameStatus.setLayoutX(Constants.APP_WIDTH / 2 - 100);
        this.gameStatus.setLayoutY(Constants.APP_HEIGHT / 2);

        Image sandTexture = new Image
                ("cartoon/sand texture.png", Constants.APP_WIDTH, Constants.APP_HEIGHT, false, true);
        ImageView sandTextureNode = new ImageView(sandTexture);

        Image ocean = new Image
                ("./cartoon/waves.png", 1000, 100, false, true);
        ImageView oceanNode = new ImageView(ocean);
        oceanNode.toFront();

        this.gamePane.getChildren().addAll(this.gameStatus, sandTextureNode, oceanNode);
    }

    /**
     * This method creates the label Pane situated at the top of the BorderPane. It
     * contains a Label that shows the total rounds the seagulls have made, a Label
     * that shows the number of turtles that have made it to the ocean, and the
     * level of difficulty Slider.
     */
    private void setTopPane() {
        Pane topPane = new Pane();
        topPane.setPrefSize(1000, 30);
        topPane.setStyle("-fx-background-color: #33aed9");
        topPane.toFront();

        this.totalSeagullRoundsMade = new Label();
        this.totalSeagullRoundsMade.setLayoutX(5);
        this.totalSeagullRoundsMade.setLayoutY(5);

        this.babyTurtlesSurvived = new Label();
        this.babyTurtlesSurvived.setLayoutX(420);
        this.babyTurtlesSurvived.setLayoutY(5);

        Label difficultyLabel = new Label();
        difficultyLabel.setLayoutX(760);
        difficultyLabel.setLayoutY(5);
        difficultyLabel.setText("Difficulty Level: ");

        this.difficulty = new Slider(1, 5, 1);
        this.difficulty.setLayoutX(850);
        this.difficulty.setLayoutY(5);
        this.difficulty.setShowTickMarks(true);
        this.difficulty.setShowTickLabels(true);
        this.difficulty.setSnapToTicks(true);
        this.difficulty.snapToTicksProperty();
        this.difficulty.setMajorTickUnit(1.0f);
        this.difficulty.setBlockIncrement(1.0f);

        this.root.setTop(topPane);
        topPane.getChildren().addAll(this.totalSeagullRoundsMade, this.babyTurtlesSurvived, difficultyLabel, this.difficulty);
        topPane.setFocusTraversable(false);
    }

    /**
     * This method creates the Quit Button which when pressed closes the
     * application.
     */
    private void setBottomPane(){
        Pane bottomPane = new Pane(); //This instantiates the button pane
        bottomPane.setPrefSize(Constants.APP_WIDTH, 35); //This sets its size
        this.root.setBottom(bottomPane);

        this.totalPoints = new Label();
        this.totalPoints.setLayoutX(5);
        this.totalPoints.setLayoutY(10);

        this.back.setLayoutX(Constants.BACK_X);
        this.back.setLayoutY(0);

        this.restart.setLayoutX(Constants.RESTART_X);
        this.restart.setLayoutY(0);
        this.restart.setOnAction((ActionEvent e) -> this.cartoon.restart());

        this.quit.setLayoutX(Constants.QUIT_X);
        this.quit.setLayoutY(0);

        bottomPane.getChildren().addAll(this.totalPoints, this.back, this.restart, this.quit);
    }

    /**
     * This method returns the BorderPane.
     */
    public BorderPane getRoot(){
        return this.root;
    }
}