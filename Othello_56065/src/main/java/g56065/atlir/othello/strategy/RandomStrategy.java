package g56065.atlir.othello.strategy;

import g56065.atlir.othello.model.GameOthello;
import g56065.atlir.othello.model.Position;
import java.util.List;
import java.util.Random;

/**
 * The RandomStrategy class implements the Strategy interface to define a random
 * strategy for the Othello game.
 * 
 * Author: Youssef El Ouahabi
 */
public class RandomStrategy implements Strategy {

    /**
     * Strategy method that randomly selects a valid move to make in the game.
     * If no valid move is possible, the method checks the game state to determine if
     * there is a winner or if it is a draw.
     *
     * @param game The Othello game on which to apply the strategy.
     */
    @Override
    public void strat(GameOthello game) {
        // Get the list of valid moves for the current player
        List<Position> possibleMoves = game.getBoard().getValidMoves(game.getCurrentPlayer());

        // Check if there are no valid moves
        if (possibleMoves.isEmpty()) {
            // Check the game state to determine if there is a winner or if it is a draw
            game.checkGameState();
        } else {
            // Randomly select a move from the valid moves
            Random random = new Random();
            Position randomMove = possibleMoves.get(random.nextInt(possibleMoves.size()));

            // Add the piece to the game board at the randomly chosen position
            game.addPiece(randomMove.getRow(), randomMove.getCol());
        }
    }
}
