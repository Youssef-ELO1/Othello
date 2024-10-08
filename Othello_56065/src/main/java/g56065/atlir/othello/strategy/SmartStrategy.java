package g56065.atlir.othello.strategy;

import g56065.atlir.othello.model.ColorPiece;
import g56065.atlir.othello.model.GameOthello;
import g56065.atlir.othello.model.Position;
import java.util.List;

/**
 * The SmartStrategy class implements the Strategy interface to define an intelligent
 * strategy for the Othello game.
 * 
 * Author: Youssef El Ouahabi
 */
public class SmartStrategy implements Strategy {

    /**
     * Strategy method that selects the best possible move by evaluating potential captures.
     * If no valid move is found, the method checks the game state to determine if there is a winner
     * or if it is a draw.
     *
     * @param game The Othello game on which to apply the strategy.
     */
    @Override
    public void strat(GameOthello game) {
        // Retrieve all legal moves for the current player
        List<Position> validMoves = game.getBoard().getLegalPositions(game.getCurrentPlayer());
        Position bestMove = null;
        int maxCaptures = 0;

        // Evaluate each move to find the one with the maximum potential captures
        for (Position move : validMoves) {
            int captures = calculatePotentialCaptures(game, move);
            if (captures > maxCaptures) {
                maxCaptures = captures;
                bestMove = move;
            }
        }

        // If an optimal move is found, execute it
        if (bestMove != null) {
            game.addPiece(bestMove.getRow(), bestMove.getCol());
        } else {
            // No legal move found, possibly handle the end of the game here
            game.checkGameState();
        }
    }

    /**
     * Method to calculate the potential number of captures for a given move.
     *
     * @param game The Othello game on which to apply the calculation.
     * @param move The move for which to evaluate potential captures.
     * @return The potential number of captures for the given move.
     */
    private int calculatePotentialCaptures(GameOthello game, Position move) {
        return game.getBoard().evaluateMoveEfficiency(move, game.getCurrentPlayer());
    }
}
