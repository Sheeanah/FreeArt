package controleur;

import modele.Categorie;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.sql.Date;
import java.util.List;

/**
 * Created by Kylian on 20/01/2015.
 */
public class CategorieManager { //EJBCategorie

    public static List<Categorie> getAll()
    {
        SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();

        Session session = sessionFactory.openSession();

        session.beginTransaction();

        List categories = session
                .createQuery("from Categorie ").list(); // Eager fetch the collection so we can use it detached
        session.close();
        if ( sessionFactory != null ) {
            sessionFactory.close();
        }
        return categories;
    }

    public static boolean Exists(Categorie c)
    {
        SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();

        Session session = sessionFactory.openSession();

        session.beginTransaction();

        List categories = session
                .createQuery("select label from Categorie where label = :clabel")
                .setParameter("clabel", c.getLabel())
                .list();

        session.getTransaction().commit();
        session.close();
        if ( sessionFactory != null ) {
            sessionFactory.close();
        }
        if(categories.size() == 0)
        {
            return false;
        }
        else
        {
            return true;
        }
    }

    public static int Create(Categorie c)
    {
        SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();

        Session session = sessionFactory.openSession();

        session.beginTransaction();



        Integer id = (Integer) session.save(c);

        session.getTransaction().commit();
        session.close();
        if ( sessionFactory != null ) {
            sessionFactory.close();
        }

        return id.intValue();
    }

    public static Categorie GetByName(String name)
    {
        SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();

        Session session = sessionFactory.openSession();

        session.beginTransaction();

        List categories = session
                .createQuery("from Categorie where label = :clabel")
                .setParameter("clabel", name)
                .list();

        session.getTransaction().commit();
        session.close();
        if ( sessionFactory != null ) {
            sessionFactory.close();
        }

        return (Categorie)categories.get(0);
    }

    public static Categorie GetById(int id)
    {
        SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();

        Session session = sessionFactory.openSession();

        session.beginTransaction();

        List categories = session
                .createQuery("from Categorie where id = :id")
                .setParameter("id", id)
                .list();

        session.getTransaction().commit();
        session.close();
        if ( sessionFactory != null ) {
            sessionFactory.close();
        }
        if(categories.size() > 0)
        {
            return (Categorie)categories.get(0);
        }
        else
        {
            return null;
        }
    }

}
