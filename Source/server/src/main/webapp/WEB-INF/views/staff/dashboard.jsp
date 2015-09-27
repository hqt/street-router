<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="_shared/header.jsp" %>

<div id="wrapper">

    <%@ include file="_shared/navigation.jsp" %>

    <div id="page-wrapper">
        <div class="row">
            <div class="col-lg-12">
                <h3 class="page-header">Thông báo
                <span class="pull-right small">
                    <a href="#" id="view-unread-notifs" onclick="updateNotifs(false)">
                        Chưa đọc
                        <span></span>
                    </a>
                    |
                    <a href="#" id="view-read-notifs" onclick="updateNotifs(true)">
                        Đã đọc
                        <span></span>
                    </a>
                </span>
                </h3>
            </div>
            <!-- /.col-lg-12 -->
        </div>
        <div class="row">
            <div class="col-lg-12">
                <div>
                    <table class="table table-hover">
                        <c:choose>
                            <c:when test="${notifications.size() == 0}">
                                <c:if test="${noCardContractCount == 0}">
                                    <tr>
                                        <td colspan="6" style="vertical-align: middle; text-align: center;">
                                            Không có thông báo mới nào
                                        </td>
                                    </tr>
                                </c:if>
                            </c:when>
                            <c:otherwise>
                                <c:forEach var="notification" items="${notifications}" varStatus="counter">
                                    <tr class="notif-item" data-id="${notification.id}"
                                        data-is-read="${notification.isRead != null ? 'true' : 'false'}">
                                        <td>
                                            <a class="notif-link"
                                               href="/notif?action=markAsRead&id=${notification.id}&redirect=true">
                                                <i class="fa fa-bell-o"></i> ${notification.content}
                                            </a>
                                        </td>
                                        <td>
                                            <fmt:formatDate value="${notification.createdDate}" pattern="dd/MM/yyyy"/>
                                            lúc
                                            <fmt:formatDate value="${notification.createdDate}" type="time"/>
                                        </td>
                                        <td>
                                            <a class="notif-control mark-as-read"
                                               href="javascript:markAsRead(${notification.id})"
                                               data-toggle="tooltip" title="Đánh dấu đã đọc">
                                                <i class="fa fa-check"></i>
                                            </a>
                                            <a class="notif-control mark-as-unread"
                                               href="javascript:markAsUnread(${notification.id})"
                                               data-toggle="tooltip" title="Đánh dấu chưa đọc">
                                                <i class="fa fa-bell"></i>
                                            </a>
                                        </td>
                                    </tr>
                                </c:forEach>
                            </c:otherwise>
                        </c:choose>
                    </table>
                </div>

                <c:if test="${noCardContractCount > 0}">
                    <p>
                        <a href="${pageContext.request.contextPath}/staff/contract?action=&keyword=&status=No%20card">
                            <i class="fa fa-warning text-warning-notif"></i>
                            Có ${noCardContractCount} hợp đồng chưa được cấp thẻ. Nhấn để xem chi tiết</a>
                    </p>
                </c:if>
            </div>
            <!-- /.col-lg-12 -->
        </div>
        <div class="row">
            <div class="col-lg-12">
                <h3 class="page-header">Thông tin chung</h3>
            </div>
            <!-- /.col-lg-12 -->
        </div>
        <!-- /.row -->
        <div class="row">
            <div class="col-sm-6">
                <div class="panel panel-primary">
                    <div class="panel-heading">
                        <div class="row">
                            <div class="col-xs-3">
                                <i class="fa fa-file-text fa-5x"></i>
                            </div>
                            <div class="col-xs-9 text-right">
                                <div class="huge">${activeContractCount}</div>
                                <div>Hợp đồng</div>
                            </div>
                        </div>
                    </div>
                    <a href="${pageContext.request.contextPath}/staff/contract">
                        <div class="panel-footer">
                            <span class="pull-left">Xem danh sách</span>
                            <span class="pull-right"><i class="fa fa-arrow-circle-right"></i></span>

                            <div class="clearfix"></div>
                        </div>
                    </a>
                </div>
            </div>
            <div class="col-sm-6">
                <div class="panel panel-green">
                    <div class="panel-heading">
                        <div class="row">
                            <div class="col-xs-3">
                                <i class="fa fa-gavel fa-5x"></i>
                            </div>
                            <div class="col-xs-9 text-right">
                                <div class="huge">${compensationCount}</div>
                                <div>Yêu cầu bồi thường</div>
                            </div>
                        </div>
                    </div>
                    <a href="${pageContext.request.contextPath}/staff/compensation">
                        <div class="panel-footer">
                            <span class="pull-left">Xem danh sách</span>
                            <span class="pull-right"><i class="fa fa-arrow-circle-right"></i></span>

                            <div class="clearfix"></div>
                        </div>
                    </a>
                </div>
            </div>
            <div class="col-sm-6">
                <div class="panel panel-yellow">
                    <div class="panel-heading">
                        <div class="row">
                            <div class="col-xs-3">
                                <i class="fa fa-exclamation fa-5x"></i>
                            </div>
                            <div class="col-xs-9 text-right">
                                <div class="huge">${newCardRequestCount}</div>
                                <div>Yêu cầu cấp thẻ mới</div>
                            </div>
                        </div>
                    </div>
                    <a href="${pageContext.request.contextPath}/staff/card?action=newCardRequest">
                        <div class="panel-footer">
                            <span class="pull-left">Xem danh sách</span>
                            <span class="pull-right"><i class="fa fa-arrow-circle-right"></i></span>

                            <div class="clearfix"></div>
                        </div>
                    </a>
                </div>
            </div>
            <div class="col-sm-6">
                <div class="panel panel-red">
                    <div class="panel-heading">
                        <div class="row">
                            <div class="col-xs-3">
                                <i class="fa fa-exclamation-circle fa-5x"></i>
                            </div>
                            <div class="col-xs-9 text-right">
                                <div class="huge">${requestCancelContractCount}</div>
                                <div>Yêu cầu hủy hợp đồng</div>
                            </div>
                        </div>
                    </div>
                    <a href="${pageContext.request.contextPath}/staff/contract">
                        <div class="panel-footer">
                            <span class="pull-left">Xem danh sách</span>
                            <span class="pull-right"><i class="fa fa-arrow-circle-right"></i></span>

                            <div class="clearfix"></div>
                        </div>
                    </a>
                </div>
            </div>
        </div>

        <br/>
        <br/>
        <br/>
        <br/>
        <br/>
        <br/>
        <br/>
        <br/>
        <br/>
        <br/>
        <br/>
        <br/>
        <br/>
        <br/>
    </div>
</div>
<!-- /#wrapper -->

<%@ include file="_shared/footer.jsp" %>