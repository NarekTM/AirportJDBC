package service.filehandler;

import dao.PassInTripDao;
import dao.impl.PassInTripDaoImpl;
import model.PassInTrip;
import service.builder.PassInTripModelBuilder;
import java.io.*;

public class PassInTripFileService {
    public static void saveAllPassInTripsFromFileToDB(File file) {
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(file))) {
            String passInTripInfo;
            PassInTripDao passInTripDao = new PassInTripDaoImpl();
            while ((passInTripInfo = bufferedReader.readLine()) != null) {
                PassInTrip passInTrip = PassInTripModelBuilder.makePassInTrip(passInTripInfo);
                passInTripDao.save(passInTrip);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
