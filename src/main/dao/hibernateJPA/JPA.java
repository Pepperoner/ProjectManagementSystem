package dao.hibernateJPA;

import instance.*;
import org.hibernate.Session;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class JPA {

    public static void main(String[] args) {
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("hibernate");
        EntityManager entityManager = entityManagerFactory.createEntityManager();

        Session session = (Session) entityManager;
        session.getTransaction().begin();
        Skill skill = new Skill();
        skill.setSkillName("Web");
        session.save(skill);

        session.getTransaction().commit();
        session.close();
        entityManagerFactory.close();
    }
}
