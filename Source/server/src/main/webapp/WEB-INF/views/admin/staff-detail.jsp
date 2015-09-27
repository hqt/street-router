<%--
  Created by IntelliJ IDEA.
  User: TriPQM
  Date: 07/02/2015
  Time: 11:40 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="_shared/header.jsp" %>

<div id="wrapper">

  <%@ include file="_shared/navigation.jsp" %>
  <div id="page-wrapper">
    <div class="row">
      <div class="col-lg-12">
        <h1 class="page-header">Thông tin nhân viên
          <div class="pull-right">
            <a href="${pageContext.request.contextPath}/admin/staff?action=deleteStaff&code=${param.code}" class="btn btn-danger">
              <i class="fa fa-trash"></i>
              Xóa nhân viên này
            </a>
          </div>
        </h1>
      </div>
      <!-- /.col-lg-12 -->
    </div>
    <div class="row">
      <div class="col-lg-12">
        <br/>

        <form class="form-horizontal">
          <fieldset>
            <!-- Text input-->
            <div class="form-group">
              <label class="col-sm-4 control-label" >Mã nhân viên :</label>

              <div class="col-sm-6">
              <p class="text-value">
                <span class="label label-info"
                      style="font-size: 16px">${staff.staffCode}</span>
              </p>

              </div>
            </div>
            <!-- Text input-->
            <div class="form-group">
              <label class="col-sm-4 control-label" >Tên nhân viên :</label>

              <div class="col-sm-6">
                <p class="text-value">
                  ${staff.name}
                </p>
              </div>
            </div>

            <!-- Text input-->
            <div class="form-group">
              <label class="col-sm-4 control-label" >Email :</label>

              <div class="col-sm-6">
                <p class="text-value">
                  ${staff.email}
                </p>
              </div>
            </div>

            <!-- Text input-->
            <div class="form-group">
              <label class="col-sm-4 control-label" >Số điện thoại :</label>

              <div class="col-sm-6">
                <p class="text-value">
                  ${staff.phone}
                </p>
              </div>
            </div>
          </fieldset>
        </form>

        <div class="text-left">

          <a href="${pageContext.request.contextPath}/admin/staff" type="button"
             class="btn btn-default">
            <i class="fa fa-arrow-left"></i>
            Trở lại
          </a>
        </div>

      </div>
    </div>
  </div>
</div>
<!-- /#wrapper -->


<%@ include file="_shared/footer.jsp" %>
