package controleur;

import modele.Categorie;
import modele.User;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * Created by Kylian on 22/01/2015.
 */
@WebFilter(filterName = "FilterLoadCategories")
public class FilterLoadCategories implements javax.servlet.Filter {
    public void destroy() {
    }

    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws ServletException, IOException {

        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) resp;

        List categories = CategorieManager.getAll();
        request.setAttribute("categoriesMenu",(List<Categorie>) categories);
        List users = UserManager.getAll();
        request.setAttribute("users",(List<User>) users);

        chain.doFilter(req, resp);
    }

    public void init(FilterConfig config) throws ServletException {

    }

}
