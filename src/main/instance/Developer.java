package instance;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@NamedQuery(name = "Developer.findAll", query = "select d from Developer d")
@Table(name = "developers")
public class Developer implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column
    private String name;
    @Column
    private String surname;
    @Column
    private int salary;
    @ManyToMany(fetch = FetchType.EAGER)  // cascade = CascadeType.REFRESH - (соответсвует - select) при удалении будет затронута только промежуточная таблица
    private List<Skill> skillList;
    @Transient
    private int projectID;
    @ManyToOne
    private Project project;

    public Developer() {
    }

    public Developer(String name, String surname, int salary, List<Skill> skillList) {
        this.name = name;
        this.surname = surname;
        this.salary = salary;
        this.skillList = skillList;
    }

    public List<Skill> getSkillList() {
        return skillList;
    }

    public void setSkillList(List<Skill> skillList) {
        this.skillList = skillList;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public int getSalary() {
        return salary;
    }

    public void setSalary(int salary) {
        this.salary = salary;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getProjectID() {
        return projectID;
    }

    public void setProjectID(int projectID) {
        this.projectID = projectID;
    }

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }

    @Override
    public String toString() {
        return "Name " + name +
                ", Surname " + surname +
                ", Salary " + salary +
                ", Skills " + skillList;
    }
}
