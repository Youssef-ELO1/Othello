/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package g56065.atlir.othello.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class GameOthelloTest {

    private GameOthello game;

    @BeforeEach
    void setUp() {
        game = new GameOthello(8); // Initialisation d'un jeu Othello standard
    }

    @Test
    void testAddPiece() {
        Position move = new Position(3, 2);
        game.addPiece(move.getRow(), move.getCol());
        assertEquals(ColorPiece.BLACK, game.getBoard().getPieceAt(move));
        assertEquals(ColorPiece.BLACK, game.getBoard().getPieceAt(new Position(3, 3)), "La pièce à (3, 3) devrait être noire après la capture");
    }

    @Test
    void testCapturePieces() {

        game.addPiece(3, 2);

        assertEquals(ColorPiece.BLACK, game.getBoard().getPieceAt(new Position(3, 3)), "La pièce à (3, 3) devrait être noire après capture");
        assertEquals(ColorPiece.BLACK, game.getBoard().getPieceAt(new Position(4, 3)), "La pièce à (4, 3) devrait être noire après capture");
    }

    @Test
    void testTogglePlayer() {
        ColorPiece initialPlayer = game.getCurrentPlayer();
        game.togglePlayer();
        assertNotEquals(initialPlayer, game.getCurrentPlayer(), "Le joueur devrait être changé après toggle");

        game.togglePlayer();
        assertEquals(initialPlayer, game.getCurrentPlayer(), "Le joueur devrait revenir à l'initial après deux toggles");
    }

    @Test
    void testIsGameOver() {

        GameOthello game = new GameOthello(8);
        Board board = game.getBoard();

        board.clearBoard();


        for (int row = 0; row < board.getSize(); row++) {
            for (int col = 0; col < board.getSize(); col++) {
                if ((row + col) % 2 == 0) {
                    board.placePiece(new Position(row, col), ColorPiece.BLACK);
                } else {
                    board.placePiece(new Position(row, col), ColorPiece.WHITE);
                }
            }
        }

        assertTrue(game.isGameOver(), "Le jeu devrait être terminé");
    }
}

