package g56065.atlir.othello.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BoardTest {

    private Board board;

    @BeforeEach
    void setUp() {
        // Initialize a default board before each test
        board = new Board(8); // Assuming 8x8 is the default size
    }

    @Test
    void testDefaultBoardInitialization() {
        Board board = new Board(8); // Initialize with default size
        assertEquals(8, board.getSize(), "Board size should be the default size (8).");
        assertEquals(ColorPiece.WHITE, board.getPieceAt(new Position(3, 3)), "Piece at (3, 3) should be WHITE.");
        assertEquals(ColorPiece.WHITE, board.getPieceAt(new Position(4, 4)), "Piece at (4, 4) should be WHITE.");
        assertEquals(ColorPiece.BLACK, board.getPieceAt(new Position(3, 4)), "Piece at (3, 4) should be BLACK.");
        assertEquals(ColorPiece.BLACK, board.getPieceAt(new Position(4, 3)), "Piece at (4, 3) should be BLACK.");
    }

    @Test
    void testCustomBoardInitialization() {
        int customSize = 6;
        Board customBoard = new Board(customSize);
        assertEquals(customSize, customBoard.getSize(), "Board size should be the custom size (6).");
        assertNull(customBoard.getPieceAt(new Position(0, 0)), "Piece at (0, 0) should be null.");
    }

        @Test
    void testIsValidMove() {
        // Positions initiales, normalement les seuls mouvements valides pour chaque couleur
        assertTrue(board.isValidMove(new Position(3, 2), ColorPiece.BLACK));
        assertTrue(board.isValidMove(new Position(2, 3), ColorPiece.BLACK));
        assertFalse(board.isValidMove(new Position(3, 3), ColorPiece.BLACK));
        assertFalse(board.isValidMove(new Position(5, 5), ColorPiece.WHITE));
    }

    @Test
    void testPlacePiece() {
        Position position = new Position(3, 2);
        board.placePiece(position, ColorPiece.BLACK);
        assertEquals(ColorPiece.BLACK, board.getPieceAt(position), "La pièce devrait être noire à (3, 2)");
    }


    @Test
    void testCountCapturesInDirection() {
        // Setup pour tester les captures
        board.placePiece(new Position(3, 2), ColorPiece.WHITE);
        board.placePiece(new Position(3, 3), ColorPiece.BLACK);
        board.placePiece(new Position(3, 4), ColorPiece.WHITE);

        int captures = board.evaluateMoveEfficiency(new Position(3, 5), ColorPiece.BLACK);
        assertEquals(1, captures, "Il devrait capturer 1 pièce blanche");

        captures = board.evaluateMoveEfficiency(new Position(3, 5), ColorPiece.WHITE);
        assertEquals(0, captures, "Aucune capture possible pour le joueur blanc");
    }
}

