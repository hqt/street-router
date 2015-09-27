<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="_shared/header.jsp"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<div id="wrapper">

    <%@ include file="_shared/navigation.jsp" %>
    <div id="page-wrapper">
        <div class="row">
            <div class="col-lg-12">
                <h1 class="page-header">Thêm khách hàng mới</h1>
            </div>
        </div>
        <div class="row">
            <div class="col-lg-12">
                <div class="well text-success text-center bs-example">
                    <i class="fa fa-check"></i>
                    Đã thêm khách hàng mới thành công!
                </div>

                <c:set var="info" value="${requestScope.CUSTOMER}"/>

                <c:if test="${not empty info}">
                    <form class="form-horizontal">
                        <fieldset>
                            <!-- Customer code & Customer name -->
                            <div class="form-group">
                                <label class="col-sm-3 control-label">Mã khách hàng</label>

                                <div class="col-sm-2">
                                    <div class="text-value text-primary">
                                        <a href="${pageContext.request.contextPath}/staff/customer?action=detail&code=${info.customerEntity.customerCode}">
                                                <strong>${info.customerEntity.customerCode}</strong>
                                        </a>
                                    </div>
                                </div>

                                <label class="col-sm-3 control-label">Tên khách hàng</label>

                                <div class="col-sm-4">
                                    <div class="text-value">
                                            ${info.customerEntity.name}
                                    </div>
                                </div>
                            </div>

                            <!-- Address -->
                            <div class="form-group">
                                <label class="col-sm-3 control-label">Địa chỉ</label>

                                <div class="col-sm-8">
                                    <div class="text-value">
                                        ${info.customerEntity.address}
                                    </div>
                                </div>
                            </div>

                            <!-- Email -->
                            <div class="form-group">
                                <label class="col-sm-3 control-label">Email</label>

                                <div class="col-sm-8">
                                    <div class="text-value">
                                        ${info.customerEntity.email}
                                    </div>
                                </div>
                            </div>

                            <!-- Password -->
                            <div class="form-group">
                                <label class="col-sm-3 control-label">Mật khẩu</label>

                                <div class="col-sm-8">
                                    <div class="text-value">
                                        <c:if test="${info.emailSuccess}">
                                            <span class="label label-success"><i class='fa fa-check'></i> Đã gởi</span>
                                            Kiểm tra email ${info.customerEntity.email}
                                        </c:if>
                                        <c:if test="${not info.emailSuccess}">
                                            <span class="label label-warning"><i class='fa fa-times'></i> Chưa gởi</span>
                                            Mật khẩu chưa được gởi
                                        </c:if>

                                        <button type="button" data-customer-code="${info.customerEntity.customerCode}" id="btnResendPassword" class="btn btn-xs btn-primary">
                                            <i class="fa fa-refresh"></i>
                                            Gửi lại email
                                        </button>
                                    </div>
                                </div>
                            </div>

                            <!-- Phone & Personal ID -->
                            <div class="form-group">
                                <label class="col-sm-3 control-label">Số điện thoại</label>

                                <div class="col-sm-2">
                                    <div class="text-value">
                                        ${info.customerEntity.phone}
                                    </div>
                                </div>

                                <label class="col-sm-3 control-label">Số CMND / Hộ chiếu</label>

                                <div class="col-sm-2">
                                    <div class="text-value">
                                        <c:choose>
                                            <c:when test="${empty info.customerEntity.personalId}">
                                                <span class="empty-value">Không có</span>
                                            </c:when>
                                            <c:otherwise>
                                                ${info.customerEntity.personalId}
                                            </c:otherwise>
                                        </c:choose>
                                    </div>
                                </div>
                            </div>
                        </fieldset>
                    </form>
                </c:if>

                <div class="text-center">
                    <a href="${pageContext.request.contextPath}/staff/contract?action=create&code=${info.customerEntity.customerCode}"
                       type="button" class="btn btn-default">
                        <i class="fa fa-arrow-right"></i> <strong>Thêm hợp đồng cho khách hàng này</strong>
                    </a>
                    <br/><br/>
                    <a href="${pageContext.request.contextPath}/staff/customer" type="button" class="btn btn-default">
                        <i class="fa fa-arrow-left"></i> <strong>Danh sách khách hàng</strong>
                    </a>
                </div>

                <br/>

                <div class="panel panel-success">
                    <div class="panel-heading">
                        Hướng dẫn cách phát hành thẻ cho khách hàng
                    </div>
                    <div class="panel-body">
                        Để cấp thẻ cho khách hàng, nhân viên cần sử dụng <b>Ứng dụng in thẻ</b> trên điện thoại để in
                        thông tin khách hàng lên thẻ.
                        <ol>
                            <li>Tìm kiếm thông tin khách hàng trên ứng dụng.</li>
                            <li>Kiểm tra chính xác thông tin khác hàng.</li>
                            <li>In thông tin ra thẻ và chuyển phát cho khách hàng.</li>
                        </ol>
                    </div>
                </div>

            </div>
        </div>
    </div>
</div>
<!-- /#wrapper -->
<script src="/js/resend-email.js"></script>
<%@ include file="_shared/footer.jsp"%>