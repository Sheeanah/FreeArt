package Servlet;

import controleur.ImageManager;
import controleur.TagManager;
import modele.Imagetag;
import modele.Tag;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * Created by Kylian on 29/01/2015.
 */
@WebServlet("/Recherche/*")
public class Recherche extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String recherche = request.getParameter("recherche");

        List<modele.Image> images = ImageManager.Search(recherche);

        if(images.size()>0)
        {
            request.setAttribute("images",images);
            List imgtag = TagManager.GetAllAssociation();
            request.setAttribute("imagetag",(List<Imagetag>) imgtag);
            List tag = TagManager.getAllTag();
            request.setAttribute("tags",(List<Tag>) tag);
        }
        else
        {
            request.setAttribute("vide","La recherche n'a renvoyé aucun résultat");
        }


        this.getServletContext().getRequestDispatcher( "/recherche.jsp" ).forward(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.sendRedirect("/Home");
    }
}
