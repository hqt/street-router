<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="_shared/header.jsp" %>
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
            <div style="max-width: 800px;margin: 0 auto;">

                <!--      Wizard container        -->

                <div class="wizard-container">
                    <div class="card wizard-card ct-wizard-azzure" id="wizard">

                        <!-- You can switch "ct-wizard-azzure"  with one of the next bright colors: "ct-wizard-blue",
                        "ct-wizard-green", "ct-wizard-orange", "ct-wizard-red" -->

                        <div class="wizard-header">
                            <h3>
                                <p class="lead">Giao dịch không thành công</p>

                                <h3>
                                    <p>${cancel_message}</p>
                                    <a id="link" href='${redirectLink}'>Trở về trang trước đó</a>
                                </h3>
                            </h3>

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
</body>
<% HttpSession nsession = request.getSession(false);
    if (nsession != null)
        nsession.removeAttribute("CONTRACT_CODE");
        nsession.removeAttribute("amountVND");
        nsession.removeAttribute("ACK");
        nsession.removeAttribute("SUCCESS_URL");
        nsession.removeAttribute("EXPRESS_MARK");
        nsession.removeAttribute("payer_id");
        nsession.removeAttribute("checkoutDetails");
        nsession.removeAttribute("checkout");
        nsession.removeAttribute("TOKEN");
%>
<%@ include file="_shared/footer.jsp" %>
</html>

