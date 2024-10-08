/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package g56065.atlir.othello.command;

/**
 * Interface for the command design pattern.
 * Defines the method required to execute a specific command in the game.
 */
public interface Command {
    /**
     * Executes the command defined by this interface.
     * This method must be implemented by all classes that implement this interface,
     * thus allowing any specific action to be encapsulated as an executable command.
     */
    void execute();
}
