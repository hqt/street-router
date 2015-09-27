<%@ page import="java.util.HashMap" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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
                                <b> THANH TOÁN</b><br>



                            </h3>
                            <form class="form" action="${pageContext.request.contextPath}/public/checkout" method="get">
                                <div class="row">
                                    <div class="col-sm-9 col-sm-offset-2">
                                        <% HashMap result = (HashMap) request.getAttribute("result"); %>
                                        <div class="col-sm-9 col-sm-offset-1">
                                            <p class="text-center"><b>Nội dung thanh toán: </b> ${param.L_PAYMENTREQUEST_0_DESC0}</p>
                                            <input type="hidden" id="amount"
                                                   value="${requestScope.result['PAYMENTREQUEST_0_AMT']}">

                                            <p class="text-center"><b>Tổng tiền phải trả: </b><span id="amount1"></span> VNĐ
                                            </p>
                                            <p class="lead">Xin lựa chọn phương thức thanh toán sau:</p>
                                        </div>

                                        <div class="col-sm-6">
                                            <div class="choice" data-toggle="wizard-radio" rel="tooltip"
                                                 title="Chọn nếu quý khách thanh toán qua Paypal">
                                                <input type="radio" name="type" value="Paypal">

                                                <div class="form-group">
                                                    <!-- PayPal Logo -->
                                                    <table border="0" cellpadding="6" cellspacing="0"
                                                           align="center">
                                                        <tr>
                                                            <td align="center"></td>
                                                        </tr>
                                                        <tr>
                                                            <td align="center">
                                                                <input type="submit" id="checkout" value=""
                                                                       style="background-image: url(https://www.paypalobjects.com/webstatic/mktg/logo/pp_cc_mark_74x46.jpg); border: solid 0px #000000; width: 150px; height: 94px;"/>
                                                            </td>

                                                        </tr>
                                                    </table>
                                                    <!-- PayPal Logo -->
                                                </div>
                                                <h6>Paypal</h6>
                                            </div>
                                        </div>

                                        <div class="col-sm-5">
                                            <a href="#companyAddress" data-toggle="collapse"
                                               aria-expanded="false" aria-controls="companyAddress">
                                                <div class="choice" data-toggle="wizard-radio" rel="tooltip"
                                                     title="Chọn nếu quý khách thanh toán trực tiếp">
                                                    <input type="radio" name="type" value="Trực tiếp">

                                                    <div class="icon">
                                                        <i class="fa fa-building"></i>
                                                    </div>
                                                    <h6>Trực tiếp</h6>
                                                </div>
                                            </a>
                                        </div>
                                        <div class="col-sm-12">
                                            <div class="collapse" id="companyAddress">
                                                <div class="form-group">
                                                    <p class="lead">Quý khách vui lòng đi đến địa chỉ sau đây để thanh toán:</p>
                                                    <p class="text-muted"><b>Địa chỉ:</b> Lầu 2, tòa nhà Innovation,
                                                        lô 24, Công viên phần mềm
                                                        Quang Trung, P.Tân Chánh Hiệp, Quận 12, TP. Hồ Chí Minh.
                                                    </p>

                                                    <p class="text-muted"><b>Số điện thoại:</b> 0937337009</p>

                                                    <p class="text-muted"><b>Email:</b> hydrangea8604@gmail.com</p>
                                                    <%--<img src="${pageContext.request.contextPath}/img/map.png" alt="Trường đại học FPT"/>--%>
                                                    <iframe
                                                            width="600"
                                                            height="450"
                                                            frameborder="0" style="border:0"
                                                            src="https://www.google.com/maps/embed/v1/place?key=AIzaSyBHWaWHbQJEFOvVmZw7tcR0qIGQQUoxsKM&q=Trường Đại học FPT Tân Chánh Hiệp Hồ Chí Minh Việt Nam">
                                                    </iframe>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                                <input type="hidden" name="action" value="checkout">

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
</body>
<script type="text/javascript">
    window.paypalCheckoutReady = function () {
        paypal.checkout.setup('<%= new com.fpt.mic.micweb.model.dto.PayPal().getGvAPIUserName() %>', {
            button: 'checkout',
            environment: '<%= new com.fpt.mic.micweb.model.dto.PayPal().getEnvironment() %>'
        });
        Number.prototype.formatMoney = function (c, d, t) {
            var n = this,
                    c = isNaN(c = Math.abs(c)) ? 2 : c,
                    d = d == undefined ? "." : d,
                    t = t == undefined ? "," : t,
                    s = n < 0 ? "-" : "",
                    i = parseInt(n = Math.abs(+n || 0).toFixed(c)) + "",
                    j = (j = i.length) > 3 ? j % 3 : 0;
            return s + (j ? i.substr(0, j) + t : "") + i.substr(j).replace(/(\d{3})(?=\d)/g, "$1" + t) + (c ? d + Math.abs(n - i).toFixed(c).slice(2) : "");
        };
        $('#amount1').text(parseFloat($('#amount').val()).formatMoney(0,'.','.'));
    };
</script>
<script src="//www.paypalobjects.com/api/checkout.js" async></script>
<script>
    $(function () {
        $('.image-container').height('auto');
    });
</script>
<%@ include file="_shared/footer.jsp" %>