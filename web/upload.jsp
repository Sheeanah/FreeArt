<%--
  Created by IntelliJ IDEA.
  User: Kylian
  Date: 20/01/2015
  Time: 17:48
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@include file="header.jsp" %>
<div class="container">
  <div class="row">
    <div class="col-md-offset-5 col-md-3">
      <form action="/Upload" method="post" enctype="multipart/form-data">
        <fieldset>
          <legend>Ajouter une image</legend>

          <label for="tag">Tag</label>
          <input type="text" id="tag" name="tag" value="" class="form-control"/>

          <label for="titre">Titre de l'image</label>
          <input type="text" id="titre" name="titre" value="" class="form-control"/>

          <label for="description">Description de l'image</label>
          <input type="text" id="description" name="description" value="" class="form-control"/>


          <label for="categorie">Catégorie de l'image</label>
          <select id="categorie" name="categorie" class="form-control">
            <c:forEach items="${ categories }" var="item">

            <option value="${ item.id }">${ item.label } </option>
            </c:forEach>
          <select>

          <label for="fichier">Emplacement du fichier</label>
          <input type="file" id="fichier" name="fichier"/>


          <input type="submit" value="Envoyer" class="btn btn-default"/>
          <br />
            <p>${ messageErreur }</p>
        </fieldset>
      </form>
      </div>
    </div>
  </div>

</body>
</html>