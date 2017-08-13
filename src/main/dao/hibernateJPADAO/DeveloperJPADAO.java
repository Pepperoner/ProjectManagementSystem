package dao.hibernateJPADAO;

import dao.DeveloperDAO;
import instance.*;
import org.apache.log4j.Logger;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.ArrayList;
import java.util.List;

public class DeveloperJPADAO implements DeveloperDAO {

    private static final Logger logger = Logger.getLogger(DeveloperJPADAO.class);

    @Override
    public Developer save(Developer developer) {
        EntityManager em = null;
        try {
            em = JPAUtil.getEntityManager();
            em.getTransaction().begin();
            Project project = em.find(Project.class, developer.getProjectID());
            developer.setProject(project);
            em.persist(developer);
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
        return developer;
    }

    @Override
    public Developer read(int id) {
        EntityManager em = null;
        Developer developer = null;
        try {
            em = JPAUtil.getEntityManager();
            em.getTransaction().begin();
            developer = em.find(Developer.class, id);
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
        return developer;
    }

    @Override
    public void update(int id, Developer developer) {
        EntityManager em = null;
        try {
            em = JPAUtil.getEntityManager();
            em.getTransaction().begin();
            developer.setId(id);
            em.merge(developer);
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
    public void delete(int id) {
        EntityManager em = null;
        try {
            em = JPAUtil.getEntityManager();
            em.getTransaction().begin();
            em.remove(em.find(Developer.class, id));
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
    public List<Developer> readAllTable() {
        EntityManager em = null;
        List<Developer> developerList = new ArrayList<>();
        try {
            em = JPAUtil.getEntityManager();
            em.getTransaction().begin();
            TypedQuery<Developer> namedQuery = em.createNamedQuery("Developer.findAll", Developer.class);
            developerList = namedQuery.getResultList();
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
        return developerList;
    }
}
