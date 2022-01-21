package evolution.doodlejump;

/**
 * This is your Constants class. It defines some constants you will need
 * in DoodleJump, using the default values from the demo--you shouldn't
 * need to change any of these values unless you want to experiment. Feel
 * free to add more constants to this class!
 *
 * A NOTE ON THE GRAVITY CONSTANT:
 *   Because our y-position is in pixels rather than meters, we'll need our
 *   gravity to be in units of pixels/sec^2 rather than the usual 9.8m/sec^2.
 *   There's not an exact conversion from pixels to meters since different
 *   monitors have varying numbers of pixels per inch, but assuming a fairly
 *   standard 72 pixels per inch, that means that one meter is roughly 2800
 *   pixels. However, a gravity of 2800 pixels/sec2 might feel a bit fast. We
 *   suggest you use a gravity of about 1000 pixels/sec2. Feel free to change
 *   this value, but make sure your game is playable with the value you choose.
 */
public class Constants {

    //Physics Constants
    public static final int GRAVITY = 1000; // acceleration constant (UNITS: pixels/s^2)
    public static final int REBOUND_VELOCITY = -600; // initial jump velocity (UNITS: pixels/s)
    public static final int BOUNCY_REBOUND_VELOCITY = -1200; //bouncy platform initial jump velocity (UNITS: pixels/s)
    public static final double DURATION = 0.016; // KeyFrame duration (UNITS: s)

    //Platform constants
    public static final int PLATFORM_WIDTH = 40; // (UNITS: pixels)
    public static final int PLATFORM_HEIGHT = 10; // (UNITS: pixels)

    //Flower composite shape constants
    public static final int STEM_WIDTH = 6; // (UNITS: pixels)
    public static final int STEM_HEIGHT = 35; // (UNITS: pixels)
    public static final int LEAF_RAD_X = 4; // (UNITS: pixels)
    public static final int LEAF_RAD_Y = 13; // (UNITS: pixels)
    public static final int FLOWER_CENTER_RAD = 8; // (UNITS: pixels)
    public static final int PETAL_RAD = 8; // (UNITS: pixels)

    //offset constants
    public static final int FLOWER_CENTER_X_OFFSET = STEM_WIDTH / 2;
    public static final int PETAL1_X_OFFSET = STEM_WIDTH / 2;
    public static final int PETAL2_X_OFFSET = 10 + STEM_WIDTH / 2;
    public static final int PETAL3_X_OFFSET = 5 + STEM_WIDTH / 2;
    public static final int PETAL4_X_OFFSET = -5 + STEM_WIDTH / 2;
    public static final int PETAL5_X_OFFSET = -10 + STEM_WIDTH / 2;
    public static final int LEAF1_X_OFFSET = -5;
    public static final int LEAF2_X_OFFSET = STEM_WIDTH + 5;

    public static final int PETAL1_Y_OFFSET = -10;
    public static final int PETAL2_Y_OFFSET = -2;
    public static final int PETAL3_Y_OFFSET = 8;
    public static final int PETAL4_Y_OFFSET = 8;
    public static final int PETAL5_Y_OFFSET = -2;
    public static final int LEAF1_Y_OFFSET = STEM_HEIGHT - 9;
    public static final int LEAF2_Y_OFFSET = STEM_HEIGHT - 9;

    //Enemy offset constants
    public static final int ENEMY_Y_OFFSET = 45;

    //App screen constants
    public static final double APP_WIDTH = 300;
    public static final double APP_HEIGHT = 500;

    //Doodle constants
    public static final double DOODLE_START_X = APP_WIDTH / 2;
    public static final double DOODLE_START_Y = 450;
    public static final double DOODLE_DISTANCE = 15;
    public static final double PLATFORM_DISTANCE = 2;
    public static final int DOODLE_Y_OFFSET_MIN = 50;
    public static final int DOODLE_Y_OFFSET_MAX = 100;
    public static final int DOODLE_X_OFFSET = 200;
}