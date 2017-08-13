package dao;

import instance.*;
import java.util.List;

public interface CompanyDAO {

    Company save(Company company);

    Company read(int id);

    void update(int id, Company company);

    void delete(int id, List<Project> projectList);

    List<Company> readAllTable();
}
