package g56065.atlir.othello.view.console;

import g56065.atlir.othello.model.GameOthello;
import g56065.atlir.othello.model.Board;
import g56065.atlir.othello.model.ColorPiece;
import g56065.atlir.othello.model.Position;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.List;
import java.util.Scanner;

/**
 * Represents the console view for the Othello game. This class handles user
 * interaction, board display, and receiving notifications of changes in the
 * game model.
 */
public class View implements PropertyChangeListener {

    private Scanner scanner;
    private GameOthello model;
    private List<Position> validMoves;

    /**
     * Constructor that initializes the view with the Othello game model. Also
     * prompts for the board size before starting.
     *
     * @param model The game model to which this view will be linked.
     */
    public View(GameOthello model) {
        this.model = model;
        this.scanner = new Scanner(System.in);
        model.addObserver(this);  // Adds this view as an observer of the model's changes.
    }

    /**
     * Responds to notifications of changes in the model.
     *
     * @param evt The property change event.
     */
    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        switch (evt.getPropertyName()) {
            case "gameInitialized":
//                displayBoard();  // Displays the initial board
                break;
            case "addPiece":
                displayBoard();  // Update the board after each move.
                break;
            case "invalidMove":
                System.out.println("Coup invalide. Ressayez.");
                requestUserInput();  // Prompt for user input again
                break;
            case "gameOver":
                System.out.println("Le jeu est termine");
                ColorPiece winner = model.determineWinner();
                if (winner != null) {
                    System.out.println("Le gagnant est : " + winner);  // Display the winner
                } else {
                    System.out.println("Egalite !");  // Handle the case of a draw
                }
                break;
            case "requestUserInput":
                requestUserInput();  // The player will play
                break;
            case "playerChanged":
                // Handle the player change event
                ColorPiece currentPlayer = (ColorPiece) evt.getNewValue();
                if (currentPlayer == ColorPiece.BLACK) {
                    System.out.println("C'est maintenant le tour de : Black");
                } else {
                    System.out.println("C'est maintenant le tour de : White");
                }
                break;
            case "validMovesUpdated":
                validMoves = (List<Position>) evt.getNewValue();
//                displayBoard();  // Displays the board with valid moves
                break;
            case "undo":
            case "redo":
            case "stateUpdated":
                displayBoard();
                break;
            default:
                System.out.println("ERREUR: " + evt.getPropertyName());
                break;
        }
    }

    /**
     * Displays the game board with row and column indices.
     */
    public void displayBoard() {
        Board board = model.getBoard();
        ColorPiece currentPlayer = model.getCurrentPlayer();
        int[] pieceCounts = board.countPieces();  // Retrieve the piece counts

        System.out.print("   ");
        for (int col = 0; col < board.getSize(); col++) {
            System.out.print(col + " ");
        }
        System.out.println();

        for (int row = 0; row < board.getSize(); row++) {
            System.out.print(row + "  "); // Row number
            for (int col = 0; col < board.getSize(); col++) {
                ColorPiece piece = board.getPieceAt(new Position(row, col));
                if (piece == null) {
                    // If the square is empty, check if it's a valid move
                    if (validMoves != null && validMoves.contains(new Position(row, col))) {
                        System.out.print("* "); // Display an asterisk to indicate a valid move
                    } else {
                        System.out.print("- ");
                    }
                } else if (piece == ColorPiece.BLACK) {
                    System.out.print("B ");
                } else {
                    System.out.print("W ");
                }
            }
            System.out.println();
        }
    }

    public void requestUserInput() {
        System.out.println("Entrez 'q' pour quitter, 'undo' pour annuler ou 'redo' pour rétablir, ou entrez votre coup :");
        String userInput = scanner.nextLine();

        if ("q".equals(userInput)) {
            System.out.println("Fin du jeu. Merci d'avoir joué !");
            System.exit(0);
        } else if ("undo".equalsIgnoreCase(userInput)) {
            model.undo(); // Call the model's undo method
        } else if ("redo".equalsIgnoreCase(userInput)) {
            model.redo(); // Call the model's redo method
        } else {
            // Normal move processing
            try {
                System.out.print("Entrez la ligne: ");
                int row = Integer.parseInt(scanner.nextLine());

                System.out.print("Entrez la colonne: ");
                int col = Integer.parseInt(scanner.nextLine());

                model.addPiece(row, col);
            } catch (NumberFormatException e) {
                System.out.println("Coup invalide. Ressayez.");
                requestUserInput();
            }
        }
    }
}
