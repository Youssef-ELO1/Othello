/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package g56065.atlir.othello;

import javafx.stage.Stage;
import javafx.application.Application;
import g56065.atlir.othello.view.javafx.AskUser;

/**
 * The OthelloJavaFx class is a JavaFX application that launches the Othello
 * game. It extends the JavaFX Application class and implements its start()
 * method.
 */
public class OthelloJavaFx extends Application {

    /**
     * The start() method is called when the JavaFX application is launched. It
     * creates an instance of AskUser and uses its changeScene() method to
     * switch the main scene of the application using the primaryStage window.
     *
     * @param primaryStage The main window of the JavaFX application.
     * @throws Exception In case of an error during the application startup.
     */
    @Override
    public void start(Stage primaryStage) throws Exception {
        AskUser askUser = new AskUser();
        askUser.changeScene(primaryStage);
    }

    /**
     * Static method to launch the OthelloJavaFx application. It uses the
     * launch() method of the Application class to start the JavaFX application.
     *
     * @param args The command-line arguments passed to the program.
     */
    public static void exec(String[] args) {
        launch(args); // Launch the JavaFX application
    }
}
