<%@ page import="java.util.Date" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="_shared/header.jsp" %>

<script src="${pageContext.request.contextPath}/js/geolocation.js" type="text/javascript"></script>
<script src="https://maps.googleapis.com/maps/api/js?v=3.exp&signed_in=true&libraries=places"></script>
<body onload="initialize()">
<div class="image-container set-full-height"
     style="background-image: url('${pageContext.request.contextPath}/img/wizard-city.jpg')">
    <!--   MIC Branding   -->
    <a href="home">
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
                    <form id="myForm" role="form" action="${pageContext.request.contextPath}/public/register"
                          method="post">
                        <div class="card wizard-card ct-wizard-azzure" id="wizard">

                            <!-- You can switch "ct-wizard-azzure"  with one of the next bright colors: "ct-wizard-blue",
                            "ct-wizard-green", "ct-wizard-orange", "ct-wizard-red" -->

                            <div class="wizard-header">
                                <h3>
                                    <b> ĐĂNG KÝ BẢO HIỂM NFC </b><br>
                                </h3>

                            </div>
                            <ul>
                                <li><a id="hrefPersonal" href="#personal" data-toggle="tab"><strong>1. Thông tin cá
                                    nhân </strong></a>
                                </li>
                                <li><a id="hrefVehicle" href="#vehicle" onclick="step2()" data-toggle="tab"><strong>2.
                                    Thông tin xe </strong></a></li>
                                <li><a id="hrefConfirm" href="#contract" onclick="step3()" data-toggle="tab"><strong>3.
                                    Tạo hợp đồng và
                                    thanh toán </strong></a>
                                </li>
                            </ul>

                            <div class="tab-content">

                                <div class="tab-pane" id="personal">
                                    <div class="row">
                                        <c:if test="${not empty validateErrors}">

                                            <div class="col-sm-12">
                                                <div class="text-danger">
                                                    <ul>
                                                        <c:forEach var="error" items="${validateErrors}">
                                                            <li>${error}</li>
                                                        </c:forEach>
                                                    </ul>
                                                </div>
                                            </div>
                                        </c:if>
                                        <div class="col-sm-12">
                                            <h3 class="info-text"> THÔNG TIN CHỦ SỞ HỮU XE </h3>
                                            <h5 class="info-text"> Các ô có dấu * là bắt buộc </h5>
                                        </div>
                                        <div class="col-sm-4 col-sm-offset-1">
                                            <div class="form-group">
                                                <label><b>Họ tên *</b></label>
                                                <input required type="text" class="form-control" name="register:name"
                                                       id="txtName"
                                                       pattern="\S[^0-9]+"
                                                       minlength="3" maxlength="80"
                                                       placeholder="Ví dụ: Nguyễn Văn A"
                                                       title="Vui lòng nhập họ tên!"
                                                       value="${publicHomeFormDto.name}">
                                            </div>
                                        </div>
                                        <div class="col-sm-6">
                                            <div class="form-group">
                                                <label><b>Email *</b></label>
                                                <input required type="text" class="form-control" name="register:email"
                                                       id="txtEmail"
                                                       placeholder="Ví dụ: baohiem@micinsurance.vn"
                                                       pattern="^[A-Za-z0-9._%-]+@[A-Za-z0-9.-]+.[A-Za-z]{2,4}"
                                                       title="Vui lòng nhập email!"
                                                       value="${publicHomeFormDto.email}">
                                            </div>
                                        </div>
                                        <div class="col-sm-7 col-sm-offset-1">
                                            <div class="form-group">
                                                <label><b>Địa chỉ *</b></label><br>
                                                <input required type="text" class="form-control" name="register:address"
                                                       id="txtAddress" onFocus="geolocate()"
                                                       placeholder="Ví dụ: 123A, Điện Biên Phủ, Quận 1, TP.HCM"
                                                       maxlength="250" minlength="3"
                                                       title="Vui lòng nhập địa chỉ!"
                                                       value="${publicHomeFormDto.address}">
                                            </div>
                                        </div>

                                        <div class="col-sm-3">
                                            <div class="form-group">
                                                <label><b>Số điện thoại *</b></label>
                                                <input required type="text" class="form-control" name="register:phone"
                                                       id="txtPhone"
                                                       placeholder="Ví dụ: 0909000999"
                                                       pattern="[0-9]+"
                                                       minlength="8" maxlength="15"
                                                       title="Vui lòng nhập đúng số điện thoại!"
                                                       value="${publicHomeFormDto.phone}">
                                            </div>
                                        </div>
                                        <div class="col-sm-4 col-sm-offset-1">
                                            <div class="form-group">
                                                <label><b>Ngày bắt đầu *</b></label>
                                                <input required type="date" class="form-control"
                                                       name="register:startDate"
                                                       id="txtStartDate"
                                                       min="<%=new Date().getYear()+1900%>-<%=(new Date().getMonth()+1)<10?"0"+(new Date().getMonth()+1):(new Date().getMonth()+1)%>-<%=new Date().getDate()%>"
                                                       max="${startDateMax}"
                                                       placeholder="Ngày bắt đầu tham gia bảo hiểm?"
                                                       value="${startDate}">
                                            </div>
                                        </div>
                                        <div class="col-sm-6">
                                            <div class="form-group">
                                                <label><b>Số CMDN/Hộ chiếu</b></label>
                                                <input type="text" class="form-control" name="register:personalId"
                                                       id="txtPersonalId"
                                                       placeholder="Ví dụ: 272185738"
                                                       pattern="[0-9]+"
                                                       minlength="8" maxlength="15"
                                                       title="Vui lòng chỉ nhập số!"

                                                       value="${publicHomeFormDto.personalId}">
                                            </div>
                                        </div>
                                        <div class="col-sm-10 col-sm-offset-1">
                                            <div class="form-group">
                                                <label><b>Quyền lợi bảo hiểm *</b></label>
                                                <c:set var="mapContract" value="${mapContractType}"></c:set>

                                                <c:set var="selectedId"
                                                       value="${publicHomeFormDto.contractType}"></c:set>
                                                <select class="form-control" name="register:contractType"
                                                        id="ddlContractType"
                                                        onchange="{
                                                                var fee = parseFloat(this.options[this.selectedIndex].innerHTML);
                                                                var contractDefaultTerm = parseFloat('${contractDefaultTerm}');
                                                                var realFee = fee * (contractDefaultTerm/12);
                                                                if (realFee % 1000 != 0) {
                                                                realFee = realFee - (realFee % 1000) + 1000;
                                                                }
                                                                $('#txtFeeInput').val(realFee);

                                                                realFee = realFee.formatMoney(0,'.','.');
                                                                $('#txtFee1').text(realFee + ' VNĐ');
                                                                $('#txtFee2').text(realFee + ' VNĐ');
                                                                }">
                                                    <c:forEach var="row" items="${mapContractType}">
                                                        <option <c:if test="${row.key == selectedId}">
                                                            selected="selected" </c:if>
                                                                label="<c:out value="${row.value.name}" />"
                                                                value="<c:out value="${row.key}"/>">
                                                            <c:out value="${row.value.pricePerYear}"/>
                                                        </option>
                                                    </c:forEach>
                                                </select>
                                            </div>
                                        </div>
                                        <div class="col-sm-5 col-sm-offset-1">
                                            <div class="form-group">
                                                <label><b>Thời hạn bảo hiểm: </b>${contractDefaultTerm} tháng</label>
                                            </div>
                                        </div>
                                        <div class="col-sm-5 col-sm-offset-1">
                                            <div class="form-group">
                                                <label><b>Phí bảo hiểm: </b></label>
                                                <b style="color: red"><span id="txtFee1"></span></b>
                                                <input type="hidden" id="txtFeeInput" name="register:contractFee"
                                                       value="${publicHomeFormDto.contractFee}">

                                            </div>
                                        </div>
                                    </div>
                                    <div class="pull-right">
                                        <input type='button' onclick="step2()"
                                               class='btn btn-fill btn-success btn-wd btn-sm'
                                               name='btnNext' value='Tiếp theo'/>
                                    </div>
                                    <br>
                                </div>
                                <div class="tab-pane" id="vehicle">
                                    <div class="row">
                                        <c:if test="${not empty validateErrors}">

                                            <div class="col-sm-12">
                                                <div class="text-danger">
                                                    <ul>
                                                        <c:forEach var="error" items="${validateErrors}">
                                                            <li>${error}</li>
                                                        </c:forEach>
                                                    </ul>
                                                </div>
                                            </div>
                                        </c:if>
                                        <div class="col-sm-12">
                                            <h3 class="info-text"> THÔNG TIN XE </h3>
                                            <h5 class="info-text"> Quý khách vui lòng nhập thông tin dựa trên đăng ký xe
                                            </h5>
                                            <h5 class="info-text">Các ô có dấu * là bắt buộc</h5>
                                        </div>
                                        <div class="col-sm-5 col-sm-offset-1">
                                            <div class="form-group">
                                                <label><b>Biển số đăng ký *</b></label>
                                                <input required type="text" class="form-control" name="register:plate"
                                                       id="txtPlate"
                                                       minlength="4" maxlength="15"
                                                       placeholder="Ví dụ: 54-Z6 6666"
                                                       title="Vui lòng nhập biển số xe!"
                                                       value="${publicHomeFormDto.plate}">
                                            </div>
                                        </div>
                                        <div class="col-sm-5">
                                            <div class="form-group">
                                                <label><b>Nhãn hiệu *</b></label><br>
                                                <input required type="text" class="form-control" name="register:brand"
                                                       id="txtBrand"
                                                       minlength="2" maxlength="20"
                                                       placeholder="Ví dụ: Honda, Yamaha, Suzuki..."
                                                       title="Vui lòng nhập nhãn hiệu xe!"
                                                       value="${publicHomeFormDto.brand}">
                                            </div>
                                        </div>
                                        <div class="col-sm-4 col-sm-offset-1">
                                            <div class="form-group">
                                                <label><b>Số khung *</b></label>
                                                <input required type="text" class="form-control" name="register:chassis"
                                                       id="txtChassis"
                                                       minlength="2" maxlength="20"
                                                       placeholder="Ví dụ: 1033612"
                                                       title="Vui lòng nhập số khung!"
                                                       value="${publicHomeFormDto.chassis}">

                                            </div>
                                        </div>
                                        <div class="col-sm-3">
                                            <div class="form-group">
                                                <label><b>Số máy *</b></label>
                                                <input required type="text" class="form-control" name="register:engine"
                                                       id="txtEngine"
                                                       minlength="2" maxlength="20"
                                                       placeholder="Ví dụ: 1033612"
                                                       title="Vui lòng nhập số máy!"
                                                       value="${publicHomeFormDto.engine}">
                                            </div>
                                        </div>
                                        <div class="col-sm-3">
                                            <div class="form-group">
                                                <label><b>Dung tích *</b></label>
                                                <input required type="text" class="form-control"
                                                       name="register:capacity"
                                                       id="txtCapacity"
                                                       minlength="2" maxlength="20"
                                                       placeholder="Ví dụ: 110cc"
                                                       title="Dung tích xe phải từ 2 đến 20 ký tự!"
                                                       value="${publicHomeFormDto.capacity}">
                                            </div>
                                        </div>
                                        <div class="col-sm-4 col-sm-offset-1">
                                            <div class="form-group">
                                                <label><b>Số loại</b></label>
                                                <input type="text" class="form-control" name="register:model"
                                                       id="txtModel"
                                                       minlength="2" maxlength="20"
                                                       title="Số loại phải từ 2 đến 20 ký tự!"
                                                       placeholder="Ví dụ: Air Blade, Future, Wave..."
                                                       value="${publicHomeFormDto.model}">
                                            </div>
                                        </div>
                                        <div class="col-sm-3">
                                            <div class="form-group">
                                                <label><b>Loại xe</b></label>
                                                <input type="text" class="form-control" name="register:type"
                                                       id="txtType"
                                                       minlength="2" maxlength="20"
                                                       title="Loại xe phải từ 2 đến 20 ký tự!"
                                                       placeholder="Ví dụ: hai bánh, ba bánh, khác..."
                                                       value="${publicHomeFormDto.type}">
                                            </div>
                                        </div>
                                        <div class="col-sm-3">
                                            <div class="form-group">
                                                <label><b>Màu sơn</b></label>
                                                <input type="text" class="form-control" name="register:color"
                                                       id="txtColor"
                                                       minlength="2" maxlength="20"
                                                       title="Màu xe phải từ 2 đến 20 ký tự!"
                                                       placeholder="Ví dụ: Đỏ"
                                                       value="${publicHomeFormDto.color}">
                                            </div>
                                        </div>
                                        <div class="col-sm-4 col-sm-offset-1">
                                            <div class="form-group">
                                                <label><b>Năm sản xuất </b></label>
                                                <input type="number" class="form-control" name="register:yearOfMan"
                                                       id="txtYearOfMan"
                                                       placeholder="Ví dụ: 2000"
                                                       min="1900"
                                                       max="<%=new Date().getYear() + 1900%>"
                                                       pattern="[0-9]+"
                                                       title="Vui lòng nhập đúng năm từ 1900-<%=new Date().getYear() + 1900%>"
                                                       value="${publicHomeFormDto.yearOfMan}">
                                            </div>
                                        </div>
                                        <div class="col-sm-3">
                                            <div class="form-group">
                                                <label><b>Tự trọng (kg)</b></label>
                                                <input type="number" class="form-control" name="register:weight"
                                                       id="txtWeight"
                                                       min="1" max="1000"
                                                       title="Tự trọng xe từ 1-1000 kg!"
                                                       placeholder="Ví dụ: 100"
                                                       value="${publicHomeFormDto.weight}">
                                            </div>
                                        </div>
                                        <div class="col-sm-3">
                                            <div class="form-group">
                                                <label><b>Số người</b></label>
                                                <input type="number" class="form-control" name="register:seatCapacity"
                                                       id="txtSeatCapacity"
                                                       min="1" max="100"
                                                       title="Số người từ 1-100!"
                                                       placeholder="Ví dụ: 2"
                                                       value="2" disabled>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="pull-right">
                                        <input type='button' onclick="step3()"
                                               class='btn btn-fill btn-success btn-wd btn-sm'
                                               value='Tiếp theo'/>
                                    </div>
                                    <br>
                                </div>

                                <div class="tab-pane" id="contract">
                                    <h3 class="info-text"> THÔNG TIN HỢP ĐỒNG BẢO HIỂM </h3>
                                    <h5 class="info-text">Quý khách vui lòng kiểm tra lại thông tin hợp đồng bảo hiểm
                                        đã đăng ký </h5>

                                    <div class="row text-big">
                                        <div class="col-sm-5 col-sm-offset-1">
                                            <label><b>Tên: </b><span id="txtName1"></span></label>
                                        </div>
                                        <div class="col-sm-5">
                                            <label><b>Số điện thoại: </b></label>
                                            <label id="txtPhone1"></label>

                                        </div>
                                        <div class="col-sm-10 col-sm-offset-1">
                                            <label><b>Địa chỉ: </b> <span id="txtAddress1"></span></label>

                                        </div>

                                        <div class="col-sm-5 col-sm-offset-1">
                                            <label><b>Email: </b></label>
                                            <label id="txtEmail1"></label>
                                        </div>

                                        <div class="col-sm-5">
                                            <label><b>Số CMDN/Hộ chiếu: </b></label>
                                            <label id="txtPersonalId1"></label>
                                        </div>
                                        <div class="col-sm-10 col-sm-offset-1">
                                            <label><b>Quyền lợi bảo hiểm: </b></label>
                                            <label id="ddlContractType1"></label>
                                        </div>
                                        <div class="col-sm-10 col-sm-offset-1">
                                            <label><b>Thời hạn: </b></label>
                                            ${contractDefaultTerm} tháng kể từ ngày bảo hiểm có hiệu lực
                                        </div>
                                        <div class="col-sm-5 col-sm-offset-1">
                                            <label><b>Phí bảo hiểm : </b></label>
                                            <label id="txtFee2"></label>
                                        </div>
                                        <div class="col-sm-5">
                                            <label><b>Ngày bắt đầu: </b></label>
                                            <label id="txtStartDate1"></label>
                                        </div>
                                        <div class="col-sm-5 col-sm-offset-1">
                                            <label><b>Biển số đăng ký: </b></label>
                                            <label id="txtPlate1"></label>
                                        </div>
                                        <div class="col-sm-5">
                                            <label><b>Nhãn hiệu: </b></label>
                                            <label id="txtBrand1"></label>
                                        </div>
                                        <div class="col-sm-5 col-sm-offset-1">
                                            <label><b>Số máy: </b></label>
                                            <label id="txtEngine1"></label>
                                        </div>
                                        <div class="col-sm-5">
                                            <label><b>Số khung: </b></label>
                                            <label id="txtChassis1"></label>
                                        </div>
                                        <div class="col-sm-5 col-sm-offset-1">
                                            <label><b>Dung tích: </b></label>
                                            <label id="txtCapacity1"></label>
                                        </div>
                                        <div class="col-sm-5">
                                            <label><b>Màu sơn: </b></label>
                                            <label id="txtColor1"></label>

                                        </div>
                                        <div class="col-sm-5 col-sm-offset-1">
                                            <label><b>Số loại: </b></label>
                                            <label id="txtModel1"></label>

                                        </div>
                                        <div class="col-sm-5">
                                            <label><b>Loại xe: </b></label>
                                            <label id="txtType1"></label>
                                        </div>

                                        <div class="col-sm-4 col-sm-offset-1">
                                            <label><b>Năm sản xuất: </b></label>
                                            <label id="txtYearOfMan1"></label>
                                        </div>
                                        <div class="col-sm-4">
                                            <label><b>Tự trọng (kg): </b></label>
                                            <label id="txtWeight1"></label>
                                        </div>
                                        <div class="col-sm-3">
                                            <label><b>Số người: </b></label>
                                            <label id="txtSeatCapacity1"></label>
                                        </div>
                                    </div>
                                    <div class="pull-right">
                                        <input type="hidden" name="action" value="createContract"/>
                                        <input type='submit' class='btn btn-fill btn-success btn-wd btn-sm'
                                               name='btnFinish' id="btnFinish" value='Tạo hợp đồng và thanh toán'/>
                                    </div>
                                    <br>
                                </div>

                                <div class="tab-pane" id="payment">
                                    <h3 class="info-text"> PHƯƠNG THỨC THANH TOÁN </h3>
                                    <h5 class="info-text"> Quý khách vui lòng chọn phương thức thanh toán phí bảo
                                        hiểm </h5>

                                    <div class="row">
                                        <div class="col-sm-10 col-sm-offset-1">
                                            <div class="col-sm-4 col-sm-offset-2">
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
                                                                    <input type="submit" value=""
                                                                           style="background-image: url(https://www.paypalobjects.com/webstatic/mktg/logo/pp_cc_mark_74x46.jpg); border: solid 0px #000000; width: 150px; height: 94px;"/>
                                                                </td>

                                                            </tr>
                                                        </table>
                                                        <!-- PayPal Logo -->
                                                    </div>
                                                    <h6>Paypal</h6>
                                                </div>
                                            </div>

                                            <div class="col-sm-4">
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

                                            <div class="col-sm-10 col-sm-offset-1">
                                                <div class="collapse" id="companyAddress">
                                                    <div class="form-group">
                                                        <p class="text-muted"><b>Địa chỉ:</b> Lầu 2, tòa nhà Innovation,
                                                            lô 24, Công viên phần mềm
                                                            Quang Trung, P.Tân Chánh Hiệp, Quận 12, TP. Hồ Chí Minh.
                                                        </p>

                                                        <p class="text-muted"><b>Số điện thoại:</b> 0937337009</p>

                                                        <p class="text-muted"><b>Email:</b> hydrangea8604@gmail.com</p>
                                                        <img src="${pageContext.request.contextPath}/img/map.png"
                                                             alt="Trường đại học FPT"/>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <input type="hidden" name="checkout" value="check out">
                    </form>
                </div>
                <!-- wizard container -->
            </div>
        </div>
        <!-- row -->
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
        $('#txtFee1').text(parseFloat($('#txtFeeInput').val()).formatMoney(0,'.','.') + ' VNĐ');
        $('#txtFee2').text(parseFloat($('#txtFeeInput').val()).formatMoney(0,'.','.') + ' VNĐ');
        function step2() {
            if (document.getElementById('txtName').checkValidity() == false ||
                    document.getElementById('txtAddress').checkValidity() == false ||
                    document.getElementById('txtEmail').checkValidity() == false ||
                    document.getElementById('txtPhone').checkValidity() == false ||
                    document.getElementById('txtPersonalId').checkValidity() == false ||
                    document.getElementById('txtStartDate').checkValidity() == false
            ) {
                document.getElementById('btnFinish').click();
                event.preventDefault();
                event.stopPropagation();
            } else {
                document.getElementById('hrefVehicle').click();
            }
        }
        function step3() {
            if (document.getElementById('myForm').checkValidity() == false) {
                document.getElementById('btnFinish').click();
                event.preventDefault();
                event.stopPropagation();
            } else {
                document.getElementById('hrefConfirm').click();
            }
            $('#txtName1').text($('#txtName').val());
            $('#txtAddress1').text($('#txtAddress').val());
            $('#txtEmail1').text($('#txtEmail').val());
            $('#txtPhone1').text($('#txtPhone').val());
            if ($('#txtPersonalId').val() == "") {
                $('#txtPersonalId1').text("Không có");
                $('#txtPersonalId1').addClass("empty-value");
            }
            else {
                $('#txtPersonalId1').text($('#txtPersonalId').val());
                $('#txtPersonalId1').removeClass("empty-value");

            }
            $('#txtStartDate1').text($('#txtStartDate').val());
            $('#ddlContractType1').text($('#ddlContractType option:selected').attr('label'));
            $('#txtPlate1').text($('#txtPlate').val());
            $('#txtBrand1').text($('#txtBrand').val());
            if ($('#txtModel').val() == "") {
                $('#txtModel1').text("Không có");
                $('#txtModel1').addClass("empty-value");
            }
            else {
                $('#txtModel1').text($('#txtModel').val());
                $('#txtModel1').removeClass("empty-value");

            }
            if ($('#txtType').val() == "") {
                $('#txtType1').text("Không có");
                $('#txtType1').addClass("empty-value");
            }
            else {
                $('#txtType1').text($('#txtType').val());
                $('#txtType1').removeClass("empty-value");
            }
            if ($('#txtColor').val() == "") {
                $('#txtColor1').text("Không có");
                $('#txtColor1').addClass("empty-value");
            }
            else {
                $('#txtColor1').text($('#txtColor').val());
                $('#txtColor1').removeClass("empty-value");
            }

            $('#txtEngine1').text($('#txtEngine').val());
            $('#txtChassis1').text($('#txtChassis').val());
            $('#txtCapacity1').text($('#txtCapacity').val());
            if ($('#txtYearOfMan').val() == "") {
                $('#txtYearOfMan1').text("Không có");
                $('#txtYearOfMan1').addClass("empty-value");
            }
            else {
                $('#txtYearOfMan1').text($('#txtYearOfMan').val());
                $('#txtYearOfMan1').removeClass("empty-value");
            }
            if ($('#txtWeight').val() == "") {
                $('#txtWeight1').text("Không có");
                $('#txtWeight1').addClass("empty-value");
            }
            else {
                $('#txtWeight1').text($('#txtWeight').val());
                $('#txtWeight1').removeClass("empty-value");
            }

            if ($('#txtSeatCapacity').val() == "") {
                $('#txtSeatCapacity1').text("Không có");
                $('#txtSeatCapacity1').addClass("empty-value");
            }
            else {
                $('#txtSeatCapacity1').text($('#txtSeatCapacity').val());
                $('#txtSeatCapacity1').removeClass("empty-value");
            }

        }
    </script>
</div>
</body>
<%@ include file="_shared/footer.jsp" %>