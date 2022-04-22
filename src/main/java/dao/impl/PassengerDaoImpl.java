package dao.impl;

import dao.*;
import model.*;
import service.DatabaseConnectionService;
import java.sql.*;
import java.sql.Date;
import java.util.*;

public class PassengerDaoImpl implements PassengerDao {
    @Override
    public Passenger getById(long id) {
        String query =
                "SELECT p.name, p.phone, a.country, a.city " +
                        "FROM passenger p " +
                        "INNER JOIN address a ON p.address_id = a.id " +
                        "WHERE p.id = " + id + ";";

        Passenger passenger = null;

        try (Connection connection =
                     DatabaseConnectionService.DB_INSTANCE.createConnection();
             Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery(query);
            if (resultSet.next()) {
                passenger = new Passenger(
                        resultSet.getString("name"),
                        resultSet.getString("phone"),
                        new Address(
                                resultSet.getString("country"),
                                resultSet.getString("city")
                        ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return passenger;
    }

    @Override
    public long getId(Passenger passenger) {
        String query =
                "SELECT id FROM passenger WHERE name = '" +
                        passenger.getName() + "' AND phone = '" +
                        passenger.getPhone() + "' AND address_id = " +
                        passenger.getAddressId() + ";";

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
    public Set<Passenger> getAll() {
        Set<Passenger> passengers = null;
        try (Connection connection = DatabaseConnectionService
                .DB_INSTANCE.createConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet =
                     statement.executeQuery(
                             "SELECT * FROM passenger")) {

            passengers = new HashSet<>();
            fillPassengers(resultSet, passengers);
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return passengers;
    }

    @Override
    public Set<Passenger> get(int offset, int perPage, String sort) {
        Set<Passenger> passengers = null;
        try (Connection connection = DatabaseConnectionService
                .DB_INSTANCE.createConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet =
                     statement.executeQuery(
                             "SELECT * FROM passenger " +
                                     "ORDER BY " + sort + " LIMIT " + perPage +
                                     " OFFSET " + offset * perPage + ";")) {

            passengers = new HashSet<>();
            fillPassengers(resultSet, passengers);
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return passengers;
    }

    @Override
    public void save(Passenger passenger) {
        /*
        //Query for Statement
        String query =
                "INSERT INTO passenger (name, phone, address_id)" +
                        " VALUES ('" +
                        passenger.getName() + "', '" +
                        passenger.getPhone() + "', " +
                        passenger.getAddressId() + ");";
        */
        try (Connection connection =
                     DatabaseConnectionService.DB_INSTANCE.createConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(
                     "INSERT INTO passenger (name, phone, address_id)" +
                             " VALUES (?, ?, ?)")) {
            preparedStatement.setString(1, passenger.getName());
            preparedStatement.setString(2, passenger.getPhone());
            preparedStatement.setLong(3, passenger.getAddressId());

            preparedStatement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean update(long passengerId, Passenger passenger) {
        AddressDao addressDao = new AddressDaoImpl();
        if (passenger.getAddressId() != 0) {
            if (addressDao.getById(passenger.getAddressId()) == null) {
                return false;
            }
        } else {
            Address address = new Address(
                    passenger.getAddress().getCountry(),
                    passenger.getAddress().getCity());
            if (addressDao.getId(address) == -1) {
                addressDao.save(address);
            }
            long addressId = addressDao.getId(address);
            passenger = new Passenger(
                    passenger.getName(), passenger.getPhone(), addressId);
        }
        try (Connection connection =
                     DatabaseConnectionService.DB_INSTANCE.createConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(
                     "UPDATE passenger " +
                             "SET name = ?, phone = ?, address_id = ? " +
                             "WHERE id = ?;")) {

            preparedStatement.setString(1, passenger.getName());
            preparedStatement.setString(2, passenger.getPhone());
            preparedStatement.setLong(3, passenger.getAddressId());
            preparedStatement.setLong(4, passengerId);

            if (preparedStatement.execute()) {
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public void delete(long passengerId) {
        try (Connection connection =
                     DatabaseConnectionService.DB_INSTANCE.createConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(
                     "DELETE FROM passenger " +
                             "WHERE id = ?;")) {

            preparedStatement.setLong(1, passengerId);

            preparedStatement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Passenger> getPassengersOfTrip(long tripNumber) {
        List<Passenger> passengers = null;
        try (Connection connection = DatabaseConnectionService
                .DB_INSTANCE.createConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet =
                     statement.executeQuery(
                             "SELECT p.name " +
                                     "FROM passenger p " +
                                     "INNER JOIN pass_in_trip pit ON p.id = pit.psg_id " +
                                     "INNER JOIN trip t ON pit.trip_number = t.number " +
                                     "WHERE t.number = " + tripNumber + ";")) {

            passengers = new ArrayList<>();
            fillPassengers(resultSet, passengers);
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return passengers;
    }

    @Override
    public void registerTrip(PassInTrip passInTrip) {
        try (Connection connection =
                     DatabaseConnectionService.DB_INSTANCE.createConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(
                     "INSERT INTO pass_in_trip(*)" +
                             " VALUES (?, ?, ?, ?)")) {

            preparedStatement.setLong(1, passInTrip.getTripNumber());
            preparedStatement.setLong(2, passInTrip.getPsgId());
            preparedStatement.setDate(3, Date.valueOf(passInTrip.getDate()));
            preparedStatement.setString(4, passInTrip.getPlace());

            preparedStatement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void cancelTrip(long passengerId, long tripNumber) {
        try (Connection connection =
                     DatabaseConnectionService.DB_INSTANCE.createConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(
                     "DELETE FROM pass_in_trip " +
                             "WHERE trip_number = ? AND psg_id = ?;")) {

            preparedStatement.setLong(1, tripNumber);
            preparedStatement.setLong(2, passengerId);

            preparedStatement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void fillPassengers(ResultSet resultSet,
                                Collection<Passenger> collection) throws SQLException {
        Passenger passenger;
        while (resultSet.next()) {
            passenger = new Passenger(
                    resultSet.getString("name"),
                    resultSet.getString("phone"),
                    new Address(
                            resultSet.getString("country"),
                            resultSet.getString("city")
                    )
            );
            collection.add(passenger);
        }
    }
}
