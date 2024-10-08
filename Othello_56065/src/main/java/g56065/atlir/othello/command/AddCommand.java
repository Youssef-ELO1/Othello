/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package g56065.atlir.othello.command;

import g56065.atlir.othello.model.ColorPiece;
import g56065.atlir.othello.model.GameOthello;

/**
 * Command to add a piece to the Othello game board. This command encapsulates
 * all the information necessary to perform the action of adding a piece,
 * including the position and the color of the piece.
 */
public class AddCommand implements Command {

    private GameOthello Game; // Reference to the Othello game for which the command is executed.
    private int x;            // X-coordinate where the piece should be placed.
    private int y;            // Y-coordinate where the piece should be placed.
    private ColorPiece playerColor; // Color of the piece to be placed.

    /**
     * Constructor to create a command for adding a piece.
     *
     * @param Game The Othello game where the piece should be added.
     * @param x The x-coordinate of the position where the piece should be
     * placed.
     * @param y The y-coordinate of the position where the piece should be
     * placed.
     * @param playerColor The color of the piece to be added.
     */
    public AddCommand(GameOthello Game, int x, int y, ColorPiece playerColor) {
        this.Game = Game;
        this.x = x;
        this.y = y;
        this.playerColor = playerColor;
    }

    /**
     * Executes the command to add the piece to the board. This method calls the
     * addPiece method of the game to effectively place the piece at the
     * specified coordinates with the given color.
     */
    public void execute() {
        Game.addPiece(x, y); // Adds the piece to the game using the provided coordinates and color.
    }
}
