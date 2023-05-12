package controller;

import com.main.c195.main;
import dao.UsersQuery;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.Users;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.List;
import java.util.ResourceBundle;

public class createUserController implements Initializable {


    Stage stage;
    Parent scene;
    @FXML
    private TextField userName;
    @FXML
    private TextField password;
    @FXML
    private TextField verifyPassword;

    List<String> errors = FXCollections.observableArrayList();

    boolean isValidInput(String userName, String password, String verifyPassword){
        if (password.equals(verifyPassword)) {
            return true;
        }
        return false;
    }

    List<String> collectErrors (String userNameString, String passwordString, String verifyPasswordString){
        if (userNameString.isEmpty()){
            errors.add("User name field empty");
        }

        if (passwordString.isEmpty()){
            errors.add("Password field empty");
        }

        if (verifyPasswordString.isEmpty()){
            errors.add("Verify password field empty");
        }

        if (!passwordString.equals(verifyPasswordString)){
            errors.add("password verification did not match");
        }

        return errors;
    }

    @FXML
    void onCreateClick(ActionEvent event) throws IOException, SQLException {
        String userNameString = userName.getText();
        String passwordString = password.getText();
        String verifyPasswordString = verifyPassword.getText();

        if (isValidInput(userNameString, passwordString, verifyPasswordString)){

            UsersQuery.insert(
                    userNameString,
                    passwordString,
                    LocalDate.now(),
                    "script",
                    new Timestamp(10000),
                    "script"
            );

            stage = (Stage)((Button)event.getSource()).getScene().getWindow();
            FXMLLoader fxmlLoader = new FXMLLoader(main.class.getResource("login-page.fxml"));
            Scene scene = new Scene(fxmlLoader.load());
            stage.setScene(scene);
            stage.show();

            return;
        }


    }
    @FXML
    void onCancelClick(ActionEvent event) throws IOException {
        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader(main.class.getResource("login-page.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setScene(scene);
        stage.show();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}

