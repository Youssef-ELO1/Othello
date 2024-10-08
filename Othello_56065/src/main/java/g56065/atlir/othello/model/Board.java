package g56065.atlir.othello.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents the game board for the Othello game. Manages the layout and control of pieces on the board,
 * including the validation of valid moves and the manipulation of pieces.
 */
public class Board {

    private ColorPiece[][] grid; // Represents the game board as an 8x8 grid.
    private List<Position> possibleMovesBlack = new ArrayList<>();
    private List<Position> possibleMovesWhite = new ArrayList<>();
    private int captureCount; // Stores the number of captures for the current move validation

    /**
     * Constructor that initializes the board with a specific size.
     *
     * @param size The size of the board (between 3 and 15).
     */
    public Board(int size) {
        if (size < 3 || size > 15) {
            throw new IllegalArgumentException("The board size must be between 3 and 15.");
        }
        this.grid = new ColorPiece[size][size];
        initializeBoard(size);
    }

    /**
     * Initializes the board with the starting pieces.
     *
     * @param size The size of the board used to determine the initial positions.
     */
    public void initializeBoard(int size) {
        if (size >= 4) {
            int middle = size / 2;
            grid[middle - 1][middle - 1] = ColorPiece.WHITE;
            grid[middle][middle] = ColorPiece.WHITE;
            grid[middle - 1][middle] = ColorPiece.BLACK;
            grid[middle][middle - 1] = ColorPiece.BLACK;
        } else if (size == 3) {
            grid[1][1] = ColorPiece.WHITE;
            grid[1][2] = ColorPiece.BLACK;
        }
    }

    /**
     * Gets the color of the piece at the specified position.
     *
     * @param row The row of the position.
     * @param col The column of the position.
     * @return The color of the piece at the position.
     */
    public ColorPiece getColorAtPosition(int row, int col) {
        return grid[row][col];
    }

    /**
     * Returns the size of the board.
     *
     * @return The size of the board.
     */
    public int getSize() {
        return grid.length;
    }

    /**
     * Retrieves the piece at a given position on the board.
     *
     * @param position The position of the requested piece.
     * @return The color of the piece at this position, or null if no piece is present.
     */
    public ColorPiece getPieceAt(Position position) {
        if (position == null) {
            throw new IllegalArgumentException("Position cannot be null.");
        }
        return grid[position.getRow()][position.getCol()];
    }

    /**
     * Places a piece of the specified color at the given position on the board.
     *
     * @param position The position where the piece should be placed.
     * @param color The color of the piece to place.
     */
    public void placePiece(Position position, ColorPiece color) {
        if (position == null) {
            throw new IllegalArgumentException("Position cannot be null.");
        }
        if (color == null) {
            throw new IllegalArgumentException("Color cannot be null.");
        }
        grid[position.getRow()][position.getCol()] = color;
    }

    /**
     * Checks if a move at a given position is valid for a specific color.
     * Simultaneously counts the number of opponent pieces that would be captured.
     *
     * @param position The position of the move to check.
     * @param color The color of the piece for which to check the move.
     * @return True if the move is valid, false otherwise.
     */
    public boolean isValidMove(Position position, ColorPiece color) {
        if (position == null) {
            throw new IllegalArgumentException("Position cannot be null.");
        }
        if (color == null) {
            throw new IllegalArgumentException("Color cannot be null.");
        }

        int row = position.getRow();
        int col = position.getCol();

        if (!isValidPosition(row, col) || grid[row][col] != null) {
            return false;
        }

        captureCount = 0; 

        boolean valid = false;
        for (Direction dir : Direction.values()) {
            if (countCapturesInDirection(row, col, color, dir) > 0) {
                valid = true;
            }
        }

        return valid;
    }

    /**
     * Counts the number of opponent pieces capturable in a given direction
     * from a specified position and returns the count.
     *
     * @param startX The starting X coordinate.
     * @param startY The starting Y coordinate.
     * @param currentColor The current player's color.
     * @param direction The direction to check.
     * @return The number of opponent pieces capturable in the given direction.
     */
    private int countCapturesInDirection(int startX, int startY, ColorPiece currentColor, Direction direction) {
        if (currentColor == null) {
            throw new IllegalArgumentException("Color cannot be null.");
        }
        if (direction == null) {
            throw new IllegalArgumentException("Direction cannot be null.");
        }

        int captures = 0;
        int nextX = startX + direction.getDeltaX();
        int nextY = startY + direction.getDeltaY();

        // Check if the next position is out of bounds, empty, or the same color as the current player
        if (!isValidPosition(nextX, nextY) || grid[nextX][nextY] == null || grid[nextX][nextY] == currentColor) {
            return 0;
        }

        // Continue in the direction until an ending condition is met
        while (isValidPosition(nextX, nextY) && grid[nextX][nextY] != null) {
            if (grid[nextX][nextY] == currentColor.opposite()) {
                captures++; // An opponent piece is found
                nextX += direction.getDeltaX();
                nextY += direction.getDeltaY();
            } else if (grid[nextX][nextY] == currentColor) {
                captureCount += captures; // Add captures to the overall capture count
                return captures;
            } else {
                break;
            }
        }

        return 0;
    }

    /**
     * Checks if a position on the board is valid (i.e., within board boundaries).
     *
     * @param row The row to check.
     * @param col The column to check.
     * @return True if the position is valid, false otherwise.
     */
    public boolean isValidPosition(int row, int col) {
        return row >= 0 && row < grid.length && col >= 0 && col < grid[0].length;
    }

    /**
     * Counts the number of pieces for each color on the board.
     *
     * @return An array of two integers where the first element is the number of black pieces,
     * and the second element is the number of white pieces.
     */
    public int[] countPieces() {
        int blackCount = 0;
        int whiteCount = 0;
        for (int row = 0; row < grid.length; row++) {
            for (int col = 0; col < grid[row].length; col++) {
                if (grid[row][col] == ColorPiece.BLACK) {
                    blackCount++;
                } else if (grid[row][col] == ColorPiece.WHITE) {
                    whiteCount++;
                }
            }
        }
        return new int[]{blackCount, whiteCount};
    }

    /**
     * Updates the valid moves for black and white pieces.
     */
    public void updateValidMoves() {
        possibleMovesBlack.clear();
        possibleMovesWhite.clear();
        for (int row = 0; row < grid.length; row++) {
            for (int col = 0; col < grid[row].length; col++) {
                Position position = new Position(row, col);
                if (isValidMove(position, ColorPiece.BLACK)) {
                    possibleMovesBlack.add(position);
                }
                if (isValidMove(position, ColorPiece.WHITE)) {
                    possibleMovesWhite.add(position);
                }
            }
        }
    }

    /**
     * Saves the current state of the board in the undo stack.
     *
     * @return A 2D array representing the current state of the board.
     */
    public ColorPiece[][] saveState() {
        ColorPiece[][] currentState = new ColorPiece[getSize()][getSize()];
        for (int i = 0; i < getSize(); i++) {
            for (int j = 0; j < getSize(); j++) {
                currentState[i][j] = grid[i][j];
            }
        }
        return currentState;
    }

    /**
     * Returns the list of valid moves for a given color.
     *
     * @param color The color of the pieces for which valid moves are being searched.
     * @return List of valid move positions.
     */
    public List<Position> getValidMoves(ColorPiece color) {
        if (color == null) {
            throw new IllegalArgumentException("Color cannot be null.");
        }
        List<Position> validMoves = color == ColorPiece.BLACK ? possibleMovesBlack : possibleMovesWhite;
        return validMoves;
    }

    /**
     * Clears all pieces from the board, resetting it to an empty state.
     */
    public void clearBoard() {
        for (int row = 0; row < grid.length; row++) {
            for (int col = 0; col < grid[row].length; col++) {
                grid[row][col] = null;
            }
        }
    }

    /**
     * Sets the board state from a given grid.
     *
     * @param board Grid representing the state to set for the board.
     */
    public void setBoard(ColorPiece[][] board) {
        if (board == null) {
            throw new IllegalArgumentException("Board cannot be null.");
        }
        for (int row = 0; row < grid.length; row++) {
            System.arraycopy(board[row], 0, grid[row], 0, grid.length);
        }
    }

    /**
     * Calculates and returns a list of legal positions where the current player can play.
     * A position is considered legal if a move at that position is valid according to the game rules.
     *
     * @param currentPlayer The player for whom to check legal moves.
     * @return List of legal positions for the specified player.
     */
    public List<Position> getLegalPositions(ColorPiece currentPlayer) {
        if (currentPlayer == null) {
            throw new IllegalArgumentException("Current player cannot be null.");
        }
        List<Position> legalPositions = new ArrayList<>();
        for (int row = 0; row < getSize(); row++) {
            for (int col = 0; col < getSize(); col++) {
                Position pos = new Position(row, col);
                if (isValidMove(pos, currentPlayer)) {
                    legalPositions.add(pos);
                }
            }
        }
        return legalPositions;
    }

    /**
     * Evaluates the efficiency of a proposed move in terms of the number of opponent pieces
     * that would be captured if the move were played.
     *
     * @param move The position of the proposed move.
     * @param currentPlayer The current player considering the move.
     * @return The total number of opponent pieces that would be captured by the move.
     */
    public int evaluateMoveEfficiency(Position move, ColorPiece currentPlayer) {
        if (move == null) {
            throw new IllegalArgumentException("Move cannot be null.");
        }
        if (currentPlayer == null) {
            throw new IllegalArgumentException("Current player cannot be null.");
        }
        isValidMove(move, currentPlayer); // This will calculate the captureCount
        return captureCount;
    }

    /**
     * Checks if the game board is completely filled with pieces.
     *
     * @return true if the board is full, false otherwise.
     */
    public boolean isBoardFull() {
        int size = this.getSize();
        for (int row = 0; row < size; row++) {
            for (int col = 0; col < size; col++) {
                if (this.getPieceAt(new Position(row, col)) == null) {
                    return false;
                }
            }
        }
        return true;
    }
}
