package dao;

import instance.*;
import java.util.List;

public interface CustomerDAO {

    Customer save(Customer customer);

    Customer read(int id);

    void update(int id, Customer customer);

    void delete(int id, List<Project> projectList);

    List<Customer> readAllTable();
}
