<%--
  Created by IntelliJ IDEA.
  User: dinhquangtrung
  Date: 5/23/15
  Time: 11:16 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!-- Navigation -->
<nav class="navbar navbar-default navbar-static-top" role="navigation"
     style="margin-bottom: 0;">
    <div class="navbar-header">
        <button type="button" class="navbar-toggle" data-toggle="collapse" data-target=".navbar-collapse">
            <span class="sr-only">Toggle navigation</span>
            <span class="icon-bar"></span>
            <span class="icon-bar"></span>
            <span class="icon-bar"></span>
        </button>
        <a href="${pageContext.request.contextPath}/customer" class="brand-header">
            <img src="${pageContext.request.contextPath}/img/logo.png"/> Công ty bảo hiểm MIC
        </a>
    </div>
    <!-- /.navbar-header -->

    <ul class="nav navbar-top-links navbar-right">
        <li class="dropdown" id="notif-icon">
            <a class="dropdown-toggle" data-toggle="dropdown" href="#">
                <i class="fa fa-bell fa-fw"></i>
                <span class="badge badge-red" style="display: none" id="unread-notifs"></span>
            </a>
            <ul class="dropdown-menu dropdown-alerts" id="notif-list">
                <li>Loading...</li>
            </ul>
            <!-- /.dropdown-alerts -->
        </li>

        <li class="dropdown">
            <a class="dropdown-toggle" data-toggle="dropdown" href="#">
                <i class="fa fa-user fa-fw"></i>
                ${sessionScope.userDto.userEntity.name}
                <i class="fa fa-caret-down"></i>
            </a>
            <ul class="dropdown-menu dropdown-user">
                <li><a href="${pageContext.request.contextPath}/customer">
                    <i class="fa fa-user fa-fw"></i> Thông tin cá nhân</a>
                </li>
                <li class="divider"></li>
                <li><a href="${pageContext.request.contextPath}/user?action=logout"><i class="fa fa-sign-out fa-fw"></i>
                    Đăng xuất</a>
                </li>
            </ul>
            <!-- /.dropdown-user -->
        </li>
        <!-- /.dropdown -->
    </ul>
    <!-- /.navbar-top-links -->

    <div class="navbar-default sidebar" role="navigation">

        <div class="sidebar-nav navbar-collapse">
            <div class="textWellcome customer">
                <img src="${pageContext.request.contextPath}/img/customer.png">
                <p>Khách hàng</p>
                <label>
                    ${sessionScope.userDto.userEntity.name}
                </label>
            </div>
            <ul class="nav" id="side-menu">
                <li>
                    <a href="${pageContext.request.contextPath}/customer">
                        <i class="fa fa-user fa-fw"></i> Thông tin cá nhân</a>
                </li>
                <li>
                    <a href="${pageContext.request.contextPath}/customer/contract">
                        <i class="fa fa-file-text-o fa-fw"></i> Hợp đồng</a>
                </li>
                <%--<li>--%>
                <%--<a href="${pageContext.request.contextPath}/customer/punishment">Lịch Sử Vi Phạm</a>--%>
                <%--</li>--%>
                <%--<li>--%>
                <%--<a href="${pageContext.request.contextPath}/customer/compensation">Lịch Sử Bồi Thường </a>--%>
                <%--</li>--%>
                <li>
                    <a href="${pageContext.request.contextPath}/customer/card">
                        <i class="fa fa-credit-card fa-fw"></i> Thông tin thẻ</a>
                </li>
                <li>
                    <a href="${pageContext.request.contextPath}/customer/card?action=viewNewCardRequests">
                        <i class="fa fa-exclamation fa-fw"></i> Yêu cầu thẻ mới</a>
                </li>
                <li>
                    <a href="${pageContext.request.contextPath}/customer/payment">
                        <i class="fa fa-dollar fa-fw"></i> Lịch sử giao dịch</a>
                </li>
            </ul>
        </div>
        <!-- /.navbar-static-side -->
    </div>
</nav>

<script>
    $(function () {
        var shouldContinueCheck = true;
        $($('#side-menu').find('a').get().reverse()).each(function () {
            if (shouldContinueCheck && location.href.indexOf($(this).attr('href')) > -1) {
                $(this).addClass('active');
                shouldContinueCheck = false;
            }
        });
    });
</script>