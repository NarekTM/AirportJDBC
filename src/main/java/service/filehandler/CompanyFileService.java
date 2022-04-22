package service.filehandler;

import dao.CompanyDao;
import dao.impl.CompanyDaoImpl;
import model.Company;
import service.builder.CompanyModelBuilder;
import java.io.*;

public class CompanyFileService {
    public static void saveAllCompaniesFromFileToDB(File file) {
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(file))) {
            String companyInfo;
            CompanyDao companyDao = new CompanyDaoImpl();
            while ((companyInfo = bufferedReader.readLine()) != null) {
                Company company = CompanyModelBuilder.makeCompany(companyInfo);
                companyDao.save(company);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
