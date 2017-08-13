package dao.DAOImpl;

import dao.CompanyDAO;
import dao.pooledJdbc.PooledJDBCUserDAO;
import dao.logger.ExceptionLogger;
import instance.*;
import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CompanyDAOImpl extends PooledJDBCUserDAO implements CompanyDAO {

    public CompanyDAOImpl(DataSource dataSource) {
        super(dataSource);
    }

    @Override
    public Company save(Company company) {
        try (Connection connection = getConnection(); PreparedStatement ps = connection.prepareStatement("INSERT INTO companies(company_name) VALUE (?)")) {
            ps.setString(1, company.getCompanyName());
            ps.execute();
        } catch (SQLException e) {
            System.err.println("Error while working with database!");
            ExceptionLogger.initLogger(e.toString());
        }
        return company;
    }

    @Override
    public Company read(int id) {
        Company company = null;
        try (Connection connection = getConnection(); PreparedStatement ps = connection.prepareStatement("SELECT companies.id, company_name FROM companies WHERE companies.id = ?")) {
            ps.setInt(1, id);
            try (ResultSet resultSet = ps.executeQuery()) {
                while (resultSet.next()) {
                    int companyID = resultSet.getInt("id");
                    String companyName = resultSet.getString("company_name");
                    company = new Company(companyName);
                    company.setId(companyID);
                }
            }
        } catch (SQLException e) {
            System.err.println("Error while working with database!");
            ExceptionLogger.initLogger(e.toString());
        }
        return company;
    }

    @Override
    public void update(int id, Company company) {
        try (Connection connection = getConnection(); PreparedStatement ps = connection.prepareStatement("UPDATE companies SET company_name = ? WHERE id = ?;")) {
            ps.setString(1, company.getCompanyName());
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

            try (PreparedStatement ps = connection.prepareStatement("DELETE FROM companies WHERE id = ?;")) {
                ps.setInt(1, id);
                ps.executeUpdate();
            }

            connection.commit();

        } catch (Exception e) {
            try {
                if (connection != null) {
                    connection.rollback();
                }
                ExceptionLogger.initLogger(e.toString());
            } catch (SQLException e1) {
                ExceptionLogger.initLogger(e.toString());
            }
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
    public List<Company> readAllTable() {
        List<Company> companyList = new ArrayList<>();
        try (Connection connection = getConnection(); PreparedStatement ps = connection.prepareStatement("SELECT * FROM companies")) {
            try(ResultSet resultSet = ps.executeQuery()) {
                while (resultSet.next()) {
                    int id = resultSet.getInt("id");
                    String companyName = resultSet.getString("company_name");
                    Company company = new Company(companyName);
                    company.setId(id);
                    companyList.add(company);
                }
            }
        } catch (SQLException e) {
            System.err.println("Error while working with database!");
            ExceptionLogger.initLogger(e.toString());
        }
        return companyList;
    }
}
