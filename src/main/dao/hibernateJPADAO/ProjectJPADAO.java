package dao.hibernateJPADAO;

import dao.ProjectDAO;
import instance.*;
import org.apache.log4j.Logger;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.ArrayList;
import java.util.List;

public class ProjectJPADAO implements ProjectDAO {

    private static final Logger logger = Logger.getLogger(ProjectJPADAO.class);

    @Override
    public Project save(Project project) {
        EntityManager em = null;
        try {
            em = JPAUtil.getEntityManager();
            em.getTransaction().begin();
            for (Developer developer : project.getDeveloperList()) {
                developer.setProject(project);
                em.persist(developer);
            }
            em.persist(project);
            em.getTransaction().commit();
        } catch (RuntimeException e) {
            if (em != null) {
                em.getTransaction().rollback();
            }
            logger.error(e);
        } finally {
            if (em != null) {
                em.close();
            }
        }
        return project;
    }

    @Override
    public Project read(int id) {
        EntityManager em = null;
        Project project = null;
        try {
            em = JPAUtil.getEntityManager();
            em.getTransaction().begin();
            project = em.find(Project.class, id);
            em.getTransaction().commit();
        } catch (RuntimeException e) {
            if (em != null) {
                em.getTransaction().rollback();
            }
            logger.error(e);
        } finally {
            if (em != null) {
                em.close();
            }
        }
        return project;
    }

    @Override
    public void update(int id, Project project) {
        EntityManager em = null;
        try {
            em = JPAUtil.getEntityManager();
            em.getTransaction().begin();
            project.setId(id);
            em.merge(project);
            em.getTransaction().commit();
        } catch (RuntimeException e) {
            if (em != null) {
                em.getTransaction().rollback();
            }
            logger.error(e);
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    @Override
    public void delete(Project project) {
        EntityManager em = null;
        try {
            em = JPAUtil.getEntityManager();
            em.getTransaction().begin();
            em.remove(em.contains(project) ? project : em.merge(project));
            em.getTransaction().commit();
        }catch (RuntimeException e){
            if (em != null) {
                em.getTransaction().rollback();
            }
            logger.error(e);
        }finally {
            if (em != null) {
                em.close();
            }
        }
    }

    @Override
    public List<Project> readAllTable() {
        EntityManager em = null;
        List<Project> projectList = new ArrayList<>();
        try {
            em = JPAUtil.getEntityManager();
            em.getTransaction().begin();
            TypedQuery<Project> namedQuery = em.createNamedQuery("Project.findAll", Project.class);
            projectList = namedQuery.getResultList();


            em.getTransaction().commit();
        } catch (RuntimeException e) {
            if (em != null) {
                em.getTransaction().rollback();
            }
            logger.error(e);
        } finally {
            if (em != null) {
                em.close();
            }
        }
        return projectList;
    }

    @Override
    public List<Project> readProjectByCompanyID(int companyID) {
        EntityManager em = null;
        List<Project> projectList = new ArrayList<>();
        try {
            em = JPAUtil.getEntityManager();
            em.getTransaction().begin();

            TypedQuery<Project> namedQuery = em.createNamedQuery("Project.findByCompany", Project.class);
            namedQuery.setParameter("company",companyID);
            projectList = namedQuery.getResultList();

            em.getTransaction().commit();
        } catch (RuntimeException e) {
            if (em != null) {
                em.getTransaction().rollback();
            }
            logger.error(e);
        } finally {
            if (em != null) {
                em.close();
            }
        }
        return projectList;
    }

    @Override
    public List<Project> readProjectByCustomerID(int customerID) {
        EntityManager em = null;
        List<Project> projectList = new ArrayList<>();
        try {
            em = JPAUtil.getEntityManager();
            em.getTransaction().begin();

            TypedQuery<Project> namedQuery = em.createNamedQuery("Project.findByCustomer", Project.class);
            namedQuery.setParameter("customer",customerID);
            projectList = namedQuery.getResultList();

            em.getTransaction().commit();
        } catch (RuntimeException e) {
            if (em != null) {
                em.getTransaction().rollback();
            }
            logger.error(e);
        } finally {
            if (em != null) {
                em.close();
            }
        }
        return projectList;
    }
}
