package evolution;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
  * This is the main class where your Evolution program will start.
  * The main method of this application calls the App constructor.
  *
  * This is the App class where the stage is created and the top-level graphical class (the Arcade) is instantiated
  * and set as the Scene to be displayed in the stage.
  *
  */

public class App extends Application {

    @Override
    public void start(Stage stage) {
        // Create top-level object, set up the scene, and show the stage here.
        stage.setTitle("Arcade");
        stage.show();
        Arcade arcade = new Arcade(stage);
        Scene scene = new Scene(arcade.getRoot());
        stage.setScene(scene);
        stage.sizeToScene();
    }

    /*
    * Here is the mainline! No need to change this.
    */
    public static void main(String[] argv) {
        // launch is a method inherited from Application
        launch(argv);
    }
}
