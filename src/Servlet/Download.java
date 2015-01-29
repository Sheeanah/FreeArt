package Servlet;

import controleur.ImageManager;
import modele.*;
import modele.Image;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * Created by Kylian on 27/01/2015.
 */
@WebServlet("/Download")
public class Download extends HttpServlet {

    private  String OUTPUT_ZIP_FILE = "C:\\fichiers\\Zip";
    private static final String SOURCE_FOLDER = "C:\\fichiers";
    List<String> imageList;
    private static final int TAILLE_TAMPON = 10240;


    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String cookie = getCookieValue(request,"Panier");
        if(cookie==null || cookie=="")
        {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
            return;
        }
        List<modele.Image> images = new ArrayList<modele.Image>();
        for(String s : cookie.split("/")) {
            if(ImageManager.GetById(Integer.parseInt(s))!=null)
            {
                images.add(ImageManager.GetById(Integer.parseInt(s)));
            }

        }
        imageList = new ArrayList<String>();

        //On génère notre liste de fichier a zipper
        getZipName();
        generateFileList(images);

        zipIt(OUTPUT_ZIP_FILE);

        File fichier = new File(OUTPUT_ZIP_FILE);
        if(fichier.exists())
        {
            //Le zip que l'on vient de faire existe
            String type = getServletContext().getMimeType( fichier.getName() );

            /* Si le type de fichier est inconnu, alors on initialise un type par défaut */
            if ( type == null ) {
                type = "application/octet-stream";
            }

            /* Initialise la réponse HTTP */
            response.reset();
            response.setBufferSize( TAILLE_TAMPON );
            response.setContentType( type );
            response.setHeader( "Content-Length", String.valueOf( fichier.length() ) );
            response.setHeader( "Content-Disposition", "attachment; filename=\"" + fichier.getName() + "\"" );
            /* Prépare les flux */
            BufferedInputStream entree = null;
            BufferedOutputStream sortie = null;
            try {
            /* Ouvre les flux */
                entree = new BufferedInputStream( new FileInputStream( fichier ), TAILLE_TAMPON );
                sortie = new BufferedOutputStream( response.getOutputStream(), TAILLE_TAMPON );

            /* Lit le fichier et écrit son contenu dans la réponse HTTP */
                byte[] tampon = new byte[TAILLE_TAMPON];
                int longueur;
                while ( ( longueur= entree.read( tampon ) ) > 0 ) {
                    sortie.write( tampon, 0, longueur );
                }
            } finally {
                try {
                    sortie.close();
                } catch ( IOException ignore ) {
                }
                try {
                    entree.close();
                } catch ( IOException ignore ) {
                }
            }
        }

        response.getWriter().println("FICHIER PONDU : "+OUTPUT_ZIP_FILE);


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

    public void zipIt(String zipFile){

        byte[] buffer = new byte[1024];

        try{

            FileOutputStream fos = new FileOutputStream(zipFile);
            ZipOutputStream zos = new ZipOutputStream(fos);

            System.out.println("Output to Zip : " + zipFile);

            for(String file : this.imageList){

                System.out.println("File Added : " + file);
                ZipEntry ze= new ZipEntry(file);
                zos.putNextEntry(ze);

                FileInputStream in =
                        new FileInputStream(SOURCE_FOLDER + File.separator + file);

                int len;
                while ((len = in.read(buffer)) > 0) {
                    zos.write(buffer, 0, len);
                }

                in.close();
            }

            zos.closeEntry();
            //remember close it
            zos.close();

            System.out.println("Done");
        }catch(IOException ex){
            ex.printStackTrace();
        }
    }

    public void generateFileList(List<modele.Image> images){
        File node;
        for(modele.Image i : images)
        {

            String path = SOURCE_FOLDER + "\\" +i.getImage().split("/")[2];
            node=new File(path);
            //add file only
            if(node.isFile()){
                imageList.add(generateZipEntry(node.getAbsoluteFile().toString()));
            }
        }
    }



    /**
     Permet de faire de formatter le nom du fichier
     */
    private String generateZipEntry(String file){
        return file.substring(SOURCE_FOLDER.length()+1, file.length());
    }

        //Récupère le nom du zip

    private void getZipName()
    {
        OUTPUT_ZIP_FILE = "C:\\fichiers\\Zip";
        OUTPUT_ZIP_FILE = OUTPUT_ZIP_FILE + "\\";
        String fileName = "ZIP_1.zip";
        File f = new File(OUTPUT_ZIP_FILE+fileName);
        int i = 1;

        while(f.isFile())
        {

            i++;
            fileName = "ZIP_"+i+".zip";
            f = new File(OUTPUT_ZIP_FILE+fileName);

        }

        OUTPUT_ZIP_FILE = OUTPUT_ZIP_FILE+fileName;



    }

}
