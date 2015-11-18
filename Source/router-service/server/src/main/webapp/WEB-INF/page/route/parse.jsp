<%@ page import="java.io.File" %>
<%--
  Created by IntelliJ IDEA.
  User: datnt
  Date: 11/8/2015
  Time: 9:22 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title></title>
</head>
<body>
<form method="POST" action="" enctype="multipart/form-data">
  File:
  <input type="file" name="file" id="file"/> <br/>
  Destination:
  <input type="text" name="description"/>
  </br>
  <input type="submit" value="Upload" name="action" id="upload" />
</form>
</body>
</html>
