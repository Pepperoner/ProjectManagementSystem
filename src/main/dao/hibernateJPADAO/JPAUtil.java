package dao.hibernateJPADAO;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class JPAUtil {

    private static EntityManagerFactory entityManagerFactory;

    static {
        try {
            entityManagerFactory = Persistence.createEntityManagerFactory("hibernate");
        }catch (Throwable ex){
            throw new ExceptionInInitializerError(ex);
        }
    }

    public static EntityManager getEntityManager(){
        return entityManagerFactory.createEntityManager();
    }

    public static void shutDown(){
        entityManagerFactory.close();
    }
}
