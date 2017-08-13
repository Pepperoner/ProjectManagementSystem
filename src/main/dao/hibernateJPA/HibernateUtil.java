package dao.hibernateJPA;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.io.File;

//Утильный класс для создания фабрики сессий на основе Hibernate в одном екземпляре на программу.
public class HibernateUtil {

    private static SessionFactory newSessionFactory;

    static {
        try {
            File file = new File("src\\resources\\META-INF\\hibernate.cfg.xml");

            Configuration configuration = new Configuration().configure();
            configuration.configure(file);
            newSessionFactory = configuration.buildSessionFactory();

        }catch (Throwable ex){
            throw new ExceptionInInitializerError(ex);
        }
    }

    public static Session getSession(){
        return newSessionFactory.openSession();
    }

    public static void shutDown(){
        newSessionFactory.close();
    }
}
