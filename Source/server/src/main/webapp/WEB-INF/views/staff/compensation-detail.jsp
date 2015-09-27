<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="_shared/header.jsp" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<div id="wrapper">

    <c:set var="compensation" value="${requestScope.COMPENSATION}"/>

    <%@ include file="_shared/navigation.jsp" %>
    <div id="page-wrapper">
        <div class="row">
            <div class="col-lg-12">
                <h1 class="page-header">
                    ${compensation.compensationCode}
                    <div class="pull-right">
                        <a href="${pageContext.request.contextPath}/staff/compensation?action=edit&code=${compensation.compensationCode}"
                           type="button" class="btn btn-primary">
                            <i class="fa fa-pencil"></i> Chỉnh sửa yêu cầu
                        </a>
                    </div>
                </h1>
            </div>
            <!-- /.col-lg-12 -->
        </div>

        <div class="row">
            <div class="col-lg-12">

                <c:if test="${not empty validateErrors}">
                    <div class="text-danger">
                        <ul>
                            <c:forEach var="error" items="${validateErrors}">
                                <li>${error}</li>
                            </c:forEach>
                        </ul>
                    </div>
                </c:if>

                <form class="form-horizontal">
                    <c:if test="${empty compensation.resolveDate}">
                        <div class="alert alert-info">
                            <p class="bs-example text-center text-uppercase">
                                Yêu cầu bồi thường này chưa được giải quyết
                            </p>
                            <br/>

                            <p class="text-center">
                                <button class="btn btn-primary" type="button" data-toggle="modal"
                                        data-target="#resolve-compensation-modal">
                                    <i class="fa fa-check"></i> Giải quyết yêu cầu
                                </button>
                            </p>
                        </div>
                    </c:if>

                    <fieldset>
                        <legend>Thông tin hợp đồng</legend>

                        <div class="form-group">
                            <label class="col-sm-4 control-label">Mã hợp đồng</label>

                            <div class="col-sm-2">
                                <div class="text-value">
                                    <a href="${pageContext.request.contextPath}/staff/contract?action=detail&code=${compensation.contractCode}">
                                        <strong>${compensation.contractCode}</strong>
                                    </a>
                                </div>
                            </div>
                        </div>

                        <div class="form-group">
                            <label class="col-sm-4 control-label">Tên khách hàng</label>

                            <div class="col-sm-4">
                                <div class="text-value">
                                    <a href="${pageContext.request.contextPath}/staff/customer?action=detail&code=${compensation.micContractByContractCode.micCustomerByCustomerCode.customerCode}">
                                        <b>${compensation.micContractByContractCode.micCustomerByCustomerCode.name}</b>
                                    </a>
                                </div>
                            </div>
                        </div>
                    </fieldset>
                    <%--/Contract information--%>
                    <br/>
                    <fieldset>
                        <legend>
                            Thông tin yêu cầu bồi thường

                            <div class="pull-right" style="margin-top: -7px;">
                                <c:choose>
                                    <c:when test="${empty compensation.resolveDate}">
                                        <span class="label label-default">Chưa giải quyết</span>
                                    </c:when>
                                    <c:otherwise>
                                        <span class="label label-success">Đã giải quyết</span>
                                    </c:otherwise>
                                </c:choose>
                            </div>
                        </legend>

                        <c:choose>
                            <c:when test="${not empty compensation.attachment}">
                                <a target="_blank" href="${compensation.attachment}">
                                    Xem chi tiết <i class="fa fa-external-link"></i>
                                </a>
                                <div>
                                    <img src="${compensation.attachment}" style="width: 100%"/>
                                </div>
                            </c:when>
                            <c:otherwise>
                                <div class="form-group">
                                    <label class="col-sm-4 control-label">Ngày gởi yêu cầu</label>

                                    <div class="col-sm-4">
                                        <div class="text-value">
                                            ngày <fmt:formatDate value="${compensation.createdDate}" pattern="dd"/>
                                            tháng <fmt:formatDate value="${compensation.createdDate}" pattern="MM"/>
                                            năm <fmt:formatDate value="${compensation.createdDate}" pattern="yyyy"/>
                                        </div>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label class="col-sm-4 control-label">Họ tên lái xe</label>

                                    <div class="col-sm-3">
                                        <div class="text-value">${compensation.driverName}</div>
                                    </div>
                                    <label class="col-sm-2 control-label">Điện thoại</label>

                                    <div class="col-sm-3">
                                        <div class="text-value">${compensation.driverPhone}</div>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label class="col-sm-4 control-label">Địa chỉ liên hệ</label>

                                    <div class="col-sm-8">
                                        <div class="text-value">${compensation.driverAddress}</div>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label class="col-sm-4 control-label">Giấy phép lái xe số</label>

                                    <div class="col-sm-3">
                                        <div class="text-value">${compensation.licenseNumber}</div>
                                    </div>
                                    <label class="col-sm-2 control-label">Hạng</label>

                                    <div class="col-sm-3">
                                        <div class="text-value">${compensation.licenseType}</div>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label class="col-sm-4 control-label">Biển số xe gây tai nạn</label>

                                    <div class="col-sm-2">
                                        <div class="text-value">${compensation.plate}</div>
                                    </div>
                                    <label class="col-sm-3 control-label">Trọng tải/số chỗ ngồi</label>

                                    <div class="col-sm-2">
                                        <div class="text-value">${compensation.vehicleCapacity}</div>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label class="col-sm-4 control-label">Ngày xảy ra tai nạn</label>

                                    <div class="col-sm-4">
                                        <div class="text-value">
                                            ngày <fmt:formatDate value="${compensation.accidentDate}" pattern="dd"/>
                                            tháng <fmt:formatDate value="${compensation.accidentDate}" pattern="MM"/>
                                            năm <fmt:formatDate value="${compensation.accidentDate}" pattern="yyyy"/>
                                        </div>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label class="col-sm-4 control-label">Nơi xảy ra tai nạn</label>

                                    <div class="col-sm-8">
                                        <div class="text-value">${compensation.accidentPlace}</div>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label class="col-sm-4 control-label">Cơ quan CA giải quyết</label>

                                    <div class="col-sm-8">
                                        <div class="text-value">${compensation.controlDepartment}</div>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label class="col-sm-4 control-label">Diễn biến và nguyên nhân</label>

                                    <div class="col-sm-8">
                                        <div class="text-value">${compensation.description}</div>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label class="col-sm-4 control-label">Thiệt hại về người</label>

                                    <div class="col-sm-8">
                                        <div class="text-value">${compensation.humanDamage}</div>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label class="col-sm-4 control-label">Thiệt hại về tài sản</label>

                                    <div class="col-sm-8">
                                        <div class="text-value">${compensation.assetDamage}</div>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label class="col-sm-4 control-label">Người làm chứng</label>

                                    <div class="col-sm-8">
                                        <div class="text-value">${compensation.observer}</div>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label class="col-sm-4 control-label">Địa chỉ của người làm chứng</label>

                                    <div class="col-sm-8">
                                        <div class="text-value">${compensation.observerAddress}</div>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label class="col-sm-4 control-label">Yêu cầu bồi thường</label>

                                    <div class="col-sm-8">
                                        <div class="text-value">
                                            <c:choose>
                                                <c:when test="${empty compensation.compensationNote}">
                                                    <span class="empty-value">Không có</span>
                                                </c:when>
                                                <c:otherwise>${compensation.compensationNote}</c:otherwise>
                                            </c:choose>
                                        </div>
                                    </div>
                                </div>
                                <%--<div class="form-group">--%>
                                <%--<label class="col-sm-4 control-label">Biên bản của cơ quan CA</label>--%>

                                <%--<div class="col-sm-6">--%>
                                <%--<div class="text-value">--%>
                                <%--<c:choose>--%>
                                <%--<c:when test="${empty compensation.attachment}">--%>
                                <%--<span class="empty-value">Không có</span>--%>
                                <%--</c:when>--%>
                                <%--<c:otherwise>--%>
                                <%--<a href="${compensation.attachment}">--%>
                                <%--Xem chi tiết--%>
                                <%--</a>--%>
                                <%--</c:otherwise>--%>
                                <%--</c:choose>--%>
                                <%--</div>--%>
                                <%--</div>--%>
                                <%--</div>--%>
                            </c:otherwise>
                        </c:choose>

                    </fieldset>
                    <br/>
                    <%--/Compensation information--%>
                    <c:if test="${not empty compensation.resolveDate}">
                        <fieldset>
                            <legend>Thông tin giải quyết yêu cầu bồi thường</legend>

                            <div class="form-group">
                                <label class="col-sm-4 control-label">Ngày giải quyết yêu cầu</label>

                                <div class="col-sm-4">
                                    <div class="text-value">
                                        ngày <fmt:formatDate value="${compensation.resolveDate}" pattern="dd"/>
                                        tháng <fmt:formatDate value="${compensation.resolveDate}" pattern="MM"/>
                                        năm <fmt:formatDate value="${compensation.resolveDate}" pattern="yyyy"/>
                                    </div>
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-sm-4 control-label">Quyết định của công ty</label>

                                <div class="col-sm-6">
                                    <div class="text-value">${compensation.decision}</div>
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-sm-4 control-label">Ghi chú của công ty</label>

                                <div class="col-sm-8">
                                    <div class="text-value">
                                        <c:choose>
                                            <c:when test="${empty compensation.resolveNote}">
                                                <span class="empty-value">Không có</span>
                                            </c:when>
                                            <c:otherwise>${compensation.resolveNote}</c:otherwise>
                                        </c:choose>
                                    </div>
                                </div>
                            </div>
                        </fieldset>
                    </c:if>
                </form>
                <br/>

                <div class="text-center">
                    <a href="${pageContext.request.contextPath}/staff/compensation" type="button"
                       class="btn btn-default">
                        <i class="fa fa-arrow-left"></i> <strong>Danh sách yêu cầu bồi thường</strong>
                    </a>
                </div>
                <br/> <br/>
            </div>
        </div>
    </div>
</div>
<!-- /#wrapper -->

<!-- model for resolve compensation request -->
<div class="modal fade" id="resolve-compensation-modal">
    <div class="modal-dialog">
        <form action="${pageContext.request.contextPath}/staff/compensation" method="post" class="form-horizontal">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span
                            aria-hidden="true">&times;</span></button>
                    <h4 class="modal-title">Giải quyết yêu cầu</h4>
                </div>
                <div class="modal-body">
                    <fieldset>
                        <!-- Compensation code -->
                        <input type="hidden" name="resolve:compensationCode" value="${compensation.compensationCode}"/>

                        <!-- Resolve date -->
                        <div class="form-group">
                            <label class="col-sm-3 control-label" for="resolveDate">Ngày giải quyết *</label>

                            <div class="col-sm-4">
                                <input id="resolveDate" name="resolve:resolveDate" type="date" required
                                       class="form-control input-md">
                            </div>
                        </div>

                        <!-- Resolve note -->
                        <div class="form-group">
                            <label class="col-sm-3 control-label" for="resolveNote">Ghi chú</label>

                            <div class="col-sm-8">
                                <textarea id="resolveNote" name="resolve:resolveNote" rows="4" maxlength="2000"
                                          class="form-control input-md"></textarea>
                            </div>
                        </div>
                    </fieldset>
                </div>
                <div class="modal-footer">
                    <input type="hidden" id="decision" name="resolve:decision"/>
                    <input type="hidden" name="action" value="resolve"/>
                    <button type="submit" id="declineBtn" class="btn btn-danger">
                        <i class="fa fa-close"></i> Từ chối bồi thường
                    </button>
                    <button type="submit" id="acceptBtn" class="btn btn-primary">
                        <i class="fa fa-check"></i> Đồng ý bồi thường
                    </button>
                    <button type="button" class="btn btn-default" data-dismiss="modal">Đóng</button>
                </div>
            </div>
            <!-- /.modal-content -->
        </form>
    </div>
    <!-- /.modal-dialog -->
</div>
<!-- /.modal -->

<script type="text/javascript">
    $(document).ready(function () {
        $('#resolveDate').val(getCurrentDate());
        var createdDate = new Date('${compensation.createdDate}');
        document.getElementById("resolveDate").min = getInputDateWithoutTime(createdDate);
        document.getElementById("resolveDate").max = getCurrentDate();

        $("#declineBtn").on("click", function () {
            $('#decision').val('Từ chối bồi thường');
        });

        $("#acceptBtn").on("click", function () {
            $('#decision').val('Đồng ý bồi thường');
        });
    });
</script>

<%@ include file="_shared/footer.jsp" %>