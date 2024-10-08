package g56065.atlir.othello.model;

/**
 * Represents a position on the Othello game board.
 * This class encapsulates the row and column of a position.
 * It provides methods to access and modify these coordinates.
 * 
 * Author: Youssef El Ouahabi
 */
public class Position {
    private int row; // The row of the position.
    private int col; // The column of the position.

    /**
     * Constructor for the Position class.
     * Initializes the position with the specified row and column.
     *
     * @param row The row of the position.
     * @param col The column of the position.
     */
    public Position(int row, int col) {
        this.row = row;
        this.col = col;
    }

    /**
     * Gets the row of the position.
     *
     * @return The row of the position.
     */
    public int getRow() {
        return row;
    }

    /**
     * Sets the row of the position.
     *
     * @param row The row to set for the position.
     */
    public void setRow(int row) {
        this.row = row;
    }

    /**
     * Gets the column of the position.
     *
     * @return The column of the position.
     */
    public int getCol() {
        return col;
    }

    /**
     * Sets the column of the position.
     *
     * @param col The column to set for the position.
     */
    public void setCol(int col) {
        this.col = col;
    }
}
