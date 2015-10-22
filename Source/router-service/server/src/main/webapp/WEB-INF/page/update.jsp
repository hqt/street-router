<%--
  Created by IntelliJ IDEA.
  User: datnt
  Date: 10/19/2015
  Time: 8:29 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Demo Update Function</title>
</head>
<body>
    <form action="DispatcherServlet" method="post">
      <input type="text" value="1" name="id"/>
      <input type="text" value="Bến Thành - Phan Huy Thực" name="name"/>
      <button type="submit" name="action" value="update">Update</button>
    </form>
</body>
</html>
