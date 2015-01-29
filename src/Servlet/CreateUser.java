package Servlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Date;

import controleur.UserManager;
import modele.User;
import org.hibernate.*;
import org.hibernate.cfg.*;

/**
 * Created by Kylian on 18/01/2015.
 */
@WebServlet("/CreateUser")

//Permet de créer l'utilisateur en utilisant l'EJB UserManager

public class CreateUser extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String login = request.getParameter("userName");
        String pwd = request.getParameter("userPassword");
        String messageErrorLogin="";
        String messageErrorPwd="";
        String messageSuccess="";
        User u = new User();
        u.setLogin(login);
        u.setMdp(pwd);
        boolean formulaire_ok = true;

        if(login=="" && pwd=="")
        {
            messageErrorLogin="Ce champs est requis";
            messageErrorPwd="Ce champs est requis";
            formulaire_ok=false;
        }
        else
        {
            if(login == "")
            {
                messageErrorLogin="Ce champs est requis";
                formulaire_ok = false;
            }
            if(pwd == "")
            {
                messageErrorPwd="Ce champs est requis";
                formulaire_ok = false;
            }

        }

        if(formulaire_ok)
        {
            if(UserManager.Exists(u))
            {
                messageErrorLogin = "L'utilisateur "+login+" existe déjà.";
            }
            else
            {
                if(UserManager.Create(u))
                {
                    messageSuccess = "L'utilisateur "+login+" a été créé. Vous pouvez <a href='/Home'>vous connecter</a>";
                }
            }
        }


        request.setAttribute("messageErrorLogin",messageErrorLogin);
        request.setAttribute("messageErrorPwd",messageErrorPwd);
        request.setAttribute("messageSuccess",messageSuccess);
        this.getServletContext().getRequestDispatcher( "/createUser.jsp" ).forward(request, response); //On redurige vers la page de création de compte
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        this.getServletContext().getRequestDispatcher( "/createUser.jsp" ).forward(request, response); //On redurige vers la page de création de compte
    }
}
