package service.filehandler;

import dao.TripDao;
import dao.impl.TripDaoImpl;
import model.Trip;
import service.builder.TripModelBuilder;
import java.io.*;

public class TripFileService {
    public static void saveAllTripsFromFileToDB(File file) {
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(file))) {
            String tripInfo;
            TripDao tripDao = new TripDaoImpl();
            while ((tripInfo = bufferedReader.readLine()) != null) {
                Trip trip = TripModelBuilder.makeTrip(tripInfo);
                tripDao.save(trip);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
