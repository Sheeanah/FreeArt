<%--
  Created by IntelliJ IDEA.
  User: Kylian
  Date: 23/01/2015
  Time: 12:06
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="header.jsp" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<div class="container">
<div class="row">
  <div class="col-sm-4">
    <p class="well">Titre : ${image.titre}</p>
    <p>Desciption : ${image.description} </p>
    <p>Catégorie : <a class="btn btn-primary" href="/Categorie/${categorie.label}">${categorie.label}</a></p>
    <p>Auteur : <a class="btn btn-primary" href="/Auteur/${auteur.login}">${auteur.login}</a></p>
    <p>
      <c:forEach items="${ imagetag }" var="imagetag">
        <c:forEach items="${ tags }" var="tag">
          <c:if test="${ (image.id == imagetag.imageid) && (tag.id == imagetag.tagid)  }">
            <a href="/Tag/${tag.label}" class="btn btn-default" role="button"><span class="glyphicon glyphicon-tags"></span> ${tag.label}</a>
          </c:if>
        </c:forEach>
      </c:forEach>
    </p>
    <p>
    <form method="post">
      <input type="hidden" value="${image.id}" name="image_id_panier" id="image_id_panier">
      <input type="submit" class="btn btn-default btn-block" role="button" value="Ajouter au panier">
    </form>

    </p>
  </div>
  <div class="col-sm-8">
    <a class="thumbnail"><img class="img-responsive" src="${image.image}"></a>
    
  </div>
</div>
</div>
</body>
</html>
