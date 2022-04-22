package service.builder;

import model.Company;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class CompanyModelBuilder {
    public static Company makeCompany(String companyInfo) {
        String[] fieldsOfCompany = companyInfo.split(",", 2);
        String name = fieldsOfCompany[0];
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("M/d/yyyy");
        LocalDate fndDate = LocalDate.parse(fieldsOfCompany[1], formatter);

        return new Company(name, fndDate);
    }
}
