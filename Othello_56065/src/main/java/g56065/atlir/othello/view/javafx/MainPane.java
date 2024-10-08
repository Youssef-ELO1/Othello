package g56065.atlir.othello.view.javafx;

import g56065.atlir.othello.controller.Controler;
import g56065.atlir.othello.model.GameOthello;
import g56065.atlir.othello.model.ColorPiece;
import g56065.atlir.othello.model.Board;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Circle;
import javafx.geometry.Pos;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import javafx.scene.layout.Region;
import javafx.scene.paint.Color;

/**
 * The MainPane class is a graphical representation of the main window
 * of the Othello application. It contains the game board, score information,
 * the current player indicator, and interaction buttons.
 */
public class MainPane extends VBox implements PropertyChangeListener {

    private GameOthello gameOthello;
    private BoardPane boardPane;
    private ScorePane scorePane;
    private Circle currentPlayerIndicator;
    private ButtonPane buttonPane;
    private Controler controller;  // Reference to the controller

    /**
     * Constructor for the MainPane class.
     *
     * @param gameOthello The Othello game associated with this main window.
     * @param controller The controller associated with this view.
     */
    public MainPane(GameOthello gameOthello, Controler controller) {
        this.gameOthello = gameOthello;
        this.controller = controller;

        // Initialize the components
        boardPane = new BoardPane(gameOthello.getBoardSize(), gameOthello, controller);
        scorePane = new ScorePane();
        currentPlayerIndicator = new Circle(25); // Creates a circle with a radius of 25
        buttonPane = new ButtonPane(gameOthello); // Handles all buttons and menu items

        // Setup layout and styling   
        updateCurrentPlayerIndicator();
        currentPlayerIndicator.setStroke(Color.SKYBLUE); // Border in sky blue
        currentPlayerIndicator.setStrokeWidth(3); // Set border size

        // Alignment and positioning
        boardPane.setAlignment(Pos.CENTER);
        setAlignment(Pos.CENTER);

        Region spacer = new Region();
        spacer.setPrefHeight(20);
        // Adding components to VBox
        this.getChildren().addAll(buttonPane, spacer, boardPane, scorePane, currentPlayerIndicator);

        // Register as an observer to the game
        gameOthello.addObserver(this);
        boardPane.refresh(gameOthello.getBoard());
    }

    /**
     * Reacts to property changes in the Othello game.
     *
     * @param evt The property change event.
     */
@Override
public void propertyChange(PropertyChangeEvent evt) {
    String propName = evt.getPropertyName();


    updateCurrentPlayerIndicator();
    int[] scores = gameOthello.getBoard().countPieces();
    scorePane.updateScores(scores[0], scores[1]);


    switch (propName) {
        case "addPiece":

            if (evt.getNewValue() instanceof Board) {
                Board board = (Board) evt.getNewValue();
                controller.computerPlay();
                boardPane.refresh(board);
 
            }
            break;

        case "gameOver":
            boardPane.showGameOverAlert();
            break;

        case "undo":
        case "redo":
            if (evt.getNewValue() instanceof Board) {
                Board board = (Board) evt.getNewValue();
                boardPane.initializeBoard(board);
            }
            break;

        case "playerForfeited":
            buttonPane.abandon();
            break;

        default:
            break;
    }
}


    /**
     * Updates the current player indicator based on the current player.
     */
    public void updateCurrentPlayerIndicator() {
        currentPlayerIndicator.setFill(gameOthello.getCurrentPlayer() == ColorPiece.BLACK ? Color.BLACK : Color.WHITE);
    }


    /**
     * Returns the game board pane.
     *
     * @return The instance of BoardPane.
     */
    public BoardPane getBoardPane() {
        return boardPane;
    }
}
