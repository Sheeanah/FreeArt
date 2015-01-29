package controleur;

import modele.Imagetag;
import modele.Tag;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.util.List;

/**
 * Created by Kylian on 23/01/2015.
 */
public class TagManager {

    public static Tag Create(Tag t)
    {
        SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();

        Session session = sessionFactory.openSession();
        if(!Exists(t)) {


            session.beginTransaction();

            session.save(t);

            session.getTransaction().commit();
            session.close();
        }
        session = sessionFactory.openSession();

        session.beginTransaction();

        t = (Tag)session
                .createQuery("from Tag where label = :label")
                .setParameter("label", t.getLabel())
                .list().get(0);

        session.getTransaction().commit();
        session.close();

        if ( sessionFactory != null ) {
            sessionFactory.close();
        }
        //Fait un nouveau tag et renvoi son id
        return t;
    }

    public static Tag GetByName(Tag tag)
    {
        SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();

        Session session = sessionFactory.openSession();

        session.beginTransaction();

        List tags = session
                .createQuery("from Tag where label = :label")
                .setParameter("label", tag.getLabel())
                .list(); // Eager fetch the collection so we can use it detached

        session.getTransaction().commit();
        session.close();
        if ( sessionFactory != null ) {
            sessionFactory.close();
        }
        return (Tag)tags.get(0);
    }

    public static boolean Exists(Tag tag)
    {
        SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();

        Session session = sessionFactory.openSession();

        session.beginTransaction();

        List tags = session
                .createQuery("from Tag where label = :label")
                .setParameter("label", tag.getLabel())
                .list(); // Eager fetch the collection so we can use it detached

        session.getTransaction().commit();
        session.close();
        if ( sessionFactory != null ) {
            sessionFactory.close();
        }
        if(tags.size() == 0)
        {
            return false;
        }
        else
        {
            return true;
        }
    }


    public static void AssociateToImage(Imagetag itag) {

        SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();

        Session session = sessionFactory.openSession();

        session.beginTransaction();
        session.save(itag);

        session.getTransaction().commit();
        session.close();
        if ( sessionFactory != null ) {
            sessionFactory.close();
        }

    }

    public static List<Imagetag> GetAllAssociation()
    {
        SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();

        Session session = sessionFactory.openSession();

        session.beginTransaction();

        List imagetag = session
                .createQuery("from Imagetag ").list(); // Eager fetch the collection so we can use it detached
        session.close();
        if ( sessionFactory != null ) {
            sessionFactory.close();
        }
        return imagetag;
    }

    public static List<Tag> getAllTag()
    {
        SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();

        Session session = sessionFactory.openSession();

        session.beginTransaction();

        List tag = session
                .createQuery("from Tag ").list(); // Eager fetch the collection so we can use it detached
        session.close();
        if ( sessionFactory != null ) {
            sessionFactory.close();
        }
        return tag;
    }
}
