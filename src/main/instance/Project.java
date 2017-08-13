package instance;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@NamedQueries({
        @NamedQuery(name = "Project.findAll", query = "select p from Project p"),
        @NamedQuery(name = "Project.findByCompany", query = "select p from Project p where p.company.id = :company"),
        @NamedQuery(name = "Project.findByCustomer", query = "select p from Project p where p.customer.id = :customer")})
@Table(name = "projects")
public class Project implements Serializable{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "project_name")
    private String projectName;
    @Column(name = "cost")
    private int projectCost;
    @ManyToOne
    private Company company;
    @ManyToOne
    private Customer customer;
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "project")
    private List<Developer> developerList;

    public Project() {
    }

    public Project(String projectName, int projectCost, Company company, Customer customer, List<Developer> developerList) {
        this.projectName = projectName;
        this.projectCost = projectCost;
        this.company = company;
        this.customer = customer;
        this.developerList = developerList;
    }

    public List<Developer> getDeveloperList() {
        return developerList;
    }

    public void setDeveloperList(List<Developer> developerList) {
        this.developerList = developerList;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public int getProjectCost() {
        return projectCost;
    }

    public void setProjectCost(int projectCost) {
        this.projectCost = projectCost;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "ProjectName \"" + projectName + "\"" +
                ", ProjectCost " + projectCost +
                ", Company \"" + company + "\"" +
                ", Customer \"" + customer + "\"";
    }
}