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
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.net.URLDecoder;
import java.util.List;

/**
 * Created by Kylian on 29/01/2015.
 */
@WebServlet("/MyAccount/*")
public class MyAccount extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String image_id_delete = request.getParameter("image_id_delete");
        if(image_id_delete != null)
        {
            if(!image_id_delete.isEmpty())
            {
                if(isInteger(image_id_delete))
                {
                    int toDelete = Integer.parseInt(image_id_delete);
                    ImageManager.Delete(ImageManager.GetById(toDelete));

                }
            }
        }
        response.sendRedirect("/MyAccount");
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String currentPage = request.getPathInfo();

        if(currentPage == null)
        {
            currentPage = "1";
        }
        else
        {
            currentPage = URLDecoder.decode(currentPage,"UTF8");
        }
        int imageParPage = 12;
        int minInter=1; //Pour la pagination, le début de l'affichage
        int maxInter=12;   //la fin de l'affichage
        int maxPage=0;  //Le nombre de page maximux que l'on a

        String str[] = currentPage.split("/");

        if(str.length > 2)
        {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
            return;
        }



        //Maintenant on connaît la catégorie que souhaite visualiser l'utilisateur on regarde si cette catégorie existe
        HttpSession session = request.getSession(false);
        User u = new User();
        if(session.getAttribute("User")==null)
        {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
            return;
        }
        else
        {
            u = (User)session.getAttribute("User");
        }

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
        if(str.length == 2)
        {
            //Alors on a un numéro de page en paramètre
            page = str[1];
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
        request.setAttribute("currentAuteur",u.getLogin());
        List imgtag = TagManager.GetAllAssociation();
        request.setAttribute("imagetag",(List<Imagetag>) imgtag);
        List tag = TagManager.getAllTag();
        request.setAttribute("tags",(List<Tag>) tag);
        this.getServletContext().getRequestDispatcher( "/myaccount.jsp" ).forward(request, response);
    }

    private static boolean isInteger(String s) {
        boolean isValid = true;
        try{ Integer.parseInt(s); }
        catch(NumberFormatException nfe){ isValid = false; }
        return isValid;
    }
}
