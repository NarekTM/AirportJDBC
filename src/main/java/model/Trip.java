package model;

import java.time.LocalTime;

public class Trip {
    private long number;
    private long compId;
    private Company company;
    private String plane;
    private String townFrom;
    private String townTo;
    private LocalTime timeOut;
    private LocalTime timeIn;

    public Trip(long number, long compId, String plane, String townFrom,
                String townTo, LocalTime timeOut, LocalTime timeIn) {
        this.number = number;
        this.compId = compId;
        this.plane = plane;
        this.townFrom = townFrom;
        this.townTo = townTo;
        this.timeOut = timeOut;
        this.timeIn = timeIn;
    }

    public Trip(long number, Company company, String plane, String townFrom,
                String townTo, LocalTime timeOut, LocalTime timeIn) {
        this.number = number;
        this.company = company;
        this.plane = plane;
        this.townFrom = townFrom;
        this.townTo = townTo;
        this.timeOut = timeOut;
        this.timeIn = timeIn;
    }

    public long getNumber() {
        return number;
    }

    public void setNumber(long number) {
        this.number = number;
    }

    public long getCompId() {
        return compId;
    }

    public void setCompId(long compId) {
        this.compId = compId;
    }

    public String getPlane() {
        return plane;
    }

    public void setPlane(String plane) {
        this.plane = plane;
    }

    public String getTownFrom() {
        return townFrom;
    }

    public void setTownFrom(String townFrom) {
        this.townFrom = townFrom;
    }

    public String getTownTo() {
        return townTo;
    }

    public void setTownTo(String townTo) {
        this.townTo = townTo;
    }

    public LocalTime getTimeOut() {
        return timeOut;
    }

    public void setTimeOut(LocalTime timeOut) {
        this.timeOut = timeOut;
    }

    public LocalTime getTimeIn() {
        return timeIn;
    }

    public void setTimeIn(LocalTime timeIn) {
        this.timeIn = timeIn;
    }

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }

    @Override
    public String toString() {
        String compInfo;
        if (compId != 0) {
            compInfo = "company ID = " + compId;
        } else {
            compInfo = "company name = " + company.getName();
        }
        return "number = " + number +
                ", " + compInfo +
                ", plane = " + plane +
                ", townFrom = " + townFrom +
                ", townTo = " + townTo +
                ", timeOut = " + timeOut +
                ", timeIn = " + timeIn;
    }
}
