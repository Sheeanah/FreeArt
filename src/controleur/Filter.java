package controleur;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * Created by Kylian on 18/01/2015.
 */
@WebFilter(filterName = "Filter")
public class Filter implements javax.servlet.Filter {
    public void destroy() {
    }

    //Filtre empêchant un utilisateur connecté de créer un utilisateur

    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws ServletException, IOException {
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) resp;

        HttpSession session = request.getSession(false);

        if(session.getAttribute("User")==null)
        {
            chain.doFilter(req, resp);
        }
        else
        {
            response.sendRedirect( "/Home" );
        }


    }

    public void init(FilterConfig config) throws ServletException {

    }

}
