<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="_shared/header.jsp"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<div id="wrapper">

  <%@ include file="_shared/navigation.jsp" %>
  <div id="page-wrapper">
    <form action="${pageContext.request.contextPath}/admin/staff"
          method="get" class="form-horizontal">
    <div class="row">
      <div class="col-lg-12">
        <h1 class="page-header">
          Danh sách nhân viên
          <div class="pull-right">
              <input type="hidden" name="action" value="viewCreateStaff">
              <button type="submit" class="btn btn-success">
                <i class="fa fa-plus"></i>
                Thêm nhân viên
              </button>
            </a>
          </div>
        </h1>
      </div>
      <!-- /.col-lg-12 -->
    </div>
    <!-- /.row -->

    <div>
        <div class="text-info">
          ${message}
        </div>
    </div>
    <div class="panel panel-default">
      <div class="panel-heading">
        <div class="pull-left center-dropdown-button">
          <b>Có ${staffPaginator.itemSize} nhân viên</b>
        </div>
        <div class="pull-right no-wrap">
          <input type="text" class="form-control long-text-box"
                 placeholder="Tìm kiếm theo tên, mã nhân viên, email"/>
          <input type="button" class="btn btn-default" value="Tìm kiếm"/>

        </div>
        <div class="clearfix"></div>
      </div>
      <!-- /.panel-heading -->
      <div class="panel-body">
        <div class="table-responsive">
          <table class="table table-hover table-striped">
            <thead>
            <tr>
              <th>#</th>
              <th>Mã nhân viên</th>
              <th>Tên nhân viên</th>
              <th>Email</th>
              <th>Số điện thoại</th>
            </tr>
            </thead>
            <tbody>
            <c:set var="staffs" value="${staffPaginator.getItemsOnCurrentPage(param.page)}"/>
            <c:choose>
              <c:when test="${staffs.size() == 0}">
                <tr>
                  <td colspan="6" style="vertical-align: middle; text-align: center;">
                    Không có nhân viên nào
                  </td>
                </tr>
              </c:when>
              <c:otherwise>
                <c:forEach var="staff" items="${staffs}" varStatus="counter">
                  <tr>
                    <td>${(staffPaginator.getCurrentPage(param.page) - 1) * staffPaginator.itemPerPage + counter.count}</td>
                    <td>
                      <a href="${pageContext.request.contextPath}/admin/staff?action=staffDetail&code=${staff.staffCode}">
                          ${staff.staffCode}
                      </a>
                    </td>
                    <td>
                      ${staff.name}
                    </td>
                    <td>
                        ${staff.email}
                    </td>
                    <td>
                        ${staff.phone}
                    </td>
                  </tr>
                </c:forEach>
              </c:otherwise>
            </c:choose>
            </tbody>
          </table>
        </div>
        <!-- /.table-responsive -->
        <nav class="text-right">
          <ul class="pagination">
            <c:if test="${param.page != 1 && not empty param.page}">
              <li>
                <a href="?action=${param.action}&keyword=${param.keyword}&page=1" aria-label="Previous">
                  <span aria-hidden="true">Đầu</span>
                </a>
              </li>
            </c:if>
            <c:forEach begin="1" end="${staffPaginator.pageSize}" var="pageNumber">
              <li ${param.page == pageNumber ||(pageNumber == 1 && empty param.page) ? "class='active'": ""} >
                <a href="?action=${param.action}&keyword=${param.keyword}&page=${pageNumber}">${pageNumber}</a>
              </li>
            </c:forEach>
            <c:if test="${param.page != staffPaginator.pageSize && staffPaginator.pageSize != 1}">
              <li>
                <a href="?action=${param.action}&keyword=${param.keyword}&page=${staffPaginator.pageSize}"
                   aria-label="Next">
                  <span aria-hidden="true">Cuối</span>
                </a>
              </li>
            </c:if>
          </ul>
        </nav>
      </div>
      <!-- /.panel-body -->
    </div>
    <!-- /.panel -->
      </form>
  </div>
</div>
<!-- /#wrapper -->



<%@ include file="_shared/footer.jsp"%>