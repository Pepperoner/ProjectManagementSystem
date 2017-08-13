import api.*;
import consoleView.ConsoleHelper;
import controller.*;
import controller.controllerImpl.*;
import dao.*;
import dao.hibernateJPADAO.*;
import dao.logger.ExceptionLogger;
import dao.DAOImpl.*;
import org.apache.commons.dbcp.BasicDataSource;
import org.apache.log4j.Logger;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class ProgramStart {

    private static final Logger logger = Logger.getLogger(ProgramStart.class);

    public static void main(String[] args) {

        // Для запуска проекта на JDBC с использованием ConnectionPool!
        /*final String URL = "jdbc:mysql://localhost:3306/developers_db?useUnicode=true&useSSL=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";
        final String USER = "root";
        final String PASSWORD = "1111";
        BasicDataSource basicDataSource = new BasicDataSource();
        basicDataSource.setUrl(URL);
        basicDataSource.setUsername(USER);
        basicDataSource.setPassword(PASSWORD);
        basicDataSource.setInitialSize(5);
        CompanyDAO companyDAO = new CompanyDAOImpl(basicDataSource);
        CustomerDAO customerDAO = new CustomerDAOImpl(basicDataSource);
        DeveloperDAO developerDAO = new DeveloperDAOImpl(basicDataSource);
        ProjectDAO projectDAO = new ProjectDAOImpl(basicDataSource);
        SkillDAO skillDAO = new SkillDAOImpl(basicDataSource);*/



        // Для запуска проекта на основе JPA c использованием Hibernate

        CompanyDAO companyDAO = new CompanyJPADAO();
        CustomerDAO customerDAO = new CustomerJPADAO();
        DeveloperDAO developerDAO = new DeveloperJPADAO();
        ProjectDAO projectDAO = new ProjectJPADAO();
        SkillDAO skillDAO = new SkillJPADAO();

        CompanyController companyController = new CompanyControllerImpl(companyDAO);
        CustomerController customerController = new CustomerControllerImpl(customerDAO);
        DeveloperController developerController = new DeveloperControllerImpl(developerDAO);
        ProjectController projectController = new ProjectControllerImpl(projectDAO);
        SkillController skillController = new SkillControllerImpl(skillDAO);

        try(BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in))) {

            API api = new Application(companyController, customerController, developerController, projectController, skillController, bufferedReader);

            ConsoleHelper consoleHelper = new ConsoleHelper(api, bufferedReader);

            consoleHelper.chooseMainOperations();

        } catch (IOException e) {
            System.err.println("Data input / output error!");
            logger.error(e);
        }finally {
            JPAUtil.shutDown();
        }
    }
}


