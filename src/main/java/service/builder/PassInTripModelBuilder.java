package service.builder;

import model.PassInTrip;
import java.time.*;
import java.time.format.DateTimeFormatter;

public class PassInTripModelBuilder {
    public static PassInTrip makePassInTrip(String passInTripInfo) {
        String[] fieldsOfPassInTrip = passInTripInfo.split(",", 4);
        long tripNumber = Long.parseLong(fieldsOfPassInTrip[0]);
        long psgId = Long.parseLong(fieldsOfPassInTrip[1]);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS");
        LocalDate date = LocalDateTime.parse(fieldsOfPassInTrip[2], formatter).toLocalDate();
        String place = fieldsOfPassInTrip[3];

        return new PassInTrip(tripNumber, psgId, date, place);
    }
}
