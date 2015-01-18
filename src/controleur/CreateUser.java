package controleur;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Date;

import modele.User;
import org.hibernate.*;
import org.hibernate.cfg.*;

/**
 * Created by Kylian on 18/01/2015.
 */
@WebServlet("/CreateUser")
public class CreateUser extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();

        Session session = sessionFactory.getCurrentSession();

        session.beginTransaction();
        User u = new User();
        u.setLogin("kylian");
        u.setMdp("zizi");
        u.setDateinscri(new Date(System.currentTimeMillis()));
        session.save(u);
        session.getTransaction().commit();

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.getServletContext().getRequestDispatcher( "/createUser.jsp" ).forward(request, response); //On redurige vers la page de création de compte
    }
}
