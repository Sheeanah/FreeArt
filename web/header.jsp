<%--
  Created by IntelliJ IDEA.
  User: Kylian
  Date: 18/01/2015
  Time: 15:59
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
  <title>FreeArt</title>
  <script src="http://ajax.googleapis.com/ajax/libs/jquery/1.11.1/jquery.min.js"></script>
  <!-- Latest compiled and minified CSS -->
  <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.1/css/bootstrap.min.css">

  <!-- Optional theme -->
  <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.1/css/bootstrap-theme.min.css">

  <!-- Latest compiled and minified JavaScript -->
  <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.1/js/bootstrap.min.js"></script>

</head>
<body>
<nav class="navbar navbar-default">
  <div class="container-fluid">
    <!-- Brand and toggle get grouped for better mobile display -->
    <div class="navbar-header">
      <button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#bs-example-navbar-collapse-1">
        <span class="sr-only">Toggle navigation</span>
        <span class="icon-bar"></span>
        <span class="icon-bar"></span>
        <span class="icon-bar"></span>
      </button>
      <a class="navbar-brand" href="/Home">Free Art</a>
    </div>

    <!-- Collect the nav links, forms, and other content for toggling -->
    <div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
      <ul class="nav navbar-nav">
        <li><a href="/Home"><span class="glyphicon glyphicon-home"></span> Accueil</a></li>
        <c:choose>
          <c:when test="${!empty sessionScope.User}">
            <li><a href="/Upload"><span class="glyphicon glyphicon-upload"></span> Ajouter une image</a></li>
          </c:when>
          <c:otherwise>
            <li><a href="/CreateUser"><span class="glyphicon glyphicon-plus"></span> Créer un compte</a></li>
          </c:otherwise>
        </c:choose>




        <li class="dropdown">
          <a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-expanded="false"><span class="glyphicon glyphicon-th"></span> Catégories <span class="caret"></span></a>
          <ul class="dropdown-menu" role="menu">

            <c:forEach items="${ categoriesMenu }" var="categorie">
              <li><a href="#">${ categorie.label }</a></li>
            </c:forEach>

          </ul>
        </li>

      </ul>
      <c:choose>
        <c:when test="${empty sessionScope.User}">
          <form class="navbar-form navbar-left" method="post" action="/Home">
            <div class="form-group">
              <div class="input-group">
                <span class="input-group-addon"><span class="glyphicon glyphicon-user"></span></span>
                <input type="text" class="form-control" placeholder="Login" name="userName">
              </div>
            </div>
            <div class="form-group">
              <div class="input-group">
                <span class="input-group-addon"><span class="glyphicon glyphicon-lock"></span></span>
                <input type="password" class="form-control" placeholder="Mot de passe" name="userPassword">
              </div>
            </div>
            <button type="submit" class="btn btn-default"><span class="glyphicon glyphicon-search"></span> Se connecter</button>
          </form>
        </c:when>
        <c:otherwise>
      <form class="navbar-form navbar-left" method="post" action="/Deconnect">
        <button type="submit" class="btn btn-danger"><span class="glyphicon glyphicon-off"></span> ${sessionScope.User.login} (se deconnecter) </button>
      </form>
        </c:otherwise>
      </c:choose>

      <form class="navbar-form navbar-right" role="search" method="post" action="/Recherche/search.jsp">
        <div class="form-group">
          <input type="text" class="form-control" placeholder="Rechercher">
        </div>
        <button type="submit" class="btn btn-default"><span class="glyphicon glyphicon-search"></span> Rechercher</button>
      </form>
    </div><!-- /.navbar-collapse -->
  </div><!-- /.container-fluid -->
</nav>
