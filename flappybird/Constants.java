package evolution.flappybird;

/**
 * This is the constants class for Flappy Bird
 */
public class Constants {
    public static final int APP_WIDTH = 450;
    public static final int APP_HEIGHT = 725;

    public static final int LABEL_PANE_HEIGHT = 145;
    public static final int HIGH_SCORE_X = 12;
    public static final int HIGH_SCORE_Y = LABEL_PANE_HEIGHT / 5;
    public static final int CURRENT_SCORE_X = 12;
    public static final int CURRENT_SCORE_Y = 3 * LABEL_PANE_HEIGHT / 8;

    public static final int GAME_PANE_WIDTH = APP_WIDTH;
    public static final int GAME_PANE_HEIGHT = APP_HEIGHT - LABEL_PANE_HEIGHT;

    public static final double BIRD_WIDTH = 57;
    public static final int BIRD_HEIGHT = 37;

    public static final int BIRD_START_X = GAME_PANE_WIDTH / 5;
    public static final int BIRD_START_Y = 0;

    public static final int PIPE_WIDTH = (int) BIRD_WIDTH + 10;
    public static final int PIPE_RIM_WIDTH = (int) BIRD_WIDTH + 20;
    public static final int PIPE_RIM_HEIGHT = 35;
    public static final int PIPE_GAP_DISTANCE = BIRD_HEIGHT + 210;
    public static final int PIPE_HORIZONTAL_DISTANCE = 230;
    public static final int PIPE_MAX_HEIGHT = 210;
    public static final int PIPE_MIN_HEIGHT = 80;

    public static final int ACCELERATION = 1000; // acceleration constant (UNITS: pixels/s^2)
    public static final int REBOUND_VELOCITY = -400; // initial jump velocity (UNITS: pixels/s)
    public static final double DURATION = 0.016; // KeyFrame duration (UNITS: s)
    public static final int TIMELINE_DURATION = 15;

    public static final int DISTANCE = 2;

    public static final int GAME_CONTROLS_X = 285;
    public static final int GAME_CONTROLS_Y = LABEL_PANE_HEIGHT - 35;
    public static final int STATS_X = 8;
    public static final int STATS_Y = 27;
    public static final int SPEED_CONTROLS_X = 286;
    public static final int SPEED_CONTROLS_Y = 27;
    public static final int SPEED_1X = 1;
    public static final int SPEED_2X = 2;
    public static final int SPEED_5X = 5;
    public static final int SPEED_MAX = 10;

    public static final double SELECTION_RATE = 25;
    public static final double FITNESS_THRESHOLD = 159;
    public static final double MUTATION_PROBABILITY = 0.17; // 0.005
    public static final double LOW_MUTATION_PROBABILITY = 0.1; // 0.001
    public static final double LOWER_MUTATION_PROBABILITY = 0.08; // 0.0005
    public static final double EVEN_LOWER_MUTATION_PROBABILITY = 0.01; // 0.00001
    public static final double LOWEST_MUTATION_PROBABILITY = 0.005; // 0.000005
    public static final double JUMP_THRESHOLD = 0.5;
}
