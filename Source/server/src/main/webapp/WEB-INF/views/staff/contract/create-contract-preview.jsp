<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="../_shared/header.jsp" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<div id="wrapper">

    <c:set var="contract" value="${requestScope.CONTRACT}" scope="request"/>
    <c:set var="customer" value="${requestScope.CUSTOMER}" scope="request"/>
    <c:set var="type" value="${requestScope.TYPE}"/>

    <%@ include file="../_shared/navigation.jsp" %>
    <div id="page-wrapper">
        <div class="row">
            <div class="col-lg-12">
                <h1 class="page-header">Thêm hợp đồng mới</h1>
            </div>
            <!-- /.col-lg-12 -->
        </div>
        <div class="row">
            <div class="col-lg-12">
                <div class="well text-center text-primary">
                    Vui lòng kiểm tra lại các thông tin trong hợp đồng chính xác.<br/>
                    Nhấn nút <b>Hoàn tất hợp đồng</b> ở cuối trang để hoàn tất hợp đồng
                </div>

                <form action="${pageContext.request.contextPath}/staff/contract" id="reviewForm"
                      method="post" class="form-horizontal">
                    <jsp:include page="contract-detail-customer.jsp" flush="true"/>
                    <br/>
                    <fieldset>
                        <legend>Thông tin về dịch vụ bảo hiểm</legend>
                        <!-- Contract type -->
                        <div class="form-group">
                            <label class="col-sm-3 control-label">Loại hợp đồng</label>

                            <div class="col-sm-7">
                                <div class="text-value">${type.name}</div>
                            </div>
                        </div>
                        <!-- Start date -->
                        <div class="form-group">
                            <label class="col-sm-3 control-label">Bắt đầu có hiệu lực từ</label>

                            <div class="col-sm-5">
                                <div class="text-value">
                                    ngày <fmt:formatDate value="${contract.startDate}" pattern="dd"/>
                                    tháng <fmt:formatDate value="${contract.startDate}" pattern="MM"/>
                                    năm <fmt:formatDate value="${contract.startDate}" pattern="yyyy"/>
                                </div>
                            </div>
                        </div>
                        <!-- Expired date -->
                        <div class="form-group">
                            <label class="col-sm-3 control-label">Thời điểm hết hiệu lực</label>

                            <div class="col-sm-5">
                                <div class="text-value">
                                    ngày <fmt:formatDate value="${contract.expiredDate}" pattern="dd"/>
                                    tháng <fmt:formatDate value="${contract.expiredDate}" pattern="MM"/>
                                    năm <fmt:formatDate value="${contract.expiredDate}" pattern="yyyy"/>
                                </div>
                            </div>
                        </div>
                    </fieldset>
                    <br/>
                    <fieldset>
                        <legend>Thông tin về xe cơ giới</legend>

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
                    <br/>
                    <fieldset>
                        <legend>Thông tin thanh toán</legend>
                        <div class="form-group">
                            <label class="col-sm-3 control-label">Số tiền phí đã trả</label>

                            <div class="col-sm-3">
                                <div class="text-value">
                                    <fmt:setLocale value="vi_VN"/>
                                    <fmt:formatNumber value="${contract.amount}" type="currency"
                                                      currencySymbol="" maxFractionDigits="0"/> VNĐ
                                </div>
                            </div>

                            <label class="col-sm-2 control-label">Ngày nộp phí</label>

                            <div class="col-sm-3">
                                <div class="text-value">
                                    <fmt:formatDate value="${contract.paidDate}" pattern="dd/MM/yyyy"/>
                                </div>
                            </div>
                        </div>
                    </fieldset>
                    <br/>
                    <%-- Set submitted dto to hidden input --%>
                    <input type="hidden" name="contract:customerCode" value="${contract.customerCode}"/>
                    <input type="hidden" name="contract:contractTypeId" value="${contract.contractTypeId}"/>
                    <input type="hidden" name="contract:startDate" value="${contract.startDate}"/>
                    <input type="hidden" name="contract:expiredDate" value="${contract.expiredDate}"/>
                    <input type="hidden" name="contract:contractFee" value="${contract.contractFee}"/>
                    <input type="hidden" name="contract:amount" value="${contract.amount}"/>
                    <input type="hidden" name="contract:plate" value="${contract.plate}"/>
                    <input type="hidden" name="contract:brand" value="${contract.brand}"/>
                    <input type="hidden" name="contract:chassis" value="${contract.chassis}"/>
                    <input type="hidden" name="contract:engine" value="${contract.engine}"/>
                    <input type="hidden" name="contract:capacity" value="${contract.capacity}"/>
                    <input type="hidden" name="contract:color" value="${contract.color}"/>
                    <input type="hidden" name="contract:modelCode" value="${contract.modelCode}"/>
                    <input type="hidden" name="contract:vehicleType" value="${contract.vehicleType}"/>
                    <input type="hidden" name="contract:yearOfManufacture" value="${contract.yearOfManufacture}"/>
                    <input type="hidden" name="contract:weight" value="${contract.weight}"/>
                    <input type="hidden" name="contract:seatCapacity" value="${contract.seatCapacity}"/>
                    <input type="hidden" name="contract:paidDate" value="${contract.paidDate}"/>

                    <div class="text-center">
                        <input type="hidden" id="action-value" name="action" value="returnToEdit" />
                        <button type="button" class="btn btn-success submitButtonOnClick" value="create">
                            <i class="fa fa-arrow-right"></i> Hoàn tất hợp đồng
                        </button>
                        <br/> <br/>
                        <button type="button" class="btn btn-default submitButtonOnClick" value="returnToEdit">
                            <i class="fa fa-arrow-left"></i> Quay lại chỉnh sửa
                        </button>
                    </div>
                    <br/> <br/>
                </form>
            </div>
        </div>
    </div>
</div>
<!-- /#wrapper -->

<script type="text/javascript">
    $(document).ready(function () {
        $(".editBtn").addClass('hide');

        $(".submitButtonOnClick").on("click", function(){
            var valueAction = this.getAttribute("value");
            $("#action-value").val(valueAction);
            $("#reviewForm").submit();
        });
    });
</script>

<%@ include file="../_shared/footer.jsp" %>