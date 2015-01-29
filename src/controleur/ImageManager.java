package controleur;

import modele.*;
import modele.Image;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Kylian on 20/01/2015.
 */
public class ImageManager {

    public static modele.Image save(modele.Image i)
    {

            SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();

            Session session = sessionFactory.openSession();

            session.beginTransaction();
            session.save(i);

            session.getTransaction().commit();
        session.close();
         session = sessionFactory.openSession();

        session.beginTransaction();

        i = (modele.Image)session
            .createQuery("from Image where image = :image")
            .setParameter("image", i.getImage())
            .list().get(0);

        session.getTransaction().commit();
        session.close();

        if ( sessionFactory != null ) {
            sessionFactory.close();
        }

        return i;
    }

    public static boolean Exists(Image i)
    {
        SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();

        Session session = sessionFactory.openSession();

        session.beginTransaction();

        List images = session
                .createQuery("from Image where image = :image")
                .setParameter("image", i.getImage())
                .list(); // Eager fetch the collection so we can use it detached

        session.getTransaction().commit();
        session.close();
        if ( sessionFactory != null ) {
            sessionFactory.close();
        }
        if(images.size() == 0)
        {
            return false;
        }
        else
        {
            return true;
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

    public static List<modele.Image> GetByTag(Tag tag)
    {
        SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();

        Session session = sessionFactory.openSession();

        session.beginTransaction();

        List imagesTag = TagManager.GetAllAssociation();

        List imageByTag = new ArrayList<modele.Image>();

        for(Imagetag itag : (List<Imagetag>)imagesTag)
        {
            if(itag.getTagid() == tag.getId())
            {
                imageByTag.add(GetById(itag.getImageid()));
            }
        }

        session.getTransaction().commit();
        session.close();
        if ( sessionFactory != null ) {
            sessionFactory.close();
        }
        return imageByTag;
    }

    public static List<modele.Image> GetByAuteur(int auteur)
    {
        SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();

        Session session = sessionFactory.openSession();

        session.beginTransaction();

        List images = session
                .createQuery("from Image where auteur = :iauteur")
                .setParameter("iauteur", auteur)
                .list();

        session.getTransaction().commit();
        session.close();
        if ( sessionFactory != null ) {
            sessionFactory.close();
        }
        return images;
    }

    public static modele.Image GetById(int id)
    {
        SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();

        Session session = sessionFactory.openSession();

        session.beginTransaction();

        List images = session
                .createQuery("from Image where id = :id")
                .setParameter("id", id)
                .list();

        session.getTransaction().commit();
        session.close();
        if ( sessionFactory != null ) {
            sessionFactory.close();
        }
        if(images.size() > 0)
        {
            return (modele.Image)images.get(0);
        }
        else
        {
            return null;
        }
    }

    public static void Delete(modele.Image i)
    {
        SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();

        Session session = sessionFactory.openSession();

        session.beginTransaction();

        Query q2 = session.createQuery("delete Imagetag where imageid = "+i.getId());
         q2.executeUpdate();

        Query q = session.createQuery("delete Image where id = "+i.getId());

        q.executeUpdate();
        session.getTransaction().commit();
        session.close();
        if ( sessionFactory != null ) {
            sessionFactory.close();
        }

    }

    public static List<modele.Image> Search(String search)
    {

        SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();

        Session session = sessionFactory.openSession();

        session.beginTransaction();

        Tag t = new Tag();
        t.setLabel(search);

        List imageByTag = new ArrayList<modele.Image>();

        if(TagManager.GetByName(t) != null)
        {
            t = TagManager.GetByName(t);
            imageByTag = GetByTag(t);
        }
        List images = session
                .createQuery("from Image fetch all properties where (titre like '%" + search + "%' OR description like '%"+search+"%')")

                .list();

        session.getTransaction().commit();
        session.close();
        if ( sessionFactory != null ) {
            sessionFactory.close();
        }

        if(imageByTag.size()>0)
        {
            for(modele.Image i : (List<modele.Image>)imageByTag)
            {
                if(!images.contains(i))
                {
                    images.add(i);
                }
            }
        }



            return images;



    }

}
