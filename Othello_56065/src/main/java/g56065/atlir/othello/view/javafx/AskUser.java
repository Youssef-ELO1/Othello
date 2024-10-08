package g56065.atlir.othello.view.javafx;

import g56065.atlir.othello.controller.Controler;
import g56065.atlir.othello.model.GameOthello;
import g56065.atlir.othello.strategy.RandomStrategy;
import g56065.atlir.othello.strategy.SmartStrategy;
import g56065.atlir.othello.strategy.Strategy;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

/**
 * The AskUser class is responsible for the user interface that allows the user
 * to configure the settings for the Othello game before starting the game.
 */
public class AskUser extends GridPane {

    /**
     * Stage to display the user interface.
     */
    private Stage stage;

    /**
     * Constructor for the AskUser class. Configures the GridPane and initializes the components.
     */
    public AskUser() {
        setVgap(10);
        setHgap(10);
        setPadding(new Insets(10));

        // Component configuration
        Label sizeLabel = new Label("Entrer les dimensions du tableau:");
        TextField sizeTextField = new TextField();

        Label modeLabel = new Label("Mode de jeu :");
        ComboBox<String> modeComboBox = new ComboBox<>();
        modeComboBox.getItems().addAll("Joueur vs Strategie", "2 Joueurs");

        Label strategyLabel = new Label("Choisit la stratégie:");
        ComboBox<String> strategyComboBox = new ComboBox<>();
        strategyComboBox.getItems().addAll(""); // Starts with an empty option

        Button startButton = new Button("Démarrer la partie");
        startButton.setMaxWidth(Double.MAX_VALUE);  // Maximizes the button's width

        // Dynamic update of strategy options based on the selected game mode
        modeComboBox.setOnAction(event -> {
            String selectedMode = modeComboBox.getValue();
            strategyComboBox.getItems().clear();
            if ("Joueur vs Strategie".equals(selectedMode)) {
                strategyComboBox.getItems().addAll("Random stratégie", "Smart stratégie");
            } else {
                strategyComboBox.getItems().add(""); // Adds only the empty option for "2 Joueurs"
            }
        });

        // Adding components to the GridPane
        add(sizeLabel, 0, 0);
        add(sizeTextField, 1, 0);
        add(modeLabel, 0, 1);
        add(modeComboBox, 1, 1);
        add(strategyLabel, 0, 2);
        add(strategyComboBox, 1, 2);
        add(startButton, 0, 3, 2, 1);

        GridPane.setHalignment(startButton, HPos.CENTER);

        // Action associated with the game start button
        startButton.setOnAction(e -> startGame(sizeTextField.getText(), modeComboBox.getValue(), strategyComboBox.getValue()));
    }

    /**
     * Method to change the application's scene and allow the user to configure
     * the game settings.
     *
     * @param primaryStage The main window of the JavaFX application.
     */
    public void changeScene(Stage primaryStage) {
        stage = primaryStage;
        primaryStage.setTitle("Dimension");

        // Creating the scene and displaying the window
        Scene scene = new Scene(this, 350, 200);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    /**
     * Starts the game based on the selected settings.
     *
     * @param sizeInput The board size entered by the user
     * @param mode      The selected game mode
     * @param strategy  The selected strategy (only applicable if the mode is "Player vs Strategy")
     */
    private void startGame(String sizeInput, String mode, String strategy) {
        try {
            int size = Integer.parseInt(sizeInput);
            if (size < 3 || size > 15) {
                showErrorAlert("Mauvaise taille", "La taille doit être entre 3 et 15.");
                return;
            }

            if (mode == null || mode.isEmpty()) {
                showErrorAlert("Mode de jeu requis", "Veuillez choisir un mode de jeu.");
                return;
            }

            GameOthello game;
            if ("Joueur vs Strategie".equals(mode)) {
                if (strategy == null || strategy.isEmpty()) {
                    showErrorAlert("Stratégie requise", "Veuillez choisir une stratégie pour ce mode de jeu.");
                    return;
                }
                Strategy selectedStrategy = getStrategy(strategy);
                game = new GameOthello(size, selectedStrategy);
                
            } else if ("2 Joueurs".equals(mode)) {
                game = new GameOthello(size);  // No strategy needed for two players
            } else {
                showErrorAlert("Mode de jeu invalide", "Le mode de jeu sélectionné n'est pas valide.");
                return;
            }
            Controler controller = new Controler(game, null);
            MainPane mainPane = new MainPane(game,controller);
            Scene scene = new Scene(mainPane, 800, 600);
            stage.setScene(scene);
            stage.show();

        } catch (NumberFormatException e) {
            showErrorAlert("Entrée invalide", "Veuillez entrer un nombre valide.");
        }
    }

    /**
     * Retrieves the strategy selected by the user.
     *
     * @param strategyName The name of the selected strategy
     * @return The corresponding strategy
     */
    private Strategy getStrategy(String strategyName) {
        switch (strategyName) {
            case "Random stratégie":
                return new RandomStrategy();
            case "Smart stratégie":
                return new SmartStrategy();
            default:
                return new RandomStrategy();
        }
    }

    /**
     * Displays an alert dialog with a specified title and message.
     *
     * @param title   The title of the alert
     * @param message The message of the alert
     */
    private void showErrorAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
