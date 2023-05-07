package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import org.w3c.dom.events.Event;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class createCustomersController implements Initializable {

    @FXML
    private TextField customerName;
    @FXML
    private TextField customerAddress;
    @FXML
    private TextField customerPostalCode;
    @FXML
    private TextField customerPhone;

    @FXML
    private Button createButton;
    @FXML
    private Button exitButton;

    @FXML
    void onCreateClick(ActionEvent event) throws IOException {
        System.out.println("hello");
    }
    @FXML
    void onExitClick(ActionEvent event) throws IOException {
        System.out.println("hello");
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
