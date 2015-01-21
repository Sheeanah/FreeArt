package controleur;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLDecoder;
import java.nio.file.Files;

/**
 * Created by Kylian on 21/01/2015.
 */
@WebServlet(name = "Image")
public class Image extends HttpServlet {

    public static final int DEFAULT_BUFFER_SIZE = 10240;
    public static final int TAILLE_TAMPON = 10240;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //Chemin d'accès aux fichiers
        String chemin = this.getServletConfig().getInitParameter("chemin");
        String fichierRequis = request.getPathInfo();
        if ( fichierRequis == null || "/".equals( fichierRequis ) ) {
    /* Si non, alors on envoie une erreur 404, qui signifie que la ressource demandée n'existe pas */
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
            return;
        }

        /* Décode le nom de fichier récupéré, susceptible de contenir des espaces et autres caractères spéciaux, et prépare l'objet File */
        fichierRequis = URLDecoder.decode(fichierRequis, "UTF-8");
        File fichier = new File( chemin, fichierRequis );

        /* Vérifie que le fichier existe bien */
        if ( !fichier.exists() ) {
         /* Si non, alors on envoie une erreur 404, qui signifie que la ressource demandée n'existe pas */
                response.sendError(HttpServletResponse.SC_NOT_FOUND);
            return;
        }
        /* Récupère le type du fichier */
        String type = getServletContext().getMimeType( fichier.getName() );

        /* Si le type de fichier est inconnu, alors on initialise un type par défaut */
        if ( type == null ) {
            type = "application/octet-stream";
        }
        response.reset();
        response.setContentType(type);
        response.setHeader("Content-Length", String.valueOf(fichier.length()));

        // Write image content to response.
        Files.copy(fichier.toPath(), response.getOutputStream());











    }
}
