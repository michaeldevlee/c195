package helper;

import java.sql.Timestamp;
import java.time.*;
import java.time.format.DateTimeParseException;

public abstract class TimeFormatter {

    public static Timestamp getTimeStamp(LocalDate localDate, String timeInput, ZoneId zone) {
        LocalTime localTime = LocalTime.parse(timeInput);
        LocalDateTime localDateTime = localDate.atTime(localTime);

        ZonedDateTime zonedDateTime = localDateTime.atZone(zone);
        Instant instant = zonedDateTime.withZoneSameInstant(ZoneOffset.UTC).toInstant();

        return Timestamp.from(instant);
    }

    public static ZonedDateTime getZonedTime(Timestamp timestamp){
        // Get the user's time zone from the system settings
        ZoneId userZoneId = ZoneId.systemDefault();

        // Convert the Timestamp to a ZonedDateTime in the user's time zone
        ZonedDateTime zonedDateTime = timestamp.toInstant().atZone(userZoneId);

        return zonedDateTime;
    }


}
