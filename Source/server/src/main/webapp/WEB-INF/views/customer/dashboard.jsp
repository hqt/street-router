
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="_shared/header.jsp" %>

<div id="wrapper">

    <%@ include file="_shared/navigation.jsp" %>

    <div id="page-wrapper">
        <div class="row">
            <div class="col-lg-12">
                <h3 class="page-header">
                    Thông báo

                <span class="pull-right small">
                    <a href="#" id="view-unread-notifs">
                        Chưa đọc
                        <span></span>
                    </a>
                    |
                    <a href="#" id="view-read-notifs">
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
                <div class="table-responsive">
                    <table class="table table-hover">
                        <c:choose>
                            <c:when test="${notifications.size() == 0}">
                                <tr>
                                    <td colspan="6" style="vertical-align: middle; text-align: center;">
                                        Không có thông báo mới nào
                                    </td>
                                </tr>
                            </c:when>
                            <c:otherwise>
                                <c:forEach var="notification" items="${notifications}" varStatus="counter">
                                    <tr class="notif-item" data-id="${notification.id}"
                                        data-is-read="${notification.isRead != null ? 'true' : 'false'}">
                                        <td>
                                            <a class="notif-link" href="/notif?action=markAsRead&id=${notification.id}&redirect=true">
                                                <i class="fa fa-bell-o"></i> ${notification.content}
                                            </a>
                                        </td>
                                        <td>
                                            <fmt:formatDate value="${notification.createdDate}" pattern="dd/MM/yyyy"/>
                                            lúc
                                            <fmt:formatDate value="${notification.createdDate}" type="time"/>
                                        </td>
                                        <td>
                                            <a class="notif-control mark-as-read" href="javascript:markAsRead(${notification.id})"
                                               data-toggle="tooltip" title="Đánh dấu đã đọc">
                                                <i class="fa fa-check"></i>
                                            </a>
                                            <a class="notif-control mark-as-unread" href="javascript:markAsUnread(${notification.id})"
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
            </div>
            <!-- /.col-lg-12 -->
        </div>
    </div>
</div>
<!-- /#wrapper -->

<%@ include file="_shared/footer.jsp" %>