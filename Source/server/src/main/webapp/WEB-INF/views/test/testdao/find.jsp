<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: dinhquangtrung
  Date: 6/2/15
  Time: 8:59 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title></title>
</head>
<body>
<form action="${pageContext.request.contextPath}/testdao">
<label>Keyword: <input type="text" name="keyword" value="${param.keyword}"/></label>
    <input type="submit" value="Search"/>
    <input type="hidden" name="action" value="findStaff"/>
</form>
Found ${requestScope.findResults.size()} result(s):<br/>
<ul>
<c:forEach items="${requestScope.findResults}" var="staff">
  <li>${staff.staffCode}</li>
</c:forEach>
</ul>
</body>
</html>
