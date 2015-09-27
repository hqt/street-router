<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="../_shared/header.jsp" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<div id="wrapper">

    <c:set var="contract" value="${requestScope.CONTRACT}" scope="request"/>

    <%@ include file="../_shared/navigation.jsp" %>
    <div id="page-wrapper">
        <div class="row">
            <div class="col-lg-12">
                <h1 class="page-header">Chỉnh sửa thông tin xe cơ giới</h1>
            </div>
            <!-- /.col-lg-12 -->
        </div>
        <div class="row">
            <div class="col-lg-12">

                <c:if test="${not empty validateErrors}">
                    <input type="hidden" id="modify-reason" value="${contract.modifyReason}"/>

                    <div class="text-danger">
                        <ul>
                            <c:forEach var="error" items="${validateErrors}">
                                <li>${error}</li>
                            </c:forEach>
                        </ul>
                    </div>
                </c:if>

                <!-- Form to create new customer -->
                <form action="${pageContext.request.contextPath}/staff/contract" method="post" class="form-horizontal">
                    <jsp:include page="contract-detail-general.jsp" flush="true"/>
                    <fieldset>
                        <legend>Thông tin về xe cơ giới</legend>
                        <p class="text-right"><b>Các ô có dấu * là bắt buộc</b></p>
                        <br/>
                        <!-- Plate & Brand -->
                        <div class="form-group">
                            <label class="col-sm-3 control-label" for="plate">Biển số đăng ký *</label>

                            <div class="col-sm-3">
                                <input id="plate" name="edit:plate" class="form-control input-md"
                                       type="text" required minlength="4" maxlength="15"
                                       title="Vui lòng nhập biển số xe" placeholder="Ví dụ: 78Y9-15383"
                                       <c:if test="${empty submitted}">value="${contract.plate}"</c:if>
                                       <c:if test="${not empty submitted}">value="${submitted.plate}"</c:if>
                                        >
                            </div>

                            <label class="col-sm-2 control-label" for="brand">Nhãn hiệu *</label>

                            <div class="col-sm-3">
                                <input id="brand" name="edit:brand" class="form-control input-md"
                                       type="text" required minlength="2" maxlength="20"
                                       title="Vui lòng nhập nhãn hiệu xe" placeholder="Ví dụ: Honda"
                                       <c:if test="${empty submitted}">value="${contract.brand}"</c:if>
                                       <c:if test="${not empty submitted}">value="${submitted.brand}"</c:if>
                                        >
                            </div>
                        </div>

                        <!-- Engine & Chassis -->
                        <div class="form-group">
                            <label class="col-sm-3 control-label" for="chassis">Số khung *</label>

                            <div class="col-sm-3">
                                <input id="chassis" name="edit:chassis" class="form-control input-md"
                                       type="text" required minlength="2" maxlength="20"
                                       title="Vui lòng nhập số khung xe!"
                                       <c:if test="${empty submitted}">value="${contract.chassis}"</c:if>
                                       <c:if test="${not empty submitted}">value="${submitted.chassis}"</c:if>
                                        >
                            </div>

                            <label class="col-sm-2 control-label" for="engine">Số máy *</label>

                            <div class="col-sm-3">
                                <input id="engine" name="edit:engine" class="form-control input-md"
                                       type="text" required minlength="2" maxlength="20"
                                       title="Vui lòng nhập số máy xe!"
                                       <c:if test="${empty submitted}">value="${contract.engine}"</c:if>
                                       <c:if test="${not empty submitted}">value="${submitted.engine}"</c:if>
                                        >
                            </div>
                        </div>

                        <!-- Color & Capacity -->
                        <div class="form-group">
                            <label class="col-sm-3 control-label" for="capacity">Dung tích *</label>

                            <div class="col-sm-2">
                                <input id="capacity" name="edit:capacity" class="form-control input-md"
                                       type="text" required minlength="2" maxlength="20"
                                       title="Vui lòng nhập dung tích xe!"
                                       <c:if test="${empty submitted}">value="${contract.capacity}"</c:if>
                                       <c:if test="${not empty submitted}">value="${submitted.capacity}"</c:if>
                                        >
                            </div>

                            <label class="col-sm-3 control-label" for="color">Màu sơn</label>

                            <div class="col-sm-3">
                                <input id="color" name="edit:color" class="form-control input-md"
                                       type="text" minlength="2" maxlength="20"
                                       title="Vui lòng nhập màu sơn xe!"
                                       <c:if test="${empty submitted}">value="${contract.color}"</c:if>
                                       <c:if test="${not empty submitted}">value="${submitted.color}"</c:if>
                                        >
                            </div>
                        </div>

                        <!-- Vehicle type & Model code -->
                        <div class="form-group">
                            <label class="col-sm-3 control-label" for="modelCode">Số loại</label>

                            <div class="col-sm-3">
                                <input id="modelCode" name="edit:modelCode" class="form-control input-md"
                                       type="text" minlength="2" maxlength="20"
                                       title="Vui lòng nhập số loại xe!" placeholder="Ví dụ: Air Blade"
                                       <c:if test="${empty submitted}">value="${contract.modelCode}"</c:if>
                                       <c:if test="${not empty submitted}">value="${submitted.modelCode}"</c:if>
                                        >
                            </div>

                            <label class="col-sm-2 control-label" for="vehicleType">Loại xe</label>

                            <div class="col-sm-3">
                                <input id="vehicleType" name="edit:vehicleType" class="form-control input-md"
                                       type="text" minlength="2" maxlength="20"
                                       title="Vui lòng nhập loại xe!" placeholder="Ví dụ: Hai bánh"
                                       <c:if test="${empty submitted}">value="${contract.vehicleType}"</c:if>
                                       <c:if test="${not empty submitted}">value="${submitted.vehicleType}"</c:if>
                                        >
                            </div>
                        </div>

                        <!-- Year of manufacture & Weight -->
                        <div class="form-group">
                            <label class="col-sm-3 control-label" for="yearOfMan">Năm sản xuất</label>

                            <div class="col-sm-2">
                                <input id="yearOfMan" name="edit:yearOfManufacture" class="form-control input-md"
                                       type="number" min="1900" title="Vui lòng nhập năm sản xuất xe!"
                                       <c:if test="${empty submitted}">value="${contract.yearOfManufacture}"</c:if>
                                       <c:if test="${not empty submitted}">value="${submitted.yearOfManufacture}"</c:if>
                                        >
                            </div>

                            <label class="col-sm-3 control-label" for="weight">Tự trọng</label>

                            <div class="col-sm-3">
                                <div class="input-group">
                                    <input id="weight" name="edit:weight" class="form-control input-md"
                                           type="number" min="1" max="1000"
                                           title="Vui lòng nhập tự trọng của xe!"
                                           <c:if test="${empty submitted}">value="${contract.weight}"</c:if>
                                           <c:if test="${not empty submitted}">value="${submitted.weight}"</c:if>
                                            >
                                    <span class="input-group-addon">kg</span>
                                </div>
                            </div>
                        </div>

                        <!-- Seat capacity -->
                        <div class="form-group">
                            <label class="col-sm-3 control-label" for="seatCapacity">Số người được chở</label>

                            <div class="col-sm-3">
                                <div class="input-group">
                                    <input id="seatCapacity" name="edit:seatCapacity" class="form-control input-md"
                                           type="number" min="1" max="100"
                                           title="Vui lòng nhập số người được phép chở!"
                                           <c:if test="${empty submitted}">value="${contract.seatCapacity}"</c:if>
                                           <c:if test="${not empty submitted}">value="${submitted.seatCapacity}"</c:if>
                                            >
                                    <span class="input-group-addon">người</span>
                                </div>
                            </div>
                        </div>
                    </fieldset>
                    <%--/Vehicle information--%>
                    <br/>
                    <!-- Edit & return button -->
                    <div class="text-center">
                        <input type="hidden" name="edit:contractCode" value="${contract.contractCode}"/>
                        <input type="hidden" name="action" value="editVehicle"/>
                        <button type="submit" id="btnEdit" class="btn btn-primary">
                            <i class="fa fa-pencil"></i> Cập nhật thông tin xe cơ giới
                        </button>
                        <br/><br/>
                        <a href="${pageContext.request.contextPath}/staff/contract?action=detail&code=${contract.contractCode}"
                           type="button" class="btn btn-default">
                            <i class="fa fa-arrow-left"></i> <strong>Xem chi tiết hợp đồng hiện tại</strong>
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

<script type="text/javascript">
    $(document).ready(function () {
        document.getElementById("yearOfMan").max = new Date().getFullYear();
    });
</script>

<%@ include file="../_shared/footer.jsp" %>