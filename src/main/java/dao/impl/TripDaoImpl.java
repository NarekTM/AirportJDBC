package dao.impl;

import dao.TripDao;
import model.Trip;
import service.DatabaseConnectionService;
import java.sql.*;
import java.util.*;

public class TripDaoImpl implements TripDao {
    @Override
    public Trip getByNumber(long number) {
        String query =
                "SELECT t.number, c.name, t.plane, t.town_from, t.town_to, t.time_out, t.time_in " +
                        "FROM trip t " +
                        "INNER JOIN company c ON t.comp_id = c.id " +
                        "WHERE t.number = " + number + ";";

        Trip trip = null;

        try (Connection connection =
                     DatabaseConnectionService.DB_INSTANCE.createConnection();
             Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery(query);
            if (resultSet.next()) {
                trip = new Trip(
                        resultSet.getLong("number"),
                        resultSet.getLong("comp_id"),
                        resultSet.getString("plane"),
                        resultSet.getString("town_from"),
                        resultSet.getString("town_to"),
                        resultSet.getTime("time_out").toLocalTime(),
                        resultSet.getTime("time_in").toLocalTime()
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return trip;
    }

    @Override
    public Set<Trip> getAll() {
        Set<Trip> trips = null;
        try (Connection connection = DatabaseConnectionService
                .DB_INSTANCE.createConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet =
                     statement.executeQuery(
                             "SELECT * FROM trip")) {

            trips = new HashSet<>();
            fillTrips(resultSet, trips);
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return trips;
    }

    @Override
    public Set<Trip> get(int offset, int perPage, String sort) {
        Set<Trip> trips = null;
        try (Connection connection = DatabaseConnectionService
                .DB_INSTANCE.createConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet =
                     statement.executeQuery(
                             "SELECT * FROM trip " +
                                     "ORDER BY " + sort + " LIMIT " + perPage +
                                     " OFFSET " + offset * perPage + ";")) {

            trips = new HashSet<>();
            fillTrips(resultSet, trips);
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return trips;
    }

    @Override
    public void save(Trip trip) {
        try (Connection connection =
                     DatabaseConnectionService.DB_INSTANCE.createConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(
                     "INSERT INTO trip (number, comp_id, plane, town_from," +
                             "town_to, time_out, time_in)" +
                             " VALUES (?, ?, ?, ?, ?, ?, ?)")) {

            setPrepStatQueryByValues(trip, preparedStatement);

            preparedStatement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean update(long number, Trip trip) {
//        CompanyDao companyDao = new CompanyDaoImpl();
//        if (trip.getCompId() != 0) {
//            if (companyDao.getById(trip.getCompId()) == null) {
//                return false;
//            }
//        } else {
//            Company company = new Company(
//                    trip.getAddress().getCountry(),
//                    trip.getAddress().getCity());
//            if (addressDao.getId(address) == -1) {
//                addressDao.save(address);
//            }
//            long addressId = addressDao.getId(address);
//            trip = new Passenger(
//                    trip.getName(), trip.getPhone(), addressId);
//        }

        try (Connection connection =
                     DatabaseConnectionService.DB_INSTANCE.createConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(
                     "UPDATE trip " +
                             "SET number = ?, comp_id = ?, plane = ?, " +
                             "town_from = ?, town_to = ?, time_out = ?, time_in = ? " +
                             "WHERE number = ?;")) {

            setPrepStatQueryByValues(trip, preparedStatement);

            if (preparedStatement.execute()) {
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    private void setPrepStatQueryByValues(Trip trip, PreparedStatement preparedStatement)
            throws SQLException {
        preparedStatement.setLong(1, trip.getNumber());
        preparedStatement.setLong(2, trip.getCompId());
        preparedStatement.setString(3, trip.getPlane());
        preparedStatement.setString(4, trip.getTownFrom());
        preparedStatement.setString(5, trip.getTownTo());
        preparedStatement.setTime(6, Time.valueOf(trip.getTimeOut()));
        preparedStatement.setTime(7, Time.valueOf(trip.getTimeIn()));
    }

    @Override
    public void delete(long number) {
        try (Connection connection =
                     DatabaseConnectionService.DB_INSTANCE.createConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(
                     "DELETE FROM trip " +
                             "WHERE number = ?;")) {

            preparedStatement.setLong(1, number);

            preparedStatement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Trip> getTripsFrom(String city) {
        List<Trip> trips = null;
        try (Connection connection = DatabaseConnectionService
                .DB_INSTANCE.createConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet =
                     statement.executeQuery(
                             "SELECT * FROM trip " +
                                     "WHERE town_from = " + city + ";")) {

            trips = new ArrayList<>();
            fillTrips(resultSet, trips);
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return trips;
    }

    @Override
    public List<Trip> getTripsTo(String city) {
        List<Trip> trips = null;
        try (Connection connection = DatabaseConnectionService
                .DB_INSTANCE.createConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet =
                     statement.executeQuery(
                             "SELECT * FROM trip " +
                                     "WHERE town_to = " + city + ";")) {

            trips = new ArrayList<>();
            fillTrips(resultSet, trips);
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return trips;
    }

    private void fillTrips(ResultSet resultSet, Collection<Trip> collection)
            throws SQLException {
        Trip trip;
        while (resultSet.next()) {
            trip = new Trip(
                    resultSet.getLong("number"),
                    resultSet.getLong("comp_id"),
                    resultSet.getString("plane"),
                    resultSet.getString("town_from"),
                    resultSet.getString("town_to"),
                    resultSet.getTime("time_out").toLocalTime(),
                    resultSet.getTime("time_in").toLocalTime()
            );
            collection.add(trip);
        }
    }
}
