<%--
  Created by IntelliJ IDEA.
  User: dinhquangtrung
  Date: 5/23/15
  Time: 9:59 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title></title>
</head>
<body>
This is the JspPage result of testcontroller/getTest method (send by GET)
<form action="testcontroller" method="POST">
    <input type="hidden" name="action" value="test"/>
    <input type="submit" value="Send POST request to testcontroller"/>
</form>
</body>
</html>
