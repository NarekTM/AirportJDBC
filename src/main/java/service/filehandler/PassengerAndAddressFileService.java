package service.filehandler;

import dao.*;
import dao.impl.*;
import model.*;
import service.builder.*;
import java.io.*;

public class PassengerAndAddressFileService {
//    public static void saveAllPassengersFromFileToDB(File file) {
//        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(file))) {
//            String passengerInfo;
//            boolean isFirstLineHeader = true;
//            PassengerDao passengerDao = new PassengerDaoImpl();
//            while ((passengerInfo = bufferedReader.readLine()) != null) {
//                if (isFirstLineHeader) {
//                    isFirstLineHeader = false;
//                    continue;
//                }
//                Passenger passenger = PassengerDao.makePassenger(passengerInfo);
//                passengerDao.save(passenger);
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }

    public static void saveAllPassengersAndAddressesFromFileToDB(File file) {
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(file))) {
            String passengerInfo;
            boolean isFirstLineHeader = true;
            PassengerDao passengerDao = new PassengerDaoImpl();
            AddressDao addressDao = new AddressDaoImpl();
            while ((passengerInfo = bufferedReader.readLine()) != null) {
                if (isFirstLineHeader) {
                    isFirstLineHeader = false;
                    continue;
                }

                if (passengerInfo.contains("'")) {
                    passengerInfo = passengerInfo.replaceAll("'", "’");
                }

                Address address = AddressModelBuilder.makeAddress(passengerInfo);
                if (addressDao.getId(address) == -1) {
                    addressDao.save(address);
                }
                Passenger passenger = PassengerModelBuilder.makePassenger(passengerInfo, address);
                passengerDao.save(passenger);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
