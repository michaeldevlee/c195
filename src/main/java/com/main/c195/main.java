package com.main.c195;

import dao.CustomersQuery;
import helper.JDBC;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;
import java.time.ZoneId;


public class main extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        ZoneId zoneId = ZoneId.systemDefault();




        FXMLLoader fxmlLoader = new FXMLLoader(main.class.getResource("login-page.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("Hello!");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) throws SQLException {
        JDBC.openConnection();
        launch();
    }
}