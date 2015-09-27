<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="_shared/header.jsp" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<div id="wrapper">

    <c:set var="compensation" value="${requestScope.COMPENSATION}"/>

    <%@ include file="_shared/navigation.jsp" %>
    <div id="page-wrapper">
        <div class="row">
            <div class="col-lg-12">
                <h1 class="page-header">Chỉnh sửa yêu cầu bồi thường ${compensation.compensationCode}</h1>
            </div>
            <!-- /.col-lg-12 -->
        </div>
        <div class="row">
            <div class="col-lg-12">
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
                <!-- Form to edit compensation -->
                <form id="editForm" action="${pageContext.request.contextPath}/staff/compensation" method="post"
                      class="form-horizontal">
                    <fieldset>
                        <!-- Contract input & Created date-->
                        <div class="form-group">
                            <label class="col-sm-3 control-label" for="contractCode">Mã hợp đồng *</label>

                            <div class="col-sm-3">
                                <div class="input-group">
                                    <input id="contractCode" name="edit:contractCode"
                                           class="form-control input-md" value="${not empty submitted.contractCode ? submitted.contractCode : compensation.contractCode}"
                                           readonly title="Ví dụ: HD2703"
                                           type="text" required pattern="^HD([0-9A-Z]{4,8})$">
                                    <span class="input-group-btn" data-toggle="tooltip" data-placement="top"
                                          id="btnTooltip" title="Chọn hợp đồng có sẵn trong hệ thống">
                                    <button id="contract-select-btn" type="button" class="btn btn-primary"
                                            data-toggle="modal" data-target="#select-contract-modal"
                                            onclick="loadContracts()">
                                        <i class="fa fa-search"></i> Chọn
                                    </button>
                                    </span>
                                </div>
                            </div>

                            <label class="col-sm-3 control-label" for="createdDate">Ngày gởi yêu cầu *</label>

                            <div class="col-sm-3">
                                <input id="createdDate" name="edit:createdDate"
                                       value="<fmt:formatDate value="${not empty submitted.createdDate ? submitted.createdDate : compensation.createdDate}" pattern="yyyy-MM-dd" />"
                                       class="form-control input-md" type="date" required>
                            </div>
                        </div>

                        <!-- Driver name & Phone number -->
                        <div class="form-group">
                            <label class="col-sm-3 control-label" for="driverName">Họ tên lái xe *</label>

                            <div class="col-sm-4">
                                <input id="driverName" name="edit:driverName" class="form-control input-md"
                                       type="text" required minlength="3" maxlength="80"
                                       pattern="^([^0-9`~!@#$%^&*,.<>;':/|{}()=_+-]+)$"
                                       title="Vui lòng nhập họ tên" placeholder="Ví dụ: Nguyễn Văn A"
                                       value="${not empty submitted.driverName ? submitted.driverName : compensation.driverName}">
                            </div>

                            <label class="col-sm-2 control-label" for="driverPhone">Điện thoại *</label>

                            <div class="col-sm-3">
                                <input id="driverPhone" name="edit:driverPhone" class="form-control input-md"
                                       type="tel" required minlength="8" maxlength="15"
                                       pattern="[0-9]+" title="Vui lòng chỉ nhập số" placeholder="Ví dụ: 0933270393"
                                       value="${not empty submitted.driverPhone ? submitted.driverPhone : compensation.driverPhone}">
                            </div>
                        </div>

                        <!-- Address input -->
                        <div class="form-group">
                            <label class="col-sm-3 control-label" for="driverAddress">Địa chỉ *</label>

                            <div class="col-sm-8">
                                <input id="driverAddress" name="edit:driverAddress"
                                       class="form-control input-md"
                                       type="text" required minlength="3" maxlength="250"
                                       value="${not empty submitted.driverAddress ? submitted.driverAddress : compensation.driverAddress}"
                                       onfocus="geolocate()" title="Vui lòng nhập địa chỉ"
                                       placeholder="Ví dụ: 123A, Điện Biên Phủ, Quận 1, TP.HCM">
                            </div>
                        </div>

                        <!-- Driver license -->
                        <div class="form-group">
                            <label class="col-sm-3 control-label" for="licenseNumber">Giấy phép lái xe số *</label>

                            <div class="col-sm-3">
                                <input id="licenseNumber" name="edit:licenseNumber" type="text"
                                       class="form-control input-md"
                                       required minlength="10" maxlength="15" placeholder="Ví dụ: 740118000357"
                                       value="${not empty submitted.licenseNumber ? submitted.licenseNumber : compensation.licenseNumber}"
                                       title="Vui lòng nhập số GPLX">
                            </div>

                            <label class="col-sm-3 control-label" for="licenseType">Hạng *</label>

                            <div class="col-sm-2">
                                <input id="licenseType" name="edit:licenseType"
                                       class="form-control input-md" type="text" title="Vui lòng nhập hạng GPLX"
                                       required minlength="1" maxlength="15" placeholder="Ví dụ: A1"
                                       value="${not empty submitted.licenseType ? submitted.licenseType : compensation.licenseType}">
                            </div>
                        </div>

                        <!-- Plate & Capacity -->
                        <div class="form-group">
                            <label class="col-sm-3 control-label" for="plate">Biển số xe gây tai nạn *</label>

                            <div class="col-sm-3">
                                <input id="plate" name="edit:plate" class="form-control input-md"
                                       type="text" required minlength="4" maxlength="15"
                                       title="Vui lòng nhập biển số xe!" placeholder="Ví dụ: 78Y9-15383"
                                       value="${not empty submitted.plate ? submitted.plate : compensation.plate}">
                            </div>
                            <label class="col-sm-3 control-label" for="vehicleCapacity">
                                Trọng tải/số chỗ ngồi *
                            </label>

                            <div class="col-sm-3">
                                <div class="input-group">
                                    <input id="vehicleCapacity" name="edit:vehicleCapacity"
                                           class="form-control input-md"
                                           type="text" required minlength="1" maxlength="20"
                                           title="Vui lòng nhập trọng tải hoặc số chỗ ngồi!" placeholder="Ví dụ: 7"
                                           value="${not empty submitted.vehicleCapacity ? submitted.vehicleCapacity : compensation.vehicleCapacity}">
                                    <span class="input-group-addon" id="basic-addon">(tấn/chỗ)</span>
                                </div>
                            </div>
                        </div>

                        <!-- Accident date -->
                        <div class="form-group">
                            <label class="col-sm-3 control-label" for="accidentDate">Ngày xảy ra tai nạn *</label>

                            <div class="col-sm-3">
                                <input id="accidentDate" name="edit:accidentDate"
                                       value="<fmt:formatDate value="${not empty submitted.accidentDate ? submitted.accidentDate : compensation.accidentDate}" pattern="yyyy-MM-dd" />"
                                       class="form-control input-md" type="date" required>
                            </div>
                        </div>

                        <!-- Accident place -->
                        <div class="form-group">
                            <label class="col-sm-3 control-label" for="accidentPlace">Nơi xảy ra tai nạn *</label>

                            <div class="col-sm-8">
                                <input id="accidentPlace" name="edit:accidentPlace"
                                       class="form-control input-md" onfocus="geolocate()"
                                       type="text" required minlength="3" maxlength="250"
                                       value="${not empty submitted.accidentPlace ? submitted.accidentPlace : compensation.accidentPlace}"
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
                                <input id="controlDepartment" name="edit:controlDepartment"
                                       class="form-control input-md"
                                       type="text" required minlength="3" maxlength="250"
                                       value="${not empty submitted.controlDepartment ? submitted.controlDepartment : compensation.controlDepartment}"
                                       title="Vui lòng nhập cơ quan công an giải quyết tai nạn"
                                       placeholder="Ví dụ: Đội CSGT Bình Triệu">
                            </div>
                        </div>

                        <!-- Description -->
                        <div class="form-group">
                            <label class="col-sm-3 control-label" for="description">Diễn biến tai nạn *</label>

                            <div class="col-sm-8">
                                <textarea id="description" name="edit:description" rows="2"
                                          required maxlength="2000"
                                          title="Vui lòng nhập diễn biến và nguyên nhân tai nạn"
                                          class="form-control input-md">${not empty submitted.description ? submitted.description : compensation.description}</textarea>
                            </div>
                        </div>

                        <!-- Human damage -->
                        <div class="form-group">
                            <label class="col-sm-3 control-label" for="humanDamage">Thiệt hại về người *</label>

                            <div class="col-sm-8">
                                <textarea id="humanDamage" name="edit:humanDamage" rows="2"
                                          required maxlength="2000" title="Vui lòng nhập tình hình thiệt hại về người"
                                          class="form-control input-md">${not empty submitted.humanDamage ? submitted.humanDamage : compensation.humanDamage}</textarea>
                            </div>
                        </div>

                        <!-- Asset damage -->
                        <div class="form-group">
                            <label class="col-sm-3 control-label" for="assetDamage">Thiệt hại về tài sản *</label>

                            <div class="col-sm-8">
                                <textarea id="assetDamage" name="edit:assetDamage" rows="2"
                                          required maxlength="2000" title="Vui lòng nhập tình hình thiệt hại về tài sản"
                                          class="form-control input-md">${not empty submitted.assetDamage ? submitted.assetDamage : compensation.assetDamage}</textarea>
                            </div>
                        </div>

                        <!-- Observer -->
                        <div class="form-group">
                            <label class="col-sm-3 control-label" for="observer">Người làm chứng *</label>

                            <div class="col-sm-4">
                                <input id="observer" name="edit:observer" class="form-control input-md"
                                       type="text" required minlength="3" maxlength="80"
                                       pattern="^([^0-9`~!@#$%^&*,.<>;':/|{}()=_+-]+)$"
                                       value="${not empty submitted.observer ? submitted.observer : compensation.observer}"
                                       placeholder="Ví dụ: Đặng Thu Thảo" title="Vui lòng nhập tên người làm chứng">
                            </div>
                        </div>

                        <!-- Observer address -->
                        <div class="form-group">
                            <label class="col-sm-3 control-label" for="observerAddress">
                                Địa chỉ liên hệ *
                            </label>

                            <div class="col-sm-8">
                                <input id="observerAddress" name="edit:observerAddress"
                                       class="form-control input-md" onfocus="geolocate()"
                                       type="text" required minlength="3" maxlength="250"
                                       value="${not empty submitted.observerAddress ? submitted.observerAddress : compensation.observerAddress}"
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
                                <textarea id="compensationNote" name="edit:compensationNote" maxlength="2000"
                                          title="Vui lòng nhập yêu cầu bồi thường"
                                          class="form-control input-md"
                                          rows="2">${not empty submitted.compensationNote ? submitted.compensationNote : compensation.compensationNote}</textarea>
                            </div>
                        </div>

                        <!-- Attachment -->
                        <%--<div class="form-group">--%>
                            <%--<label class="col-sm-3 control-label" for="attachment">--%>
                                <%--Biên bản của CQCA--%>
                            <%--</label>--%>

                            <%--<div class="col-sm-6">--%>
                                <%--<input id="attachment" name="edit:attachment" type="hidden" maxlength="255"--%>
                                       <%--value="${compensation.attachment}">--%>
                                <%--<img id="imgAttachment" height="100px" src="${compensation.attachment}"/>--%>
                                <%--<button type="button" id="pickAttachment" class="btn btn-primary">Chọn tập tin</button>--%>
                                <%--<script type="text/javascript" src="//api.filepicker.io/v2/filepicker.js"></script>--%>
                                <%--<input type="filepicker" data-fp-apikey="AEbPPQfPfRHqODjEl5AZ2z" class="hide filePicker"--%>
                                       <%--onchange="$('#imgAttachment').attr('src', event.fpfile.url);$('#attachment').val(event.fpfile.url);">--%>
                            <%--</div>--%>
                        <%--</div>--%>
                    </fieldset>
                    <br/>
                    <%--/Compensation information--%>
                    <c:if test="${not empty compensation.resolveDate}">
                        <fieldset>
                            <legend>Thông tin giải quyết yêu cầu bồi thường</legend>

                            <!-- Resolve date -->
                            <div class="form-group">
                                <label class="col-sm-3 control-label" for="resolveDate">Ngày giải quyết</label>

                                <div class="col-sm-3">
                                    <input id="resolveDate" name="edit:resolveDate" type="date"
                                           value="<fmt:formatDate value="${not empty submitted.resolveDate ? submitted.resolveDate : compensation.resolveDate}" pattern="yyyy-MM-dd" />"
                                           class="form-control input-md">
                                </div>
                            </div>

                            <!-- Decision -->
                            <div class="form-group">
                                <label class="col-sm-3 control-label" for="decision">Quyết định của công ty</label>

                                <div class="col-sm-4">
                                    <select class="form-control" id="decision" name="edit:decision">
                                        <option value="Đồng ý bồi thường">Đồng ý bồi thường</option>
                                        <option value="Từ chối bồi thường">Từ chối bồi thường</option>
                                    </select>
                                </div>
                            </div>

                            <!-- Resolve note -->
                            <div class="form-group">
                                <label class="col-sm-3 control-label" for="resolveNote">Ghi chú</label>

                                <div class="col-sm-8">
                                <textarea id="resolveNote" name="edit:resolveNote" rows="3" maxlength="2000"
                                          title="Vui lòng nhập ghi chú của công ty"
                                          class="form-control input-md">${not empty submitted.resolveNote ? submitted.resolveNote : compensation.resolveNote}</textarea>
                                </div>
                            </div>
                        </fieldset>
                    </c:if>
                    <%--/Resolve information --%>
                    <!-- Update compensation information button -->
                    <div class="text-center">
                        <input type="hidden" name="edit:compensationCode" value="${compensation.compensationCode}"/>
                        <input type="hidden" name="action" value="edit"/>
                        <button type="submit" id="btnEdit" class="btn btn-primary">
                            <i class="fa fa-pencil"></i> Cập nhật yêu cầu bồi thường
                        </button>
                        <br/> <br/>
                        <a href="${pageContext.request.contextPath}/staff/compensation?action=detail&code=${compensation.compensationCode}"
                           type="button" class="btn btn-default">
                            <i class="fa fa-arrow-left"></i> <strong>Xem chi tiết yêu cầu này</strong>
                        </a>
                    </div>
                </form>
            </div>
            <br/> <br/>
            <!-- /.col-lg-12 -->
        </div>
        <!-- /.row -->
    </div>
    <!-- /.page-wrapper -->
</div>
<!-- /#wrapper -->

<!-- model for select contract -->
<div class="modal fade" id="select-contract-modal">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span
                        aria-hidden="true">&times;</span></button>
                <h4 class="modal-title">Chọn hợp đồng đã có sẵn trong hệ thống</h4>
            </div>
            <div class="modal-body">
                <input type="text" class="form-control" id="select-contract-keyword"
                       placeholder="Tìm theo mã hợp đồng hoặc tên khách hàng"/>
                <br/>

                <div class="table-responsive">
                    <table class="table table-striped table-bordered table-hover">
                        <thead>
                        <tr>
                            <th>#</th>
                            <th>Mã hợp đồng</th>
                            <th>Tên khách hàng</th>
                            <th>Chọn</th>
                        </tr>
                        </thead>
                        <tbody id="list-items">
                        </tbody>
                    </table>
                </div>
                <!-- /.table-responsive -->
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">Đóng</button>
            </div>
        </div>
        <!-- /.modal-content -->
    </div>
    <!-- /.modal-dialog -->
</div>
<!-- /.modal -->

<script src="${pageContext.request.contextPath}/js/geolocation.js" type="text/javascript"></script>
<!-- Google API Autocomplete for address-->
<script src="https://maps.googleapis.com/maps/api/js?v=3.exp&signed_in=true&libraries=places"></script>

<script type="text/javascript">
    initialize1();
    initialize2();
    initialize3();
    $(document).ready(function () {
        // Use Vietnamese button to open File Picker dialog
        $('#pickAttachment').click(function () {
            $('.filePicker').trigger("click");
        });
        // Set accident date value and restriction follow up created date
        document.getElementById("createdDate").max = getCurrentDate();
        document.getElementById("accidentDate").max = getCurrentDate();
        $('#createdDate').change(function () {
            document.getElementById("accidentDate").max = $('#createdDate').val();
        });
        var decision = '${compensation.decision}';
        if (decision != null) {
            $('#decision').val(decision);
        }

        // Ajax load for search box in contract select modal
        var ajaxDelay;
        $('#select-contract-keyword').keyup(function () {
            clearTimeout(ajaxDelay);
            ajaxDelay = setTimeout(function () {
                loadContracts();
            }, 500);
        });

        // Auto open modal to select contract (do not allow handy enter contract code)
        $('#contractCode').click(function () {
            $('#contract-select-btn').click();
        })
    });

    function escapeHtml(unsafe) {
        return unsafe
                .replace(/&/g, "&amp;")
                .replace(/</g, "&lt;")
                .replace(/>/g, "&gt;")
                .replace(/"/g, "&quot;")
                .replace(/'/g, "&#039;");
    }

    function showContractInfo(info) {
        $('#contractCode').val(info.contractCode);
    }

    function loadContracts() {
        var keyword = $('#select-contract-keyword').val();

        var updateList = function (items) {
            var html = '';
            if (!items || items.length == 0) {
                html = '<tr><td class="text-center" colspan="4">Không có hợp đồng nào</td></tr>'
            } else {
                for (var i = 0; i < items.length; i++) {
                    var item = items[i];
                    html += '<tr>' +
                            '<td>' + (i + 1) + '</td>' +
                            '<td>' + item.contractCode + '</td>' +
                            '<td>' + item.micCustomerByCustomerCode.name + '</td>' +
                            '<td><button data-dismiss="modal" type="button" class="btn btn-primary btn-xs"' +
                            'onclick="showContractInfo(' + escapeHtml(JSON.stringify(item)) + ')">' +
                            '<i class="fa fa-check"></i> Chọn</button></td>' +
                            '</tr>';
                }
            }
            $('#list-items').html(html);
        };

        $('#list-items').html('<tr><td class="text-center" colspan="4">Đang tìm kiếm...</td></tr>');
        $.ajax({
            url: '/ajax',
            method: 'get',
            dataType: 'json',
            data: {
                action: 'loadContracts',
                keyword: keyword
            }
        }).done(function (contracts) {
            updateList(contracts);
        }).fail(function () {
            updateList([]);
        });
    }
</script>

<%@ include file="_shared/footer.jsp" %>