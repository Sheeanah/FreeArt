<%--
  Created by IntelliJ IDEA.
  User: Dorian
  Date: 16/01/2015
  Time: 16:00
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%@include file="header.jsp" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>



<div class="container">
<c:forEach items="${ categories }" var="categ">
  <p class="well"><a href="/Categorie/${ categ.label }">${ categ.label }</a></p>
  <div class="row">
    <c:set var="cpt" value="0" scope="page"/>
    <c:forEach items="${ images }" var="item">
      <c:if test="${ (item.categorie == categ.id) && (cpt < 4)}">

        <div class="col-lg-3 col-md-4 col-xs-6 thumb">
          <div class="thumbnail">

              <img src="${pageContext.request.contextPath}${ item.image }" class = "img img-responsive" width="300" height="300"/>

            <div class="caption">
              <h3>${ item.titre }</h3>
              <p>${ item.description }</p>
              <p>Image ajoutée par :

            <c:forEach items="${ users }" var="auteur">
              <c:if test="${ item.auteur == auteur.id}">

                <a href="#" class="btn btn-primary" role="button">${ auteur.login }</a>

              </c:if>
            </c:forEach>

                <a href="#" class="btn btn-default" role="button">Les tags maggle</a></p>

            </div>
          </div>
        </div>
        <c:set var="cpt" value="${cpt + 1}" scope="page"/>
      </c:if>
    </c:forEach>
  </div>
</c:forEach>
</div>







</body>
</html>