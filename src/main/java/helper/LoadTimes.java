package helper;

import javafx.scene.control.ComboBox;

public abstract class LoadTimes {
    public static void loadInTimesByHour(ComboBox comboBox){
        for (int i = 0; i < 24; i++) {
            String[] timeArray = String.format("%02d:%02d:%02d", i, 0, 0).split(":");
            comboBox.getItems().add(String.join(":", timeArray));
        }
    }
}
