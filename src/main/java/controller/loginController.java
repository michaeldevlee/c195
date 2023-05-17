/**

 The loginController class is responsible for controlling the login page of the application. It includes methods for logging in,
 changing language, and showing alerts for unsuccessful login attempts or upcoming appointments.
 */


package controller;

import com.main.c195.main;
import dao.AppointmentsQuery;
import dao.UsersQuery;
import helper.AppConfig;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Locale;
import java.util.Optional;
import java.util.ResourceBundle;

public class loginController implements Initializable {

    Stage stage;

    /**
     * The application name text in the login page.
     */
    @FXML
    private Text appNameText;

    /**
     * The create button in the login page.
     */
    @FXML
    private Button createButton;

    /**
     * The label that shows the current language/locale.
     */
    @FXML
    private Label currentLocale;

    /**
     * The exit button in the login page.
     */
    @FXML
    private Button exitButton;

    /**
     * The login button in the login page.
     */
    @FXML
    private Button loginButton;

    /**
     * The login text in the login page.
     */
    @FXML
    private Text loginText;

    /**
     * The "new to the system" text in the login page.
     */
    @FXML
    private Text newToSystemText;

    /**
     * The password field in the login page.
     */
    @FXML
    private TextField password;

    /**
     * The password text in the login page.
     */
    @FXML
    private Text passwordText;

    /**
     * The username field in the login page.
     */
    @FXML
    private TextField userName;

    /**
     * The username text in the login page.
     */
    @FXML
    private Text userNameText;


    /**
     * The method for logging in.
     *
     * @param event the event that triggers the method
     * @throws SQLException if there is an error with the SQL query
     * @throws IOException if there is an error with the file input/output
     */
    void loginLog(ActionEvent event) throws SQLException, IOException {
        String userNameInput = userName.getText();
        String userPassInput = password.getText();
        boolean loginSuccess = UsersQuery.select(userNameInput, userPassInput);

        // Log the login attempt
        try (FileWriter writer = new FileWriter("login_activity.txt", true)) {
            String logMessage = String.format("[%s] User '%s' attempted to log in. Login %s.\n",
                    LocalDateTime.now(), userNameInput, loginSuccess ? "successful" : "failed");
            writer.write(logMessage);
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (loginSuccess) {
            alertIfAppointmentIsClose();
            stage = (Stage)((Button)event.getSource()).getScene().getWindow();
            FXMLLoader fxmlLoader = new FXMLLoader(main.class.getResource("view-appointment-page.fxml"));
            Scene scene = new Scene(fxmlLoader.load());
            stage.setScene(scene);
            stage.show();
        } else {
            // Show an alert for unsuccessful login attempts
            if (Locale.getDefault().getLanguage().equals("fr")){
                Alert alert = new Alert(Alert.AlertType.WARNING, "Le nom d'utilisateur et le mot de passe sont incorrects, veuillez réessayer");
                Optional<ButtonType> result = alert.showAndWait();
            } else {
                Alert alert = new Alert(Alert.AlertType.WARNING, "Username and password are incorrect, please try again");
                Optional<ButtonType> result = alert.showAndWait();
            }
        }
    }

    /**

     Sets the language of the user interface according to the provided ResourceBundle.
     @param bundle the ResourceBundle containing the language resources.
     */
    void setLanguage(ResourceBundle bundle){
        userNameText.setText(bundle.getString("userName"));
        passwordText.setText(bundle.getString("password"));
        loginText.setText(bundle.getString("loginText"));
        appNameText.setText(bundle.getString("appName"));
        loginButton.setText(bundle.getString("loginButton"));
        newToSystemText.setText(bundle.getString("newToSystem"));
        createButton.setText(bundle.getString("createButton"));
        exitButton.setText(bundle.getString("exitButton"));
    }

    /**

     Shows an alert if an appointment is coming up within a certain time period.
     @throws SQLException if a database access error occurs.
     */
    void alertIfAppointmentIsClose() throws SQLException {
        Alert alert = new Alert(Alert.AlertType.INFORMATION, AppointmentsQuery.isAppointmentComingUp(Timestamp.valueOf(LocalDateTime.now())));
        Optional<ButtonType> result = alert.showAndWait();
    }

    /**

     Handles the event when the login button is clicked.
     @param event the event triggered by the click.
     @throws IOException if an I/O error occurs.
     @throws SQLException if a database access error occurs.
     */
    @FXML
    void onLoginClick(ActionEvent event) throws IOException, SQLException {
        loginLog(event);
    }

    /**

     Handles the event when the create user button is clicked.
     @param event the event triggered by the click.
     @throws IOException if an I/O error occurs.
     */
    @FXML
    void onCreateClick(ActionEvent event) throws IOException {
        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader(main.class.getResource("create-user-page.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setScene(scene);
        stage.show();
    }

    /**

     Handles the event when the exit button is clicked.
     @param event the event triggered by the click.
     @throws IOException if an I/O error occurs.
     */
    @FXML
    void onExitClick(ActionEvent event) throws IOException {
        System.exit(0);

    }
    /**

     Initializes the controller.
     @param url the location used to resolve relative paths for the root object, or null if the location is not known.
     @param resourceBundle the ResourceBundle containing the language resources.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        currentLocale.setText("Location: " + AppConfig.DEFAULT_ZONE_ID);
        ResourceBundle bundle = AppConfig.bundleInit();
        setLanguage(bundle);
    }
}
