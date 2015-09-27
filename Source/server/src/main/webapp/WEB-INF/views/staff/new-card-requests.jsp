<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="_shared/header.jsp" %>

<div id="wrapper">

    <%@ include file="_shared/navigation.jsp" %>
    <div id="page-wrapper">
        <div class="row">
            <div class="col-lg-12">
                <h1 class="page-header">Yêu cầu cấp thẻ mới</h1>
            </div>
            <!-- /.col-lg-12 -->
        </div>
        <div class="row">
            <div class="col-lg-12">
                <c:if test="${not empty validateErrors}">
                    <div class="well well-lg text-danger ">
                        <ul>
                            <c:forEach var="error" items="${validateErrors}">
                                <li>${error}</li>
                            </c:forEach>
                        </ul>
                    </div>
                </c:if>
                <c:if test="${param.info eq 'cancelNewCardRequestSuccess'}">
                    <div class="text-success text-center">
                        Hủy yêu cầu thẻ mới thành công
                    </div>
                </c:if>
                <c:if test="${param.info eq 'fail'}">
                    <div class="text-danger text-center">
                        Có lỗi xảy ra. Xin thử lại
                    </div>
                </c:if>
                <div class="panel panel-default">
                    <div class="panel-heading">
                        <div class="pull-left center-dropdown-button">
                            <!--<input type="checkbox" class="check-all"/>-->
                            <b>Có ${unresolvedRequestCount} yêu cầu cấp thẻ mới chưa xử lý</b>
                        </div>
                        <div class="pull-right no-wrap">
                            <form action="${pageContext.request.contextPath}/staff/card" method="get">
                                <input type="hidden" name="action" value="newCardRequestSearch"/>
                                <input type="text" class="form-control long-text-box" name="keyword"
                                       placeholder="Tìm kiếm theo mã thẻ cũ" value="${param.keyword}"/>
                                <input type="submit" class="btn btn-default" value="Tìm kiếm"/>
                            </form>
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
                                    <th>Thời gian</th>
                                    <th>Ghi chú</th>
                                    <th>Mã thẻ cũ</th>
                                    <th>Hợp đồng</th>
                                    <th>Ngày cấp mới</th>
                                    <th>Thẻ mới cấp</th>
                                    <th>Thực hiện</th>
                                </tr>
                                </thead>
                                <c:set var="requests" value="${requestPaginator.getItemsOnCurrentPage(param.page)}"/>
                                <tbody>
                                <c:choose>
                                    <c:when test="${requests.size() == 0}">
                                        <tr>
                                            <td colspan="8" style="vertical-align: middle; text-align: center;">
                                                Không có yêu cầu thẻ mới nào
                                            </td>
                                        </tr>
                                    </c:when>
                                    <c:otherwise>
                                        <c:forEach var="newRequest" items="${requests}" varStatus="counter">
                                            <tr>
                                                <td>${(requestPaginator.getCurrentPage(param.page) - 1) * requestPaginator.itemPerPage + counter.count}</td>
                                                <td>
                                                    <fmt:formatDate value="${newRequest.requestDate}"
                                                                    pattern="dd/MM/yyyy"/>
                                                </td>
                                                <td><a tabindex="0" data-trigger="focus" data-toggle="popover"
                                                       title="Ghi chú từ khách hàng"
                                                       role="button" data-content="${newRequest.note}"><i
                                                        class="fa fa-file"></i></a></td>
                                                <td>
                                                    <a href="${pageContext.request.contextPath}/staff/card?action=detail&cardId=${newRequest.micCardInstanceByOldCardInstanceId.cardId}">
                                                            ${newRequest.micCardInstanceByOldCardInstanceId.cardId}
                                                    </a>
                                                </td>
                                                <td>
                                                    <a href="${pageContext.request.contextPath}/staff/contract?action=detail&code=${newRequest.micCardInstanceByOldCardInstanceId.contractCode}">
                                                            ${newRequest.micCardInstanceByOldCardInstanceId.contractCode}
                                                    </a>

                                                </td>

                                                <td>
                                                    <c:if test="${empty newRequest.resolveDate}">
                                                        <span class="label label-danger">Chưa cấp</span>
                                                    </c:if>
                                                    <fmt:formatDate value="${newRequest.resolveDate}"
                                                                    pattern="dd/MM/yyyy"/>

                                                </td>
                                                <td>
                                                    <a href="${pageContext.request.contextPath}/staff/card?action=detail&cardId=${map[newRequest.id]}">
                                                            ${map[newRequest.id]}
                                                    </a>

                                                </td>
                                                <td>
                                                    <c:if test="${newRequest.isPaid == 1}">
                                                        <span class="label label-info">Đã thanh toán</span>
                                                    </c:if>
                                                    <c:if test="${newRequest.isPaid == 0}">
                                                        <%--<span class="label label-danger">Chưa thanh toán</span>--%>

                                                        <button delivery="${newRequest.isDeliveryRequested}"
                                                                contractCode="${newRequest.micCardInstanceByOldCardInstanceId.contractCode}"
                                                                type="button" class="btn btn-success btn-xs"
                                                                data-toggle="modal" data-target="#add-payment-modal"
                                                                onclick="{
//                                                            var rowNumber = $(this).attr('value');
                                                                    var delivery = $(this).attr('delivery');
                                                                    var contractCode = $(this).attr('contractCode');
                                                                    var content;
                                                                    var fee;
                                                                    var newCardFee =parseInt( $('#newCardFee').val());
                                                                    var deliveryFee = parseInt( $('#deliveryFee').val());
                                                                    if (delivery == 1){
                                                                        content = 'Đăng ký thẻ mới ' +contractCode +' + giao thẻ';
                                                                        fee = newCardFee + deliveryFee; // fix later
                                                                    } else {
                                                                        fee = newCardFee;
                                                                        content = 'Đăng ký thẻ mới ' +contractCode;
                                                                    }


                                                                    $('#addContent').text(content);
                                                                    $('#content').val(content);
                                                                    $('#amount').val(fee);
                                                                    $('#addAmount').text(parseFloat(fee).formatMoney(0,'.','.'));
                                                                    $('#contractCode').val(contractCode);
                                                                    $('#delivery').val(delivery);

                                                            }">
                                                            <i class="fa fa-plus"></i> Thanh toán
                                                        </button>
                                                        <c:if test="${empty map[newRequest.id]}">
                                                            <c:if test="${newRequest.micCardInstanceByOldCardInstanceId.micContractByContractCode.status.equalsIgnoreCase('Ready')}">
                                                                <button contractCode="${newRequest.micCardInstanceByOldCardInstanceId.contractCode}" type="button" class="btn btn-danger btn-xs"
                                                                        data-toggle="modal" data-target="#cancel-new-card-request" onclick="{
                                                                    var contractCode = $(this).attr('contractCode');
                                                                    $('#contractCodeModal').val(contractCode);
                                                                    $('#contractCodeModal1').text(contractCode);
                                                                 }">
                                                                    <i class="fa fa-times"></i> Hủy
                                                                </button>
                                                            </c:if>

                                                        </c:if>


                                                    </c:if>

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
                                        <a href="?action=${param.action}&keyword=${param.keyword}&page=1"
                                           aria-label="Previous">
                                            <span aria-hidden="true">Đầu</span>
                                        </a>
                                    </li>
                                </c:if>
                                <c:forEach begin="1" end="${requestPaginator.pageSize}" var="pageNumber">
                                    <li ${param.page == pageNumber ||(pageNumber == 1 && empty param.page) ? "class='active'": ""} >
                                        <a href="?action=${param.action}&keyword=${param.keyword}&page=${pageNumber}">${pageNumber}</a>
                                    </li>
                                </c:forEach>
                                <c:if test="${param.page != requestPaginator.pageSize && requestPaginator.pageSize != 1}">
                                    <li>
                                        <a href="?action=${param.action}&keyword=${param.keyword}&page=${requestPaginator.pageSize}"
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


                <div class="panel panel-success">
                    <div class="panel-heading">
                        Quy trình cấp lại thẻ mới
                    </div>
                    <div class="panel-body">
                        <p>
                            Để cấp thẻ mới cho khách hàng trong trường hợp có yêu cầu thẻ mới, cần thực hiện các bước
                            sau đây:
                        </p>
                        <ol>
                            <li>Sử dụng <b>Ứng dụng in thẻ</b> trên điện thoại, bấm chọn hoặc dùng chức năng <b>Tìm
                                kiếm</b> để tìm kiếm hợp đồng cần in thẻ mới.
                            </li>
                            <li>Chuyển phát thẻ mới cho khách hàng nếu có yêu cầu, thẻ cũ sẽ tự động bị <b>vô hiệu
                                hoá</b>.
                            </li>
                            <li>
                                Thông tin về thẻ mới sẽ được <b>tự động cập nhật</b> ở trang này sau khi thẻ mới được
                                cấp.
                            </li>
                        </ol>

                        <p>
                            Trong trường hợp cần thiết, các thông tin lưu trên thẻ cũ vẫn có thể được tra cứu lại tại
                            trang
                            <a href="${pageContext.request.contextPath}/staff/card">Quản lý thẻ</a>
                        </p>
                    </div>
                </div>

            </div>
        </div>
    </div>
</div>
<!-- /#wrapper -->
<jsp:include page="new-card-requests-modal.jsp" flush="true"/>
<%@ include file="_shared/footer.jsp" %>
<script language="JavaScript">
    document.getElementById("addPaidDate").min = '${config.paidDateMin}';
    document.getElementById("addPaidDate").max = '${config.paidDateMax}';

    function setInputDate(_id) {
        var _dat = document.querySelector(_id);
        var hoy = new Date(),
                d = hoy.getDate(),
                m = hoy.getMonth() + 1,
                y = hoy.getFullYear(),
                data;

        if (d < 10) {
            d = "0" + d;
        }
        ;
        if (m < 10) {
            m = "0" + m;
        }
        ;

        data = y + "-" + m + "-" + d;
        console.log(data);
        _dat.value = data;
    }
    ;
    if ($('#addPaidDate').val() == "") {
        setInputDate("#addPaidDate");
    }
</script>
<jsp:include page="contract/cancel-new-card-request-modal.jsp" flush="true"/>