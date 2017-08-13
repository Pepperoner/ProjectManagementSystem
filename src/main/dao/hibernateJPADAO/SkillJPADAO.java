package dao.hibernateJPADAO;

import dao.SkillDAO;
import instance.*;
import org.apache.log4j.Logger;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.ArrayList;
import java.util.List;

public class SkillJPADAO implements SkillDAO {

    private static final Logger logger = Logger.getLogger(SkillJPADAO.class);

    @Override
    public Skill save(Skill skill) {
        EntityManager em = null;
        try{
            em = JPAUtil.getEntityManager();
            em.getTransaction().begin();
            em.persist(skill);
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
        return skill;
    }

    @Override
    public Skill read(int id) {
        EntityManager em = null;
        Skill skill = null;
        try {
            em = JPAUtil.getEntityManager();
            em.getTransaction().begin();
            skill = em.find(Skill.class, id);
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
        return skill;
    }

    @Override
    public void update(int id, Skill skill) {
        EntityManager em = null;
        try {
            em = JPAUtil.getEntityManager();
            em.getTransaction().begin();
            skill.setId(id);
            em.merge(skill);
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
    public void delete(int id) {
        EntityManager em = null;
        try {
            em = JPAUtil.getEntityManager();
            em.getTransaction().begin();
            Skill skill = em.find(Skill.class,id);
            em.remove(skill);
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
    public List<Skill> readAllTable() {
        EntityManager em = null;
        List<Skill> skillList = new ArrayList<>();
        try {
            em = JPAUtil.getEntityManager();
            em.getTransaction().begin();
            TypedQuery<Skill> namedQuery = em.createNamedQuery("Skills.findAll", Skill.class);
            skillList = namedQuery.getResultList();
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
        return skillList;
    }
}
