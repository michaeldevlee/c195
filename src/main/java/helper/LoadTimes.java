package helper;

import javafx.scene.control.ComboBox;

/**

 A utility class for loading times into a given ComboBox in hour format (24 hour clock).
 */
public abstract class LoadTimes {
    /**

     A utility class for loading times into a given ComboBox in hour format (24 hour clock).
     */
    public static void loadInTimesByHour(ComboBox comboBox){
        for (int i = 0; i < 24; i++) {
            String[] timeArray = String.format("%02d:%02d:%02d", i, 0, 0).split(":");
            comboBox.getItems().add(String.join(":", timeArray));
        }
    }
}
