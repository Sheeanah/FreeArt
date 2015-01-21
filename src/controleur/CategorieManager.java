package controleur;

import modele.Categorie;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.util.List;

/**
 * Created by Kylian on 20/01/2015.
 */
public class CategorieManager {

    public static List<Categorie> getAll()
    {
        SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();

        Session session = sessionFactory.getCurrentSession();

        session.beginTransaction();

        List categories = session
                .createQuery("from Categorie ").list(); // Eager fetch the collection so we can use it detached

        return categories;
    }

}
