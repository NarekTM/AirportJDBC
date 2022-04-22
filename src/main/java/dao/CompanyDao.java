package dao;

import model.Company;
import java.util.Set;

public interface CompanyDao {
    Company getById(long id);

    Set<Company> getAll();

    Set<Company> get(int offset, int perPage, String sort);

    void save(Company company);

    boolean update(long companyId, Company company);

    void delete(long companyId);
}
