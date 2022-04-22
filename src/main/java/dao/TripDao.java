package dao;

import model.Trip;
import java.util.*;

public interface TripDao {
    Trip getByNumber(long number);

    Set<Trip> getAll();

    Set<Trip> get(int offset, int perPage, String sort);

    void save(Trip trip);

    boolean update(long number, Trip trip);

    void delete(long number);

    List<Trip> getTripsFrom(String city);

    List<Trip> getTripsTo(String city);
}
