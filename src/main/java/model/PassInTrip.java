package model;

import java.time.LocalDate;

public class PassInTrip {
    private long tripNumber;
    private long psgId;
    private LocalDate date;
    private String place;

    public PassInTrip(long tripId, long psgId, LocalDate date, String place) {
        this.tripNumber = tripId;
        this.psgId = psgId;
        this.date = date;
        this.place = place;
    }

    public long getTripNumber() {
        return tripNumber;
    }

    public void setTripNumber(long tripNumber) {
        this.tripNumber = tripNumber;
    }

    public long getPsgId() {
        return psgId;
    }

    public void setPsgId(long psgId) {
        this.psgId = psgId;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }
}
