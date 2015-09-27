<%--
  Created by IntelliJ IDEA.
  User: dinhquangtrung
  Date: 5/23/15
  Time: 11:16 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<!-- Navigation -->
<nav class="navbar navbar-default navbar-static-top" role="navigation" style="margin-bottom: 0">
    <div class="navbar-header">
        <button type="button"
                class="navbar-toggle"
                data-toggle="collapse"
                data-target=".navbar-collapse">
            <span class="sr-only">Toggle navigation</span>
            <span class="icon-bar"></span>
            <span class="icon-bar"></span>
            <span class="icon-bar"></span>
        </button>
        <a href="${pageContext.request.contextPath}/admin">
            <img src="${pageContext.request.contextPath}/img/logoCompany.png"/>
        </a>
    </div>
    <!-- /.navbar-header -->

    <%--<ul class="nav navbar-top-links navbar-right">--%>
        <%--<li class="dropdown">--%>
            <%--<a class="dropdown-toggle" data-toggle="dropdown" href="#">--%>
                <%--<i class="fa fa-user fa-fw"></i>--%>
                <%--${sessionScope.userDto.userEntity.name}--%>
                <%--<i class="fa fa-caret-down"></i>--%>
            <%--</a>--%>
            <%--<ul class="dropdown-menu dropdown-user">--%>
                <%--<li><a href="${pageContext.request.contextPath}/staff"><i class="fa fa-user fa-fw"></i> Thông tin cá nhân</a>--%>
                <%--</li>--%>
                <%--<li class="divider"></li>--%>
                <%--<li><a href="${pageContext.request.contextPath}/user?action=logout"><i class="fa fa-sign-out fa-fw"></i> Đăng xuất</a>--%>
                <%--</li>--%>
            <%--</ul>--%>
            <%--<!-- /.dropdown-user -->--%>
        <%--</li>--%>
        <%--<!-- /.dropdown -->--%>
    <%--</ul>--%>

    <div class="navbar-default sidebar" role="navigation">
        <div class="sidebar-nav navbar-collapse">
            <ul class="nav" id="side-menu">
                <li>
                    <a href="${pageContext.request.contextPath}/admin/staff">
                        <i class="fa fa-key fa-fw"></i> Nhân viên
                    </a>
                </li>
                <li>
                    <a href="${pageContext.request.contextPath}/admin/config">
                        <i class="fa fa-key fa-fw"></i> Thiết lập
                    </a>
                </li>
                <li>
                    <a href="${pageContext.request.contextPath}/admin/contractType">
                        <i class="fa fa-key fa-fw"></i> Loại hợp đồng
                    </a>
                </li>
            </ul>
        </div>
        <!-- /.sidebar-collapse -->
    </div>
    <!-- /.navbar-static-side -->
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