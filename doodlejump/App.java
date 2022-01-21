package evolution.doodlejump;

import doodlejump.Constants;
import doodlejump.PaneOrganizer;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * This is the main class where your DoodleJump game will start.
 * The main method of this application calls launch, a JavaFX method
 * which eventually calls the start method below. You will need to fill
 * in the start method to start your game!
 *
 * This is the App class that inherits from class Application.
 * This class is responsible for creating a new scene, stage, and instance of the PaneOrganizer.
 * It sets the scene onto the stage and allows the scene to display.
 */
public class App extends Application {

    @Override
    public void start(Stage stage) {
        stage.setTitle("Stage");
        stage.show();

        // Instantiate top-level object, set up the scene, and show the stage here.
        PaneOrganizer organizer = new PaneOrganizer();
        Scene scene = new Scene(organizer.getRoot(), Constants.APP_WIDTH, Constants.APP_HEIGHT);
        stage.setScene(scene);
    }

    /*
     * Here is the mainline! No need to change this.
     */
    public static void main(String[] argv) {
        // launch is a static method inherited from Application.
        launch(argv);
    }
}
