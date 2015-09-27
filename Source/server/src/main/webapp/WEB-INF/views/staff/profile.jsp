<%--
  Created by IntelliJ IDEA.
  User: TriPQM
  Date: 07/02/2015
  Time: 11:40 PM
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
                <h1 class="page-header">Thông tin nhân viên
                    <div class="pull-right">
                        <a href="${pageContext.request.contextPath}/staff/profile?action=viewEditProfile" class="btn btn-primary">
                            <i class="fa fa-edit"></i>
                            Sửa thông tin
                        </a>
                        <button type="button" class="btn btn-primary" data-toggle="modal" id="changePass"
                                data-target="#change-password-model">
                            Đổi mật khẩu
                        </button>
                    </div>
                </h1>
            </div>
            <!-- /.col-lg-12 -->
        </div>
        <c:if test="${param.info eq 'success'}">
            <div class="text-success">
                Sửa thông tin thành công
            </div>
        </c:if>
        <c:if test="${param.info eq 'fail'}">
            <div class="text-danger">
                Có lỗi xảy ra. Xin thử lại
            </div>
            </c:if>
        <c:if test="${param.info eq 'changePasswordSuccess'}">
            <div class="text-success">
                Đổi mật khẩu thành công
            </div>
        </c:if>
        <c:if test="${param.info eq 'changePasswordFail'}">
            <div class="text-danger">
                Đổi mật khẩu không thành công. Xin thử lại
            </div>
        </c:if>
        <c:if test="${not empty validateErrors}">

            <div class="text-danger ">
                <ul>
                    <c:forEach var="error" items="${validateErrors}">
                        <li>${error}</li>
                    </c:forEach>
                </ul>
            </div>

        </c:if>
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
                            <label class="col-sm-4 control-label" >Họ tên :</label>

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

                    <a href="${pageContext.request.contextPath}/staff" type="button"
                       class="btn btn-default">
                        <i class="fa fa-arrow-left"></i>
                        Trở về trang chủ
                    </a>
                </div>

            </div>
        </div>
        <form action="${pageContext.request.contextPath}/staff/profile" method="post">
            <div id="change-password-model" class="modal fade bs-example-modal-md" tabindex="-1" role="dialog"
                 aria-labelledby="myLargeModalLabel"
                 aria-hidden="true">
                <div class="modal-dialog modal-md">
                    <div class="modal-content">
                        <div class="modal-header">
                            <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span
                                    aria-hidden="true">&times;</span></button>
                            <h4 class="modal-title">
                                <label class="text-danger">Đổi mật khẩu</label></h4>

                        </div>
                        <div class="modal-body">
                            <div class="text-center alert alert-danger alert-dismissible hide" id="notify1"
                                 role="alert">
                                Mật khẩu mới không được trùng với mật khẩu hiện tại
                            </div>
                            <div class="text-center alert alert-danger alert-dismissible hide" id="notify2"
                                 role="alert">
                                Xác nhận mật khẩu không khớp với mật khẩu mới
                            </div>
                            <br/>

                            <div class="form-horizontal">
                                <div class="form-group">
                                    <label class="col-sm-5 control-label">Xác nhận mật khẩu hiện tại *</label>

                                    <div class="col-sm-5">
                                        <input type="password" class="form-control"
                                               required
                                               title="Nhập mật khẩu hiện tại"
                                               id="currentPass"
                                               name="newPass:currentPassword"
                                                >
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label class="col-sm-5 control-label">Mật khẩu mới *</label>

                                    <div class="col-sm-5">
                                        <input type="password" class="form-control"
                                               required
                                               title="Nhập mật khẩu mới"
                                               minlength="6"
                                               maxlength="32"
                                               id="newPass"
                                               name="newPass:newPassword"
                                                >
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label class="col-sm-5 control-label">Xác nhận mật khẩu mới *

                                    </label>

                                    <div class="col-sm-5">
                                        <input type="password" class="form-control"
                                               required
                                               title="Xác nhận lại mật khẩu"
                                               minlength="6"
                                               maxlength="32"
                                               id="confirmPass"
                                               name="newPass:confirmPassword"

                                                >
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="modal-footer">
                            <%--Post to server --%>
                            <input type="hidden" name="action" value="changePassword"/>
                            <%---------------------------------------%>
                            <input id="confirm" type="submit" class="btn btn-primary" name="Xác Nhận"
                                   value="Xác Nhận"/>
                            <input type="button" class="btn btn-danger" id="cancel" data-dismiss="modal"
                                   value="Hủy Bỏ"/>
                        </div>
                    </div>

                </div>
            </div>
        </form>
    </div>
</div>
<!-- /#wrapper -->


<%@ include file="_shared/footer.jsp" %>
<script language="javascript">
    $('#confirm').click(function () {
        var currentPass = $('#currentPass').val();
        var newPass = $('#newPass').val();
        var confirmPass = $('#confirmPass').val();
        $('#notify1').addClass('hide');
        $('#notify2').addClass('hide');
        if (currentPass != null && newPass != null && confirmPass != null) {
            if (confirmPass != newPass) {
                $('#notify2').removeClass('hide');
                return false;
            }
            if (currentPass == newPass) {
                $('#notify1').removeClass('hide');
                return false;
            }
        }
    });
</script>