<%--
  Created by IntelliJ IDEA.
  User: datnt
  Date: 10/17/2015
  Time: 5:05 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html>
<head>
  <c:import url="../../common/library.jsp"/>

  <style>
    a {color: #333;}
    a:hover {
      color: #3175af;
    }
    a:focus, a:hover, a:active {
      outline: 0;
    }
  </style>
</head>
<body class="page-boxed page-header-fixed page-sidebar-closed-hide-logo page-container-bg-solid page-sidebar-closed-hide-logo">
<c:import url="../../common/header.jsp"/>
<div class="container">
  <div class="page-container">
    <c:set var="routes" scope="request" value="${requestScope.routes}"/>
    <c:import url="../../common/sidebar.jsp"/>
    <c:import url="add.jsp"/>
    <c:import url="../../common/footer.jsp"/>
  </div>
</div>
</body>
<c:import url="../../common/scripts.jsp"/>
</html>
