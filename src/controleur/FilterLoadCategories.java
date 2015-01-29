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
    //Ce filtre est appliqué au pattern /*
    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws ServletException, IOException {

        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) resp;

        //On récupère toutes les catégories (pour les avoir dans le menu et aussi dans les jsp

        List categories = CategorieManager.getAll();
        request.setAttribute("categoriesMenu",(List<Categorie>) categories);

        //On récupère tous nos utilisateurs pour y accéder dans nos jsp
        List users = UserManager.getAll();
        request.setAttribute("users",(List<User>) users);

        //On traite les demande d'ajout ou non dans le panier
        String addToPanier = request.getParameter("image_id_panier");
        String removeFromPanier = request.getParameter("image_id_panier_remove");
        String cookie = getCookieValue(request, "Panier");
        String vider = request.getParameter("viderPanier");
        if(vider!=null)
        {
            if(!vider.isEmpty())
            {
                setCookie(response,"Panier","",0);
            }
        }
        if(!(addToPanier == null))
        {
            if(!addToPanier.isEmpty())
            {
                //On récupère l'id de l'image a ajouter au panier
                int image = Integer.parseInt(addToPanier);
                modele.Image i = ImageManager.GetById(image);
                if(i != null)
                {
                    //Si le cookie existe on le complète
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
                        //Sinon on créé le cookie
                        cookie=i.getId()+"";
                        setCookie( response, "Panier", cookie, COOKIE_MAX_AGE );
                    }
                }
            }

        }
        //Pour enlever une image du panier
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

        //Permet de connaître le nombre d'élément dans le panier
        if(cookie != null && cookie!="")
        {
            int i=0;
            for(String s : cookie.split("/")) {
                if(ImageManager.GetById(Integer.parseInt(s))!=null)
                {
                    i++;
                }

            }

            request.setAttribute("elem_panier",i);
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
