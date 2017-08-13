package dao;

import instance.*;
import java.util.List;

public interface ProjectDAO {

    Project save(Project project);

    Project read(int id);

    void update(int id, Project project);

    void delete(Project project);

    List<Project> readAllTable();

    List<Project> readProjectByCompanyID (int companyID);

    List<Project> readProjectByCustomerID(int customerID);
}
