package controleur;

import modele.Categorie;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLDecoder;
import java.util.List;

/**
 * Created by Kylian on 22/01/2015.
 */
@WebServlet(name = "CategorieContent")
public class CategorieContent extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String categorie_label = request.getPathInfo();
        if ( categorie_label == null || "/".equals( categorie_label ) ) {
    /* Si non, alors on envoie une erreur 404, qui signifie que la ressource demandée n'existe pas */
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
            return;
        }

        categorie_label = URLDecoder.decode(categorie_label,"UTF8");
        int imageParPage = 12;
        int minInter=1; //Pour la pagination, le début de l'affichage
        int maxInter=12;   //la fin de l'affichage
        int maxPage=0;  //Le nombre de page maximux que l'on a

        String str[] = categorie_label.split("/");

        if(str.length > 3)
        {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
            return;
        }

        categorie_label = str[1];

        //Maintenant on connaît la catégorie que souhaite visualiser l'utilisateur on regarde si cette catégorie existe
        Categorie c = new Categorie();
        c.setLabel(categorie_label);
        if(!CategorieManager.Exists(c))
        {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
            return;
        }

        c = CategorieManager.GetByName(categorie_label);

        List<modele.Image> images = ImageManager.GetByCategorie(c.getId());
        if(images.size()<12)
        {

            maxPage=1;
        }
        else
        {
            maxPage = (int) Math.ceil(images.size() * 1.0 / imageParPage);
        }
        String debug = images.size()+" et categ = "+categorie_label;
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
        request.setAttribute("debug",debug);
        request.setAttribute("currentPage",pageDemandee);
        request.setAttribute("currentCategorie",categorie_label);
        this.getServletContext().getRequestDispatcher( "/imgbycategorie.jsp" ).forward(request, response);

    }

    private static boolean isInteger(String s) {
        boolean isValid = true;
        try{ Integer.parseInt(s); }
        catch(NumberFormatException nfe){ isValid = false; }
        return isValid;
    }
}
