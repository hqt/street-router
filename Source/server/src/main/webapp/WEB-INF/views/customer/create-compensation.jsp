<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="_shared/header.jsp" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<div id="wrapper">

    <%@ include file="_shared/navigation.jsp" %>


    <div id="page-wrapper">
        <div class="row">
            <div class="col-lg-12">
                <h1 class="page-header">Yêu cầu bồi thường cho hợp đồng ${contractCode}

                </h1>
            </div>
            <!-- /.col-lg-12 -->
        </div>
        <div class="row">
            <div class="col-lg-12">
                <form class="form-horizontal">
                    <fieldset>
                        <legend>Thông tin hợp đồng
                        </legend>

                        <!-- Text input-->
                        <div class="form-group">
                            <label class="col-sm-4 control-label">Mã hợp đồng</label>

                            <div class="col-sm-6">
                                <div class="text-value">
                                    <a href="${pageContext.request.contextPath}/customer/contract?action=detail&code=${contractCode}">
                                        <b>${contractCode}</b></a>
                                </div>
                            </div>
                        </div>

                        <div class="form-group">
                            <label class="col-sm-4 control-label">Tên khách hàng</label>

                            <div class="col-sm-6">
                                <div class="text-value">
                                    <b>${sessionScope.userDto.userEntity.name}</b>
                                </div>
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-sm-6 control-label">
                                <a style="padding-right: 9px;"
                                   href="${pageContext.request.contextPath}/file/Yeu cau boi thuong 84182_TT126BTC6.DOC"
                                   download>
                                    Tải mẫu văn bản yêu cầu bồi thường
                                </a>

                            </label>


                        </div>

                    </fieldset>
                </form>
            </div>

            <div class="col-lg-12">
                <legend>Hay điền trực tiếp thông tin yêu cầu bồi thường dưới đây <br/>
                </legend>

                <c:if test="${not empty validateErrors}">
                    <div class="text-danger">
                        <ul>
                            <c:forEach var="error" items="${validateErrors}">
                                <li>${error}</li>
                            </c:forEach>
                        </ul>
                    </div>
                </c:if>

                <p class="text-right"><b>Các ô có dấu * là bắt buộc</b></p>

                <div class="alert alert-info">
                    <div class="text text-field" style="font-size: medium">
                        <strong>Lưu ý quan trọng</strong>:
                        Người kê khai phải kê khai đầy đủ và trung thực các nội dung dưới đây.
                        Doanh nghiệp bảo hiểm có thể từ chối một phần số tiền bồi thường nếu nhận được nội dung kê
                        khai thiếu trung thực
                    </div>
                </div>
                <br/>
                <!-- Form to create new compensation -->
                <form action="${pageContext.request.contextPath}/customer/compensation" method="post"
                      class="form-horizontal">
                    <input type="hidden" name="compensation:inputMethod" value="input"/>

                    <fieldset>
                        <!-- Driver name & Phone number -->
                        <div class="form-group">
                            <label class="col-sm-3 control-label" for="driverName">Họ tên lái xe *</label>

                            <div class="col-sm-4">
                                <input id="driverName" name="compensation:driverName" class="form-control input-md"
                                       type="text" required minlength="3" maxlength="80"
                                       pattern="^([^0-9`~!@#$%^&*,.<>;':/|{}()=_+-]+)$"
                                       value="${sessionScope.userDto.userEntity.name}" title="Vui lòng nhập họ tên"
                                       placeholder="Ví dụ: Nguyễn Văn A">
                            </div>

                            <label class="col-sm-2 control-label" for="driverPhone">Điện thoại *</label>

                            <div class="col-sm-3">
                                <input id="driverPhone" name="compensation:driverPhone" class="form-control input-md"
                                       type="tel" required minlength="8" maxlength="15"
                                       pattern="[0-9]+" title="Vui lòng chỉ nhập số"
                                       value="${customer.phone}" placeholder="Ví dụ: 0933270393">
                            </div>
                        </div>

                        <!-- Address input -->
                        <div class="form-group">
                            <label class="col-sm-3 control-label" for="driverAddress">Địa chỉ *</label>

                            <div class="col-sm-8">
                                <input id="driverAddress" name="compensation:driverAddress"
                                       class="form-control input-md"
                                       type="text" required minlength="3" maxlength="250"
                                       value="${customer.address}" onfocus="geolocate()"
                                       title="Vui lòng nhập địa chỉ"
                                       placeholder="Ví dụ: 123A, Điện Biên Phủ, Quận 1, TP.HCM">
                            </div>
                        </div>

                        <!-- Driver license -->
                        <div class="form-group">
                            <label class="col-sm-3 control-label" for="licenseNumber">Giấy phép lái xe số *</label>

                            <div class="col-sm-3">
                                <input id="licenseNumber" name="compensation:licenseNumber" type="text"
                                       class="form-control input-md"
                                       required minlength="10" maxlength="15" placeholder="Ví dụ: 740118000357"
                                       value="${submitted.licenseNumber}" title="Vui lòng nhập số GPLX">
                            </div>

                            <label class="col-sm-3 control-label" for="licenseType">Hạng *</label>

                            <div class="col-sm-2">
                                <input id="licenseType" name="compensation:licenseType"
                                       class="form-control input-md" type="text"
                                       required minlength="1" maxlength="15" placeholder="Ví dụ: A1"
                                       value="${submitted.licenseType}" title="Vui lòng nhập hạng GPLX">
                            </div>
                        </div>

                        <!-- Plate & Capacity -->
                        <div class="form-group">
                            <label class="col-sm-3 control-label" for="plate">Biển số xe gây tai nạn *</label>

                            <div class="col-sm-3">
                                <input id="plate" name="compensation:plate" class="form-control input-md"
                                       type="text" required minlength="4" maxlength="15"
                                       title="Vui lòng nhập biển số xe!" placeholder="Ví dụ: 78Y9-15383"
                                        >
                            </div>
                            <label class="col-sm-3 control-label" for="vehicleCapacity">
                                Trọng tải/số chỗ ngồi *
                            </label>

                            <div class="col-sm-3">
                                <div class="input-group">
                                    <input id="vehicleCapacity" name="compensation:vehicleCapacity"
                                           class="form-control input-md"
                                           type="text" required minlength="1" maxlength="20"
                                           title="Vui lòng nhập trọng tải hoặc số chỗ ngồi!" placeholder="Ví dụ: 7"
                                            >
                                    <span class="input-group-addon" id="basic-addon">(tấn/chỗ)</span>
                                </div>
                            </div>
                        </div>

                        <!-- Accident date -->
                        <div class="form-group">
                            <label class="col-sm-3 control-label" for="accidentDate">Ngày xảy ra tai nạn *</label>

                            <div class="col-sm-3">
                                <input id="accidentDate" name="compensation:accidentDate"
                                       value="<fmt:formatDate value="${submitted.accidentDate}" pattern="yyyy-MM-dd" />"
                                       class="form-control input-md" type="date" required>
                            </div>
                        </div>

                        <!-- Accident place -->
                        <div class="form-group">
                            <label class="col-sm-3 control-label" for="accidentPlace">Nơi xảy ra tai nạn *</label>

                            <div class="col-sm-8">
                                <input id="accidentPlace" name="compensation:accidentPlace"
                                       class="form-control input-md"
                                       type="text" required minlength="3" maxlength="250"
                                       value="${submitted.accidentPlace}" onfocus="geolocate()"
                                       title="Vui lòng nhập nơi xảy ra tai nạn"
                                       placeholder="Ví dụ: 405, Hoàng Văn Thụ, Quận Tân Bình, TP.HCM">
                            </div>
                        </div>

                        <!-- Control department -->
                        <div class="form-group">
                            <label class="col-sm-3 control-label" for="controlDepartment">
                                CQCA giải quyết *
                            </label>

                            <div class="col-sm-4">
                                <input id="controlDepartment" name="compensation:controlDepartment"
                                       class="form-control input-md"
                                       type="text" required minlength="3" maxlength="250"
                                       value="${submitted.controlDepartment}"
                                       title="Vui lòng nhập cơ quan công an giải quyết tai nạn"
                                       placeholder="Ví dụ: Đội CSGT Bình Triệu">
                            </div>
                        </div>

                        <!-- Description -->
                        <div class="form-group">
                            <label class="col-sm-3 control-label" for="description">Diễn biến tai nạn *</label>

                            <div class="col-sm-8">
                                <textarea id="description" name="compensation:description" rows="2"
                                          required maxlength="2000"
                                          title="Vui lòng nhập diễn biến và nguyên nhân tai nạn"
                                          class="form-control input-md valueInput">${submitted.description}</textarea>
                            </div>
                        </div>

                        <!-- Human damage -->
                        <div class="form-group">
                            <label class="col-sm-3 control-label" for="humanDamage">Thiệt hại về người *</label>

                            <div class="col-sm-8">
                                <textarea id="humanDamage" name="compensation:humanDamage" rows="2"
                                          required maxlength="2000" title="Vui lòng nhập tình hình thiệt hại về người"
                                          class="form-control input-md valueInput">${submitted.humanDamage}</textarea>
                            </div>
                        </div>

                        <!-- Asset damage -->
                        <div class="form-group">
                            <label class="col-sm-3 control-label" for="assetDamage">Thiệt hại về tài sản *</label>

                            <div class="col-sm-8">
                                <textarea id="assetDamage" name="compensation:assetDamage" rows="2"
                                          required maxlength="2000" title="Vui lòng nhập tình hình thiệt hại về tài sản"
                                          class="form-control input-md valueInput">${submitted.assetDamage}</textarea>
                            </div>
                        </div>

                        <!-- Observer -->
                        <div class="form-group">
                            <label class="col-sm-3 control-label" for="observer">Người làm chứng *</label>

                            <div class="col-sm-4">
                                <input id="observer" name="compensation:observer" class="form-control input-md"
                                       type="text" required minlength="3" maxlength="80"
                                       pattern="^([^0-9`~!@#$%^&*,.<>;':/|{}()=_+-]+)$"
                                       value="${submitted.observer}" title="Vui lòng nhập tên người làm chứng"
                                       placeholder="Ví dụ: Đặng Thu Thảo">
                            </div>
                        </div>

                        <!-- Observer address -->
                        <div class="form-group">
                            <label class="col-sm-3 control-label" for="observerAddress">
                                Địa chỉ liên hệ *
                            </label>

                            <div class="col-sm-8">
                                <input id="observerAddress" name="compensation:observerAddress"
                                       class="form-control input-md"
                                       type="text" required minlength="3" maxlength="250"
                                       value="${submitted.observerAddress}" onfocus="geolocate()"
                                       title="Vui lòng nhập địa chỉ của người làm chứng"
                                       placeholder="Ví dụ: 73B, Nguyễn Trãi, Quận 5, TP.HCM">
                            </div>
                        </div>

                        <!-- Compensation note -->
                        <div class="form-group">
                            <label class="col-sm-3 control-label" for="compensationNote">
                                Yêu cầu bồi thường
                            </label>

                            <div class="col-sm-8">
                                <textarea id="compensationNote" name="compensation:compensationNote" maxlength="2000"
                                          title="Vui lòng nhập yêu cầu bồi thường"
                                          class="form-control input-md valueInput"
                                          rows="2">${submitted.compensationNote}</textarea>
                            </div>
                        </div>

                        <!-- Attachment -->
                        <%--<div class="form-group">--%>
                            <%--<label class="col-sm-3 control-label" for="attachment">--%>
                                <%--Biên bản của CQCA--%>
                            <%--</label>--%>

                            <%--<div class="col-sm-6">--%>
                                <%--<input id="attachment" name="compensation:attachment" type="hidden" maxlength="255">--%>
                                <%--<img id="imgAttachment" height="100px" src=""/>--%>
                                <%--<button type="button" id="pickAttachment" class="btn btn-primary">Chọn tập tin</button>--%>
                                <%--<script type="text/javascript" src="//api.filepicker.io/v2/filepicker.js"></script>--%>
                                <%--<input type="filepicker" data-fp-apikey="AEbPPQfPfRHqODjEl5AZ2z" class="hide filePicker"--%>
                                       <%--onchange="$('#imgAttachment').attr('src', event.fpfile.url);$('#attachment').val(event.fpfile.url);">--%>
                            <%--</div>--%>
                        <%--</div>--%>
                    </fieldset>


                    <br/>
                    <!-- Create new customer button -->
                    <div class="text-center">
                        <input type="hidden" name="action" value="create"/>
                        <input type="hidden" name="compensation:contractCode" value="${contractCode}">
                        <input type="hidden" name="contractCode" value="${contractCode}">

                        <div class="form-group hide">
                            <div class="col-sm-3">
                                <input id="createdDate" name="compensation:createdDate"
                                       class="form-control input-md" type="date" required>
                            </div>

                        </div>

                        <button type="submit" class="btn btn-success">
                            <i class="fa fa-arrow-right"></i>
                            Gởi yêu cầu bồi thường
                        </button>

                    </div>
                </form>
                <br/>
            </div>
        </div>


    </div>
</div>
<!-- /#page-wrapper -->


<!-- /#wrapper -->


<%@ include file="_shared/footer.jsp" %>

<script src="${pageContext.request.contextPath}/js/geolocation.js" type="text/javascript"></script>
<!-- Google API Autocomplete for address-->
<script src="https://maps.googleapis.com/maps/api/js?v=3.exp&signed_in=true&libraries=places"></script>

<script type="text/javascript">
    $(document).ready(function () {
        $('#pickAttachment').click(function () {
            $('.filePicker').trigger("click");
        });
        if ($('#createdDate').val() == "") {
            $('#createdDate').val(getCurrentDate());
        }
        document.getElementById("createdDate").max = getCurrentDate();
        if ($('#accidentDate').val() == "") {
            $('#accidentDate').val(getCurrentDate());
        }
        document.getElementById("accidentDate").max = getCurrentDate();
        $('input[type=text]').each(function () {
            if ($(this).val() == '') {
                $(this).addClass('textColor');
            }
        });
        $('input[type=text]').change(function () {
            if ($(this).val() != '') {
                $(this).removeClass('textColor');
            }
        });
        /////////////////////////////////////////
        $('.valueInput').each(function () {
            if ($(this).val() == '') {
                $(this).addClass('textColor');
            }
        });
        $('.valueInput').change(function () {
            if ($(this).val() != '') {
                $(this).removeClass('textColor');
            }
        });
    });
    $(document).ready(function () {
        initialize1();
        initialize2();
        initialize3();
    });
</script>
<style type="text/css">
    .textColor {
        background-color: bisque;
    }
</style>
