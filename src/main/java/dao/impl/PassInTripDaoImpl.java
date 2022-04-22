package dao.impl;

import dao.PassInTripDao;
import model.PassInTrip;
import service.DatabaseConnectionService;
import java.sql.*;

public class PassInTripDaoImpl implements PassInTripDao {
    @Override
    public void save(PassInTrip passInTrip) {
        /*
        //Query for Statement
        String query =
                "INSERT INTO pass_in_trip (trip_number, psg_id, date, place)" +
                        " VALUES (" +
                        tripNumber + ", " +
                        psgId + "," +
                        date + ", '" +
                        place + "');";
         */

        try (Connection connection =
                     DatabaseConnectionService.DB_INSTANCE.createConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(
                     "INSERT INTO pass_in_trip (trip_number, psg_id, date, place)" +
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
}
