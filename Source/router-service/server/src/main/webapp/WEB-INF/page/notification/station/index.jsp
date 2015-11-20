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
  <c:import url="../../../common/library.jsp"/>
</head>
<body class="page-boxed page-header-fixed page-sidebar-closed-hide-logo page-container-bg-solid page-sidebar-closed-hide-logo">
<c:import url="../../../common/header.jsp"/>
<div class="container">
  <div class="page-container">

    <c:set var="activeStaNofItems" value="${requestScope.activeStaNofItems}" scope="request"/>
    <c:set var="blockStaNofItems" value="${requestScope.blockStaNofItems}" scope="request"/>

    <c:import url="../../../common/sidebar.jsp"/>
    <c:import url="content.jsp"/>
    <c:import url="../../../common/footer.jsp"/>
  </div>
</div>
<c:import url="../../../common/scripts.jsp"/>
</body>
</html>