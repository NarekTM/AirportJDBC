package service.builder;

import model.Address;

public class AddressModelBuilder {
    public static Address makeAddress(String addressInfo) {
        String[] fieldsOfAddress = addressInfo.split(",", 4);
        String country = fieldsOfAddress[2];
        String city = fieldsOfAddress[3];

        return new Address(country, city);
    }
}
