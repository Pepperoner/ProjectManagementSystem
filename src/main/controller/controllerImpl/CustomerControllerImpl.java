package controller.controllerImpl;

import controller.CustomerController;
import dao.CustomerDAO;
import instance.*;
import java.util.ArrayList;
import java.util.List;

public class CustomerControllerImpl implements CustomerController {

    private CustomerDAO dao;

    public CustomerControllerImpl(CustomerDAO dao) {
        this.dao = dao;
    }

    @Override
    public List<Customer> readAllTable() {
        List<Customer> customersList = dao.readAllTable();
        if (customersList == null || customersList.isEmpty()){
            System.out.println("List is empty!");
            return new ArrayList<>();
        }
        return dao.readAllTable();
    }

    @Override
    public Customer addData(String customerName) {
        return dao.save(new Customer(customerName));
    }

    @Override
    public void update(int id, String newCustomerName) {
        dao.update(id,new Customer(newCustomerName));
    }

    @Override
    public void delete(int id, List<Project> customerProjectList) {
        dao.delete(id, customerProjectList);
    }
}
