/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package g56065.atlir.othello.command;

import g56065.atlir.othello.model.Board;

/**
 * The Undo class represents a command to undo a previous action on a game board.
 * It implements the Command interface, allowing its use in a Command design pattern.
 */
public class Undo implements Command {

    /**
     * The game board on which this command will be executed.
     */
    private final Board board;

    /**
     * Constructor for the Undo class.
     *
     * @param board The game board on which this command will be executed.
     */
    public Undo(Board board) {
        this.board = board;
    }

    /**
     * Method to execute the Undo command, which undoes a previous action on the game board.
     * This method takes no parameters because the action to be undone is determined by the current state of the board.
     */
    public void execute() {
    }
}
