package dao.hibernateJPA;

import instance.*;
import org.hibernate.Session;
import org.hibernate.Transaction;

public class HibernateTest {

    public static void main(String[] args) {
        Session session = HibernateUtil.getSession();
        Transaction transaction = session.getTransaction();
        transaction.begin();
        Skill skill = new Skill();
        skill.setSkillName("MVC");
        session.save(skill);
        transaction.commit();
        HibernateUtil.shutDown();
    }
}
