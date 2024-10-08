package g56065.atlir.othello.controller;

import g56065.atlir.othello.model.ColorPiece;
import g56065.atlir.othello.model.GameOthello;
import g56065.atlir.othello.model.Position;
import g56065.atlir.othello.view.javafx.MainPane;
import g56065.atlir.othello.model.Board;


/**
 * The Controller acts as an intermediary between the GameOthello model
 * and the MainPane view, initializing and coordinating the application.
 */
public class Controler {

    private final GameOthello game;
    private final MainPane view;

    /**
     * Constructor for the Controller.
     *
     * @param game The Othello game model.
     * @param view The main application view.
     */
    public Controler(GameOthello game, MainPane view) {
        this.game = game;
        this.view = view;
    }

    /**
     * Handles the click on a cell of the board.
     *
     * @param row The row of the clicked cell.
     * @param col The column of the clicked cell.
     */
    public void handleCellClick(int row, int col) {
        if (game.getBoard().isValidMove(new Position(row, col), game.getCurrentPlayer())) {
            game.addPiece(row, col);}
    }
    
    
    /**
     * Handles the computer's turn if the current player is white and a strategy is defined.
     */
    public void computerPlay() {
        if (game.getCurrentPlayer() == ColorPiece.WHITE && game.getStrategy() != null) {
            game.getStrategy().strat(game);
        }
    }
}
