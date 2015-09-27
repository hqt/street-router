<%@ page import="java.util.Date" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt" %>--%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<%--
  Created by IntelliJ IDEA.
  User: PhucNguyen
  Date: 26/05/2015
  Time: 10:39 SA
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="_shared/header.jsp" %>

<div id="wrapper">

    <%@ include file="_shared/navigation.jsp" %>
    <div id="page-wrapper">
        <div class="row">
            <div class="col-lg-12">
                <h1 class="page-header">Thêm hợp đồng mới</h1>
            </div>
            <!-- /.col-lg-12 -->
        </div>
        <div class="row">
            <div class="col-lg-12">
                <p class="text-center">
                    <b>
                        <c:if test="${not empty validateErrors}">
                            <div class="text-danger">
                                <ul>
                                    <c:forEach var="error" items="${validateErrors}">
                                        <li>${error}</li>
                                    </c:forEach>
                                </ul>
                            </div>
                        </c:if>
                    </b></p>
                <p class="text-right"><b>Các ô có dấu * là bắt buộc</b></p>

                <form class="form-horizontal" action="${pageContext.request.contextPath}/customer/contract"
                      method="get">
                    <fieldset>
                        <legend>Thông tin về dịch vụ bảo hiểm</legend>

                        <!-- Contract type -->
                        <div class="form-group">
                            <label class="col-sm-4 control-label">Loại hình bảo hiểm *</label>

                            <c:set var="selectedId" value="${submitted.contractType}"></c:set>
                            <div class="col-sm-7">
                                <select required class="form-control" name="contract:contractType" id="ddlContractType"
                                        onchange="{
                                                var fee = parseFloat(this.options[this.selectedIndex].innerHTML);

                                                var contractDefaultTerm = parseFloat('${configUtils.contractDefaultTerm}');
                                                var realFee = fee * (contractDefaultTerm/12);
                                                if (realFee % 1000 != 0) {
                                                    realFee = realFee - (realFee % 1000) + 1000;
                                                }

                                                $('#txtFeeInput').val(realFee);

                                                realFee = realFee.formatMoney(0,'.','.');
                                                $('#txtFee1').text(realFee);
                                                }">
                                    <option value="" disabled selected style="display:none;">
                                        Vui lòng chọn loại hợp đồng
                                    </option>
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

                        <!-- Start date -->
                        <div class="form-group">
                            <label class="col-sm-4 control-label" for="dateDefault">Thời điểm có hiệu lực *</label>

                            <div class="col-sm-3">
                                <input required type="date" name="contract:startDate"
                                       min="<%=new Date().getYear()+1900%>-<%=(new Date().getMonth()+1)<10?"0"+(new Date().getMonth()+1):(new Date().getMonth()+1)%>-<%=(new Date().getDate())<10?"0"+(new Date().getDate()):(new Date().getDate())%>"
                                       title="Vui lòng chọn ngày bắt đầu"
                                       value="${startDate}"
                                       max="${configUtils.startDateMax}"
                                       class="form-control" id="dateDefault"
                                        />
                            </div>
                        </div>

                        <!-- Expired date -->
                        <div class="form-group">
                            <label class="col-sm-4 control-label">Thời điểm hết hiệu lực:</label>

                            <div class="col-sm-3">
                                <div class="text-value">
                                    ${configUtils.contractDefaultTerm} tháng kể từ ngày cấp
                                </div>
                            </div>

                        </div>

                        <!-- Contract fee -->
                        <div class="form-group">
                            <label class="col-sm-4 control-label">Phí bảo hiểm: </label>

                            <div class="col-sm-3">
                                <div class="text-value">
                                    <b style="color: red"><span id="txtFee1"></span> VNĐ
                                    </b>

                                    <input type="hidden" id="txtFeeInput" name="contract:contractFee"
                                           value="${submitted.contractFee}"/>
                                </div>
                            </div>
                        </div>
                    </fieldset>
                    <%--/Contract information--%>
                    <br/>

                    <fieldset>
                        <legend>Thông tin về xe cơ giới</legend>

                        <!-- Plate & Brand -->
                        <div class="form-group">
                            <label class="col-sm-3 control-label" for="txtPlate">Biển số đăng ký *</label>

                            <div class="col-sm-3">
                                <input id="txtPlate" name="contract:plate" class="form-control input-md"
                                       type="text" required minlength="4" maxlength="15"
                                       title="Vui lòng nhập biển số xe!" placeholder="Ví dụ: 78Y9-15383"
                                       value="${submitted.plate}">
                            </div>

                            <label class="col-sm-2 control-label" for="txtBrand">Nhãn hiệu *</label>

                            <div class="col-sm-3">
                                <input id="txtBrand" name="contract:brand" class="form-control input-md"
                                       type="text" required minlength="2" maxlength="20"
                                       title="Vui lòng nhập nhãn hiệu xe!" placeholder="Ví dụ: Honda"
                                       value="${submitted.brand}">
                            </div>
                        </div>

                        <!-- Engine & Chassis -->
                        <div class="form-group">
                            <label class="col-sm-3 control-label" for="txtEngine">Số máy *</label>

                            <div class="col-sm-3">
                                <input id="txtEngine" name="contract:engine" class="form-control input-md"
                                       type="text" required minlength="2" maxlength="20"
                                       title="Vui lòng nhập số máy xe!"
                                       value="${submitted.engine}">
                            </div>

                            <label class="col-sm-2 control-label" for="txtChassis">Số khung *</label>

                            <div class="col-sm-3">
                                <input id="txtChassis" name="contract:chassis" class="form-control input-md"
                                       type="text" required minlength="2" maxlength="20"
                                       title="Vui lòng nhập số khung xe!"
                                       value="${submitted.chassis}">
                            </div>
                        </div>

                        <!-- Color & Capacity -->
                        <div class="form-group">
                            <label class="col-sm-3 control-label" for="txtCapacity">Dung tích *</label>

                            <div class="col-sm-2">
                                <input id="txtCapacity" name="contract:capacity" class="form-control input-md"
                                       type="text" required minlength="2" maxlength="20"
                                       title="Vui lòng nhập dung tích xe!"
                                       value="${submitted.capacity}">
                            </div>

                            <label class="col-sm-3 control-label" for="txtColor">Màu sơn</label>

                            <div class="col-sm-3">
                                <input id="txtColor" name="contract:color" class="form-control input-md"
                                       type="text" minlength="2" maxlength="20"
                                       title="Vui lòng nhập màu sơn xe!"
                                       value="${submitted.color}">
                            </div>
                        </div>

                        <!-- Vehicle type & Model code -->
                        <div class="form-group">
                            <label class="col-sm-3 control-label" for="txtType">Loại xe</label>

                            <div class="col-sm-3">
                                <input id="txtType" name="contract:type" class="form-control input-md"
                                       type="text" minlength="2" maxlength="20"
                                       title="Vui lòng nhập loại xe!" placeholder="Ví dụ: Hai bánh"
                                       value="${submitted.type}">
                            </div>

                            <label class="col-sm-2 control-label" for="txtModel">Số loại</label>

                            <div class="col-sm-3">
                                <input id="txtModel" name="contract:model" class="form-control input-md"
                                       type="text" minlength="2" maxlength="20"
                                       title="Vui lòng nhập số loại xe!" placeholder="Ví dụ: Air Blade"
                                       value="${submitted.model}">
                            </div>
                        </div>

                        <!-- Year of manufacture & Weight -->
                        <div class="form-group">
                            <label class="col-sm-3 control-label" for="txtYearOfMan">Năm sản xuất</label>

                            <div class="col-sm-2">
                                <input id="txtYearOfMan" name="contract:yearOfMan" class="form-control input-md"
                                       type="number" min="1900" max="2200"
                                       title="Vui lòng nhập năm sản xuất xe!"
                                       value="${submitted.yearOfMan}">
                            </div>

                            <label class="col-sm-3 control-label" for="txtWeight">Tự trọng (kg)</label>

                            <div class="col-sm-2">
                                <input id="txtWeight" name="contract:weight" class="form-control input-md"
                                       type="number" min="1" max="1000"
                                       title="Vui lòng nhập tự trọng của xe!"
                                       value="${submitted.weight}">
                            </div>
                        </div>

                        <!-- Seat capacity -->
                        <div class="form-group">
                            <label class="col-sm-3 control-label" for="txtSeatCapacity">Số người được chở</label>

                            <div class="col-sm-2">
                                <input id="txtSeatCapacity" name="contract:seatCapacity" class="form-control input-md"
                                       type="number" min="1" max="1000"
                                       title="Vui lòng nhập số người cho phép chở!"
                                       value="2" disabled>
                            </div>
                        </div>
                    </fieldset>
                    <div class="text-center">

                        <input type="hidden" name="action" value="reviewNewContract"/>
                        <button type="submit" class="btn btn-success">
                            <i class="fa fa-arrow-right"></i>
                            Thêm hợp đồng
                        </button>
                    </div>
                </form>
                <br/>

                <br/>
                <br/>
                <br/>
                <br/>
                <br/>
                <br/>
                <br/>
                <br/>
                <br/>
                <br/>
                <br/>
                <br/>
            </div>
        </div>
    </div>
</div>

<!-- /#wrapper -->
<%@ include file="_shared/footer.jsp" %>
<script language="javascript">
    $(document).ready(function () {

        var dateStartDefault = $('#dateDefault').val();
        var temp = $('#configTime').val();
        $('#defaultExpired').val(checkDefaultExpired(dateStartDefault, temp));
        $('#dateDefault').change(function () {
            $('#defaultExpired').val(checkDefaultExpired($('#dateDefault').val(), temp));
        });

    });

    function checkDefaultExpired(dateStart, temp) {
        //dateStart : start date of contract
        //temp : config rule default time for contract

        var dt = dateStart.split(/\-|\s/);
        var year = parseInt(dt[0]);
        var month = parseInt(parseInt(dt[1]) + parseInt(temp));
        var day = dt[2].toString();
        var tempYear = parseInt(month / 12).toFixed(0);
        year = parseInt(parseInt(tempYear) + parseInt(dt[0]));
        if (month > 12) {
            month = parseInt(parseInt(month + temp) % 12);
        }

        //nam nhuan
        if ((year % 4 == 0 && year % 100 != 0) || year % 400 == 0) {
            if (month == 2 && day == 29) {
                day = 29;
            }
        }
        else {
            if (month == 2 && day == 29) {
                day = 28;
            }
        }

        month = month.toString().length > 1 ? month : '0' + month;

        day = day.toString().length > 1 ? day : '0' + day;

        return day + '/' + month + '/' + year;
    }
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
    if ($('#dateDefault').val() == "") {
        setInputDate("#dateDefault");
    }

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
    $('#txtFee1').text(parseFloat($('#txtFeeInput').val()).formatMoney(0,'.','.'));
</script>