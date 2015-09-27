<%--
  Created by IntelliJ IDEA.
  User: TriPQM
  Date: 07/18/2015
  Time: 8:45 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="_shared/header.jsp" %>

<div id="wrapper">

  <%@ include file="_shared/navigation.jsp" %>
  <div id="page-wrapper">
    <div class="row">
      <div class="col-lg-12">
        <h1 class="page-header">Quản lý loại hợp đồng</h1>
      </div>
      <!-- /.col-lg-12 -->
    </div>
    <c:if test="${param.info eq '1'}">
      <div class="text-success">
        Ngừng hoạt động thành công
      </div>
    </c:if>
    <c:if test="${param.info eq '0'}">
      <div class="text-danger">
        Ngừng hoạt động không thành công
      </div>
    </c:if>
    <c:if test="${param.info eq 'editSuccess'}">
      <div class="text-success">
        Cập nhật thông tin loại hợp đồng thành công
      </div>
    </c:if>
    <c:if test="${param.info eq 'fail'}">
      <div class="text-danger">
        Có lỗi xảy ra. Xin thử lại
      </div>
    </c:if>
    <div class="row">
      <div class="col-lg-12">
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
        <form action="${pageContext.request.contextPath}/admin/contractType"
              method="post" class="form-horizontal">
          <fieldset>
            <legend>Chi tiết loại hợp đồng</legend>
            <div>
              <label class="col-sm-4 control-label">Tên loại hợp đồng *</label>

              <div class="col-sm-7">
                <p class="text-value"><input name="contractType:name" type="text" class="form-control input-md" required
                                             placeholder="Ví dụ: Xe trên 50cc có BH cho người trên xe" value="${submitted.name}"></p>

              </div>
            </div>
            <div>
              <label class="col-sm-4 control-label">Miêu tả *</label>

              <div class="col-sm-7">
                <p class="text-value"><textarea name="contractType:description" cols="2" class="form-control input-md"
                                                placeholder="Ví dụ: Bảo hiểm cho xe trên 50cc và có BH cho người ngồi trên xe" required>${submitted.description}</textarea></p>

              </div>
            </div>
            <div>
              <label class="col-sm-4 control-label">Phí hằng năm (VNĐ) *</label>

              <div class="col-sm-7">
                <p class="text-value"><input name="contractType:pricePerYear" type="number" class="form-control input-md"
                                             required min="0" max="1000000000" placeholder="Ví dụ: 86000"

                                             value= "<fmt:formatNumber value="${submitted.pricePerYear}"
                                                                      type="number" groupingUsed="false" maxFractionDigits="0"/>"></p>

              </div>
            </div>

          </fieldset>
          <br>

          <div class="pull-left">

            <a href="${pageContext.request.contextPath}/admin/contractType" type="button"
               class="btn btn-default">
              <i class="fa fa-arrow-left"></i>
              Trở lại
            </a>
          </div>
          <div class="text-center">
            <input type="hidden" name="contractTypeId" value="${contractTypeId}">
            <input type="hidden" name="action" value="editContractType">
            <button type="submit" class="btn btn-primary">
              Cập nhật
            </button>

          </div>
        </form>


      </div>
    </div>
  </div>
  <!-- /#wrapper -->
  <jsp:include page="contract-type-modal.jsp" flush="true"/>
  <%@ include file="_shared/footer.jsp" %>

