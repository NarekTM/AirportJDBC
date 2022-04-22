package service.builder;

import model.Trip;
import java.time.*;
import java.time.format.DateTimeFormatter;

public class TripModelBuilder {
    public static Trip makeTrip(String tripInfo) {
        String[] fieldsOfTrip = tripInfo.split(",", 7);
        long number = Long.parseLong(fieldsOfTrip[0]);
        long compId = Long.parseLong(fieldsOfTrip[1]);
        String plane = fieldsOfTrip[2];
        String townFrom = fieldsOfTrip[3];
        String townTo = fieldsOfTrip[4];
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS");
        LocalTime timeOut = LocalDateTime.parse(fieldsOfTrip[5], formatter).toLocalTime();
        LocalTime timeIn = LocalDateTime.parse(fieldsOfTrip[6], formatter).toLocalTime();

        return new Trip(number, compId, plane, townFrom, townTo, timeOut, timeIn);
    }
}
