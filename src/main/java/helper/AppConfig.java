package helper;

import java.time.ZoneId;
import java.util.Locale;
import java.util.ResourceBundle;

public abstract class AppConfig {
    public static final ZoneId DEFAULT_ZONE_ID = ZoneId.systemDefault();

    public static ResourceBundle bundleInit(){
        ResourceBundle bundle;
        if (Locale.getDefault().getLanguage().equals("fr")) {
            bundle = ResourceBundle.getBundle("messages_fr");
        } else {
            bundle = ResourceBundle.getBundle("messages");
        }
        return bundle;
    }
}
