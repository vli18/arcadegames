package evolution.tetris;

/**
 * This is the enum class that details the difficulty, which corresponds to the buttons on the right side of the game
 * window. The methods pertain to the manipulation of the timeline's rate and the scoreMultiplier for the scoring of the game.
 */
public enum Difficulty {
    EASY, MEDIUM, HARD;// three difficulties
    /**
     * This method is a switch statement that returns the rate that the timeline should be manipulated based on the difficulty
     * that the player selected.
     */
    public double duration() {
        switch (this) {
            case EASY:
                 return Constants.EASY_RATE;
            case MEDIUM:
                return Constants.MEDIUM_RATE;
            case HARD:
                return Constants.HARD_RATE;
            default:
                return 0;
        }
    }
    /**
     * This is the method that essentially gets the score multiplier associated with a certain difficulty level.
     */
    public int scoreMultiplier() {
        switch (this) {
            case EASY:
                return Constants.EASY_SCORE;
            case MEDIUM:
                return Constants.MEDIUM_SCORE;
            case HARD:
                return Constants.HARD_SCORE;
            default:
                return 0;
        }
    }
}
