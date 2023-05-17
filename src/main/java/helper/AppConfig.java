package helper;

import java.time.ZoneId;
import java.util.Locale;
import java.util.ResourceBundle;

/**
 * This class represents the application configuration.
 * It provides a default time zone and a method to initialize the resource bundle.
 */
public abstract class AppConfig {
    /**
     * The default time zone used by the application.
     */
    public static final ZoneId DEFAULT_ZONE_ID = ZoneId.systemDefault();

    /**
     * Initializes the resource bundle based on the default locale of the system.
     * If the default language is French, it loads the "messages_fr" bundle, otherwise it loads the "messages" bundle.
     * @return The initialized resource bundle.
     */
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
