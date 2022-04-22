package service.builder;

import dao.impl.AddressDaoImpl;
import model.Address;
import model.Passenger;

public class PassengerModelBuilder {
    public static Passenger makePassenger(String passengerInfo, Address address) {
        String[] fieldsOfPassenger = passengerInfo.split(",", 4);
        String name = fieldsOfPassenger[0];
        String phone = fieldsOfPassenger[1];
        long addressId = new AddressDaoImpl().getId(address);

        return new Passenger(name, phone, addressId);
    }
}
