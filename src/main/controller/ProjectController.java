package controller;

import instance.*;
import java.util.List;

public interface ProjectController {

    List<Project> readAllTable();

    Project addData(String projectName, int projectCost, Company company, Customer customer, List<Developer> developerList);

    Project readByID(int projectID);

    void update(int id, String projectName, int projectCost, Customer customer, Company company, List<Developer> developerList);

    List<Project> readProjectByCompanyID (int companyID);

    List<Project> readProjectByCustomerID(int customerID);

    void delete(Project project);
}
