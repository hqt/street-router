<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="_shared/header.jsp" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<div id="wrapper">

    <%@ include file="_shared/navigation.jsp" %>
    <div id="page-wrapper">
        <div class="row">
            <div class="col-lg-12">
                <h1 class="page-header">Thẻ đã phát hành</h1>
            </div>
            <!-- /.col-lg-12 -->
        </div>
        <!-- /.row -->
        <div class="row">
            <div class="panel panel-default">

                <c:set var="cards" value="${cardPaginator.getItemsOnCurrentPage(param.page)}"/>

                <div class="panel-heading">
                    <div class="pull-left center-dropdown-button">
                        <b>Có ${cardPaginator.itemSize} thẻ đã phát hành</b>
                    </div>
                    <div class="pull-right no-wrap">
                        <form action="${pageContext.request.contextPath}/staff/card" method="get">
                            <input type="text" class="form-control long-text-box" name="keyword"
                                   placeholder="Tìm kiếm theo mã thẻ" value="${param.keyword}"/>
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
                                <th>Mã thẻ</th>
                                <th>Chủ thẻ</th>
                                <th>Mã hợp đồng</th>
                                <th>Ngày kích hoạt</th>
                                <th>Trạng thái</th>
                            </tr>
                            </thead>
                            <tbody>
                            <c:choose>
                                <c:when test="${cards.size() eq 0}">
                                    <tr>
                                        <td colspan="6" style="vertical-align: middle; text-align: center;">
                                            Không có thẻ nào
                                        </td>
                                    </tr>
                                </c:when>
                                <c:otherwise>
                                    <c:forEach var="cardEntity" items="${cards}" varStatus="counter">
                                        <c:set var="card" value="${cardEntity.micCardInstancesByCardId.get(0)}"/>
                                        <tr>
                                            <td>${(cardPaginator.getCurrentPage(param.page) - 1) * cardPaginator.itemPerPage + counter.count}</td>
                                            <td>
                                                <a href="${pageContext.request.contextPath}/staff/card?action=detail&cardId=${card.cardId}">
                                                        ${card.cardId}
                                                </a>
                                            </td>
                                            <td>
                                                <a href="${pageContext.request.contextPath}/staff/customer?action=detail&code=${card.micContractByContractCode.micCustomerByCustomerCode.customerCode}">
                                                        ${card.micContractByContractCode.micCustomerByCustomerCode.name}
                                                </a>
                                            </td>
                                            <td>
                                                <a href="${pageContext.request.contextPath}/staff/contract?action=detail&code=${card.contractCode}">
                                                        ${card.contractCode}
                                                </a>
                                            </td>
                                            <td>
                                                <fmt:formatDate value="${card.activatedDate}" pattern="dd/MM/yyyy"/>
                                            </td>
                                            <td>
                                                <c:choose>
                                                    <c:when test="${card.micCardByCardId.status == 0}">
                                                        <span class="label label-info">Có thể cấp lại</span>
                                                    </c:when>
                                                    <c:otherwise>
                                                        <c:choose>
                                                            <c:when test="${empty card.deactivatedDate}">
                                                                <span class="label label-success">Hoạt động</span>
                                                            </c:when>
                                                            <c:otherwise>
                                                                <span class="label label-danger">Ngưng hoạt động</span>
                                                                <c:set var="canRecycle" value="${true}"/>
                                                            </c:otherwise>
                                                        </c:choose>
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
                                    <a href="?action=${param.action}&keyword=${param.keyword}&page=1" aria-label="Previous">
                                        <span aria-hidden="true">Đầu</span>
                                    </a>
                                </li>
                            </c:if>
                            <c:forEach begin="1" end="${cardPaginator.pageSize}" var="pageNumber">
                                <li ${param.page == pageNumber ||(pageNumber == 1 && empty param.page) ? "class='active'": ""} >
                                    <a href="?action=${param.action}&keyword=${param.keyword}&page=${pageNumber}">${pageNumber}</a>
                                </li>
                            </c:forEach>
                            <c:if test="${param.page != cardPaginator.pageSize && cardPaginator.pageSize != 1}">
                                <li>
                                    <a href="?action=${param.action}&keyword=${param.keyword}&page=${cardPaginator.pageSize}"
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
        </div>
    </div>
</div>
</div>
<!-- /#wrapper -->

<%@ include file="_shared/footer.jsp" %>