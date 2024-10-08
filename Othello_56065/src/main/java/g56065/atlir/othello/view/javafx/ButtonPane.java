package g56065.atlir.othello.view.javafx;

import g56065.atlir.othello.model.ColorPiece;
import g56065.atlir.othello.model.GameOthello;
import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;

/**
 * The ButtonPane class is a graphical representation of the interaction buttons
 * for the Othello game. It allows the user to perform actions such as
 * forfeiting the game, undoing, or redoing a move.
 */
public class ButtonPane extends VBox {

    private GameOthello gameOthello;

    /**
     * Constructor for the ButtonPane class.
     *
     * @param game The Othello game associated with these interaction buttons.
     */
    public ButtonPane(GameOthello game) {
        this.gameOthello = game;
        Region spacer = new Region();
        spacer.setPrefHeight(10);
        this.getChildren().addAll(createMenuBar(), spacer, createUndoRedoButtons());
    }

    /**
     * Creates a menu bar with an option to forfeit the game.
     *
     * @return The created menu bar.
     */
    private MenuBar createMenuBar() {
        MenuBar menuBar = new MenuBar();
        Menu fileMenu = new Menu("File");
        MenuItem abandonItem = new MenuItem("Forfeit");
        abandonItem.setOnAction(event -> abandon());

        fileMenu.getItems().add(abandonItem);
        menuBar.getMenus().add(fileMenu);

        return menuBar;
    }

    /**
     * Creates buttons to undo or redo a move.
     *
     * @return The HBox container containing the created buttons.
     */
    private HBox createUndoRedoButtons() {
        Button undoButton = new Button("Undo");
        undoButton.setOnAction(event -> gameOthello.undo());

        Button redoButton = new Button("Redo");
        redoButton.setOnAction(event -> gameOthello.redo());

        HBox buttonBox = new HBox(10, undoButton, redoButton);
        buttonBox.setAlignment(Pos.CENTER);

        return buttonBox;
    }

    public void abandon() {

        ColorPiece forfeitingPlayer = gameOthello.getCurrentPlayer();

        ColorPiece winner = (forfeitingPlayer == ColorPiece.BLACK) ? ColorPiece.WHITE : ColorPiece.BLACK;

        int[] scores = gameOthello.getBoard().countPieces();

        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("Game Forfeited");
        alert.setHeaderText("Player forfeited: " + forfeitingPlayer);
        alert.setContentText("The winner is: " + winner + "\nScore BLACK: " + scores[0] + "\nScore WHITE: " + scores[1]);

        alert.setOnCloseRequest(event -> {
            Platform.exit(); // close the app
        });

        alert.showAndWait();
    }
}
