package helper;

import java.sql.Timestamp;
import java.time.*;
import java.time.format.DateTimeParseException;

/**
 * Provides utility methods for formatting time and date objects.
 */
public abstract class TimeFormatter {
    /**
     * Converts a LocalDate and time input string to a Timestamp object in a specified time zone.
     *
     * @param localDate the date portion of the timestamp
     * @param timeInput the time portion of the timestamp as a string
     * @param zone      the time zone to convert the timestamp to
     * @return a Timestamp object representing the combined date and time in the specified time zone
     */
    public static Timestamp getTimeStamp(LocalDate localDate, String timeInput, ZoneId zone) {
        LocalTime localTime = LocalTime.parse(timeInput);
        LocalDateTime localDateTime = localDate.atTime(localTime);

        ZonedDateTime zonedDateTime = localDateTime.atZone(zone);
        Instant instant = zonedDateTime.withZoneSameInstant(ZoneOffset.UTC).toInstant();

        return Timestamp.from(instant);
    }
    /**
     * Converts a Timestamp object to a ZonedDateTime object in the user's time zone.
     *
     * @param timestamp the timestamp to convert
     * @return a ZonedDateTime object representing the timestamp in the user's time zone
     */
    public static ZonedDateTime getZonedTime(Timestamp timestamp){
        // Get the user's time zone from the system settings
        ZoneId userZoneId = ZoneId.systemDefault();

        // Convert the Timestamp to a ZonedDateTime in the user's time zone
        ZonedDateTime zonedDateTime = timestamp.toInstant().atZone(userZoneId);

        return zonedDateTime;
    }


}
