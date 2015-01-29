package controleur;

import modele.Categorie;
import modele.User;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Kylian on 22/01/2015.
 */
@WebFilter(filterName = "FilterLoadCategories")
public class FilterLoadCategories implements javax.servlet.Filter {

    private static final int COOKIE_MAX_AGE = 60*60*24*365;

    public void destroy() {
    }

    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws ServletException, IOException {

        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) resp;

        List categories = CategorieManager.getAll();
        request.setAttribute("categoriesMenu",(List<Categorie>) categories);
        List users = UserManager.getAll();
        request.setAttribute("users",(List<User>) users);
        String addToPanier = request.getParameter("image_id_panier");
        String removeFromPanier = request.getParameter("image_id_panier_remove");
        String cookie = getCookieValue(request, "Panier");
        if(!(addToPanier == null))
        {
            if(!addToPanier.isEmpty())
            {

                int image = Integer.parseInt(addToPanier);
                modele.Image i = ImageManager.GetById(image);
                if(i != null)
                {
                    if(cookie!=null && cookie!="")
                    {
                        String str[] = cookie.split("/");
                        boolean exists = false;
                        for(String s : str)
                        {
                            if(s.equals(i.getId()+""))
                            {
                                exists = true;
                            }
                        }
                        if(!exists)
                        {
                            cookie = cookie+"/"+i.getId();
                            setCookie(response, "Panier", cookie, COOKIE_MAX_AGE);
                        }

                    }
                    else
                    {
                        cookie=i.getId()+"";
                        setCookie( response, "Panier", cookie, COOKIE_MAX_AGE );
                    }
                }
            }

        }
        if(!(removeFromPanier == null))
        {
            if(!(removeFromPanier.isEmpty()))
            {
                int image = Integer.parseInt(removeFromPanier);
                if(cookie != null)
                {
                    String str[] = cookie.split("/");
                    cookie="";
                    boolean first_to_delete=false;
                    for(int i = 0;i<str.length;i++)
                    {



                        if(i==0 && Integer.parseInt(str[i])!=image && str.length == 1)
                        {
                            cookie = str[i];
                        }
                        else if(i==0 && Integer.parseInt(str[i])!=image && str.length > 1)
                        {
                            cookie = str[i];
                        }
                        else if(i==0 && Integer.parseInt(str[i])==image && str.length == 1)
                        {
                            cookie = null;
                        }
                        else if(i==0 && Integer.parseInt(str[i])==image && str.length > 1)
                        {
                            first_to_delete = true;
                        }
                        else
                        {
                            if(Integer.parseInt(str[i])!=image)
                            {
                                if(first_to_delete)
                                {
                                    cookie = str[i];
                                    first_to_delete=false;
                                }
                                else
                                {
                                    cookie = cookie + "/" + str[i];
                                }

                            }


                        }
                    }
                    if(cookie == "")
                    {
                        cookie=null;
                    }
                }
                if(cookie == "")
                {
                    cookie=null;
                }
                setCookie(response, "Panier", cookie, COOKIE_MAX_AGE);
            }

        }
        if(cookie != null && cookie!="")
        {
            String str[] = cookie.split("/");

            request.setAttribute("elem_panier",str.length);
        }
        else
        {
            request.setAttribute("elem_panier",0);
        }



        chain.doFilter(req, resp);
    }

    public void init(FilterConfig config) throws ServletException {

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
