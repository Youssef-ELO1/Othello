/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package g56065.atlir.othello.command;

import g56065.atlir.othello.model.Board;

/**
 * Command to redo a previously undone action in the Othello game.
 * This command is responsible for restoring the game board to its previous state before the undo operation.
 */
public class Redo implements Command {
    private final Board board; // Reference to the game board to access and modify its state.

    /**
     * Constructor that initializes the command with the game board on which actions will be restored.
     * @param board The game board where the action is to be restored.
     */
    public Redo(Board board) {
        this.board = board;
    }

    /**
     * Executes the redo command. This method should restore the last game state that was undone.
     * The specific logic for restoring the state depends on the state management in the Board or GameOthello class.
     */
    public void execute() {
    }
}
