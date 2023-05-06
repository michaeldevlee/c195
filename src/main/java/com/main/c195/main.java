package com.main.c195;

import helper.CustomersQuery;
import helper.JDBC;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDate;

public class main extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(main.class.getResource("hello-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 320, 240);
        stage.setTitle("Hello!");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) throws SQLException {
        JDBC.openConnection();
        int rowsAffected = CustomersQuery.insert(10,
                "Mike",
                "100 Hello St",
                "20000",
                "800-400-1000",
                LocalDate.now(),
                "Michael",
                new Timestamp(System.currentTimeMillis()),
                "Now",
                1);

        if (rowsAffected > 0){
            System.out.println("Insert successful");
        }
        else{
            System.out.println("Insert failed");
        }

        JDBC.closeConnection();
        //launch();
    }
}