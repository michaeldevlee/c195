package controller;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

/**
 * Controller class for the main.fxml file.
 */
public class mainController {

    /**
     * The label that displays the welcome text.
     */
    @FXML
    private Label welcomeText;

    /**
     * Changes the welcome text when the "Hello" button is clicked.
     */
    @FXML
    protected void onHelloButtonClick() {
        welcomeText.setText("Welcome to JavaFX Application!");
    }
}