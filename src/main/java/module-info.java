module com.main.c195 {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens com.main.c195 to javafx.fxml;
    exports com.main.c195;
    exports controller;
    opens controller to javafx.fxml;
}