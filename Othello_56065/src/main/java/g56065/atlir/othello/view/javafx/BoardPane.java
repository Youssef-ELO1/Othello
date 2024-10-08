/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package g56065.atlir.othello.view.javafx;

/**
 *
 * @author Youssef El Ouahabi
 */
import g56065.atlir.othello.controller.Controler;
import g56065.atlir.othello.model.Board;
import g56065.atlir.othello.model.ColorPiece;
import g56065.atlir.othello.model.GameOthello;
import g56065.atlir.othello.model.Position;
import java.util.List;
import javafx.geometry.*;
import javafx.scene.control.Alert;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;

/**
 * The BoardPane class is a graphical representation of the Othello game board.
 * It displays the game board, game pieces, and manages user interactions.
 */
public class BoardPane extends GridPane {

    private GameOthello gameOthello;
    private Board board;
    private int size;
    private double cellSize = 50;
    private double pieceSize = 20;
    private Circle[][] pieces;
    private Rectangle[][] cases;
    private Controler controller;

    /**
     * Constructor for the BoardPane class.
     *
     * @param size The size of the game board.
     * @param gameOthello The Othello game associated with this board.
     */
    public BoardPane(int size, GameOthello gameOthello, Controler controller) {
        this.size = size;
        this.gameOthello = gameOthello;
        this.board = gameOthello.getBoard();
        this.controller = controller;
        initializeBoard();
    }

    /**
     * Initializes the game board with pieces and cells.
     */
    public void initializeBoard() {
        pieces = new Circle[size][size];
        cases = new Rectangle[size][size];
        List<Position> validMoves = gameOthello.getValidMoves();
        for (int row = 0; row < size; row++) {
            for (int col = 0; col < size; col++) {
                //les cases de la grille 
                Rectangle cell = new Rectangle(cellSize, cellSize);
                // Les pions
                Circle piece = new Circle(pieceSize);
                pieces[row][col] = piece;
                cases[row][col] = cell;
                cell.setFill(Color.GREEN); // Couleur par défaut des cellules
                cell.setStroke(Color.BLACK);
                // Ici on set les pièces 
                piece.setFill(determinePieceColor(row, col));
                setHalignment(piece, HPos.CENTER); // Pour centrer la pièce dans la cellule
                setValignment(piece, VPos.CENTER);
                add(cell, col, row); // Ajoute la cellule à la grille
                add(piece, col, row); // Ajoute la pièce à la grille
                int finalRow = row;
                int finalCol = col;
                cell.setOnMouseClicked(event -> controller.handleCellClick(finalRow, finalCol));
            }
        }
        possibleMoves();

    }

    /**
     * Initializes the game board with pieces and cells.
     */
    public void initializeBoard(Board board) {
        this.board = board;
        pieces = new Circle[size][size];
        cases = new Rectangle[size][size];

        for (int row = 0; row < size; row++) {
            for (int col = 0; col < size; col++) {
                //les cases de la grille
                Rectangle cell = new Rectangle(cellSize, cellSize);
                // Les pions
                Circle piece = new Circle(pieceSize);
                pieces[row][col] = piece;
                cases[row][col] = cell;
                cell.setFill(Color.GREEN); // Couleur par défaut des cellules
                cell.setStroke(Color.BLACK);
                // Ici on set les pièces
                piece.setFill(determinePieceColor(row, col));

                setHalignment(piece, HPos.CENTER); // Pour centrer la pièce dans la cellule
                setValignment(piece, VPos.CENTER);

                add(cell, col, row); // Ajoute la cellule à la grille
                add(piece, col, row); // Ajoute la pièce à la grille

                int finalRow = row;
                int finalCol = col;
                cell.setOnMouseClicked(event -> controller.handleCellClick(finalRow, finalCol));
            }
        }
        possibleMoves();
    }

    /**
     * Determines the color of the piece to be placed on the specified cell.
     *
     * @param row The row of the cell.
     * @param col The column of the cell.
     * @return The color of the piece to display.
     */
    public Color determinePieceColor(int row, int col) {
        Position position = new Position(row, col);
        ColorPiece pieceColor = board.getPieceAt(position);
        if (pieceColor == ColorPiece.WHITE) {
            return Color.WHITE;
        } else if (pieceColor == ColorPiece.BLACK) {
            return Color.BLACK;
        } else {
            return null;
        }
    }

    public int getSize() {
        return size;
    }

    /**
     * Refreshes the game board with a new configuration of pieces.
     *
     * @param board The new game board configuration.
     */
    public void refresh(Board board1) {
        this.board = board1;
        for (int row = 0; row < board.getSize(); row++) {
            for (int col = 0; col < board.getSize(); col++) {
                cases[row][col].setFill(Color.GREEN);
                Position currrent = new Position(row, col);
                ColorPiece colorPiece = board.getPieceAt(currrent);

                if (colorPiece != null) {

                    if (colorPiece == ColorPiece.BLACK) {
                        Circle piece = pieces[row][col];
                        piece.setFill(Color.BLACK);

                    } else if (colorPiece == ColorPiece.WHITE) {
                        Circle piece = pieces[row][col];
                        piece.setFill(Color.WHITE);
                    } else {
                        cases[row][col].setFill(Color.GREEN);
                    }

                }
            }

        }
        possibleMoves();
    }

    /**
     * Highlights possible moves on the game board.
     */
    public void possibleMoves() {
        List<Position> possibleMoves = gameOthello.getValidMoves();
        for (Position pos : possibleMoves) {
            cases[pos.getRow()][pos.getCol()].setFill(Color.RED);
        }

    }

    public void showGameOverAlert() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Game Over");
        alert.setHeaderText(null);
        String message = "Game Over!";

        ColorPiece finalWinner = gameOthello.determineWinner();
        if (finalWinner != null) {
            message += " Le Gagnant est " + (finalWinner == ColorPiece.BLACK ? "BLACK" : "WHITE") + ".";
        } else {  // *
            message += " Match nul.";
        }  // *

        alert.setContentText(message);
        alert.setOnCloseRequest(event -> {
            System.exit(0);
        });
        alert.showAndWait();
    }
}
