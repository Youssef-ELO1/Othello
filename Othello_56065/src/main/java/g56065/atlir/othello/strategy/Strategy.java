/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package g56065.atlir.othello.strategy;

import g56065.atlir.othello.model.GameOthello;

/**
 * The Strategy interface defines the contract for different game strategies in Othello.
 * Any class implementing this interface must provide a strat() method that takes a
 * GameOthello object as a parameter and defines the logic for selecting the next move.
 * 
 * Author: Youssef El Ouahabi
 */
public interface Strategy {

    /**
     * Strategy method that defines the logic for selecting the next move in the game.
     *
     * @param game The Othello game on which to apply the strategy.
     */
    void strat(GameOthello game);
}
