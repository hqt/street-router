<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.Date" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/sql" prefix="sql" %>
<!DOCTYPE html>
<html lang="en">
<script src="${pageContext.request.contextPath}/js/jquery.min.js" type="text/javascript"></script>
<script src="${pageContext.request.contextPath}/js/bootstrap.min.js" type="text/javascript"></script>

<script src="${pageContext.request.contextPath}/js/geolocation.js" type="text/javascript"></script>
<!-- Google API Autocomplete for address-->
<script src="https://maps.googleapis.com/maps/api/js?v=3.exp&signed_in=true&libraries=places"></script>

<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>MIC - Bảo hiểm trực tuyến</title>

    <!-- CSS -->
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/fonts.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/bootstrap.min.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/font-awesome.min.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/home-style.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/footer-distributed-with-address-and-phones.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/slider.css">

    <script type="text/javascript" src="${pageContext.request.contextPath}/js/jssor.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/jssor.slider.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/slider.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/global-utils.js"></script>

</head>

<body onload="initialize()">
<!-- Top menu -->
<nav class="navbar  navbar-no-bg" role="navigation">
    <div class="container">
        <div class="navbar-header">
            <button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#top-navbar-1">
                <span class="sr-only">Toggle navigation</span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
            </button>
            <img src="${pageContext.request.contextPath}/img/logoCompany.png" style="height: 80px;">
        </div>
        <!-- Collect the nav links, forms, and other content for toggling -->
        <div class="collapse navbar-collapse" id="top-navbar-1">
            <ul class="nav navbar-nav navbar-right">
                <li>
                    <form action="${pageContext.request.contextPath}/user" method="post">
                        <table id="login-box">
                            <tr>
                                <td class="text-left" style="padding-left: 16px">
                                    Mã khách hàng/email
                                </td>
                                <td class="text-left" style="padding-left: 16px">
                                    Mật Khẩu
                                </td>
                            </tr>
                            <tr>
                                <td class="col-md-5">
                                    <input type="text" name="login:emailorcode" class="form-control"
                                           placeholder=""
                                           required autofocus title="Vui lòng nhập email hoặc mã khách hàng">
                                </td>
                                <td class="col-md-5 ">
                                    <input type="password" name="login:password" class="form-control"
                                           required title="Vui lòng nhập mật khẩu">
                                </td>
                                <td>
                                    <input type="hidden" name="login:redirect" value="/customer"/>
                                    <input type="hidden" name="login:role" value="customer"/>
                                    <input type="hidden" name="action" value="login"/>
                                    <button type="submit" name="btnLogin" class="btn btn-success">Đăng nhập</button>
                                </td>
                            </tr>
                            <tr>
                                <td class="text-left" style="padding-left: 16px">

                                </td>
                                <td class="text-left" style="padding-left: 16px;">
                                    <a href="#" style="color: white">Quên Mật Khẩu?</a>
                                </td>
                            </tr>
                        </table>
                    </form>
                </li>
            </ul>
        </div>
    </div>
</nav>


<div id="slider2_container" style="position: relative; top: 0; left: 0; width: 600px;
        height: 150px;">

    <!-- Loading Screen -->
    <div u="loading" style="position: absolute; top: 0; left: 0;">
        <div style="filter: alpha(opacity=70); opacity:0.7; position: absolute; display: block;
                background-color: #000; top: 0; left: 0;width: 100%;height:100%;">
        </div>
        <div style="position: absolute; display: block; background: url(${pageContext.request.contextPath}/img/loading.gif) no-repeat center center;
                top: 0; left: 0;width: 100%;height:100%;">
        </div>
    </div>

    <!-- Slides Container -->
    <div u="slides" style="cursor: move; position: absolute; left: 0; top: 0; width: 600px; height: 150px;
            overflow: hidden;">
        <div>
            <a u=image href="#"><img src="${pageContext.request.contextPath}/img/landscape/01.jpg"/></a>
        </div>
        <div>
            <a u=image href="#"><img src="${pageContext.request.contextPath}/img/landscape/02.jpg"/></a>
        </div>
        <div>
            <a u=image href="#"><img src="${pageContext.request.contextPath}/img/landscape/03.jpg"/></a>
        </div>
        <div>
            <a u=image href="#"><img src="${pageContext.request.contextPath}/img/landscape/05.jpg"/></a>
        </div>
    </div>
    <!--#region Bullet Navigator Skin Begin -->
    <!-- Help: http://www.jssor.com/development/slider-with-bullet-navigator-jquery.html -->
    <!-- bullet navigator container -->
    <div u="navigator" class="jssorb01" style="bottom: 16px; right: 10px;">
        <!-- bullet navigator item prototype -->
        <div u="prototype"></div>
    </div>
    <!--#endregion Bullet Navigator Skin End -->

    <!-- Arrow Left -->
        <span u="arrowleft" class="jssora05l" style="top: 50px; left: 8px;">
        </span>
    <!-- Arrow Right -->
        <span u="arrowright" class="jssora05r" style="top: 50px; right: 8px;">
        </span>
    <a style="display: none" href="http://www.jssor.com">Bootstrap Slider</a>
</div>

<div style="height: 50px"></div>
<!-- Top content -->
<div class="top-content">
    <div class="container">
        <div class="row">
            <div class="col-md-5">
                <h1>Bảo hiểm xe máy</h1>

                <div id="tab1" class="tab-details" style="display: block;">
                    <img src="${pageContext.request.contextPath}/img/home-img.png"
                         style="width: 100%; margin: 15px 0; border-radius: 20px;"/>

                    <p style="text-align: justify;">
                        Chiếc xe máy là người bạn đồng hành không thể thiếu trong mỗi gia đình người Việt.
                        &nbsp;Tham gia <span style="color:#ff0000;"><strong>Gói bảo hiểm xe máy toàn diện
                    </strong></span> của MIC (bao gồm bảo hiểm cháy nổ xe, bảo hiểm trách nhiệm dân sự
                        chủ xe và bảo hiểm tai nạn người ngồi trên xe) với một khoản chi phí nhỏ
                        <span style="color:#ff0000;"><strong>chỉ khoảng 200.000 đồng</strong></span>, bạn
                        đã có thể bảo vệ toàn diện cho chiếc xe và người thân khi tham gia giao thông.</p>

                    <p>
                        &nbsp;</p>

                    <p class="text-center">
                        <a href="#" class="btn btn-success btn-lg">
                            <i class="fa fa-arrow-right"></i>
                            Xem thông tin các gói bảo hiểm
                        </a>
                    </p>
                </div>
            </div>
            <div class="col-md-7">
                <div class="form-top-left col-md-12" style="padding-top: 0;">
                    <h1>Đăng ký ngay</h1>

                    <p>Tạo hợp đồng mới và thanh toán online</p>
                </div>
                <div class="form-top-right">
                    <i class="fa fa-pencil"></i>
                </div>
                <form id="myForm" role="form" action="${pageContext.request.contextPath}/public/register" method="post">
                    <div class="form-group">

                        <div class="form-group col-md-12">
                            <c:if test="${not empty validateErrors}">
                                <div class="text-danger">
                                    <ul>
                                        <c:forEach var="error" items="${validateErrors}">
                                            <li>${error}</li>
                                        </c:forEach>
                                    </ul>
                                </div>
                            </c:if>
                        </div>
                        <div class="form-group col-md-6">
                            <label>Họ tên *</label>
                            <input required type="text" name="register:name"
                                   pattern="\S[^0-9!@#$%^&*()+=~`]+"
                                   minlength="3" maxlength="80"
                                   title="Vui lòng nhập họ tên"
                                   placeholder="Ví dụ: Nguyễn Văn A"
                                   class="form-control"
                                   value="${submitted.name}">
                        </div>
                        <div class="form-group col-md-6">
                            <label>Email *</label>
                            <input required type="text" name="register:email"
                                   pattern="[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,3}$"
                                   title="Vui lòng nhập Email hợp lệ"
                                   placeholder="Ví dụ: baohiem@micinsurance.vn"
                                   class="form-control" value="${submitted.email}">
                        </div>
                    </div>
                    <div class="form-group">
                        <div class="form-group col-md-6">
                            <label>Số điện thoại *</label>
                            <input required type="tel" name="register:phone"
                                   class="form-control"
                                   pattern="[0-9]+"
                                   minlength="8" maxlength="15"
                                   placeholder="Ví dụ: 0909000999"
                                   title="Vui lòng nhập đúng số điện thoại"
                                   value="${submitted.phone}">
                        </div>
                        <div class="form-group col-md-6">
                            <label>Số CMND/Hộ chiếu</label>
                            <input type="text" name="register:personalId"
                                   class="form-control"
                                   pattern="[0-9]+"
                                   minlength="8" maxlength="15"
                                   placeholder="Ví dụ: 272185738"
                                   title="Vui lòng chỉ nhập số"
                                   value="${submitted.personalId}">
                        </div>
                    </div>
                    <div class="form-group col-md-12">
                        <label>Địa chỉ *</label>
                        <input required type="text" name="register:address" onFocus="geolocate()"
                               maxlength="250" minlength="3"
                               title="Vui lòng nhập địa chỉ"
                               placeholder="Ví dụ: 123A, Điện Biên Phủ, Quận 1, TP.HCM"
                               class="form-control" id="txtAddress"
                               value="${submitted.address}">
                    </div>
                    <div class="form-group">
                        <div class="form-group  col-md-5">
                            <label>Ngày bắt đầu *</label>
                            <input required type="date" name="register:startDate"
                                   min="<%=new Date().getYear()+1900%>-<%=(new Date().getMonth()+1)<10?"0"+(new Date().getMonth()+1):(new Date().getMonth()+1)%>-<%=(new Date().getDate())<10?"0"+(new Date().getDate()):(new Date().getDate())%>"
                                   max="${startDateMax}"
                                   title="Vui lòng chọn ngày bắt đầu"
                                   value="${startDate}"
                                   class="form-control" id="dateDefault"
                                    />
                        </div>
                        <div class="form-group  col-md-7">
                            <label>Hình Thức Bảo Hiểm *</label>
                            <c:set var="selectedId" value="${submitted.contractType}"></c:set>
                            <select required class="form-control" name="register:contractType" id="ddlContractType"
                                    onchange="{
                                            var fee = parseFloat(this.options[this.selectedIndex].innerHTML);
                                            var contractDefaultTerm = parseFloat('${contractDefaultTerm}');
                                            var realFee = fee * (contractDefaultTerm/12);
                                            if (realFee % 1000 != 0) {
                                            realFee = realFee - (realFee % 1000) + 1000;
                                            }
                                            $('#txtFeeInput').val(realFee);

                                            realFee = realFee.formatMoney(0,'.','.');
                                            $('#txtFee1').text(realFee);

                                            }">
                                <c:forEach var="row" items="${listContractType}">
                                    <option <c:if test="${row.id == selectedId}">
                                        selected="selected" </c:if>
                                            label="<c:out value="${row.name}"/>" value="<c:out value="${row.id}"/>">
                                        <c:out value="${row.pricePerYear}"/></option>
                                </c:forEach>
                            </select>

                        </div>
                    </div>
                    <div class="form-group  col-md-6">
                        <p class="form-control-static">
                            <label>Thời hạn:</label>
                            ${contractDefaultTerm} tháng kể từ khi cấp
                        </p>

                        <p class="form-control-static">
                            <label>Phí bảo hiểm: </label>
                            <b style="color: red"><span id="txtFee1"></span> VNĐ
                            </b>

                            <input type="hidden" id="txtFeeInput" name="register:contractFee"
                                   value="${submitted.contractFee}"/>
                        </p>
                        <input type="hidden" name="action" value="register"/>
                        <input type="submit" id="btnNext" name="btnNext" class="btn btn-primary btn-lg"
                               value="Tiếp theo"/>
                    </div>

                    <div class="form-group  col-md-6">
                        <label>Câu hỏi bảo mật *</label>
                        <div>
                        <span style="    color: black;
    font-size: 26px;
    display: inline-block;
    height: 40px;
    padding-top: 5px;
    vertical-align: middle;">
                            <span id="firstNumber"></span> +
                            <span id="secondNumber"></span> = </span>
                            <input style="display: inline-block;width: 100px;"
                                     required type="number" name="register:captchaTotalNumber"
                                     title="Vui lòng trả lời câu hỏi bảo mật"
                                     class="form-control"
                            />
                        </div>
                    </div>
                    <input type="hidden" name="register:plate">
                    <input type="hidden" name="register:brand">
                    <input type="hidden" name="register:chassis">
                    <input type="hidden" name="register:engine">
                    <input type="hidden" name="register:capacity">
                    <input type="hidden" name="register:type">
                    <input type="hidden" name="register:model">
                    <input type="hidden" name="register:color">
                    <input type="hidden" name="register:yearOfMan">
                    <input type="hidden" name="register:weight">
                    <input type="hidden" name="register:seatCapacity">
                    <input id="firstNumberInput" type="hidden" name="register:captchaFirstNumber">
                    <input id="secondNumberInput" type="hidden" name="register:captchaSecondNumber">
                    <script>
                        $(function () {
                            var f = Math.round(Math.random() * 10);
                            var s = Math.round(Math.random() * 10);
                            $('#firstNumber').html(f);
                            $('#secondNumber').html(s);
                            $('#firstNumberInput').val(f);
                            $('#secondNumberInput').val(s);
                        })
                    </script>
                </form>

                <br/>
                <br/>
                <br/>
                <br/>
                <br/>
                <br/>
                <br/>
                &nbsp;
                &nbsp;
                &nbsp;
                <br/>
                <br/>
                <br/>
            </div>
        </div>

    </div>


</div>

<script language="javascript">
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

    function setInputDate(_id) {
        var _dat = document.querySelector(_id);
        var hoy = new Date(),
                d = hoy.getDate(),
                m = hoy.getMonth() + 1,
                y = hoy.getFullYear(),
                data;

        if (d < 10) {
            d = "0" + d;
        }
        ;
        if (m < 10) {
            m = "0" + m;
        }
        ;

        data = y + "-" + m + "-" + d;
        console.log(data);
        _dat.value = data;
    }
    ;
    if ($('#dateDefault').val() == "") {
        setInputDate("#dateDefault");
    }

    if ($('#txtFeeInput').val() == "") {
        var fee = parseFloat('${listContractType[0].pricePerYear}');
        var contractDefaultTerm = parseFloat('${contractDefaultTerm}');
        var realFee = fee * (contractDefaultTerm / 12);
        if (realFee % 1000 != 0) {
            realFee = realFee - (realFee % 1000) + 1000;
        }
        $('#txtFeeInput').val(realFee);
    }
    $('#txtFee1').text(parseFloat($('#txtFeeInput').val()).formatMoney(0, '.', '.'));

</script>
</body>

<%@ include file="_shared/footer.jsp" %>