package controller;

import com.main.c195.main;
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

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;

import java.util.Locale;
import java.util.Optional;
import java.util.ResourceBundle;

public class loginController implements Initializable {

    Stage stage;

    @FXML
    private TextField userName;
    @FXML
    private TextField password;

    @FXML
    private Text userNameText;
    @FXML
    private Text loginText;
    @FXML
    private Text appNameText;

    @FXML
    private Label currentLocale;

    void setLanguage(ResourceBundle bundle){
        userNameText.setText(bundle.getString("userName"));
        password.setText(bundle.getString("password"));
        loginText.setText(bundle.getString("loginText"));
        appNameText.setText(bundle.getString("appName"));
    }

    void setCurrentLocale(){
        ResourceBundle bundle;
        if (Locale.getDefault().getLanguage().equals("fr")) {
            bundle = ResourceBundle.getBundle("messages_fr");
            setLanguage(bundle);
        } else {
            bundle = ResourceBundle.getBundle("messages");
        }
    }
    @FXML
    void onLoginClick(ActionEvent event) throws IOException, SQLException {
        String userNameInput = userName.getText();
        String userPassInput = password.getText();
        if (UsersQuery.select(userNameInput, userPassInput)){
            stage = (Stage)((Button)event.getSource()).getScene().getWindow();
            FXMLLoader fxmlLoader = new FXMLLoader(main.class.getResource("view-appointment-page.fxml"));
            Scene scene = new Scene(fxmlLoader.load());
            stage.setScene(scene);
            stage.show();
        }
        else{
            Alert alert = new Alert(Alert.AlertType.WARNING, "Username and password are incorrect, please try again");
            Optional<ButtonType> result = alert.showAndWait();
        }
    }

    @FXML
    void onCreateClick(ActionEvent event) throws IOException {
        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader(main.class.getResource("create-user-page.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    void onExitClick(ActionEvent event) throws IOException {
        System.exit(0);

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        currentLocale.setText("Location: " + AppConfig.DEFAULT_ZONE_ID);
        setCurrentLocale();
    }
}
