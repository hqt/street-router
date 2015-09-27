<%--
  Created by IntelliJ IDEA.
  User: dinhquangtrung
  Date: 5/23/15
  Time: 11:45 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title></title>
</head>
<body>
  <form action="/testcontroller" method="post">
    <input type="text" name="username" value=""/>
    <input type="text" name="password" value=""/>
    <input type="hidden" name="action" value="create"/>
    <input type="submit" value="Submit"/>
  </form>
</body>
</html>
