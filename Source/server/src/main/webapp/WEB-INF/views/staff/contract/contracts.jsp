<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="../_shared/header.jsp" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<div id="wrapper">

    <c:set var="contracts" value="${contractPaginator.getItemsOnCurrentPage(param.page)}"/>

    <%@ include file="../_shared/navigation.jsp" %>
    <div id="page-wrapper">
        <div class="row">
            <div class="col-lg-12">
                <h1 class="page-header">
                    Hợp đồng
                    <div class="pull-right">
                        <a href="${pageContext.request.contextPath}/staff/contract?action=create"
                           class="btn btn-success">
                            <i class="fa fa-plus"></i>
                            Hợp đồng mới
                        </a>
                    </div>
                </h1>
            </div>
            <!-- /.col-lg-12 -->
        </div>
        <!-- /.row -->
        <div class="panel panel-default">
            <div class="panel-heading">
                <div class="pull-left center-dropdown-button">
                    <!--<input type="checkbox" class="check-all"/>-->
                    <b>Có ${contractPaginator.itemSize} hợp đồng</b>
                </div>
                <div class="pull-right no-wrap">
                    <form action="${pageContext.request.contextPath}/staff/contract" method="get">
                        <input type="text" class="form-control long-text-box" name="keyword"
                               placeholder="Tìm kiếm theo tên, mã hợp đồng" value="${param.keyword}"/>
                        <input type="submit" class="btn btn-default" value="Tìm kiếm"/>
                        <input type="hidden" name="action" value="search"/>
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
                            <th>Mã hợp đồng</th>
                            <th>Tên khách hàng</th>
                            <th>Ngày bắt đầu</th>
                            <th>Ngày kết thúc</th>
                            <th>
                                <div class="dropdown">
                                    <span class="dropdown-toggle"
                                            type="button" id="status-dropdown"
                                            data-toggle="dropdown" aria-haspopup="true"
                                            aria-expanded="false">
                                        Trạng thái <span id="status-dropdown-status"></span>
                                        <span class="caret"></span>
                                    </span>
                                    <ul class="dropdown-menu" aria-labelledby="status-dropdown">
                                        <li><a data-status="" href="?action=${param.action}&keyword=${param.keyword}&status=">
                                            Tất cả
                                        </a></li>
                                        <li><a data-status="Pending" href="?action=${param.action}&keyword=${param.keyword}&status=Pending">
                                            <span class="label label-gray">Chưa kích hoạt</span>
                                        </a></li>
                                        <li><a data-status="No card" href="?action=${param.action}&keyword=${param.keyword}&status=No card">
                                            <span class="label label-primary">Chưa có thẻ</span>
                                        </a></li>
                                        <li><a data-status="Ready" href="?action=${param.action}&keyword=${param.keyword}&status=Ready">
                                            <span class="label label-success">Sẵn sàng</span>
                                        </a></li>
                                        <li><a data-status="Request cancel" href="?action=${param.action}&keyword=${param.keyword}&status=Request cancel">
                                            <span class="label label-warning">Yêu cầu hủy</span>
                                        </a></li>
                                        <li><a data-status="Expired" href="?action=${param.action}&keyword=${param.keyword}&status=Expired">
                                            <span class="label label-danger">Hết hạn</span>
                                        </a></li>
                                        <li><a data-status="Cancelled" href="?action=${param.action}&keyword=${param.keyword}&status=Cancelled">
                                            <span class="label label-dark">Đã huỷ</span>
                                        </a></li>
                                    </ul>
                                </div>
                            </th>
                        </tr>
                        </thead>
                        <tbody>
                        <c:choose>
                            <c:when test="${contracts.size() == 0}">
                                <tr>
                                    <td colspan="6" style="vertical-align: middle; text-align: center;">
                                        Không có hợp đồng nào
                                    </td>
                                </tr>
                            </c:when>
                            <c:otherwise>
                                <c:forEach var="contract" items="${contracts}" varStatus="counter">
                                    <tr>
                                        <td>${(contractPaginator.getCurrentPage(param.page) - 1) * contractPaginator.itemPerPage + counter.count}</td>
                                        <td>
                                            <a href="${pageContext.request.contextPath}/staff/contract?action=detail&code=${contract.contractCode}">
                                                    ${contract.contractCode}
                                            </a>
                                        </td>
                                        <td>
                                            <a href="${pageContext.request.contextPath}/staff/customer?action=detail&code=${contract.customerCode}">
                                                    ${contract.micCustomerByCustomerCode.name}
                                            </a>
                                        </td>
                                        <td>
                                            <fmt:formatDate value="${contract.startDate}" pattern="dd/MM/yyyy"/>
                                        </td>
                                        <td>
                                            <fmt:formatDate value="${contract.expiredDate}" pattern="dd/MM/yyyy"/>
                                        </td>
                                        <td>
                                            <c:set var="status" value="${contract.status}"/>
                                            <c:choose>
                                                <c:when test="${status.equalsIgnoreCase('Pending')}">
                                                    <span class="label label-gray">Chưa kích hoạt</span>
                                                </c:when>
                                                <c:when test="${status.equalsIgnoreCase('No card')}">
                                                    <span class="label label-primary">Chưa có thẻ</span>
                                                </c:when>
                                                <c:when test="${status.equalsIgnoreCase('Ready')}">
                                                    <span class="label label-success">Sẵn sàng</span>
                                                </c:when>
                                                <c:when test="${status.equalsIgnoreCase('Request cancel')}">
                                                    <span class="label label-warning">Yêu cầu hủy</span>
                                                </c:when>
                                                <c:when test="${status.equalsIgnoreCase('Expired')}">
                                                    <span class="label label-danger">Hết hạn</span>
                                                </c:when>
                                                <c:when test="${status.equalsIgnoreCase('Cancelled')}">
                                                    <span class="label label-dark">Đã huỷ</span>
                                                </c:when>
                                                <c:otherwise>
                                                    <span class="label label-default">Không trạng thái</span>
                                                </c:otherwise>
                                            </c:choose>
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
                                <a href="?action=${param.action}&keyword=${param.keyword}&page=1&status=${param.status}" aria-label="Previous">
                                    <span aria-hidden="true">Đầu</span>
                                </a>
                            </li>
                        </c:if>
                        <c:forEach begin="1" end="${contractPaginator.pageSize}" var="pageNumber">
                            <li ${param.page == pageNumber ||(pageNumber == 1 && empty param.page) ? "class='active'": ""} >
                                <a href="?action=${param.action}&keyword=${param.keyword}&page=${pageNumber}&status=${param.status}">${pageNumber}</a>
                            </li>
                        </c:forEach>
                        <c:if test="${param.page != contractPaginator.pageSize && contractPaginator.pageSize != 1}">
                            <li>
                                <a href="?action=${param.action}&keyword=${param.keyword}&page=${contractPaginator.pageSize}&status=${param.status}"
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


        <div class="panel panel-info">
            <div class="panel-heading">
                Các trạng thái của hợp đồng:
            </div>
            <div class="panel-body">
                <ul>
                    <li>
                        <span class="label label-gray">Chưa kích hoạt</span>
                        Khách hàng đăng ký online nhưng chưa thanh toán hoặc thời điểm bắt đầu hiệu lực của hợp đồng chưa đến.
                    </li>
                    <li>
                        <span class="label label-primary">Chưa có thẻ</span>
                        Khách hàng đã thanh toán cho hợp đồng nhưng chưa được in thẻ để sử dụng.
                    </li>
                    <li>
                        <span class="label label-success">Sẵn sàng</span>
                        Khánh hàng đã hoàn thành hợp đồng và đã có thẻ để sẵn sàng sử dụng.
                    </li>
                    <li>
                        <span class="label label-warning">Yêu cầu huỷ</span>
                        Khách hàng gửi yêu cầu huỷ hợp đồng và đang chờ nhân viên xác nhận.
                    </li>
                    <li>
                        <span class="label label-danger">Hết hạn</span>
                        Hợp đồng đã hết hạn và không có giá trị. Khánh hàng phải gia hạn để tiếp tục sử dụng chương
                        trình bảo hiểm.
                    </li>
                    <li>
                        <span class="label label-dark">Đã huỷ</span>
                        Hợp đồng bị huỷ. Khánh hàng muốn tiếp tục sử dụng cần phải đăng ký hợp đồng mới.
                    </li>
                </ul>
            </div>
        </div>
    </div>
</div>
<!-- /#wrapper -->
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
    $(function () {
        // Contract status filter
        var $status = $('#status-dropdown-status');
        var status = getQueryVariable('status') || "";
        status = status.replace("%20", " ");
        if (status) {
            $status.html($('[data-status="'+status+'"]').html());
        }
    });
</script>
<%@ include file="../_shared/footer.jsp" %>