package controller;

import instance.*;
import java.util.List;

public interface CompanyController {

    List<Company> readAllTable();

    Company addData(String companyName);

    void update(int id, String newCompanyName);

    void delete(int id, List<Project> projectList);
}
