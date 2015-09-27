<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="../_shared/header.jsp" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<div id="wrapper">

    <c:set var="contract" value="${requestScope.CONTRACT}" scope="request"/>
    <c:set var="customer" value="${requestScope.CUSTOMER}" scope="request"/>
    <c:set var="listPayment" value="${requestScope.PAYMENT}"/>
    <c:set var="listCompensation" value="${requestScope.COMPENSATION}" scope="request"/>
    <c:set var="listAccident" value="${requestScope.ACCIDENT}" scope="request"/>
    <c:set var="listPunishment" value="${requestScope.PUNISHMENT}" scope="request"/>
    <c:set var="listCard" value="${requestScope.CARD}" scope="request"/>
    <c:set var="config" value="${requestScope.CONFIG}"/>
    <c:set var="detailConfiguration" value="${requestScope.detailConfig}"/>

    <%@ include file="../_shared/navigation.jsp" %>
    <div id="page-wrapper">
        <div class="row">
            <div class="col-lg-12">
                <h1 class="page-header">
                    ${contract.contractCode}
                    <div class="pull-right">
                        <c:choose>
                            <c:when test="${not empty contract.needRenewPayment}">
                                <button id="btnRenew" type="button" class="btn btn-primary"
                                        data-toggle="modal" data-target="#renew-contract-modal">
                                    <i class="fa fa-refresh"></i> Thanh toán gia hạn
                                </button>
                            </c:when>
                            <c:when test="${detailConfiguration.renewable}">
                                <button id="btnRenew" type="button" class="btn btn-primary"
                                        data-toggle="modal" data-target="#renew-contract-modal">
                                    <i class="fa fa-refresh"></i> Gia hạn
                                </button>
                            </c:when>
                        </c:choose>
                        <c:if test="${detailConfiguration.cancelable}">
                            <button id="btnCancel" type="button" class="btn btn-danger"
                                    data-toggle="modal" data-target="#cancel-contract-modal">
                                <i class="fa fa-times"></i> Hủy hợp đồng
                            </button>
                        </c:if>
                    </div>
                </h1>
            </div>
            <!-- /.col-lg-12 -->
        </div>

        <div class="row">
            <div class="col-lg-12">

                <c:if test="${not empty validateErrors}">
                    <input type="hidden" id="modify-reason" value="${contract.modifyReason}"/>

                    <div class="text-danger">
                        <ul>
                            <c:forEach var="error" items="${validateErrors}">
                                <li>${error}</li>
                            </c:forEach>
                        </ul>
                    </div>
                </c:if>

                <form class="form-horizontal">
                    <c:if test="${(contract.status eq 'Pending') and (empty listPayment)}">
                        <div class="alert alert-info">
                            <p class="bs-example text-center text-uppercase">
                                Hợp đồng này chưa được thanh toán
                            </p>
                            <br/>

                            <p class="text-center">
                                <button class="btn btn-success" type="button" data-toggle="modal"
                                        data-target="#complete-payment-modal">
                                    <i class="fa fa-check"></i> Hoàn tất thanh toán
                                </button>
                            </p>
                        </div>
                    </c:if>
                    <%--/Show pending contract alert --%>

                    <c:if test="${contract.status eq 'Request cancel'}">
                        <div class="alert alert-info">
                            <p class="bs-example text-center text-uppercase">
                                Hợp đồng này đang có yêu cầu hủy từ khách hàng
                            </p>
                            <br/>
                            <!-- Cancel date -->
                            <div class="form-group">
                                <label class="col-sm-3 control-label">Thời điểm gởi yêu cầu</label>

                                <div class="col-sm-4">
                                    <div class="text-value">
                                        <fmt:formatDate value="${contract.cancelDate}" pattern="dd/MM/yyyy"/>
                                        lúc
                                        <fmt:formatDate value="${contract.cancelDate}" type="time"/>
                                    </div>
                                </div>
                            </div>

                            <!-- Cancel reason -->
                            <div class="form-group">
                                <label class="col-sm-3 control-label">Lý do hủy hợp đồng</label>

                                <div class="col-sm-8">
                                    <div class="text-value">${contract.cancelReason}</div>
                                </div>
                            </div>

                            <p class="text-center">
                                <button class="btn btn-primary" type="button" data-toggle="modal"
                                        data-target="#handle-request-cancel-modal">
                                    <i class="fa fa-check"></i> Giải quyết
                                </button>
                            </p>
                        </div>
                    </c:if>
                    <%--/Show request cancel contract information --%>

                    <c:if test="${contract.status eq 'Cancelled'}">
                        <div class="alert alert-warning">
                            <p class="bs-example text-center text-uppercase">
                                <strong>Hợp đồng này đã bị hủy</strong>
                            </p>
                            <br/>
                            <!-- Cancel date -->
                            <div class="form-group">
                                <label class="col-sm-3 control-label">Thời điểm hủy</label>

                                <div class="col-sm-4">
                                    <div class="text-value">
                                        <fmt:formatDate value="${contract.cancelDate}" pattern="dd/MM/yyyy"/>
                                        lúc
                                        <fmt:formatDate value="${contract.cancelDate}" type="time"/>
                                    </div>
                                </div>
                            </div>

                            <!-- Cancel reason -->
                            <div class="form-group">
                                <label class="col-sm-3 control-label">Lý do hủy</label>

                                <div class="col-sm-8">
                                    <div class="text-value">${contract.cancelReason}</div>
                                </div>
                            </div>

                            <!-- Cancel note -->
                            <div class="form-group">
                                <label class="col-sm-3 control-label">Ghi chú hủy</label>

                                <div class="col-sm-8">
                                    <div class="text-value">
                                        <c:choose>
                                            <c:when test="${empty contract.cancelNote}">
                                                <span class="empty-value">Không có</span>
                                            </c:when>
                                            <c:otherwise>
                                                ${contract.cancelNote}
                                            </c:otherwise>
                                        </c:choose>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </c:if>
                    <%--/Show cancel contract information --%>
                    <jsp:include page="contract-detail-general.jsp" flush="true"/>
                    <!-- Remaining days & contract fee -->
                    <div class="form-group">
                        <c:choose>
                            <c:when test="${not empty listPayment}">
                                <label class="col-sm-4 control-label">Thời gian đến ngày hết hạn</label>

                                <div class="col-sm-2">
                                    <div class="text-value">
                                        <c:choose>
                                            <c:when test="${contract.status eq 'Cancelled'}">
                                                <span class="empty-value">Đã hủy</span>
                                            </c:when>
                                            <c:when test="${contract.status eq 'Expired'}">
                                                <span class="empty-value">Đã hết hạn</span>
                                            </c:when>
                                            <c:otherwise>
                                                <span id="remain"
                                                      style="color:deepskyblue; font-weight: bolder; font-size: large">${detailConfiguration.displayRemain}</span>
                                            </c:otherwise>
                                        </c:choose>
                                    </div>
                                </div>

                                <label class="col-sm-2 control-label">Phí bảo hiểm</label>
                            </c:when>
                            <c:otherwise>
                                <label class="col-sm-4 control-label">Phí bảo hiểm</label>
                            </c:otherwise>
                        </c:choose>

                        <div class="col-sm-3">
                            <div class="text-value">
                                <span style="color:hotpink; font-weight: bolder; font-size: large">
                                    <fmt:setLocale value="vi_VN"/>
                                    <fmt:formatNumber value="${contract.contractFee}" type="currency"
                                                      currencySymbol="" maxFractionDigits="0"/> VNĐ
                                </span>
                            </div>
                        </div>
                    </div>
                </form>
                <%--/General information--%>
                <br/>

                <div role="tabpanel">
                    <!-- Nav tabs -->
                    <ul class="nav nav-tabs" role="tablist">
                        <li role="presentation" class="active">
                            <a href="#contractInfo" aria-controls="home" role="tab" data-toggle="tab">
                                <strong>Thông tin hợp đồng</strong>
                            </a>
                        </li>
                        <li role="presentation">
                            <a href="#compensations" aria-controls="profile" role="tab" data-toggle="tab">
                                <strong>Lịch sử bồi thường</strong>
                            </a>
                        </li>
                        <li role="presentation">
                            <a href="#accidents" aria-controls="settings" role="tab" data-toggle="tab">
                                <strong>Lịch sử tai nạn</strong>
                            </a>
                        </li>
                        <li role="presentation">
                            <a href="#punishments" aria-controls="messages" role="tab" data-toggle="tab">
                                <strong>Lịch sử vi phạm luật ATGT</strong>
                            </a>
                        </li>
                    </ul>
                </div>

                <div class="tab-content">
                    <div role="tabpanel" class="tab-pane active" id="contractInfo">
                        <br/>

                        <form class="form-horizontal">
                            <jsp:include page="contract-detail-customer.jsp" flush="true"/>
                            <%--/Customer information--%>
                            <br/>

                            <jsp:include page="contract-detail-vehicle.jsp" flush="true"/>
                            <%--/Vehicle information--%>
                            <br/>
                        </form>
                    </div>
                    <%--/Contract information tab --%>

                    <div role="tabpanel" class="tab-pane" id="compensations">
                        <br/>
                        <jsp:include page="contract-detail-compensation.jsp" flush="true"/>
                    </div>
                    <%--/Compensation information tab --%>

                    <div role="tabpanel" class="tab-pane" id="accidents">
                        <br/>
                        <jsp:include page="contract-detail-accident.jsp" flush="true"/>
                    </div>
                    <%--/Accident information tab --%>

                    <div role="tabpanel" class="tab-pane" id="punishments">
                        <br/>
                        <jsp:include page="contract-detail-punishment.jsp" flush="true"/>
                    </div>
                    <%--/Punishments information tab --%>
                </div>
                <%--/Tab content --%>
                <div role="tabpanel">
                    <!-- Nav tabs -->
                    <ul class="nav nav-tabs" role="tablist">
                        <li role="presentation" class="active">
                            <a href="#paymentInfo" aria-controls="home" role="tab" data-toggle="tab">
                                <strong>Lịch sử giao dịch</strong>
                            </a>
                        </li>
                        <li role="presentation">
                            <a href="#cards" aria-controls="cards" role="tab" data-toggle="tab">
                                <strong>Lịch sử thẻ</strong>
                            </a>
                        </li>
                    </ul>
                </div>
                <div class="tab-content">
                    <div role="tabpanel" class="tab-pane active" id="paymentInfo">
                        <br/>

                        <form class="form-horizontal">
                            <fieldset>
                                <legend>
                                    Thông tin thanh toán

                                    <div class="pull-right" style="margin-top: -10px">
                                        <button id="btnPayment" type="button" class="btn btn-success"
                                                data-toggle="modal" data-target="#add-payment-modal">
                                            <i class="fa fa-plus"></i> Thêm thông tin thanh toán
                                        </button>
                                    </div>
                                </legend>

                                <div class="table-responsive">
                                    <table class="table table-striped table-bordered table-hover">
                                        <thead>
                                        <tr>
                                            <th>Mã GD</th>
                                            <th>Thời gian</th>
                                            <th>Hình thức</th>
                                            <th>Dịch vụ</th>
                                            <th>Số tiền</th>
                                            <th>Chi tiết</th>
                                        </tr>
                                        </thead>
                                        <tbody>
                                        <c:forEach var="payment" items="${listPayment}" varStatus="counter">
                                            <tr>
                                                <td>${payment.id}</td>
                                                <td>
                                                    <fmt:formatDate value="${payment.paidDate}" pattern="dd/MM/yyyy"/>
                                                </td>
                                                <td>${payment.paymentMethod}</td>
                                                <td>${payment.content}</td>
                                                <td>
                                                    <fmt:formatNumber value="${payment.amount}" type="currency"
                                                                      currencySymbol="" maxFractionDigits="0"/> VNĐ
                                                </td>
                                                <td>
                                                    <a href="javascript:;"
                                                       class="payment-id-clicker btn btn-primary btn-xs"
                                                       payment-id="${payment.id}">
                                                        Chi tiết
                                                    </a>
                                                </td>
                                            </tr>
                                        </c:forEach>
                                        </tbody>
                                    </table>
                                </div>
                            </fieldset>
                            <%--/Payment information--%>
                        </form>
                    </div>
                    <%--/Payment information tab --%>

                    <div role="tabpanel" class="tab-pane" id="cards">
                        <br/>
                        <jsp:include page="contract-detail-card.jsp" flush="true"/>
                    </div>
                    <%--/Card information tab --%>
                </div>
                <%--/Tab content --%>
                <br/>

                <div class="text-center">
                    <a href="${pageContext.request.contextPath}/staff/contract" class="btn btn-default">
                        <i class="fa fa-arrow-left"></i> <strong>Danh sách hợp đồng</strong>
                    </a>
                </div>
                <br/>
                <br/>
            </div>
        </div>
    </div>
</div>
<!-- /#wrapper -->

<jsp:include page="contract-detail-modal.jsp" flush="true"/>

<script type="text/javascript">

    // Constants from server business config
    var contractStatus = '${contract.status}';
    var expDate = new Date("${contract.expiredDate}");
    var pricePerYear = '${contract.micContractTypeByContractTypeId.pricePerYear}';
    var contractTerm = 365;
    var renewFee = pricePerYear;
    var newCardFee = parseFloat('${config.newCardFee}');
    var deliveryFee = parseFloat('${config.deliveryFee}');
    var totalFee = parseFloat(renewFee) + newCardFee;

    // State variables
    var isNewCard = false;
    var isDeliveryNewCard = false;
    var isCancel = false;

    function calcRenewFee() {
        var inputDate = new Date($('#expiredDate').val());
        if (contractStatus != 'Expired') {
            contractTerm = getMonthsBetween(expDate, inputDate);
        } else {
            contractTerm = getMonthsBetween(new Date(getCurrentDate()), inputDate);
        }
        return calculateContractFee(contractTerm, pricePerYear);
    }

    // Calculate fees
    function refreshFees() {
        var calculatedRenewFee = calcRenewFee();
        var calculatedNewCardFee = isNewCard ? newCardFee : 0;
        var calculatedDeliveryFee = isNewCard ? (isDeliveryNewCard ? deliveryFee : 0) : 0;

        // Data to display to user
        $('#renewFee').text(calculatedRenewFee.formatMoney(0,'.','.'));
        $('#newCardFee').text(calculatedNewCardFee.formatMoney(0,'.','.'));
        $('#deliveryFee').text(calculatedDeliveryFee.formatMoney(0,'.','.'));
        $('#totalFee').text((calculatedRenewFee + calculatedNewCardFee + calculatedDeliveryFee).formatMoney(0,'.','.'));

        // Data to send to server
        $('#contractFee').val(calculatedRenewFee);
        $('#amount').val(calculatedRenewFee + calculatedNewCardFee + calculatedDeliveryFee);

        refreshFeeUI();
    }

    function refreshFeeUI() {
        if ('${contract.status}' == 'No card') {
            $('#newCard').attr('disabled', 'disabled');
            $('#newCardTooltip').attr('data-original-title', 'Hợp đồng này chưa được cấp thẻ nên không thể yêu cầu thẻ mới.');
        } else {
            $('#newCard').removeAttr('disabled');
            $('#newCardTooltip').attr('data-original-title', 'Thẻ cũ sẽ bị vô hiệu hóa, yêu cầu cấp thẻ mới sẽ được tạo.');
        }
        if (isNewCard) {
            $('.control-delivery').slideDown();
        } else {
            // turn off the delivery checkbox
            $('.control-delivery').slideUp();
        }
    }

    function refreshCancelUI() {
        if (isCancel) {
            $('.control-cancel-note').slideDown();
        } else {
            $('.control-cancel-note').slideUp();
        }
    }

    $(document).ready(function () {
        $('#expiredDate').val(addMonth(getCurrentDate(), '${config.contractDefaultTerm}'));
        $('#addPaidDate').val(getCurrentDate());
        document.getElementById("addPaidDate").min = '${config.paidDateMin}';
        document.getElementById("addPaidDate").max = '${config.paidDateMax}';
        $('#completePaidDate').val(getCurrentDate());
        document.getElementById("completePaidDate").min = '${config.paidDateMin}';
        document.getElementById("completePaidDate").max = '${config.paidDateMax}';
        $('#paidDate').val(getCurrentDate());
        document.getElementById("paidDate").min = '${config.paidDateMin}';
        document.getElementById("paidDate").max = '${config.paidDateMax}';
        $('#cancelDate').val(getCurrentDate());
        document.getElementById("cancelDate").min = '${config.cancelDateMin}';
        document.getElementById("cancelDate").max = '${config.cancelDateMax}';

        // Bind events to refresh fees
        $('#expiredDate').blur(function () {
            refreshFees();
        });

        $('#newCard').click(function () {
            var isChecked = $(this).is(':checked');
            isNewCard = isChecked;
            refreshFees();
        });

        $('#deliveryNewCard').click(function () {
            var isChecked = $(this).is(':checked');
            isDeliveryNewCard = isChecked;
            refreshFees();
        });
        setTimeout(refreshFees, 1000);

        // Handle request cancel contract
        $('#decision').change(function () {
            var decision = $('#decision option:selected').val();
            if (decision == "cancelContract") {
                isCancel = true;
            } else {
                isCancel = false;
            }
            refreshCancelUI();
        });

        if (contractStatus.toLowerCase() == 'Pending'.toLowerCase()) {
            // Hide create & edit compensation, accident, punishment
            $('.updateBtn').addClass('hide');
        }

        if (contractStatus.toLowerCase() == 'Expired'.toLowerCase()) {
            // Hide create & edit compensation, accident, punishment while exceed staff config
            var durationFromCurrentToExpired = daysBetween(new Date("${contract.expiredDate}"), new Date());
            if (durationFromCurrentToExpired > '${config.updateContractDueDate}') {
                $('.updateBtn').addClass('hide');
            }
            $('#startDate').val(getCurrentDate());
            document.getElementById("expiredDate").min = '${config.expiredDateMin}';
            document.getElementById("expiredDate").max = '${config.expiredDateMax}';
        } else {
            $('#startDate').val('${contract.expiredDate}');
            $('#expiredDate').val(addMonth(getInputDateWithoutTime(expDate), '${config.contractDefaultTerm}'));
            document.getElementById("expiredDate").min = addMonth(getInputDateWithoutTime(expDate), '${config.contractMinTerm}');
            document.getElementById("expiredDate").max = addMonth(getInputDateWithoutTime(expDate), '${config.contractDefaultTerm}');
        }

        if (contractStatus.toLowerCase() == 'Cancelled'.toLowerCase()) {
            // Hide 2 edit button
            $('#btnPayment').addClass('hide');
            $('.editBtn').addClass('hide');
            // Hide create & edit compensation, accident, punishment while exceed staff config
            var durationFromCurrentToCancel = daysBetween(new Date("${contract.cancelDate}"), new Date());
            if (durationFromCurrentToCancel > '${config.updateContractDueDate}') {
                $('.updateBtn').addClass('hide');
            }
        }

        // Ajax load data to modal
        $(".payment-id-clicker").on("click", function () {
            var that = this;
            var paymentId = that.getAttribute("payment-id");
            $.ajax({
                url: "${pageContext.request.contextPath}/ajax?action=paymentDetail&paymentId=" + paymentId,
                type: "GET"
            }).done(function (data) {
                $("#payment-id-value").html(data.id);
                $("#contract-code-value").html(data.contractCode);
                $("#amount-value").html(data.amount.formatMoney(0,'.','.') + ' VNĐ');
                $("#content-value").html(data.content);
                $("#payment-method-value").html(data.paymentMethod);
                $("#paid-date-value").html(getDateTime(data.paidDate));
                console.log(data.paidDate);
                console.log(data.startDate);
                console.log(data.expiredDate);
                if ((data.paymentMethod).toLowerCase().indexOf("paypal") > -1) {
                    $("#ppIDCtrl").removeClass('hide');
                    $("#paypal-id-value").html(data.paypalTransId);
                } else {
                    $("#receiverCtrl").removeClass('hide');
                    $("#receiver-value").html(data.micStaffByReceiver ? data.micStaffByReceiver.name : '');
                }

                if (data.startDate) {
                    $("#stDateCtrl").removeClass('hide');
                    $("#start-date-value").html(getDateTime(data.startDate));
                }

                if (data.expiredDate) {
                    $("#expDateCtrl").removeClass('hide');
                    $("#expired-date-value").html(getDateTime(data.expiredDate));
                }

                $("#detail-payment-modal").modal("show");
            });
        });

        // Clear modal value after close
        $('#detail-payment-modal').on('hidden.bs.modal', function () {
            $("#payment-id-value").html("");
            $("#contract-code-value").html("");
            $("#amount-value").html("");
            $("#content-value").html("");
            $("#payment-method-value").html("");
            $("#paid-date-value").html("");
            $("#ppIDCtrl").addClass('hide');
            $("#paypal-id-value").html("");
            $("#receiverCtrl").addClass('hide');
            $("#receiver-value").html("");
            $("#stDateCtrl").addClass('hide');
            $("#start-date-value").html("");
            $("#expDateCtrl").addClass('hide');
            $("#expired-date-value").html("");
        })
    });
</script>
<script>
    function getQueryVariable(variable) {
        var query = window.location.search.substring(1);
        var vars = query.split("&");
        for (var i = 0; i < vars.length; i++) {
            var pair = vars[i].split("=");
            if (pair[0] == variable) {
                return pair[1];
            }
        }
        return false;
    }
    // Auto active tab after pagination
    $(function () {
        var active = getQueryVariable('active');
        if (active) {
            $(".nav-tabs a[href='#" + active + "s']").tab('show')
        }
    });
</script>
<%@ include file="../_shared/footer.jsp" %>
<%--<jsp:include page="cancel-new-card-request-modal.jsp" flush="true"/>--%>