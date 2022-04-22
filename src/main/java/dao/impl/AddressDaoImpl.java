package dao.impl;

import dao.AddressDao;
import model.Address;
import service.DatabaseConnectionService;
import java.sql.*;

public class AddressDaoImpl implements AddressDao {
    @Override
    public void save(Address address) {
        /*
        //Query for Statement
        String query =
                "INSERT INTO address (country, city)" +
                        " VALUES ('" +
                        address.getCountry() + "', '" +
                        address.getCity() + "');";
        */

        try (Connection connection =
                     DatabaseConnectionService.DB_INSTANCE.createConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(
                     "INSERT INTO address(country, city) VALUES (?, ?)")) {
            preparedStatement.setString(1, address.getCountry());
            preparedStatement.setString(2, address.getCity());

            preparedStatement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public long getId(Address address) {
        String query =
                "SELECT id FROM address WHERE country LIKE '" +
                        address.getCountry() + "' AND city LIKE '" +
                        address.getCity() + "';";

        try (Connection connection =
                     DatabaseConnectionService.DB_INSTANCE.createConnection();
             Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery(query);
            if (resultSet.next()) {
                return resultSet.getLong("id");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }

    @Override
    public Address getById(long id) {
        String query =
                "SELECT * FROM address WHERE id = " + id + ";";

        Address address = null;

        try (Connection connection =
                     DatabaseConnectionService.DB_INSTANCE.createConnection();
             Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery(query);
            if (resultSet.next()) {
                address = new Address(
                        resultSet.getString("country"),
                        resultSet.getString("city")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return address;
    }
}
