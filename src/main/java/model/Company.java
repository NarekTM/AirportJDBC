package model;

import java.time.LocalDate;

public class Company {
    private String name;
    private LocalDate fndDate;

    public Company(String name, LocalDate fndDate) {
        this.name = name;
        this.fndDate = fndDate;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDate getFndDate() {
        return fndDate;
    }

    public void setFndDate(LocalDate fndDate) {
        this.fndDate = fndDate;
    }

    @Override
    public String toString() {
        return "name = " + name +
                ", fndDate = " + fndDate;
    }
}
