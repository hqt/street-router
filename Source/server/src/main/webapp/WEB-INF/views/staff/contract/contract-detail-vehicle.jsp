<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<fieldset>
    <legend>Thông tin về xe cơ giới
        <div class="pull-right" style="margin-top: -5px;">
            <a href="${pageContext.request.contextPath}/staff/contract?action=editVehicle&code=${contract.contractCode}"
               type="button" class="btn btn-xs btn-primary editBtn">
                <i class="fa fa-pencil"></i> Chỉnh sửa
            </a>
        </div>
    </legend>

    <!-- Plate number & brand -->
    <div class="form-group">
        <label class="col-sm-3 control-label">Biển số đăng ký</label>

        <div class="col-sm-3">
            <div class="text-value">${contract.plate}</div>
        </div>

        <label class="col-sm-2 control-label">Nhãn hiệu</label>

        <div class="col-sm-3">
            <div class="text-value">${contract.brand}</div>
        </div>
    </div>

    <!-- Chassis & Engine -->
    <div class="form-group">
        <label class="col-sm-3 control-label">Số khung</label>

        <div class="col-sm-3">
            <div class="text-value">${contract.chassis}</div>
        </div>

        <label class="col-sm-2 control-label">Số máy</label>

        <div class="col-sm-3">
            <div class="text-value">${contract.engine}</div>
        </div>
    </div>

    <!-- Capacity & Color -->
    <div class="form-group">
        <label class="col-sm-3 control-label">Dung tích</label>

        <div class="col-sm-3">
            <div class="text-value">${contract.capacity}</div>
        </div>

        <label class="col-sm-2 control-label">Màu sơn</label>

        <div class="col-sm-3">
            <div class="text-value">
                <c:choose>
                    <c:when test="${empty contract.color}">
                        <span class="empty-value">Không có</span>
                    </c:when>
                    <c:otherwise>${contract.color}</c:otherwise>
                </c:choose>
            </div>
        </div>
    </div>

    <!-- Vehicle type & model code -->
    <div class="form-group">
        <label class="col-sm-3 control-label">Số loại</label>

        <div class="col-sm-3">
            <div class="text-value">
                <c:choose>
                    <c:when test="${empty contract.modelCode}">
                        <span class="empty-value">Không có</span>
                    </c:when>
                    <c:otherwise>${contract.modelCode}</c:otherwise>
                </c:choose>
            </div>
        </div>

        <label class="col-sm-2 control-label">Loại xe</label>

        <div class="col-sm-3">
            <div class="text-value">
                <c:choose>
                    <c:when test="${empty contract.vehicleType}">
                        <span class="empty-value">Không có</span>
                    </c:when>
                    <c:otherwise>${contract.vehicleType}</c:otherwise>
                </c:choose>
            </div>
        </div>
    </div>

    <!-- Year of manufacture & empty weight -->
    <div class="form-group">
        <label class="col-sm-3 control-label">Năm sản xuất</label>

        <div class="col-sm-2">
            <div class="text-value">
                <c:choose>
                    <c:when test="${empty contract.yearOfManufacture}">
                        <span class="empty-value">Không có</span>
                    </c:when>
                    <c:otherwise>${contract.yearOfManufacture}</c:otherwise>
                </c:choose>
            </div>
        </div>

        <label class="col-sm-3 control-label">Tự trọng</label>

        <div class="col-sm-2">
            <div class="text-value">
                <c:choose>
                    <c:when test="${empty contract.weight}">
                        <span class="empty-value">Không có</span>
                    </c:when>
                    <c:otherwise>${contract.weight} kg</c:otherwise>
                </c:choose>
            </div>
        </div>
    </div>

    <div class="form-group">
        <label class="col-sm-3 control-label">Số người được chở</label>

        <div class="col-sm-2">
            <div class="text-value">
                <c:choose>
                    <c:when test="${empty contract.seatCapacity}">
                        <span class="empty-value">Không có</span>
                    </c:when>
                    <c:otherwise>${contract.seatCapacity} người</c:otherwise>
                </c:choose>
            </div>
        </div>
    </div>
</fieldset>