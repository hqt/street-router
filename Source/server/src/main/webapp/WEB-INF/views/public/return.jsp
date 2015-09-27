<%@ page import="java.util.HashMap" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="_shared/header.jsp" %>
<body>
<div class="image-container set-full-height"
     style="background-image: url('${pageContext.request.contextPath}/img/wizard-city.jpg')">
    <!--   MIC Branding   -->
    <a href="${pageContext.request.contextPath}/">
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
                                    <% if((request.getAttribute("ack").equals("SUCCESS") || request.getAttribute("ack").equals("SUCCESSWITHWARNING") ) ) {
									HashMap<String,String> result = (HashMap<String,String>) request.getAttribute("result");
								%>


                                <!-- Display the Transaction Details-->
                                <p class="lead"> ${message} </p>

                                <p>Mã giao dịch PayPal: <%=result.get("PAYMENTINFO_0_TRANSACTIONID")%>
                                </p>
                                <input type="hidden" id="amount" value="${amountVND}">

                                <p>Số tiền đã thanh toán: <span id="amount1"></span> VND
                                </p>

                                <h3>
                                    <a id="link" href='${redirectLink}'>Trở về trang trước đó</a>
                                </h3>

                                    <% } else {
									HashMap<String,String> result = (HashMap<String,String>) request.getAttribute("result");
								%>


                                <!-- Display the Transaction Details-->
                                <p class="lead"> Thanh toán không thành công! </p>

                                <p class="lead"> Tình trạng giao dịch: <%=result.get("PAYMENTINFO_0_PAYMENTSTATUS")%>
                                </p>


                                    <% } %>
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
<script language="javascript">
    //    function redirect(){
    //        window.location = $('#link').attr('href');
    //    }
    //
    //    setTimeout(redirect, 5000);
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
</script>
<%@ include file="_shared/footer.jsp" %>
</html>
