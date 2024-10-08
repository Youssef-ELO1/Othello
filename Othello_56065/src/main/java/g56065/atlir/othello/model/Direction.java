package g56065.atlir.othello.model;

/**
 * Enumeration of possible directions on an Othello game board.
 * Each direction is defined by a change in x (deltaX) and a change in y (deltaY).
 * 
 * Author: Youssef El Ouahabi
 */
public enum Direction {

    N(0, -1),    // North
    S(0, 1),     // South
    E(1, 0),     // East
    O(-1, 0),    // West
    N_E(1, -1),  // North-East
    N_O(-1, -1), // North-West
    S_E(1, 1),   // South-East
    S_O(-1, 1);  // South-West

    private final int deltaX;
    private final int deltaY;

    /**
     * Constructor for the Direction enum.
     *
     * @param deltaX The change in the x-coordinate for this direction.
     * @param deltaY The change in the y-coordinate for this direction.
     */
    private Direction(int deltaX, int deltaY) {
        this.deltaX = deltaX;
        this.deltaY = deltaY;
    }

    /**
     * Gets the change in the x-coordinate for this direction.
     * 
     * @return The delta x value.
     */
    public int getDeltaX() {
        return deltaX;
    }

    /**
     * Gets the change in the y-coordinate for this direction.
     * 
     * @return The delta y value.
     */
    public int getDeltaY() {
        return deltaY;
    }
}
