package controleur;

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
 * Created by Kylian on 29/01/2015.
 */
@WebServlet("/Tag/*")
public class TagView extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String tag_label = request.getPathInfo();
        if ( tag_label == null || "/".equals( tag_label ) ) {
    /* Si non, alors on envoie une erreur 404, qui signifie que la ressource demandée n'existe pas */
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
            return;
        }

        tag_label = URLDecoder.decode(tag_label, "UTF8");
        int imageParPage = 12;
        int minInter=1; //Pour la pagination, le début de l'affichage
        int maxInter=12;   //la fin de l'affichage
        int maxPage=0;  //Le nombre de page maximux que l'on a

        String str[] = tag_label.split("/");

        if(str.length > 3)
        {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
            return;
        }

        tag_label = str[1];

        //Maintenant on connaît la catégorie que souhaite visualiser l'utilisateur on regarde si cette catégorie existe
        Tag tag = new Tag();
        tag.setLabel(tag_label);
        if(!TagManager.Exists(tag))
        {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
            return;
        }

        tag = TagManager.GetByName(tag);

        List<modele.Image> images = ImageManager.GetByTag(tag);
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
        request.setAttribute("currentTag",tag_label);
        List imgtag = TagManager.GetAllAssociation();
        request.setAttribute("imagetag",(List<Imagetag>) imgtag);
        List tagtag = TagManager.getAllTag();
        request.setAttribute("tags",(List<Tag>) tagtag);
        this.getServletContext().getRequestDispatcher( "/imgbytag.jsp" ).forward(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String tag_label = request.getPathInfo();
        if ( tag_label == null || "/".equals( tag_label ) ) {
    /* Si non, alors on envoie une erreur 404, qui signifie que la ressource demandée n'existe pas */
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
            return;
        }

        tag_label = URLDecoder.decode(tag_label, "UTF8");
        int imageParPage = 12;
        int minInter=1; //Pour la pagination, le début de l'affichage
        int maxInter=12;   //la fin de l'affichage
        int maxPage=0;  //Le nombre de page maximux que l'on a

        String str[] = tag_label.split("/");

        if(str.length > 3)
        {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
            return;
        }

        tag_label = str[1];

        //Maintenant on connaît la catégorie que souhaite visualiser l'utilisateur on regarde si cette catégorie existe
        Tag tag = new Tag();
        tag.setLabel(tag_label);
        if(!TagManager.Exists(tag))
        {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
            return;
        }

         tag = TagManager.GetByName(tag);

        List<modele.Image> images = ImageManager.GetByTag(tag);
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
        request.setAttribute("currentTag",tag_label);
        List imgtag = TagManager.GetAllAssociation();
        request.setAttribute("imagetag",(List<Imagetag>) imgtag);
        List tagtag = TagManager.getAllTag();
        request.setAttribute("tags",(List<Tag>) tagtag);
        this.getServletContext().getRequestDispatcher( "/imgbytag.jsp" ).forward(request, response);
    }

    private static boolean isInteger(String s) {
        boolean isValid = true;
        try{ Integer.parseInt(s); }
        catch(NumberFormatException nfe){ isValid = false; }
        return isValid;
    }

}
