package helper;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public abstract class TimeFormatter {
    public static LocalDateTime getDateAndTime(LocalDate localDate, String timeInput){
        try {
            String[] timeArray = timeInput.split(":");
            int hour = Integer.parseInt(timeArray[0]);
            int minute = Integer.parseInt(timeArray[1]);
            LocalDateTime localDateTime = LocalDateTime.of(localDate.getYear(), localDate.getMonth(), localDate.getDayOfMonth(), hour, minute, 0);
            return localDateTime;
        } catch (DateTimeParseException ex) {
            System.out.println("Invalid Time: " + timeInput);
            return null;
        }
    }


}
