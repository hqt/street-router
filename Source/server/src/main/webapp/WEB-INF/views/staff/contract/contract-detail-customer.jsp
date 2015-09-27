<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<fieldset>
    <legend>Thông tin khách hàng
        <div class="pull-right" style="margin-top: -5px;">
            <a href="${pageContext.request.contextPath}/staff/customer?action=viewEditProfile&customerCode=${customer.customerCode}"
               type="button" class="btn btn-xs btn-primary editBtn">
                <i class="fa fa-pencil"></i> Chỉnh sửa
            </a>
        </div>
    </legend>

    <!-- Customer name & Customer code -->
    <div class="form-group">
        <label class="col-sm-3 control-label">Tên khách hàng</label>

        <div class="col-sm-8">
            <div class="text-value">
                <a href="${pageContext.request.contextPath}/staff/customer?action=detail&code=${customer.customerCode}">
                    <strong>${customer.name}</strong>
                </a>
            </div>
        </div>
    </div>

    <!-- Address -->
    <div class="form-group">
        <label class="col-sm-3 control-label">Địa chỉ</label>

        <div class="col-sm-8">
            <div class="text-value">${customer.address}</div>
        </div>
    </div>

    <!-- Customer email -->
    <div class="form-group">
        <label class="col-sm-3 control-label">Email</label>

        <div class="col-sm-8">
            <div class="text-value">${customer.email}</div>
        </div>
    </div>

    <!-- Email & personal ID -->
    <div class="form-group">
        <label class="col-sm-3 control-label">Số điện thoại</label>

        <div class="col-sm-2">
            <div class="text-value">${customer.phone}</div>
        </div>

        <label class="col-sm-3 control-label">Số CMND / Hộ chiếu</label>

        <div class="col-sm-3">
            <div class="text-value">
                <c:choose>
                    <c:when test="${empty customer.personalId}">
                        <span class="empty-value">Không có</span>
                    </c:when>
                    <c:otherwise>${customer.personalId}</c:otherwise>
                </c:choose>
            </div>
        </div>
    </div>
</fieldset>