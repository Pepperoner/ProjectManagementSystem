package controller.controllerImpl;

import controller.DeveloperController;
import dao.DeveloperDAO;
import instance.*;
import java.util.ArrayList;
import java.util.List;


public class DeveloperControllerImpl implements DeveloperController {

    private DeveloperDAO dao;

    public DeveloperControllerImpl(DeveloperDAO dao) {
        this.dao = dao;
    }

    @Override
    public List<Developer> readAllTable() {
        List<Developer> developerList = dao.readAllTable();
        if (developerList == null || developerList.isEmpty()) {
            System.out.println("List is empty!");
            return new ArrayList<>();
        }
        return dao.readAllTable();
    }

    @Override
    public Developer addData(String name, String surname, int salary, List<Skill> developerSkillList, int projectID) {
        Developer developer = new Developer(name, surname, salary, developerSkillList);
        developer.setProjectID(projectID);
        return dao.save(developer);
    }

    @Override
    public void update(int id, String newDeveloperName, String newDeveloperSurname, int newSalary, List<Skill> developerSkillList, int newProjectID) {
        Developer developer = new Developer(newDeveloperName, newDeveloperSurname, newSalary, developerSkillList);
        developer.setProjectID(newProjectID);
        dao.update(id, developer);
    }

    @Override
    public void update(int id, String newDeveloperName, String newDeveloperSurname, int newSalary, List<Skill> developerSkillList, Project project) {
        Developer developer = new Developer(newDeveloperName,newDeveloperSurname,newSalary,developerSkillList);
        developer.setProject(project);
        dao.update(id,developer);
    }

    public void delete(int id){
        dao.delete(id);
    }
}
