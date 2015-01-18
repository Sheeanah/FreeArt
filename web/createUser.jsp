<%--
  Created by IntelliJ IDEA.
  User: Kylian
  Date: 18/01/2015
  Time: 17:34
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@include file="header.jsp" %>
<STYLE type="text/css">
  .container {
    padding: 25px;

  }

  .form-login {
    background-color: #EDEDED;
    padding-top: 10px;
    padding-bottom: 20px;
    padding-left: 20px;
    padding-right: 20px;
    border-radius: 15px;
    border-color:#d2d2d2;
    border-width: 5px;
    box-shadow:0 1px 0 #cfcfcf;
  }

  h4 {
    border:0 solid #fff;
    border-bottom-width:1px;
    padding-bottom:10px;
    text-align: center;
  }

  .form-control {
    border-radius: 10px;
  }

  .error{
    color:red;
  }
  .sucess
  {
    color:green;
  }
</STYLE>
<div class="container">
  <div class="row">
    <div class="col-md-offset-5 col-md-3">
      <div class="form-login">
        <form method="post" action="/CreateUser">
        <h4>Créer un compte</h4>
        <input type="text" name="userName" class="form-control input-sm" placeholder="Login" />
          <p class="error">${ messageErrorLogin }</p>
        </br>
        <input type="text" name="userPassword" class="form-control input-sm" placeholder="Mot de passe" />
          <p class="error">${ messageErrorPwd }</p>
        </br>
        <div class="wrapper">
            <span class="group-btn">
               <input type="submit" value="Valider" class="btn btn-primary btn-block">
            </span>
        </div>
          <p class="sucess">${ messageSuccess }</p>
        </form>

      </div>

    </div>
  </div>
</div>


</body>
</html>
