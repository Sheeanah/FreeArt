package controleur;

import modele.User;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.sql.Date;
import java.util.List;

/**
 * Created by Kylian on 18/01/2015.
 */
public class UserManager {

    public static boolean Exists(User u)
    {
        SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();

        Session session = sessionFactory.openSession();

        session.beginTransaction();

        List user = session
                .createQuery("select login from User where login = :ulogin")
                .setParameter("ulogin", u.getLogin())
                .list(); // Eager fetch the collection so we can use it detached

        session.getTransaction().commit();
        if(user.size() == 0)
        {
            return false;
        }
        else
        {
            return true;
        }
    }

    public static boolean Create(User u)
    {
        try {
            SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();

            Session session = sessionFactory.openSession();

            session.beginTransaction();

            u.setDateinscri(new Date(System.currentTimeMillis()));

            session.save(u);

            session.getTransaction().commit();
            session.close();
            if ( sessionFactory != null ) {
                sessionFactory.close();
            }

            return true;
        }
        catch (Exception ex)
        {
            return false;
        }

    }

    public static User Connect(User u)
    {
        SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();

        Session session = sessionFactory.openSession();

        session.beginTransaction();

        Query q = session.createQuery("from User where login = :ulogin and mdp = :umdp");

        q.setParameter("ulogin", u.getLogin());
        q.setParameter("umdp",u.getMdp());

        List user = q.list();

        session.getTransaction().commit();
        session.close();
        if ( sessionFactory != null ) {
            sessionFactory.close();
        }
        if(user.size() == 0)
        {
            return null;
        }
        else
        {
            return (User)user.get(0);
        }
    }

    public static List<User> getAll()
    {
        SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();

        Session session = sessionFactory.openSession();

        session.beginTransaction();

        List users = session
                .createQuery("from User ").list(); // Eager fetch the collection so we can use it detached
        session.close();
        if ( sessionFactory != null ) {
            sessionFactory.close();
        }
        return users;
    }

    public static User GetById(int id)
    {
        SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();

        Session session = sessionFactory.openSession();

        session.beginTransaction();

        List users = session
                .createQuery("from User where id = :id")
                .setParameter("id", id)
                .list();

        session.getTransaction().commit();
        session.close();
        if ( sessionFactory != null ) {
            sessionFactory.close();
        }
        if(users.size() > 0)
        {
            return (User)users.get(0);
        }
        else
        {
            return null;
        }
    }

}
