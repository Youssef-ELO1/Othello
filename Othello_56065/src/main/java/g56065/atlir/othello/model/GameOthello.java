package g56065.atlir.othello.model;

import g56065.atlir.othello.command.Command;
import g56065.atlir.othello.command.Redo;
import g56065.atlir.othello.command.Undo;
import g56065.atlir.othello.strategy.Strategy;
import g56065.atlir.othello.view.console.View;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

/**
 * Main class for the Othello game model. Manages the gameplay, including move
 * handling, player switching, game state verification, and rule application.
 */
public class GameOthello {

    private PropertyChangeSupport ob;
    private Board board;
    private ColorPiece currentPlayer;
    private int size;
    private int blackCount;
    private int whiteCount;
    private List<Position> previousValidMoves;
    private Command undo;
    private Command redo;
    private Strategy strategy;
    private Stack<ColorPiece[][]> undoStack = new Stack<>();
    private Stack<ColorPiece[][]> redoStack = new Stack<>();

    /**
     * Constructor for GameOthello that initializes a game with a specific board
     * size.
     *
     * @param size Size of the game board.
     */
    public GameOthello(int size) {
        this.size = size;
        ob = new PropertyChangeSupport(this);
        board = new Board(size);
        initializePieceCounts();
        board.updateValidMoves();
        currentPlayer = ColorPiece.BLACK;
        previousValidMoves = new ArrayList<>();
        undo = new Undo(board);
        redo = new Redo(board);
        undoStack.push(board.saveState());
        List<Position> validMoves = getValidMoves();
        ob.firePropertyChange("init", null, board);
    }

    /**
     * Constructor for GameOthello that allows specifying a strategy for AI.
     *
     * @param size Size of the game board.
     * @param strategy Strategy to be used for AI moves.
     */
    public GameOthello(int size, Strategy strategy) {
        if (strategy == null) {
            throw new IllegalArgumentException("Strategy cannot be null.");
        }
        this.size = size;
        ob = new PropertyChangeSupport(this);
        board = new Board(size);
        initializePieceCounts();
        board.updateValidMoves();
        currentPlayer = ColorPiece.BLACK;
        previousValidMoves = new ArrayList<>();
        undo = new Undo(board);
        redo = new Redo(board);
        undoStack.push(board.saveState());
        this.strategy = strategy;
    }

    /**
     * Sets the game strategy for the AI.
     *
     * @param strategy Strategy to be used.
     */
    public void setStrategy(Strategy strategy) {
        this.strategy = strategy;
    }

    /**
     * Retrieves the current strategy used for AI moves.
     *
     * @return The current strategy.
     */
    public Strategy getStrategy() {
        return this.strategy;
    }

    /**
     * Associates a view with the model for updating user interfaces.
     *
     * @param view View to be associated.
     */
    public void setView(View view) {
        addObserver(view);
    }

    /**
     * Initializes piece counters by counting black and white pieces on the
     * board.
     */
    private void initializePieceCounts() {
        int[] counts = board.countPieces();
        blackCount = counts[0];
        whiteCount = counts[1];
    }

    /**
     * Adds a listener for property changes.
     *
     * @param listener Listener to be notified of changes.
     */
    public void addObserver(PropertyChangeListener listener) {
        ob.addPropertyChangeListener(listener);
    }

    public void addPiece(int row, int col) {
        Position position = new Position(row, col);

        int nbPass = 0;
        if (board.isValidMove(position, currentPlayer)) {
            if (!redoStack.isEmpty()) {
                redoStack.clear();
            }
            board.placePiece(position, currentPlayer);
            updatePieceCount(currentPlayer, 1);
            capturePieces(row, col, currentPlayer);
            nbPass = passTurn();
            ob.firePropertyChange("addPiece", null, board); // Notifies that the piece has been added
            undoStack.push(board.saveState()); // Save the board state
        } else {
            ob.firePropertyChange("invalidMove", null, null);
        }
        checkGameState(nbPass); // Check if the game is over
    }

    /**
     * Updates the piece count based on the specified color and quantity.
     *
     * @param color The color of the piece.
     * @param count The number of pieces to add (can be negative to remove).
     */
    private void updatePieceCount(ColorPiece color, int count) {
        if (color == ColorPiece.BLACK) {
            blackCount += count;
        } else if (color == ColorPiece.WHITE) {
            whiteCount += count;
        }
    }

    public int passTurn() {
        List<Position> validMoves;
        int nbPass = 0;
        do {
            togglePlayer();
            validMoves = getValidMoves();
            nbPass++;
        } while (validMoves.isEmpty() && (nbPass <= 2));

        return nbPass;
    }

    /**
     * Captures pieces in all directions from a given position.
     *
     * @param row The starting row.
     * @param col The starting column.
     * @param color The color of the current player.
     */
    public void capturePieces(int row, int col, ColorPiece color) {

        searchAndCapture(row, col, color);
        //ob.firePropertyChange("piecesCaptured", null, new Position(row, col));
    }

    /**
     * Searches and captures opponent pieces by exploring all directions from an
     * initial position.
     *
     * @param x Initial X position.
     * @param y Initial Y position.
     * @param playerColor Color of the player making the move.
     */
    public void searchAndCapture(int x, int y, ColorPiece playerColor) {
        if (playerColor == null) {
            throw new IllegalArgumentException("Player color cannot be null.");
        }
        // Iterate over all available directions
        for (Direction d : Direction.values()) {
            int newX = x + d.getDeltaX(); // New x coordinate
            int newY = y + d.getDeltaY(); // New y coordinate

            // Check if the new position is valid
            if (board.isValidPosition(newX, newY)) {
                ColorPiece color = board.getColorAtPosition(newX, newY);

                // If the first square in the specified direction is of opposite color
                if (color != null && color != playerColor) {
                    List<Position> canTurn = new ArrayList<>();
                    boolean turnImpossible = false;

                    // Traverse the direction until a square of the same color is found
                    while (!turnImpossible && color != null && color != playerColor) {
                        canTurn.add(new Position(newX, newY));
                        newX += d.getDeltaX();
                        newY += d.getDeltaY();

                        // Check the next position
                        if (board.isValidPosition(newX, newY)) {
                            color = board.getColorAtPosition(newX, newY);
                        } else {
                            turnImpossible = true;  // The next position is not valid
                        }
                    }

                    // If a square of the same color is found at the end, flip the pieces
                    if (!turnImpossible && color != null && color == playerColor) {
                        for (Position position : canTurn) {
                            board.placePiece(position, playerColor);
                            updatePieceCount(playerColor, 1);
                            updatePieceCount(playerColor.opposite(), -1);

                        }
                    }
                }
            }
        }
    }

    /**
     * Changes the current player to the opposite player and updates the valid
     * moves on the board.
     */
    public void togglePlayer() {
        currentPlayer = currentPlayer.opposite();
        board.updateValidMoves();

    }

    /**
     * Checks the current state of the game to determine if the game is over.
     *
     * @param nbPass
     */
    public void checkGameState(int... nbPass) {// 
        if (isGameOver() || (nbPass.length == 1 && nbPass[0] > 2)) {// If it skips twice, the game is over
            ob.firePropertyChange("gameOver", null, null);
        }
    }

    /**
     * Determines if the game is over by checking the absence of valid moves for
     * both players or if the board is full.
     *
     * @return true if the game is over, false otherwise.
     */
    public boolean isGameOver() {
        return !hasValidMoves(ColorPiece.BLACK) && !hasValidMoves(ColorPiece.WHITE) || board.isBoardFull();
    }

    /**
     * Checks if a player has valid moves available on the board.
     *
     * @param color The color of the player to check.
     * @return true if valid moves are available, false otherwise.
     */
    public boolean hasValidMoves(ColorPiece color) {
        int boardSize = board.getSize();
        for (int row = 0; row < boardSize; row++) {
            for (int col = 0; col < boardSize; col++) {
                if (board.isValidMove(new Position(row, col), color)) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Determines the winner of the game by counting the pieces of each color.
     *
     * @return The color of the player with the most pieces on the board, or
     * null in case of a tie.
     */
    public ColorPiece determineWinner() {
        return blackCount > whiteCount ? ColorPiece.BLACK : whiteCount > blackCount ? ColorPiece.WHITE : null;
    }

    /**
     * Gets the current game board.
     *
     * @return The game board.
     */
    public Board getBoard() {
        return board;
    }

    /**
     * Gets the current player.
     *
     * @return The current player.
     */
    public ColorPiece getCurrentPlayer() {
        return currentPlayer;
    }

    /**
     * Gets the list of valid moves for the current player.
     *
     * @return The list of valid move positions.
     */
    public List<Position> getValidMoves() {
        //ob.firePropertyChange("validMovesUpdated", null, board.getValidMoves(currentPlayer));
        List<Position> validMoves = board.getValidMoves(currentPlayer);
        return validMoves;
    }

    /**
     * Gets the size of the game board.
     *
     * @return The size of the board.
     */
    public int getBoardSize() {
        return size;
    }

    /**
     * Undoes the last move and updates the game state.
     */
    public void undo() {
        if (strategy != null) {
            undoMove();
        }
        undoMove();
        board.countPieces();
        refreshGameState();
        ob.firePropertyChange("undo", null, board);
        
    }

    /**
     * Redoes the last undone move and updates the game state.
     */
    public void redo() {
        if (strategy != null) {
            redoMove();
        }
        redoMove();
        board.countPieces();
        refreshGameState();
        ob.firePropertyChange("redo", null, board);
      
    }

    /**
     * Updates the game state, including valid moves and the current player.
     */
    private void refreshGameState() {
        board.updateValidMoves();
        getCurrentPlayer();
        ob.firePropertyChange("stateUpdated", null, null);
    }

    /**
     * Undoes the last move made on the game board. This method restores the
     * board to the previous state by extracting this state from the undo stack.
     * If the stack is not empty after the undo, it updates the board with the
     * new top state of the stack. If the stack is empty after undoing a state,
     * this means there are no more states to restore, and in this case, it
     * attempts to redo a move previously undone by the user.
     */
    public void undoMove() {
        if (!undoStack.isEmpty()) {
            redoStack.push(undoStack.pop()); // Get the top of undo stack and put it in redo stack
            togglePlayer(); // Toggle because we go one step back, so it's the previous player's turn
            if (!undoStack.isEmpty()) {
                var grid = undoStack.peek(); // Get the top of the stack and update the board according to it
                board.setBoard(grid);
            } else {
                redoMove(); // If in the initial case, undo is empty because we put the only element in redo, so we just call redo.
            }
        }
    }

    /**
     * Restores the last undone move by restoring the next state of the board.
     */
    public void redoMove() {
        if (!redoStack.isEmpty()) {
            var grid = redoStack.peek();
            undoStack.push(redoStack.pop()); // Save current state before redo for undo
            board.setBoard(grid);
            togglePlayer();
        }

    }

}
