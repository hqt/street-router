<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="_shared/header.jsp" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<div id="wrapper">

    <%@ include file="_shared/navigation.jsp" %>
    <div id="page-wrapper">
        <div class="row">
            <div class="col-lg-12">
                <h1 class="page-header">Thông báo tai nạn và yêu cầu bồi thường</h1>
            </div>
        </div>
        <div class="row">
            <div class="col-lg-12">
                <div class="well text-success text-center bs-example">
                    <i class="fa fa-check"></i>
                    Đã thêm yêu cầu bồi thường mới thành công!
                </div>

                <c:set var="compensation" value="${requestScope.COMPENSATION}"/>

                <c:if test="${not empty compensation}">
                    <form class="form-horizontal">
                        <fieldset>
                            <!-- Compensation code & contract code -->
                            <div class="form-group">
                                <label class="col-sm-3 control-label">Mã yêu cầu</label>

                                <div class="col-sm-2">
                                    <div class="text-value">
                                        <a href="${pageContext.request.contextPath}/staff/compensation?action=detail&code=${compensation.compensationCode}">
                                            <strong>${compensation.compensationCode}</strong>
                                        </a>
                                    </div>
                                </div>

                                <label class="col-sm-3 control-label">Mã hợp đồng</label>

                                <div class="col-sm-1">
                                    <div class="text-value">
                                        <a href="${pageContext.request.contextPath}/staff/contract?action=detail&code=${compensation.contractCode}">
                                            <strong>${compensation.contractCode}</strong>
                                        </a>
                                    </div>
                                </div>
                            </div>

                            <!-- Created date -->
                            <div class="form-group">
                                <label class="col-sm-3 control-label">Ngày gởi yêu cầu</label>

                                <div class="col-sm-4">
                                    <div class="text-value">
                                        <fmt:formatDate value="${compensation.createdDate}" pattern="dd/MM/yyy"/> lúc
                                        <fmt:formatDate value="${compensation.createdDate}" type="time"/>
                                    </div>
                                </div>
                            </div>

                            <!-- Driver name & phone -->
                            <%--<div class="form-group">--%>
                                <%--<label class="col-sm-3 control-label">Họ tên lái xe</label>--%>

                                <%--<div class="col-sm-3">--%>
                                    <%--<div class="text-value">--%>
                                            <%--${compensation.driverName}--%>
                                    <%--</div>--%>
                                <%--</div>--%>

                                <%--<label class="col-sm-2 control-label">Điện thoại</label>--%>

                                <%--<div class="col-sm-3">--%>
                                    <%--<div class="text-value">--%>
                                            <%--${compensation.driverPhone}--%>
                                    <%--</div>--%>
                                <%--</div>--%>
                            <%--</div>--%>
                        </fieldset>
                    </form>
                </c:if>

                <div class="text-center">
                    <a href="${pageContext.request.contextPath}/staff/compensation?action=detail&code=${compensation.compensationCode}"
                       type="button" class="btn btn-default">
                        <i class="fa fa-arrow-right"></i> <strong>Xem chi tiết yêu cầu này</strong>
                    </a>
                    <br/><br/>
                    <a href="${pageContext.request.contextPath}/staff/compensation" type="button"
                       class="btn btn-default">
                        <i class="fa fa-arrow-left"></i> <strong>Danh sách yêu cầu bồi thường</strong>
                    </a>
                </div>
                <br/>
            </div>
        </div>
    </div>
</div>
<!-- /#wrapper -->

<%@ include file="_shared/footer.jsp" %>