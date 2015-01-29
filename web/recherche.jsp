<%--
  Created by IntelliJ IDEA.
  User: Kylian
  Date: 29/01/2015
  Time: 22:26
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="header.jsp" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<style>
  .img
  {
    width: 250px;
    height: 300px;
    background-size: auto 300px;
    background-position: center top;
    background-repeat: no-repeat;
    margin:auto;
    cursor: pointer;
  }



</style>

<div class="container">

  <c:set var="cpt" value="4" scope="page"/>

  <c:forEach items="${ images }" var="item">
      <c:if test="${  cpt % 4 == 0 }">
        <div class="row">
      </c:if>
      <div class="col-lg-3 col-md-4 col-xs-6 thumb">
        <div class="thumbnail">
          <div onclick="location.href='/ImageViewer/${item.id}';" class="img" style="background-image: url(${pageContext.request.contextPath}${ item.image });">

          </div>
          <div class="caption">
            <h3>${ item.titre }</h3>
            <p>${ item.description }</p>


            <c:forEach items="${ categoriesMenu }" var="categ">
              <c:if test="${ item.categorie == categ.id}">

                <p>Catégorie : <a href="/Categorie/${ categ.label }" class="btn btn-primary" role="button">${ categ.label }</a></p>

              </c:if>
            </c:forEach>
            <c:forEach items="${ users }" var="auteur">
              <c:if test="${ item.auteur == auteur.id}">

                <p>Image ajoutée par : <a href="/Auteur/${ auteur.login }" class="btn btn-primary" role="button">${ auteur.login }</a></p>

              </c:if>
            </c:forEach>
            <p>
              <c:forEach items="${ imagetag }" var="imagetag">
                <c:forEach items="${ tags }" var="tag">
                  <c:if test="${ (item.id == imagetag.imageid) && (tag.id == imagetag.tagid)  }">
                    <a href="/Tag/${tag.label}" class="btn btn-default" role="button"><span class="glyphicon glyphicon-tags"></span> ${tag.label}</a>
                  </c:if>
                </c:forEach>
              </c:forEach>
            </p>

          </div>
        </div>
      </div>

      <c:set var="cpt" value="${cpt + 1}" scope="page"/>

      <c:if test="${  cpt % 4 == 0 }">
        </div>
      </c:if>

  </c:forEach>
<c:if test="${  !empty vide }">
  <p class="well">${vide}</p>
</c:if>

</div>


</div>
</body>
</html>
