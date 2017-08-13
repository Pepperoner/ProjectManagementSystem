package dao.DAOImpl;

import dao.CustomerDAO;
import dao.pooledJdbc.PooledJDBCUserDAO;
import dao.logger.ExceptionLogger;
import instance.*;
import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CustomerDAOImpl extends PooledJDBCUserDAO implements CustomerDAO {

    public CustomerDAOImpl(DataSource dataSource) {
        super(dataSource);
    }

    @Override
    public Customer save(Customer customer) {
        try (Connection connection = getConnection(); PreparedStatement ps = connection.prepareStatement("INSERT INTO customers(customer_name) VALUE(?)")) {
            ps.setString(1, customer.getCustomerName());
            ps.execute();
        } catch (SQLException e) {
            System.err.println("Error while working with database!");
            ExceptionLogger.initLogger(e.toString());
        }
        return customer;
    }

    @Override
    public Customer read(int id) {
        Customer customer = null;
        try (Connection connection = getConnection(); PreparedStatement ps = connection.prepareStatement("SELECT customers.id, customer_name FROM customers WHERE customers.id = ?;")) {
            ps.setInt(1, id);
            try (ResultSet resultSet = ps.executeQuery()) {
                while (resultSet.next()) {
                    int customerID = resultSet.getInt("id");
                    String customerName = resultSet.getString("customer_name");
                    customer = new Customer(customerName);
                    customer.setId(customerID);
                }
            }
        } catch (SQLException e) {
            System.err.println("Error while working with database!");
            ExceptionLogger.initLogger(e.toString());
        }
        return customer;
    }

    @Override
    public void update(int id, Customer customer) {
        try (Connection connection = getConnection(); PreparedStatement ps = connection.prepareStatement("UPDATE customers SET customer_name = ? WHERE id = ?;")) {
            ps.setString(1, customer.getCustomerName());
            ps.setInt(2, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Error while working with database!");
            ExceptionLogger.initLogger(e.toString());
        }
    }

    @Override
    public void delete(int id, List<Project> projectList) {
        Connection connection = null;
        try {
            connection = getConnection();
            connection.setAutoCommit(false);

            for (Project project : projectList) {

                for (Developer developer : project.getDeveloperList()) {
                    try (PreparedStatement ps = connection.prepareStatement("DELETE FROM developers_has_skills WHERE Developers_id = ?")) {
                        ps.setInt(1, developer.getId());
                        ps.executeUpdate();
                    }

                    try (PreparedStatement ps2 = connection.prepareStatement("DELETE FROM developers WHERE developers.id = ?")) {
                        ps2.setInt(1, developer.getId());
                        ps2.executeUpdate();
                    }
                }

                try (PreparedStatement ps = connection.prepareStatement("DELETE FROM projects WHERE projects.id = ?")) {
                    ps.setInt(1, project.getId());
                    ps.executeUpdate();
                }
            }

            try (PreparedStatement ps = connection.prepareStatement("DELETE FROM customers WHERE id = ?;")) {
                ps.setInt(1, id);
                ps.executeUpdate();
            }

            connection.commit();

        } catch (Exception e) {
            try {
                if (connection != null) {
                    connection.rollback();
                }
            } catch (SQLException e1) {
                ExceptionLogger.initLogger(e1.toString());
            }
            System.err.println("Error while working with database!");
            ExceptionLogger.initLogger(e.toString());
        } finally {
            if (connection != null) {
                try {
                    connection.setAutoCommit(true);
                } catch (SQLException e) {
                    ExceptionLogger.initLogger(e.toString());
                }
                try {
                    connection.close();
                } catch (SQLException e) {
                    ExceptionLogger.initLogger(e.toString());
                }
            }
        }
    }

    @Override
    public List<Customer> readAllTable() {
        List<Customer> customerList = new ArrayList<>();
        try (Connection connection = getConnection(); PreparedStatement ps = connection.prepareStatement("SELECT * FROM customers")) {
            try (ResultSet resultSet = ps.executeQuery()) {
                while (resultSet.next()) {
                    int id = resultSet.getInt("id");
                    String customerName = resultSet.getString("customer_name");
                    Customer customer = new Customer(customerName);
                    customer.setId(id);
                    customerList.add(customer);
                }
            }
        } catch (SQLException e) {
            System.err.println("Error while working with database!");
            ExceptionLogger.initLogger(e.toString());
        }
        return customerList;
    }
}
