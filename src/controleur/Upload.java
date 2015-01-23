package controleur;

import eu.medsea.mimeutil.MimeUtil;
import modele.Categorie;
import modele.Imagetag;
import modele.Tag;
import modele.User;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.io.*;
import java.net.FileNameMap;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;


/**
 * Created by Kylian on 20/01/2015.
 */
@WebServlet(name="Upload")
public class Upload extends HttpServlet {

    public static final String CHEMIN        = "chemin";
    public static final int TAILLE_TAMPON = 10240; // 10 ko
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        /* Récupération du contenu du champ de description */
        String chemin = this.getServletConfig().getInitParameter( CHEMIN );
        String tag = request.getParameter("tag");
        String categorie = request.getParameter("categorie");
        String addCategorie = request.getParameter("addCategorie");
        String description = request.getParameter("description");
        String titre = request.getParameter("titre");
        User currentUser = (User) request.getSession().getAttribute("User");
        String messageErreur = "";

        String tags[] = tag.split(";");

        boolean tagok = true;
        if(tags.length == 0)
        {
            tagok = false;
        }
        for(String s : tags)
        {
            if(s.isEmpty())
            {
                tagok=false;
            }
        }

        if(!tagok  || description.isEmpty() || titre.isEmpty()) {
            messageErreur = "Tous les champs doivent être remplis";
        }
        else
        {
            if(!addCategorie.isEmpty()) //Si la personne veut ajouter une catégorie
            {
                Categorie c = new Categorie();
                c.setLabel(addCategorie);
                if(CategorieManager.Exists(c));
            }

        /*
         * Les données reçues sont multipart, on doit donc utiliser la méthode
         * getPart() pour traiter le champ d'envoi de fichiers.
         */
            Part part = request.getPart( "fichier" );

        /*
         * Il faut déterminer s'il s'agit d'un champ classique
         * ou d'un champ de type fichier : on délègue cette opération
         * à la méthode utilitaire getNomFichier().
         */
            String nomFichier = getNomFichier( part );



        /*
         * Si la méthode a renvoyé quelque chose, il s'agit donc d'un champ
         * de type fichier (input type="file").
         */
            if ( nomFichier != null && !nomFichier.isEmpty() ) {
                String nomChamp = part.getName();
                nomFichier = nomFichier.substring( nomFichier.lastIndexOf( '/' ) + 1 )
                        .substring( nomFichier.lastIndexOf( '\\' ) + 1 );
                String contentType = getServletContext().getMimeType(nomFichier);

                // Check if file is actually an image (avoid download of other files by hackers!).
                // For all content types, see: http://www.w3schools.com/media/media_mimeref.asp
                if (contentType == null || !contentType.startsWith("image")) {
                    // Do your thing if the file appears not being a real image.
                    // Throw an exception, or send 404, or show default/warning image, or just ignore it.
                    messageErreur = "Le fichier choisi n'est pas une image";
                }
                else
                {

                    boolean enregistrer = true;

                    if(!addCategorie.isEmpty()) //Si la personne veut ajouter une catégorie
                    {
                        Categorie c = new Categorie();
                        c.setLabel(addCategorie);
                        if(CategorieManager.Exists(c))
                        {
                            enregistrer = false;
                        }
                        else
                        {
                            CategorieManager.Create(c); //On créé la nouvelle catégorie
                            Categorie categ = CategorieManager.GetByName(c.getLabel());
                            categorie = ""+categ.getId();

                        }
                    }



                    if(enregistrer)
                    {
                        modele.Image temp = new modele.Image();
                        temp.setImage("/Image/"+nomFichier);

                        if(ImageManager.Exists(temp))
                        {
                            messageErreur = "L'image existe déjà veuillez renommer le fichier";
                        }
                        else
                        {
                            //On enregistre le fichier

                            ecrireFichier(part,nomFichier,chemin);

                            //On l'enregistre dans la base de données
                            modele.Image image = new modele.Image();
                            image.setImage("/Image/"+nomFichier);
                            image.setDateajout(new Date(System.currentTimeMillis()));
                            image.setAuteur(currentUser.getId());
                            image.setTitre(titre);
                            image.setCategorie(Integer.parseInt(categorie));
                            image.setDescription(description);
                            modele.Image lastImage = ImageManager.save(image);



                            //Gestion des tags
                            int id_image_ajoutee = lastImage.getId();
                            for(String s : tags)
                            {
                                Tag t = new Tag();
                                t.setLabel(s);
                                int tag_id = TagManager.Create(t).getId(); //Renvoi l'id du nouveau tag ou de l'existant tavu
                                //On associe l'id de l'image a l'id du tag
                                Imagetag itag = new Imagetag();
                                itag.setImageid(id_image_ajoutee);
                                itag.setTagid(tag_id);
                                TagManager.AssociateToImage(itag);


                            }

                        }


                    }
                    else
                    {
                        messageErreur = "La catégorie existe déjà veuillez recommencer";
                    }
                }
            }
        }
        List categ = CategorieManager.getAll();
        request.setAttribute("categories",(List<Categorie>) categ);
        request.setAttribute("messageErreur", messageErreur);
        this.getServletContext().getRequestDispatcher( "/upload.jsp" ).forward(request, response); //On redurige vers la page d'upload
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List categ = CategorieManager.getAll();
        request.setAttribute("categories",(List<Categorie>) categ);
        this.getServletContext().getRequestDispatcher( "/upload.jsp" ).forward(request, response); //On redurige vers la page d'upload
    }

    private static String getNomFichier( Part part ) {
    /* Boucle sur chacun des paramètres de l'en-tête "content-disposition". */
        for ( String contentDisposition : part.getHeader( "content-disposition" ).split( ";" ) ) {
    	/* Recherche de l'éventuelle présence du paramètre "filename". */
            if ( contentDisposition.trim().startsWith("filename") ) {
            /* Si "filename" est présent, alors renvoi de sa valeur, c'est-à-dire du nom de fichier. */
                return contentDisposition.substring( contentDisposition.indexOf( '=' ) + 1 ).trim().replace( "\"", "" );
            }
        }
    /* Et pour terminer, si rien n'a été trouvé... */
        return null;
    }

    private boolean ecrireFichier( Part part, String nomFichier, String chemin ) throws IOException {
    /* Prépare les flux. */
        BufferedInputStream entree = null;
        BufferedOutputStream sortie = null;
        boolean ok = true;
        try {
        /* Ouvre les flux. */
            entree = new BufferedInputStream( part.getInputStream(), TAILLE_TAMPON );
            sortie = new BufferedOutputStream( new FileOutputStream( new File( chemin + nomFichier ) ),
                    TAILLE_TAMPON );

        /*
         * Lit le fichier reçu et écrit son contenu dans un fichier sur le
         * disque.
         */
            byte[] tampon = new byte[TAILLE_TAMPON];
            int longueur;
            while ( ( longueur = entree.read( tampon ) ) > 0 ) {
                sortie.write( tampon, 0, longueur );
            }
        }
        catch (Exception e)
        {
            ok = false;
        }
        finally {
            try {
                sortie.close();
            } catch ( IOException ignore ) {
            }
            try {
                entree.close();
            } catch ( IOException ignore ) {
            }
        }
        return ok;
    }
}
