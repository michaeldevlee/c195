package helper;

import dao.AppointmentsQuery;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.DatePicker;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.*;
import java.time.format.DateTimeFormatter;

/**
 * This abstract class provides various methods for validating appointment data.
 */
public abstract class AppValidCheck {

    /**
     * Returns a list of errors for the given business hours and appointment start and end times.
     *
     * @param fromTime       the appointment start time in "HH:mm:ss" format
     * @param fromDatePicker the appointment start date
     * @param toTime         the appointment end time in "HH:mm:ss" format
     * @param toDatePicker   the appointment end date
     * @return the list of errors
     */
    public static ObservableList<String> businessHoursErrorList(String fromTime, DatePicker fromDatePicker, String toTime, DatePicker toDatePicker){

        ObservableList<String> listOfErrors = FXCollections.observableArrayList();

        LocalDate startDate = ZonedDateTime.of(fromDatePicker.getValue(), LocalTime.of(8, 0), ZoneId.of("America/New_York")).toLocalDate();
        LocalDate endDate = ZonedDateTime.of(toDatePicker.getValue(), LocalTime.of(22, 0), ZoneId.of("America/New_York")).toLocalDate();

        DayOfWeek startDay = startDate.getDayOfWeek();
        DayOfWeek endDay = endDate.getDayOfWeek();

        LocalTime openingTime = ZonedDateTime.of(LocalDate.now(), LocalTime.of(8, 0), ZoneId.of("America/New_York")).toLocalTime();
        LocalTime closingTime = ZonedDateTime.of(LocalDate.now(), LocalTime.of(22, 0), ZoneId.of("America/New_York")).toLocalTime();

        LocalTime localTime = LocalTime.parse(fromTime, DateTimeFormatter.ofPattern("HH:mm:ss"));
        LocalTime localTime2 = LocalTime.parse(toTime, DateTimeFormatter.ofPattern("HH:mm:ss"));

        ZonedDateTime zonedDateTime = ZonedDateTime.of(fromDatePicker.getValue(), localTime, ZoneId.systemDefault());
        ZonedDateTime zonedDateTime2 = ZonedDateTime.of(toDatePicker.getValue(), localTime2, ZoneId.systemDefault());

        ZonedDateTime startDateTimeInEST = zonedDateTime.withZoneSameInstant(ZoneId.of("America/New_York"));
        ZonedDateTime endDateTimeInEST = zonedDateTime2.withZoneSameInstant(ZoneId.of("America/New_York"));

        LocalTime endTimeInEST = endDateTimeInEST.toLocalTime();
        LocalTime startTimeInEST = startDateTimeInEST.toLocalTime();

        if (startTimeInEST.isAfter(closingTime)){
            listOfErrors.add("Your start time is after closing time in EST");
        }

        if (startTimeInEST.isBefore(openingTime)){
            listOfErrors.add("Your start time is before opening time in EST");
        }


        if (endTimeInEST.isAfter(closingTime)){
            listOfErrors.add("Your end time is after closing time in EST");
        }

        if (endTimeInEST.isBefore(openingTime)){
            listOfErrors.add("Your end time is before opening time in EST");
        }

        if (startDay == DayOfWeek.SATURDAY || startDay == DayOfWeek.SUNDAY){
            listOfErrors.add("Your chosen start date falls on a weekend. Please choose a weekday.");
        }

        if (endDay == DayOfWeek.SATURDAY || endDay == DayOfWeek.SUNDAY){
            listOfErrors.add("Your chosen end date falls on a weekend. Please choose a weekday.");
        }

        return listOfErrors;
    }


}
