package evolution.tetris;

public class Constants {

    public static final int SQUARE_SIZE = 30;

    public static final int APP_WIDTH = SQUARE_SIZE * 15;
    public static final int APP_HEIGHT = SQUARE_SIZE * 21;
    public static final int GAME_PANE_WIDTH = SQUARE_SIZE * 11;
    public static final int BUTTON_PANE_WIDTH = SQUARE_SIZE * 4;
    public static final int BUTTON_SPACING = SQUARE_SIZE / 3;

    public static final int BORDER_NUM_ROWS = 21;
    public static final int BORDER_NUM_COLS = 11;

    public static final int BORDER_TOP_BOUND = SQUARE_SIZE;
    public static final int BORDER_LEFT_BOUND = SQUARE_SIZE;
    public static final int BORDER_RIGHT_BOUND = GAME_PANE_WIDTH - SQUARE_SIZE;
    public static final int BORDER_BOTTOM_BOUND = APP_HEIGHT - SQUARE_SIZE;

    public static final int DURATION = 500;
    public static final double EASY_RATE = 1.0;
    public static final double MEDIUM_RATE = 2.0;
    public static final double HARD_RATE = 3.0;
    public static final int EASY_SCORE = 1;
    public static final int MEDIUM_SCORE = 2;
    public static final int HARD_SCORE = 3;

    public static final int PIECE_START_X = SQUARE_SIZE * 5;
    public static final int PIECE_LENGTH = 4;

    public static final int[][] I_PIECE_COORDS = {{0, 0}, {0, SQUARE_SIZE}, {0, 2 * SQUARE_SIZE}, {0, 3 * SQUARE_SIZE}};
    public static final int[][] T_PIECE_COORDS = {{-1 * SQUARE_SIZE, 0}, {-1 * SQUARE_SIZE, SQUARE_SIZE}, {-1 * SQUARE_SIZE, 2 * SQUARE_SIZE}, {0, SQUARE_SIZE}};
    public static final int[][] O_PIECE_COORDS = {{-1 * SQUARE_SIZE, 0}, {-1 * SQUARE_SIZE, SQUARE_SIZE}, {0, 0}, {0, SQUARE_SIZE}};
    public static final int[][] L_PIECE_COORDS = {{0, 0}, {0, SQUARE_SIZE}, {0, 2 * SQUARE_SIZE}, {-1 * SQUARE_SIZE, 2 * SQUARE_SIZE}};
    public static final int[][] J_PIECE_COORDS = {{-1 * SQUARE_SIZE, 0}, {0, 0}, {0, SQUARE_SIZE}, {0, 2 * SQUARE_SIZE}};
    public static final int[][] S_PIECE_COORDS = {{0, 0}, {0, SQUARE_SIZE}, {-1 * SQUARE_SIZE, SQUARE_SIZE}, {-1 * SQUARE_SIZE, 2 * SQUARE_SIZE}};
    public static final int[][] Z_PIECE_COORDS = {{-1 * SQUARE_SIZE, 0}, {-1 * SQUARE_SIZE, SQUARE_SIZE}, {0, SQUARE_SIZE}, {0, 2 * SQUARE_SIZE}};
}
