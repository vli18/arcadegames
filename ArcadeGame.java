package evolution;

import javafx.animation.Timeline;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;

/**
 * This is the ArcadeGame enum class. It includes constants and methods corresponding to the various Arcade Games:
 * Manual Flappy Bird, Smart Flappy Bird, Multiplayer Flappy Bird, Cartoon, Doodle Jump, and Tetris. This class allows
 * for any new Arcade Game to be easily added to the Arcade.
 */
public enum ArcadeGame {
    MANUAL_FLAPPY_BIRD, SMART_FLAPPY_BIRD, MULTIPLAYER_FLAPPY_BIRD, CARTOON, DOODLE_JUMP, TETRIS;

    /**
     * This method returns the title of the Arcade games as a String
     * @return
     */
    public String title(){
        switch(this){
            case MANUAL_FLAPPY_BIRD:
                return "Manual Flappy Bird";
            case SMART_FLAPPY_BIRD:
                return "Smart Flappy Bird";
            case MULTIPLAYER_FLAPPY_BIRD:
                return "Multiplayer Flappy Bird";
            case CARTOON:
                return "Cartoon";
            case DOODLE_JUMP:
                return "Doodle Jump";
            case TETRIS:
                return "Tetris";
            default:
                return null;
        }
    }

    /**
     * This method returns the BorderPane of the corresponding arcade game.
     * @return
     */
    public BorderPane getRoot(Timeline timeline, Label gameStatus, Button back, Button restart, Button quit){
        switch(this){
            case MANUAL_FLAPPY_BIRD:
                evolution.flappybird.PaneOrganizer manualFlappyBird = new evolution.flappybird.PaneOrganizer(timeline, gameStatus, back, restart, quit, evolution.flappybird.GameMode.MANUAL);
                return manualFlappyBird.getRoot();
            case SMART_FLAPPY_BIRD:
                evolution.flappybird.PaneOrganizer smartFlappyBird = new evolution.flappybird.PaneOrganizer(timeline, gameStatus, back, restart, quit, evolution.flappybird.GameMode.SMART);
                return smartFlappyBird.getRoot();
            case MULTIPLAYER_FLAPPY_BIRD:
                evolution.flappybird.PaneOrganizer multiplayerFlappyBird = new evolution.flappybird.PaneOrganizer(timeline, gameStatus, back, restart, quit, evolution.flappybird.GameMode.MULTIPLAYER);
                return multiplayerFlappyBird.getRoot();
            case CARTOON:
                evolution.cartoon.PaneOrganizer cartoon = new evolution.cartoon.PaneOrganizer(timeline, gameStatus, back, restart, quit);
                return cartoon.getRoot();
            case DOODLE_JUMP:
                evolution.doodlejump.PaneOrganizer doodleJump = new evolution.doodlejump.PaneOrganizer(timeline, gameStatus, back, restart, quit);
                return doodleJump.getRoot();
            case TETRIS:
                evolution.tetris.PaneOrganizer tetris = new evolution.tetris.PaneOrganizer(timeline, gameStatus, back, restart, quit);
                return tetris.getRoot();
            default:
                return null;
        }
    }
}
