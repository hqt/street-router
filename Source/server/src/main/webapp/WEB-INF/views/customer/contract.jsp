<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="_shared/header.jsp" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<div id="wrapper">

    <%@ include file="_shared/navigation.jsp" %>
    <div id="page-wrapper">
        <div class="row">
            <div class="col-lg-12">
                <h1 class="page-header">
                    Hợp đồng

                    <div class="pull-right">
                        <a href="${pageContext.request.contextPath}/customer/contract?action=NewContract"
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

        <div>

        </div>
        <div class="panel panel-default">
            <div class="panel-heading">
                <div class="pull-left center-dropdown-button">
                    <!--<input type="checkbox" class="check-all"/>-->
                    <b>Có ${contractPaginator.itemSize} hợp đồng</b>
                </div>
                <div class="pull-right no-wrap">
                    <form action="${pageContext.request.contextPath}/customer/contract" method="get">
                        <input type="text" class="form-control long-text-box" name="keyword"
                               placeholder="Tìm kiếm theo mã hợp đồng" value="${param.keyword}"/>
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
                            <th>Ngày bắt đầu</th>
                            <th>Ngày kết thúc</th>
                            <th>Trạng thái</th>
                        </tr>
                        </thead>
                        <tbody>
                        <c:set var="contracts" value="${contractPaginator.getItemsOnCurrentPage(param.page)}"/>
                        <c:choose>
                            <c:when test="${contracts.size() == 0}">
                                <tr>
                                    <td colspan="5" style="vertical-align: middle; text-align: center;">
                                        Không có hợp đồng nào
                                    </td>
                                </tr>
                            </c:when>
                            <c:otherwise>
                                <c:forEach items="${contractPaginator.getItemsOnCurrentPage(param.page)}" var="contract"
                                           varStatus="counter">

                                    <tr>
                                        <td>${(contractPaginator.getCurrentPage(param.page) - 1) * contractPaginator.itemPerPage + counter.count}</td>
                                        <td>
                                            <a href="${pageContext.request.contextPath}/customer/contract?action=detail&code=${contract.contractCode}">
                                                    ${contract.contractCode}</a>
                                        </td>
                                        <td><fmt:formatDate value="${contract.startDate}" pattern="dd/MM/yyyy"/></td>
                                        <td><fmt:formatDate value="${contract.expiredDate}" pattern="dd/MM/yyyy"/></td>
                                        <td>
                                            <c:if test="${contract.status.equalsIgnoreCase('Ready')}">
                                                <span class="label label-success">Sẵn sàng</span>
                                            </c:if>
                                            <c:if test="${contract.status.equalsIgnoreCase('Cancelled')}">
                                                <span class="label label-dark">Đã huỷ</span>
                                            </c:if>
                                            <c:if test="${contract.status.equalsIgnoreCase('No card')}">
                                                <span class="label label-primary">Chưa có thẻ</span>
                                            </c:if>
                                            <c:if test="${contract.status.equalsIgnoreCase('Expired')}">
                                                <span class="label label-danger">Hết hạn</span>
                                            </c:if>
                                            <c:if test="${contract.status.equalsIgnoreCase('Pending')}">
                                                <span class="label label-default">Chưa kích hoạt</span>
                                            </c:if>
                                            <c:if test="${contract.status.equalsIgnoreCase('Request cancel')}">
                                                <span class="label label-warning">Yêu cầu hủy</span>
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
                                <a href="?action=${param.action}&keyword=${param.keyword}&page=1" aria-label="Previous">
                                    <span aria-hidden="true">Đầu</span>
                                </a>
                            </li>
                        </c:if>
                        <c:forEach begin="1" end="${contractPaginator.pageSize}" var="pageNumber">
                            <li ${param.page == pageNumber ||(pageNumber == 1 && empty param.page) ? "class='active'": ""} >
                                <a href="?action=${param.action}&keyword=${param.keyword}&page=${pageNumber}">${pageNumber}</a>
                            </li>
                        </c:forEach>
                        <c:if test="${param.page != contractPaginator.pageSize && contractPaginator.pageSize != 1}">
                            <li>
                                <a href="?action=${param.action}&keyword=${param.keyword}&page=${contractPaginator.pageSize}"
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
                Các trạng thái của hợp đồng:
            </div>
            <div class="panel-body">
                <ul>
                    <li>
                        <span class="label label-default">Chưa kích hoạt</span>
                        Khách hàng đăng ký online nhưng chưa thanh toán.
                    </li>
                    <li>
                        <span class="label label-warning">Yêu cầu hủy</span>
                        Khách hàng đã yêu cầu hủy hợp đồng
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
                        <span class="label label-danger">Hết hạn</span>
                        Thời hạn của hợp đồng sắp hết, cần được gia hạn.
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


<%@ include file="_shared/footer.jsp" %>