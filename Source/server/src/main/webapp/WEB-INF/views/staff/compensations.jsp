<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="_shared/header.jsp" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<div id="wrapper">

    <%@ include file="_shared/navigation.jsp" %>
    <div id="page-wrapper">
        <div class="row">
            <div class="col-lg-12">
                <h1 class="page-header">
                    Yêu cầu bồi thường

                    <div class="pull-right">
                        <a href="${pageContext.request.contextPath}/staff/compensation?action=create"
                           class="btn btn-success">
                            <i class="fa fa-plus"></i>
                            Yêu cầu bồi thường mới
                        </a>
                    </div>
                </h1>
            </div>
            <!-- /.col-lg-12 -->
        </div>
        <!-- /.row -->
        <div class="panel panel-default">

            <c:set var="compensations" value="${compenPaginator.getItemsOnCurrentPage(param.page)}"/>

            <div class="panel-heading">
                <div class="pull-left center-dropdown-button">
                    <b>Có ${compenPaginator.itemSize} yêu cầu bồi thường</b>
                </div>
                <div class="pull-right no-wrap">
                    <form action="${pageContext.request.contextPath}/staff/compensation" method="get">
                        <input type="text" class="form-control long-text-box" name="keyword"
                               placeholder="Tìm kiếm theo mã yêu cầu, tên khách hàng..." value="${param.keyword}"/>
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
                            <th>Mã yêu cầu</th>
                            <th>Ngày yêu cầu</th>
                            <th>Mã hợp đồng</th>
                            <th>Tên khách hàng</th>
                            <th>Trạng thái</th>
                        </tr>
                        </thead>
                        <tbody>
                        <c:choose>
                            <c:when test="${compensations.size() eq 0}">
                                <tr>
                                    <td colspan="6" style="vertical-align: middle; text-align: center;">
                                        Không có yêu cầu bồi thường nào
                                    </td>
                                </tr>
                            </c:when>
                            <c:otherwise>
                                <c:forEach var="compensation" items="${compensations}" varStatus="counter">
                                    <tr>
                                        <td>${(compenPaginator.getCurrentPage(param.page) - 1) * compenPaginator.itemPerPage + counter.count}</td>
                                        <td>
                                            <a href="${pageContext.request.contextPath}/staff/compensation?action=detail&code=${compensation.compensationCode}">
                                                ${compensation.compensationCode}
                                            </a>
                                        </td>
                                        <td>
                                            <fmt:formatDate value="${compensation.createdDate}" pattern="dd/MM/yyyy"/>
                                        </td>
                                        <td>
                                            <a href="${pageContext.request.contextPath}/staff/contract?action=detail&code=${compensation.contractCode}">
                                                ${compensation.contractCode}
                                            </a>
                                        </td>
                                        <td>
                                            <a href="${pageContext.request.contextPath}/staff/customer?action=detail&code=${compensation.micContractByContractCode.micCustomerByCustomerCode.customerCode}">
                                                ${compensation.micContractByContractCode.micCustomerByCustomerCode.name}
                                            </a>
                                        </td>
                                        <td>
                                            <c:choose>
                                                <c:when test="${empty compensation.resolveDate}">
                                                    <span class="label label-default">Chưa giải quyết</span>
                                                </c:when>
                                                <c:otherwise>
                                                    <span class="label label-success">Đã giải quyết</span>
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
                        <c:forEach begin="1" end="${compenPaginator.pageSize}" var="pageNumber">
                            <li ${param.page == pageNumber ||(pageNumber == 1 && empty param.page) ? "class='active'": ""} >
                                <a href="?action=${param.action}&keyword=${param.keyword}&page=${pageNumber}">${pageNumber}</a>
                            </li>
                        </c:forEach>
                        <c:if test="${param.page != compenPaginator.pageSize && compenPaginator.pageSize != 1}">
                            <li>
                                <a href="?action=${param.action}&keyword=${param.keyword}&page=${compenPaginator.pageSize}"
                                   aria-label="Next">
                                    <span aria-hidden="true">Cuối</span>
                                </a>
                            </li>
                        </c:if>
                    </ul>
                </nav>
                <%--/.pagination--%>
            </div>
            <!-- /.panel-body -->
        </div>
        <!-- /.panel -->
    </div>
</div>
<!-- /#wrapper -->

<%@ include file="_shared/footer.jsp" %>