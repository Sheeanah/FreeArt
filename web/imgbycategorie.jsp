<%--
  Created by IntelliJ IDEA.
  User: Kylian
  Date: 22/01/2015
  Time: 16:11
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
  <c:set var="pagination" value="0" scope="page"/>
<c:forEach items="${ images }" var="item">
  <c:set var="pagination" value="${pagination + 1}" scope="page"/>
  <c:if test="${  pagination >=   minInter  && pagination <=   maxInter  }">


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

        <p>
          <form method="post">
          <input type="hidden" value="${item.id}" name="image_id_panier" id="image_id_panier">
          <input type="submit" class="btn btn-default" role="button" value="Ajouter au panier">
         </form>

        </p>
      </div>
      </div>
    </div>

    <c:set var="cpt" value="${cpt + 1}" scope="page"/>

  <c:if test="${  cpt % 4 == 0 }">
    </div>
  </c:if>
    </c:if>
</c:forEach>
</div>
  <div class="text-center">
    <ul class="pagination pagination-lg">


      <c:choose>
        <c:when test="${currentPage == 1}">
          <li class="disabled">
            <a href="#" aria-label="Previous">
              <span aria-hidden="true">&laquo;</span>
            </a>
          </li>
        </c:when>
        <c:otherwise>
          <li>
            <a href="/Categorie/${currentCategorie}/${currentPage - 1}" aria-label="Previous">
              <span aria-hidden="true">&laquo;</span>
            </a>
          </li>
        </c:otherwise>
      </c:choose>

      <c:forEach begin="1" end="${maxPage}" var="i">
        <c:choose>
          <c:when test="${currentPage == i}">
            <li class="active"><a href="#">${i}</a></li>
          </c:when>
          <c:otherwise>
            <li><a href="/Categorie/${currentCategorie}/${i}">${i}</a></li>
          </c:otherwise>
        </c:choose>
      </c:forEach>
      <c:choose>
        <c:when test="${currentPage == maxPage}">
          <li class="disabled">
            <a href="#" aria-label="Next">
              <span aria-hidden="true">&raquo;</span>
            </a>
          </li>
        </c:when>
        <c:otherwise>
          <li>
            <a href="/Categorie/${currentCategorie}/${currentPage + 1}" aria-label="Next">
              <span aria-hidden="true">&raquo;</span>
            </a>
          </li>
        </c:otherwise>
      </c:choose>

    </ul>
  </div>

</div>
</body>
</html>
