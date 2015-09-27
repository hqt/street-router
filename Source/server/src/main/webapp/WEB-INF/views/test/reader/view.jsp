<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: dinhquangtrung
  Date: 6/21/15
  Time: 11:43 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title></title>
</head>
<body>
<h1>Register</h1>
  <form action="${pageContext.request.contextPath}/testreader" method="post">
      <font color="red">${errors}</font><br/>
      <c:if test="${success}">
      <font color="blue">Đăng ký thành công!</font> <br/><br/>
      </c:if>
    Tài khoản:         <input type="text" name="register:username"/><br/>
    Mật khẩu:         <input type="password" name="register:password"/><br/>
    Xác nhận mật khẩu: <input type="password" name="register:confirmPassword"/>
      <input type="checkbox" name="register:remember" value="1"/>
      <br/>
    <input type="submit" value="Register"/>
    <input type="hidden" name="action" value="register"/>
  </form>
</body>
</html>
