package controller;

import instance.*;
import java.util.List;

public interface DeveloperController {

    List<Developer> readAllTable();

    Developer addData(String name, String surname, int salary, List<Skill> developerSkillList, int projectID);

    void update(int id, String newDeveloperName, String newDeveloperSurname, int newSalary, List<Skill> developerSkillList, int newProjectID);

    void update(int id, String newDeveloperName, String newDeveloperSurname, int newSalary, List<Skill> developerSkillList, Project project);

    void delete(int id);
}
