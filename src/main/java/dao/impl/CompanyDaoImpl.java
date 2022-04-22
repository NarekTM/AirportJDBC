package dao.impl;

import dao.CompanyDao;
import model.Company;
import service.DatabaseConnectionService;
import java.sql.*;
import java.util.HashSet;
import java.util.Set;

public class CompanyDaoImpl implements CompanyDao {
    @Override
    public Company getById(long id) {
        String query =
                "SELECT * FROM company WHERE id = " + id + ";";

        Company company = null;

        try (Connection connection =
                     DatabaseConnectionService.DB_INSTANCE.createConnection();
             Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery(query);
            if (resultSet.next()) {
                company = new Company(
                        resultSet.getString("name"),
                        resultSet.getDate("fnd_date").toLocalDate()
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return company;
    }

    @Override
    public Set<Company> getAll() {
        Set<Company> companies = null;
        try (Connection connection = DatabaseConnectionService
                .DB_INSTANCE.createConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet =
                     statement.executeQuery(
                             "SELECT * FROM company")) {

            companies = fillCompanies(resultSet);
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return companies;
    }

    @Override
    public Set<Company> get(int offset, int perPage, String sort) {
        Set<Company> companies = null;
        try (Connection connection = DatabaseConnectionService
                .DB_INSTANCE.createConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet =
                     statement.executeQuery(
                             "SELECT * FROM company " +
                                     "ORDER BY " + sort + " LIMIT " + perPage +
                                     " OFFSET " + offset + ";")) {

            companies = fillCompanies(resultSet);
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return companies;
    }

    private Set<Company> fillCompanies(ResultSet resultSet) throws SQLException {
        Set<Company> companies = new HashSet<>();

        Company company;
        while (resultSet.next()) {
            company = new Company(
                    resultSet.getString("name"),
                    resultSet.getDate("fnd_date").toLocalDate()
            );
            companies.add(company);
        }

        return companies;
    }

    @Override
    public void save(Company company) {
        /*
        //Query for Statement
        String query =
                "INSERT INTO company (name, fnd_date)" +
                        " VALUES ('" +
                        company.getName() + "', '" +
                        company.getFndDate() + "');";
        */
        try (Connection connection =
                     DatabaseConnectionService.DB_INSTANCE.createConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(
                     "INSERT INTO company (name, fnd_date)" +
                             " VALUES (?, ?)")) {
            preparedStatement.setString(1, company.getName());
            preparedStatement.setDate(2, Date.valueOf(company.getFndDate()));

            preparedStatement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean update(long companyId, Company company) {
        try (Connection connection =
                     DatabaseConnectionService.DB_INSTANCE.createConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(
                     "UPDATE company " +
                             "SET name = ?, fnd_date = ? " +
                             "WHERE id = ?;")) {
            preparedStatement.setString(1, company.getName());
            preparedStatement.setDate(2, Date.valueOf(company.getFndDate()));
            preparedStatement.setLong(3, companyId);

            if (preparedStatement.execute()) {
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public void delete(long companyId) {
        try (Connection connection =
                     DatabaseConnectionService.DB_INSTANCE.createConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(
                     "DELETE FROM company " +
                             "WHERE id = ?;")) {

            preparedStatement.setLong(1, companyId);

            preparedStatement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
