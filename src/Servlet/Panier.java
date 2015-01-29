package Servlet;

import controleur.ImageManager;
import controleur.TagManager;
import modele.*;
import modele.Image;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Kylian on 26/01/2015.
 */
@WebServlet("/Panier/*")
public class Panier extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {



        response.sendRedirect( "/Panier" );

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
        if(str.length == 1)
        {
            currentPage=1+"";
        }
        else
        {
            currentPage = str[1];
        }

        String cookie = getCookieValue(request,"Panier");

        if(!(cookie == null || cookie==""))
        {

            List<modele.Image> images = new ArrayList<modele.Image>();
            for(String s : cookie.split("/")) {
                if(ImageManager.GetById(Integer.parseInt(s))!=null)
                {
                    images.add(ImageManager.GetById(Integer.parseInt(s)));
                }

            }

            if(images.size()<12)
            {

                maxPage=1;
            }
            else
            {
                maxPage = (int) Math.ceil(images.size() * 1.0 / imageParPage);
            }

            String page =currentPage;
            int pageDemandee = 1;
            //Alors on a un numéro de page en paramètre

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



            request.setAttribute("images",images);
            request.setAttribute("maxPage",maxPage);
            request.setAttribute("maxInter",maxInter);
            request.setAttribute("minInter",minInter);

            request.setAttribute("currentPage",pageDemandee);

            List imgtag = TagManager.GetAllAssociation();
            request.setAttribute("imagetag",(List<Imagetag>) imgtag);
            List tag = TagManager.getAllTag();
            request.setAttribute("tags",(List<Tag>) tag);
            this.getServletContext().getRequestDispatcher( "/panier.jsp" ).forward(request, response);
        }
        else
        {

            this.getServletContext().getRequestDispatcher( "/panier.jsp" ).forward(request, response);
        }



    }

    private static boolean isInteger(String s) {
        boolean isValid = true;
        try{ Integer.parseInt(s); }
        catch(NumberFormatException nfe){ isValid = false; }
        return isValid;
    }

    private static String getCookieValue( HttpServletRequest request, String nom ) {
        Cookie[] cookies = request.getCookies();
        if ( cookies != null ) {
            for ( Cookie cookie : cookies ) {
                if ( cookie != null && nom.equals( cookie.getName() ) ) {
                    return cookie.getValue();
                }
            }
        }
        return null;
    }

    private static void setCookie( HttpServletResponse response, String nom, String valeur, int maxAge ) {
        Cookie cookie = new Cookie( nom, valeur );
        cookie.setMaxAge( maxAge );
        cookie.setPath("/");
        response.addCookie( cookie );
    }


}
