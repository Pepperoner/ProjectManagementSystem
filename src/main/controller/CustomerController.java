package controller;

import instance.*;
import java.util.List;

public interface CustomerController {

    List<Customer> readAllTable();

    Customer addData(String customerName);

    void update(int id, String newCustomerName);

    void delete(int id, List<Project> customerProjectList);
}
