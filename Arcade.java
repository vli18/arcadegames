package evolution;

import javafx.animation.Animation;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

/**
 * This is the Arcade class that is associated to the Stage. It is the top-level graphical and logical class of the Arcade
 * that sets up the arcade menu with buttons that lead to various Arcade games. It also sets up the various
 * shared Arcade functions.
 */
public class Arcade {

    private Stage stage;
    private Timeline timeline;
    private BorderPane root;
    private VBox menuPane;
    private Label gameStatus;
    private Button back;
    private Button restart;
    private Button quit;

    /**
     * This is the constructor of the Arcade class. It instantiates the instance variables of the associated
     * Stage, the timeline, and the BorderPane (root of the Panes). It also calls the methods that set up the Menu Pane
     * and the shared Arcade functions
     * @param stage
     */
    public Arcade(Stage stage){
        this.stage = stage;
        this.timeline = new Timeline();
        this.timeline.setCycleCount(Animation.INDEFINITE);
        this.root = new BorderPane();
        this.root.setPrefSize(Constants.ARCADE_WIDTH, Constants.ARCADE_HEIGHT);
        this.root.setStyle("-fx-background-color: black");

        this.setMenuPane();
        this.setSharedArcadeFunctions();
    }

    /**
     * This method sets up the menu pane that includes all the buttons that lead to the available Arcade games:
     * Manual Flappy Bird, Smart Flappy Bird, Multiplayer Flappy Bird, Cartoon, Doodle Jump, and Tetris. It also includes
     * an Arcade logo and a Quit button.
     */
    private void setMenuPane(){
        this.menuPane = new VBox(Constants.VBOX_SPACING);
        this.menuPane.setAlignment(Pos.CENTER);
        this.root.setCenter(this.menuPane);

        Image arcadeLogo = new Image("evolution/arcade logo.png", Constants.ARCADE_LOGO_WIDTH, Constants.ARCADE_LOGO_HEIGHT, true, true);
        ImageView arcadeLogoNode = new ImageView(arcadeLogo);

        Image manualFlappyBirdImage = new Image("evolution/manual flappy bird button.png", Constants.MAN_FP_BUTTON_WIDTH, Constants.BUTTON_HEIGHT, true, true);
        ImageView manualFlappyBirdNode = new ImageView(manualFlappyBirdImage);
        Button manualFlappyBirdButton = new Button("", manualFlappyBirdNode);
        manualFlappyBirdButton.setStyle("-fx-background-color: black");
        manualFlappyBirdButton.setOnAction((ActionEvent e) -> this.openArcadeGame(ArcadeGame.MANUAL_FLAPPY_BIRD));

        Image smartFlappyBirdImage = new Image("evolution/smart flappy bird button.png", Constants.S_FP_BUTTON_WIDTH, Constants.BUTTON_HEIGHT, true, true);
        ImageView smartFlappyBirdNode = new ImageView(smartFlappyBirdImage);
        Button smartFlappyBirdButton = new Button("", smartFlappyBirdNode);
        smartFlappyBirdButton.setStyle("-fx-background-color: black");
        smartFlappyBirdButton.setOnAction((ActionEvent e) -> this.openArcadeGame(ArcadeGame.SMART_FLAPPY_BIRD));

        Image multiplayerFlappyBirdImage = new Image("evolution/multiplayer flappy bird button.png", Constants.MULT_FP_BUTTON_WIDTH, Constants.BUTTON_HEIGHT, true, true);
        ImageView multiplayerFlappyBirdNode = new ImageView(multiplayerFlappyBirdImage);
        Button multiplayerFlappyBirdButton = new Button("", multiplayerFlappyBirdNode);
        multiplayerFlappyBirdButton.setStyle("-fx-background-color: black");
        multiplayerFlappyBirdButton.setOnAction((ActionEvent e) -> this.openArcadeGame(ArcadeGame.MULTIPLAYER_FLAPPY_BIRD));

        Image cartoonImage = new Image("evolution/cartoon button.png", Constants.CARTOON_BUTTON_WIDTH, Constants.BUTTON_HEIGHT, true, true);
        ImageView cartoonNode = new ImageView(cartoonImage);
        Button cartoonButton = new Button("", cartoonNode);
        cartoonButton.setStyle("-fx-background-color: black");
        cartoonButton.setOnAction((ActionEvent e) -> this.openArcadeGame(ArcadeGame.CARTOON));

        Image doodleJumpImage = new Image("evolution/doodle jump button.png", Constants.DJ_BUTTON_WIDTH, Constants.BUTTON_HEIGHT, true, true);
        ImageView doodleJumpNode = new ImageView(doodleJumpImage);
        Button doodleJumpButton = new Button("", doodleJumpNode);
        doodleJumpButton.setStyle("-fx-background-color: black");
        doodleJumpButton.setOnAction((ActionEvent e) -> this.openArcadeGame(ArcadeGame.DOODLE_JUMP));

        Image tetrisImage = new Image("evolution/tetris button.png", Constants.TETRIS_BUTTON_WIDTH, Constants.BUTTON_HEIGHT, true, true);
        ImageView tetrisNode = new ImageView(tetrisImage);
        Button tetrisButton = new Button("", tetrisNode);
        tetrisButton.setStyle("-fx-background-color: black");
        tetrisButton.setOnAction((ActionEvent e) -> this.openArcadeGame(ArcadeGame.TETRIS));

        Image quitImage = new Image("evolution/quit button.png", Constants.QUIT_BUTTON_WIDTH, Constants.BUTTON_HEIGHT, true, true);
        ImageView quitNode = new ImageView(quitImage);
        Button quitButton = new Button("", quitNode);
        quitButton.setOnAction((ActionEvent e) -> System.exit(0));

       this.menuPane.getChildren().addAll(arcadeLogoNode, manualFlappyBirdButton, smartFlappyBirdButton, multiplayerFlappyBirdButton, cartoonButton, doodleJumpButton, tetrisButton, quitButton);
    }

    /**
     * This method sets up the shared Arcade functions including the game status label that shows whether an arcade game
     * is over or paused and the back, restart, and quit buttons. The back button allows the player to return to the
     * Arcade menu, the restart button restarts the Arcade game, and the quit button quits out of the App.
     */
    private void setSharedArcadeFunctions(){
        DropShadow dropShadow = new DropShadow();
        dropShadow.setRadius(0);
        dropShadow.setOffsetX(4.0);
        dropShadow.setOffsetY(3.0);
        dropShadow.setColor(Color.rgb(83, 62, 71));

        this.gameStatus = new Label();
        this.gameStatus.setTextFill(Color.WHITE);
        this.gameStatus.setEffect(dropShadow);
        this.gameStatus.setFont(Font.font("Verdana", FontWeight.BLACK, 30));

        this.back = new Button("Back");
        this.back.setOnAction((ActionEvent e) -> this.returnToArcade());

        this.restart = new Button("Restart");

        this.quit = new Button("Quit");
        this.quit.setOnAction((ActionEvent e) -> System.exit(0));
    }

    /**
     * This method allows the player to stop the Arcade game and return to the Arcade menu.
     */
    private void returnToArcade(){
        this.stage.setTitle("Arcade");
        evolution.Arcade arcade = new Arcade(this.stage);
        Scene scene = new Scene(arcade.getRoot(), evolution.Constants.ARCADE_WIDTH, evolution.Constants.ARCADE_HEIGHT);
        this.stage.setScene(scene);
        this.timeline.stop();
    }

    /**
     * This method has a parameter of the ArcadeGame enum that allows the player to open any Arcade game in the menu.
     * The App stage dimensions also adapt to the respective game scene.
     * @param arcadeGame
     */
    private void openArcadeGame(ArcadeGame arcadeGame){
        this.root.getChildren().removeAll(this.menuPane);
        this.stage.setTitle(arcadeGame.title());
        Scene scene = new Scene(arcadeGame.getRoot(this.timeline, this.gameStatus, this.back, this.restart, this.quit));
        this.stage.setScene(scene);
        this.stage.sizeToScene();
    }

    /**
     * This method returns Arcade BorderPane.
     * @return
     */
    public BorderPane getRoot() {
        return this.root;
    }
}
