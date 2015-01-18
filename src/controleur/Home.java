package controleur;

import modele.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Created by Dorian on 16/01/2015.
 */
@WebServlet("/Home")
public class Home extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String login = request.getParameter("userName");
        String pwd = request.getParameter("userPassword");
        if(login != "" && pwd != "")
        {
            User u = new User();
            u.setLogin(login);
            u.setMdp(pwd);
            if(UserManager.Connect(u))
            {
                HttpSession session = request.getSession(true);
                session.setAttribute("User", u);
            }
        }

        this.getServletContext().getRequestDispatcher( "/index.jsp" ).forward(request, response); //On redurige vers la page d'accueil
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.getServletContext().getRequestDispatcher( "/index.jsp" ).forward(request, response); //On redurige vers la page d'accueil

    }
}
