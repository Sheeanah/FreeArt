package controleur;

import modele.Categorie;
import modele.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by Kylian on 23/01/2015.
 */
@WebServlet(name = "ImageViewer")
public class ImageViewer extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String param = request.getPathInfo();

        String str[] = param.split("/"); //On récupère les paramètres de l'url individuellement
        //Le premier élément du tableau est vide

        if(str.length != 2)
        {
            //Si on a pas le bon nombre de parametres
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
            return;
        }

        if(!isInteger(str[1]))
        {
            //Si on a pas un int
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
            return;
        }

        int image_id = Integer.parseInt(str[1]);

        modele.Image currentImage = ImageManager.GetById(image_id);

        if(currentImage == null)
        {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
            return;
        }

        //L'image demandée existe et on a ses infos dans currentImage

        Categorie imageCategorie = CategorieManager.GetById(currentImage.getCategorie());

        User auteur = UserManager.GetById(currentImage.getAuteur());

        request.setAttribute("image", currentImage);
        request.setAttribute("categorie", imageCategorie);
        request.setAttribute("auteur", auteur);

        this.getServletContext().getRequestDispatcher( "/imageView.jsp" ).forward(request, response); //On redurige vers la page d'accueil



    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String param = request.getPathInfo();

        String str[] = param.split("/"); //On récupère les paramètres de l'url individuellement
        //Le premier élément du tableau est vide

        if(str.length != 2)
        {
            //Si on a pas le bon nombre de parametres
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
            return;
        }

        if(!isInteger(str[1]))
        {
            //Si on a pas un int
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
            return;
        }

        int image_id = Integer.parseInt(str[1]);

        modele.Image currentImage = ImageManager.GetById(image_id);

        if(currentImage == null)
        {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
            return;
        }

        //L'image demandée existe et on a ses infos dans currentImage

        Categorie imageCategorie = CategorieManager.GetById(currentImage.getCategorie());

        User auteur = UserManager.GetById(currentImage.getAuteur());

        request.setAttribute("image", currentImage);
        request.setAttribute("categorie", imageCategorie);
        request.setAttribute("auteur", auteur);

        this.getServletContext().getRequestDispatcher( "/imageView.jsp" ).forward(request, response); //On redurige vers la page d'accueil



    }

    private static boolean isInteger(String s) {
        boolean isValid = true;
        try{ Integer.parseInt(s); }
        catch(NumberFormatException nfe){ isValid = false; }
        return isValid;
    }

}
