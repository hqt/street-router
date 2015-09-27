<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ include file="_shared/header.jsp" %>
<style>
    label, input {
        width: 100%;
    }
</style>
<body>
<div class="image-container set-full-height"
     style="background-image: url('${pageContext.request.contextPath}/img/wizard-city.jpg')">
    <!--   MIC Branding   -->
    <a href="home">
        <div class="logo-container">
            <img src="${pageContext.request.contextPath}/img/logoCompany.png"
                 style="height: 70px;">
        </div>
    </a>

    <div class="container">
        <div class="row">
            <div style="max-width: 400px;margin: 0 auto;">

                <!--      Wizard container        -->

                <div class="wizard-container">
                    <div class="card wizard-card ct-wizard-azzure" id="wizard">


                        <!-- You can switch "ct-wizard-azzure"  with one of the next bright colors: "ct-wizard-blue",
                        "ct-wizard-green", "ct-wizard-orange", "ct-wizard-red" -->

                        <div class="wizard-header">
                            <h3>
                                <b>ĐĂNG NHẬP</b><br>
                            </h3>
                        </div>
                        <div style="padding: 30px;">

                            <form role="form" action="${pageContext.request.contextPath}/user" method="post">
                                <fieldset>

                                    <c:if test="${not empty validateErrors}">
                                        <div class="text-danger">
                                            <ul>
                                                <c:forEach var="error" items="${validateErrors}">
                                                    <li>${error}</li>
                                                </c:forEach>
                                            </ul>
                                        </div>
                                    </c:if>

                                    <div class="text-danger" id="authorize-failed" style="display: none">
                                        <p>
                                            Bạn không có quyền truy cập vào trang này
                                        </p>
                                    </div>

                                    <div class="form-group">
                                        <label>Email hoặc mã số
                                            <input class="form-control" name="login:emailorcode"
                                                   required autofocus title="Vui lòng nhập email hoặc mã khách hàng"
                                                   value="${submitted.emailorcode}">
                                        </label>
                                    </div>
                                    <div class="form-group">
                                        <label>Mật khẩu
                                            <input class="form-control" name="login:password" type="password"
                                                   required title="Vui lòng nhập mật khẩu"
                                                   value="${submitted.password}">
                                        </label>
                                    </div>
                                    <div class="form-group">
                                        <label>Đăng nhập với tư cách:
                                            <select id="selectRole" name="login:role" class="form-control">
                                                <option value="customer"
                                                ${submitted.role == "customer" ? "selected" : ""}>Khách hàng
                                                </option>
                                                <option value="staff"
                                                ${submitted.role == "staff" ? "selected" : ""}>Nhân viên MIC
                                                </option>
                                            </select>
                                        </label>
                                    </div>
                                    <input type="hidden" id="redirectId" name="login:redirect"
                                           value="${submitted.redirect}"/>
                                    <input type="hidden" name="action" value="login"/>
                                    <!-- Change this to a button or input when using this as a form -->
                                    <button type="submit" class="btn btn-lg btn-success btn-block">Đăng nhập</button>
                                </fieldset>
                            </form>


                        </div>


                    </div>
                </div>
                <!-- wizard container -->
            </div>
        </div>
        <!-- row -->
    </div>
    <!--  big container -->
</div>

<!-- jQuery -->
<script src="${pageContext.request.contextPath}/js/jquery.min.js"></script>
<!-- Bootstrap Core JavaScript -->
<script src="${pageContext.request.contextPath}/js/bootstrap.min.js"></script>

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

        // Fill redirect value
        var $redirect = $('#redirectId');
        var redirect = getQueryVariable('redirect');
        if ($redirect.val() == "" && redirect) {
            $redirect.val(redirect);
            if (redirect.substr(0, 8) == "%2Fstaff") {
                $('#selectRole').val("staff");
            } else if (redirect.substr(0, 11) == "%2Fcustomer") {
                $('#selectRole').val("customer");
            }
        }


        // Show authorize message
        if (getQueryVariable("authorize")) {
            $('#authorize-failed').show();
        }

        // Auto select staff if possible
        var refrole = getQueryVariable("refrole");
        if (refrole) {
            $('#selectRole').val(refrole);
        }
    });
</script>

</body>

<%@ include file="_shared/footer.jsp" %>