package controleur;

import modele.*;
import modele.Image;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.sql.Date;
import java.util.List;

/**
 * Created by Kylian on 20/01/2015.
 */
public class ImageManager {

    public static void save(modele.Image i)
    {

            SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();

            Session session = sessionFactory.openSession();

            session.beginTransaction();



            session.save(i);

            session.getTransaction().commit();
        session.close();
        if ( sessionFactory != null ) {
            sessionFactory.close();
        }
    }

    public static List<modele.Image> getAll()
    {
        SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();

        Session session = sessionFactory.openSession();

        session.beginTransaction();

        List images = session
                .createQuery("from Image ").list(); // Eager fetch the collection so we can use it detached
        session.close();
        if ( sessionFactory != null ) {
            sessionFactory.close();
        }
        return images;
    }

    public static List<modele.Image> GetByCategorie(int categorie)
    {
        SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();

        Session session = sessionFactory.openSession();

        session.beginTransaction();

        List images = session
                .createQuery("from Image where categorie = :icategorie")
                .setParameter("icategorie", categorie)
                .list();

        session.getTransaction().commit();
        session.close();
        if ( sessionFactory != null ) {
            sessionFactory.close();
        }
        return images;
    }

}
