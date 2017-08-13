package api;

import controller.*;
import instance.*;
import org.apache.log4j.Logger;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.NoSuchElementException;

public class Application implements API {

    private CompanyController companyController;
    private CustomerController customerController;
    private DeveloperController developerController;
    private ProjectController projectController;
    private SkillController skillController;
    private BufferedReader br;

    private final static Logger logger = Logger.getLogger(Application.class);

    public Application(CompanyController companyController, CustomerController customerController, DeveloperController developerController, ProjectController projectController, SkillController skillController, BufferedReader br) {
        this.companyController = companyController;
        this.customerController = customerController;
        this.developerController = developerController;
        this.projectController = projectController;
        this.skillController = skillController;
        this.br = br;
    }

    public void readAllTable(int tableNumber) {
        try {
            switch (tableNumber) {

                case 1:
                    List<Customer> customersList = customerController.readAllTable();
                    System.out.println("List of customers in system:");
                    customersList.forEach(System.out::println);
                    break;

                case 2:
                    List<Company> companyList = companyController.readAllTable();
                    System.out.println("List of companies in system:");
                    companyList.forEach(System.out::println);
                    break;

                case 3:
                    List<Project> projectList = projectController.readAllTable();
                    System.out.println("List of projects in system:");
                    projectList.stream().map(project -> {
                        System.out.println("------------------------------------------------------------------------------\n");
                        System.out.println("\n" + project);
                        System.out.println("------------------------------------------------------------------------------");
                        return project.getDeveloperList();
                    }).forEach(developers -> developers.stream()
                            .sorted(Comparator.comparing(Developer::getName))
                            .forEach(System.out::println));
                    break;

                case 4:
                    List<Developer> developerList = developerController.readAllTable();
                    System.out.println("List of developers in system:");
                    developerList.forEach(System.out::println);
                    break;

                case 5:
                    List<Skill> skillList = skillController.readAllTable();
                    System.out.println("List of skills in system:");
                    skillList.forEach(System.out::println);
                    break;

                case 0:
                    return;

                default:
                    System.out.println("Invalid operation number! Try again! \n To exit, input \"0\"");
            }
        } catch (NoSuchElementException | NumberFormatException n) {
            System.out.println("The command is entered incorrectly! Repeat the selection!" + " \n To exit, input \"0\"");
        }
    }

    public void addOperations(int tableNumber) {
        try {

            switch (tableNumber) {

                case 1:
                    System.out.println("To add a new customer, enter its name:");
                    String customerName = br.readLine();
                    Customer customer = customerController.addData(customerName);
                    System.out.println("New customer " + customer.getCustomerName() + " successfully saved!");
                    break;


                case 2:
                    System.out.println("To add a new company, enter its name:");
                    String companyName = br.readLine();
                    Company company = companyController.addData(companyName);
                    System.out.println("New company " + company.getCompanyName() + " successfully saved!");
                    break;


                case 3:
                    System.out.println("To add a new project, select the customer number of the project:");
                    List<Customer> customerList = customerController.readAllTable();
                    int i = 1;
                    for (Customer customerFromList : customerList) {
                        System.out.println(i++ + ". " + customerFromList.getCustomerName());
                    }
                    int customerIndex = Integer.parseInt(br.readLine());
                    Customer customerForProject = customerList.get(customerIndex - 1);


                    System.out.println("Enter the name of the new project:");
                    String projectName = br.readLine();
                    System.out.println("Enter the cost of the new project:");
                    int projectCost = Integer.parseInt(br.readLine());


                    System.out.println("Select the company that will develop the project:");
                    List<Company> companyList = companyController.readAllTable();
                    i = 1;
                    for (Company companyFromList : companyList) {
                        System.out.println(i++ + ". " + companyFromList.getCompanyName());
                    }
                    int companyIndex = Integer.parseInt(br.readLine());
                    Company companyForProject = companyList.get(companyIndex - 1);


                    System.out.println("Adding developers to a new project:");
                    List<Developer> developerList = new ArrayList<>();
                    int back = -1;
                    while (back != 0) {
                        System.out.println("Enter developer name:");
                        String name = br.readLine();
                        System.out.println("Enter developer surname:");
                        String surname = br.readLine();
                        System.out.println("Enter the developer's salary for the month:");
                        int salary = Integer.parseInt(br.readLine());

                        System.out.println("Select a skill number for the developer from the list:");
                        List<Skill> fullSkillList = skillController.readAllTable();
                        i = 1;
                        int skillIndex = -1;
                        List<Skill> devSkillList = new ArrayList<>();
                        while (skillIndex != 0) {
                            for (Skill skill : fullSkillList) {
                                System.out.println(i++ + ". " + skill.getSkillName());
                            }
                            i = 1;
                            skillIndex = Integer.parseInt(br.readLine());
                            if (skillIndex != 0) {
                                Skill devSkill = fullSkillList.get(skillIndex - 1);
                                devSkillList.add(devSkill);
                            }
                            System.out.println("To finish adding skills, input 0, to further add skills, select the skill:");
                        }
                        developerList.add(new Developer(name, surname, salary, devSkillList));
                        System.out.println("To continue adding developers, press 1, otherwise the project data will be saved and you will be taken to the main menu");
                        String answer = br.readLine();
                        switch (answer) {
                            case "1":
                                continue;
                            default:
                                back = 0;
                        }
                    }
                    Project projectSave = projectController.addData(projectName, projectCost, companyForProject, customerForProject, developerList);
                    System.out.println("New project " + projectSave.getProjectName() + " successfully saved!");
                    break;


                case 4:
                    System.out.println("Select the project number from the list to which you would like to add this developer:");
                    List<Project> projectList = projectController.readAllTable();
                    i = 1;
                    for (Project project : projectList) {
                        System.out.println(i++ + ". " + project.getProjectName());
                    }
                    int projectIndex = Integer.parseInt(br.readLine());
                    int projectID = projectList.get(projectIndex - 1).getId();

                    System.out.println("To add a new developer, enter its name:");
                    String name = br.readLine();
                    System.out.println("Enter developer surname:");
                    String surname = br.readLine();
                    System.out.println("Enter the developer's salary for the month:");
                    int salary = Integer.parseInt(br.readLine());

                    System.out.println("Select a skill number for the developer from the list:");
                    List<Skill> fullSkillList = skillController.readAllTable();
                    i = 1;
                    int skillIndex = -1;
                    List<Skill> devSkillList = new ArrayList<>();
                    while (skillIndex != 0) {
                        for (Skill skill : fullSkillList) {
                            System.out.println(i++ + ". " + skill.getSkillName());
                        }
                        i = 1;
                        skillIndex = Integer.parseInt(br.readLine());
                        if (skillIndex != 0) {
                            Skill devSkill = fullSkillList.get(skillIndex - 1);
                            devSkillList.add(devSkill);
                        }
                        System.out.println("To save and exit, input 0, to further add skills, select the skill:");
                    }

                    Developer developer = developerController.addData(name, surname, salary, devSkillList, projectID);
                    System.out.println("New developer " + developer.getName() + " " + developer.getSurname() + " successfully saved!");
                    break;


                case 5:
                    System.out.println("To add a new skill, enter its name:");
                    String skillName = br.readLine();
                    Skill skill = skillController.addData(skillName);
                    System.out.println("New skill " + skill.getSkillName() + " successfully saved!");
                    break;


                case 0:
                    break;

                default:
                    System.out.println("Invalid operation number! Try again! \n To exit, input \"0\"");
            }
        } catch (IOException e) {
            System.err.println("Data input / output error!");
            logger.error(e);
        } catch (NoSuchElementException | NumberFormatException n) {
            System.out.println("The command is entered incorrectly! Repeat the selection!" + " \n To exit, input \"0\"");
        }
    }

    @Override
    public void updateOperations(int tableNumber) {
        try {
            switch (tableNumber) {

                case 1:
                    System.out.println("Select a customer number from the list to change its data");
                    List<Customer> customerList = customerController.readAllTable();
                    int i = 1;
                    for (Customer customer : customerList) {
                        System.out.println(i++ + ". " + customer);
                    }
                    int customerIndex = Integer.parseInt(br.readLine());
                    Customer oldCustomer = customerList.get(customerIndex - 1);
                    System.out.println("Company name of the customer " + oldCustomer.getCustomerName() + " change to:");
                    String newCustomerName = br.readLine();
                    customerController.update(oldCustomer.getId(), newCustomerName);
                    System.out.println("Changes saved!");
                    break;


                case 2:
                    System.out.println("Select a company number from the list to change its data");
                    List<Company> companyList = companyController.readAllTable();
                    i = 1;
                    for (Company company : companyList) {
                        System.out.println(i++ + ". " + company);
                    }
                    int companyIndex = Integer.parseInt(br.readLine());
                    Company oldCompany = companyList.get(companyIndex - 1);
                    System.out.println("Company name " + oldCompany.getCompanyName() + " change to:");
                    String newCompanyName = br.readLine();
                    companyController.update(oldCompany.getId(), newCompanyName);
                    System.out.println("Changes saved!");
                    break;


                case 3:
                    System.out.println("Select a project number from the list to change its data");
                    List<Project> newProjectList = projectController.readAllTable();
                    i = 1;
                    for (Project project : newProjectList) {
                        System.out.println(i++ + ". " + project.getProjectName());
                    }
                    int forProjectIndex = Integer.parseInt(br.readLine());
                    Project oldProject = newProjectList.get(forProjectIndex - 1);
                    System.out.println("Project name " + oldProject.getProjectName() + " change to:");
                    String newProjectName = br.readLine();
                    System.out.println("Project cost " + oldProject.getProjectCost() + " change to:");
                    int newProjectCost = Integer.parseInt(br.readLine());
                    System.out.println("Now the customer of the project " + oldProject.getCustomer().getCustomerName() + ". To change, select the customer from the list in the system:");
                    List<Customer> customerListForShow = customerController.readAllTable();
                    i = 1;
                    for (Customer customer : customerListForShow) {
                        System.out.println(i++ + ". " + customer.getCustomerName());
                    }
                    int newCustomerIndex = Integer.parseInt(br.readLine());
                    Customer newCustomerForProject = customerListForShow.get(newCustomerIndex - 1);
                    System.out.println("Company engaged in the development of the project " + oldProject.getCompany().getCompanyName() + ". To change, select a company from the list in the system:");
                    List<Company> companyListForShow = companyController.readAllTable();
                    i = 1;
                    for (Company company : companyListForShow) {
                        System.out.println(i++ + ". " + company.getCompanyName());
                    }
                    int companyForProject = Integer.parseInt(br.readLine());
                    Company newCompanyForProjects = companyListForShow.get(companyForProject - 1);
                    projectController.update(oldProject.getId(), newProjectName, newProjectCost, newCustomerForProject, newCompanyForProjects, oldProject.getDeveloperList());
                    System.out.println("Changes saved!");
                    break;


                case 4:
                    System.out.println("Select a developer number from the list to change its data");
                    List<Developer> developerList = developerController.readAllTable();
                    i = 1;
                    for (Developer developer : developerList) {
                        System.out.println(i++ + ". " + developer);
                    }
                    int developerIndex = Integer.parseInt(br.readLine());
                    Developer developer = developerList.get(developerIndex - 1);
                    System.out.println("Developer name " + developer.getName() + " change to:");
                    String newDevName = br.readLine();
                    System.out.println("Developer surname " + developer.getSurname() + " change to:");
                    String newDevSurname = br.readLine();
                    System.out.println("Salary " + developer.getSalary() + " change to:");
                    int newSalary = Integer.parseInt(br.readLine());
                    Project project;
                    if (developer.getProjectID() != 0) {
                        project = projectController.readByID(developer.getProjectID());
                    } else project = projectController.readByID(developer.getProject().getId());
                    System.out.println("The developer is working on a project " + project.getProjectName());
                    System.out.println("If you want to change the project for the developer, input 1, when you press any other command, all the specified data for the developer will be changed!");
                    String answer = br.readLine();
                    if ("1".equals(answer)) {
                        List<Project> projectList = projectController.readAllTable();
                        i = 1;
                        for (Project projectFromAll : projectList) {
                            System.out.println(i++ + ". " + projectFromAll.getProjectName());
                        }
                        int projectIndex = Integer.parseInt(br.readLine());
                        Project projectFromDB = projectList.get(projectIndex - 1);
                        int newProjectID = projectList.get(projectIndex - 1).getId();
                        if (developer.getProjectID() != 0) {
                            developerController.update(developer.getId(), newDevName, newDevSurname, newSalary, developer.getSkillList(), newProjectID);
                        } else
                            developerController.update(developer.getId(), newDevName, newDevSurname, newSalary, developer.getSkillList(), projectFromDB);
                    } else if (developer.getProjectID() != 0) {
                        developerController.update(developer.getId(), newDevName, newDevSurname, newSalary, developer.getSkillList(), developer.getProjectID());
                    } else
                        developerController.update(developer.getId(), newDevName, newDevSurname, newSalary, developer.getSkillList(), project);
                    System.out.println("Changes saved!");
                    break;


                case 5:
                    System.out.println("Select a skill number from the list to change its data");
                    List<Skill> skillList = skillController.readAllTable();
                    i = 1;
                    for (Skill skill : skillList) {
                        System.out.println(i++ + ". " + skill);
                    }
                    int skillIndex = Integer.parseInt(br.readLine());
                    Skill oldSkill = skillList.get(skillIndex - 1);
                    System.out.println("Skill name " + oldSkill.getSkillName() + " change to:");
                    String newSkillName = br.readLine();
                    skillController.update(oldSkill.getId(), newSkillName);
                    System.out.println("Changes saved!");
                    break;

                case 0:
                    return;

                default:
                    System.out.println("Invalid operation number! Try again! \n To exit, input \"0\"");
            }
        } catch (IOException e) {
            System.err.println("Data input / output error!");
            logger.error(e);
        } catch (NoSuchElementException | NumberFormatException n) {
            System.out.println("The command is entered incorrectly! Repeat the selection!" + " \n To exit, input \"0\"");
        }
    }

    @Override
    public void deleteOperations(int tableName) {
        try {
            switch (tableName) {

                case 1:
                    System.out.println("Select a customer number from the list to delete an entry for it");
                    List<Customer> customerList = customerController.readAllTable();
                    int i = 1;
                    for (Customer customer : customerList) {
                        System.out.println(i++ + ". " + customer);
                    }
                    int customerIndex = Integer.parseInt(br.readLine());
                    Customer customer = customerList.get(customerIndex - 1);
                    List<Project> customerProjectList = projectController.readProjectByCustomerID(customer.getId());
                    System.out.println("Are you sure that you want to delete the customer \"" + customer + "\"?");
                    System.out.println("Also, projects and data related to them will be deleted along with it:");
                    customerProjectList.stream()
                            .map(Project::getProjectName)
                            .forEach((x) -> System.out.println("Project " + x));
                    System.out.println("\n To confirm the selection, input 1 ,or input 0, to cancel and return to the previous menu:");
                    System.out.println("Make your choice:");
                    while (true) {
                        int choice = Integer.parseInt(br.readLine());
                        if (choice == 1) {
                            customerController.delete(customer.getId(), customerProjectList);
                            System.out.println("The customer and all data associated with it are deleted!");
                            break;
                        } else if (choice == 0) {
                            break;
                        }
                        System.out.println("The command was entered incorrectly, please try again:");
                    }
                    break;

                case 2:
                    System.out.println("Select a company number from the list to delete the entry for it");
                    List<Company> companyList = companyController.readAllTable();
                    i = 1;
                    for (Company company : companyList) {
                        System.out.println(i++ + ". " + company);
                    }
                    int companyIndex = Integer.parseInt(br.readLine());
                    Company company = companyList.get(companyIndex - 1);
                    List<Project> companyProjectList = projectController.readProjectByCompanyID(company.getId());
                    System.out.println("Are you sure you want to delete the company \"" + company + "\"?");
                    System.out.println("Also, projects and data related to them will be deleted along with it:");
                    companyProjectList.stream()
                            .map(Project::getProjectName)
                            .forEach((x) -> System.out.println("Project " + x));
                    System.out.println("\n To confirm the selection, input 1 ,or input 0, to cancel and return to the previous menu:");
                    System.out.println("Make your choice:");
                    while (true) {
                        int choice = Integer.parseInt(br.readLine());
                        if (choice == 1) {
                            companyController.delete(company.getId(), companyProjectList);
                            System.out.println("The company and all data associated with it are deleted!");
                            break;
                        } else if (choice == 0) {
                            break;
                        }
                        System.out.println("The command was entered incorrectly, please try again:");
                    }
                    break;

                case 3:
                    System.out.println("Select a project number from the list to delete an entry for it");
                    List<Project> projectList = projectController.readAllTable();
                    i = 1;
                    for (Project project : projectList) {
                        System.out.println(i++ + ". " + project.getProjectName());
                    }
                    int projectIndex = Integer.parseInt(br.readLine());
                    Project project = projectList.get(projectIndex - 1);
                    System.out.println("Are you sure that you want to delete the project \"" + project.getProjectName() + "\"?");
                    System.out.println("Also, together with it, the developers who are assigned to the project will be deleted:");
                    project.getDeveloperList()
                            .forEach(developer -> System.out.println("Name " + developer.getName() + ", Surname " + developer.getSurname()));
                    System.out.println("\n To confirm the selection, input 1 ,or input 0, to cancel and return to the previous menu:");
                    System.out.println("Make your choice:");
                    while (true) {
                        int choice = Integer.parseInt(br.readLine());
                        if (choice == 1) {
                            projectController.delete(project);
                            System.out.println("The project and all data associated with it are deleted!");
                            break;
                        } else if (choice == 0) {
                            break;
                        }
                        System.out.println("The command was entered incorrectly, please try again:");
                    }
                    break;

                case 4:
                    System.out.println("Select a developer number from the list to delete the entry for it");
                    List<Developer> developerList = developerController.readAllTable();
                    i = 1;
                    for (Developer developer : developerList) {
                        System.out.println(i++ + ". " + developer.getName() + " " + developer.getSurname());
                    }
                    int developerIndex = Integer.parseInt(br.readLine());
                    Developer developer = developerList.get(developerIndex - 1);
                    System.out.println("Are you sure you want to delete the developer \"" + developer.getName() + " " + developer.getSurname() + "\"?" + developer.getId());
                    System.out.println("\n To confirm the selection, input 1 ,or input 0, to cancel and return to the previous menu:");
                    System.out.println("Make your choice:");
                    while (true) {
                        int choice = Integer.parseInt(br.readLine());
                        if (choice == 1) {
                            developerController.delete(developer.getId());
                            System.out.println("Developer is deleted!");
                            break;
                        } else if (choice == 0) {
                            break;
                        }
                        System.out.println("The command was entered incorrectly, please try again:");
                    }
                    break;

                case 5:
                    System.out.println("Select a skill number from the list to delete the entry for it");
                    List<Skill> skillList = skillController.readAllTable();
                    i = 1;
                    for (Skill skill : skillList) {
                        System.out.println(i++ + ". " + skill);
                    }
                    int skillIndex = Integer.parseInt(br.readLine());
                    Skill skill = skillList.get(skillIndex - 1);
                    System.out.println("Are you sure that you want to delete skill \"" + skill + "\"?");
                    System.out.println("\n To confirm the selection, input 1 ,or input 0, to cancel and return to the previous menu:");
                    System.out.println("Make your choice:");
                    while (true) {
                        int choice = Integer.parseInt(br.readLine());
                        if (choice == 1) {
                            skillController.delete(skill.getId());
                            System.out.println("Skill is deleted!");
                            break;
                        } else if (choice == 0) {
                            break;
                        }
                        System.out.println("The command was entered incorrectly, please try again:");
                    }
                    break;

                case 0:
                    return;

                default:
                    System.out.println("Invalid operation number! Try again! \n To exit, input \"0\"");
            }
        } catch (IOException e) {
            System.err.println("Data input / output error!");
            logger.error(e);
        } catch (NoSuchElementException | NumberFormatException n) {
            System.out.println("The command is entered incorrectly! Repeat the selection!" + " \n To exit, input \"0\"");
        }
    }
}
