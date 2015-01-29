package Servlet;

import controleur.ImageManager;
import controleur.TagManager;
import controleur.UserManager;
import modele.*;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLDecoder;
import java.util.List;

/**
 * Created by Kylian on 27/01/2015.
 */
@WebServlet("/Auteur/*")

//Servlet qui va servir à afficher les images d'un auteur spécifique

public class Auteur extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String auteur = request.getPathInfo();//On récupère le paramètre passé à l'URL
        if ( auteur == null || "/".equals( auteur ) ) {
            /* Si non, alors on envoie une erreur 404, qui signifie que la ressource demandée n'existe pas */
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
            return;
        }
        //Potentillement on peut avoir des caractères spéciaux
        auteur = URLDecoder.decode(auteur,"UTF8");
        int imageParPage = 12;
        int minInter=1; //Pour la pagination, le début de l'affichage
        int maxInter=12;   //la fin de l'affichage
        int maxPage=0;  //Le nombre de page maximux que l'on a

        String str[] = auteur.split("/");

        if(str.length > 3)//Le nombre de paramètre passé est incorrect
        {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
            return;
        }

        auteur = str[1];

        //Maintenant on connaît l'auteur que souhaite visualiser l'utilisateur on regarde si cet auteur existe
        User u = new User();
        u.setLogin(auteur);
        if(!UserManager.Exists(u))
        {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
            return;
        }

        u = UserManager.GetByLogin(u);

        List<modele.Image> images = ImageManager.GetByAuteur(u.getId());
        //On récupère nos images et on paramètre la pagination
        if(images.size()<12)
        {

            maxPage=1;
        }
        else
        {
            maxPage = (int) Math.ceil(images.size() * 1.0 / imageParPage);
        }

        String page ="";
        int pageDemandee = 1;
        if(str.length == 3)
        {
            //Alors on a un numéro de page en paramètre
            page = str[2];
            if(isInteger(page))
            {
                pageDemandee = Integer.parseInt(page);
                //Si le paramètre est bien un chiffre
                if(pageDemandee > 0 && pageDemandee <= maxPage)
                {
                    minInter=(minInter+pageDemandee*imageParPage)-imageParPage;
                    maxInter=(maxInter+pageDemandee*imageParPage)-imageParPage;
                }
                else
                {
                    response.sendError(HttpServletResponse.SC_NOT_FOUND);
                    return;
                }
            }
            else
            {
                response.sendError(HttpServletResponse.SC_NOT_FOUND);
                return;
            }
        }
         //On transmet les paramètres a la requete afin de les retrouver dans la jsp

        request.setAttribute("images",images);
        request.setAttribute("maxPage",maxPage);
        request.setAttribute("maxInter",maxInter);
        request.setAttribute("minInter",minInter);

        request.setAttribute("currentPage",pageDemandee);
        request.setAttribute("currentAuteur",auteur);
        List imgtag = TagManager.GetAllAssociation();
        request.setAttribute("imagetag",(List<Imagetag>) imgtag);
        List tag = TagManager.getAllTag();
        request.setAttribute("tags",(List<Tag>) tag);
        this.getServletContext().getRequestDispatcher( "/imgbyauteur.jsp" ).forward(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String auteur = request.getPathInfo();
        if ( auteur == null || "/".equals( auteur ) ) {
    /* Si non, alors on envoie une erreur 404, qui signifie que la ressource demandée n'existe pas */
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
            return;
        }

        auteur = URLDecoder.decode(auteur,"UTF8");
        int imageParPage = 12;
        int minInter=1; //Pour la pagination, le début de l'affichage
        int maxInter=12;   //la fin de l'affichage
        int maxPage=0;  //Le nombre de page maximux que l'on a

        String str[] = auteur.split("/");

        if(str.length > 3)
        {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
            return;
        }

        auteur = str[1];

        //Maintenant on connaît la catégorie que souhaite visualiser l'utilisateur on regarde si cette catégorie existe
        User u = new User();
        u.setLogin(auteur);
        if(!UserManager.Exists(u))
        {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
            return;
        }

        u = UserManager.GetByLogin(u);

        List<modele.Image> images = ImageManager.GetByAuteur(u.getId());
        if(images.size()<12)
        {

            maxPage=1;
        }
        else
        {
            maxPage = (int) Math.ceil(images.size() * 1.0 / imageParPage);
        }

        String page ="";
        int pageDemandee = 1;
        if(str.length == 3)
        {
            //Alors on a un numéro de page en paramètre
            page = str[2];
            if(isInteger(page))
            {
                pageDemandee = Integer.parseInt(page);
                //Si le paramètre est bien un chiffre
                if(pageDemandee > 0 && pageDemandee <= maxPage)
                {
                    minInter=(minInter+pageDemandee*imageParPage)-imageParPage;
                    maxInter=(maxInter+pageDemandee*imageParPage)-imageParPage;
                }
                else
                {
                    response.sendError(HttpServletResponse.SC_NOT_FOUND);
                    return;
                }
            }
            else
            {
                response.sendError(HttpServletResponse.SC_NOT_FOUND);
                return;
            }
        }


        request.setAttribute("images",images);
        request.setAttribute("maxPage",maxPage);
        request.setAttribute("maxInter",maxInter);
        request.setAttribute("minInter",minInter);

        request.setAttribute("currentPage",pageDemandee);
        request.setAttribute("currentAuteur",auteur);
        List imgtag = TagManager.GetAllAssociation();
        request.setAttribute("imagetag",(List<Imagetag>) imgtag);
        List tag = TagManager.getAllTag();
        request.setAttribute("tags",(List<Tag>) tag);
        this.getServletContext().getRequestDispatcher( "/imgbyauteur.jsp" ).forward(request, response);
    }

    private static boolean isInteger(String s) {
        boolean isValid = true;
        try{ Integer.parseInt(s); }
        catch(NumberFormatException nfe){ isValid = false; }
        return isValid;
    }

}
