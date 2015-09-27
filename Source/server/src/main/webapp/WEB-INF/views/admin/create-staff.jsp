<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: TriPQM
  Date: 07/02/2015
  Time: 11:39 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="_shared/header.jsp" %>

<div id="wrapper">

  <%@ include file="_shared/navigation.jsp" %>
  <div id="page-wrapper">
    <div class="row">
      <div class="col-lg-12">
        <h1 class="page-header">Thêm nhân viên mới</h1>
      </div>
      <!-- /.col-lg-12 -->
    </div>
    <div class="row">
      <div class="col-lg-12">
        <p class="text-right"><b>Các ô có dấu * là bắt buộc</b></p>
        <br/>
        <c:if test="${not empty validateErrors}">
          <div class="text-danger">
            <ul>
              <c:forEach var="error" items="${validateErrors}">
                <li>${error}</li>
              </c:forEach>
            </ul>
          </div>
        </c:if>
        <form action="${pageContext.request.contextPath}/admin/staff"
              method="post" class="form-horizontal">
          <fieldset>
            <!-- Text input-->
            <%--<div class="form-group">--%>
            <%--<label class="col-sm-4 control-label" for="name">Tên đăng nhập *</label>--%>

            <%--<div class="col-sm-6">--%>
            <%--<input id="code" name="staff:staffCode" type="text" class="form-control input-md"--%>
            <%--type="text" required minlength="4" maxlength="10"--%>
            <%--pattern="[0-9a-zA-Z]+"--%>
            <%--value="${submitted.staffCode}" title="Vui lòng nhập mã nhân viên">--%>
            <%--</div>--%>
            <%--</div>--%>
            <!-- Text input-->
            <div class="form-group">
              <label class="col-sm-4 control-label" for="email">Email *</label>

              <div class="col-sm-6 ">
                <input id="email" name="staff:email" type="text" class="form-control input-md"
                       type="text" required minlength="3" maxlength="250"
                       pattern="^([a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,3})$"
                       value="${submitted.email}" title="Vui lòng nhập một email đúng"
                        >
              </div>
            </div>
            <!-- Text input-->
            <div class="form-group">
              <label class="col-sm-4 control-label" for="password">Mật khẩu*</label>

              <div class="col-sm-6">
                <input id="password" name="staff:password" class="form-control input-md"
                       type="password" required minlength="6" maxlength="80"
                       value="${submitted.password}" title="Vui lòng nhập mật khẩu cho nhân viên">

              </div>
              <div class="text-value">
                <input type="checkbox" id="showPass" value="false" onclick="{
                        if ( $('#password').attr('type') == 'password'){
                          $('#password').attr('type','text');
                        } else {
                          $('#password').attr('type','password');
                        }
                }">Hiện mật khẩu
              </div>
            </div>
            <!-- Text input-->
            <%--<div class="form-group">--%>
            <%--<label class="col-sm-4 control-label" for="passwordConfirm">Nhập lại mật khẩu*</label>--%>

            <%--<div class="col-sm-6">--%>
            <%--<input id="passwordConfirm" name="staff:passwordConfirm" type="password" class="form-control input-md">--%>
            <%--</div>--%>
            <%--</div>--%>
            <!-- Text input-->
            <div class="form-group">
              <label class="col-sm-4 control-label" for="name">Tên nhân viên *</label>

              <div class="col-sm-6">
                <input id="name" name="staff:name" type="text" class="form-control input-md"
                       type="text" required minlength="3" maxlength="80"
                       pattern="^([^0-9`~!@#$%^&*,.<>;':/|{}()=_+-]+)$"
                       value="${submitted.name}" title="Vui lòng nhập họ tên"
                        >
              </div>
            </div>

            <!-- Text input-->
            <div class="form-group">
              <label class="col-sm-4 control-label" for="phone">Số điện thoại*</label>

              <div class="col-sm-6">
                <input id="phone" name="staff:phone" type="text" class="form-control input-md"
                       type="tel" minlength="8" maxlength="15"
                       pattern="[0-9]+" title="Vui lòng chỉ nhập số"
                       value="${submitted.phone}">
              </div>
            </div>
          </fieldset>
          <div class="text-center">
            <input type="hidden" name="action" value="createStaff">
            <button type="submit" class="btn btn-success">
              <i class="fa fa-plus"></i>
              Thêm nhân viên
            </button>

          </div>
        </form>


      </div>
    </div>
    <!-- /#wrapper -->
</div>
    </div>


    <%@ include file="_shared/footer.jsp" %>
