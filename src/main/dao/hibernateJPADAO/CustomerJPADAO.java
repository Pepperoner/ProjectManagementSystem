package dao.hibernateJPADAO;

import dao.CustomerDAO;
import instance.*;
import org.apache.log4j.Logger;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.ArrayList;
import java.util.List;

public class CustomerJPADAO implements CustomerDAO {

    private static final Logger logger = Logger.getLogger(CustomerJPADAO.class);

    @Override
    public Customer save(Customer customer) {
        EntityManager em = null;
        try {
            em = JPAUtil.getEntityManager();
            em.getTransaction().begin();
            em.persist(customer);
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
        return customer;
    }

    @Override
    public Customer read(int id) {
        EntityManager em = null;
        Customer customer = null;
        try {
            em = JPAUtil.getEntityManager();
            em.getTransaction().begin();
            customer = em.find(Customer.class, id);
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
        return customer;
    }

    @Override
    public void update(int id, Customer customer) {
        EntityManager em = null;
        try {
            em = JPAUtil.getEntityManager();
            em.getTransaction().begin();
            customer.setId(id);
            em.merge(customer);
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
    public void delete(int id, List<Project> projectList) {
        EntityManager em = null;
        try {
            em = JPAUtil.getEntityManager();
            em.getTransaction().begin();
            Customer customer = em.find(Customer.class, id);
            em.remove(customer);
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
    public List<Customer> readAllTable() {
        EntityManager em = null;
        List<Customer> customerList = new ArrayList<>();
        try {
            em = JPAUtil.getEntityManager();
            em.getTransaction().begin();
            TypedQuery<Customer> namedQuery = em.createNamedQuery("Customer.findAll", Customer.class);
            customerList = namedQuery.getResultList();
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
        return customerList;
    }
}
