package dao.DAOImpl;

import dao.ProjectDAO;
import dao.pooledJdbc.PooledJDBCUserDAO;
import dao.logger.ExceptionLogger;
import instance.*;
import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProjectDAOImpl extends PooledJDBCUserDAO implements ProjectDAO {

    public ProjectDAOImpl(DataSource dataSource) {
        super(dataSource);
    }

    @Override
    public Project save(Project project) {
        Connection connection = null;
        try {
            connection = getConnection();
            connection.setAutoCommit(false);

            try (PreparedStatement ps = connection.prepareStatement("INSERT INTO projects(project_name, cost, customer_id, company_id) VALUES (?,?,?,?);")) {
                ps.setString(1, project.getProjectName());
                ps.setInt(2, project.getProjectCost());
                ps.setInt(3, project.getCustomer().getId());
                ps.setInt(4, project.getCompany().getId());
                ps.execute();
            }

            for (Developer developer : project.getDeveloperList()) {
                try (PreparedStatement ps = connection.prepareStatement("INSERT INTO developers(name, surname, salary, Projects_id) VALUES(?,?,?,(SELECT projects.id FROM projects WHERE project_name = ? AND cost = ? AND customer_id = ? AND company_id = ?));")) {
                    ps.setString(1, developer.getName());
                    ps.setString(2, developer.getSurname());
                    ps.setInt(3, developer.getSalary());
                    ps.setString(4, project.getProjectName());
                    ps.setInt(5, project.getProjectCost());
                    ps.setInt(6, project.getCustomer().getId());
                    ps.setInt(7, project.getCompany().getId());
                    ps.execute();
                }

                int developerID = 0;
                try (PreparedStatement ps = connection.prepareStatement("SELECT last_insert_id() AS developerID FROM developers LIMIT 1")) {
                    ResultSet resultSet = ps.executeQuery();
                    while (resultSet.next()) {
                        developerID = resultSet.getInt("developerID");
                    }
                }

                for (Skill skill : developer.getSkillList())
                    try (PreparedStatement ps = connection.prepareStatement("INSERT INTO developers_has_skills(Developers_id, Skills_id) VALUES (?,?)")) {
                        ps.setInt(1, developerID);
                        ps.setInt(2, skill.getId());
                        ps.execute();
                    }
            }

            connection.commit();

        } catch (SQLException e) {
            try {
                if (connection != null) {
                    connection.rollback();
                }
            } catch (SQLException e1) {
                ExceptionLogger.initLogger(e1.toString());
            }
            System.err.println("Error while working with database!");
            ExceptionLogger.initLogger(e.toString());
        }finally {
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
        return project;
    }

    @Override
    public Project read(int id) {
        Connection connection = null;
        Project project = null;

        try {
            connection = getConnection();
            connection.setAutoCommit(false);

            try (PreparedStatement ps = connection.prepareStatement("SELECT * FROM projects WHERE id = ?")) {
                ps.setInt(1, id);
                try (ResultSet resultSet = ps.executeQuery()) {
                    while (resultSet.next()) {
                        int projectID = resultSet.getInt("id");
                        String projectName = resultSet.getString("project_name");
                        int projectCost = resultSet.getInt("cost");
                        int companyID = resultSet.getInt("company_id");
                        int customerID = resultSet.getInt("customer_id");


                        Company company = null;
                        try (PreparedStatement ps1 = connection.prepareStatement("SELECT company_name FROM companies WHERE id = ?;")) {
                            ps1.setInt(1, companyID);
                            try (ResultSet rs = ps1.executeQuery()) {
                                while (rs.next()) {
                                    String companyName = rs.getString("company_name");
                                    company = new Company(companyName);
                                    company.setId(companyID);
                                }
                            }
                        }

                        Customer customer = null;
                        try (PreparedStatement ps1 = connection.prepareStatement("SELECT customer_name FROM customers WHERE id = ?;")) {
                            ps1.setInt(1, customerID);
                            try (ResultSet rs = ps1.executeQuery()) {
                                while (rs.next()) {
                                    String customerName = rs.getString("customer_name");
                                    customer = new Customer(customerName);
                                    customer.setId(customerID);
                                }
                            }
                        }

                        List<Developer> developerList = new ArrayList<>();
                        try (PreparedStatement ps3 = connection.prepareStatement("SELECT * FROM developers WHERE Projects_id = ?;")) {
                            ps3.setInt(1, projectID);
                            try (ResultSet resultSet3 = ps3.executeQuery()) {
                                while (resultSet3.next()) {
                                    int developID = resultSet3.getInt("id");
                                    String developName = resultSet3.getString("name");
                                    String developSurname = resultSet3.getString("surname");
                                    int salary = resultSet3.getInt("salary");
                                    int projectIDFromDevelopers = resultSet3.getInt("Projects_id");
                                    Developer developer = new Developer(developName, developSurname, salary, new ArrayList<Skill>());
                                    developer.setId(developID);
                                    developer.setProjectID(projectIDFromDevelopers);
                                    developerList.add(developer);
                                }
                            }
                        }

                        try (PreparedStatement ps4 = connection.prepareStatement("SELECT * FROM skills WHERE skills.id IN (SELECT Skills_id FROM developers_has_skills WHERE Developers_id = ?);")) {
                            for (Developer developer : developerList) {
                                ps4.setInt(1, developer.getId());
                                try (ResultSet resultSet4 = ps4.executeQuery()) {
                                    while (resultSet4.next()) {
                                        int skillID = resultSet4.getInt("id");
                                        String skillName = resultSet4.getString("skills");
                                        Skill skill = new Skill(skillName);
                                        skill.setId(skillID);
                                        developer.getSkillList().add(skill);
                                    }
                                }
                            }
                        }

                        project = new Project(projectName, projectCost, company, customer, developerList);
                        project.setId(projectID);

                    }
                }
            }

            connection.commit();

        } catch (Exception e) {
            try {
                connection.rollback();
            } catch (SQLException e1) {
                ExceptionLogger.initLogger(e1.toString());
            }
            System.err.println("Error while working with database!");
            ExceptionLogger.initLogger(e.toString());
        }finally {
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
        return project;
    }

    @Override
    public void update(int id, Project project) {
        try (Connection connection = getConnection(); PreparedStatement ps = connection.prepareStatement("UPDATE projects SET project_name = ?, cost = ?, customer_id = ?, company_id = ? WHERE id = ?;")) {
            ps.setString(1, project.getProjectName());
            ps.setInt(2, project.getProjectCost());
            ps.setInt(3,project.getCustomer().getId());
            ps.setInt(4,project.getCompany().getId());
            ps.setInt(5, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Error while working with database!");
            ExceptionLogger.initLogger(e.toString());
        }
    }

    @Override
    public void delete(Project project) {
        Connection connection = null;
        try  {
            connection = getConnection();
            connection.setAutoCommit(false);

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

            try(PreparedStatement ps = connection.prepareStatement("DELETE FROM projects WHERE id = ?;")) {
                ps.setInt(1, project.getId());
                ps.executeUpdate();
            }

            connection.commit();

        } catch (SQLException e) {
            try {
                if (connection != null) {
                    connection.rollback();
                }
            } catch (SQLException e1) {
                ExceptionLogger.initLogger(e1.toString());
            }
            System.err.println("Error while working with database!");
            ExceptionLogger.initLogger(e.toString());
        }finally {
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
    public List<Project> readAllTable() {
        Connection connection = null;
        List<Project> projectList = new ArrayList<>();

        try {
            connection = getConnection();
            connection.setAutoCommit(false);

            Company company;
            Customer customer;
            try (PreparedStatement preparedStatement = connection.prepareStatement("SELECT projects.id, project_name, cost, customer_id, customers.customer_name AS customer, company_id, companies.company_name AS company FROM projects LEFT JOIN customers ON projects.customer_id = customers.id LEFT JOIN companies ON projects.company_id = companies.id;")) {
                try (ResultSet rs = preparedStatement.executeQuery()) {
                    while (rs.next()) {
                        int projectID = rs.getInt("id");
                        String projectName = rs.getString("project_name");
                        int projectCost = rs.getInt("cost");
                        int customerID = rs.getInt("customer_id");
                        String customerName = rs.getString("customer");
                        int companyID = rs.getInt("company_id");
                        String companyName = rs.getString("company");

                        customer = new Customer(customerName);
                        customer.setId(customerID);
                        company = new Company(companyName);
                        company.setId(companyID);

                        List<Developer> developerList = new ArrayList<>();
                        try (PreparedStatement ps3 = connection.prepareStatement("SELECT * FROM developers WHERE Projects_id = ?;")) {
                            ps3.setInt(1, projectID);
                            try (ResultSet resultSet3 = ps3.executeQuery()) {
                                while (resultSet3.next()) {
                                    int developID = resultSet3.getInt("id");
                                    String developName = resultSet3.getString("name");
                                    String developSurname = resultSet3.getString("surname");
                                    int salary = resultSet3.getInt("salary");
                                    int projectIDFromDevelopers = resultSet3.getInt("Projects_id");
                                    Developer developer = new Developer(developName, developSurname, salary, new ArrayList<Skill>());
                                    developer.setId(developID);
                                    developer.setProjectID(projectIDFromDevelopers);
                                    developerList.add(developer);
                                }
                            }
                        }

                        try (PreparedStatement ps4 = connection.prepareStatement("SELECT * FROM skills WHERE skills.id IN (SELECT Skills_id FROM developers_has_skills WHERE Developers_id = ?);")) {
                            for (Developer developer : developerList) {
                                ps4.setInt(1, developer.getId());
                                try (ResultSet resultSet4 = ps4.executeQuery()) {
                                    while (resultSet4.next()) {
                                        int skillID = resultSet4.getInt("id");
                                        String skillName = resultSet4.getString("skills");
                                        Skill skill = new Skill(skillName);
                                        skill.setId(skillID);
                                        developer.getSkillList().add(skill);
                                    }
                                }
                            }
                        }
                        Project project = new Project(projectName, projectCost, company, customer, developerList);
                        project.setId(projectID);
                        projectList.add(project);
                    }
                }
            }

            connection.commit();

        } catch (SQLException e) {
            try {
                if (connection != null) {
                    connection.rollback();
                }
            } catch (SQLException e1) {
                ExceptionLogger.initLogger(e1.toString());
            }
            System.err.println("Error while working with database!");
            ExceptionLogger.initLogger(e.toString());
        }finally {
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
        return projectList;
    }

    public List<Project> readProjectByCompanyID (int companyID){

        List<Project> projectList = new ArrayList<>();

        try(Connection connection = getConnection()){
            try(PreparedStatement ps = connection.prepareStatement("SELECT projects.id FROM projects WHERE company_id = ?;")){
                ps.setInt(1,companyID);
                try(ResultSet resultSet = ps.executeQuery()) {
                    while (resultSet.next()) {
                        int projectID = resultSet.getInt(1);
                        Project customerProject = read(projectID);
                        projectList.add(customerProject);
                    }
                }
            }
        } catch (SQLException e) {
            System.err.println("Error while working with database!");
            ExceptionLogger.initLogger(e.toString());
        }
        return projectList;
    }

    public List<Project> readProjectByCustomerID(int customerID){

        List<Project> projectList = new ArrayList<>();

        try(Connection connection = getConnection()){
            try(PreparedStatement ps = connection.prepareStatement("SELECT projects.id FROM projects WHERE customer_id = ?;")){
                ps.setInt(1,customerID);
                try(ResultSet resultSet = ps.executeQuery()) {
                    while (resultSet.next()) {
                        int projectID = resultSet.getInt(1);
                        Project customerProject = read(projectID);
                        projectList.add(customerProject);
                    }
                }
            }
        } catch (SQLException e) {
            System.err.println("Error while working with database!");
            ExceptionLogger.initLogger(e.toString());
        }
        return projectList;
    }
}
