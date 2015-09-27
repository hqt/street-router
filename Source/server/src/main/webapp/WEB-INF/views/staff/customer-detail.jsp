<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="_shared/header.jsp" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<div id="wrapper">

    <c:set var="customer" value="${requestScope.CUSTOMER}" scope="request"/>
    <c:set var="listContracts" value="${requestScope.CONTRACTS}"/>

    <%@ include file="_shared/navigation.jsp" %>
    <div id="page-wrapper">
        <div class="row">
            <div class="col-lg-12">
                <h1 class="page-header">
                    ${customer.customerCode}
                </h1>
            </div>
            <!-- /.col-lg-12 -->
        </div>
        <c:if test="${param.info eq 'success'}">
            <div class="text-success text-center">
                Sửa thông tin thành công
            </div>
        </c:if>
        <c:if test="${param.info eq 'fail'}">
            <div class="text-danger text-center">
                Có lỗi xảy ra. Xin thử lại
            </div>
        </c:if>
        <div class="row">
            <div class="col-lg-12">
                <form class="form-horizontal">
                    <jsp:include page="contract/contract-detail-customer.jsp" flush="true"/>
                    <%--/Customer information--%>
                    <br/>

                    <fieldset>
                        <legend>
                            Hợp đồng bảo hiểm
                            <div class="pull-right" style="margin-top: -5px;">
                                <a href="${pageContext.request.contextPath}/staff/contract?action=create&code=${customer.customerCode}"
                                   class="btn btn-xs btn-success">
                                    <i class="fa fa-plus-square"></i>
                                    Hợp đồng mới
                                </a>
                            </div>
                        </legend>

                        <div class="table-responsive">
                            <table class="table table-striped table-bordered table-hover">
                                <thead>
                                <tr>
                                    <th>#</th>
                                    <th>Mã hợp đồng</th>
                                    <th>Loại hợp đồng</th>
                                    <th>Trạng thái</th>
                                </tr>
                                </thead>
                                <tbody>
                                <c:forEach var="contract" items="${listContracts}" varStatus="counter">
                                    <tr>
                                        <td>${counter.count}</td>
                                        <td>
                                            <a href="${pageContext.request.contextPath}/staff/contract?action=detail&code=${contract.contractCode}">
                                                ${contract.contractCode}
                                            </a>
                                        </td>
                                        <td>${contract.micContractTypeByContractTypeId.name}</td>
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
                                </tbody>
                            </table>
                        </div>
                        <!-- /.table-responsive -->
                    </fieldset>
                </form>
                <br/>
                <br/>
                <div class="text-center">
                    <a href="${pageContext.request.contextPath}/staff/customer" type="button" class="btn btn-default">
                        <i class="fa fa-arrow-left"></i> <strong>Danh sách khách hàng</strong>
                    </a>
                </div>
                <br/>
                <br/>
            </div>
            <!-- col -->
        </div>
    </div>
</div>
<!-- /#wrapper -->

<%@ include file="_shared/footer.jsp" %>