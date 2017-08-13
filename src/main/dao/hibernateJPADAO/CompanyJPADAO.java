package dao.hibernateJPADAO;

import dao.CompanyDAO;
import instance.*;
import org.apache.log4j.Logger;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.List;

public class CompanyJPADAO implements CompanyDAO  {

    private static final Logger logger = Logger.getLogger(CompanyJPADAO.class);

    @Override
    public Company save(Company company) {
        EntityManager entityManager = null;
        try {
            entityManager = JPAUtil.getEntityManager();
            entityManager.getTransaction().begin();
            entityManager.persist(company);
            entityManager.getTransaction().commit();
        } catch (RuntimeException e) {
            if (entityManager != null) {
                entityManager.getTransaction().rollback();
            }
            logger.error(e);
        } finally {
            if (entityManager != null) {
                entityManager.close();
            }
        }
        return company;
    }

    @Override
    public Company read(int id) {
        EntityManager em = null;
        Company company = null;
        try {
            em = JPAUtil.getEntityManager();
            em.getTransaction().begin();
            company = em.find(Company.class, id);
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
        return company;
    }

    @Override
    public void update(int id, Company company) {
        EntityManager em = null;
        try {
            em = JPAUtil.getEntityManager();
            em.getTransaction().begin();
            company.setId(id);
            em.merge(company);
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
    public void delete(int id, List<Project> projectList) {
        EntityManager em = null;
        try {
            em = JPAUtil.getEntityManager();
            em.getTransaction().begin();
            Company company = em.find(Company.class, id);
            em.remove(company);
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
    public List<Company> readAllTable() {
        EntityManager em = null;
        List<Company> companyList = null;
        try {
            em = JPAUtil.getEntityManager();
            em.getTransaction().begin();

            TypedQuery<Company> namedQuery = em.createNamedQuery("Company.findAll", Company.class);
            companyList = namedQuery.getResultList();
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
        return companyList;
    }
}
