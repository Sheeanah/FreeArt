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
  <p class="well">${ categ.label }</p>
  <div class="row">
    <c:forEach items="${ images }" var="item" end="4">
      <c:if test="${ item.categorie == categ.id}">

        <div class="col-lg-3 col-md-4 col-xs-6 thumb">
          <div class="thumbnail">

              <img src="${pageContext.request.contextPath}${ item.image }" class = "img img-responsive" width="300" height="300"/>

            <div class="caption">
              <h3>Thumbnail label</h3>

              <p><a href="#" class="btn btn-primary" role="button">Button</a> <a href="#" class="btn btn-default" role="button">Button</a></p>
            </div>
          </div>
        </div>

      </c:if>
    </c:forEach>
  </div>
</c:forEach>
</div>







</body>
</html>