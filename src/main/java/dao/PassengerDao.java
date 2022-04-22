package dao;

import model.*;
import java.util.*;

public interface PassengerDao {
    Passenger getById(long id);

    long getId(Passenger passenger);

    Set<Passenger> getAll();

    Set<Passenger> get(int offset, int perPage, String sort);

    void save(Passenger passenger);

    boolean update(long passengerId, Passenger passenger);

    void delete(long passengerId);

    List<Passenger> getPassengersOfTrip(long tripNumber);

    void registerTrip(PassInTrip passInTrip);

    void cancelTrip(long passengerId, long tripNumber);
}
