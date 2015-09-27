<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="_shared/header.jsp" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<body>
<div class="image-container set-full-height"
     style="background-image: url('${pageContext.request.contextPath}/img/wizard-city.jpg')">
    <!--   MIC Branding   -->
    <a href="home.html">
        <div class="logo-container">
            <img src="${pageContext.request.contextPath}/img/logoCompany.png"
                 style="height: 70px;">
        </div>
    </a>

    <!--   Big container   -->
    <div class="container">
        <div class="row">
            <div style="max-width: 800px;margin: 0 auto;">

                <!--      Wizard container        -->
                <div class="wizard-container">
                    <form role="form" action="${pageContext.request.contextPath}/public/checkout" method="post">
                        <div class="card wizard-card ct-wizard-azzure" id="wizard">

                            <!-- You can switch "ct-wizard-azzure"  with one of the next bright colors: "ct-wizard-blue",
                            "ct-wizard-green", "ct-wizard-orange", "ct-wizard-red" -->

                            <div class="wizard-header">
                                <h3>
                                    <b> TẠO HỢP ĐỒNG MỚI THÀNH CÔNG </b><br>
                                    <c:if test="${!register.existCustomer}">
                                        <small>Vui lòng ghi nhớ những thông tin cần thiết sau:</small>
                                    </c:if>
                                </h3>
                                <div class="col-sm-9 col-sm-offset-3">
                                    <p style="color: red;">Vui lòng thanh toán để hợp đồng có hiệu lực</p>
                                    <p><b>Mã khách hàng:</b> ${register.customerEntity.customerCode}</p>
                                    <c:if test="${!register.existCustomer}">
                                        <p><b>Mật khẩu:</b>
                                                ${register.emailSuccess ? "<span class='label label-success'>đã gửi</span>" : "<span class='label label-danger'>gửi thất bại</span>"}
                                            (kiểm tra email: ${register.customerEntity.email})
                                            <button data-customer-code="${register.customerEntity.customerCode}" type="button" id="btnResendPassword" class="btn btn-xs btn-primary">
                                                <i class="fa fa-refresh"></i>
                                                Gửi lại email
                                            </button>
                                        </p>
                                    </c:if>

                                    <p><b>Mã hợp đồng:</b> ${register.contractEntity.contractCode}</p>
                                    <input type="hidden" id="amount" value="${register.contractEntity.contractFee}">
                                    <p><b>Phí cần thanh toán:  </b><span id="amount1"></span> VNĐ</p>

                                </div>
                            </div>
                            <div class="row">
                                <br/>
                                <br/>
                                <div class="col-sm-10 col-sm-offset-1 text-center">
                                    <input type="submit" class="btn btn-primary btn-lg" value="Thanh toán ngay bây giờ"/>
                                </div>
                            </div>

                        </div>
                        <input type="hidden" name="L_PAYMENTREQUEST_0_NAME0" value="Đăng ký hợp đồng mới ${register.contractEntity.contractCode}">
                        <input type="hidden" name="L_PAYMENTREQUEST_0_DESC0" value="Đăng ký hợp đồng mới ${register.contractEntity.contractCode}">
                        <input type="hidden" name="L_PAYMENTREQUEST_0_QTY0" value="1">
                        <input type="hidden" name="PAYMENTREQUEST_0_ITEMAMT" value="${register.contractEntity.contractFee}">
                        <input type="hidden" name="PAYMENTREQUEST_0_TAXAMT" value="0">
                        <input type="hidden" name="PAYMENTREQUEST_0_AMT" value="${register.contractEntity.contractFee}">
                        <input type="hidden" name="currencyCodeType" value="USD">
                        <input type="hidden" name="paymentType" value="Sale">
                        <input type="hidden" name="checkout" value="true">
                        <input type="hidden" name="action" value="checkout">
                    </form>
                </div>
                <!-- wizard container -->
            </div>
        </div>
        <!-- row -->
    </div>


<script language="javascript">
    Number.prototype.formatMoney = function(c, d, t){
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


</script>
<script src="/js/resend-email.js"></script>
<%@ include file="_shared/footer.jsp" %>