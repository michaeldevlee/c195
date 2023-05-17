/**
 * Controller class for the create user page.
 */

package controller;

import com.main.c195.main;
import dao.UsersQuery;
import helper.AppConfig;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.FlowPane;
import javafx.scene.text.Text;
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
    /**
     * The stage used to display the create user page.
     */
    Stage stage;

    /**
     * The app name text displayed on the create user page.
     */
    @FXML
    private Text appName;

    /**
     * The cancel button used to cancel the create user process.
     */
    @FXML
    private Button cancelButton;

    /**
     * The create button used to create a new user.
     */
    @FXML
    private Button createButton;

    /**
     * The text indicating that a new account is being created.
     */
    @FXML
    private Text createNewAccountText;

    /**
     * The error pane used to display any errors that occur during the create user process.
     */
    @FXML
    private FlowPane errorPane;

    /**
     * The text indicating the user's location.
     */
    @FXML
    private Text locationText;

    /**
     * The password text field used to input the user's password.
     */
    @FXML
    private TextField password;

    /**
     * The text indicating the password text field.
     */
    @FXML
    private Text passwordText;

    /**
     * The user name text field used to input the user's name.
     */
    @FXML
    private TextField userName;

    /**
     * The text indicating the user name text field.
     */
    @FXML
    private Text userNameText;

    /**
     * The verify password text field used to input and verify the user's password.
     */
    @FXML
    private TextField verifyPassword;

    /**
     * The text indicating the verify password text field.
     */
    @FXML
    private Text verifyPasswordText;


    /**

     Sets the text of the UI elements to the corresponding strings in the provided resource bundle.
     @param bundle the resource bundle containing the localized strings
     */
    void setLanguage(ResourceBundle bundle){
        appName.setText(bundle.getString("appNameCreateUser"));
        cancelButton.setText(bundle.getString("cancelButtonCreateUser"));
        createButton.setText(bundle.getString("createButtonCreateUser"));
        createNewAccountText.setText(bundle.getString("createNewAccountTextCreateUser"));
        locationText.setText(bundle.getString("locationTextCreateUser"));
        passwordText.setText(bundle.getString("passwordTextCreateUser"));
        userNameText.setText(bundle.getString("userNameTextCreateUser"));
        verifyPasswordText.setText(bundle.getString("verifyPasswordTextCreateUser"));
    }

    List<String> errors = FXCollections.observableArrayList();

    /**

     Checks whether the provided user input is valid.
     @param userName the user name to be checked
     @param password the password to be checked
     @param verifyPassword the verification password to be checked against the password
     @return true if the provided input is valid, false otherwise
     */
    boolean isValidInput(String userName, String password, String verifyPassword){
        if (password.equals(verifyPassword)) {
            return true;
        }
        return false;
    }

    /**

     Handles the create button click event by validating the user input and inserting the user details into the database

     if the input is valid. After successful insertion, navigates to the login page.

     @param event The event triggered by the create button click.

     @throws IOException If there is an error in loading the login page.

     @throws SQLException If there is an error in inserting the user details into the database.
     */
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

        }

    }
    /**

     Handles the action event when the cancel button is clicked.
     Redirects the user to the login page.
     @param event the action event triggered by clicking the cancel button
     @throws IOException if an input/output error occurs while loading the login page FXML file
     */
    @FXML
    void onCancelClick(ActionEvent event) throws IOException {
        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader(main.class.getResource("login-page.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setScene(scene);
        stage.show();
    }


/**

 Initializes the controller class.
 @param url The location used to resolve relative paths for the root object,
 or null if the location is not known.
 @param resourceBundle The resources used to localize the root object, or null
 if the root object was not localized.
 @throws NullPointerException if bundle returned from AppConfig is null
 */
 @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ResourceBundle bundle = AppConfig.bundleInit();
        setLanguage(bundle);
    }
}

