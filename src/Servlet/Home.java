package Servlet;

import controleur.CategorieManager;
import controleur.ImageManager;
import controleur.TagManager;
import controleur.UserManager;
import modele.*;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

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
            User connectedUser = UserManager.Connect(u);
            if(connectedUser != null)
            {
                HttpSession session = request.getSession(true);
                session.setAttribute("User", connectedUser);
            }
        }
        List users = UserManager.getAll();
        request.setAttribute("users",(List<User>) users);
        List categ = CategorieManager.getAll();
        request.setAttribute("categories",(List<Categorie>) categ);
        List img = ImageManager.getAll();
        request.setAttribute("images",(List<modele.Image>) img);
        List imgtag = TagManager.GetAllAssociation();
        request.setAttribute("imagetag",(List<Imagetag>) imgtag);
        List tag = TagManager.getAllTag();
        request.setAttribute("tags",(List<Tag>) tag);

        this.getServletContext().getRequestDispatcher( "/index.jsp" ).forward(request, response); //On redurige vers la page d'accueil
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List users = UserManager.getAll();
        request.setAttribute("users",(List<User>) users);
        List categ = CategorieManager.getAll();
        request.setAttribute("categories",(List<Categorie>) categ);
        List img = ImageManager.getAll();
        request.setAttribute("images",(List<modele.Image>) img);
        List imgtag = TagManager.GetAllAssociation();
        request.setAttribute("imagetag",(List<Imagetag>) imgtag);
        List tag = TagManager.getAllTag();
        request.setAttribute("tags",(List<Tag>) tag);
        this.getServletContext().getRequestDispatcher( "/index.jsp" ).forward(request, response); //On redurige vers la page d'accueil

    }
}
