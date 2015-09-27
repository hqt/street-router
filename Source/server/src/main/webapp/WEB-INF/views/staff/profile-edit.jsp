<%--
  Created by IntelliJ IDEA.
  User: TriPQM
  Date: 07/02/2015
  Time: 11:39 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="_shared/header.jsp" %>

<div id="wrapper">

  <%@ include file="_shared/navigation.jsp" %>
  <div id="page-wrapper">
    <div class="row">
      <div class="col-lg-12">
        <h1 class="page-header">Sửa thông tin cá nhân</h1>
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
        <form action="${pageContext.request.contextPath}/staff/profile"
              method="post" class="form-horizontal">
          <fieldset>
            <!-- Text input-->
            <div class="form-group">
              <label class="col-sm-4 control-label" >Mã nhân viên :</label>

              <div class="col-sm-6">
                <p class="text-value">
                <span class="label label-info"
                      style="font-size: 16px">${submitted.staffCode}</span>
                </p>
                <input type="hidden" name="staff:staffCode" value="${submitted.staffCode}">

              </div>
            </div>

            <div class="form-group">
              <label class="col-sm-4 control-label" for="name">Họ tên *</label>

              <div class="col-sm-6">
                <input id="name" name="staff:name" type="text" class="form-control input-md"
                       type="text" required minlength="3" maxlength="80"
                       pattern="^([^0-9`~!@#$%^&*,.<>;':/|{}()=_+-]+)$"
                       value="${submitted.name}" title="Vui lòng nhập họ tên"
                        >
              </div>
            </div>
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
              <label class="col-sm-4 control-label" for="phone">Số điện thoại*</label>

              <div class="col-sm-6">
                <input id="phone" name="staff:phone" type="text" class="form-control input-md"
                       type="tel" minlength="8" maxlength="15"
                       pattern="[0-9]+" title="Vui lòng chỉ nhập số"
                       value="${submitted.phone}">
              </div>
            </div>
          </fieldset>
          <div class="pull-left">

            <a href="${pageContext.request.contextPath}/staff/profile" type="button"
               class="btn btn-default">
              <i class="fa fa-arrow-left"></i>
              Trở về
            </a>
          </div>
          <div class="text-center">
            <input type="hidden" name="action" value="editProfile">
            <button type="submit" class="btn btn-primary">
              </i>
              Cập nhật
            </button>

          </div>
        </form>


      </div>
    </div>
    <!-- /#wrapper -->
  </div>
</div>


<%@ include file="_shared/footer.jsp" %>

