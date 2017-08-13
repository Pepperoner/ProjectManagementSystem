package controller.controllerImpl;

import controller.CompanyController;
import dao.CompanyDAO;
import instance.*;
import java.util.ArrayList;
import java.util.List;


public class CompanyControllerImpl implements CompanyController {

    private CompanyDAO dao;

    public CompanyControllerImpl(CompanyDAO dao) {
        this.dao = dao;
    }

    @Override
    public List<Company> readAllTable() {
        List<Company> companyList = dao.readAllTable();
        if (companyList == null || companyList.isEmpty()) {
            System.out.println("List is empty!");
            return new ArrayList<>();
        }
        return companyList;
    }

    @Override
    public Company addData(String companyName) {
        return dao.save(new Company(companyName));
    }

    @Override
    public void update(int id, String newCompanyName) {
        dao.update(id, new Company(newCompanyName));
    }

    @Override
    public void delete(int id, List<Project> projectList) {
        dao.delete(id, projectList);
    }
}
