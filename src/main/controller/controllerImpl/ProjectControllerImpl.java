package controller.controllerImpl;

import controller.ProjectController;
import dao.ProjectDAO;
import instance.*;
import java.util.ArrayList;
import java.util.List;

public class ProjectControllerImpl implements ProjectController {

    private ProjectDAO dao;

    public ProjectControllerImpl(ProjectDAO dao) {
        this.dao = dao;
    }

    @Override
    public List<Project> readAllTable() {
        List<Project> projectList = dao.readAllTable();
        if (projectList == null || projectList.isEmpty()) {
            System.out.println("List is empty!");
            return new ArrayList<>();
        }
        return dao.readAllTable();
    }

    @Override
    public Project addData(String projectName, int projectCost, Company company, Customer customer, List<Developer> developerList) {
        return dao.save(new Project(projectName, projectCost, company, customer, developerList));
    }

    @Override
    public Project readByID(int projectID) {
        return dao.read(projectID);
    }

    @Override
    public void update(int id, String projectName, int projectCost, Customer customer, Company company, List<Developer> developerList) {
        dao.update(id, new Project(projectName, projectCost, company, customer, developerList));
    }

    @Override
    public List<Project> readProjectByCompanyID(int companyID) {
        return dao.readProjectByCompanyID(companyID);
    }

    @Override
    public List<Project> readProjectByCustomerID(int customerID) {
        return dao.readProjectByCustomerID(customerID);
    }

    @Override
    public void delete(Project project) {
        dao.delete(project);
    }
}
