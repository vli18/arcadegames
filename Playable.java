package evolution;

import javafx.scene.input.KeyEvent;

/**
 * This is the Playable interface that includes methods that are shared across Arcade games.
 */
public interface Playable {
    void addToTimeline();
    void handleKeyPress(KeyEvent e);
    void updateGame();
    void pause();
    void restart();
}
