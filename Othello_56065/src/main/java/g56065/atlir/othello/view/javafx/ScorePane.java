/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package g56065.atlir.othello.view.javafx;

import javafx.geometry.*;
import javafx.scene.control.Label;
import javafx.scene.layout.*;

/**
 * The ScorePane class is a graphical component in the JavaFX application 
 * that displays the scores of both players in the Othello game. It uses 
 * a GridPane layout to organize labels that show the current scores of 
 * the black and white players.
 *
 * @author Youssef El Ouahabi
 */
public class ScorePane extends GridPane {
    private Label playerScore1;
    private Label playerScore2;

    /**
     * Constructs a new ScorePane and initializes the score labels for both players.
     * The labels are styled and positioned within a GridPane to provide a clear 
     * display of the current game scores.
     */
    public ScorePane() {
        playerScore1 = new Label("Score BLACK: 0");
        playerScore2 = new Label("Score WHITE: 0");

        // Apply style to labels
        playerScore1.setStyle("-fx-font-size: 16px;");
        playerScore2.setStyle("-fx-font-size: 16px;");

        // Center the labels both horizontally and vertically
        setAlignment(Pos.CENTER);

        // Add the labels to the pane
        add(playerScore1, 0, 0);
        add(playerScore2, 0, 1);

        // Set internal margins for the labels
        setMargin(playerScore1, new Insets(10));
        setMargin(playerScore2, new Insets(10));
    }

    /**
     * Updates the score display for both players.
     *
     * @param blackScore The current score for the black player.
     * @param whiteScore The current score for the white player.
     */
    public void updateScores(int blackScore, int whiteScore) {
        playerScore1.setText("Score BLACK: " + blackScore);
        playerScore2.setText("Score WHITE: " + whiteScore);
    }
}
