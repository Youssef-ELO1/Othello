package g56065.atlir.othello.model;

/**
 * Enum representing the colors of the pieces in the Othello game.
 * This enum provides the two possible colors for pieces: BLACK and WHITE.
 * It also includes a method to retrieve the opposite color.
 * 
 * Author: Youssef El Ouahabi
 */
public enum ColorPiece {
    BLACK,
    WHITE;

    /**
     * Returns the opposite color.
     * 
     * @return The opposite color of the current piece.
     */
    public ColorPiece opposite() {
        if (this == BLACK) {
            return WHITE;
        } else {
            return BLACK;
        }
    }
}
