<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="_shared/header.jsp" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<script src="${pageContext.request.contextPath}/js/geolocation.js" type="text/javascript"></script>
<!-- Google API Autocomplete for address-->
<script src="https://maps.googleapis.com/maps/api/js?v=3.exp&signed_in=true&libraries=places"></script>

<div id="wrapper">


  <%@ include file="_shared/navigation.jsp" %>
  <div id="page-wrapper">
    <div class="row">
      <div class="col-lg-12">
        <h1 class="page-header">
          ${submitted.customerCode}
        </h1>
      </div>
      <!-- /.col-lg-12 -->
    </div>
    <c:if test="${not empty validateErrors}">
      <div class="text-danger">
        <ul>
          <c:forEach var="error" items="${validateErrors}">
            <li>${error}</li>
          </c:forEach>
        </ul>
      </div>
    </c:if>
    <div class="row">
      <div class="col-lg-12">
        <form action="${pageContext.request.contextPath}/staff/customer"
              method="post" class="form-horizontal">
          <fieldset>
            <legend>Thông tin khách hàng
            </legend>
            <input type="hidden" name="customer:customerCode" value="${submitted.customerCode}">
            <!-- Customer name & Customer code -->
            <div class="form-group">
              <label class="col-sm-3 control-label">Tên khách hàng *</label>

              <div class="col-sm-9">
                <div class="text-value">
                  <input class="form-control input-md" type="text" name="customer:name" required value="${submitted.name}" pattern="\S[^0-9!@#$%^&*()+=~`]+"
                         minlength="3" maxlength="80">
                </div>
              </div>
            </div>

            <!-- Address -->
            <div class="form-group">
              <label class="col-sm-3 control-label">Địa chỉ *</label>

              <div class="col-sm-9">
                <div class="text-value">
                  <input class="form-control input-md" type="text" id="txtAddress" name="customer:address" onFocus="geolocate()" required value="${submitted.address}" maxlength="250" minlength="3">
                </div>
              </div>
            </div>

            <!-- Customer email -->
            <div class="form-group">
              <label class="col-sm-3 control-label">Email *</label>

              <div class="col-sm-9">
                <div class="text-value">
                  <input class="form-control input-md" type="text" name="customer:email" required value="${submitted.email}" pattern="[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,3}$">
                </div>
              </div>
            </div>

            <!-- Email & personal ID -->
            <div class="form-group">
              <label class="col-sm-3 control-label">Số điện thoại *</label>

              <div class="col-sm-3">
                <div class="text-value">
                  <input class="form-control input-md" type="text" name="customer:phone" required value="${submitted.phone}" pattern="[0-9]+"
                         minlength="8" maxlength="15">
                </div>
              </div>

              <label class="col-sm-3 control-label">Số CMND / Hộ chiếu </label>

              <div class="col-sm-3">
                <div class="text-value">
                  <input class="form-control input-md" type="text" name="customer:personalId" value="${submitted.personalId}" pattern="[0-9]+"
                         minlength="8" maxlength="15">
                </div>
              </div>
            </div>
          </fieldset>
          <%--/Customer information--%>
          <br/>

        <br/>
        <br/>
        <div class="pull-left">
          <a href="${pageContext.request.contextPath}/staff/customer?action=detail&code=${submitted.customerCode}" type="button" class="btn btn-default">
            <i class="fa fa-arrow-left"></i> <strong>Trở về</strong>
          </a>
        </div>
        <div class="text-center">
          <input type="hidden" name="action" value="editProfile">
          <button type="submit" class="btn btn-primary">
            Cập nhật
          </button>
        </div>
        <br/>
        <br/>
        </form>
      </div>
      <!-- col -->
    </div>
  </div>
</div>
<!-- /#wrapper -->

<%@ include file="_shared/footer.jsp" %>
<script language="JavaScript">
  initialize();
</script>